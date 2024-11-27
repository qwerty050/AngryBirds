package io.angrybirds;

import com.badlogic.gdx.physics.box2d.*;

public class Platform implements CollisionHandler.Damageable {
    private World world;
    private Body body;

    public Platform(World world, float x, float y, float width, float height) {
        this.world = world;
        this.body=createBox(x, y, width, height);
    }

    public Body createBox(float x, float y, float width, float height) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);  // Set the body's position


        Body body = world.createBody(bodyDef);
        body.setUserData(this);  // Add this line to set the Bird instance as UserData


        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width , height );  // Half width and height for Box2D coordinates


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;


        body.createFixture(fixtureDef);
        shape.dispose();  // Dispose the shape after creating fixture

        return body;
    }

    @Override
    public void takeDamage(int damage) {

    }

    @Override
    public String getCollisionName() {
        return "Platform";
    }
}
