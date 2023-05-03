package com.oshewo.panic.lists;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.oshewo.panic.actor.*;
import com.oshewo.panic.base.*;
import com.oshewo.panic.non_actor.Customer;
import com.oshewo.panic.stations.*;

import java.util.*;

public class Lists {
    // Object lists
    public static final List<BaseActor> walls = new ArrayList<>();
    public static final List<Station> stoves = new ArrayList<>();
    public static final List<Station> bins = new ArrayList<>();
    public static final List<Station> ovens = new ArrayList<>();
    public static final List<Station> choppingBoards = new ArrayList<>();
    public static final List<Station> servingStations = new ArrayList<>();
    public static final List<FoodCrateBox> foodCrateBoxes = new ArrayList<>();
    public static final List<StationTimer> timers = new ArrayList<>();
    public static final List<FoodActor> foodActors = new ArrayList<>();
    public static final List<Customer> customers = new ArrayList<>();
    public static final HashMap<String, Label> attentionLabels = new HashMap<>();
}
