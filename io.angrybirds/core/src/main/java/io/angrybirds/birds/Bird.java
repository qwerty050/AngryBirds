package io.angrybirds.birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bird {
    Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    private Vector2 direction;
    Sprite sprite;

    public Bird(int x, int y) {
        position = new Vector2(x, y);
    }
    private void handleMovement() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            position.y += 100 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            position.y  -= 100 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= 100 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += 100 * Gdx.graphics.getDeltaTime();
        }
//        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//            img1Height += 100 * Gdx.graphics.getDeltaTime();
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//            img1Height -= 100 * Gdx.graphics.getDeltaTime();
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//            img1Width -= 100 * Gdx.graphics.getDeltaTime();
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//            img1Width += 100 * Gdx.graphics.getDeltaTime();
//        }
    }
    public void draw(SpriteBatch batch){
//        handleMovement();
        batch.draw(sprite, position.x, position.y, sprite.getWidth()/2, sprite.getHeight()/2);
//        System.out.println(position.x + " " + position.y);
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }
    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }
    public void setDirection(Vector2 direction) {
        this.direction = direction;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }
    public Vector2 getVelocity() {
        return velocity;
    }
    public Vector2 getPosition() {
        return position;
    }
}
