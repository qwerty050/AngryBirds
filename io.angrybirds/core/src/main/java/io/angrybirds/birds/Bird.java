package io.angrybirds.birds;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import io.angrybirds.CollisionHandler;

import java.util.Random;

public abstract class Bird implements CollisionHandler.Damageable {
    private Body body;          // Box2D Body that will represent the bird in the physics world.
    private Sprite sprite;      // The visual representation of the bird.
    private Vector2 position;   // The position of the bird (not used directly since Box2D handles it).
    private boolean launched;   // Whether the bird has been launched.
    private World world;
    private boolean dead;
    private boolean removed;
    private boolean hasCollided;
    private Sprite[] BirdSprites;
    private Sprite flyingSprite;
    private Sprite deadSprite;
    private int currentFrame;
    private float frameTime;
    private float timeToChange; // Time until the next random change
    private Random random;

    // Constructor
    public Bird(World world, float x, float y) {
        this.position = new Vector2(x, y);
        this.sprite = new Sprite();
        this.launched = false;
        this.dead = false;
        this.removed = false;
        this.hasCollided = false;
        this.world = world;
        currentFrame = 0;
        frameTime = 0f;
        random = new Random();
        timeToChange = getRandomChangeTime(); // Get initial random change time
    }

    // Getter and Setter for body
    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    // Getter and Setter for sprite
    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    // Getter and Setter for position
    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
        body.setTransform(position, 0);
    }

    // Getter and Setter for launched
    public boolean isLaunched() {
        return launched;
    }

    public void setLaunched(boolean launched) {
        this.launched = launched;
    }

    // Getter and Setter for world
    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    // Getter and Setter for dead
    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    // Getter and Setter for removed
    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    // Getter and Setter for BirdSprites
    public Sprite[] getBirdSprites() {
        return BirdSprites;
    }

    public void setBirdSprites(Sprite[] birdSprites) {
        this.BirdSprites = birdSprites;
    }

    // Getter and Setter for flyingSprite
    public Sprite getFlyingSprite() {
        return flyingSprite;
    }

    public void setFlyingSprite(Sprite flyingSprite) {
        this.flyingSprite = flyingSprite;
    }

    // Getter and Setter for deadSprite
    public Sprite getDeadSprite() {
        return deadSprite;
    }

    public void setDeadSprite(Sprite deadSprite) {
        this.deadSprite = deadSprite;
    }

    // Getter and Setter for currentFrame
    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    // Getter and Setter for frameTime
    public float getFrameTime() {
        return frameTime;
    }

    public void setFrameTime(float frameTime) {
        this.frameTime = frameTime;
    }

    // Getter and Setter for timeToChange
    public float getTimeToChange() {
        return timeToChange;
    }

    public void setTimeToChange(float timeToChange) {
        this.timeToChange = timeToChange;
    }

    // Getter and Setter for random
    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public boolean isHasCollided(){
        return hasCollided;
    }

        // Create the Box2D body for the bird
    public void createBody(World world, float x, float y) {
        // Define the body and its properties
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;  // Dynamic body so it can be moved and affected by forces
        bodyDef.position.set(x, y);  // Set the initial position of the bird in the world

        // Create the body in the world
        body = world.createBody(bodyDef);
        body.setUserData(this);  // Add this line to set the Bird instance as UserData

        // Define the shape of the bird (circle for the bird body)
        CircleShape shape = new CircleShape();
        shape.setRadius(this.sprite.getWidth()/4);  // Set the radius of the circle (RADIUS should be defined elsewhere)

        // Define the fixture (collision properties) for the bird
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;  // Mass of the bird (adjust as needed)
        fixtureDef.friction = 0.5f; // Friction when it interacts with other objects
        fixtureDef.restitution = 0.2f; // How much it bounces when hitting other objects

        // Attach the fixture to the body
        body.createFixture(fixtureDef);

        // Dispose of the shape as it's no longer needed
        shape.dispose();
//        body.setActive(false);
    }

    // Method to launch the bird with a force
    public void launch(Vector2 force) {
        if (!launched) {
            body.setLinearVelocity(force);
            launched = true; // Set the bird as launched
        }
    }

    // Update the bird's position and rotation
    public void linkSpritetoBody() {
        if (body != null) {
            // Update the position and rotation of the sprite to match the Box2D body
            position.set(body.getPosition());
            sprite.setPosition(position.x - sprite.getWidth() / 2, position.y - sprite.getHeight() / 2);
            sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);  // Convert angle to degrees
        }
    }

    // Draw the bird's sprite
    public void draw(SpriteBatch batch) {
        spriteSetter(Gdx.graphics.getDeltaTime());
        if(!dead) {
            linkSpritetoBody();
            sprite.setScale(0.5f, 0.5f);
            sprite.draw(batch);
        }
    }

    public void reset() {
        if (body != null) {
            // Optionally reset the bird's body and position (useful when reusing birds)
            body.setLinearVelocity(0, 0);
            body.setAngularVelocity(0);
            body.setTransform(position.x, position.y, 0);
            launched = false;
        }
    }

    @Override
    public void takeDamage(int damage) {
        markDead();
        hasCollided = true;
        System.out.println("Bird Damage: "+ damage);
    }

    @Override
    public String getCollisionName() {
        return "Bird";
    }

    public void markDead() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                setDead(true);
            }
        }, 8);
    }

    public void update() {
        if (dead && !removed) {
            removed = true;
            world.destroyBody(body);
            body=null;
            System.out.println("Bird destroyed!");
        }
    }

    public void ability(){
    }

    // Get a random time to change the frame
    private float getRandomChangeTime() {
        return 0.1f + random.nextFloat() * 0.5f; // Random time between 0.1s and 0.6s
    }

    // Update the current frame based on elapsed time
    public void spriteSetter(float deltaTime) {
        frameTime += deltaTime;

        if(hasCollided){
            this.sprite = deadSprite;
            return;
        }

        if(launched) {
            this.sprite = flyingSprite;
            return;
        }


        // Change the frame randomly based on time
        if (frameTime >= timeToChange) {
            currentFrame = random.nextInt(BirdSprites.length); // Randomly select a frame
            frameTime = 0f; // Reset the timer
            timeToChange = getRandomChangeTime(); // Get a new random time to change
        }

        this.sprite= BirdSprites[currentFrame];
    }
}
