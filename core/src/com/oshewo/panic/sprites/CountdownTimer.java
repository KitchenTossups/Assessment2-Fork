package com.oshewo.panic.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

/**
 * Sets up countdown timer for how long the player takes to complete the game
 *
 * @author Oshewo
 */
public class CountdownTimer extends Sprite {
    private final long startTime;
    private final int seconds;
    private boolean isCounting;
    /**
     * The constant timerArray.
     */
    public static ArrayList<CountdownTimer> timerArray = new ArrayList<CountdownTimer>();

    /**
     * Instantiates a new Countdown timer.
     *
     * @param seconds   the seconds
     * @param rectangle the rectangle
     */
    public CountdownTimer(int seconds, Rectangle rectangle) {
        super(new Texture("progressRed.png"));
        this.seconds = seconds;
        this.isCounting = true;
        this.startTime = TimeUtils.millis();
        timerArray.add(this);
        this.setX(rectangle.x + (rectangle.getWidth() - 18) / 2);
        this.setY(rectangle.y + rectangle.getHeight());
    }

    /**
     * Updates whether the timer is running.
     */
    public void update() {
        if (isCounting) {
            long currentTime = TimeUtils.millis();
            if (TimeUtils.timeSinceMillis(startTime) >= (seconds * 1000L)) {
                isCounting = false;
            } else {

            }
        } else { // timer resets if isCounting is True
            timerArray.remove(this);
            isComplete();
        }
    }

    /**
     * Determines whether the timer should stop running or not
     *
     * @return the boolean
     */
    public boolean isComplete() {
        return !isCounting;
    }

    /**
     * Gets how long since the timer has started
     *
     * @return the float value for the time
     */
    public float getProgressPercent() {
        float prog = (float) TimeUtils.timeSinceMillis(startTime) / (seconds * 1000);
        return prog;
    }
}
