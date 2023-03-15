package com.oshewo.panic.tools;

import com.oshewo.panic.PiazzaPanic;
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
    private final PiazzaPanic game;

    /**
     * Instantiates a new Order system.
     * Creates recipes
     */
    public OrderSystem(PiazzaPanic game) {
        this.game = game;

        CustomerCreator customerCreator = new CustomerCreator();
        customerCreator.start();
    }

    /**
     * Generate order randomly.
     *
     * @return the order
     */
    public Recipe generateOrder() {
        return new Recipe(Product.DOUBLE_CHEESEBURGER);
//        return new Recipe(Product.getRandomProduct());
    }

    /**
     * Updates order hud
     */
    public void update() {
        StringBuilder sb = new StringBuilder();
        if (customers.size() != 0)
            for (int i = 0; i < Math.min(3, customers.size()); i++)
                sb.append(customers.get(i).getOrder().getEndProduct().toString()).append("\n").append(customers.get(i).getOrder().getIngredients()).append("\n");
        else
            sb.append("CONGRATULATIONS!").append("\n").append("    ").append("You've completed").append("\n").append("    ").append("level 1!");
        orderHud.getLabel().setText(sb.toString());
    }

    private class CustomerCreator implements Runnable {
        private Thread t;

        CustomerCreator() {
            if (game.isVerbose()) System.out.println("Starting CustomerCreator");
        }

        public void run() {
            if (game.isVerbose()) System.out.println("Running CustomerCreator");
            int time, customerNum;

            switch (game.DIFFICULTY) {
                case EASY:
                    time = 60;
                    customerNum = 3;
                    break;
                case MEDIUM:
                    time = 45;
                    customerNum = 5;
                    break;
                case HARD:
                    time = 5; // testing value only
                    customerNum = 8;
                    break;
                default:
                    System.out.println("Error difficulty");
                    return;
            }

            switch (game.MODE) {
                case SCENARIO:
                    for (int i = 0; i < customerNum; i++) {
                        Customer customer = new Customer(generateOrder(), game.worldTimer);
                        customers.add(customer);
                        if (game.isVerbose()) System.out.println(customer);
                        if (i + 1 == customerNum)
                            break;
                        try {
                            Thread.sleep(time * 1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case ENDLESS:
                    break;
            }
            if (game.isVerbose()) System.out.println("Finished CustomerCreate");
        }

        public void start() {
            if (t == null) {
                t = new Thread(this);
                t.start();
            }
        }
    }
}
