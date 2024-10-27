package io.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class SlingShot {
    private Sprite sprite;
    private Vector2 position;
    Texture birdSheet;

    public SlingShot(int x, int y){
        position = new Vector2(x, y);
        birdSheet=new Texture("slingshot.png");
        sprite = new Sprite(birdSheet);
    }
    private void handleMovement() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            position.y += 100 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            position.y -= 100 * Gdx.graphics.getDeltaTime();
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
        batch.draw(sprite, position.x, position.y, sprite.getWidth()/3, sprite.getHeight()/3);
//        System.out.println(position.x + " " + position.y);
    }

}
