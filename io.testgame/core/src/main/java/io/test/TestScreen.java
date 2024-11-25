package io.test;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;

import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;


import static java.util.Arrays.asList;

public class TestScreen implements Screen {
    private Main game;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Body birdBody;
    private Bird bird;
    private OrthographicCamera camera;
    private SlingShot SlingShot;
    private CollisionHandler collisionHandler;
    private ArrayList<Block> blocks;
    private ArrayList<Bird> birds;
    private Bird yb;
    Block wd;

    public TestScreen(Main game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;
    }

    @Override
    public void show() {

        initWorld();
        initBlocks();
        yb=new BlueBird(world, 1000, 100);
        birds= new ArrayList<Bird>(asList(yb));
//            new BlackBird(world, 100, 50),
//            new YellowBird(world, 50, 70)));
        SlingShot=new SlingShot(100,150,birds);

    }


    public void update(float delta) {
        world.step(1 / 60f, 8, 3);
        collisionHandler.update(delta);
        for(Block b:blocks)  b.update();

        for(Bird b:birds){
            if(b.isLaunched()) b.update();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            yb.ability();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            for (Block b : blocks) {
                b.ready();
            }
            System.out.println("All Blocks Ready");
        }


    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        update(delta);
        SlingShot.setDebugMode(true);
        debugRenderer.render(world, camera.combined);
        batch.begin();
//        for(Bird b: birds) b.draw(batch);
//        wd.draw(batch);
        SlingShot.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        // Dispose resources when the screen is closed
        world.dispose();
        debugRenderer.dispose();
    }

    // Utility function to create Box2D bodies (static or dynamic)
    private Body createBox(float x, float y, float width, float height, boolean isDynamic) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isDynamic ? BodyDef.BodyType.DynamicBody : BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);  // Set the body's position


        Body body = world.createBody(bodyDef);


        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2);  // Half width and height for Box2D coordinates


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = isDynamic ? 1f : 0f;  // Only dynamic bodies have density

        body.createFixture(fixtureDef);
        shape.dispose();  // Dispose the shape after creating fixture

        return body;
    }

    private void initWorld() {
        world = new World(new Vector2(0, -30f), true);  // Negative Y for gravity
        debugRenderer = new Box2DDebugRenderer();

        collisionHandler = new CollisionHandler();
        world.setContactListener(collisionHandler);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 720, 480);  // Set the camera's viewport size
        camera.update();
    }

    private void initBlocks() {
        Body platform = createBox(360, 20, 720, 40, false);
//
//        BlockMaker blockMaker= new BlockMaker(world);
//        wd= blockMaker.WoodLong(0,0,0);
//        wd.getBody().setActive(false);
        blocks=new ArrayList<>(asList(
            new WoodBlock(world,400, 90, 10, 50),
            new WoodBlock(world,440, 90, 10, 50),
            new WoodBlock(world,420, 90+50, 50, 10),
            new WoodBlock(world,400+70, 90, 10, 50),
            new WoodBlock(world,440+70, 90, 10, 50),
            new WoodBlock(world,420+70, 90+50, 50, 10),
            new WoodBlock(world,400+35, 90+80, 10, 50),
            new WoodBlock(world,440+35, 90+80, 10, 50),
            new WoodBlock(world,420+35, 90+130, 50, 10),

              new WoodBlock(world,400+140, 90, 10, 50),
            new WoodBlock(world,440+140, 90, 10, 50),
            new WoodBlock(world,420+140, 90+50, 50, 10),
            new WoodBlock(world,400+70+140, 90, 10, 50),
            new WoodBlock(world,440+70+140, 90, 10, 50),
            new WoodBlock(world,420+70+140, 90+50, 50, 10),
            new WoodBlock(world,400+35+140, 90+80, 10, 50),
            new WoodBlock(world,440+35+140, 90+80, 10, 50),
            new WoodBlock(world,420+35+140, 90+130, 50, 10),

            new WoodBlock(world,550, 90, 10, 50)
//            wd
            ));
    }

    int x;
    int y;
    float rotation;
    private void handleMovement() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y += 100 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y -= 100 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x += 100 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            y -= 100 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            rotation += 1 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            rotation -= 1 * Gdx.graphics.getDeltaTime();
        }
    }

}
