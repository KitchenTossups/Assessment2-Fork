package com.oshewo.panic.sprites;
;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.oshewo.panic.interfaces.IInteractable;


public class Food extends Sprite implements IInteractable {
    private int id;
    private boolean choppable;
    private boolean grillable;
    private boolean followingChef = false;
    private Chef chefToFollow;
    public static Array<Food> foodArray;



    public Food(Texture texture, int id, boolean choppable, boolean grillable, Array<Food> foodArray){
        super(texture);
        this.id = id;
        this.choppable = choppable;
        this.grillable = grillable;
        foodArray.add(this);
    }
    public Food(Texture texture, int id, boolean choppable, boolean grillable){
        this(texture, id, choppable, grillable, foodArray);
    }

    public Food(Texture texture, int id){ this(texture, id, false, false, foodArray);
    }


    public void update(float dt) {
        if (followingChef){
            this.setX(chefToFollow.getX()+chefToFollow.getWidth()/4);
            this.setY(chefToFollow.getY());
        }
    }

    public Vector2 getPosition(){
        return(new Vector2(this.getX(),this.getY()));
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
