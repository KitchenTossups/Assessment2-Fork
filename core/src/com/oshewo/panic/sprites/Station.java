package com.oshewo.panic.sprites;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

import static com.oshewo.panic.sprites.Food.foodArray;
import static com.oshewo.panic.sprites.CountdownTimer.timerArray;

public class Station{
    private String type;
    private Rectangle bounds;
    private int id;
    private int foodId = -1;
    private CountdownTimer timer;


    public Station(String type,int id,  Rectangle bounds){
        this.type = type;
        this.id = id;
        this.bounds = bounds;
    }

    public void update(){
        if(foodId < 0){
            checkForFood();
        } else if(timer.isComplete()){
            foodId = -1;
            timerArray.remove(timer);
        } else{
            showProgress();
        }
    }

    public void checkForFood(){
        for(Food food: new ArrayList<>(foodArray)){
            if(bounds.contains(food.getX(),food.getY()) && !food.followingChef){
                foodId = food.getId();
                foodArray.remove(food);
                timer = new CountdownTimer(15,bounds);
            }
        }

    }
    public void showProgress() {
        float progress = timer.getProgressPercent();
        float x = this.bounds.x;
        float y = this.bounds.y + this.bounds.height;
        float width = 32;
        float height = 5;
    }
}
