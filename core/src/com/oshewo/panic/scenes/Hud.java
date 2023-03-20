package com.oshewo.panic.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.oshewo.panic.PiazzaPanic;
import com.oshewo.panic.base.BaseActor;

/**
 * The Hud displays the time and score counter
 *
 * @author Oshewo
 */
public class Hud extends BaseActor {

    private final PiazzaPanic game;

//    private int worldTimer;
    public static int score;
    public static long hudStartTime;

    // HUD labels will be split into static labels and dynamic labels that will be combined into the hud in a way such as STATIC : DYNAMIC or Time: (S) 12 (D)
    // Static labels (e.g. "Time:")
    Label timeLabel;
    Label scoreLabel;
    // Dynamic labels (e.g. "9" -> "10" etc)
    Label countupLabel;
    Label livesLabel;

    /**
     * Instantiates a new Hud which contains time and score
     *
     * @param game Piazza Panic
     */
    public Hud(float x, float y, Stage s, PiazzaPanic game) {
        super(x, y, s);
        this.game = game;
        score = 3;
        hudStartTime = TimeUtils.millis();

        // score and time HUD
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("arcadeclassic.ttf"));
        FreeTypeFontParameter fontParameters = new FreeTypeFontParameter();
        fontParameters.size = 36;
        fontParameters.color = Color.WHITE;
        fontParameters.borderWidth = 2;
        fontParameters.borderColor = Color.BLACK;
        fontParameters.borderStraight = true;
        fontParameters.minFilter = Texture.TextureFilter.Linear;
        fontParameters.magFilter = Texture.TextureFilter.Linear;

        BitmapFont bitmap = fontGenerator.generateFont(fontParameters);

        Label.LabelStyle style = new Label.LabelStyle(bitmap, Color.WHITE);

        countupLabel = new Label(String.format("%03d", game.worldTimer), style);
        livesLabel = new Label(String.format("%01d", score), style);
        timeLabel = new Label("TIME", style);
        scoreLabel = new Label("LIVES", style);

        table.add(scoreLabel).expandX().padTop(10);
        table.add(new Actor()).expandX().padTop(10); // spacer
        table.add(timeLabel).expandX().padTop(10);
        table.row().pad(10);
        table.add(livesLabel).expandX();
        table.add(new Actor()).expandX().padTop(10); // spacer
        table.add(countupLabel).expandX();

        s.addActor(table);
    }

    /**
     * Updates time and score as the game progresses
     */
    public void update() {
        game.worldTimer = (int) TimeUtils.timeSinceMillis(hudStartTime) / 1000;
        countupLabel.setText(String.format("%03d", game.worldTimer));
        livesLabel.setText(String.format("%01d", score));
    }
}
