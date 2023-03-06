package com.oshewo.panic.sprites;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.oshewo.panic.enums.Ingredients;
import com.oshewo.panic.interfaces.Interactable;


import java.util.ArrayList;
import java.util.List;

import static com.oshewo.panic.tools.InputHandler.lastMove;
import static com.oshewo.panic.screens.PlayScreen.activePlayer;

/**
 * The food class sets the attributes for each ingredient and what preparation can be done to it
 *
 * @author Oshewo
 */
public class Food extends Sprite implements Interactable {
    private final Ingredients ingredient;

    // how the foods can be prepped
    private boolean choppable = false;
    private boolean grillable = false;

    // what is being held by the chef
    public static ArrayList<Food> foodArray = new ArrayList<Food>();
    public boolean followingChef = false;
    private Chef chefToFollow;

    /**
     * Instantiates a new Food. Sets ID and whether it is choppable or grillable.
     *
     * @param texture    the texture for the food
     * @param ingredient the ingredient
     */
    public Food(Texture texture, Ingredients ingredient) {
        super(texture);
        this.ingredient = ingredient;
        if (ingredient == Ingredients.TOMATO || ingredient == Ingredients.ONION || ingredient == Ingredients.LETTUCE) {
            this.choppable = true;
        } else if (ingredient == Ingredients.BUN || ingredient == Ingredients.PATTY) {
            this.grillable = true;
        }
        foodArray.add(this);
    }


    /**
     * Updates which foods are being carried by the chef and sets the texture according to the food being carried
     *
     * @param dt the dt
     */
    public void update(float dt) {
        if (followingChef) {
            this.setX(chefToFollow.getX() + chefToFollow.getWidth() / 4);
            this.setY(chefToFollow.getY());
        } else {
            List<Ingredients> ingredientsList = new ArrayList<>();
            List<Food> foods = new ArrayList<>();
            for (Food food : foodArray) {
                if (!food.isCarried()) {
                    float yDiff = food.getPosition().y - this.getPosition().y;
                    float xDiff = food.getPosition().x - this.getPosition().x;
                    if (yDiff >= -16 && yDiff <= 16 && xDiff >= -16 && xDiff <= 16) {
                        Ingredients ingredient = food.getIngredient();
                        ingredientsList.add(ingredient);
                        foods.add(food);
                    }
                }
            }
            if (ingredientsList.size() == 3 && ingredientsList.contains(Ingredients.TOMATO) && ingredientsList.contains(Ingredients.ONION) && ingredientsList.contains(Ingredients.LETTUCE)) {
                Food gen = new Food(new Texture("salad.png"), Ingredients.SALAD);
                gen.setX(this.getPosition().x);
                gen.setY(this.getPosition().y);
                for (Food food : foods) {
                    foodArray.remove(food);
                }
            } else if (ingredientsList.size() == 2 && ingredientsList.contains(Ingredients.BUN) && ingredientsList.contains(Ingredients.PATTY)) {
                Food gen = new Food(new Texture("burger.png"), Ingredients.BURGER);
                gen.setX(this.getPosition().x);
                gen.setY(this.getPosition().y);
                for (Food food : foods) {
                    foodArray.remove(food);
                }
            } else {
                return;
            }
            activePlayer.isHolding = false;
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


    @Override
    public void onUse(Chef chefInUse) {
        float offsetX;
        float offsetY;

        // puts down food according to direction of chef which is what movement key was last pressed
        if (followingChef && chefToFollow == chefInUse) {
            chefInUse.isHolding = false;
            followingChef = false;
            if (lastMove == Input.Keys.S) {
                offsetX = chefToFollow.getWidth() / 4;
                offsetY = -10;
            } else if (lastMove == Input.Keys.W) {
                offsetX = chefToFollow.getWidth() / 4;
                offsetY = chefToFollow.getHeight();
            } else if (lastMove == Input.Keys.A) {
                offsetX = -10;
                offsetY = 2;
            } else {
                offsetX = chefToFollow.getWidth();
                offsetY = 2;
            }
            this.setX(chefToFollow.getX() + offsetX);
            this.setY(chefToFollow.getY() + offsetY);
            chefToFollow = null;
        }
        // pickup food
        else {
            if (!chefInUse.isHolding && chefToFollow == null) {
                chefToFollow = chefInUse;
                chefInUse.isHolding = true;
                followingChef = true;
            }
        }
    }

    /**
     * Get id
     *
     * @return id
     */
    public Ingredients getIngredient() {
        return ingredient;
    }

    /**
     * Determines whether food is choppable
     *
     * @return choppable boolean
     */
    public boolean isChoppable() {
        return choppable;
    }

    /**
     * Determines whether food is grillable.
     *
     * @return grillable boolean
     */
    public boolean isGrillable() {
        return grillable;
    }

    /**
     * Determines whether food is being carried or not.
     *
     * @return boolean of if it is carried
     */
    public boolean isCarried() {
        return followingChef;
    }
}
