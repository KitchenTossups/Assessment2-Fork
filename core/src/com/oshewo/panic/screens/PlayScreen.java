package com.oshewo.panic.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import com.badlogic.gdx.utils.viewport.*;

import com.oshewo.panic.base.*;
import com.oshewo.panic.enums.*;
import com.oshewo.panic.stations.*;
import com.oshewo.panic.tools.*;
import com.oshewo.panic.*;
import com.oshewo.panic.scenes.*;
import com.oshewo.panic.sprites.Food;
import com.oshewo.panic.sprites.Station;
import com.oshewo.panic.sprites.CountdownTimer;
import com.oshewo.panic.actor.Chef;

import java.util.*;

import static com.oshewo.panic.sprites.Food.foodArray;
import static com.oshewo.panic.sprites.CountdownTimer.timerArray;
import static com.oshewo.panic.tools.WorldCreator.*;

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
    private final TmxMapLoader mapLoader;
    private final OrthoCachedTiledMapRenderer renderer;
//    private final World world;
    private final Box2DDebugRenderer b2dr;
    private final GameMode mode;
    private final Difficulty difficulty;
//    private Background background;

    // tools
    private final TextureAtlas atlas;
//    public static SpriteBatch batch;

    // Hud
    private Hud hud;
    public static OrderHud orderHud;

    // Chef
    public final Chef[] chefs;
    private int chefSelector = 0;
    private boolean tabPressed = false;
//    public static Chef activePlayer;
//    private static Chef player0, player1, player2;

    // order
    private final OrderSystem orderSystem;
    //    public static Order currentOrder;
    public static int ordersCompleted = 0;

    /**
     * Instantiates a new Play screen.
     *
     * @param game the game
     */
    public PlayScreen(PiazzaPanic game) {
        super();
        this.mode = game.MODE;
        this.difficulty = game.DIFFICULTY;
        atlas = new TextureAtlas("sprites.txt");

        this.game = game;

        // HUD
        hud = new Hud(0, 0, uiStage, game);
        orderHud = new OrderHud(20, 80, uiStage);

        // game, camera and map setup
        gameCam = new OrthographicCamera(game.V_WIDTH, game.V_HEIGHT);
//        background = new Background();
//        gamePort = new FitViewport(PiazzaPanic.V_WIDTH, PiazzaPanic.V_HEIGHT, gameCam);
        gamePort = new FitViewport(game.V_WIDTH / (game.V_ZOOM), game.V_HEIGHT / (game.V_ZOOM), gameCam);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("piazza-map-big2.tmx");
        renderer = new OrthoCachedTiledMapRenderer(map);
        renderer.render();
        gameCam.position.set(gamePort.getWorldWidth() / (1.9f * game.V_ZOOM), gamePort.getWorldHeight() / (1.1f * game.V_ZOOM), 0);
//        gameCam.position.set(120, 300, 0);
//        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();
        new WorldCreator(map, this, super.uiStage, game);

        if (game.MODE == GameMode.SCENARIO)
            chefs = new Chef[2];
        else
            chefs = new Chef[3];

//        this.chefs[0] = new Chef(new Texture("ChefB1.png"), world, 200, 200);
//        this.chefs[0].setPosition(180, 180);
//        this.chefs[1] = new Chef(new Texture("ChefB2.png"), world, 240, 200);
//        if (mode == GameMode.ENDLESS) this.chefs[2] = new Chef(world, 2, this, 280, 200);
        this.chefs[0] = new Chef(400, 100, super.uiStage, 1);
        this.chefs[1] = new Chef(450, 100, super.uiStage, 2);
        if (mode == GameMode.ENDLESS) this.chefs[2] = new Chef(500, 100, super.uiStage, 3);

        // sets up and positions both chefs in the game
//        player0 = new Chef(world, 0, this);
//        player1 = new Chef(world, 1, this);
//        player2 = new Chef(world, 2, this);
//        this.chefs[0].getBDef().position.set(180, 180);
//        player2.getBDef().position.set(200, 160);
        // current chef is set to player 0 by default
//        activePlayer = player0;

        // order
        orderSystem = new OrderSystem(game);
        batch = new SpriteBatch();
//        Food gen = new Food(new Texture("ChefB1.png"), Item.PATTY, this, game);
//        gen.setPosition(200, 200);
    }

    /**
     * Get texture atlas.
     *
     * @return the texture atlas
     */
    public TextureAtlas getAtlas() {
        return atlas;
    }

    /**
     * Updates positions of chefs, timer hud, food and stations
     *
     */
    public void update(float dt) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        gameCam.update();
        renderer.setView(gameCam);
        renderer.render();

        // updates according to user input
        handleInput();

