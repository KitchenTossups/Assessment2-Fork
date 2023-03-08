package com.oshewo.panic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.oshewo.panic.PiazzaPanic;

/**
 * The game starts on the main menu which allows the player to either start playing the game or exit
 *
 * @author sl3416
 */
public class MainMenu implements Screen {
    // setup
    private final PiazzaPanic game;
    private final FitViewport viewport;
    private Stage stage;

    private Texture background;
    private static final int buttonWidth = 125;
    private static final int buttonHeight = 50;

    /**
     * Instantiates a new Main menu.
     *
     * @param game the game
     */
    public MainMenu(final PiazzaPanic game) {
        this.game = game;

        OrthographicCamera camera = new OrthographicCamera(PiazzaPanic.V_WIDTH, PiazzaPanic.V_HEIGHT);
        this.viewport = new FitViewport(PiazzaPanic.V_WIDTH, PiazzaPanic.V_HEIGHT, camera);
    }

    /**
     * When the screen switches to the main menu screen, it sets up the background and the start and exit buttons.
     * The start button brings the player to the PlayScreen screen to play the game
     * The exit button exits the game
     */
    @Override
    public void show() {
        // set stage for actors
        stage = new Stage(viewport);

        // set background of main menu screen
        background = new Texture(Gdx.files.internal("piazza_panic_main_menu_background.png"));

        // import buttons
        // buttons setup
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("buttons.pack"));
        Skin skin = new Skin(atlas);

        // create table
        Table table = new Table(skin);
        table.setBounds(0, -150, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(table);

        // create Play button
        BitmapFont font = new BitmapFont();

        TextButtonStyle button = new TextButtonStyle();
        button.font = font;
        button.up = skin.getDrawable("play_button_inactive");
        button.down = skin.getDrawable("play_button_active");

        TextButton buttonPlay = new TextButton("", button);
        table.add(buttonPlay).center().size(buttonWidth, buttonHeight).pad(10);
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

        TextButtonStyle button3 = new TextButtonStyle();
        button3.font = font;
        button3.up = skin.getDrawable("settings_button_inactive");
        button3.down = skin.getDrawable("settings_button_active");

        TextButton buttonSetting = new TextButton("", button3);
        table.add(buttonSetting).center().size(buttonWidth, buttonHeight).pad(10);
        table.row();

        buttonSetting.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingsScreen(game));
            }
        });

        // create Exit button
        TextButtonStyle button2 = new TextButtonStyle();
        button2.font = font;
        button2.up = skin.getDrawable("exit_button_inactive");
        button2.down = skin.getDrawable("exit_button_active");

        TextButton buttonExit = new TextButton("", button2);
        table.add(buttonExit).center().size(buttonWidth, buttonHeight).pad(10);

        // button event handler for Exit button
        // exit game when button clicked
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    /**
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        // sets background of game to black and clears screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // draws background and stage
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, PiazzaPanic.V_WIDTH/* * V_ZOOM*/, PiazzaPanic.V_HEIGHT/* * V_ZOOM*/);
        stage.getBatch().end();

        stage.act(delta);
        stage.draw();
    }

    /**
     * Resizes stage so it maintains aspect ratio.
     *
     * @param width  width
     * @param height height
     */
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

    /**
     * Disposes stage
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}
