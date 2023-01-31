package com.oshewo.panic.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.oshewo.panic.interfaces.IInteractable;
import com.oshewo.panic.screens.PlayScreen;
import com.oshewo.panic.tools.InputHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static com.oshewo.panic.tools.InputHandler.lastMove;
import static com.oshewo.panic.screens.PlayScreen.activePlayer;

public class Food extends Sprite implements IInteractable {
    private int id;
    private boolean choppable = false;
    private boolean grillable = false;
    public boolean followingChef = false;
    private Chef chefToFollow;

    public static ArrayList<Food> foodArray = new ArrayList<Food>();

    public Food(Texture texture, int id){
        super(texture);
        this.id = id;
        if(id<=4 && id>=1){
            this.choppable = true;
        } else if (id==5 || id ==40) {
            this.grillable = true;
        }
        foodArray.add(this);
    }


    public void update(float dt) {
        if (followingChef){
            this.setX(chefToFollow.getX()+chefToFollow.getWidth()/4);
            this.setY(chefToFollow.getY());
        } else{
            List<Integer> idList = new ArrayList<>();
            List<Food> foods = new ArrayList<>();
            for(Food food : foodArray){
                if(!food.isCarried()) {
                    float yDiff = food.getPosition().y - this.getPosition().y;
                    float xDiff = food.getPosition().x - this.getPosition().x;
                    if (yDiff >= -16 && yDiff <= 16 && xDiff >= -16 && xDiff <= 16) {
                        int id = food.getId();
                        idList.add(id);
                        foods.add(food);
                    }
                }
            }
            if(idList.size() == 3 && idList.contains(10) && idList.contains(20) && idList.contains(30)){
                Food gen = new Food(new Texture("salad.png"), 60);
                gen.setX(this.getPosition().x);
                gen.setY(this.getPosition().y);
                for(Food food : foods){
                    foodArray.remove(food);
                }
            } else if (idList.size() == 2 && idList.contains(400) && idList.contains(50)) {
                Food gen = new Food(new Texture("burger.png"), 450);
                gen.setX(this.getPosition().x);
                gen.setY(this.getPosition().y);
                for(Food food : foods){
                    foodArray.remove(food);
                }
            } else{return;}
            activePlayer.isHolding = false;
        }
    }

    public Vector2 getPosition(){
        return new Vector2(this.getX(),this.getY());
    }


    @Override
    public void onUse(Chef chefInUse) {
        float offsetX;
        float offsetY;

        // put down
        if(followingChef && chefToFollow == chefInUse){
            chefInUse.isHolding = false;
            followingChef = false;
            if(lastMove== Input.Keys.S){
                offsetX = chefToFollow.getWidth()/4;
                offsetY = -10;
            } else if (lastMove == Input.Keys.W) {
                offsetX = chefToFollow.getWidth()/4;
                offsetY = chefToFollow.getHeight();
            } else if (lastMove == Input.Keys.A){
                offsetX = -10;
                offsetY = 2;
            } else{
                offsetX = chefToFollow.getWidth();
                offsetY = 2;
            }
            this.setX(chefToFollow.getX()+offsetX);
            this.setY(chefToFollow.getY()+offsetY);
            chefToFollow = null;
        }
        // pickup
        else{
            if(!chefInUse.isHolding && chefToFollow == null ) {
                chefToFollow = chefInUse;
                chefInUse.isHolding = true;
                followingChef = true;
            }
        }
    }
    public int getId(){
        return id;
    }

    public boolean isChoppable(){
        return choppable;
    }

    public boolean isGrillable(){
        return grillable;
    }

    public boolean isCarried(){
        return followingChef;
    }
}
