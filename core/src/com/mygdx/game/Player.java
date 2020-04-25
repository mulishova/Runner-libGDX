package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private GameScreen gameScreen;

    private Texture texture;
    private Vector2 position; // координаты игрока
    private Vector2 velocity; // скорость персонажа
    private Rectangle rectangle;

    private final int WIDTH = 100; // размеры персонажа
    private final int HEIGHT = 80;

    private float score; // очки персонажа
    private float time; // время бега персонажа

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Player (GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.texture = new Texture("player.png");
        this.position = new Vector2(0, 70);
        this.velocity = new Vector2(150.0f, 0.0f);
        this.rectangle = new Rectangle(position.x, position.y, WIDTH, HEIGHT);
        this.score = 0;
    }

    public void render (SpriteBatch batch) {
        int frame = (int) (time / 0.2f); // скорость анимации
        frame = frame % 2;
        batch.draw(texture, gameScreen.getPlayerAnchor(), position.y, frame * 100, 0, WIDTH, HEIGHT);
    }

    public void update (float dt) {
        if (position.y > gameScreen.getGroundHeight()) {
            velocity.y -= 700.0f * dt;
        } else {
            position.y = gameScreen.getGroundHeight();
            velocity.y = 0.0f;
            time += velocity.x * dt / 100.0f;

            if (Gdx.input.justTouched()) { // если ткнуть в экран
                velocity.y = 500.0f;
            }
        }

        position.mulAdd(velocity, dt);

        velocity.x += 3.0f * dt;
        score += velocity.x * dt / 5.0f;
        rectangle.setPosition(position.x, position.y);
    }
}
