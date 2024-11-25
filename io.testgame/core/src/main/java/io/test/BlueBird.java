package io.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import java.util.ArrayList;
import java.util.Random;

public class BlueBird extends Bird {

    private Texture birds;
    private Sprite[] blueBirdSprites;
    private int currentFrame;
    private float frameTime;
    private float timeToChange;
    private Random random;
    private boolean hasUsedAbility = false;
    private ArrayList<Bird> splitBirds;
    private World world;

    public BlueBird(World world, float x, float y) {
        super(world, x, y);
        this.world = world;
        birds = new Texture("birds.png");

        // Load the bird sprites into an array (adjust coordinates based on your sprite sheet)
        blueBirdSprites = new Sprite[] {
            new Sprite(birds,735,1305,63,56),
            new Sprite(birds, 735,1247,63,56),
            new Sprite(birds,846,1181,63,56),
            new Sprite(birds,913,1181,68,55),
        };

        currentFrame = 0;
        frameTime = 0f;
        random = new Random();
        timeToChange = getRandomChangeTime();
        splitBirds = new ArrayList<>();
    }

    private float getRandomChangeTime() {
        return 0.1f + random.nextFloat() * 0.5f;
    }

    public void update(float deltaTime) {
        frameTime += deltaTime;

        if (frameTime >= timeToChange) {
            currentFrame = random.nextInt(blueBirdSprites.length);
            frameTime = 0f;
            timeToChange = getRandomChangeTime();
        }

        // Update split birds if they exist
        for (Bird bird : splitBirds) {
            bird.update();
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.setSprite(blueBirdSprites[currentFrame]);
        super.draw(batch);

        // Draw split birds
        for (Bird bird : splitBirds) {
            bird.draw(batch);
        }
    }

    @Override
    public void ability() {
        if (!hasUsedAbility && isLaunched()) {
            hasUsedAbility = true;
            Vector2 originalVelocity = getBody().getLinearVelocity();
            float speed = originalVelocity.len();

            // Create two additional birds
            createSplitBird(20f, speed);  // Split upward
            createSplitBird(-20f, speed); // Split downward
        }
    }

    private void createSplitBird(float angleOffset, float speed) {
        // Create new bird at current position
        Bird splitBird = new BlueBird(world,
            getBody().getPosition().x,
            getBody().getPosition().y);
        splitBird.getBody().setActive(true);
        // Scale down the split bird
//        splitBird.getSprite().setScale(0.8f);

        // Calculate new velocity
        Vector2 currentVel = getBody().getLinearVelocity();
        float currentAngle = (float) Math.atan2(currentVel.y, currentVel.x);
        float newAngle = currentAngle + (float) Math.toRadians(angleOffset);

        // Set new velocity
        Vector2 newVelocity = new Vector2(
            (float) (speed * Math.cos(newAngle)),
            (float) (speed * Math.sin(newAngle))
        );

        splitBird.getBody().setLinearVelocity(newVelocity);
        splitBird.setLaunched(true);

        splitBirds.add(splitBird);
    }

    public ArrayList<Bird> getSplitBirds() {
        return splitBirds;
    }

}
