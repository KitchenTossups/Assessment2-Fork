package com.oshewo.panic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.oshewo.panic.PiazzaPanic;
import com.oshewo.panic.base.BaseActor;
import com.oshewo.panic.base.BaseScreen;
import com.oshewo.panic.enums.Difficulty;
import com.oshewo.panic.enums.GameMode;

public class PauseScreen extends BaseScreen {

    private static final int buttonWidth = 125;
    private static final int buttonHeight = 50;
    private final Label modeLabel, difficultyLabel;

    public PauseScreen(PiazzaPanic game) {
        BaseActor background = new BaseActor(0, 0, this.mainStage);
        background.loadTexture("piazza_panic_main_menu_background.png");
        background.setSize(game.V_WIDTH, game.V_HEIGHT);

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("buttons.pack"));
        Skin skin = new Skin(atlas);

        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Minecraftia-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameters.size = 20;
        fontParameters.color = Color.WHITE;
        fontParameters.borderColor = Color.BLACK;
        fontParameters.borderStraight = true;
        fontParameters.borderWidth = 2;
        fontParameters.minFilter = Texture.TextureFilter.Linear;
        fontParameters.magFilter = Texture.TextureFilter.Linear;

        BitmapFont bitmap = fontGenerator.generateFont(fontParameters);

        bitmap.getData().setScale(1, 1f);

        Label.LabelStyle style = new Label.LabelStyle(bitmap, null);

        this.modeLabel = new Label(String.format("The current game mode is: %s\nPress the button below to change it;", game.MODE), style);
        this.modeLabel.setAlignment(Align.center);

        Label modeChangeLabel = new Label("CHANGE GAME MODE", style);
        modeChangeLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.MODE == GameMode.SCENARIO)
                    game.MODE = GameMode.ENDLESS;
                else
                    game.MODE = GameMode.SCENARIO;
                modeLabel.setText(String.format("The current game mode is: %s\nPress the button below to change it;", game.MODE));
            }
        });

        this.difficultyLabel = new Label(String.format("The current game difficulty is: %s\nPress the button below to change it;", game.DIFFICULTY), style);
        this.difficultyLabel.setAlignment(Align.center);

        Label difficultyChangeLabel = new Label("CHANGE GAME DIFFICULTY", style);
        difficultyChangeLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (game.DIFFICULTY == Difficulty.EASY)
                    game.DIFFICULTY = Difficulty.MEDIUM;
                else if (game.DIFFICULTY == Difficulty.MEDIUM)
                    game.DIFFICULTY = Difficulty.HARD;
                else
                    game.DIFFICULTY = Difficulty.EASY;
                difficultyLabel.setText(String.format("The current game difficulty is: %s\nPress the button below to change it;", game.DIFFICULTY));
            }
        });

//        Label difficultyChangeLabel = new Label("CHANGE GAME DIFFICULTY", style);
//        difficultyChangeLabel.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                if (game.DIFFICULTY == Difficulty.EASY)
//                    game.DIFFICULTY = Difficulty.MEDIUM;
//                else if (game.DIFFICULTY == Difficulty.MEDIUM)
//                    game.DIFFICULTY = Difficulty.HARD;
//                else
//                    game.DIFFICULTY = Difficulty.EASY;
//                difficultyLabel.setText(String.format("The current game difficulty is: %s\nPress the button below to change it;", game.DIFFICULTY));
//            }
//        });

        TextButton.TextButtonStyle button = new TextButton.TextButtonStyle();
        button.font = game.labelStyle[0].font;
        button.up = skin.getDrawable("exit_button_inactive");
        button.down = skin.getDrawable("exit_button_active");

        TextButton buttonExit = new TextButton("", button);

        this.uiTable.pad(50);
        this.uiTable.row().height(300);
        this.uiTable.add(new Actor()); // Program will add a space if there is something here, even if it's empty
        this.uiTable.row().height(50);
        this.uiTable.add(this.modeLabel).center().expandX().pad(10);
        this.uiTable.row().height(50);
        this.uiTable.add(modeChangeLabel).center().expandX().pad(10);
        this.uiTable.row().height(50);
        this.uiTable.add(this.difficultyLabel).center().expandX().pad(10);
        this.uiTable.row().height(50);
        this.uiTable.add(difficultyChangeLabel).center().expandX().pad(10);
        this.uiTable.row().height(50);
//        this.uiTable.add(returnLabel).center().expandX().pad(10);
        this.uiTable.row().height(50);
        this.uiTable.add(buttonExit).center().size(buttonWidth, buttonHeight).pad(10);

        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dispose();
                game.setActiveScreen(new MainMenu(game));
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
