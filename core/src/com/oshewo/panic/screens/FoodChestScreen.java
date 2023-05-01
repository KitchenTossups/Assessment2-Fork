package com.oshewo.panic.screens;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.*;
import com.oshewo.panic.PiazzaPanic;
import com.oshewo.panic.base.*;
import com.oshewo.panic.enums.*;
import com.oshewo.panic.stations.FoodCrate;

import static com.oshewo.panic.lists.Lists.*;

public class FoodChestScreen extends BaseScreen {
    private final PiazzaPanic game;
    private final PlayScreen playScreen;
    private final TiledMap map;
    private final OrthoCachedTiledMapRenderer renderer;
    private final OrthographicCamera gameCam;
    private final Viewport gamePort;

    public FoodChestScreen(PiazzaPanic game, PlayScreen playScreen) {
        this.game = game;
        this.gameCam = new OrthographicCamera(this.game.V_WIDTH, this.game.V_HEIGHT);
        this.gamePort = new FitViewport(this.game.V_WIDTH, this.game.V_HEIGHT, this.gameCam);
        this.playScreen = playScreen;

        TmxMapLoader mapLoader = new TmxMapLoader();
        this.map = mapLoader.load("piazza-map-big2-chest-screen.tmx");
        this.renderer = new OrthoCachedTiledMapRenderer(this.map);
        this.renderer.render();
        this.gameCam.position.set((this.game.V_WIDTH / 2), (this.game.V_HEIGHT / 2), 0); // 0,0 is apparently in the centre of the screen maybe...

        WorldCreator();
    }

    public void update(float dt) {
        this.gameCam.update();
        this.renderer.setView(this.gameCam);
        this.renderer.render();
    }

    public void resizing(int width, int height) {

    }

    public void disposing() {

    }

    private void WorldCreator() {
        for (MapLayer mapLayer : this.map.getLayers()) {
            switch (TiledAssets.getValueOf(mapLayer.getName())) {
                case TEXT:
                    break;
                case LETTUCE:
                    InitialiseFoodObject(mapLayer, Ingredients.LETTUCE);
                    break;
                case TOMATO:
                    InitialiseFoodObject(mapLayer, Ingredients.TOMATO);
                    break;
                case ONION:
                    InitialiseFoodObject(mapLayer, Ingredients.ONION);
                    break;
                case PATTY:
                    InitialiseFoodObject(mapLayer, Ingredients.PATTY);
                    break;
                case BUNS:
                    InitialiseFoodObject(mapLayer, Ingredients.BUN);
                    break;
                case PEPERONI:
                    InitialiseFoodObject(mapLayer, Ingredients.PEPERONI);
                    break;
                case BEANS:
                    InitialiseFoodObject(mapLayer, Ingredients.BEANS);
                    break;
                case JACKET:
                    InitialiseFoodObject(mapLayer, Ingredients.JACKET);
                    break;
                case MOZZARELLA:
                    InitialiseFoodObject(mapLayer, Ingredients.MOZZARELLA);
                    break;
                case PIZZA_BASE:
                    InitialiseFoodObject(mapLayer, Ingredients.PIZZA_BASE);
                    break;
                default:
                    break;
            }
        }
    }

    private void InitialiseFoodObject(MapLayer mapLayer, Ingredients ingredients) {
        for (RectangleMapObject object : mapLayer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();
            foodCrates.add(new FoodCrate(rectangle, ingredients, this.playScreen, this.game));
        }
    }

    public void dispose() {
        this.map.dispose();
    }
}
