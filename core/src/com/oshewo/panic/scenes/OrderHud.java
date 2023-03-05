package com.oshewo.panic.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oshewo.panic.PiazzaPanic;

/**
 * The Order Hud displays the info of recipe name and ingredients of current order to do on the order sheet
 *
 * @author sl3416
 */
public class OrderHud implements Disposable {

    PiazzaPanic game;
    public Stage stage;

    Label recipeLabel;
    Label ingredient1Label;
    Label ingredient2Label;
    Label ingredient3Label;


    /**
     * Instantiates a new Order hud by setting up the order labels, images as actors in a table to display on screen
     *
     * @param sb the spritebatch to draw multiple sprites at once
     */
    public OrderHud(SpriteBatch sb) {

        Viewport viewport = new FitViewport(PiazzaPanic.V_WIDTH, PiazzaPanic.V_WIDTH, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        // Order Hud Setup
        // Receipt image
        // variables for order - order image, recipe and ingredient labels for recipe
        Image order_receipt = new Image(new Texture(Gdx.files.internal("order_receipt.png")));
        order_receipt.setPosition(25, 400);
        order_receipt.setSize(300, 600);

        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("arcadeclassic.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameters.size = 36;
        fontParameters.color = Color.BLACK;
//        fontParameters.borderWidth = 2;
        fontParameters.borderColor = Color.WHITE;
        fontParameters.borderStraight = true;
        fontParameters.minFilter = Texture.TextureFilter.Linear;
        fontParameters.magFilter = Texture.TextureFilter.Linear;

        BitmapFont bitmap = fontGenerator.generateFont(fontParameters);

        Label.LabelStyle style = new Label.LabelStyle(bitmap, Color.BLACK);

        // Creating order labels
        recipeLabel = new Label("", style);
        recipeLabel.setPosition(110, 750);
        recipeLabel.setSize(150, 150);

        ingredient1Label = new Label("", style);
        ingredient1Label.setPosition(125, 710);
        ingredient1Label.setSize(150, 150);

        ingredient2Label = new Label("", style);
        ingredient2Label.setPosition(125, 670);
        ingredient2Label.setSize(150, 150);

        ingredient3Label = new Label("", style);
        ingredient3Label.setPosition(125, 630);
        ingredient3Label.setSize(150, 150);

        // Lays out recipe and ingredients label onto the order image in a table
        Table image_table = new Table();
        image_table.addActor(order_receipt);

        Table labels_table = new Table();
        labels_table.addActor(recipeLabel);
        labels_table.addActor(ingredient1Label);
        labels_table.addActor(ingredient2Label);
        labels_table.addActor(ingredient3Label);

        Stack stack = new Stack();
        stack.add(image_table);
        stack.add(labels_table);

        stage.addActor(stack);
    }

    /**
     * Get recipe label.
     *
     * @return the label for recipe of order
     */
    public Label getRecipeLabel() {
        return recipeLabel;
    }

    /**
     * Get ingredient 1 label of order recipe.
     *
     * @return the label for ingredient 1
     */
    public Label getIngredient1Label() {
        return ingredient1Label;
    }

    /**
     * Get ingredient 2 label of order recipe.
     *
     * @return the label for ingredient 2
     */
    public Label getIngredient2Label() {
        return ingredient2Label;
    }

    /**
     * Get ingredient 3 label of order recipe.
     *
     * @return the label for ingredient 3
     */
    public Label getIngredient3Label() {
        return ingredient3Label;
    }

    /**
     * Renders stage
     *
     * @param delta the delta
     */
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    /**
     * Resizes stage so it maintains aspect ratio.
     *
     * @param width  the width of screen
     * @param height the height of screen
     */
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /**
     * Disposes stage
     */
    @Override
    public void dispose() {
        stage.dispose();
    }
}
