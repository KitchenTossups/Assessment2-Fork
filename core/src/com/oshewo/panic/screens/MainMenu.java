package com.oshewo.panic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.oshewo.panic.PiazzaPanic;

import static com.oshewo.panic.PiazzaPanic.V_ZOOM;

public class MainMenu implements Screen {

    private PiazzaPanic game;
    private OrthographicCamera camera;
    private FitViewport viewport;

    private Stage stage;

    private TextureAtlas atlas;
    private Skin skin;
    private Table table;

    private TextButton buttonPlay, buttonExit;
    private Texture background;
    private BitmapFont font;

    private static final int buttonWidth = 125;
    private static final int buttonHeight = 50;

    public MainMenu (final PiazzaPanic game) {
        this.game = game;

        this.camera = new OrthographicCamera(PiazzaPanic.V_WIDTH*V_ZOOM,PiazzaPanic.V_HEIGHT*V_ZOOM);
        this.viewport = new FitViewport(PiazzaPanic.V_WIDTH*V_ZOOM,PiazzaPanic.V_HEIGHT*V_ZOOM, camera);

    }

    public void handleInput() {

    }

    public void update(float dt) {
    }

    @Override
    public void show() {
        // set stage for actors
        stage = new Stage(viewport);

        // set background
        background = new Texture(Gdx.files.internal("piazza_panic_main_menu_bckgrnd.png"));

        // import buttons
        atlas = new TextureAtlas(Gdx.files.internal("buttons.pack"));
        skin = new Skin(atlas);

        // create table
        table = new Table(skin);
        table.setBounds(-30,-150, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(table);

        // create Play button
        font = new BitmapFont();

        TextButtonStyle button = new TextButtonStyle();
        button.font = font;
        button.up = skin.getDrawable("play_button_inactive");
        button.down = skin.getDrawable("play_button_active");

        buttonPlay = new TextButton("", button);
        table.add(buttonPlay).center().size(buttonWidth,buttonHeight).pad(10);
        table.row();

        // button event handler for Play button
        // start game when button clicked
        Gdx.input.setInputProcessor(stage);

        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayScreen(game));
            }
        });
        // create Exit button
        font = new BitmapFont();

        TextButtonStyle button2 = new TextButtonStyle();
        button2.font = font;
        button2.up = skin.getDrawable("exit_button_inactive");
        button2.down = skin.getDrawable("exit_button_active");

        buttonExit = new TextButton("", button2);
        table.add(buttonExit).center().size(buttonWidth,buttonHeight).pad(10);

        // button event handler for Exit button
        // exit when button clicked

        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, PiazzaPanic.V_WIDTH*V_ZOOM,PiazzaPanic.V_HEIGHT*V_ZOOM);
        stage.getBatch().end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
    }
}
