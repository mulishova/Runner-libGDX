package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class RunnerGame extends Game {
	private SpriteBatch batch; // область отрисовки
	private GameScreen gameScreen;
	private Viewport viewport; // управление выводом картинки на экран, масштабирование

	public Viewport getViewport () {
		return viewport;
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gameScreen = new GameScreen(this, batch);
		viewport = new FitViewport(900, 675); // подстраивает картинку под размер экрана
		setScreen(gameScreen);
	}

	@Override
	public void render () { // <= 60 кадров в секунду
		float dt = Gdx.graphics.getDeltaTime(); // сколько прошло времени с последней отрисовки

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		getScreen().render(dt);
	}
	
	@Override
	public void dispose () { // освобождение ресурсов
		batch.dispose();
		getScreen().dispose();
	}
}
