package com.oshewo.panic.stations;

import com.badlogic.gdx.math.Rectangle;
import com.oshewo.panic.sprites.Station;

/**
 * The type Prep station.
 * Sets type, id and bounds for a prep station
 *
 * @author Oshewo
 */
public class PrepStation extends Station {
    /**
     * Instantiates a new Prep station.
     *
     * @param type   the type
     * @param id     the id
     * @param bounds the bounds
     */
    public PrepStation(String type, int id, Rectangle bounds) {
        super(type, id, bounds);
    }
}