//        for (CountdownTimer timer : timerArray) {
//            game.batch.draw(new Texture("progressGrey.png"), timer.getX(), timer.getY(), 18, 4);
//            game.batch.draw(timer.getTexture(), timer.getX() + 1, timer.getY() + 1, 16 * timer.getProgressPercent(), 2);
//        }

//        world.step(1 / 60f, 6, 2);

//        for (Chef chef : this.chefs)
//            chef.update();
        for (CountdownTimer timer : new ArrayList<>(timerArray))
            timer.update();
        for (Food food : foodArray)
            food.update(this);
        for (Station stove : stoveArray)
            stove.update(this);
        for (Station board : boardArray)
            board.update(this);
        for (Station servery : servingArray)
            servery.update(this);

        orderSystem.update();

        hud.update();
    }

    public int lastMove;

    /**
     * Handle input of picking up food that is nearest
     */
    public void handleInput() {
//        Food nearestFood = this.chefs[this.chefSelector].nearestFood(game.PICKUP_RADIUS);
        // pickup item
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
//            if (nearestFood != null) {
//                nearestFood.onUse();
//            } else {
//                for (FoodCrate crate : crateArray) {
//                    crate.onUse(this);
//                }
//            }
        }

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
                    if (game.isVerbose()) System.out.println("Tab pressed");
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
//            if (Gdx.input.isKeyJustPressed(Input.Keys.E))
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
        System.out.println(oldX + " " +  oldY);
        if (this.chefs[this.chefSelector].getX() + this.chefs[this.chefSelector].getWidth() > game.V_WIDTH || this.chefs[this.chefSelector].getX() < 0)
            this.chefs[this.chefSelector].setX(oldX);
        if (this.chefs[this.chefSelector].getY() + this.chefs[this.chefSelector].getHeight() > game.V_HEIGHT || this.chefs[this.chefSelector].getY() < 0)
            this.chefs[this.chefSelector].setY(oldY);
        for (BaseActor counter : wallArray) {
            if (counter.getBoundaryRectangle().overlaps(this.chefs[this.chefSelector].getBoundaryRectangle())) {
                System.out.println(counter.getX() + " " + counter.getY());
                System.out.println(counter.getWidth() + " " + counter.getHeight());
                System.out.println(1);
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

    /**
     * Renders the play screen and all of its assets / objects
     *
     * @param dt The time in seconds since the last render.
     */
    @Override
    public void render(float dt) {
        dt = Math.min(dt, 1 / 30f);

        update(dt);

        super.uiStage.act(dt);
        super.mainStage.act(dt);

        // sets background of game to black and clears screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        renderer.render();

        super.mainStage.draw();
        super.uiStage.draw();

        //b2dr.render(world,gameCam.combined); //uncomment to see hitboxes

//        game.batch.setProjectionMatrix(gameCam.combined);
//        game.batch.begin();
//
//        // ensures the chef in front is drawn over the other
////        if (player0.b2body.getPosition().y >= player1.b2body.getPosition().y) {
////            player0.draw(game.batch);
////            player1.draw(game.batch);
////        } else {
////            player1.draw(game.batch);
////            player0.draw(game.batch);
////        }
//        for (Food food : foodArray) {
//            food.draw(game.batch);
//        }
//
//        // draws the timer
//        for (CountdownTimer timer : timerArray) {
//            game.batch.draw(new Texture("progressGrey.png"), timer.getX(), timer.getY(), 18, 4);
//            game.batch.draw(timer.getTexture(), timer.getX() + 1, timer.getY() + 1, 16 * timer.getProgressPercent(), 2);
//        }
//
//        game.batch.end();

        // draws the huds
//        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
//        game.batch.setProjectionMatrix(orderHud.stage.getCamera().combined);
//        hud.stage.draw();
//        orderHud.stage.draw();
    }

    /**
     * Get the selected chef number
     *
     * @return int chefSelector
     */
    public int getChefSelector() {
        return this.chefSelector;
    }

    /**
     * Resizes screen accordingly
     *
     * @param width width
     * @param height height
     */
    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    /**
     * Disposes of resources in screen
     */
    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
//        world.dispose();
        b2dr.dispose();
//        orderHud.dispose();
        mainStage.dispose();
        uiStage.dispose();
    }
}
