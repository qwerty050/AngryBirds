package io.angrybirds.utils;

import com.badlogic.gdx.physics.box2d.*;

import static io.angrybirds.Main.PPM;

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
        bodyDef.position.set(x/PPM, y/PPM);  // Set the body's position


        Body body = world.createBody(bodyDef);
        body.setUserData(this);  // Add this line to set the Bird instance as UserData


        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/PPM , height/PPM );  // Half width and height for Box2D coordinates


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;


        body.createFixture(fixtureDef);
        shape.dispose();  // Dispose the shape after creating fixture

        return body;
    }

    public void createScreenBoundaries(World world, float screenWidth, float screenHeight) {
        // Bottom boundary
        createEdge(world, 0, 0, screenWidth, 0);

        // Top boundary
        createEdge(world, 0, screenHeight, screenWidth, screenHeight);

        // Left boundary
        createEdge(world, 0, 0, 0, screenHeight);

        // Right boundary
        createEdge(world, screenWidth, 0, screenWidth, screenHeight);
    }


    private Body createEdge(World world, float x1, float y1, float x2, float y2) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 0);

        Body body = world.createBody(bodyDef);

        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(x1/PPM, y1/PPM, x2/PPM, y2/PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = edgeShape;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.3f;

        body.createFixture(fixtureDef);
        edgeShape.dispose();

        return body;
    }

    @Override
    public void takeDamage(int damage) {

    }

    @Override
    public String getCollisionName() {
        return "Platform";
    }

    public Body getBody() {
        return body;
    }
}
