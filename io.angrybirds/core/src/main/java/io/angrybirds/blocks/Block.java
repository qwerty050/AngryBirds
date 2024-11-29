package io.angrybirds.blocks;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.angrybirds.utils.CollisionHandler;

import java.io.Serializable;

import static io.angrybirds.Main.PPM;

public class Block implements CollisionHandler.Damageable, Serializable {
    private int ID;

    private  Body body;
    private  Sprite  sprite;
    private Vector2 position;
    private float health;
    private boolean destroyed;
    private boolean markedForDestruction;
    private boolean ready;
    private boolean pointsCounted;
    private World world;
    private  Sprite FullHealth;
    private  Sprite damaged1;
    private  Sprite damaged2;
    private  Sprite damaged3;

    private final int BLOCK_POINTS;
    private final Float BLOCK_HEALTH;

    public Block(float health, World world,float x, float y, float width, float height, int points) {
        this.health = health;
        BLOCK_HEALTH=health;
        BLOCK_POINTS=points;
        this.world = world;
        this.destroyed = false;
        this.markedForDestruction = false;
        this.ready = true;
        this.pointsCounted = false;
        position = new Vector2(x, y);
        this.body=(createBox(x, y, width, height));
    }

    // Getter and Setter for sprite
    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Body getBody(){
        return body;
    }

    public void setBody(Body body){
        this.body = body;
    }

    // Getter and Setter for position
    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    // Getter and Setter for health
    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
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

    // Getter and Setter for ready
    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    // Getter and Setter for world
    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    // Getter and Setter for fullHealth
    public Sprite getFullHealth() {
        return FullHealth;
    }

    public void setFullHealth(Sprite fullHealth) {
        this.FullHealth = fullHealth;
    }

    // Getter and Setter for damaged1
    public Sprite getDamaged1() {
        return damaged1;
    }

    public void setDamaged1(Sprite damaged1) {
        this.damaged1 = damaged1;
    }

    // Getter and Setter for damaged2
    public Sprite getDamaged2() {
        return damaged2;
    }

    public void setDamaged2(Sprite damaged2) {
        this.damaged2 = damaged2;
    }

    // Getter and Setter for damaged3
    public Sprite getDamaged3() {
        return damaged3;
    }

    public void setDamaged3(Sprite damaged3) {
        this.damaged3 = damaged3;
    }

    // Getter for BLOCK_HEALTH (since it's final, no setter is provided)
    public Float getBLOCK_HEALTH() {
        return BLOCK_HEALTH;
    }



    @Override
    public void takeDamage(int damage) {
        if (!destroyed && !markedForDestruction && ready) {
            health -= damage;
//            System.out.println("Block health: " + health);

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
//        System.out.println("Block marked for destruction!");
    }

    // Call this method from your game's update loop, not from collision callbacks
    public int update() {
        if (markedForDestruction && !destroyed) {
            destroyed = true;
            world.destroyBody(body);
            body = null;
        }

        if(destroyed && !pointsCounted) {
            pointsCounted = true;
            return BLOCK_POINTS;
        }
        return 0;
    }

    public Body createBox(float x, float y, float width, float height) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x/PPM, y/PPM);  // Set the body's position


        Body body = world.createBody(bodyDef);
        body.setUserData(this);  // Add this line to set the Bird instance as UserData

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width /(2*PPM), height/(2*PPM) );  // Half width and height for Box2D coordinates


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
            // Convert Box2D body position to pixel coordinates using PPM
            position.set(
                    body.getPosition().x * PPM,
                    body.getPosition().y * PPM
            );

            // Adjust sprite position, accounting for sprite's center
            sprite.setPosition(
                    position.x - sprite.getWidth() / 2,
                    position.y - sprite.getHeight() / 2
            );

            // Rotation remains the same
            sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        }
    }

    // Draw the bird's sprite
    public void draw(SpriteBatch batch) {
        if(!destroyed) {
            sprite=currentSprite();
            linkSpritetoBody();
            sprite.setScale(0.5f, 0.5f);
            sprite.draw(batch);
        }
    }

    public Sprite currentSprite() {
        if(health>BLOCK_HEALTH*4/5) return FullHealth;
        else if (health>BLOCK_HEALTH*3/5) return damaged1;
        else if (health>BLOCK_HEALTH*2/5) return damaged2;
        else if (health>BLOCK_HEALTH*1/5) return damaged3;
        return FullHealth;
    }

    public int getID() {
        return ID;
    }
    public void setID(int a){
        ID=a;
    }

}