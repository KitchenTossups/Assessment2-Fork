package com.oshewo.panic.tools;

import com.oshewo.panic.PiazzaPanic;
import com.oshewo.panic.enums.*;
import com.oshewo.panic.non_actor.*;
import com.oshewo.panic.screens.*;

import java.util.ArrayList;
import java.util.Date;

import static com.oshewo.panic.screens.PlayScreen.orderHud;
import static com.oshewo.panic.lists.Lists.customers;

/**
 * The type Order system.
 *
 * @author sl3416, Oshewo
 */
public class OrderSystem {      // Initialises order process

    private final PiazzaPanic game;
    private PlayScreen playScreen;
    private final CustomerCreator customerCreator;
    private final int timeCustomerLeaves;

    /**
     * Instantiates a new Order system.
     * Creates recipes
     */
    public OrderSystem(PlayScreen playScreen, PiazzaPanic game) {
        this.playScreen = playScreen;
        this.game = game;

        this.customerCreator = new CustomerCreator();
        this.customerCreator.start();

        switch (game.DIFFICULTY) {
            case EASY:
                this.timeCustomerLeaves = 240;
                break;
            case MEDIUM:
                this.timeCustomerLeaves = 210;
                break;
            case HARD:
                this.timeCustomerLeaves = 180;
                break;
            default:
                this.timeCustomerLeaves = -1;
                System.out.println("Error difficulty");
        }
    }

    /**
     * Generate order randomly.
     *
     * @return the order
     */
    public Recipe generateOrder() {
//        return new Recipe(Product.CHEESEBURGER);
        return new Recipe(Product.getRandomProduct());
    }

    /**
     * Updates order hud
     */
    public void update() {
        StringBuilder sb = new StringBuilder();
        if (customers.size() != 0) {
            for (Customer customer : new ArrayList<>(customers)) {
                if (((new Date().getTime()) - customer.getOrderPlaced()) / 1000 > this.timeCustomerLeaves && !customer.isPenalty()) {
                    customer.setPenalty();
                    this.playScreen.hud.reduceLives();
                    customers.remove(customer);
                }
            }
            if (this.playScreen.hud.getLives() <= 0) {
                this.customerCreator.kill();
                this.game.RUNNING = false;
                if (this.game.VERBOSE) {
                    System.out.println("Fail!");
                }
                this.game.setActiveScreen(new EndScreen(this.game, false, this.playScreen.getOrdersCompleted(), this.playScreen.getBinnedItems()));
            }
            for (int i = 0; i < Math.min(3, customers.size()); i++)
                sb.append(customers.get(i).getOrder().getEndProduct().toString()).append("\n").append(customers.get(i).getOrder().getIngredients()).append("\n");
        } else
            sb.append("CONGRATULATIONS!").append("\n").append("    ").append("You've completed").append("\n").append("    ").append("level 1!");
        orderHud.getLabel().setText(sb.toString());
    }

    private class CustomerCreator implements Runnable {
        private Thread t;
        private boolean run = true;

        CustomerCreator() {
            if (game.VERBOSE) System.out.println("Starting CustomerCreator");
        }

        public void kill() {
            run = false;
            t.interrupt();
        }

        public void run() {
            if (game.VERBOSE) System.out.println("Running CustomerCreator");
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
                    time = 30;
                    customerNum = 8;
                    break;
                default:
                    System.out.println("Error difficulty");
                    return;
            }

            switch (game.MODE) {
                case SCENARIO:
                    for (int i = 0; i < customerNum; i++) {
                        if (this.run) {
                            Customer customer = new Customer(generateOrder());
                            customers.add(customer);
                            if (game.VERBOSE) System.out.println(customer);
                            if (i + 1 == customerNum)
                                break;
                            try {
                                Thread.sleep(time * 1000);
                            } catch (Exception e) {
                                if (!this.run)
                                    System.out.println("CustomerCreate ended!");
                                else
                                    e.printStackTrace();
                            }
                        } else
                            break;
                    }
                    break;
                case ENDLESS:
                    while (this.run) {
                        Customer customer = new Customer(generateOrder());
                        customers.add(customer);
                        if (game.VERBOSE) System.out.println(customer);
                        try {
                            Thread.sleep(time * 1000);
                        } catch (Exception e) {
                            if (!this.run)
                                System.out.println("CustomerCreate ended!");
                            else
                                e.printStackTrace();
                        }
                    }
            }
            if (game.VERBOSE) System.out.println("Finished CustomerCreate");
        }

        public void start() {
            if (this.t == null) {
                this.t = new Thread(this);
                this.t.start();
            }
        }
    }

    public void updatePlayScreen(PlayScreen playScreen) {
        this.playScreen = playScreen;
    }
}
