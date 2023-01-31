package com.oshewo.panic.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.oshewo.panic.sprites.Station;
import com.oshewo.panic.stations.FoodCrate;
import com.oshewo.panic.stations.Servery;


import java.util.HashSet;
import java.util.Set;

public class WolrdCreator {
    public static Set<Station> stoveArray = new HashSet<>();
    public static Set<Station> boardArray = new HashSet<>();
    public static Set<Station> servingArray = new HashSet<>();
    public static Set<FoodCrate> crateArray = new HashSet<>();

    public WolrdCreator(World world, TiledMap map){

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        // object collision detector
        // walls
       for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight()/2);

            body = world.createBody(bdef);

            shape.setAsBox(rectangle.getWidth()/2,rectangle.getHeight()/2);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

       // stoves
        int id = 0;
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            stoveArray.add(new Station("stove",id,rectangle));
            id++;

        }

        // chopping counter
        id = 0;
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            boardArray.add(new Station("board",id,rectangle));
            id++;
        }

        // Service Station
        id = 0;
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            servingArray.add(new Servery("service",id,rectangle));
            id++;
        }

        // Food Boxes
        for(int i = 1; i<=5; i++){
            for(MapObject object : map.getLayers().get(i+7).getObjects().getByType(RectangleMapObject.class)) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                crateArray.add(new FoodCrate(rectangle, i));
            }
        }
    }
}
