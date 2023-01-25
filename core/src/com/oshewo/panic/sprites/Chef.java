package com.oshewo.panic.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.oshewo.panic.screens.PlayScreen;

import static com.oshewo.panic.screens.PlayScreen.activePlayer;
import static com.oshewo.panic.sprites.Food.foodArray;

public class Chef extends Sprite {
    public World world;
    public Body b2body;
    private TextureRegion chefStand;

    public boolean isHolding = false;

    public Chef(World world, int id, PlayScreen screen){
        super(screen.getAtlas().findRegion("chef0"));
        this.world = world;
        defineChef(id);
        chefStand = new TextureRegion(getTexture(),128*id,0,32,32);
        setBounds(0,0,32,32);
        setRegion(chefStand);
    }


    public void update(float dt){
        setPosition(b2body.getPosition().x-(getWidth()/2),b2body.getPosition().y-(getHeight()/4));
    }
    public void defineChef(int id){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32*(id+5),32*(id+5));
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

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


    public Food nearestFood(){
        return this.nearestFood(32);
    }

    public Vector2 getPosition(){
        return new Vector2(this.getX(),this.getY());
    }



}
