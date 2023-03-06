package com.oshewo.panic.tools;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.oshewo.panic.enums.Ingredients;
import com.oshewo.panic.enums.TiledAssets;
import com.oshewo.panic.sprites.Station;
import com.oshewo.panic.stations.FoodCrate;
import com.oshewo.panic.stations.Servery;


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
    public World world;
    public TiledMap map;
    public Body body;

    /**
     * Instantiates a new World creator.
     *
     * @param world the world
     * @param map   the map
     */
    public WorldCreator(World world, TiledMap map) {
        this.world = world;
        this.map = map;

        for (MapLayer mapLayer : map.getLayers()) {
            if (mapLayer.getName().equals(TiledAssets.WALLS.getLayerName())) {
                InitialiseWalls(mapLayer);
            } else if (mapLayer.getName().equals(TiledAssets.STOVES.getLayerName())) {
                InitialiseStoves(mapLayer);
            } else if (mapLayer.getName().equals(TiledAssets.CHOPPING_BOARD.getLayerName())) {
                InitialiseChoppingCounter(mapLayer);
            } else if (mapLayer.getName().equals(TiledAssets.SERVING_STATION.getLayerName())) {
                InitialiseServiceStation(mapLayer);
            } else if (mapLayer.getName().equals(TiledAssets.LETTUCE.getLayerName())) {
                InitialiseFoodObject(mapLayer, Ingredients.LETTUCE);
            } else if (mapLayer.getName().equals(TiledAssets.TOMATO.getLayerName())) {
                InitialiseFoodObject(mapLayer, Ingredients.TOMATO);
            } else if (mapLayer.getName().equals(TiledAssets.ONION.getLayerName())) {
                InitialiseFoodObject(mapLayer, Ingredients.ONION);
            } else if (mapLayer.getName().equals(TiledAssets.PATTY.getLayerName())) {
                InitialiseFoodObject(mapLayer, Ingredients.PATTY);
            } else if (mapLayer.getName().equals(TiledAssets.BUNS.getLayerName())) {
                InitialiseFoodObject(mapLayer, Ingredients.BUN);
            }
        }
    }

    private void InitialiseWalls(MapLayer mapLayer) {
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        for (RectangleMapObject object : mapLayer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2);

            body = world.createBody(bdef);

            shape.setAsBox(rectangle.getWidth() / 2, rectangle.getHeight() / 2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }

    private void InitialiseStoves(MapLayer mapLayer) {
        int id = 0;
        for (RectangleMapObject object : mapLayer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();
            stoveArray.add(new Station("stove", id, rectangle));
            id++;
        }
    }

    private void InitialiseChoppingCounter(MapLayer mapLayer) {
        int id = 0;
        for (RectangleMapObject object : mapLayer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();
            boardArray.add(new Station("board", id, rectangle));
            id++;
        }
    }

    private void InitialiseServiceStation(MapLayer mapLayer) {
        int id = 0;
        for (RectangleMapObject object : mapLayer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();
            servingArray.add(new Servery("service", id, rectangle));
            id++;
        }
    }

    private void InitialiseFoodObject(MapLayer mapLayer, Ingredients ingredient) {
        for (RectangleMapObject object : mapLayer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();
            crateArray.add(new FoodCrate(rectangle, ingredient));
        }
    }
}