package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Enemy {
    public enum Type {
        STONE, WOOD
    }

    private TextureRegion texture;
    private Vector2 position;
    private Vector2 velocity;
    // private Rectangle rectangle; // для проверки столкновений
    private Circle hitArea;

    private final int WIDTH = 67;
    private final int HEIGHT = 100;

    public Vector2 getPosition() {
        return position;
    }

    /* public Rectangle getRectangle() {
        return rectangle;
    }*/

    public Circle getHitArea() {
        return hitArea;
    }

    public Enemy(TextureRegion texture, Vector2 position) {
        this.texture = texture;
        this.position = position;
        this.velocity = new Vector2(0, 0);
        //this.rectangle = new Rectangle(position.x, position.y, WIDTH, HEIGHT);
        this.hitArea = new Circle(position.x + WIDTH / 2, position.y + HEIGHT / 2, WIDTH / 2);;
    }

    public void setPosition (float x, float y) { // смена позиции камня
        position.set(x, y);
        //rectangle.setPosition(position);
        hitArea.setPosition(position.x + WIDTH / 2, position.y + HEIGHT / 2);
    }

    public void setup (TextureRegion texture, float x, float y, float vx, float vy) {
        this.texture = texture;
        this.position.set(x, y);
        this.velocity.set(vx, vy);
    }

    public void render (SpriteBatch batch, float worldX) {
        batch.draw(texture, position.x - worldX, position.y);
    }

    public void update (float dt) {
        position.mulAdd(velocity, dt);
        // rectangle.setPosition(position);
        hitArea.setPosition(position.x + WIDTH / 2, position.y + HEIGHT / 2);
    }
}
