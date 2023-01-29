package com.oshewo.panic.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

public class CountdownTimer extends Sprite {
    private long startTime;
    private int seconds;
    private boolean isCounting;
    public static ArrayList<CountdownTimer> timerArray = new ArrayList<CountdownTimer>();

    public CountdownTimer(int seconds, Rectangle rectangle){
        super(new Texture("progressRed.png"));
        this.seconds = seconds;
        this.isCounting = true;
        this.startTime = TimeUtils.millis();
        timerArray.add(this);
        this.setX(rectangle.x+(rectangle.getWidth()-18)/2);
        this.setY(rectangle.y+rectangle.getHeight());
    }

    public void update(){
        if(isCounting) {
            long currentTime = TimeUtils.millis();
            if (TimeUtils.timeSinceMillis(startTime) >= (seconds * 1000)) {
                isCounting = false;
            }else{

            }
        }else{
            timerArray.remove(this);
            isComplete();
        }
    }

    public boolean isComplete(){
        return !isCounting;
    }

    public float getProgressPercent(){
        float prog = (float)TimeUtils.timeSinceMillis(startTime)/(seconds*1000);
        return prog;
    }
}
