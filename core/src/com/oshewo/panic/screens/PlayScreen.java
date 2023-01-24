package com.oshewo.panic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oshewo.panic.PiazzaPanic;
import com.oshewo.panic.scenes.Hud;
import com.oshewo.panic.sprites.Chef;
import com.oshewo.panic.sprites.Food;
import com.oshewo.panic.tools.B2WolrdCreator;

public class PlayScreen implements Screen {
    private  PiazzaPanic game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthoCachedTiledMapRenderer renderer;
    private World world;
    private Box2DDebugRenderer b2dr;
    public static Chef activePlayer;
    private Chef player0;
    private Chef player1;
    private TextureAtlas atlas;
    private Food testFood;



    public PlayScreen(PiazzaPanic game){
        atlas = new TextureAtlas("sprites.txt");

        this.game = game;
        hud = new Hud(game.batch);
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(PiazzaPanic.V_WIDTH,PiazzaPanic.V_HEIGHT, gameCam);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("piazza.tmx");
        renderer = new OrthoCachedTiledMapRenderer(map);
        gameCam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);
        world = new World(new Vector2(0,0),true);
        b2dr = new Box2DDebugRenderer();

        new B2WolrdCreator(world,map);

        player0 = new Chef(world, 0,this);
        player1 = new Chef(world, 1,this);

        testFood = new Food(new Texture("lettuce.png"), 0);
        activePlayer = player0;


    }

    public TextureAtlas getAtlas(){
        return atlas;
    }


    public void handleInput(float dt){

        // * * * * M O V E M E N T * * * * //

        // Cancel momentum then handle new inputs
        float x = 0;
        float y = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            x += 200f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            x -= 200f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            y -= 200f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            y += 200f;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.TAB)){
            // stop old chef then switch control of chef to the other
            if(activePlayer == player0){
                activePlayer.b2body.setLinearVelocity(new Vector2(0,0));
                activePlayer = player1;
            }
            else{
                activePlayer.b2body.setLinearVelocity(new Vector2(0,0));
                activePlayer = player0;
            }
        }

        // apply moves from all input keys
        activePlayer.b2body.setLinearVelocity(new Vector2(x,y));
    }

    public void update(float dt){
        handleInput(dt);

        world.step(1/60f,6,2);

        gameCam.position.x = activePlayer.b2body.getPosition().x;
        gameCam.position.y = activePlayer.b2body.getPosition().y;

        player0.update(dt);
        player1.update(dt);
        testFood.update(dt);

        gameCam.update();
        renderer.setView(gameCam);
    }

    @Override
    public void show() {

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
        testFood.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

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

    }
}
