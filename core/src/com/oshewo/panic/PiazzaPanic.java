package com.oshewo.panic;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.oshewo.panic.screens.MainMenu;
import com.oshewo.panic.screens.PlayScreen;

public class PiazzaPanic extends Game {
	public static final float V_ZOOM = 0.7f;
	public static final float V_WIDTH = 800;
	public static final float V_HEIGHT = 416;

	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

}
