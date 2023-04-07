package com.oshewo.panic.actor;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.oshewo.panic.PiazzaPanic;
import com.oshewo.panic.base.BaseActor;
import com.oshewo.panic.enums.*;
import com.oshewo.panic.non_actor.Ingredient;
import com.oshewo.panic.screens.PlayScreen;

import java.util.*;

import static com.oshewo.panic.lists.Lists.foods;

/**
 * The food class sets the attributes for each ingredient and what preparation can be done to it
 *
 * @author Oshewo
 */
public class Food extends BaseActor {

    private final PiazzaPanic game;
    private final Ingredient ingredient;

    private PlayScreen playScreen;

    // what is being held by the chef
    private int chefToFollow = -1;

    /**
     * Instantiates a new Food. Sets ID and whether it is choppable or grillable.
     *
     * @param texture    the texture for the food
     * @param ingredient the ingredient
     */
    public Food(float x, float y, Stage s, String texture, Ingredient ingredient, PlayScreen playScreen, PiazzaPanic game) {
        super(x, y, s);
        this.playScreen = playScreen;
        this.game = game;
        this.ingredient = ingredient;
        this.loadTexture(texture, 40, 40);
    }

    public Food(float x, float y, Stage s, String texture, Ingredient ingredient, PlayScreen playScreen, PiazzaPanic game, int following) {
        super(x, y, s);
        this.playScreen = playScreen;
        this.game = game;
        this.ingredient = ingredient;
        this.loadTexture(texture, 40, 40);
        this.chefToFollow = following;
    }

    public Food(float x, float y, Stage s, Texture texture, Ingredient ingredients, PlayScreen playScreen, PiazzaPanic game) {
        super(x, y, s);
        this.playScreen = playScreen;
        this.game = game;
        this.ingredient = ingredients;
//        foods.add(this);
    }


    /**
     * Updates which foods are being carried by the chef and sets the texture according to the food being carried
     */
    public void update(PlayScreen playScreen) {
        updatePlayScreen(playScreen);
        if (this.chefToFollow != -1) {
            this.setX(this.playScreen.chefs[this.chefToFollow].getX() + this.playScreen.chefs[this.chefToFollow].getWidth() / 4);
            this.setY(this.playScreen.chefs[this.chefToFollow].getY());
        } else {
            List<Ingredients> ingredientsList = new ArrayList<>();
            List<Food> food1 = new ArrayList<>();
            for (Food food : foods) {
                if (!food.isCarried()) {
                    float yDiff = food.getPosition().y - this.getPosition().y;
                    float xDiff = food.getPosition().x - this.getPosition().x;
                    if (yDiff >= -16 && yDiff <= 16 && xDiff >= -16 && xDiff <= 16) {
//                        Ingredients ingredients = food.getIngredient();
//                        ingredientsList.add(ingredients);
                        food1.add(food);
                    }
                }
            }
            if (ingredientsList.size() == 3 && ingredientsList.contains(Ingredients.TOMATO) && ingredientsList.contains(Ingredients.ONION) && ingredientsList.contains(Ingredients.LETTUCE)) {
//                Food gen = new Food(new Texture("salad.png"), Item.SALAD);
                Food gen = null;
                gen.setX(this.getPosition().x);
                gen.setY(this.getPosition().y);
                for (Food food : food1) {
                    foods.remove(food);
                }
            } else if (ingredientsList.size() == 2 && ingredientsList.contains(Ingredients.TOP_BUN) && ingredientsList.contains(Ingredients.PATTY)) {
//                Food gen = new Food(new Texture("burger.png"), Item.BURGER);
                Food gen = null;
                gen.setX(this.getPosition().x);
                gen.setY(this.getPosition().y);
                for (Food food : food1) {
                    foods.remove(food);
                }
            } else {
                return;
            }
//            this.playScreen.chefs[this.chefToFollow].isHolding = false;
        }
    }

    /**
     * Gets x and y position
     *
     * @return 2D vector of x and y position
     */
    public Vector2 getPosition() {
        return new Vector2(this.getX(), this.getY());
    }


//    @Override
    public void onUse() {
        float offsetX;
        float offsetY;

        System.out.println(5);
        // puts down food according to direction of chef which is what movement key was last pressed
        if (this.chefToFollow != -1 && this.chefToFollow == this.playScreen.getChefSelector()) {
            System.out.println(6);
            this.playScreen.chefs[this.chefToFollow].isHolding = false;
            if (this.playScreen.getLastMove() == Input.Keys.S) {
                System.out.println(7);
                offsetX = this.playScreen.chefs[this.chefToFollow].getWidth() / 4;
                offsetY = -10;
            } else if (this.playScreen.getLastMove() == Input.Keys.W) {
                System.out.println(8);
                offsetX = this.playScreen.chefs[this.chefToFollow].getWidth() / 4;
                offsetY = this.playScreen.chefs[this.chefToFollow].getHeight();
            } else if (this.playScreen.getLastMove() == Input.Keys.A) {
                System.out.println(9);
                offsetX = -10;
                offsetY = 2;
            } else {
                System.out.println(10);
                offsetX = this.playScreen.chefs[this.chefToFollow].getWidth();
                offsetY = 2;
            }
            System.out.println(11);
            this.setX(this.playScreen.chefs[this.chefToFollow].getX() + offsetX);
            this.setY(this.playScreen.chefs[this.chefToFollow].getY() + offsetY);
            chefToFollow = -1;
        }
        // pickup food
        else {
            System.out.println(12);
            if (chefToFollow == -1) {
                System.out.println(13);
                this.chefToFollow = this.playScreen.getChefSelector();
                this.playScreen.chefs[this.chefToFollow].isHolding = true;
            } else if (!this.playScreen.chefs[this.chefToFollow].isHolding) {
                System.out.println(14);
                this.chefToFollow = this.playScreen.getChefSelector();
                this.playScreen.chefs[this.chefToFollow].isHolding = true;
            }
        }
    }

    /**
     * Get id
     *
     * @return id
     */
    public Ingredient getIngredient() {
        return ingredient;
    }

    /**
     * Determines whether food is being carried or not.
     *
     * @return boolean of if it is carried
     */
    public boolean isCarried() {
        return this.chefToFollow != -1;
    }

    public void updatePlayScreen(PlayScreen playScreen) {
        this.playScreen = playScreen;
    }
}
