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

public class EndScreen extends BaseScreen {

    private static final int buttonWidth = 125;
    private static final int buttonHeight = 50;
    private final Label finalOutcomeLabel, difficultyLabel, completedOrdersLabel, binnedItemsLabel;
    private final PiazzaPanic game;

    public EndScreen(PiazzaPanic game, boolean success, int completedOrders, int binnedItems) {
        this.game = game;
        BaseActor background = new BaseActor(0, 0, this.mainStage);
        background.loadTexture("piazza_panic_main_menu_background.png");
        background.setSize(game.V_WIDTH, game.V_HEIGHT);

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("buttons.pack"));
        Skin skin = new Skin(atlas);

        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Minecraftia-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameters1 = new FreeTypeFontGenerator.FreeTypeFontParameter(), fontParameters2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameters1.size = 20;
        fontParameters1.color = Color.WHITE;
        fontParameters1.borderColor = Color.BLACK;
        fontParameters1.borderStraight = true;
        fontParameters1.borderWidth = 2;
        fontParameters1.minFilter = Texture.TextureFilter.Linear;
        fontParameters1.magFilter = Texture.TextureFilter.Linear;

        fontParameters2.size = 28;
        fontParameters2.color = Color.WHITE;
        fontParameters2.borderColor = Color.BLACK;
        fontParameters2.borderStraight = true;
        fontParameters2.borderWidth = 3;
        fontParameters2.minFilter = Texture.TextureFilter.Linear;
        fontParameters2.magFilter = Texture.TextureFilter.Linear;

        BitmapFont bitmap1 = fontGenerator.generateFont(fontParameters1), bitmap2 = fontGenerator.generateFont(fontParameters2);

        bitmap1.getData().setScale(1f, 1f);

        bitmap2.getData().setScale(1f, 1f);

        Label.LabelStyle style1 = new Label.LabelStyle(bitmap1, null), style2 = new Label.LabelStyle(bitmap2, null);

        this.finalOutcomeLabel = new Label(String.format("You have %s!", success ? "SUCCEEDED" : "FAILED"), style2);
        this.finalOutcomeLabel.setAlignment(Align.center);

        this.difficultyLabel = new Label(String.format("Difficulty: " + game.DIFFICULTY), style1);
        this.difficultyLabel.setAlignment(Align.center);

        this.completedOrdersLabel = new Label(String.format("Completed orders: " + completedOrders), style1);
        this.completedOrdersLabel.setAlignment(Align.center);

        this.binnedItemsLabel = new Label(String.format("Binned items: " + binnedItems), style1);
        this.binnedItemsLabel.setAlignment(Align.center);

        TextButton.TextButtonStyle button = new TextButton.TextButtonStyle();
        button.font = game.labelStyle[0].font;
        button.up = skin.getDrawable("exit_button_inactive");
        button.down = skin.getDrawable("exit_button_active");

        TextButton buttonExit = new TextButton("", button);

        this.uiTable.pad(50);
        this.uiTable.row().height(300);
        this.uiTable.add(new Actor()); // Program will add a space if there is something here, even if it's empty
        this.uiTable.row().height(50);
        this.uiTable.add(this.finalOutcomeLabel).center().expandX().pad(10);
        this.uiTable.row().height(50);
        this.uiTable.add(this.difficultyLabel).center().expandX().pad(10);
        this.uiTable.row().height(50);
        this.uiTable.add(this.completedOrdersLabel).center().expandX().pad(10);
        this.uiTable.row().height(50);
        this.uiTable.add(this.binnedItemsLabel).center().expandX().pad(10);
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
        if (Gdx.input.isTouched()) {
            this.dispose();
            this.game.setActiveScreen(new MainMenu(this.game));
        }
    }

    public void resizing(int width, int height) {

    }

    public void disposing() {

    }
}
