package com.oshewo.panic.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.oshewo.panic.interfaces.IInteractable;
import com.oshewo.panic.screens.PlayScreen;

import static com.oshewo.panic.screens.PlayScreen.activePlayer;

public class Food extends Sprite implements IInteractable {
    private int id;
    private boolean choppable;
    private boolean grillable;
    private boolean followingChef = false;
    private Chef chefToFollow;

    public Food(Texture texture, int id, boolean choppable, boolean grillable){
        super(texture);
        this.id = id;
        this.choppable = choppable;
        this.grillable = grillable;
    }

    public Food(Texture texture, int id){
        this(texture, id, false, false);
    }

    public void update(float dt) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            this.onUse(activePlayer);
        }

        if (followingChef){
            this.setX(chefToFollow.getX()+chefToFollow.getWidth()/4);
            this.setY(chefToFollow.getY());
        }
    }


    @Override
    public void onUse(Chef chefInUse) {
        if(followingChef && chefToFollow == chefInUse){
            chefInUse.isHolding = false;
            followingChef = false;
            this.setX(chefToFollow.getX()+chefToFollow.getWidth()/4);
            this.setY(chefToFollow.getY()-10);
            chefToFollow = null;
        }
        else{
            if(chefInUse.isHolding == false && chefToFollow == null) {
                chefToFollow = chefInUse;
                chefInUse.isHolding = true;
                followingChef = true;
            }
        }
    }
}
