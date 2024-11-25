package io.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

public class RedBird extends Bird {
    private Texture birds;
    private Sprite[] redBirdSprites;
    private int currentFrame;
    private float frameTime;
    private float timeToChange; // Time until the next random change
    private Random random;

    public RedBird(World world ,int x, int y) {
        super(world, x, y);

        // Load the bird sprites into an array
        redBirdSprites = new Sprite[] {
            new Sprite(new Texture("redbird1.png")),
            new Sprite(new Texture("redbird2.png")),
            new Sprite(new Texture("redbird3.png"))
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
            currentFrame = random.nextInt(redBirdSprites.length); // Randomly select a frame
            frameTime = 0f; // Reset the timer
            timeToChange = getRandomChangeTime(); // Get a new random time to change
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        update(Gdx.graphics.getDeltaTime()); // Update animation on draw call
        super.setSprite(redBirdSprites[currentFrame]);
        super.draw(batch);
    }
}
