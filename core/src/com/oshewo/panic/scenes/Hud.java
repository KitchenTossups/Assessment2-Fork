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
import com.oshewo.panic.non_actor.Customer;

import static com.oshewo.panic.lists.Lists.customers;

/**
 * The Hud displays the time and score counter
 *
 * @author Oshewo
 */
public class Hud extends BaseActor {

    private final PiazzaPanic game;

    // Label values that need to be accessed in the update
    private int lives;
    private final long startTime;

    // HUD labels that need to be edited in update
    private final Label countupLabel, livesLabel, orderLabel, orderTimeLabel;

    /**
     * Instantiates a new Hud which contains time and score
     *
     * @param game Piazza Panic
     */
    public Hud(float x, float y, Stage s, PiazzaPanic game) {
        super(x, y, s);
        this.game = game;
        this.lives = 3;
        this.startTime = TimeUtils.millis();

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

        this.countupLabel = new Label(String.format("%03d", this.game.worldTimer), style);
        this.livesLabel = new Label(String.format("%01d", this.lives), style);
        Label timeLabel = new Label("TIME", style);
        this.orderLabel = new Label("OLDEST ORDER DURATION", style);
        this.orderTimeLabel = new Label(String.format("%02d", 0), style);
        this.orderLabel.setVisible(false);
        this.orderTimeLabel.setVisible(false);
        Label scoreLabel = new Label("LIVES", style);

        table.add(scoreLabel).expandX().padTop(10);
        table.add(this.orderLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row().pad(10);
        table.add(this.livesLabel).expandX();
        table.add(this.orderTimeLabel).expandX();
        table.add(this.countupLabel).expandX();

        s.addActor(table);
    }

    /**
     * Updates time and score as the game progresses
     */
    public void update() {
        if (customers.size() > 0) {
            this.orderLabel.setVisible(true);
            this.orderTimeLabel.setVisible(true);
            long oldest = Long.MAX_VALUE;
            for (Customer customer : customers) {
                oldest = Math.min(oldest, customer.getOrderPlaced());
            }
            this.orderTimeLabel.setText(String.format("%02d", TimeUtils.timeSinceMillis(oldest) / 1000));
            this.game.worldTimer = (int) TimeUtils.timeSinceMillis(this.startTime) / 1000;
            this.countupLabel.setText(String.format("%03d", this.game.worldTimer));
            this.livesLabel.setText(String.format("%01d", this.lives));
        } else {
            this.orderLabel.setVisible(false);
            this.orderTimeLabel.setVisible(false);
        }
    }
}
