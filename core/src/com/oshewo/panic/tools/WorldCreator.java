package com.oshewo.panic.tools;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.oshewo.panic.sprites.Station;
import com.oshewo.panic.stations.*;


import java.util.*;

/**
 * The type World creator.
 * Sets collisions and gets stations
 *
 * @author Oshewo, sl3416
 */
public class WorldCreator {
    public static Set<Station> stoveArray = new HashSet<>();
    public static Set<Station> boardArray = new HashSet<>();
    public static Set<Station> servingArray = new HashSet<>();
    public static Set<FoodCrate> crateArray = new HashSet<>();

    /**
     * Instantiates a new World creator.
     *
     * @param world the world
     * @param map   the map
     */
    public WorldCreator(World world, TiledMap map) {

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        // object collision detector
        // walls
        for (RectangleMapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2);

            body = world.createBody(bdef);

            shape.setAsBox(rectangle.getWidth() / 2, rectangle.getHeight() / 2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        // stoves
        int id = 0;
        for (RectangleMapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();
            stoveArray.add(new Station("stove", id, rectangle));
            id++;
        }

        // chopping counter
        id = 0;
        for (RectangleMapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();
            boardArray.add(new Station("board", id, rectangle));
            id++;
        }

        // Service Station
        id = 0;
        for (RectangleMapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();
            servingArray.add(new Servery("service", id, rectangle));
            id++;
        }

        // Food Boxes
        for (int i = 0; i <= map.getLayers().size() - 9; i++) {
            for (RectangleMapObject object : map.getLayers().get(i + 8).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rectangle = object.getRectangle();
                crateArray.add(new FoodCrate(rectangle, i));
            }
        }
    }
}
