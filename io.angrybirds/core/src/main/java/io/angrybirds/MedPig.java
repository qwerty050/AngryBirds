package io.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class MedPig extends Pig{
    private Sprite[] MedPigSprites;
    private int currentFrame;
    private float frameTime;
    private float timeToChange; // Time until the next random change
    private Random random;

    public MedPig(int x, int y) {
        super(x, y);


        // Load the bird sprites into an array
        MedPigSprites = new Sprite[] {
                new Sprite(new Texture("medpig1.png")),
                new Sprite(new Texture("medpig2.png")),
                new Sprite(new Texture("medpig3.png"))
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
            currentFrame = random.nextInt(MedPigSprites.length); // Randomly select a frame
            frameTime = 0f; // Reset the timer
            timeToChange = getRandomChangeTime(); // Get a new random time to change
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        update(Gdx.graphics.getDeltaTime()); // Update animation on draw call
        batch.draw(MedPigSprites[currentFrame], position.x, position.y, MedPigSprites[currentFrame].getWidth() / 3, MedPigSprites[currentFrame].getHeight() / 3);
    }
}
