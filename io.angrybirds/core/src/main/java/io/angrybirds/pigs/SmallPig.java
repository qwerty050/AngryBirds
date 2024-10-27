package io.angrybirds.pigs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class SmallPig extends Pig{
    private Sprite[] PigSprites;
    private int currentFrame;
    private float frameTime;
    private float timeToChange; // Time until the next random change
    private Random random;
    private Texture pigTexture;

    public SmallPig(int x, int y) {
        super(x, y);

        pigTexture = new Texture(Gdx.files.internal("birds.png"));
        // Load the bird sprites into an array
        PigSprites = new Sprite[] {
                new Sprite(pigTexture,847,1294,48,48),
                new Sprite(pigTexture,913,1239,48,48),
                new Sprite(pigTexture,984,1182,48,48),
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
            currentFrame = random.nextInt(PigSprites.length); // Randomly select a frame
            frameTime = 0f; // Reset the timer
            timeToChange = getRandomChangeTime(); // Get a new random time to change
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        update(Gdx.graphics.getDeltaTime()); // Update animation on draw call
        batch.draw(PigSprites[currentFrame], position.x, position.y, PigSprites[currentFrame].getWidth() / 3, PigSprites[currentFrame].getHeight() / 3);
    }
}
