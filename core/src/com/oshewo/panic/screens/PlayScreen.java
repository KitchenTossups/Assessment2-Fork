package com.oshewo.panic.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.*;

import com.oshewo.panic.base.*;
import com.oshewo.panic.enums.*;
import com.oshewo.panic.stations.*;
import com.oshewo.panic.non_actor.*;
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
 */
public class PlayScreen extends BaseScreen {

    // sets up world and map for the game
    private final PiazzaPanic game;
    private final OrthographicCamera gameCam;
    private final Viewport gamePort;
    private final TiledMap map;
    private final OrthoCachedTiledMapRenderer renderer;
    private final Difficulty difficulty;

    // Hud
    public Hud hud;
    public static OrderHud orderHud;

    // Chef
    public final ChefActor[] chefs;
    private int chefSelector = 0;
    private int lastMove;

    private boolean tabPressed = false;

    // Order
    private final OrderSystem orderSystem;
    private int ordersCompleted = 0;

    private long timeUntilNextPowerUp;
    private PowerUpActor powerUp;
    private final Random random = new Random();

    /**
     * Instantiates a new Play screen.
     *
     * @param game the game
     */
    public PlayScreen(PiazzaPanic game) {
        super();
        this.difficulty = game.DIFFICULTY;

        this.game = game;

        // HUD
        this.hud = new Hud(0, 0, super.uiStage, this.game);
        this.orderHud = new OrderHud(0, 80, super.uiStage);

        // game, camera and map setup
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

        // order
        this.orderSystem = new OrderSystem(this.game);
        foodActors.add(new FoodActor(500, 250, super.uiStage, new Food(Ingredients.PATTY, IngredientState.UNCUT_UNCOOKED), this, this.game, -1));
//        timers.add(new Timer(500, 300, 40, 10, super.uiStage, 15));
//        timers.add(new Timer(700, 300, 40, 10, super.uiStage, 20));
//        int time = 10;
//        for (Station s : stoves) {
//            timers.add(new StationTimer(s.getBounds().getX() + (s.getBounds().getWidth() - 40) / 2, s.getBounds().getY() + s.getBounds().getHeight() + 5, 40, 10, super.uiStage, time));
//            time += 5;
//        }
//        for (Station s : choppingBoards) {
//            timers.add(new StationTimer(s.getBounds().getX() + (s.getBounds().getWidth() - 40) / 2, s.getBounds().getY() + s.getBounds().getHeight() + 5, 40, 10, super.uiStage, time));
//            time += 5;
//        }
        this.timeUntilNextPowerUp = new Date().getTime() + (this.random.nextInt(30) + 45) * 1000;
    }

    /**
     * Updates positions of chefs, timer hud, food and stations
     */
    public void update(float dt) {
        this.gameCam.update();
        this.renderer.setView(this.gameCam);
        this.renderer.render();

        // updates according to user input
        handleInput();

        for (FoodActor foodActor : new ArrayList<>(foodActors))
            foodActor.update(this);
        for (Station stove : stoves)
            stove.update(this);
        for (Station choppingBoard : choppingBoards)
            choppingBoard.update(this);
        for (Station servingStation : servingStations)
            servingStation.update(this);
        for (StationTimer timer : new ArrayList<>(timers)) {
            float percent = timer.getProgressPercent();
            if (percent >= 1) {
                foodActors.add(new FoodActor(timer.getHeldFoodX(), timer.getHeldFoodY(), super.uiStage, timer.getHeldFood(), this, this.game, -1));
                timers.remove(timer);
                timer.delete();
            } else
                timer.setInnerWidth(36 * percent);
        }

        this.orderSystem.update();

        this.hud.update();

        if (this.powerUp == null) {
            if (this.timeUntilNextPowerUp < new Date().getTime()) {
                this.powerUp = new PowerUpActor(300, 300, super.uiStage, this, PowerUps.getRandomPowerUp());
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
                    timeUntilNextPowerUp = new Date().getTime() + (random.nextInt(30) + 45) * 1000;
                }
            });
        }
    }

    /**
     * Handle input of picking up food that is nearest
     */
    public void handleInput() {
//        Food nearestFood = this.chefs[this.chefSelector].nearestFood(game.PICKUP_RADIUS);
        // pickup item
//        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
//            if (nearestFood != null) {
//                nearestFood.onUse();
//            } else {
//                for (FoodCrate crate : crateArray) {
//                    crate.onUse(this);
//                }
//            }
//        }

        try {
            if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
                float oldY = this.chefs[this.chefSelector].getY();
                this.chefs[this.chefSelector].setY(this.chefs[this.chefSelector].getY() + 300 * Gdx.graphics.getDeltaTime());
                this.checkCollision(this.chefs[this.chefSelector].getX(), oldY);
                this.lastMove = Input.Keys.W;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
                float oldY = this.chefs[this.chefSelector].getY();
                this.chefs[this.chefSelector].setY(this.chefs[this.chefSelector].getY() - 300 * Gdx.graphics.getDeltaTime());
                this.checkCollision(this.chefs[this.chefSelector].getX(), oldY);
                this.lastMove = Input.Keys.S;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
                float oldX = this.chefs[this.chefSelector].getX();
                this.chefs[this.chefSelector].setX(this.chefs[this.chefSelector].getX() + 300 * Gdx.graphics.getDeltaTime());
                this.checkCollision(oldX, this.chefs[this.chefSelector].getY());
                this.lastMove = Input.Keys.D;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
                float oldX = this.chefs[this.chefSelector].getX();
                this.chefs[this.chefSelector].setX(this.chefs[this.chefSelector].getX() - 300 * Gdx.graphics.getDeltaTime());
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
                if (nearestFoodActor != null) {
                    if (this.game.VERBOSE)
                        System.out.println(1);
                    nearestFoodActor.onUse();
                } else {
                    if (this.game.VERBOSE)
                        System.out.println(2);
                    for (FoodCrate crate : foodCrates) {
                        if (this.game.VERBOSE)
                            System.out.println(crate.toString());
                        crate.onUse(this, super.uiStage);
                    }
                }
            }
//                this.stationProximity();
//            if (this.customers.size() == 0) {
//                System.out.println("FINISHED");
//                System.out.println("This game lasted " + (new Date().getTime() - startTime) / 1000 + " seconds");
//                dispose();
//                this.game.setActiveScreen(new EndScreen(this.width, this.height, getBinnedItems(), this.game.labelStyle, startTime));
//            }
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
                case LETTUCE:
                    InitialiseFoodObject(mapLayer, Ingredients.LETTUCE);
                    break;
                case TOMATO:
                    InitialiseFoodObject(mapLayer, Ingredients.TOMATO);
                    break;
                case ONION:
                    InitialiseFoodObject(mapLayer, Ingredients.ONION);
                    break;
                case PATTY:
                    InitialiseFoodObject(mapLayer, Ingredients.PATTY);
                    break;
                case BUNS:
                    InitialiseFoodObject(mapLayer, Ingredients.BUN);
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

    private void InitialiseFoodObject(MapLayer mapLayer, Ingredients ingredients) {
        for (RectangleMapObject object : mapLayer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();
            foodCrates.add(new FoodCrate(rectangle, ingredients, this, this.game));
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

    /**
     * Resizes screen accordingly
     *
     * @param width  width
     * @param height height
     */
    public void resizing(int width, int height) {
        this.gamePort.update(width, height);
    }

    /**
     * Disposes of resources in screen
     */
    public void disposing() {
        this.map.dispose();
        this.renderer.dispose();
        this.mainStage.dispose();
        this.uiStage.dispose();
    }
}
