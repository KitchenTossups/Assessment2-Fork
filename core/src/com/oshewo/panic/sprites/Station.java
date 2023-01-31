package com.oshewo.panic.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

import static com.oshewo.panic.scenes.Hud.hudStartTime;
import static com.oshewo.panic.screens.PlayScreen.*;
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
            output();
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
                if(food.isChoppable() && this.type == "board"){
                    foodArray.remove(food);
                    timer = new CountdownTimer(15,bounds);
                }
                else if(food.isGrillable() && this.type == "stove"){
                    foodArray.remove(food);
                    timer = new CountdownTimer(15,bounds);
                }else if(foodId == currentOrder.getOrderId() && this.type == "service"){
                    foodArray.remove(food);
                    timer = new CountdownTimer(0,bounds);
                    submitOrder();
                }
                else{
                    foodId = -1;
                }
            }
        }

    }
    public void submitOrder(){
        currentOrder =null;
        hudStartTime = TimeUtils.millis();
        ordersCompleted++;
    }

    public void showProgress() {
        float progress = timer.getProgressPercent();
        float x = this.bounds.x;
        float y = this.bounds.y + this.bounds.height;
        float width = 32;
        float height = 5;
    }
    public void output(){
        String tex = "";
        if(type=="board"){
            tex = choppingOutput();
        } else if(type=="stove"){
            tex = cookingOutput();
        }
        if(tex=="" || tex ==null){return;}
        foodId*=10;
        Food gen = new Food(new Texture(tex), foodId);
        gen.setX(bounds.getX()-10);
        gen.setY(bounds.getY()-10);
    }
    public String choppingOutput(){
        if(foodId==1){
            return  "lettuce_chopped.png";
        } else if(foodId==2){
            return  "tomato_chopped.png";
        } else if(foodId==3){
            return  "onion_chopped.png";
        } else if(foodId==4) {
            return "patty.png";
        }
        return null;
    }

    public String cookingOutput(){
        if(foodId==5){
            return  "bun_toasted.png";
        } else if(foodId==40){
            return  "patty_cooked.png";
        }
        return null;
    }
}
