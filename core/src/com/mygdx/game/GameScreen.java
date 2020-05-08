package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class GameScreen implements Screen {
    private RunnerGame runnerGame;
    private SpriteBatch batch;
    private TextureAtlas atlas;

    private Stage stage;
    private Skin skin;
    private boolean paused;

    private TextureRegion textureBackground;
    private TextureRegion textureGround;
    private TextureRegion textureStone;
    private TextureRegion textureWood;

    private BitmapFont font32;
    private BitmapFont font96;

    private float groundHeight = 70.0f;
    private float playerAnchor = 200.0f; // точка, к которой привязан персонаж, координата X

    private boolean gameOver;
    private float time;

    private Player player;
    private Enemy[] enemies;

    private Music music;
    private Sound playerJumpSound;

    public float getPlayerAnchor() {
        return playerAnchor;
    }

    public float getGroundHeight() {
        return groundHeight;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public GameScreen (RunnerGame runnerGame, SpriteBatch batch) {
        this.runnerGame = runnerGame;
        this.batch = batch;
    }

    @Override
    public void show() { // подготовка данных для экрана
        atlas = new TextureAtlas("runner.pack");
        textureBackground = atlas.findRegion("background");
        textureGround = atlas.findRegion("ground");
        textureStone = atlas.findRegion("stone");
        textureWood = atlas.findRegion("wood");

        playerJumpSound = Gdx.audio.newSound(Gdx.files.internal("playerSound.ogg"));
        player = new Player(this, playerJumpSound);

        music = Gdx.audio.newMusic(Gdx.files.internal("main.mp3"));
        music.setLooping(true); // зациклить
        music.setVolume(0.05f);
        music.play();

        enemies = new Enemy[5];
        enemies[0] = new Enemy(textureStone, new Vector2(800, groundHeight));
        for (int i = 1; i < 5; i++) {
            enemies[i] = new Enemy(textureStone, new Vector2(enemies[i - 1].getPosition().x + MathUtils.random(300, 1000), groundHeight));
        }

        gameOver = false;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("amatic.ttf")); // шрифт из ttf файла
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 32; // размер букв
        parameter.borderColor = Color.DARK_GRAY; // границы
        parameter.borderWidth = 2; // толщина границы
        parameter.shadowOffsetX = 2; // тень
        parameter.shadowOffsetY = -2;
        parameter.shadowColor = Color.DARK_GRAY;
        font32 = generator.generateFont(parameter); // шрифт
        parameter.size = 96;
        font96 = generator.generateFont(parameter);
        generator.dispose();

        HighScore.createTable();
        HighScore.loadTable();

        Gdx.input.setInputProcessor(null);

        paused = false;
        createGUI();
    }

    public void createGUI () {
        stage = new Stage(runnerGame.getViewport(), batch);
        skin = new Skin(atlas);
        Gdx.input.setInputProcessor(stage); // обрабатываем события ввода на элементы интерфейса stage

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle(); // стиль кнопки
        textButtonStyle.up = skin.getDrawable("ground"); // если кнопку никто не трогает
        textButtonStyle.font = font32;
        skin.add("tbs", textButtonStyle); // задаем название стиля

        TextButton btnPause = new TextButton("P", skin, "tbs");
        btnPause.setSize(50, 50);
        btnPause.setPosition(940, 565);
        stage.addActor(btnPause);

        btnPause.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                paused = !paused;
            }
        });

        /*TextField.TextFieldStyle tfs= new TextField.TextFieldStyle();
        tfs.font = font32;
        tfs.fontColor = Color.WHITE;
        tfs.background = skin.getDrawable("ground");
        skin.add("tfs", tfs);

        TextField field = new TextField("A", skin, "tfs");
        stage.addActor(field);
        field.setPosition(500, 500);*/

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) { // отрисовка 60 кадров в секунду
        update(delta);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(runnerGame.getViewport().getCamera().combined);
        batch.begin();
        batch.draw(textureBackground, 0, 0);

        for (int i = 0; i < 16; i++) {
            batch.draw(textureGround, groundHeight * i - player.getPosition().x % groundHeight, 0);
        }

        player.render(batch);

        for (int i = 0; i < enemies.length; i++) {
            enemies[i].render(batch, player.getPosition().x - playerAnchor);
        }

        font32.draw(batch, "TOP PLAYER: " + HighScore.topPlayerName + " " + HighScore.topPlayerScore, 20, 600);
        font32.draw(batch, "SCORE: " + (int)player.getScore(), 20, 550);

        if (gameOver) {
            font96.draw(batch, "GAME OVER", 0, 400, 1000, 1, false);
            font32.setColor(1, 1, 1, 0.5f + 0.5f * (float) Math.sin(time * 4.0f));
            font32.draw(batch, "Tap to RESTART", 0, 300, 1000, 1, false);
            font32.setColor(1, 1, 1, 1);
        }

        batch.end();

        stage.draw();
    }

    public void restart() { // перезапуск игры после gameOver
        gameOver = false;
        time = 0.0f;

        enemies[0].setPosition(800, groundHeight);
        for (int i = 1; i < enemies.length; i++) {
            enemies[i].setPosition(enemies[i - 1].getPosition().x + MathUtils.random(300, 1000), groundHeight);
        }

        player.restart();
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

    public void generateEnemy (int index) {
        int maxType = 1; // по умолчанию создаются только STONE

        Enemy.Type type = Enemy.Type.values()[(int) (Math.random() * (maxType + 1))];

        switch (type) {
            case STONE:
                enemies[index].setup(textureStone, getRightestEnemy() + MathUtils.random(100, 700), groundHeight, 0, 0);
                break;
            case WOOD:
                enemies[index].setup(textureWood, getRightestEnemy() + MathUtils.random(100, 700), groundHeight + 1000, - MathUtils.random(100, 400), - MathUtils.random(100, 400));
                break;
        }
    }

    public void update (float dt) {
        if (!paused) {
            time += dt;

            if (!gameOver) {
                player.update(dt);

                for (int i = 0; i < enemies.length; i++) {
                    enemies[i].update(dt);
                    if (enemies[i].getPosition().x < player.getPosition().x - playerAnchor - 100) { // если заехали за левую сторону экрана
                        generateEnemy(i);
                    }
                }

                for (int i = 0; i < enemies.length; i++) {
                    if (enemies[i].getHitArea().overlaps(player.getHitArea())) {
                        gameOver = true;
                        HighScore.updateTable("Player", (int) player.getScore());
                        break;
                    }
                }
            } else if (Gdx.input.justTouched()) {
                restart();
            }
        }

            stage.act(dt);
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
        atlas.dispose();
        music.dispose();
        playerJumpSound.dispose();
        font96.dispose();
        font32.dispose();
    }
}
