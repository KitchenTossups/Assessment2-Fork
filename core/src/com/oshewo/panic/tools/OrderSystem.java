package com.oshewo.panic.tools;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.oshewo.panic.enums.Product;
import com.oshewo.panic.non_sprite.Recipe;

import java.util.*;

import static com.oshewo.panic.screens.PlayScreen.currentOrder;
import static com.oshewo.panic.screens.PlayScreen.orderHud;

/**
 * The type Order system.
 *
 * @author sl3416, Oshewo
 */
public class OrderSystem {
//    public static HashMap<String, List<String>> recipes;
//    public static Queue<Object> orders = new LinkedList<>();
    private static final List<Recipe> orders = new ArrayList<>();
//    private final int orderId;
    private com.badlogic.gdx.utils.Timer timer;

    /**
     * Instantiates a new Order system.
     * Creates recipes
     */
    public OrderSystem() {
//        recipes = new HashMap<>();
//
//        // burger
//        recipes.put("Burger", Arrays.asList("bun, toasted", "beef, formed then fried"));
//
//        // salad
//        recipes.put("Salad", Arrays.asList("lettuce, chopped", "tomato, chopped", "onion, chopped"));
//        orderId = 1;
    }

//    /**
//     * Gets recipes.
//     *
//     * @return the recipes
//     */
//    public static HashMap<String, List<String>> getRecipes() {
//        return recipes;
//    }

    /**
     * Generate order randomly.
     *
     * @return the order
     */
    public Recipe generateOrder() {
        return new Recipe(Product.getRandomProduct());
    }

    /**
     * Updates order hud
     */
    public void update() {
        timer = new com.badlogic.gdx.utils.Timer();
        final Label recipeLabel = orderHud.getRecipeLabel();
        final Label ingredient1Label = orderHud.getIngredient1Label();
        final Label ingredient2Label = orderHud.getIngredient2Label();
        final Label ingredient3Label = orderHud.getIngredient3Label();

        timer.scheduleTask(new com.badlogic.gdx.utils.Timer.Task() {

            @Override
            public void run() {
                if (currentOrder != null) {
                    recipeLabel.setText(currentOrder.getEndProduct());

                    switch (currentOrder.getEndProduct()) {
                        case "Burger":
                            ingredient1Label.setText("bun");
                            ingredient2Label.setText("patty");
                            ingredient3Label.setText("");
                            break;
                        case "Salad":
                            ingredient1Label.setText("lettuce");
                            ingredient2Label.setText("tomato");
                            ingredient3Label.setText("onion");
                            break;
                    }
                } else {
                    recipeLabel.setText("CONGRATULATIONS!");
                    ingredient1Label.setText("You've completed");
                    ingredient2Label.setText("level 1!");
                    ingredient3Label.setText("");
                }
            }
        }, 1);
    }
}
