package com.oshewo.panic.base;

import com.badlogic.gdx.graphics.Texture;
import com.oshewo.panic.PiazzaPanic;

public abstract class BasePowerUp {

    boolean active = false;
    String imageURL;
    Texture texture;
    PiazzaPanic game;

    public BasePowerUp(PiazzaPanic game, String imageURL){
        this.imageURL = imageURL;
        this.texture = new Texture(imageURL);
        this.game = game;
    }

    public abstract void usePowerUp();

    public void show(){

    }

    public void hide(){

    }
}
