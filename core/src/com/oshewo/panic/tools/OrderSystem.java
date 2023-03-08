package com.oshewo.panic.tools;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.oshewo.panic.enums.*;
import com.oshewo.panic.non_sprite.*;

import java.util.*;

import static com.oshewo.panic.screens.PlayScreen.orderHud;

/**
 * The type Order system.
 *
 * @author sl3416, Oshewo
 */
public class OrderSystem {

    private static final List<Customer> customers = new ArrayList<>();
    private com.badlogic.gdx.utils.Timer timer;
    private final GameMode mode;

    /**
     * Instantiates a new Order system.
     * Creates recipes
     */
    public OrderSystem(GameMode mode) {
        this.mode = mode;
    }

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
        final Label label = orderHud.getLabel();
        timer.scheduleTask(new com.badlogic.gdx.utils.Timer.Task() {
            @Override
            public void run() {

            }
        }, 1);

        timer.scheduleTask(new com.badlogic.gdx.utils.Timer.Task() {
            @Override
            public void run() {
                StringBuilder sb = new StringBuilder();
                if (customers.size() != 0)
                    for (int i = 0; i < Math.min(3, customers.size()); i++)
                        sb.append(customers.get(i).getOrder().getEndProduct().toString()).append("\n").append(customers.get(i).getOrder().getIngredients());
                else
                    sb.append("CONGRATULATIONS!").append("\n").append("    ").append("You've completed").append("\n").append("    ").append("level 1!");
                label.setText(sb.toString());
            }
        }, 1);
    }
}
