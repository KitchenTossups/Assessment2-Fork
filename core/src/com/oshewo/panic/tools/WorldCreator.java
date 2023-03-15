package com.oshewo.panic.tools;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.oshewo.panic.PiazzaPanic;
import com.oshewo.panic.base.BaseActor;
import com.oshewo.panic.enums.*;
import com.oshewo.panic.screens.PlayScreen;
import com.oshewo.panic.sprites.Station;
import com.oshewo.panic.stations.FoodCrate;


import java.util.*;

/**
 * The type World creator.
 * Sets collisions and gets stations
 *
 * @author Oshewo, sl3416
 */
public class WorldCreator {

    public static Set<BaseActor> wallArray = new HashSet<>();
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
    public WorldCreator(World world, TiledMap map, PlayScreen playScreen, Stage s, PiazzaPanic game) {
        this.world = world;
        this.map = map;

        for (MapLayer mapLayer : map.getLayers()) {
            if (mapLayer.getName().equals(TiledAssets.WALLS.getLayerName())) {
                InitialiseWalls(mapLayer, s);
            } else if (mapLayer.getName().equals(TiledAssets.STOVES.getLayerName())) {
                InitialiseStoves(mapLayer, playScreen, game);
            } else if (mapLayer.getName().equals(TiledAssets.CHOPPING_BOARD.getLayerName())) {
                InitialiseChoppingCounter(mapLayer, playScreen, game);
            } else if (mapLayer.getName().equals(TiledAssets.SERVING_STATION.getLayerName())) {
                InitialiseServiceStation(mapLayer, playScreen, game);
            } else if (mapLayer.getName().equals(TiledAssets.LETTUCE.getLayerName())) {
                InitialiseFoodObject(mapLayer, Item.LETTUCE, playScreen, game);
            } else if (mapLayer.getName().equals(TiledAssets.TOMATO.getLayerName())) {
                InitialiseFoodObject(mapLayer, Item.TOMATO, playScreen, game);
            } else if (mapLayer.getName().equals(TiledAssets.ONION.getLayerName())) {
                InitialiseFoodObject(mapLayer, Item.ONION, playScreen, game);
            } else if (mapLayer.getName().equals(TiledAssets.PATTY.getLayerName())) {
                InitialiseFoodObject(mapLayer, Item.PATTY, playScreen, game);
            } else if (mapLayer.getName().equals(TiledAssets.BUNS.getLayerName())) {
                InitialiseFoodObject(mapLayer, Item.TOP_BUN, playScreen, game);
            }
        }
    }

    private void InitialiseWalls(MapLayer mapLayer, Stage s) {
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();

        for (RectangleMapObject object : mapLayer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2);

            body = world.createBody(bodyDef);

            shape.setAsBox(rectangle.getWidth() / 2, rectangle.getHeight() / 2);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
            BaseActor baseActor = new BaseActor(rectangle.x, rectangle.y - 50, s);
            baseActor.setSize(rectangle.width, rectangle.height);
            baseActor.setBoundaryRectangle();
            wallArray.add(baseActor);
        }
    }

    private void InitialiseStoves(MapLayer mapLayer, PlayScreen playScreen, PiazzaPanic game) {
        for (RectangleMapObject object : mapLayer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();
            stoveArray.add(new Station(StationType.STOVE, rectangle, playScreen, game));
        }
    }

    private void InitialiseChoppingCounter(MapLayer mapLayer, PlayScreen playScreen, PiazzaPanic game) {
        for (RectangleMapObject object : mapLayer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();
            boardArray.add(new Station(StationType.CHOPPING_BOARD, rectangle, playScreen, game));
        }
    }

    private void InitialiseServiceStation(MapLayer mapLayer, PlayScreen playScreen, PiazzaPanic game) {
        for (RectangleMapObject object : mapLayer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();
            servingArray.add(new Station(StationType.SERVING, rectangle, playScreen, game));
        }
    }

    private void InitialiseFoodObject(MapLayer mapLayer, Item item, PlayScreen playScreen, PiazzaPanic game) {
        for (RectangleMapObject object : mapLayer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();
            crateArray.add(new FoodCrate(rectangle, item, playScreen, game));
        }
    }
}