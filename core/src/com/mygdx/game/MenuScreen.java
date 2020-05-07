package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MenuScreen implements Screen {
    private RunnerGame runnerGame;
    private SpriteBatch batch;

    private Stage stage; // система интерфейсов
    private Skin skin; // как выглядит интерфейс

    private TextureAtlas atlas;
    private TextureRegion textureBackground;
    private BitmapFont font96;
    private BitmapFont font32;

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
        parameter.size = 32;
        font32 = generator.generateFont(parameter);
        generator.dispose();

        createGUI();
    }

    public void createGUI () {
        stage = new Stage(runnerGame.getViewport(), batch);
        skin = new Skin(atlas);
        Gdx.input.setInputProcessor(stage); // обрабатываем события ввода на элементы интерфейса stage

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle(); // стиль кнопки
        textButtonStyle.up = skin.getDrawable("blue_button00"); // если кнопку никто не трогает
        textButtonStyle.font = font32;
        skin.add("tbs", textButtonStyle); // задаем название стиля

        TextButton btnNewGame = new TextButton("Play", skin, "tbs");
        TextButton btnExitGame = new TextButton("Exit", skin, "tbs");

        btnNewGame.setPosition(405, 200);
        btnExitGame.setPosition(405, 120);

        stage.addActor(btnNewGame);
        stage.addActor(btnExitGame);

        btnNewGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                runnerGame.switchScreen(RunnerGame.Screens.GAME);
            }
        });

        btnExitGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
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

    public void update (float dt) {
        stage.act(dt); // stage реагирует на наши действия

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
        font32.dispose();
    }
}
