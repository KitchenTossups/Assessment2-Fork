package com.oshewo.panic.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

public class Station extends InteractiveTileObject{
    private String type;
    private String contents;

    public Station(World world, TiledMap map, Rectangle bounds){
        super(world, map, bounds);
    }
}
