package io.angrybirds.pigs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Pig {
    Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    private Vector2 direction;
    Sprite sprite;

    public Pig(int x, int y) {
        position = new Vector2(x, y);
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
        sprite.setPosition(position.x, position.y);
        sprite.setScale(0.3f, 0.3f);
//        sprite.setSize(sprite.getWidth()/2, sprite.getHeight()/2);
        sprite.draw(batch);
    }

    public Sprite getSprite() {
        return sprite;
    }
}
