package io.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

public class YellowBird extends Bird {

    private Texture birds;
    private Sprite[] yellowBirdSprites;
    private int currentFrame;
    private float frameTime;
    private float timeToChange; // Time until the next random change
    private Random random;


    public YellowBird(World world, float x, float y) {
        super(world, x, y);
        birds = new Texture("birds.png");

        // Load the bird sprites into an array
        yellowBirdSprites = new Sprite[] {
            new Sprite(birds,735,1305,63,56),
            new Sprite(birds, 735,1247,63,56),
            new Sprite(birds,846,1181,63,56),
            new Sprite(birds,913,1181,68,55),
        };
        currentFrame = 0;
        frameTime = 0f;
        random = new Random();
        timeToChange = getRandomChangeTime(); // Get initial random change time
    }

    // Get a random time to change the frame
    private float getRandomChangeTime() {
        return 0.1f + random.nextFloat() * 0.5f; // Random time between 0.1s and 0.6s
    }

    // Update the current frame based on elapsed time
    public void update(float deltaTime) {
        frameTime += deltaTime;

        // Change the frame randomly based on time
        if (frameTime >= timeToChange) {
            currentFrame = random.nextInt(yellowBirdSprites.length); // Randomly select a frame
            frameTime = 0f; // Reset the timer
            timeToChange = getRandomChangeTime(); // Get a new random time to change
        }
    }
    @Override
    public void draw(SpriteBatch batch) {
        update(Gdx.graphics.getDeltaTime()); // Update animation on draw call
        super.setSprite(yellowBirdSprites[currentFrame]);
        super.draw(batch);
    }

    @Override
    public void ability() {
        float vx = this.getBody().getLinearVelocity().x;
        float vy = this.getBody().getLinearVelocity().y;

        if(vy>vx){
            this.getBody().setLinearVelocity(this.getBody().getLinearVelocity().x,vy*5);
        }

        else {
            this.getBody().setLinearVelocity(vx*5,this.getBody().getLinearVelocity().y);
        }
    }
}

