package com.oshewo.panic.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.oshewo.panic.sprites.Food;
import com.oshewo.panic.stations.FoodCrate;

import static com.oshewo.panic.screens.PlayScreen.activePlayer;
import static com.oshewo.panic.screens.PlayScreen.swapChef;
import static com.oshewo.panic.tools.WorldCreator.crateArray;

/**
 * The type Input handler.
 * Handles user input and what actions need to be taken
 *
 * @author Oshewo
 */
public class InputHandler {
    public static int lastMove;
    private static final int pickupRadius = 48;

    /**
     * Handle input of picking up food that is nearest
     *
     * @param dt the dt
     */
    public static void handleInput(float dt) {
        Food nearestFood = activePlayer.nearestFood(pickupRadius);
        // pickup item
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (nearestFood != null) {
                nearestFood.onUse(activePlayer);
            } else {
                for (FoodCrate crate : crateArray) {
                    crate.onUse(activePlayer);
                }
            }
        }

        handleMovement(dt);
        //debugControls();
    }

    /**
     * Handle movement of chefs.
     *
     * @param dt the dt
     */
    public static void handleMovement(float dt) {

        // * * * * M O V E M E N T * * * * //

        // Cancel momentum then handle new inputs
        float x = 0;
        float y = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += 200f;
            lastMove = Input.Keys.D;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= 200f;
            lastMove = Input.Keys.A;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            y -= 200f;
            lastMove = Input.Keys.S;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            y += 200f;
            lastMove = Input.Keys.W;
        }
        // swap Chefs
        if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)) {
            swapChef();
        }

        // apply moves from all input keys
        activePlayer.b2body.setLinearVelocity(new Vector2(x, y));
    }

    /**
     * Debug controls
     */
    public static void debugControls() {
        // spawn lettuce
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            Food gen = new Food(new Texture("lettuce.png"), 1);
            gen.setX(activePlayer.getX());
            gen.setY(activePlayer.getY());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            Food gen = new Food(new Texture("tomato.png"), 2);
            gen.setX(activePlayer.getX());
            gen.setY(activePlayer.getY());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            Food gen = new Food(new Texture("onion.png"), 3);
            gen.setX(activePlayer.getX());
            gen.setY(activePlayer.getY());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Y)) {
            Food gen = new Food(new Texture("meat.png"), 4);
            gen.setX(activePlayer.getX());
            gen.setY(activePlayer.getY());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.U)) {
            Food gen = new Food(new Texture("bun.png"), 5);
            gen.setX(activePlayer.getX());
            gen.setY(activePlayer.getY());
        }
    }

}
