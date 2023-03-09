package com.oshewo.panic.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.*;
import com.oshewo.panic.*;

public class SettingsScreen implements Screen {

    private final PiazzaPanic game;
    private final FitViewport viewport;
    private Stage stage;
    private Texture background;
    private static final int buttonWidth = 125;
    private static final int buttonHeight = 50;

    public SettingsScreen(PiazzaPanic game) {
        this.game = game;

        OrthographicCamera camera = new OrthographicCamera(game.V_WIDTH, game.V_HEIGHT);
        this.viewport = new FitViewport(game.V_WIDTH, game.V_HEIGHT, camera);
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

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("buttons.pack"));
        Skin skin = new Skin(atlas);

        Table table = new Table(skin);
        table.setBounds(0, -150, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(table);

        BitmapFont font = new BitmapFont();

        TextButton.TextButtonStyle button = new TextButton.TextButtonStyle();
        button.font = font;
        button.up = skin.getDrawable("exit_button_inactive");
        button.down = skin.getDrawable("exit_button_active");

        TextButton buttonExit = new TextButton("", button);
        table.add(buttonExit).center().size(buttonWidth, buttonHeight).pad(10);

        // button event handler for Exit button
        // exit game when button clicked
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenu(game));
            }
        });

        Gdx.input.setInputProcessor(stage);
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
        stage.getBatch().draw(background, 0, 0, game.V_WIDTH/* * V_ZOOM*/, game.V_HEIGHT/* * V_ZOOM*/);
        stage.getBatch().end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
