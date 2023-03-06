package com.oshewo.panic.stations;

import com.badlogic.gdx.math.Rectangle;
import com.oshewo.panic.enums.StationType;
import com.oshewo.panic.sprites.Station;

import static com.oshewo.panic.sprites.CountdownTimer.timerArray;

/**
 * The type Servery.
 * Sets type, id and bounds for the servery station
 *
 * @author Oshewo
 */
public class Servery extends Station {
    /**
     * Instantiates a new Servery.
     *
     * @param type   the type
     * @param bounds the bounds
     */
    public Servery(StationType type, Rectangle bounds) {
        super(type, bounds);
    }
}