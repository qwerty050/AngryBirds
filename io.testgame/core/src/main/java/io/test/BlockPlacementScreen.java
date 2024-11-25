package io.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class BlockPlacementScreen implements Screen {
    private Main game;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Platform platform;
    private OrthographicCamera camera;
    private ArrayList<Block> blocks;
    private Block currentBlock;

    public BlockPlacementScreen(Main game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;
    }

    @Override
    public void show() {
        initWorld();
        initBlocks();


    }


    public void update(float v){
        world.step(1 / 60f, 8, 3);

        handleMovement();
        currentBlock.getBody().setTransform(new Vector2(x,y), rotation);
        System.out.println(x+" "+y+" "+rotation);
    }
    @Override
    public void render(float v) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        update(v);
        debugRenderer.render(world, camera.combined);

        batch.begin();
        for(Block b : blocks){
            b.draw(batch);
        }
        batch.end();

    }

    @Override
    public void resize(int i, int i1) {

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
            x -= 100 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            rotation += 1 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            rotation -= 1 * Gdx.graphics.getDeltaTime();
        }
    }


    private void initWorld() {
        world = new World(new Vector2(0, -30f), true);  // Negative Y for gravity
        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 720, 480);  // Set the camera's viewport size
        camera.update();
    }


    private void initBlocks() {
        platform = new Platform(world,360, 20, 720, 40);
        BlockMaker blockMaker =new BlockMaker(world);
        currentBlock=blockMaker.WoodLong(0,0,0);
        blocks = new ArrayList<>(asList(
            blockMaker.WoodLong(321,95,1.56f),
            
            currentBlock
        ));
    }
}
