package com.oshewo.panic.actor;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import com.oshewo.panic.base.BaseActor;

import java.util.Date;

public class StationTimer extends BaseActor {

    private final long timeStarted;
    private final TimerSub timerSub;
    private final int duration;

    public StationTimer(float x, float y, int width, int height, Stage s, int seconds) {
        super(x, y, s);
        this.duration = seconds;
        this.timeStarted = new Date().getTime();
        this.timerSub = new TimerSub(x + 2, y + 2, width - 4, height - 4, s);
        this.loadTexture("progressGrey.png", width, height);
    }

    public float getProgressPercent() {
        return (float) TimeUtils.timeSinceMillis(this.timeStarted) / (this.duration * 1000);
    }

    public void delete() {
        this.timerSub.remove();
        this.remove();
    }

    public void setInnerWidth(float w) {
        this.timerSub.setWidth(w);
    }

    static class TimerSub extends BaseActor {
        public TimerSub(float x, float y, int width, int height, Stage s) {
            super(x, y, s);
            this.loadTexture("progressRed.png", width, height);
        }
    }
}
