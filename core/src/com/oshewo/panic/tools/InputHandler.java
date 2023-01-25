package com.oshewo.panic.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.oshewo.panic.screens.PlayScreen;

import static com.oshewo.panic.screens.PlayScreen.activePlayer;

public class InputHandler {
    public static void handleInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            activePlayer.nearestFood().onUse(activePlayer);
        }


        // * * * * M O V E M E N T * * * * //

        // Cancel momentum then handle new inputs
        float x = 0;
        float y = 0;
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            x += 200f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            x -= 200f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            y -= 200f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            y += 200f;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.TAB)){
            // stop old chef then switch control of chef to the other
            PlayScreen.swapChef();
        }

        // apply moves from all input keys
        activePlayer.b2body.setLinearVelocity(new Vector2(x,y));
    }
}
