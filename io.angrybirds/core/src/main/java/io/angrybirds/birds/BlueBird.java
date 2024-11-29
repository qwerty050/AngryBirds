package io.angrybirds.birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.Random;

import static io.angrybirds.Main.PPM;

public class BlueBird extends Bird {
    private Texture birds;
    private Sprite[] blueBirdSprites;
    private boolean hasUsedAbility = false;
    private ArrayList<Bird> splitBirds;
    private World world;

    public BlueBird(World world, float x, float y) {
        super(world, x, y);
        this.world = world;
        birds = new Texture("birds.png");

        // Load the bird sprites into an array (adjust coordinates based on your sprite sheet)
        blueBirdSprites = new Sprite[] {
            new Sprite(birds,1103,1005,33,34),
        };

        super.setID(2);
        super.setBirdSprites(blueBirdSprites);
        super.spriteSetter(Gdx.graphics.getDeltaTime());
        super.setFlyingSprite(new Sprite(birds,565,200,34,37));
        super.setDeadSprite(new Sprite(birds,564,240,34,34));
        super.createBody(world,x,y);
        super.getBody().setActive(false);
        splitBirds = new ArrayList<>();
    }


    public void update() {
        super.update();
        for (Bird bird : splitBirds) {
            bird.update();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            this.ability();
            System.out.println("called ability");
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        
        for (Bird bird : splitBirds) {
            bird.draw(batch);
        }
    }

    @Override
    public void ability() {
        if (!hasUsedAbility && isLaunched() && !super.isHasCollided()) {
            hasUsedAbility = true;
            Vector2 originalVelocity = getBody().getLinearVelocity();
            float speed = originalVelocity.len();

            // Create two additional birds
            createSplitBird(20f, speed);  // Split upward
            createSplitBird(-20f, speed); // Split downward
        }
    }

    private void createSplitBird(float angleOffset, float speed) {
        // Create new bird at current position, converting back from Box2D coordinates
        Bird splitBird = new BlueBird(world,
                getBody().getPosition().x * PPM,
                getBody().getPosition().y * PPM);
        splitBird.getBody().setActive(true);

        // Calculate new velocity
        Vector2 currentVel = getBody().getLinearVelocity();
        float currentAngle = (float) Math.atan2(currentVel.y, currentVel.x);
        float newAngle = currentAngle + (float) Math.toRadians(angleOffset);

        // Set new velocity (already in Box2D coordinates, no PPM conversion needed)
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
