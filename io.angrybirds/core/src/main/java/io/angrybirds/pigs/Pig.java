package io.angrybirds.pigs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.angrybirds.CollisionHandler;

import java.util.Random;

public class Pig implements CollisionHandler.Damageable{
    private Body body;
    private Vector2 position;
    private World world;
    private boolean destroyed;
    private boolean markedForDestruction;
    private boolean pointsCounted;
    private Sprite sprite;
    private Sprite[] CurrentSprites;
    private Sprite[] fullHealthSprites;
    private Sprite[] dmg1Sprites;
    private Sprite[] dmg2Sprites;
    private int health;
    private int currentFrame;
    private float frameTime;
    private float timeToChange; // Time until the next random change
    private Random random;

    private int PIG_HEALTH;
    private int PIG_POINTS;

    public Pig(World world,int x, int y, int health, int points) {
        position = new Vector2(x, y);
        this.world = world;

        this.destroyed = false;
        this.markedForDestruction = false;
        this.pointsCounted = false;

        this.health=health;
        PIG_HEALTH=health;
        PIG_POINTS=points;

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

    // Getter and Setter for position
    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    // Getter and Setter for world
    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    // Getter and Setter for destroyed
    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    // Getter and Setter for markedForDestruction
    public boolean isMarkedForDestruction() {
        return markedForDestruction;
    }

    public void setMarkedForDestruction(boolean markedForDestruction) {
        this.markedForDestruction = markedForDestruction;
    }

    // Getter and Setter for sprite
    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    // Getter and Setter for currentSprites
    public Sprite[] getCurrentSprites() {
        return CurrentSprites;
    }

    public void setCurrentSprites(Sprite[] currentSprites) {
        this.CurrentSprites = currentSprites;
    }

    // Getter and Setter for fullHealthSprites
    public Sprite[] getFullHealthSprites() {
        return fullHealthSprites;
    }

    public void setFullHealthSprites(Sprite[] fullHealthSprites) {
        this.fullHealthSprites = fullHealthSprites;
    }

    // Getter and Setter for dmg1Sprites
    public Sprite[] getDmg1Sprites() {
        return dmg1Sprites;
    }

    public void setDmg1Sprites(Sprite[] dmg1Sprites) {
        this.dmg1Sprites = dmg1Sprites;
    }

    // Getter and Setter for dmg2Sprites
    public Sprite[] getDmg2Sprites() {
        return dmg2Sprites;
    }

    public void setDmg2Sprites(Sprite[] dmg2Sprites) {
        this.dmg2Sprites = dmg2Sprites;
    }

    // Getter and Setter for health
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

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
        shape.setRadius(this.sprite.getWidth()*0.25f);  // Set the radius of the circle (RADIUS should be defined elsewhere)

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

    public int update() {
        if (markedForDestruction && !destroyed) {
            destroyed = true;
            world.destroyBody(body);
            body = null;
        }

        if(destroyed && !pointsCounted) {
            pointsCounted = true;
            return PIG_POINTS;
        }
        return 0;

    }

    public void linkSpritetoBody() {
        if (body != null) {
            // Update the position and rotation of the sprite to match the Box2D body
            position.set(body.getPosition());
            sprite.setPosition(position.x - sprite.getWidth()/2 , position.y - sprite.getHeight()/2);
            sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);  // Convert angle to degrees
        }
    }

    public void draw(SpriteBatch batch){

        if(!destroyed){
            linkSpritetoBody();
            spriteSetter(Gdx.graphics.getDeltaTime());
            sprite.setScale(0.5f, 0.5f);
            sprite.draw(batch);
        }
    }

    // Get a random time to change the frame
    private float getRandomChangeTime() {
        return 0.1f + random.nextFloat() * 0.5f; // Random time between 0.1s and 0.6s
    }


    @Override
    public void takeDamage(int damage) {
        if (!destroyed && !markedForDestruction) {
            health -= damage;
            System.out.println("Pig health: " + health);

            if (health <= 0) {
                markForDestruction();
            }
        }
    }

    private void markForDestruction() {
        markedForDestruction=true;
    }

    @Override
    public String getCollisionName() {
        return "Pig";
    }

    // Update the current frame based on elapsed time
    public void spriteSetter(float deltaTime) {
        currentSpriteSetter();
        frameTime += deltaTime;
        // Change the frame randomly based on time
        if (frameTime >= timeToChange) {
            currentFrame = random.nextInt(CurrentSprites.length); // Randomly select a frame
            frameTime = 0f; // Reset the timer
            timeToChange = getRandomChangeTime(); // Get a new random time to change
        }

        this.sprite= CurrentSprites[currentFrame];
    }

    private void currentSpriteSetter() {
        if(health>PIG_HEALTH*3/4) CurrentSprites=fullHealthSprites;
        else if(health>PIG_HEALTH*2/4) CurrentSprites=dmg1Sprites;
        else if(health>PIG_HEALTH/4) CurrentSprites=dmg2Sprites;
    }
}
