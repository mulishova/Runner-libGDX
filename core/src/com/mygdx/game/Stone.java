package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Stone {
    private TextureRegion texture;
    private Vector2 position;
    private Rectangle rectangle; // для проверки столкновений

    private final int WIDTH = 67;
    private final int HEIGHT = 100;

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Stone (TextureRegion texture, Vector2 position) {
        this.texture = texture;
        this.position = position;
        this.rectangle = new Rectangle(position.x, position.y, WIDTH, HEIGHT);
    }

    public void setPosition (float x, float y) { // смена позиции камня
        position.set(x, y);
        rectangle.setPosition(position);
    }

    public void render (SpriteBatch batch, float worldX) {
        batch.draw(texture, position.x - worldX, position.y);
    }
}
