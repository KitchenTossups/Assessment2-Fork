package com.oshewo.panic.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oshewo.panic.PiazzaPanic;

/**
 * The Hud displays the time and score counter
 *
 * @author Oshewo
 */
public class Hud implements Disposable {
    public Stage stage;
    private final Viewport viewport;

    private int worldTimer;
    private final float timeCount;
    public static int score;
    public static long hudStartTime;

    // HUD labels will be split into static labels and dynamic labels that will be combined into the hud in a way such as STATIC : DYNAMIC or Time: (S) 12 (D)
    // Static labels (e.g. "Time:")
    Label timeLabel;
    Label scoreLabel;
    // Dynamic labels (e.g. "9" -> "10" etc)
    Label countdownLabel;
    Label pointsLabel;

    /**
     * Instantiates a new Hud which contains time and score
     *
     * @param sb the sb
     */
    public Hud(SpriteBatch sb) {
        worldTimer = 0;
        timeCount = 0;
        score = 3;
        hudStartTime = TimeUtils.millis();

        viewport = new FitViewport(PiazzaPanic.V_WIDTH, PiazzaPanic.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

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

        countdownLabel = new Label(String.format("%02d", worldTimer), style);
        pointsLabel = new Label(String.format("%03d", score), style);
        timeLabel = new Label("TIME", style);
        scoreLabel = new Label("SCORE", style);

        table.add(scoreLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row().pad(10);
        table.add(pointsLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }

    /**
     * Updates time and score as the game progresses
     */
    public void update() {
        worldTimer = (int) TimeUtils.timeSinceMillis(hudStartTime) / 1000;
        countdownLabel.setText(String.format("%03d", worldTimer));
        pointsLabel.setText(String.format("%03d", score));

    }

    /**
     * Disposes stage
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}
