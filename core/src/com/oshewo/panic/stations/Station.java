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
public class Station {

//    private final PiazzaPanic game;
    private PlayScreen playScreen;
    private final StationType type;
    private final Rectangle bounds;
    private final String id;
//    private Ingredients ingredients;
//    private Food food = null;
//    private StationTimer timer;
    private final Stage s;


    /**
     * Instantiates a new Station.
     *
     * @param stationType   the station type
     * @param bounds the bounds
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
//        List<Food> foodOnServing = new ArrayList<>();
//        for (Food food : foods) {
//            if (food.isNotCarried()) {
//                if (this.bounds.overlaps(food.getBoundaryRectangle())) {
//                    foodOnServing.add(food);
//                }
//            }
//        }
//        if (this.ingredients == null) {
//        if (foodId < 0) {
//        if (false) {
            checkForFood();
//        } else if (timer.isComplete()) {
//            output();
//            this.item = null;
//            timerArray.remove(timer);
//        } else {
//            showProgress();
//        }
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
                    if (foodActor.getFood().getState() == IngredientState.UNCUT || foodActor.getFood().getState() == IngredientState.UNCUT_UNCOOKED) {
//                        timers.add(new StationTimer(this.bounds.getX() + (this.bounds.getWidth() - 40) / 2, this.bounds.getY() + this.bounds.getHeight() + 5, 40, 10, this.id, foodActor.getFood(), foodActor.getX(), foodActor.getY(), this.s, 15 * this.playScreen.choppingTimerMultiplier));
                        foodActors.remove(foodActor);
                        foodActor.remove();
                        break;
                    }
                }
                break;
            case STOVE:
                for (FoodActor foodActor : foodOnStation) {
                    if (foodActor.getFood().getState() == IngredientState.UNCOOKED || foodActor.getFood().getState() == IngredientState.HALF_COOKED) {
//                        timers.add(new StationTimer(this.bounds.getX() + (this.bounds.getWidth() - 40) / 2, this.bounds.getY() + this.bounds.getHeight() + 5, 40, 10, this.id, foodActor.getFood(), foodActor.getX(), foodActor.getY(), this.s, 15 * this.playScreen.cookingTimerMultiplier));
                        foodActors.remove(foodActor);
                        foodActor.remove();
                        break;
                    }
                }
                break;
            case SERVING:
                int i = 0;
                for (Customer customer : new ArrayList<>(customers)) {
                    if (i >= 3) break;
                    if (customer.getOrder().getIngredientsRaw().size() > foodOnStation.size())
                        continue;
                    if (customer.getOrder().satisfied(foodOnStation)) {
                        customers.remove(customer);
                        for (Food food : customer.getOrder().getIngredientsRaw())
                            for (FoodActor foodActor : new ArrayList<>(foodOnStation)) {
                                Food food1 = foodActor.getFood();
                                if (food.equals(food1)) {
                                    foodOnStation.remove(foodActor);
                                    foodActors.remove(foodActor);
                                    foodActor.remove();
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
                    break;
                }
                break;
            default:
                System.out.println("Invalid station type - Station.checkForFood()");
        }






//        for (FoodActor foodActor : new ArrayList<>(foodActors)) {
//            if (this.bounds.contains(foodActor.getX(), foodActor.getY()) && foodActor.isNotCarried()) {
//                if (foodActor.getFood().getState() == IngredientState.UNCUT && this.type == StationType.CHOPPING_BOARD) {
//                    foodActors.remove(foodActor);
////                    this.food = foodActor;
//                    timers.add(new StationTimer(this.bounds.getX() + (this.bounds.getWidth() - 40) / 2, this.bounds.getY() + this.bounds.getHeight() + 5, 40, 10, this.id, foodActor.getFood(), foodActor.getX(), foodActor.getY(), this.s, 15));
//                } else if (foodActor.getFood().getState() == IngredientState.UNCOOKED && this.type == StationType.STOVE) {
//                    foodActors.remove(foodActor);
//                    timers.add(new StationTimer(this.bounds.getX() + (this.bounds.getWidth() - 40) / 2, this.bounds.getY() + this.bounds.getHeight() + 5, 40, 10, this.id, foodActor.getFood(), foodActor.getX(), foodActor.getY(), this.s, 15));
////                } else if (this.type == StationType.SERVING) {
////                    int i = 0;
////                    for (Customer customer : new ArrayList<>(customers)) {
////                        if (i >= 3) break;
////                        if (customer.getOrder().satisfied(foodActor)) {
////                            customers.remove(customer);
////                            foodActors.remove(foodActor);
////                            submitOrder();
////                            break;
////                        }
////                        i++;
////                    }
////                } else {
////                    this.ingredients = null;
//                }
//            }
//        }
    }

//    /**
//     * Submit order which finishes current order and restarts hud timer
//     */
//    public void submitOrder() {
//        this.playScreen.incrementOrderCompleted();
//    }

//    /**
//     * Outputs finished food and the correct texture of cooked food.
//     */
//    public void output() {
//        String texture = "";
//        if (this.type == StationType.CHOPPING_BOARD) {
//            texture = choppingOutput();
//        } else if (this.type == StationType.STOVE) {
//            texture = cookingOutput();
//        } else {
//            return;
//        }
//        Food gen = new Food(bounds.getX() - 10, bounds.getY() - 10, , null, null, playScreen, game);
//        Food gen = new Food(new Texture(texture), foodId);
//        gen.setX(bounds.getX() - 10);
//        gen.setY(bounds.getY() - 10);
//    }

//    /**
//     * Returns correct string for the png depending on ID of the foods which can be chopped
//     *
//     * @return the string for the png of the food
//     */
//    public String choppingOutput() {
//        if (foodId == 1) {
//            return "lettuce_chopped.png";
//        } else if (foodId == 2) {
//            return "tomato_chopped.png";
//        } else if (foodId == 3) {
//            return "onion_chopped.png";
//        } else if (foodId == 4) {
//            return "patty.png";
//        }
//        return null;
//    }

//    /**
//     * Returns correct string for the png depending on ID of the foods which can be cooked
//     *
//     * @return the string for the png of the food
//     */
//    public String cookingOutput() {
//        if (this.ingredients == Ingredients.TOP_BUN) {
//            return "top_bun_toasted.png";
//        } else if (this.ingredients == Ingredients.PATTY) {
//            return "patty_cooked.png";
//        }
//        return null;
//    }

    public void updatePlayScreen(PlayScreen playScreen) {
        this.playScreen = playScreen;
    }

//    public Rectangle getBounds() {
//        return this.bounds;
//    }
}
