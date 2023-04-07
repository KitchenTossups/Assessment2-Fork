package com.oshewo.panic.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.oshewo.panic.*;
import com.oshewo.panic.base.*;

public class SettingsScreen extends BaseScreen {

    private static final int buttonWidth = 125;
    private static final int buttonHeight = 50;
    private final Label modeLabel;

    public SettingsScreen(PiazzaPanic game) {
        BaseActor background = new BaseActor(0, 0, this.mainStage);
        background.loadTexture("piazza_panic_main_menu_background.png");
        background.setSize(game.V_WIDTH, game.V_HEIGHT);

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("buttons.pack"));
        Skin skin = new Skin(atlas);

        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Minecraftia-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameters.size = 20;
        fontParameters.color = Color.BLACK;
        fontParameters.borderColor = Color.BLACK;
        fontParameters.borderStraight = true;
        fontParameters.minFilter = Texture.TextureFilter.Linear;
        fontParameters.magFilter = Texture.TextureFilter.Linear;

        BitmapFont bitmap = fontGenerator.generateFont(fontParameters);

        bitmap.getData().setScale(1, 1f);

        Label.LabelStyle style = new Label.LabelStyle(bitmap, null);

        this.modeLabel = new Label(String.format("The current game mode is: %s\nPress the button below to change it;", game.MODE), style);
        this.modeLabel.setAlignment(Align.center);

        TextButton.TextButtonStyle button = new TextButton.TextButtonStyle();
        button.font = game.labelStyle[0].font;
        button.up = skin.getDrawable("exit_button_inactive");
        button.down = skin.getDrawable("exit_button_active");

        TextButton buttonExit = new TextButton("", button);

        this.uiTable.pad(50);
        this.uiTable.row().height(300);
        this.uiTable.add(new Actor()); // Program will add a space if there is something here, even if it's empty
        this.uiTable.row().height(100);
        this.uiTable.add(this.modeLabel).center().expandX().pad(10);
        this.uiTable.row().height(100);
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
