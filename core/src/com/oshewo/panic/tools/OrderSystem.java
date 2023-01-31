package com.oshewo.panic.tools;

import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.*;

import static com.oshewo.panic.screens.PlayScreen.currentOrder;
import static com.oshewo.panic.screens.PlayScreen.orderHud;

public class OrderSystem {
    private RandomXS128 random;
    public static HashMap<String, List<String>> recipes;
    public static Queue<Object> orders = new LinkedList<>();
    private final int orderId;
    private com.badlogic.gdx.utils.Timer timer;

    public OrderSystem() {
        random = new RandomXS128();
        recipes = new HashMap<String, List<String>>();

        // burger
        recipes.put("Burger", Arrays.asList("bun, toasted", "beef, formed then fried"));

        // salad
        recipes.put("Salad", Arrays.asList("lettuce, chopped", "tomato, chopped", "onion, chopped"));
        orderId = 1;
    }

    public static HashMap<String, List<String>> getRecipes() {
        return recipes;
    }

    public Order generateOrder() {
        Set<String> recipeTypes = recipes.keySet();
        int recipeSize = recipeTypes.size();
        int randomIndex = new Random().nextInt(recipeSize);
        String recipeType = (String)recipeTypes.toArray()[randomIndex];
        return new Order(recipeType);
    }

    public void update(){
        timer = new com.badlogic.gdx.utils.Timer();
        final Label recipeLabel = orderHud.getRecipeLabel();
        final Label ingredient1Label = orderHud.getIngredient1Label();
        final Label ingredient2Label = orderHud.getIngredient2Label();
        final Label ingredient3Label = orderHud.getIngredient3Label();

        timer.scheduleTask(new com.badlogic.gdx.utils.Timer.Task() {

            @Override
            public void run() {
                if(currentOrder!=null) {
                    recipeLabel.setText(currentOrder.getRecipeType().toString());
                    ;

                    switch (currentOrder.getRecipeType()) {
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
                }
                else{
                    recipeLabel.setText("CONGRATULATIONS!");
                    ingredient1Label.setText("You've completed");
                    ingredient2Label.setText("level 1!");
                    ingredient3Label.setText("");
                }
            }
        }, 1);

    }
}
