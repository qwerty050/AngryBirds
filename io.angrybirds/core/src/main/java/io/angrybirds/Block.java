package io.angrybirds;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Block {
    private Vector2 position;
    private Sprite sprite;
    private Float rotation;
    private int strength;

    public Block(int x, int y, Sprite sprite, int strength) {
        this.position = new Vector2(x, y);
        this.sprite = sprite;
        this.strength = strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getStrength() {
        return strength;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Float getRotation() {
        return rotation;
    }

    public void setRotation(Float rotation) {
        this.rotation = rotation;
    }

    public void draw(SpriteBatch batch) {
        sprite.setRotation(rotation);
        sprite.setPosition(position.x, position.y);
        sprite.setScale(0.5f, 0.5f);
//        sprite.setSize(sprite.getWidth()/2, sprite.getHeight()/2);
        sprite.draw(batch);
    }
}
