package com.oshewo.panic.sprites;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.oshewo.panic.PiazzaPanic;
import com.oshewo.panic.base.BaseActor;
import com.oshewo.panic.enums.*;
import com.oshewo.panic.screens.PlayScreen;

import java.util.*;

/**
 * The food class sets the attributes for each ingredient and what preparation can be done to it
 *
 * @author Oshewo
 */
public class Food extends BaseActor {

    private final PiazzaPanic game;
    private final Item item;

    private PlayScreen playScreen;

    // how the foods can be prepped
    private boolean choppable = false;
    private boolean grillable = false;

    // what is being held by the chef
    public static ArrayList<Food> foodArray = new ArrayList<>();
    public boolean followingChef = false;
    private int chefToFollow = -1;

    /**
     * Instantiates a new Food. Sets ID and whether it is choppable or grillable.
     *
     * @param texture    the texture for the food
     * @param item the ingredient
     */
    public Food(float x, float y, Stage s, String texture, Item item, PlayScreen playScreen, PiazzaPanic game) {
        super(x, y, s);
        this.playScreen = playScreen;
        this.game = game;
        this.item = item;
        this.loadTexture(texture, 40, 40);
//        if (item == Item.TOMATO || item == Item.ONION || item == Item.LETTUCE) {
//            this.choppable = true;
//        } else if (item == Item.TOP_BUN || item == Item.PATTY) {
//            this.grillable = true;
//        }
//        foodArray.add(this);
    }

    public Food(float x, float y, Stage s, Texture texture, Item item, PlayScreen playScreen, PiazzaPanic game) {
        super(x, y, s);
        this.playScreen = playScreen;
        this.game = game;
        this.item = item;
//        this.loadTexture(texture);
        if (item == Item.TOMATO || item == Item.ONION || item == Item.LETTUCE) {
            this.choppable = true;
        } else if (item == Item.TOP_BUN || item == Item.PATTY) {
            this.grillable = true;
        }
        foodArray.add(this);
    }


    /**
     * Updates which foods are being carried by the chef and sets the texture according to the food being carried
     */
    public void update(PlayScreen playScreen) {
        updatePlayScreen(playScreen);
        if (followingChef) {
            this.setX(this.playScreen.chefs[this.chefToFollow].getX() + this.playScreen.chefs[this.chefToFollow].getWidth() / 4);
            this.setY(this.playScreen.chefs[this.chefToFollow].getY());
        } else {
            List<Item> itemList = new ArrayList<>();
            List<Food> foods = new ArrayList<>();
            for (Food food : foodArray) {
                if (!food.isCarried()) {
                    float yDiff = food.getPosition().y - this.getPosition().y;
                    float xDiff = food.getPosition().x - this.getPosition().x;
                    if (yDiff >= -16 && yDiff <= 16 && xDiff >= -16 && xDiff <= 16) {
                        Item item = food.getIngredient();
                        itemList.add(item);
                        foods.add(food);
                    }
                }
            }
            if (itemList.size() == 3 && itemList.contains(Item.TOMATO) && itemList.contains(Item.ONION) && itemList.contains(Item.LETTUCE)) {
//                Food gen = new Food(new Texture("salad.png"), Item.SALAD);
                Food gen = null;
                gen.setX(this.getPosition().x);
                gen.setY(this.getPosition().y);
                for (Food food : foods) {
                    foodArray.remove(food);
                }
            } else if (itemList.size() == 2 && itemList.contains(Item.TOP_BUN) && itemList.contains(Item.PATTY)) {
//                Food gen = new Food(new Texture("burger.png"), Item.BURGER);
                Food gen = null;
                gen.setX(this.getPosition().x);
                gen.setY(this.getPosition().y);
                for (Food food : foods) {
                    foodArray.remove(food);
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
        if (this.followingChef && this.chefToFollow == this.playScreen.getChefSelector()) {
            System.out.println(6);
//            this.playScreen.chefs[this.chefToFollow].isHolding = false;
            followingChef = false;
            if (this.playScreen.lastMove == Input.Keys.S) {
                System.out.println(7);
                offsetX = this.playScreen.chefs[this.chefToFollow].getWidth() / 4;
                offsetY = -10;
            } else if (this.playScreen.lastMove == Input.Keys.W) {
                System.out.println(8);
                offsetX = this.playScreen.chefs[this.chefToFollow].getWidth() / 4;
                offsetY = this.playScreen.chefs[this.chefToFollow].getHeight();
            } else if (this.playScreen.lastMove == Input.Keys.A) {
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
                this.followingChef = true;
            } else if (!this.playScreen.chefs[this.chefToFollow].isHolding) {
                System.out.println(14);
                this.chefToFollow = this.playScreen.getChefSelector();
                this.playScreen.chefs[this.chefToFollow].isHolding = true;
                this.followingChef = true;
            }
        }
    }

    /**
     * Get id
     *
     * @return id
     */
    public Item getIngredient() {
        return item;
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

    public void updatePlayScreen(PlayScreen playScreen) {
        this.playScreen = playScreen;
    }
}
