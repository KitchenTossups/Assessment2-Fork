package com.oshewo.panic.actor;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.oshewo.panic.PiazzaPanic;
import com.oshewo.panic.base.BaseActor;
import com.oshewo.panic.enums.*;
import com.oshewo.panic.non_actor.Food;
import com.oshewo.panic.screens.PlayScreen;

import java.util.*;

import static com.oshewo.panic.lists.Lists.foodActors;

/**
 * The food class sets the attributes for each ingredient and what preparation can be done to it
 *
 * @author Oshewo
 */
public class FoodActor extends BaseActor {

    private final PiazzaPanic game;
    private final Food food;

    private PlayScreen playScreen;

    // what is being held by the chef
    private int chefToFollow = -1;

    public FoodActor(float x, float y, Stage s, Food food, PlayScreen playScreen, PiazzaPanic game, int following) {
        super(x, y, s);
        this.playScreen = playScreen;
        this.game = game;
        this.food = food;
        this.chefToFollow = following;

        String image = "";

        switch (food.getItem()) {
            case BUN:
                switch (food.getState()) {
                    case UNCOOKED:
                        image = "bun.png";
                        break;
                    case HALF_COOKED:
                        image = "bun.png";
                        break;
                    case COOKED:
                        image = "bun_toasted.png";
                        break;
                    case OVERCOOKED:
                        image = "pizza_base_burnt.png";
                    default:
                        remove();
                        break;
                }
                break;
            case PATTY:
//                bypass = true;
                switch (food.getState()) {
                    case UNCUT_UNCOOKED:
                        image = "meat.png";
                        break;
                    case UNCOOKED:
                        image = "patty.png";
                        break;
                    case HALF_COOKED:
                        image = "images/PattyHalfCooked.png";
                        break;
                    case COOKED:
                        image = "patty_cooked.png";
                        break;
                    case OVERCOOKED:
                        image = "images/PattyOvercooked.png";
                        break;
                    default:
                        remove();
                        break;
                }
                break;
            case CHEDDAR:
                switch (food.getState()) {
                    case UNCUT:
                        image = "Cheddar.png";
                        break;
                    case CUT:
                        image = "cheese_sliced.png";
                        break;
                    default:
                        remove();
                        break;
                }
                break;
            case LETTUCE:
                switch (food.getState()) {
                    case UNCUT:
                        image = "lettuce.png";
                        break;
                    case CUT:
                        image = "lettuce_chopped.png";
                        break;
                    default:
                        remove();
                        break;
                }
                break;
            case TOMATO:
                switch (food.getState()) {
                    case UNCUT:
                        image = "tomato.png";
                        break;
                    case CUT:
                        image = "tomato_chopped.png";
                        break;
                    default:
                        remove();
                        break;
                }
                break;
            case ONION:
                switch (food.getState()) {
                    case UNCUT:
                        image = "onion.png";
                        break;
                    case CUT:
                        image = "onion_chopped.png";
                        break;
                    default:
                        remove();
                        break;
                }
                break;
            case PEPPERONI:
                switch (food.getState()) {
                    case UNCUT:
                        image = "pepperoni.png";
                        break;
                    case CUT:
                        image = "pepperoni_chopped.png";
                        break;
                    default:
                        remove();
                        break;
                }
                break;
            case BEANS:
                switch (food.getState()) {
                    case UNCOOKED:
                    case HALF_COOKED:
                        image = "canned_beans.png";
                        break;
                    case COOKED:
                        image = "beans_cooked.png";
                        break;
                    case OVERCOOKED:
                        image = "beans_burnt.png";
                        break;
                    default:
                        remove();
                        break;
                }
                break;
            case JACKET:
                switch (food.getState()) {
                    case UNCOOKED:
                    case HALF_COOKED:
                        image = "potato.png";
                        break;
                    case COOKED:
                        image = "potato-cooked.png";
                        break;
                    case OVERCOOKED:
                        image = "potato-burnt.png";
                        break;
                    default:
                        remove();
                        break;
                }
                break;
            case PIZZA_BASE:
                switch (food.getState()) {
                    case UNCOOKED:
                    case HALF_COOKED:
                        image = "pizza_base.png";
                        break;
                    case COOKED:
                        image = "pizza_base_cooked.png";
                        break;
                    case OVERCOOKED:
                        image = "pizza_base_burnt.png";
                        break;
                    default:
                        remove();
                        break;
                }
                break;
            default:
                break;
        }
        this.loadTexture(image, 40, 40);
        super.setBoundaryRectangle();
    }


