package io.test;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Block implements CollisionHandler.Damageable {
    private Body body;
    private Sprite sprite;
    private Vector2 position;
    private float health;
    private boolean destroyed;
    private boolean markedForDestruction;
    private boolean ready;
    private World world;

    public Block(float health, World world,float x, float y, float width, float height) {
        this.health = health;
        this.world = world;
        this.destroyed = false;
        this.markedForDestruction = false;
        this.ready = false;
        position = new Vector2(x, y);
        this.setBody(createBox(x, y, width, height));
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Body getBody() {
        return body;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public void ready(){
        ready = true;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public void takeDamage(int damage) {
        if (!destroyed && !markedForDestruction && ready) {
            health -= damage;
            System.out.println("Block health: " + health);

            if (health <= 0) {
                markForDestruction();
            }
        }
    }

    @Override
    public String getCollisionName() {
        return "Block";
    }

    private void markForDestruction() {
        markedForDestruction = true;
        System.out.println("Block marked for destruction!");
    }

    // Call this method from your game's update loop, not from collision callbacks
    public void update() {
        if (markedForDestruction && !destroyed) {
            destroyed = true;
            world.destroyBody(body);
            body = null;
            System.out.println("Block destroyed!");
        }
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public boolean isMarkedForDestruction() {
        return markedForDestruction;
    }

    public Body createBox(float x, float y, float width, float height) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);  // Set the body's position


        Body body = world.createBody(bodyDef);
        body.setUserData(this);  // Add this line to set the Bird instance as UserData

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width /2, height/2 );  // Half width and height for Box2D coordinates


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        body.createFixture(fixtureDef);
        shape.dispose();  // Dispose the shape after creating fixture

        return body;
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
        if(!destroyed) {
            linkSpritetoBody();
            sprite.setScale(0.5f, 0.5f);
            sprite.draw(batch);
        }
    }
}
