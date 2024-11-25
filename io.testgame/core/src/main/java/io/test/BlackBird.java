package io.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
    import java.util.Random;

public class BlackBird extends Bird {

    private Texture birds;
    private Sprite[] blackBirdSprites;
    private int currentFrame;
    private float frameTime;
    private float timeToChange;
    private Random random;
    private boolean isExploding = false;
    private float explosionRadius = 400f; // Adjust based on your world scale
    private float explosionForce = 100000/6f; // Adjust based on your needs
    private World world;

    public BlackBird(World world, float x, float y) {
        super(world, x, y);
        this.world = world;
        birds = new Texture("birds.png");

        // Load the bird sprites (adjust coordinates based on your sprite sheet)
        blackBirdSprites = new Sprite[] {
            new Sprite(birds,735,1305,63,56),
            new Sprite(birds, 735,1247,63,56),
            new Sprite(birds,846,1181,63,56),
            new Sprite(birds,913,1181,68,55),
        };

        currentFrame = 0;
        frameTime = 0f;
        random = new Random();
        timeToChange = getRandomChangeTime();
    }

    private float getRandomChangeTime() {
        return 0.1f + random.nextFloat() * 0.5f;
    }

    public void update(float deltaTime) {
        frameTime += deltaTime;

        if (frameTime >= timeToChange) {
            currentFrame = random.nextInt(blackBirdSprites.length);
            frameTime = 0f;
            timeToChange = getRandomChangeTime();
        }

        // Check for collision impact if launched
        if (isLaunched() && !isExploding) {
            checkForImpact();
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (!isExploding) {
            update(Gdx.graphics.getDeltaTime());
            super.setSprite(blackBirdSprites[currentFrame]);
            super.draw(batch);
        }
    }

    private void checkForImpact() {
        // Get the bird's velocity
        Vector2 velocity = getBody().getLinearVelocity();

        // If the bird has slowed down significantly or stopped, trigger explosion
        if (velocity.len() < 1f) {
            ability();
        }
    }

    @Override
    public void ability() {
        if (!isExploding) {
            isExploding = true;
            explode();
        }
    }

    private void explode() {
        // Get the explosion center position
        Vector2 explosionCenter = getBody().getPosition();

        // Query the world for all bodies within explosion radius
        world.QueryAABB(
            fixture -> {
                Body body = fixture.getBody();
                if (body != getBody()) { // Don't apply force to self
                    // Calculate distance and direction from explosion
                    Vector2 direction = body.getPosition().sub(explosionCenter);
                    float distance = direction.len();

                    if (distance <= explosionRadius) {
                        // Calculate force based on distance (closer = stronger)
                        float forceMagnitude = explosionForce * (1 - distance/explosionRadius);
                        direction.nor(); // Normalize to get direction only
                        Vector2 force = direction.scl(forceMagnitude);

                        // Apply the explosion force
                        body.applyLinearImpulse(
                            force,
                            body.getWorldCenter(),
                            true
                        );
                    }
                }
                return true;
            },
            explosionCenter.x - explosionRadius,
            explosionCenter.y - explosionRadius,
            explosionCenter.x + explosionRadius,
            explosionCenter.y + explosionRadius
        );

        // Optional: Remove the bird's body after explosion
        world.destroyBody(getBody());
    }

}
