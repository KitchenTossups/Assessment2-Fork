package com.oshewo.panic.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.oshewo.panic.PiazzaPanic;

public class OrderHud implements Disposable {
    PiazzaPanic game;
    public Stage stage;
    private Viewport viewport;
    private Image order_receipt;

    // order and ingredients labels
    Label recipeLabel;
    Label ingredient1Label;
    Label ingredient2Label;
    Label ingredient3Label;

    public OrderHud(SpriteBatch sb){

        viewport = new FitViewport(PiazzaPanic.V_WIDTH, PiazzaPanic.V_WIDTH, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        // Order HUD
        // Receipt image
        order_receipt = new Image(new Texture(Gdx.files.internal("order_receipt.png")));
        order_receipt.setPosition(25, 400);
        order_receipt.setSize(150, 350);

        // Order labels
        recipeLabel = new Label("", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("arcade_classic.fnt")), Color.BLACK));
        recipeLabel.setPosition(65, 570);
        recipeLabel.setSize(150, 150);

        ingredient1Label = new Label("", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("arcade_classic.fnt")), Color.BLACK));
        ingredient1Label.setPosition(75, 535);
        ingredient1Label.setSize(150, 150);

        ingredient2Label = new Label("", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("arcade_classic.fnt")), Color.BLACK));
        ingredient2Label.setPosition(75, 500);
        ingredient2Label.setSize(150, 150);

        ingredient3Label = new Label("", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("arcade_classic.fnt")), Color.BLACK));
        ingredient3Label.setPosition(75, 465);
        ingredient3Label.setSize(150, 150);

        // Order receipt layout
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

    public Label getRecipeLabel(){
        return recipeLabel;
    }

    public Label getIngredient1Label(){
        return ingredient1Label;
    }

    public Label getIngredient2Label(){
        return ingredient2Label;
    }

    public Label getIngredient3Label(){
        return ingredient3Label;
    }

    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