    /**
     * Updates which foods are being carried by the chef and sets the texture according to the food being carried
     */
    public void update(PlayScreen playScreen) {         // Called whenever frames are refreshed and updates anything during runtime for FoodActor
        updatePlayScreen(playScreen);
        if (this.chefToFollow != -1) {
            this.setX(this.playScreen.chefs[this.chefToFollow].getX() + this.playScreen.chefs[this.chefToFollow].getWidth() / 4);
            this.setY(this.playScreen.chefs[this.chefToFollow].getY());
        } else {
            List<Ingredients> ingredientsList = new ArrayList<>();
            List<FoodActor> foodActor1 = new ArrayList<>();
            for (FoodActor foodActor : foodActors) {
                if (foodActor.isNotCarried()) {
                    float yDiff = foodActor.getPosition().y - this.getPosition().y;
                    float xDiff = foodActor.getPosition().x - this.getPosition().x;
                    if (yDiff >= -16 && yDiff <= 16 && xDiff >= -16 && xDiff <= 16) {
//                        Ingredients ingredients = food.getIngredient();
//                        ingredientsList.add(ingredients);
                        foodActor1.add(foodActor);
                    }
                }
            }
            if (ingredientsList.size() == 3 && ingredientsList.contains(Ingredients.TOMATO) && ingredientsList.contains(Ingredients.ONION) && ingredientsList.contains(Ingredients.LETTUCE)) {
//                Food gen = new Food(new Texture("salad.png"), Item.SALAD);
                FoodActor gen = null;
                gen.setX(this.getPosition().x);
                gen.setY(this.getPosition().y);
                for (FoodActor foodActor : foodActor1) {
                    foodActors.remove(foodActor);
                }
            } else if (ingredientsList.size() == 2 && ingredientsList.contains(Ingredients.BUN) && ingredientsList.contains(Ingredients.PATTY)) {
//                Food gen = new Food(new Texture("burger.png"), Item.BURGER);
                FoodActor gen = null;
                gen.setX(this.getPosition().x);
                gen.setY(this.getPosition().y);
                for (FoodActor foodActor : foodActor1) {
                    foodActors.remove(foodActor);
                }
            }
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

//        if (this.game.VERBOSE)
//            System.out.println(5);
        // puts down food according to direction of chef which is what movement key was last pressed
        if (this.chefToFollow != -1 && this.chefToFollow == this.playScreen.getChefSelector()) {
//            if (this.game.VERBOSE)
//                System.out.println(6);
            this.playScreen.chefs[this.chefToFollow].isHolding = false;
            if (this.playScreen.getLastMove() == Input.Keys.S) {
//                if (this.game.VERBOSE)
//                    System.out.println(7);
                offsetX = this.playScreen.chefs[this.chefToFollow].getWidth() / 4;
                offsetY = -30;
            } else if (this.playScreen.getLastMove() == Input.Keys.W) {
//                if (this.game.VERBOSE)
//                    System.out.println(8);
                offsetX = this.playScreen.chefs[this.chefToFollow].getWidth() / 4;
                offsetY = this.playScreen.chefs[this.chefToFollow].getHeight();
            } else if (this.playScreen.getLastMove() == Input.Keys.A) {
//                if (this.game.VERBOSE)
//                    System.out.println(9);
                offsetX = -40;
                offsetY = 2;
            } else {
//                if (this.game.VERBOSE)
//                    System.out.println(10);
                offsetX = this.playScreen.chefs[this.chefToFollow].getWidth();
                offsetY = 2;
            }
//            if (this.game.VERBOSE)
//                System.out.println(11);
            this.setX(this.playScreen.chefs[this.chefToFollow].getX() + offsetX);
            this.setY(this.playScreen.chefs[this.chefToFollow].getY() + offsetY);
            this.playScreen.chefs[this.chefToFollow].isHolding = false;
            this.chefToFollow = -1;
        }
        // pickup food
        else {
//            if (this.game.VERBOSE)
//                System.out.println(12);
            if (this.chefToFollow == -1 && !this.playScreen.chefs[this.playScreen.getChefSelector()].isHolding) {
//                if (this.game.VERBOSE)
//                    System.out.println(13);
                this.chefToFollow = this.playScreen.getChefSelector();
                this.playScreen.chefs[this.chefToFollow].isHolding = true;
            }
        }
    }

    /**
     * Gets the food item
     *
     * @return food
     */
    public Food getFood() {
        return this.food;
    }

    /**
     * Determines whether food is being carried or not.
     *
     * @return boolean of if it is carried
     */
    public boolean isNotCarried() {
        return this.chefToFollow == -1;
    }

    public void updatePlayScreen(PlayScreen playScreen) {
        this.playScreen = playScreen;
    }

    public String getSaveConfig() {
        return String.format("%f~%f~%d~%s~%s", super.getX(), super.getY(), this.chefToFollow, this.food.getItem().getString(), this.food.getState().getString());
    }

    @Override
    public String toString() {
        return "FoodActor{" +
                "food=" + food +
                ", chefToFollow=" + chefToFollow +
                '}';
    }
}
