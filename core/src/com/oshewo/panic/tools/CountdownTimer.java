package com.oshewo.panic.tools;

import com.badlogic.gdx.utils.TimeUtils;

import java.util.HashSet;
import java.util.Set;

public class CountdownTimer {
    private long startTime;
    private int seconds;
    private boolean isCounting;
    public static Set<CountdownTimer> timerArray = new HashSet<>();

    public CountdownTimer(int seconds){
        this.seconds = seconds;
        this.isCounting = true;
        this.startTime = TimeUtils.millis();
        timerArray.add(this);
    }

    public void update(){
        if(isCounting) {
            long currentTime = TimeUtils.millis();
            if (TimeUtils.timeSinceMillis(startTime) >= (seconds * 1000)) {
                isCounting = false;
            }
        }else{isComplete();}
    }

    public boolean isComplete(){
        timerArray.remove(this);
        return !isCounting;
    }
}
