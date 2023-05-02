package com.oshewo.panic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.oshewo.panic.PiazzaPanic;
import com.oshewo.panic.base.*;

/**
 * The game starts on the main menu which allows the player to either start playing the game or exit
 *
 * @author sl3416
 */
public class MainMenu extends BaseScreen {

    private static final int buttonWidth = 125;
    private static final int buttonHeight = 50;

    /**
     * Instantiates a new Main menu.
     *
     * @param game the game
     */
    public MainMenu(final PiazzaPanic game) {
        BaseActor background = new BaseActor(0, 0, this.mainStage);
        background.loadTexture("piazza_panic_main_menu_background.png");
        background.setSize(game.V_WIDTH, game.V_HEIGHT);

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("buttons.pack"));
        Skin skin = new Skin(atlas);

        TextButtonStyle button = new TextButtonStyle();
        button.font = game.labelStyle[1].font;
        button.up = skin.getDrawable("play_button_inactive");
        button.down = skin.getDrawable("play_button_active");

        TextButton buttonPlay = new TextButton("", button);

        this.uiTable.pad(50);
        this.uiTable.row().height(300);
        this.uiTable.add(new Actor()); // Program will add a space if there is something here, even if it's empty
        this.uiTable.row().height(100);
        this.uiTable.add(buttonPlay).center().size(buttonWidth, buttonHeight).pad(10);

        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setScreen(new PlayScreen(game));
            }
        });

        TextButtonStyle button3 = new TextButtonStyle();
        button3.font = game.labelStyle[1].font;
        button3.up = skin.getDrawable("settings_button_inactive");
        button3.down = skin.getDrawable("settings_button_active");

        TextButton buttonSetting = new TextButton("", button3);

        this.uiTable.row().height(100);
        this.uiTable.add(buttonSetting).center().size(buttonWidth, buttonHeight).pad(10);

        buttonSetting.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setActiveScreen(new SettingsScreen(game));
            }
        });

        TextButtonStyle button2 = new TextButtonStyle();
        button2.font = game.labelStyle[1].font;
        button2.up = skin.getDrawable("exit_button_inactive");
        button2.down = skin.getDrawable("exit_button_active");

        TextButton buttonExit = new TextButton("", button2);

        this.uiTable.row().height(100);
        this.uiTable.add(buttonExit).center().size(buttonWidth, buttonHeight).pad(10);

        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.exit(0);
            }
        });
    }

    public void update(float dt) {

    }

    public void resizing(int width, int height) {

    }

    public void disposing() {

    }
}
