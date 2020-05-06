package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MenuScreen implements Screen {
    private RunnerGame runnerGame;
    private SpriteBatch batch;

    private Stage stage; // система интерфейсов
    private Skin skin; // как выглядит интерфейс

    private TextureAtlas atlas;
    private TextureRegion textureBackground;
    private BitmapFont font96;

    public MenuScreen(RunnerGame runnerGame, SpriteBatch batch) {
        this.runnerGame = runnerGame;
        this.batch = batch;
    }

    @Override
    public void show() {
        atlas = new TextureAtlas("menu.pack");
        textureBackground = atlas.findRegion("background");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("amatic.ttf")); // шрифт из ttf файла
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 96; // размер букв
        parameter.borderColor = Color.DARK_GRAY; // границы
        parameter.borderWidth = 2; // толщина границы
        parameter.shadowOffsetX = 2; // тень
        parameter.shadowOffsetY = -2;
        parameter.shadowColor = Color.DARK_GRAY;
        font96 = generator.generateFont(parameter);
        generator.dispose();

        stage = new Stage(runnerGame.getViewport(), batch);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(textureBackground, 0, 0);
        font96.draw(batch, "UNDERSEA WORLD", 100, 500);
        font96.draw(batch, "RUNNER", 320, 400);
        batch.end();

        stage.draw();
    }

    public void update () {
        
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
    public void dispose() {
        atlas.dispose();
        font96.dispose();
    }
}
