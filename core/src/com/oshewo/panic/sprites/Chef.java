package com.oshewo.panic.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public class Chef extends Sprite {
    public World world;
    public Body b2body;

    public Chef(World world, int id){
        this.world = world;
        defineChef(id);
    }

    public void defineChef(int id){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32*(id+1),32*(id+1));
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
