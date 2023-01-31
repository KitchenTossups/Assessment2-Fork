package com.oshewo.panic.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.oshewo.panic.screens.PlayScreen;

import static com.oshewo.panic.sprites.Food.foodArray;

/**
 * Creates chef and its actions and interactions with foods
 *
 * @author Oshewo
 */
public class Chef extends Sprite {
    public World world;
    public Body b2body;
    private final TextureRegion chefStand;
    public boolean isHolding = false;
    private final BodyDef bdef = new BodyDef();

    /**
     * Instantiates a new Chef.
     *
     * @param world  the world
     * @param id     the id
     * @param screen the screen
     */
    public Chef(World world, int id, PlayScreen screen){
        super(screen.getAtlas().findRegion("chef0"));
        this.world = world;
        defineChef(id);
        chefStand = new TextureRegion(getTexture(),128*id,0,32,32);
        setBounds(0,0,32,32);
        setRegion(chefStand);
    }


    /**
     * Updates position of where the chef is at
     *
     * @param dt the dt
     */
    public void update(float dt){
        setPosition(b2body.getPosition().x-(getWidth()/2),b2body.getPosition().y-(getHeight()/4));
    }

    /**
     * Sets collisions and hitbox for the chef
     *
     * @param id the id
     */
    public void defineChef(int id){
        bdef.position.set(32*(id*5+5)+10,32*5);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    /**
     * Detect whether there is a food nearby and pickup.
     *
     * @param distance the distance from the food
     * @return the food that is nearest
     */
    public Food nearestFood(float distance){
        Food nearestFood = null;
        float nearestDistance = Float.MAX_VALUE;
        for (Food food : foodArray){
            float currentDistance = this.getPosition().dst(food.getPosition());
            if( currentDistance <= distance && currentDistance < nearestDistance){
                nearestDistance = currentDistance;
                nearestFood = food;
            }
        }
        return nearestFood;
    }


    /**
     * Nearest food.
     *
     * @return the food
     */
    public Food nearestFood(){
        return this.nearestFood(Float.MAX_VALUE);
    }

    /**
     * Gets x and y position of chef
     *
     * @return 2D vector of x and y position
     */
    public Vector2 getPosition(){
        return new Vector2(this.getX(),this.getY());
    }

    /**
     * Gets the body definition of chef
     *
     * @return the body def
     */
    public BodyDef getBDef(){return this.bdef;}



}
