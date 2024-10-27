package io.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
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

}
