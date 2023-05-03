package com.oshewo.panic.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.*;

import com.oshewo.panic.base.*;
import com.oshewo.panic.enums.*;
import com.oshewo.panic.stations.*;
import com.oshewo.panic.tools.*;
import com.oshewo.panic.*;
import com.oshewo.panic.scenes.*;
import com.oshewo.panic.actor.*;

import java.util.*;

import static com.oshewo.panic.lists.Lists.*;

/**
 * The Play screen is the screen featuring the actual game
 *
 * @author Oshewo, sl3416
 * @author Liam Burnand
 */
public class PlayScreen extends BaseScreen {   // Contains game in the screen

    // sets up world and map for the game
    private final PiazzaPanic game;
    private final OrthographicCamera gameCam;
    private final Viewport gamePort;
    private final TiledMap map;
    private final OrthoCachedTiledMapRenderer renderer;

    // Hud
    public Hud hud;
    public static OrderHud orderHud;

    // Chef
    public final ChefActor[] chefs;
    private int chefSelector = 0, lastMove, binnedItems = 0;

    private boolean tabPressed = false, inPauseScreen = false;

    // Order
    private final OrderSystem orderSystem;
    private int ordersCompleted = 0;

    private long timeUntilNextPowerUp;
    public long timeUntilResetChefSpeed = -1, timeUntilResetCookingMultiplier = -1, timeUntilResetChoppingMultiplier = -1;
    private PowerUpActor powerUp;
    private final Random random = new Random();
    public float movementMultiplier = 1, choppingTimerMultiplier = 1, cookingTimerMultiplier = 1;

    private final HashMap<Long, Long> timesInPause = new HashMap<>();

    /**
     * Instantiates a new Play screen.
     *
     * @param game the game
     */
    public PlayScreen(PiazzaPanic game) {
        super();

        this.game = game;

        this.hud = new Hud(0, 0, super.uiStage, this.game);
        orderHud = new OrderHud(0, 80, super.uiStage);

        this.gameCam = new OrthographicCamera(this.game.V_WIDTH, this.game.V_HEIGHT);
        this.gamePort = new FitViewport(this.game.V_WIDTH, this.game.V_HEIGHT, this.gameCam);
        TmxMapLoader mapLoader = new TmxMapLoader();
        this.map = mapLoader.load("piazza-map-big2.tmx");
        this.renderer = new OrthoCachedTiledMapRenderer(this.map);
        this.renderer.render();
        this.gameCam.position.set((this.game.V_WIDTH / 2), (this.game.V_HEIGHT / 2), 0); // 0,0 is apparently in the centre of the screen maybe...
        WorldCreator();

        if (this.game.MODE == GameMode.SCENARIO)
            this.chefs = new ChefActor[2];
        else
            this.chefs = new ChefActor[3];

        this.chefs[0] = new ChefActor(400, 200, super.uiStage, this.game, 0);
        this.chefs[1] = new ChefActor(450, 200, super.uiStage, this.game, 1);
        if (this.game.MODE == GameMode.ENDLESS) this.chefs[2] = new ChefActor(500, 200, super.uiStage, this.game, 2);

        this.orderSystem = new OrderSystem(this, this.game);
        this.timeUntilNextPowerUp = new Date().getTime() + (this.random.nextInt(30) + 180) * 1000;
    }

    /**
     * Update screen method, used by libgdx
     *
     * @param dt deltaTime
     */
    public void update(float dt) {
        this.gameCam.update();
        this.renderer.setView(this.gameCam);
        this.renderer.render();

        handleInput();

        for (FoodActor foodActor : new ArrayList<>(foodActors))
            foodActor.update(this);
        for (Station stove : stoves)
            stove.update(this);
        for (Station choppingBoard : choppingBoards)
            choppingBoard.update(this);
        for (Station servingStation : servingStations)
            servingStation.update(this);
        for (StationTimer timer : new ArrayList<>(timers))
            timer.update(super.uiStage, this, this.game);
        for (Station bin : bins)
            bin.update(this);
        for (StationTimer timer : new ArrayList<>(timers))
            timer.update(super.uiStage, this, this.game);

        this.orderSystem.update();

        this.hud.update(this);

        powerUpHandler();
    }

