package com.oshewo.panic.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

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
    private final Box2DDebugRenderer b2dr;
    private final GameMode mode;
    private final Difficulty difficulty;

    // Hud
    private final Hud hud;
    public static OrderHud orderHud;

    // Chef
    public final ChefActor[] chefs;
    private int chefSelector = 0;
    private int lastMove;

    private boolean tabPressed = false;

    // Order
    private final OrderSystem orderSystem;
    private int ordersCompleted = 0;

    /**
     * Instantiates a new Play screen.
     *
     * @param game the game
     */
    public PlayScreen(PiazzaPanic game) {
        super();
        this.mode = game.MODE;
        this.difficulty = game.DIFFICULTY;
        // tools
//        TextureAtlas atlas = new TextureAtlas("sprites.txt");

        this.game = game;

        // HUD
        hud = new Hud(0, 0, uiStage, game);
        orderHud = new OrderHud(0, 80, uiStage);

        // game, camera and map setup
        gameCam = new OrthographicCamera(game.V_WIDTH, game.V_HEIGHT);
        gamePort = new FitViewport(game.V_WIDTH, game.V_HEIGHT, gameCam);
        TmxMapLoader mapLoader = new TmxMapLoader();
        map = mapLoader.load("piazza-map-big2.tmx");
        renderer = new OrthoCachedTiledMapRenderer(map);
        renderer.render();
        gameCam.position.set((game.V_WIDTH / 2), (game.V_HEIGHT / 2), 0); // 0,0 is apparently in the centre of the screen maybe...
        b2dr = new Box2DDebugRenderer();
        WorldCreator();

        if (game.MODE == GameMode.SCENARIO)
            chefs = new ChefActor[2];
        else
            chefs = new ChefActor[3];

        this.chefs[0] = new ChefActor(400, 200, super.uiStage, 0);
        this.chefs[1] = new ChefActor(450, 200, super.uiStage, 1);
        if (mode == GameMode.ENDLESS) this.chefs[2] = new ChefActor(500, 200, super.uiStage, 2);

        // order
        orderSystem = new OrderSystem(game);
        foods.add(new Food(500, 250, super.uiStage, "patty.png", new Ingredient(Ingredients.PATTY, IngredientState.UNCUT), this, game, -1));
//        timers.add(new Timer(500, 300, 40, 10, super.uiStage, 15));
//        timers.add(new Timer(700, 300, 40, 10, super.uiStage, 20));
        int time = 10;
        for (Station s : stoves) {
            timers.add(new StationTimer(s.getBounds().getX() + (s.getBounds().getWidth() - 40) / 2, s.getBounds().getY() + s.getBounds().getWidth() + 5, 40, 10, super.uiStage, time));
            time += 5;
        }
        for (Station s : choppingBoards) {
            timers.add(new StationTimer(s.getBounds().getX() + (s.getBounds().getWidth() - 40) / 2, s.getBounds().getY() + s.getBounds().getWidth() + 5, 40, 10, super.uiStage, time));
            time += 5;
        }
    }

//    Food gen;

