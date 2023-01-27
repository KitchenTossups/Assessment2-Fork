package com.oshewo.panic.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.oshewo.panic.tools.CountdownTimer;

import static com.oshewo.panic.sprites.Food.foodArray;

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
        }
    }

    public void checkForFood(){
        for(Food food: foodArray){
            if(bounds.contains(food.getX(),food.getY()) && !food.followingChef){
                foodId = food.getId();
                foodArray.remove(food);
                timer = new CountdownTimer(15);
            }
        }

    }
}
