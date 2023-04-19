package com.oshewo.panic.base;

import com.badlogic.gdx.graphics.Texture;
import com.oshewo.panic.PiazzaPanic;

public abstract class BasePowerup {

    boolean active = false;
    String imageURL;
    Texture texture;
    PiazzaPanic game;

    public BasePowerup(PiazzaPanic game, String imageURL){
        this.imageURL = imageURL;
        this.texture = new Texture(imageURL);
        this.game = game;
    }

    public abstract void usePowerup();

    public void show(){

    }

    public void hide(){

    }
}
