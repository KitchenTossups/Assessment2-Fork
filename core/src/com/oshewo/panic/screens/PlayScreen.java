package com.oshewo.panic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
    private Chef activePlayer;
    private Chef player0;
    private Chef player1;



    public PlayScreen(PiazzaPanic game){
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
        player0 = new Chef(world, 0);
        player1 = new Chef(world, 1);
        activePlayer = player0;

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight()/2);

            body = world.createBody(bdef);

            shape.setAsBox(rectangle.getWidth()/2,rectangle.getHeight()/2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight()/2);

            body = world.createBody(bdef);

            shape.setAsBox(rectangle.getWidth()/2,rectangle.getHeight()/2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }


    public void handleInput(float dt){
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
            if(activePlayer == player0){
                activePlayer.b2body.setLinearVelocity(new Vector2(0,0));
                activePlayer = player1;
            }
            else{
                activePlayer.b2body.setLinearVelocity(new Vector2(0,0));
                activePlayer = player0;
            }
        }
        activePlayer.b2body.setLinearVelocity(new Vector2(x,y));
    }

    public void update(float dt){
        handleInput(dt);

        world.step(1/60f,6,2);

        gameCam.position.x = activePlayer.b2body.getPosition().x;
        gameCam.position.y = activePlayer.b2body.getPosition().y;

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

    }
}
