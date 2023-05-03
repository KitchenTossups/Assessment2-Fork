package com.oshewo.panic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.oshewo.panic.PiazzaPanic;
import com.oshewo.panic.actor.FoodActor;
import com.oshewo.panic.base.*;
import com.oshewo.panic.enums.*;
import com.oshewo.panic.non_actor.Food;
import com.oshewo.panic.stations.FoodCrate;

import static com.oshewo.panic.lists.Lists.foodActors;

public class FoodChestScreen extends BaseScreen { //Displays Foods ket in the chest
    private final PiazzaPanic game;
    private final PlayScreen playScreen;
    private final TiledMap map;
    private final OrthoCachedTiledMapRenderer renderer;
    private final OrthographicCamera gameCam;
    private final Stage playScreenStage;

    /**
     * Constructor function
     *
     * @param game PiazzaPanic
     * @param playScreen PlayScreen
     * @param playScreenStage PlayScreenStage
     */
    public FoodChestScreen(PiazzaPanic game, PlayScreen playScreen, Stage playScreenStage) {
        this.game = game;
        this.gameCam = new OrthographicCamera(this.game.V_WIDTH, this.game.V_HEIGHT);
//        this.gamePort = new FitViewport(this.game.V_WIDTH, this.game.V_HEIGHT, this.gameCam);
        this.playScreen = playScreen;
        this.playScreenStage = playScreenStage;

        TmxMapLoader mapLoader = new TmxMapLoader();
        this.map = mapLoader.load("piazza-map-big2-chest-screen.tmx");
        this.renderer = new OrthoCachedTiledMapRenderer(this.map);
        this.renderer.render();
        this.gameCam.position.set((this.game.V_WIDTH / 2), (this.game.V_HEIGHT / 2), 0); // 0,0 is apparently in the centre of the screen maybe...

        WorldCreator();

        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Minecraftia-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameters.size = 20;
        fontParameters.color = Color.WHITE;
        fontParameters.borderColor = Color.BLACK;
        fontParameters.borderStraight = true;
        fontParameters.borderWidth = 2;
        fontParameters.minFilter = Texture.TextureFilter.Linear;
        fontParameters.magFilter = Texture.TextureFilter.Linear;

        BitmapFont bitmap = fontGenerator.generateFont(fontParameters);

        bitmap.getData().setScale(1, 1f);

        Label.LabelStyle style = new Label.LabelStyle(bitmap, null);

        Label titleLabel = new Label("Food Chest\n(Click any food item to pick it up)", style);
        titleLabel.setAlignment(Align.center);

        this.uiTable.pad(50);
        this.uiTable.row().height(300);
        this.uiTable.add(new Actor()); // Program will add a space if there is something here, even if it's empty
        this.uiTable.row().height(50);
        this.uiTable.add(titleLabel).center().expandX().pad(10);
        this.uiTable.row().height(50);
    }

    /**
     * Update screen method, used by libgdx
     *
     * @param dt deltaTime
     */
    public void update(float dt) {
        this.gameCam.update();
        this.renderer.setView(this.gameCam);
        this.renderer.render();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (this.game.VERBOSE)
                System.out.println("ESC");
            this.game.setActiveScreen(this.playScreen);
        }
    }
    /**
     * Resizing code for the window
     *
     * @param width width
     * @param height height
     */
    public void resizing(int width, int height) {

    }

    /**
     * World creator creates the object layers from the tmx
     */
    private void WorldCreator() {
        for (MapLayer mapLayer : this.map.getLayers()) {
            switch (TiledAssets.getValueOf(mapLayer.getName())) {
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
                case PIZZA_BASE:
                    InitialiseFoodObject(mapLayer, Ingredients.PIZZA_BASE);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Initialisation of the food object
     *
     * @param mapLayer map layer
     * @param ingredients ingredients
     */
    private void InitialiseFoodObject(MapLayer mapLayer, Ingredients ingredients) {
        for (RectangleMapObject object : mapLayer.getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();
            FoodCrate foodCrate = new FoodCrate(rectangle, super.uiStage, ingredients);
            foodCrate.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    foodActors.add(new FoodActor(0, 0, playScreenStage, new Food(ingredients, ingredients.getDefaultState()), playScreen, game, playScreen.getChefSelector()));
                    game.setActiveScreen(playScreen);
                }
            });
        }
    }

    /**
     * Disposal of the screen
     */
    public void disposing() {
        this.map.dispose();
    }
}