    private void powerUpHandler() {
        if (this.timeUntilResetChefSpeed != -1) {
            if (((int) TimeUtils.timeSinceMillis(this.timeUntilResetChefSpeed) / 1000) >= 30) {
                this.movementMultiplier = 1;
                this.timeUntilResetChefSpeed = -1;
                this.powerUp = null;
                this.timeUntilNextPowerUp = new Date().getTime() + (random.nextInt(45) + 30) * 1000;
            }
        }
        if (this.timeUntilResetChoppingMultiplier != -1) {
            if (((int) TimeUtils.timeSinceMillis(this.timeUntilResetChoppingMultiplier) / 1000) >= 30) {
                this.choppingTimerMultiplier = 1;
                this.timeUntilResetChoppingMultiplier = -1;
                this.powerUp = null;
                this.timeUntilNextPowerUp = new Date().getTime() + (random.nextInt(45) + 30) * 1000;
            }
        }
        if (this.timeUntilResetCookingMultiplier != -1) {
            if (((int) TimeUtils.timeSinceMillis(this.timeUntilResetCookingMultiplier) / 1000) >= 30) {
                this.cookingTimerMultiplier = 1;
                this.timeUntilResetCookingMultiplier = -1;
                this.powerUp = null;
                this.timeUntilNextPowerUp = new Date().getTime() + (random.nextInt(30) + 45) * 1000;
            }
        }
        if (this.powerUp == null) {
            if (this.timeUntilNextPowerUp < new Date().getTime()) {
                PowerUps powerUp;
                do {
                    powerUp = PowerUps.getRandomPowerUp();
                    System.out.println(powerUp);
                } while ((powerUp == PowerUps.EXTRA_LIFE && this.hud.getLives() == 4) || ((int) TimeUtils.timeSinceMillis(this.game.worldTimer) / 1000 > 180 && powerUp == PowerUps.CLEAR_NEXT_ORDER));
                this.powerUp = new PowerUpActor(super.uiStage, this, powerUp);
                if (this.game.VERBOSE) {
                    System.out.println(this.powerUp);
                }
            }
        } else if (!this.powerUp.listenerInit) {
            this.powerUp.listenerInit = true;
            this.powerUp.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    powerUp.activate();
                    powerUp.remove();
                    powerUp = null;
                    timeUntilNextPowerUp = new Date().getTime() + (random.nextInt(45) + 30) * 1000;
                }
            });
        }
    }

    /**
     * Handle input of picking up food that is nearest
     */
    public void handleInput() {
        try {
            if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
                float oldY = this.chefs[this.chefSelector].getY();
                this.chefs[this.chefSelector].setY(this.chefs[this.chefSelector].getY() + 300 * Gdx.graphics.getDeltaTime() * movementMultiplier);
                this.checkCollision(this.chefs[this.chefSelector].getX(), oldY);
                this.lastMove = Input.Keys.W;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
                float oldY = this.chefs[this.chefSelector].getY();
                this.chefs[this.chefSelector].setY(this.chefs[this.chefSelector].getY() - 300 * Gdx.graphics.getDeltaTime() * movementMultiplier);
                this.checkCollision(this.chefs[this.chefSelector].getX(), oldY);
                this.lastMove = Input.Keys.S;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
                float oldX = this.chefs[this.chefSelector].getX();
                this.chefs[this.chefSelector].setX(this.chefs[this.chefSelector].getX() + 300 * Gdx.graphics.getDeltaTime() * movementMultiplier);
                this.checkCollision(oldX, this.chefs[this.chefSelector].getY());
                this.lastMove = Input.Keys.D;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
                float oldX = this.chefs[this.chefSelector].getX();
                this.chefs[this.chefSelector].setX(this.chefs[this.chefSelector].getX() - 300 * Gdx.graphics.getDeltaTime() * movementMultiplier);
                this.checkCollision(oldX, this.chefs[this.chefSelector].getY());
                this.lastMove = Input.Keys.A;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.TAB) && !Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
                if (!this.tabPressed) {
                    if (this.game.VERBOSE) System.out.println("Tab pressed");
                    switch (this.game.MODE) {
                        case SCENARIO:
                            this.chefSelector++;
                            if (this.chefSelector == 2) this.chefSelector = 0;
                            break;
                        case ENDLESS:
                            this.chefSelector++;
                            if (this.chefSelector == 3) this.chefSelector = 0;
                            break;
                    }
                }
                this.tabPressed = true;
            } else
                this.tabPressed = false;
            if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                if (this.game.VERBOSE)
                    System.out.println("E");
                FoodActor nearestFoodActor = this.chefs[this.chefSelector].nearestFood(48);
                if (this.game.VERBOSE)
                    System.out.println(nearestFoodActor);
                for (StationTimer timer : timers) {
                    if (timer.isInteractionRequired()) {
                        for (Station station : stoves)
                            if (timer.getStationId().equals(station.getId()))
                                if (this.chefs[this.chefSelector].getBoundaryRectangle().overlaps(station.getBounds()))
                                    timer.interacted(super.uiStage, this, this.game);
                        for (Station station : ovens)
                            if (timer.getStationId().equals(station.getId()))
                                if (this.chefs[this.chefSelector].getBoundaryRectangle().overlaps(station.getBounds()))
                                    timer.interacted(super.uiStage, this, this.game);
                    }
                }
                if (nearestFoodActor != null) {
                    if (this.game.VERBOSE)
                        System.out.println(1);
                    nearestFoodActor.onUse();
                } else {
                    if (this.game.VERBOSE)
                        System.out.println(2);
                    for (FoodCrateBox crate : foodCrateBoxes) {
                        if (this.game.VERBOSE)
                            System.out.println(crate.toString());
                        if (crate.checkForChef()) {
                            this.game.setActiveScreen(new FoodChestScreen(this.game, this, super.uiStage));
                            break;
                        }
                    }
                }
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                if (this.game.VERBOSE)
                    System.out.println("ESC");
                this.inPauseScreen = true;
                this.game.setActiveScreen(new PauseScreen(this.game, this));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkCollision(float oldX, float oldY) {
        if (this.chefs[this.chefSelector].getX() + this.chefs[this.chefSelector].getWidth() > this.game.V_WIDTH || this.chefs[this.chefSelector].getX() < 0)
            this.chefs[this.chefSelector].setX(oldX);
        if (this.chefs[this.chefSelector].getY() + this.chefs[this.chefSelector].getHeight() > this.game.V_HEIGHT || this.chefs[this.chefSelector].getY() < 0)
            this.chefs[this.chefSelector].setY(oldY);
        for (BaseActor counter : walls) {
            if (counter.getBoundaryRectangle().overlaps(this.chefs[this.chefSelector].getBoundaryRectangle())) {
                this.chefs[this.chefSelector].setX(oldX);
                this.chefs[this.chefSelector].setY(oldY);
            }
        }
        switch (this.chefSelector) {
            case 0:
                if (this.game.MODE == GameMode.ENDLESS)
                    if (this.chefs[this.chefSelector].getBoundaryRectangle().overlaps(this.chefs[2].getBoundaryRectangle())) {
                        this.chefs[this.chefSelector].setX(oldX);
                        this.chefs[this.chefSelector].setY(oldY);
                    }
                if (this.chefs[this.chefSelector].getBoundaryRectangle().overlaps(this.chefs[1].getBoundaryRectangle())) {
                    this.chefs[this.chefSelector].setX(oldX);
                    this.chefs[this.chefSelector].setY(oldY);
                }
                break;
            case 1:
                if (this.game.MODE == GameMode.ENDLESS)
                    if (this.chefs[this.chefSelector].getBoundaryRectangle().overlaps(this.chefs[2].getBoundaryRectangle())) {
                        this.chefs[this.chefSelector].setX(oldX);
                        this.chefs[this.chefSelector].setY(oldY);
                    }
                if (this.chefs[this.chefSelector].getBoundaryRectangle().overlaps(this.chefs[0].getBoundaryRectangle())) {
                    this.chefs[this.chefSelector].setX(oldX);
                    this.chefs[this.chefSelector].setY(oldY);
                }
                break;
            case 2:
                if (this.chefs[this.chefSelector].getBoundaryRectangle().overlaps(this.chefs[0].getBoundaryRectangle())) {
                    this.chefs[this.chefSelector].setX(oldX);
                    this.chefs[this.chefSelector].setY(oldY);
                }
                if (this.chefs[this.chefSelector].getBoundaryRectangle().overlaps(this.chefs[1].getBoundaryRectangle())) {
                    this.chefs[this.chefSelector].setX(oldX);
                    this.chefs[this.chefSelector].setY(oldY);
                }
                break;
        }
    }

    private void WorldCreator() {
        for (MapLayer mapLayer : this.map.getLayers()) {
            switch (TiledAssets.getValueOf(mapLayer.getName())) {
                case WALLS:
                    InitialiseWalls(mapLayer);
                    break;
                case STOVES:
                    InitialiseStoves(mapLayer);
                    break;
                case CHOPPING_BOARD:
                    InitialiseChoppingCounter(mapLayer);
                    break;
                case SERVING_STATION:
                    InitialiseServiceStation(mapLayer);
                    break;
                case OVEN:
                    InitialiseOvens(mapLayer);
                    break;
                case FOOD_CRATE:
                    InitialiseFoodCrateObject(mapLayer);
                    break;
                case BIN:
                    InitialiseBinStation(mapLayer);
                    break;
                default:
                    break;
            }
        }
    }

    private void InitialiseWalls(MapLayer mapLayer) {
        for (RectangleMapObject object : mapLayer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();
            BaseActor baseActor = new BaseActor(rectangle.x, rectangle.y, super.uiStage);
            baseActor.setSize(rectangle.width, rectangle.height);
            baseActor.setBoundaryRectangle();
            walls.add(baseActor);
        }
    }

    private void InitialiseStoves(MapLayer mapLayer) {
        for (RectangleMapObject object : mapLayer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();
            stoves.add(new Station(StationType.STOVE, rectangle, this, super.uiStage));
        }
    }

    private void InitialiseOvens(MapLayer mapLayer){
        for (RectangleMapObject object : mapLayer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();
            ovens.add(new Station(StationType.OVEN, rectangle, this, super.uiStage));
        }
    }

    private void InitialiseChoppingCounter(MapLayer mapLayer) {
        for (RectangleMapObject object : mapLayer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();
            choppingBoards.add(new Station(StationType.CHOPPING_BOARD, rectangle, this,  super.uiStage));
        }
    }

    private void InitialiseServiceStation(MapLayer mapLayer) {
        for (RectangleMapObject object : mapLayer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();
            servingStations.add(new Station(StationType.SERVING, rectangle, this, super.uiStage));
        }
    }

    private void InitialiseFoodCrateObject(MapLayer mapLayer) {
        for (RectangleMapObject object : mapLayer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();
            foodCrateBoxes.add(new FoodCrateBox(rectangle, this, this.game));
        }
    }

    private void InitialiseBinStation(MapLayer mapLayer) {
        for (RectangleMapObject object : mapLayer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();
            bins.add(new Station(StationType.BIN, rectangle, this, super.uiStage));
        }
    }

    /**
     * Get the selected chef number
     *
     * @return int chefSelector
     */
    public int getChefSelector() {
        return this.chefSelector;
    }

    public int getLastMove() {
        return this.lastMove;
    }

    public void incrementOrderCompleted() {
        this.ordersCompleted++;
    }

    public void incrementBinnedItems() {
        this.binnedItems++;
    }

    public int getOrdersCompleted() {
        return this.ordersCompleted;
    }

    public int getBinnedItems() {
        return this.binnedItems;
    }

    public long getTimeUntilNextPowerUp() {
        return timeUntilNextPowerUp;
    }

    public long getTimeUntilResetChefSpeed() {
        return timeUntilResetChefSpeed;
    }

    public long getTimeUntilResetCookingMultiplier() {
        return timeUntilResetCookingMultiplier;
    }

    public long getTimeUntilResetChoppingMultiplier() {
        return timeUntilResetChoppingMultiplier;
    }

    public PowerUpActor getPowerUp() {
        return powerUp;
    }

    public float getMovementMultiplier() {
        return movementMultiplier;
    }

    public float getChoppingTimerMultiplier() {
        return choppingTimerMultiplier;
    }

    /**
     * Returns the cooking multiplier
     *
     * @return cookingTimeMultiplier
     */
    public float getCookingTimerMultiplier() {
        return cookingTimerMultiplier;
    }

    /**
     * Submits the duration the game was paused for
     *
     * @param timeInPause time in pause
     */
    public void submitPauseTime(long timeInPause) {
        this.timesInPause.put(new Date().getTime(), timeInPause);
        this.inPauseScreen = false;
    }

    /**
     * Returns if the game is paused
     *
     * @return boolean pause
     */
    public boolean isInPauseScreen() {
        return inPauseScreen;
    }

    /**
     * Returns the hash map of the times the game was paused
     *
     * @return Map<long, Long> timesInPause
     */
    public Map<Long, Long> getTimesInPause() {
        return this.timesInPause;
    }

    /**
     * Resizing code for the window
     *
     * @param width width
     * @param height height
     */
    public void resizing(int width, int height) {
        this.gamePort.update(width, height);
    }

    /**
     * Disposal of the screen
     */
    public void disposing() {
        this.map.dispose();
        this.renderer.dispose();
        this.mainStage.dispose();
        this.uiStage.dispose();
    }
}
