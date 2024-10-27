package io.angrybirds.birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class WhiteBird extends Bird {
    private Texture birds;
    private Sprite[] BirdSprites;
    private int currentFrame;
    private float frameTime;
    private float timeToChange; // Time until the next random change
    private Random random;

    public WhiteBird(int x, int y) {
        super(x, y);
        birds = new Texture("birds.png");
        this.sprite = new Sprite(birds, 1022, 1231, 44, 53); // Original sprite (if needed)

        // Load the bird sprites into an array
        BirdSprites = new Sprite[] {
                new Sprite(birds,984,783,84,92),
                new Sprite(birds, 896,784,84,92),

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
            currentFrame = random.nextInt(BirdSprites.length); // Randomly select a frame
            frameTime = 0f; // Reset the timer
            timeToChange = getRandomChangeTime(); // Get a new random time to change
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        update(Gdx.graphics.getDeltaTime()); // Update animation on draw call
        batch.draw(BirdSprites[currentFrame], position.x, position.y, BirdSprites[currentFrame].getWidth() / 2, BirdSprites[currentFrame].getHeight() / 2);
    }
}
