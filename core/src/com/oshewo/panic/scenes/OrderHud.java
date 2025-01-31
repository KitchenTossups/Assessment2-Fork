package com.oshewo.panic.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.oshewo.panic.base.BaseActor;

/**
 * The Order Hud displays the info of recipe name and ingredients of current order to do on the order sheet
 *
 * @author sl3416
 */
public class OrderHud extends BaseActor {   //Uses orders placed by customers to instruct user what to make and how

    Label label;

    /**
     * Instantiates a new Order hud by setting up the order labels, images as actors in a table to display on screen
     *
     */
    public OrderHud(float x, float y, Stage s) {
        super(x, y, s);

        // Order Hud Setup
        // Receipt image
        // variables for order - order image, recipe and ingredient labels for recipe
        Image order_receipt = new Image(new Texture(Gdx.files.internal("order_receipt1.png")));
        order_receipt.setPosition(x, y);
        order_receipt.setSize(250, 500);

        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Minecraftia-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameters.size = 14;
        fontParameters.color = Color.BLACK;
        fontParameters.borderColor = Color.WHITE;
        fontParameters.borderStraight = true;
        fontParameters.minFilter = Texture.TextureFilter.Linear;
        fontParameters.magFilter = Texture.TextureFilter.Linear;

        BitmapFont bitmap = fontGenerator.generateFont(fontParameters);

        bitmap.getData().setScale(1, 0.8f);

        Label.LabelStyle style = new Label.LabelStyle(bitmap, Color.BLACK);

        this.label = new Label("", style);
        this.label.setPosition(x + 65, y + 5);
        this.label.setWrap(true);
        this.label.setAlignment(Align.topLeft);
        this.label.setSize(175, 390);

        // Lays out recipe and ingredients label onto the order image in a table
        Table image_table = new Table();
        image_table.addActor(order_receipt);

        Table labels_table = new Table();
        labels_table.addActor(this.label);

        Stack stack = new Stack();
        stack.add(image_table);
        stack.add(labels_table);

        s.addActor(stack);
    }

    /**
     * Get label.
     *
     * @return the label for orders
     */
    public Label getLabel() {
        return this.label;
    }
}
