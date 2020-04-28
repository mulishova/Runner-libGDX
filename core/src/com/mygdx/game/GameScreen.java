package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements Screen {
    private RunnerGame runnerGame;
    private SpriteBatch batch;

    private Texture textureBackground;
    private Texture textureGround;
    private Texture textureStone;

    private BitmapFont font48;
    private BitmapFont font96;

    private float groundHeight = 70.0f;
    private float playerAnchor = 200.0f; // точка, к которой привязан персонаж, координата X

    private boolean gameOver;

    private Player player;
    private Stone[] enemies;

    public float getPlayerAnchor() {
        return playerAnchor;
    }

    public float getGroundHeight() {
        return groundHeight;
    }

    public GameScreen (RunnerGame runnerGame, SpriteBatch batch) {
        this.runnerGame = runnerGame;
        this.batch = batch;
    }

    @Override
    public void show() { // подготовка данных для экрана
        textureBackground = new Texture("background.png");
        textureGround = new Texture("ground.png");
        textureStone = new Texture("stone.png");

        player = new Player(this);

        enemies = new Stone[5];
        enemies[0] = new Stone(textureStone, new Vector2(800, groundHeight));
        for (int i = 1; i < 5; i++) {
            enemies[i] = new Stone(textureStone, new Vector2(enemies[i - 1].getPosition().x + MathUtils.random(300, 1000), groundHeight));
        }

        gameOver = false;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("amatic.ttf")); // шрифт из ttf файла
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 48; // размер букв
        parameter.borderColor = Color.DARK_GRAY; // границы
        parameter.borderWidth = 2; // толщина границы
        parameter.shadowOffsetX = 2; // тень
        parameter.shadowOffsetY = -2;
        parameter.shadowColor = Color.DARK_GRAY;
        font48 = generator.generateFont(parameter); // шрифт
        parameter.size = 96;
        font96 = generator.generateFont(parameter);
        generator.dispose();
    }

    @Override
    public void render(float delta) { // отрисовка 60 кадров в секунду
        update(delta);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(textureBackground, 0, 0);

        for (int i = 0; i < 16; i++) {
            batch.draw(textureGround, groundHeight * i - player.getPosition().x % groundHeight, 0);
        }

        player.render(batch);

        for (int i = 0; i < enemies.length; i++) {
            enemies[i].render(batch, player.getPosition().x - playerAnchor);
        }

        font48.draw(batch, "SCORE: " + (int)player.getScore(), 20, 600);

        batch.end();
    }

    private float getRightestEnemy() { // координата Х самого правого камня
        float maxValue = 0.0f;

        for (int i = 0; i < enemies.length; i++) {
            if (maxValue < enemies[i].getPosition().x) {
                maxValue = enemies[i].getPosition().x;
            }
        }

        return maxValue;
    }

    public void update (float dt) {
        if (!gameOver) {
            player.update(dt);

            for (int i = 0; i < enemies.length; i++) {
                if (enemies[i].getPosition().x < player.getPosition().x - playerAnchor - 100) { // если заехали за левую сторону экрана
                    enemies[i].setPosition(getRightestEnemy() + MathUtils.random(300, 1000), groundHeight);
                }
            }

            for (int i = 0; i < enemies.length; i++) {
                if (enemies[i].getRectangle().overlaps(player.getRectangle())) {
                    gameOver = true;
                    break;
                }
            }
        }
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
        textureStone.dispose();
    }
}
