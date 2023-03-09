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

import com.oshewo.panic.enums.*;
import com.oshewo.panic.stations.*;
import com.oshewo.panic.tools.*;
import com.oshewo.panic.*;
import com.oshewo.panic.scenes.*;
import com.oshewo.panic.sprites.*;

import java.util.*;

import static com.oshewo.panic.sprites.Food.foodArray;
import static com.oshewo.panic.sprites.CountdownTimer.timerArray;
import static com.oshewo.panic.tools.WorldCreator.*;

/**
 * The Play screen is the screen featuring the actual game
 *
 * @author Oshewo, sl3416
 */
public class PlayScreen implements Screen {
    // sets up world and map for the game
    private final PiazzaPanic game;
    private final OrthographicCamera gameCam;
    private final Viewport gamePort;
    private final TiledMap map;
    private final TmxMapLoader mapLoader;
    private final OrthoCachedTiledMapRenderer renderer;
    private final World world;
    private final Box2DDebugRenderer b2dr;
    private final GameMode mode;
    private final Difficulty difficulty;

    // tools
    private final TextureAtlas atlas;
    public static SpriteBatch batch;

    // Hud
    private final Hud hud;
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
        this.mode = game.MODE;
        this.difficulty = game.DIFFICULTY;
        atlas = new TextureAtlas("sprites.txt");

        this.game = game;

        // HUD
        hud = new Hud(game);
        orderHud = new OrderHud(game);

        // game, camera and map setup
        gameCam = new OrthographicCamera(game.V_WIDTH, game.V_HEIGHT);
//        gamePort = new FitViewport(PiazzaPanic.V_WIDTH, PiazzaPanic.V_HEIGHT, gameCam);
        gamePort = new FitViewport(game.V_WIDTH / (game.V_ZOOM), game.V_HEIGHT / (game.V_ZOOM), gameCam);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("piazza-map-big.tmx");
        renderer = new OrthoCachedTiledMapRenderer(map);
        gameCam.position.set(gamePort.getWorldWidth() / (1.9f * game.V_ZOOM), gamePort.getWorldHeight() / (1.1f * game.V_ZOOM), 0);
//        gameCam.position.set(120, 300, 0);
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();
        new WorldCreator(world, map, this, game);

        if (game.MODE == GameMode.SCENARIO)
            chefs = new Chef[2];
        else
            chefs = new Chef[3];

        this.chefs[0] = new Chef(world, 0, this, 120, 160);
        this.chefs[1] = new Chef(world, 1, this, 160, 160);
        if (mode == GameMode.ENDLESS) this.chefs[2] = new Chef(world, 2, this, 200, 160);

        // sets up and positions both chefs in the game
//        player0 = new Chef(world, 0, this);
//        player1 = new Chef(world, 1, this);
//        player2 = new Chef(world, 2, this);
//        player1.getBDef().position.set(160, 160);
//        player2.getBDef().position.set(200, 160);
        // current chef is set to player 0 by default
//        activePlayer = player0;

        // order
        orderSystem = new OrderSystem(game);
        batch = new SpriteBatch();
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
    public void update() {
        // updates according to user input
        handleInput();

        world.step(1 / 60f, 6, 2);

        for (Chef chef : this.chefs)
            chef.update();
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
        renderer.setView(gameCam);
    }

    public int lastMove;

    /**
     * Handle input of picking up food that is nearest
     */
    public void handleInput() {
        Food nearestFood = this.chefs[this.chefSelector].nearestFood(game.PICKUP_RADIUS);
        // pickup item
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (nearestFood != null) {
                nearestFood.onUse();
            } else {
                for (FoodCrate crate : crateArray) {
                    crate.onUse(this);
                }
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            lastMove = Input.Keys.D;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            lastMove = Input.Keys.A;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            lastMove = Input.Keys.S;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            lastMove = Input.Keys.W;
        }
        // swap Chefs

        if (Gdx.input.isKeyPressed(Input.Keys.TAB) && !Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
            if (!this.tabPressed) {
                if (game.isVerbose()) System.out.println("Tab pressed");
                switch (game.MODE) {
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
    }

    @Override
    public void show() {

    }

    /**
     * Renders the play screen and all of its assets / objects
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        update();

        // sets background of game to black and clears screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        renderer.render();

        //b2dr.render(world,gameCam.combined); //uncomment to see hitboxes

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();

        // ensures the chef in front is drawn over the other
//        if (player0.b2body.getPosition().y >= player1.b2body.getPosition().y) {
//            player0.draw(game.batch);
//            player1.draw(game.batch);
//        } else {
//            player1.draw(game.batch);
//            player0.draw(game.batch);
//        }
        for (Food food : foodArray) {
            food.draw(game.batch);
        }

        // draws the timer
        for (CountdownTimer timer : timerArray) {
            game.batch.draw(new Texture("progressGrey.png"), timer.getX(), timer.getY(), 18, 4);
            game.batch.draw(timer.getTexture(), timer.getX() + 1, timer.getY() + 1, 16 * timer.getProgressPercent(), 2);
        }

        game.batch.end();

        // draws the huds
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        game.batch.setProjectionMatrix(orderHud.stage.getCamera().combined);
        hud.stage.draw();
        orderHud.stage.draw();
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

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    /**
     * Disposes of resources in screen
     */
    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
        orderHud.dispose();
    }
}
