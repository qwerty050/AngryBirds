package io.test;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

public class WoodBlock extends Block {
    private Sprite FullHealth;
    private Sprite damaged1;
    private Sprite damaged2;
    private Sprite damaged3;
    private World world;
    private static final float WOODBLOCK_HEALTH = 15000f;


    public WoodBlock(World world, float x, float y, float width, float height) {
        super(WOODBLOCK_HEALTH, world, x, y, width, height);
        this.world = world;

    }


    public WoodBlock(World world, float x, float y, Sprite d1,Sprite d2,Sprite d3,Sprite d4) {
        super(WOODBLOCK_HEALTH, world, x, y, d1.getWidth()/2, d1.getHeight()/2);
        this.world = world;
        FullHealth = d1;
        damaged1 = d2;
        damaged2 = d3;
        damaged3 = d4;
    }

    public void draw(SpriteBatch batch) {
        super.setSprite(currentSprite());
        super.draw(batch);
    }

    public Sprite currentSprite() {
        float health = super.getHealth();

        // Debugging: Print current health
//        System.out.println("Current Health: " + health);

        if (health == WOODBLOCK_HEALTH) {
//            System.out.println("Returning FullHealth sprite");
            return FullHealth;
        } else if (health > WOODBLOCK_HEALTH * 3 / 4) {
//            System.out.println("Returning damaged1 sprite (health > 75%)");
            return damaged1;
        } else if (health > WOODBLOCK_HEALTH * 2 / 4) {
//            System.out.println("Returning damaged2 sprite (health > 50%)");
            return damaged2;
        } else if (health > WOODBLOCK_HEALTH * 1 / 4) {
//            System.out.println("Returning damaged3 sprite (health > 25%)");
            return damaged3;
        } else {
//            System.out.println("Returning damaged3 sprite (health <= 25%)");
            return damaged3;  // Return damaged3 when health is 0 to 25% or lower
        }
    }



}
