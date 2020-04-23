package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {
    private RunnerGame runnerGame;
    private SpriteBatch batch;

    private Texture textureBackground;
    private Texture textureGround;

    private float globalX; // задаем движение земли
    private float groundHeight = 70.0f;
    private float playerAnchor = 200.0f; // точка, к которой привязан персонаж, координата X

    private Player player;

    public float getPlayerAnchor() {
        return playerAnchor;
    }

    public GameScreen (RunnerGame runnerGame, SpriteBatch batch) {
        this.runnerGame = runnerGame;
        this.batch = batch;
    }

    @Override
    public void show() { // подготовка данных для экрана
        textureBackground = new Texture("background.png");
        textureGround = new Texture("ground.png");
        player = new Player(this);
    }

    @Override
    public void render(float delta) { // отрисовка 60 кадров в секунду
        update(delta);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(textureBackground, 0, 0);

        for (int i = 0; i < 16; i++) {
            batch.draw(textureGround, 70 * i - globalX % 70, 0);
        }

        player.render(batch);

        batch.end();
    }

    public void update (float dt) {
        globalX += 70 * dt;
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
        textureBackground.dispose();
        textureGround.dispose();
    }
}
