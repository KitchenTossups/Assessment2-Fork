package com.oshewo.panic.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.oshewo.panic.sprites.Food;

import static com.oshewo.panic.screens.PlayScreen.activePlayer;
import static com.oshewo.panic.screens.PlayScreen.swapChef;

public class InputHandler {
    public static int lastMove;
    private static int pickupRadius = 48;

    public static void handleInput(float dt){
        Food nearestFood = activePlayer.nearestFood(pickupRadius);
        // pickup item
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            if (nearestFood == null) {
            } else {
                nearestFood.onUse(activePlayer);
            }
        }

        handleMovement(dt);
        debugControls();
    }

    public static void handleMovement(float dt){

        // * * * * M O V E M E N T * * * * //

        // Cancel momentum then handle new inputs
        float x = 0;
        float y = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            x += 200f;
            lastMove = Input.Keys.D;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            x -= 200f;
            lastMove = Input.Keys.A;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            y -= 200f;
            lastMove = Input.Keys.S;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            y += 200f;
            lastMove = Input.Keys.W;
        }
        // swap Chefs
        if(Gdx.input.isKeyJustPressed(Input.Keys.TAB)){
            swapChef();
        }

        // apply moves from all input keys
        activePlayer.b2body.setLinearVelocity(new Vector2(x,y));
    }

    public static void debugControls(){
        // spawn lettuce
        if(Gdx.input.isKeyJustPressed(Input.Keys.E)){
            Food gen = new Food(new Texture("lettuce.png"), 0);
            gen.setX(activePlayer.getX());
            gen.setY(activePlayer.getY());
        }
    }

}