//    /**
//     * Get texture atlas.
//     *
//     * @return the texture atlas
//     */
//    public TextureAtlas getAtlas() {
//        return atlas;
//    }

    /**
     * Updates positions of chefs, timer hud, food and stations
     */
    public void update(float dt) {
        gameCam.update();
        renderer.setView(gameCam);
        renderer.render();

//        gen.update(this);

        // updates according to user input
        handleInput();

//        for (Chef chef : this.chefs)
//            chef.update();
//        for (CountdownTimer timer : new ArrayList<>(timerArray))
//            timer.update();
        for (Food food : new ArrayList<>(foods))
            food.update(this);
        for (Station stove : stoves)
            stove.update(this);
        for (Station board : choppingBoards)
            board.update(this);
        for (Station servery : servingStations)
            servery.update(this);
        for (StationTimer timer : new ArrayList<>(timers)) {
            float percent = timer.getProgressPercent();
            if (percent >= 1) {
                timers.remove(timer);
                timer.delete();
            } else
                timer.setInnerWidth(36 * percent);
        }

        orderSystem.update();

        hud.update();
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
                lastMove = Input.Keys.W;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
                float oldY = this.chefs[this.chefSelector].getY();
                this.chefs[this.chefSelector].setY(this.chefs[this.chefSelector].getY() - 300 * Gdx.graphics.getDeltaTime());
                this.checkCollision(this.chefs[this.chefSelector].getX(), oldY);
                lastMove = Input.Keys.S;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
                float oldX = this.chefs[this.chefSelector].getX();
                this.chefs[this.chefSelector].setX(this.chefs[this.chefSelector].getX() + 300 * Gdx.graphics.getDeltaTime());
                this.checkCollision(oldX, this.chefs[this.chefSelector].getY());
                lastMove = Input.Keys.D;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
                float oldX = this.chefs[this.chefSelector].getX();
                this.chefs[this.chefSelector].setX(this.chefs[this.chefSelector].getX() - 300 * Gdx.graphics.getDeltaTime());
                this.checkCollision(oldX, this.chefs[this.chefSelector].getY());
                lastMove = Input.Keys.A;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.TAB) && !Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
                if (!this.tabPressed) {
                    if (game.VERBOSE) System.out.println("Tab pressed");
                    switch (this.mode) {
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
                if (game.VERBOSE)
                    System.out.println("E");
                Food nearestFood = this.chefs[this.chefSelector].nearestFood(48);
                if (game.VERBOSE)
                    System.out.println(nearestFood);
                if (nearestFood != null) {
                    if (game.VERBOSE)
                        System.out.println(1);
                    nearestFood.onUse();
                } else {
                    if (game.VERBOSE)
                        System.out.println(2);
                    for (FoodCrate crate : foodCrates) {
                        if (game.VERBOSE)
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

//        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//            lastMove = Input.Keys.A;
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//            lastMove = Input.Keys.S;
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//            lastMove = Input.Keys.W;
//        }
//        // swap Chefs
//
//        if (Gdx.input.isKeyPressed(Input.Keys.TAB) && !Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
//            if (!this.tabPressed) {
//                if (game.isVerbose()) System.out.println("Tab pressed");
//                switch (game.MODE) {
//                    case SCENARIO:
//                        this.chefSelector++;
//                        if (this.chefSelector == 2) this.chefSelector = 0;
//                        break;
//                    case ENDLESS:
//                        this.chefSelector++;
//                        if (this.chefSelector == 3) this.chefSelector = 0;
//                        break;
//                }
//            }
//            this.tabPressed = true;
//        } else
//            this.tabPressed = false;
    }

    private void checkCollision(float oldX, float oldY) {
        if (this.chefs[this.chefSelector].getX() + this.chefs[this.chefSelector].getWidth() > game.V_WIDTH || this.chefs[this.chefSelector].getX() < 0)
            this.chefs[this.chefSelector].setX(oldX);
        if (this.chefs[this.chefSelector].getY() + this.chefs[this.chefSelector].getHeight() > game.V_HEIGHT || this.chefs[this.chefSelector].getY() < 0)
            this.chefs[this.chefSelector].setY(oldY);
        for (BaseActor counter : walls) {
            if (counter.getBoundaryRectangle().overlaps(this.chefs[this.chefSelector].getBoundaryRectangle())) {
//                System.out.println(oldX + " " + oldY + " " + this.chefs[this.chefSelector].getWidth() + " " + this.chefs[this.chefSelector].getHeight());
//                System.out.println(counter.getX() + " " + counter.getY() + " " + counter.getWidth() + " " + counter.getHeight());
//                System.out.println(1);
                this.chefs[this.chefSelector].setX(oldX);
                this.chefs[this.chefSelector].setY(oldY);
            }
        }
        switch (this.chefSelector) {
            case 0:
                if (this.mode == GameMode.ENDLESS)
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
                if (this.mode == GameMode.ENDLESS)
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
            stoves.add(new Station(StationType.STOVE, rectangle, this, this.game, super.uiStage));
        }
    }

    private void InitialiseChoppingCounter(MapLayer mapLayer) {
        for (RectangleMapObject object : mapLayer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();
            choppingBoards.add(new Station(StationType.CHOPPING_BOARD, rectangle, this, this.game, super.uiStage));
        }
    }

    private void InitialiseServiceStation(MapLayer mapLayer) {
        for (RectangleMapObject object : mapLayer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();
            servingStations.add(new Station(StationType.SERVING, rectangle, this, this.game, super.uiStage));
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
        return lastMove;
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
        gamePort.update(width, height);
    }

    /**
     * Disposes of resources in screen
     */
    public void disposing() {
        map.dispose();
        renderer.dispose();
        b2dr.dispose();
        mainStage.dispose();
        uiStage.dispose();
    }
}
