package io.test;


import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

public abstract class Bird implements CollisionHandler.Damageable {
    private Body body;          // Box2D Body that will represent the bird in the physics world.
    private Sprite sprite;      // The visual representation of the bird.
    private Vector2 position;   // The position of the bird (not used directly since Box2D handles it).
    private boolean launched;   // Whether the bird has been launched.
    private World world;
    private boolean dead;
    private boolean removed;

    private float RADIUS = 10f;  // Radius of the bird (Box2D uses physics units).

    // Constructor
    public Bird(World world, float x, float y) {
        this.position = new Vector2(x, y);
        this.sprite = new Sprite();
        this.launched = false;
        this.world = world;

        createBody(world, x, y);
        body.setActive(false);
    }

    // Create the Box2D body for the bird
    private void createBody(World world, float x, float y) {
        // Define the body and its properties
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;  // Dynamic body so it can be moved and affected by forces
        bodyDef.position.set(x, y);  // Set the initial position of the bird in the world

        // Create the body in the world
        body = world.createBody(bodyDef);
        body.setUserData(this);  // Add this line to set the Bird instance as UserData

        // Define the shape of the bird (circle for the bird body)
        CircleShape shape = new CircleShape();
        shape.setRadius(RADIUS);  // Set the radius of the circle (RADIUS should be defined elsewhere)

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
    }


    // Method to launch the bird with a force
    public void launch(Vector2 force) {
        if (!launched) {
            // Apply the force to the bird's body in the Box2D world
            body.setLinearVelocity(force);
            launched = true; // Set the bird as launched
            markDead();
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
        if(!dead) {
            linkSpritetoBody();
            sprite.setScale(0.5f, 0.5f);
            sprite.draw(batch);
        }
    }

    // Getter methods for the bird's position and velocity
    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return body.getLinearVelocity();
    }

    public Body getBody() {
        return body;
    }

    public boolean isLaunched() {
        return launched;
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

    public Sprite getSprite() {
        return sprite;
    }

    public void setLaunched(boolean launched) {
        this.launched = launched;
    }

    public void setPosition(Vector2 position) {
        body.setTransform(position.x, position.y, 0);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public void takeDamage(int damage) {
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
                // Your code to run after delay goes here
                isDead();
                System.out.println("10 seconds have passed!");
            }
        }, 10); // 10 second delay
    }

    private void isDead() {
        this.dead = true;
    }

    // Call this method from your game's update loop, not from collision callbacks
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
}
