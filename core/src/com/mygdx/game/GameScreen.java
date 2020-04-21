package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {
    private RunnerGame runnerGame;
    private SpriteBatch batch;

    private Texture textureBackground; // задний фон

    public GameScreen (RunnerGame runnerGame, SpriteBatch batch) {
        this.runnerGame = runnerGame;
        this.batch = batch;
    }

    @Override
    public void show() { // подготовка данных для экрана
        textureBackground = new Texture("background.jpg");
    }

    @Override
    public void render(float delta) { // отрисовка 60 кадров в секунду
        batch.begin();
        batch.draw(textureBackground, 0, 0);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        runnerGame.getViewport().update(width, height, true);
        runnerGame.getViewport().apply();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() { // освобождение ресурсов

    }
}
