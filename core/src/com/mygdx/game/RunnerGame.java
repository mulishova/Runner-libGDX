package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class RunnerGame extends Game {
	public enum Screens {
		MENU, GAME
	}

	private SpriteBatch batch; // область отрисовки
	private GameScreen gameScreen;
	private MenuScreen menuScreen;
	private Viewport viewport; // управление выводом картинки на экран, масштабирование

	public Viewport getViewport () {
		return viewport;
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gameScreen = new GameScreen(this, batch);
		menuScreen = new MenuScreen(this, batch);
		viewport = new FitViewport(1000, 625); // подстраивает картинку под размер экрана
		//setScreen(menuScreen);
		switchScreen(Screens.MENU);
	}

	@Override
	public void render () { // <= 60 кадров в секунду
		float dt = Gdx.graphics.getDeltaTime(); // сколько прошло времени с последней отрисовки

		getScreen().render(dt);
	}

	public void switchScreen (Screens type) {
		Screen currentScreen = getScreen();

		if (currentScreen != null) {
			currentScreen.dispose();
		}

		switch (type) {
			case MENU:
				setScreen(menuScreen);
				break;
			case GAME:
				setScreen(gameScreen);
				break;
		}
	}
	
	@Override
	public void dispose () { // освобождение ресурсов
		batch.dispose();
		getScreen().dispose();
	}
}
