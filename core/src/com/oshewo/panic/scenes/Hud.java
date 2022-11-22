package com.oshewo.panic.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oshewo.panic.PiazzaPanic;

public class Hud {
    public Stage stage;
    private Viewport viewport;

    private int worldTimer;
    private float timeCount;
    private int score;


    // HUD labels will be split into static labels and dynamic labels that will be combined into the hud in a way such as STATIC : DYNAMIC or Time: (S) 12 (D)
    // Static labels (e.g. "Time:")
    Label timeLabel;
    Label scoreLabel;
    // Dynamic labels (e.g. "9" -> "10" etc)
    Label countdownLabel;
    Label pointsLabel;

    public Hud(SpriteBatch sb){
        worldTimer = 0;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(PiazzaPanic.V_WIDTH, PiazzaPanic.V_WIDTH, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%02d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        pointsLabel = new Label(String.format("%03d", score), new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.YELLOW));
        scoreLabel = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.YELLOW));

        table.add(scoreLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(pointsLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }

}
