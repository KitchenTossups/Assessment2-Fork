package com.oshewo.panic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.*;
import com.oshewo.panic.PiazzaPanic;
import com.oshewo.panic.actor.FoodActor;
import com.oshewo.panic.actor.StationTimer;
import com.oshewo.panic.base.*;
import com.oshewo.panic.non_actor.Customer;
import com.oshewo.panic.stations.Station;

import java.util.*;
import java.util.concurrent.atomic.*;

import static com.oshewo.panic.lists.Lists.*;

public class PauseScreen extends BaseScreen {  // Stops gameplay when button is pressed

    private static final int buttonWidth = 125;
    private static final int buttonHeight = 50;
    private final PlayScreen playScreen;
    private final long timeSinceEscPressed;

    public PauseScreen(PiazzaPanic game, PlayScreen playScreen) {
        BaseActor background = new BaseActor(0, 0, this.mainStage);
        background.loadTexture("piazza_panic_main_menu_background.png");
        background.setSize(game.V_WIDTH, game.V_HEIGHT);
        this.playScreen = playScreen;
        this.timeSinceEscPressed = new Date().getTime();

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("buttons.pack"));
        Skin skin = new Skin(atlas);

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

        Label mainLabel = new Label("You are currently paused.\nClick below to save the game.", style);
        mainLabel.setAlignment(Align.center);

        Label modeChangeLabel = new Label("SAVE GAME", style);
        modeChangeLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saveGame();
            }
        });

        TextButton.TextButtonStyle button = new TextButton.TextButtonStyle();
        button.font = game.labelStyle[0].font;
        button.up = skin.getDrawable("exit_button_inactive");
        button.down = skin.getDrawable("exit_button_active");

        TextButton buttonExit = new TextButton("", button);

        this.uiTable.pad(50);
        this.uiTable.row().height(300);
        this.uiTable.add(new Actor()); // Program will add a space if there is something here, even if it's empty
        this.uiTable.row().height(50);
        this.uiTable.add(mainLabel).center().expandX().pad(10);
        this.uiTable.row().height(50);
        this.uiTable.add(modeChangeLabel).center().expandX().pad(10);
        this.uiTable.row().height(50);
        this.uiTable.add(buttonExit).center().size(buttonWidth, buttonHeight).pad(10);

        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playScreen.submitPauseTime(TimeUtils.timeSinceMillis(timeSinceEscPressed));
                dispose();
                game.setActiveScreen(playScreen);
            }
        });
    }

    private void saveGame() {
        Preferences preferences = Gdx.app.getPreferences("game_save");
        AtomicReference<AtomicLong> atomicTime = new AtomicReference<>(new AtomicLong());
        playScreen.getTimesInPause().forEach((key, value) -> atomicTime.updateAndGet((v) -> new AtomicLong(v.get() + value)));
        preferences.putLong("game_duration", TimeUtils.timeSinceMillis(this.playScreen.hud.getStartTime()) - atomicTime.get().get());
        preferences.putInteger("completed_orders", this.playScreen.getOrdersCompleted());
        preferences.putInteger("binned_items", this.playScreen.getBinnedItems());
        preferences.putInteger("chef_selector", this.playScreen.getChefSelector());
        preferences.putLong("time_until_next_power_up", this.playScreen.getTimeUntilNextPowerUp() - new Date().getTime());
        preferences.putLong("time_until_reset_chef_speed", this.playScreen.getTimeUntilResetChefSpeed() - new Date().getTime());
        preferences.putLong("time_until_reset_cooking_multiplier", this.playScreen.getTimeUntilResetCookingMultiplier() - new Date().getTime());
        preferences.putLong("time_until_reset_chopping_multiplier", this.playScreen.getTimeUntilResetChoppingMultiplier() - new Date().getTime());
        if (this.playScreen.getPowerUp() != null)
            preferences.putString("power_up", this.playScreen.getPowerUp().getPowerUpType().toString());
        preferences.putFloat("movement_multiplier", this.playScreen.getMovementMultiplier());
        preferences.putFloat("chopping_timer_multiplier", this.playScreen.getChoppingTimerMultiplier());
        preferences.putFloat("cooking_timer_multiplier", this.playScreen.getCookingTimerMultiplier());
        Map<String, String> stoveMap = new HashMap<>();
        Map<String, String> ovenMap = new HashMap<>();
        Map<String, String> choppingMap = new HashMap<>();
        Map<String, String> timerMap = new HashMap<>();
        Map<String, String> foodMap = new HashMap<>();
        Map<String, String> customerMap = new HashMap<>();
        int i = 0;
        for (StationTimer timer : timers) {
            timerMap.put(String.format("station_timer_%d", i), timer.getSaveConfig(this.playScreen.getTimesInPause()));
            i++;
        }
        i = 0;
        for (FoodActor food : foodActors) {
            foodMap.put(String.format("food_actor_%d", i), food.getSaveConfig());
            i++;
        }
        i = 0;
        for (Customer customer : customers) {
            customerMap.put(String.format("customer_%d", i), customer.getSaveConfig(this.playScreen.getTimesInPause()));
            i++;
        }
        i = 0;
        for (Station stove : stoves) {
            stoveMap.put(String.format("stove_%d", i), stove.getId());
            i++;
        }
        i = 0;
        for (Station oven : ovens) {
            ovenMap.put(String.format("oven_%d", i), oven.getId());
            i++;
        }
        i = 0;
        for (Station chopping : choppingBoards) {
            choppingMap.put(String.format("chopping_%d", i), chopping.getId());
            i++;
        }
        preferences.put(timerMap);
        preferences.put(foodMap);
        preferences.put(customerMap);
        preferences.put(ovenMap);
        preferences.put(stoveMap);
        preferences.put(choppingMap);
        preferences.flush();
    }

    /**
     * Update screen method, used by libgdx
     *
     * @param dt deltaTime
     */
    public void update(float dt) {

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
     * Disposal of the screen
     */
    public void disposing() {

    }
}
