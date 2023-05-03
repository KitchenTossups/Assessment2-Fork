package com.oshewo.panic.stations;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.oshewo.panic.actor.*;
import com.oshewo.panic.enums.*;
import com.oshewo.panic.non_actor.*;
import com.oshewo.panic.screens.*;

import java.util.*;

import static com.oshewo.panic.lists.Lists.*;

/**
 * The type Station.
 * Sets methods for all stations - prep stations and servery
 *
 * @author Oshewo
 */
public class Station {             // Initialises the stations

    private PlayScreen playScreen;
    private final StationType type;
    private final Rectangle bounds;
    private final String id;
    private final Stage s;

    /**
     * Instantiates a new Station.
     *
     * @param stationType the station type
     * @param bounds      the bounds
     */
    public Station(StationType stationType, Rectangle bounds, PlayScreen playScreen, Stage s) {
        this.type = stationType;
        this.bounds = bounds;
        this.playScreen = playScreen;
        this.s = s;
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Updates whether food has been served and if it is being prepped
     */
    public void update(PlayScreen playScreen) {
        updatePlayScreen(playScreen);
        checkForFood();
    }

    /**
     * Check whether food has been placed on a specific station then if so, starts timer for prepping
     */
    public void checkForFood() {
        for (StationTimer stationTimer : timers)
            if (stationTimer.getStationId().equals(this.id))
                return;

        List<FoodActor> foodOnStation = new ArrayList<>();

        for (FoodActor foodActor : foodActors)
            if (foodActor.isNotCarried())
                if (this.bounds.overlaps(foodActor.getBoundaryRectangle()))
                    foodOnStation.add(foodActor);

        if (foodOnStation.size() == 0)
            return;

        switch (this.type) {
            case CHOPPING_BOARD:
                for (FoodActor foodActor : foodOnStation) {
                    if (foodActor.getFood().getState() == IngredientState.UNCUT || foodActor.getFood().getState() == IngredientState.UNCUT_UNCOOKED || foodActor.getFood().getState() == IngredientState.COOKED_UNCUT) {
                        timers.add(new StationTimer(this.bounds.getX() + (this.bounds.getWidth() - 40) / 2, this.bounds.getY() + this.bounds.getHeight() + 5, 40, 10, this.id, foodActor.getFood(), foodActor.getX(), foodActor.getY(), this.s, 15 * this.playScreen.choppingTimerMultiplier, false));
                        foodActors.remove(foodActor);
                        foodActor.remove();
                        break;
                    }
                }
                break;
            case STOVE:
                for (FoodActor foodActor : foodOnStation) {
                    if (foodActor.getFood().getState() == IngredientState.UNCOOKED || foodActor.getFood().getState() == IngredientState.HALF_COOKED) {
                        if (stoveFood(foodActor.getFood())) {
                            timers.add(new StationTimer(this.bounds.getX() + (this.bounds.getWidth() - 40) / 2, this.bounds.getY() + this.bounds.getHeight() + 5, 40, 10, this.id, foodActor.getFood(), foodActor.getX(), foodActor.getY(), this.s, 15 * this.playScreen.cookingTimerMultiplier, true));
                            foodActors.remove(foodActor);
                            foodActor.remove();
                            break;
                        }
                    }
                }
                break;
            case OVEN:
                for (FoodActor foodActor : foodOnStation) {
                    if (foodActor.getFood().getState() == IngredientState.UNCOOKED || foodActor.getFood().getState() == IngredientState.HALF_COOKED) {
                        if (ovenFood(foodActor.getFood())) {
                            timers.add(new StationTimer(this.bounds.getX() + (this.bounds.getWidth() - 40) / 2, this.bounds.getY() + this.bounds.getHeight() + 5, 40, 10, this.id, foodActor.getFood(), foodActor.getX(), foodActor.getY(), this.s, 15 * this.playScreen.cookingTimerMultiplier, true));
                            foodActors.remove(foodActor);
                            foodActor.remove();
                            break;
                        }
                    }
                }
                break;
            case SERVING:
                int i = 0;
//                System.out.println(0);
                for (Customer customer : new ArrayList<>(customers)) {
//                    System.out.println(1);
                    if (i >= 3) break;
//                    System.out.println(2);
                    if (customer.getOrder().getIngredientsRaw().size() > foodOnStation.size()) {
//                        System.out.println(3);
                        continue;
                    }
//                    System.out.println(4);
//                    System.out.println(foodOnStation);
//                    System.out.println(customer.getOrder());
                    if (customer.getOrder().satisfied(foodOnStation)) {
//                        System.out.println(5);
                        customers.remove(customer);
                        for (Food food : customer.getOrder().getIngredientsRaw())
                            for (FoodActor foodActor : new ArrayList<>(foodOnStation)) {
//                                System.out.println(6);
                                Food food1 = foodActor.getFood();
                                if (food.toString().equals(food1.toString())) {
//                                    System.out.println(7);
                                    foodOnStation.remove(foodActor);
                                    foodActors.remove(foodActor);
                                    foodActor.remove();
                                    this.playScreen.incrementOrderCompleted();
                                    break;
                                }
                            }
                        this.playScreen.incrementOrderCompleted();
                        break;
                    }
                    i++;
                }
                break;
            case BIN:
                for (FoodActor foodActor : foodOnStation) {
                    foodActors.remove(foodActor);
                    foodActor.remove();
                    this.playScreen.incrementBinnedItems();
                    break;
                }
                break;
            default:
                System.out.println("Invalid station type - Station.checkForFood()");
        }
    }

    private boolean stoveFood(Food food) {
        switch (food.getItem()) {
            case PATTY:
            case BEANS:
                switch (food.getState()) {
                    case UNCOOKED:
                    case HALF_COOKED:
                        return true;
                    default:
                        return false;
                }
            default:
                return false;
        }
    }

    private boolean ovenFood(Food food) {
        switch (food.getItem()) {
            case BUN:
            case PIZZA_BASE:
            case JACKET:
                switch (food.getState()) {
                    case UNCOOKED:
                    case HALF_COOKED:
                        return true;
                    default:
                        return false;
                }
            default:
                return false;
        }
    }

    public String getId() {
        return id;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void updatePlayScreen(PlayScreen playScreen) {
        this.playScreen = playScreen;
    }
}
