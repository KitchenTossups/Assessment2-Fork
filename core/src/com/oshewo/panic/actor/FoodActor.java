package com.oshewo.panic.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
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

//    /**
//     * Instantiates a new Food. Sets ID and whether it is choppable or grillable.
//     *
//     * @param texture    the texture for the food
//     * @param food the ingredient
//     */
//    public FoodActor(float x, float y, Stage s, String texture, Food food, PlayScreen playScreen, PiazzaPanic game) {
//        super(x, y, s);
//        this.playScreen = playScreen;
//        this.game = game;
//        this.food = food;
//        this.loadTexture(texture, 40, 40);
//    }

//    public FoodActor(float x, float y, Stage s, String texture, Food food, PlayScreen playScreen, PiazzaPanic game, int following) {
//        super(x, y, s);
//        this.playScreen = playScreen;
//        this.game = game;
//        this.food = food;
//        this.loadTexture(texture, 40, 40);
//        this.chefToFollow = following;
//    }

//    public FoodActor(float x, float y, Stage s, Texture texture, Food ingredients, PlayScreen playScreen, PiazzaPanic game) {
//        super(x, y, s);
//        this.playScreen = playScreen;
//        this.game = game;
//        this.food = ingredients;
////        foods.add(this);
//    }

    public FoodActor(float x, float y, Stage s, Food food, PlayScreen playScreen, PiazzaPanic game, int following) {
        super(x, y, s);
        this.playScreen = playScreen;
        this.game = game;
        this.food = food;
//        this.loadTexture(texture, 40, 40);
        this.chefToFollow = following;

        int rows = 32;
        int cols = 32;
        Texture texture = new Texture(Gdx.files.internal("meals.png"), true);
        int frameWidth = texture.getWidth() / cols;
        int frameHeight = texture.getHeight() / rows;

        TextureRegion[][] textureRegions = TextureRegion.split(texture, frameWidth, frameHeight);

        String image = "";

        String[] images = null;

        boolean bypass = false;

        Array<TextureRegion> textureArray = new Array<>();

        Texture texture1;

        switch (food.getItem()) {
            case TOP_BUN:
                if (food.getState() != IngredientState.NOT_APPLICABLE)
                    remove();
                else
                    image = "images/TopBun.png";
                break;
            case BOTTOM_BUN:
                if (food.getState() != IngredientState.NOT_APPLICABLE)
                    remove();
                else
                    image = "images/BottomBun.png";
                break;
            case PATTY:
                bypass = true;
                switch (food.getState()) {
                    case UNCUT_UNCOOKED:
                        images = new String[]{"meat.png", "images/Patty.png", "images/PattyHalfCooked.png", "images/PattyCooked.png", "images/PattyOvercooked.png"};
//                        images = new String[]{"meat.png"};
                        break;
                    case UNCOOKED:
                        images = new String[]{"images/Patty.png", "images/PattyHalfCooked.png", "images/PattyCooked.png", "images/PattyOvercooked.png"};
//                        images = new String[]{"images/Patty.png"};
                        break;
                    case HALF_COOKED:
                        images = new String[]{"images/PattyHalfCooked.png", "images/PattyCooked.png", "images/PattyOvercooked.png"};
//                        images = new String[]{"images/PattyHalfCooked.png"};
                        break;
                    case COOKED:
                        images = new String[]{"images/PattyCooked.png", "images/PattyOvercooked.png"};
//                        images = new String[]{"images/PattyCooked.png"};
                        break;
                    case OVERCOOKED:
                        images = new String[]{"images/PattyOvercooked.png"};
                        break;
                    default:
                        remove();
                        break;
                }
                if (images != null)
                    loadTexture(images, 40, 40);
                break;
            case CHEDDAR:
                if (food.getState() != IngredientState.NOT_APPLICABLE)
                    remove();
                else
                    image = "images/Cheese.png";
                break;
            case LETTUCE:
                bypass = true;
                switch (food.getState()) {
                    case UNCUT:
                        texture1 = new Texture(Gdx.files.internal("images/Lettuce.png"));
                        texture1.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                        textureArray.add(new TextureRegion(texture1));
                    case CUT:
                        textureArray.add(textureRegions[1][1]);
                        loadAnimationFromTextureRegion(textureArray, 0.1f, 40, 40);
                        break;
                    default:
                        remove();
                }
                break;
            case TOMATO:
                bypass = true;
                switch (food.getState()) {
                    case UNCUT:
                        images = new String[]{"images/Tomato2.png", "images/Tomato.png"};
                        break;
                    case CUT:
                        images = new String[]{"images/Tomato.png"};
                        break;
                    default:
                        remove();
                }
                if (images != null)
                    loadTexture(images, 40, 40);
                break;
            case ONION:
                bypass = true;
                switch (food.getState()) {
                    case UNCUT:
                        images = new String[]{"images/Onion2.png", "images/Onion.png"};
                        break;
                    case CUT:
                        images = new String[]{"images/Onion.png"};
                        break;
                    default:
                        remove();
                }
                if (images != null)
                    loadTexture(images, 40, 40);
                break;
            default:
                break;
        }
        if (!bypass)
            this.loadTexture(image, 40, 40);
        super.setBoundaryRectangle();
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
            } else if (ingredientsList.size() == 2 && ingredientsList.contains(Ingredients.TOP_BUN) && ingredientsList.contains(Ingredients.PATTY)) {
//                Food gen = new Food(new Texture("burger.png"), Item.BURGER);
                FoodActor gen = null;
                gen.setX(this.getPosition().x);
                gen.setY(this.getPosition().y);
                for (FoodActor foodActor : foodActor1) {
                    foodActors.remove(foodActor);
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

        if (this.game.VERBOSE)
            System.out.println(5);
        // puts down food according to direction of chef which is what movement key was last pressed
        if (this.chefToFollow != -1 && this.chefToFollow == this.playScreen.getChefSelector()) {
            if (this.game.VERBOSE)
                System.out.println(6);
            this.playScreen.chefs[this.chefToFollow].isHolding = false;
            if (this.playScreen.getLastMove() == Input.Keys.S) {
                if (this.game.VERBOSE)
                    System.out.println(7);
                offsetX = this.playScreen.chefs[this.chefToFollow].getWidth() / 4;
                offsetY = -30;
            } else if (this.playScreen.getLastMove() == Input.Keys.W) {
                if (this.game.VERBOSE)
                    System.out.println(8);
                offsetX = this.playScreen.chefs[this.chefToFollow].getWidth() / 4;
                offsetY = this.playScreen.chefs[this.chefToFollow].getHeight();
            } else if (this.playScreen.getLastMove() == Input.Keys.A) {
                if (this.game.VERBOSE)
                    System.out.println(9);
                offsetX = -40;
                offsetY = 2;
            } else {
                if (this.game.VERBOSE)
                    System.out.println(10);
                offsetX = this.playScreen.chefs[this.chefToFollow].getWidth();
                offsetY = 2;
            }
            if (this.game.VERBOSE)
                System.out.println(11);
            this.setX(this.playScreen.chefs[this.chefToFollow].getX() + offsetX);
            this.setY(this.playScreen.chefs[this.chefToFollow].getY() + offsetY);
            this.playScreen.chefs[this.chefToFollow].isHolding = false;
            this.chefToFollow = -1;
        }
        // pickup food
        else {
            if (this.game.VERBOSE)
                System.out.println(12);
            if (this.chefToFollow == -1 && !this.playScreen.chefs[this.playScreen.getChefSelector()].isHolding) {
                if (this.game.VERBOSE)
                    System.out.println(13);
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

    final boolean[] pause = {true};

    class MakeReady implements Runnable {

        @Override
        public void run() {
            try {
                pause[0] = false;
                Thread.sleep(110);
            } catch (Exception e) {
                e.printStackTrace();
            }
            pause[0] = true;
        }
    }

    public void act(float deltaTime) {
        super.act(deltaTime);
        setAnimationPaused(pause[0]);
    }

    @Override
    public String toString() {
        return "FoodActor{" +
                "food=" + food +
                ", chefToFollow=" + chefToFollow +
                '}';
    }
}
