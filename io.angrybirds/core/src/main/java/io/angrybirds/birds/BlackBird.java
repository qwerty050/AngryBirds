package io.angrybirds.birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

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
            new Sprite(birds,471,974,69,81),
            new Sprite(birds, 1070,784,69,81),
        };

        super.setBirdSprites(blackBirdSprites);
        super.spriteSetter(Gdx.graphics.getDeltaTime());
        super.setFlyingSprite(new Sprite(birds,471,1141,69,81));
        super.setDeadSprite(new Sprite(birds,471,974,69,81));
        super.createBody(world,x,y);
        super.getBody().setActive(false);

    }


    public void update(float deltaTime) {
        // Check for collision impact if launched
        if (isLaunched() && !isExploding && !isDead()) {
            checkForImpact();
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (!isExploding) {
            update(Gdx.graphics.getDeltaTime());
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

        if (!isExploding && isLaunched() && !super.isHasCollided()) {
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
//        world.destroyBody(getBody());
//        super.setDead(true);
    }

}
