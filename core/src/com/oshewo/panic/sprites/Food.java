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

import java.util.HashSet;
import java.util.Set;
import static com.oshewo.panic.tools.InputHandler.lastMove;
import static com.oshewo.panic.screens.PlayScreen.activePlayer;

public class Food extends Sprite implements IInteractable {
    private int id;
    private boolean choppable;
    private boolean grillable;
    public boolean followingChef = false;
    private Chef chefToFollow;

    public static Set<Food> foodArray = new HashSet<>();

    public Food(Texture texture, int id, boolean choppable, boolean grillable){
        super(texture);
        this.id = id;
        this.choppable = choppable;
        this.grillable = grillable;
        foodArray.add(this);
    }

    public Food(Texture texture, int id){
        this(texture, id, false, false);
    }

    public void update(float dt) {
        if (followingChef){
            this.setX(chefToFollow.getX()+chefToFollow.getWidth()/4);
            this.setY(chefToFollow.getY());
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
}
