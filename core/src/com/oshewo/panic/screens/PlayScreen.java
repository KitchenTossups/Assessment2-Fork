package com.oshewo.panic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.oshewo.panic.tools.Order;
import com.oshewo.panic.tools.OrderSystem;
import com.oshewo.panic.PiazzaPanic;
import com.oshewo.panic.scenes.Hud;
import com.oshewo.panic.scenes.OrderHud;
import com.oshewo.panic.sprites.Chef;
import com.oshewo.panic.sprites.Food;
import com.oshewo.panic.sprites.Station;
import com.oshewo.panic.sprites.CountdownTimer;
import com.oshewo.panic.tools.WolrdCreator;
import com.oshewo.panic.tools.InputHandler;

import java.util.ArrayList;

import static com.oshewo.panic.sprites.Food.foodArray;
import static com.oshewo.panic.PiazzaPanic.V_ZOOM;
import static com.oshewo.panic.sprites.CountdownTimer.timerArray;
import static com.oshewo.panic.tools.WolrdCreator.*;

public class PlayScreen implements Screen {
    private  PiazzaPanic game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;
    private OrderHud orderHud;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthoCachedTiledMapRenderer renderer;
    private World world;
    private Box2DDebugRenderer b2dr;
    public static Chef activePlayer;
    private static Chef player0;
    private static Chef player1;
    private TextureAtlas atlas;
    private OrderSystem orderSystem;
    private Order currentOrder;
    private BitmapFont font;
    public static SpriteBatch batch;
    private Timer timer;

    public PlayScreen(PiazzaPanic game){
        atlas = new TextureAtlas("sprites.txt");

        this.game = game;

        // HUD
        hud = new Hud(game.batch);
        orderHud = new OrderHud(game.batch);

        // game setup
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(PiazzaPanic.V_WIDTH*V_ZOOM,PiazzaPanic.V_HEIGHT*V_ZOOM, gameCam);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("piazza2.tmx");
        renderer = new OrthoCachedTiledMapRenderer(map);
        gameCam.position.set(gamePort.getWorldWidth()/(4*V_ZOOM),gamePort.getWorldHeight()/(2.2f*V_ZOOM),0);
        world = new World(new Vector2(0,0),true);
        b2dr = new Box2DDebugRenderer();


        new WolrdCreator(world,map);

        player0 = new Chef(world, 0,this);
        player1 = new Chef(world, 1,this);
        activePlayer = player0;

        // order
        orderSystem = new OrderSystem();
        font = new BitmapFont();
        batch = new SpriteBatch();

    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    public static void swapChef(){
        if(activePlayer == player0){
            activePlayer.b2body.setLinearVelocity(new Vector2(0,0));
            activePlayer = player1;
        }
        else{
            activePlayer.b2body.setLinearVelocity(new Vector2(0,0));
            activePlayer = player0;
        }
    }




    public void update(float dt){
        InputHandler.handleInput(dt);

        world.step(1/60f,6,2);


        player0.update(dt);
        player1.update(dt);

        for(CountdownTimer timer : new ArrayList<>(timerArray)){
            timer.update();
        }
        for(Food food : foodArray){
            food.update(dt);
        }
        for(Station stove : stoveArray){
            stove.update();
        }
        for(Station board : boardArray){
            board.update();
        }
        for(Station servery : servingArray){
            servery.update();
        }

        currentOrder = orderSystem.generateOrder();

        renderer.setView(gameCam);
    }

    @Override
    public void show() {
        timer = new Timer();
        final Label recipeLabel = orderHud.getRecipeLabel();
        final Label ingredient1Label = orderHud.getIngredient1Label();
        final Label ingredient2Label = orderHud.getIngredient2Label();
        final Label ingredient3Label = orderHud.getIngredient3Label();

        timer.scheduleTask(new Timer.Task() {

            @Override
            public void run() {
                recipeLabel.setText(currentOrder.getRecipeType().toString());;

                switch (currentOrder.getRecipeType()) {
                    case "Burger":
                        ingredient1Label.setText("bun");
                        ingredient2Label.setText("patty");
                        ingredient3Label.setText("bun");
                        break;
                    case "Salad":
                        ingredient1Label.setText("lettuce");
                        ingredient2Label.setText("tomato");
                        ingredient3Label.setText("onion");
                        break;
                }
            }
        }, 0);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        renderer.render();

        b2dr.render(world,gameCam.combined);

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();

        // ensures the chef in front is drawn over the other
        if(player0.b2body.getPosition().y >= player1.b2body.getPosition().y) {
            player0.draw(game.batch);
            player1.draw(game.batch);
        }
        else{
            player1.draw(game.batch);
            player0.draw(game.batch);
        }
        for (Food food : foodArray ) {
            food.draw(game.batch);
        }

        for (CountdownTimer timer : timerArray){
            game.batch.draw(new Texture("progressGrey.png"),timer.getX(),timer.getY(),18,4);
            game.batch.draw(timer.getTexture(),timer.getX()+1,timer.getY()+1,16*timer.getProgressPercent(),2);
        }

        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        game.batch.setProjectionMatrix(orderHud.stage.getCamera().combined);
        hud.stage.draw();
        orderHud.stage.draw();

    }


    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
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
