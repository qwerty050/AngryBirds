package io.angrybirds.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import io.angrybirds.*;
import io.angrybirds.birds.Bird;
import io.angrybirds.blocks.Block;
import io.angrybirds.pigs.Pig;


import java.util.ArrayList;

import static java.util.Arrays.asList;

public class LevelScreen implements Screen {
    private Main game;
    private SpriteBatch batch;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    private CollisionHandler collisionHandler;

    private SlingShot slingShot;
    private ArrayList<Block> blocks;
    private ArrayList<Bird> birds;
    private ArrayList<Pig> pigs;

    PauseScreen pauseScreen;
    private Sprite pause;
    private Sprite altpause;
    private Sprite bluebutton;
    private Sprite greybutton;
    private Sprite redbutton;
    private Texture background;
    private BitmapFont font;

    private boolean gameOver;
    private boolean paused;
    private boolean won;
    private boolean count;
    private int level;
    private int score;

    public LevelScreen(Main game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;
        gameOver = false;
        paused = false;
        won = false;
    }


    public LevelScreen(Main game, SpriteBatch batch, int level) {
        this.game = game;
        this.batch = batch;

        this.level = level;

        gameOver = false;
        paused = false;
        won = false;
        count = false;

        pauseScreen = new PauseScreen(game, batch,this, level);
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        loadSprites();
        initWorld();
        initLevel();
    }



    @Override
    public void show() {

    }


    public void update(float delta) {
        world.step(1 / 60f, 8, 3);
        collisionHandler.update(delta);


        for(Block b:blocks)  score+=b.update();

        for(Pig p:pigs)  score+=p.update();

        for(Bird b:birds){
            if(b.isLaunched()) b.update();
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                b.ability();
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            for (Block b : blocks) {
                b.setReady(true);
            }
            System.out.println("All Blocks Ready");
        }
        update();
        handleEndGame();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        if (paused) {
            return; // Skip the rest of the update/rendering when paused
        }


        update(delta);
        slingShot.setDebugMode(true);
//        debugRenderer.render(world, camera.combined);

        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        batch.begin();

        batch.draw(background, 0, -20,720,500);

        handleButton(mouseX,mouseY,15,380,85,85,pause,altpause, this::pause2);

        for(Pig p:pigs) p.draw(batch);

        for(Block block:blocks) block.draw(batch);
        slingShot.draw(batch);

        for(Bird b: birds) b.draw(batch);

        if(gameOver) triggerEndGame(mouseX,mouseY);

        font.getData().setScale(0.8f);
        font.draw(batch,"SCORE:"+ score,540,450);

        batch.end();
        System.out.println(x2+" "+y2);
//        printValues();
    }

    private void pauseGame() {
        paused = true;
        game.setScreen(pauseScreen);  // Switch to PauseScreen
    }

    public void resumeGame() {
        paused = false;
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
    }

    private void initWorld() {
        world = new World(new Vector2(0,-30f), true);
        debugRenderer = new Box2DDebugRenderer();

        collisionHandler = new CollisionHandler();
        world.setContactListener(collisionHandler);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 720, 480);  // Set the camera's viewport size
        camera.update();
    }

    private void initLevel() {
        Platform platform = new Platform(world,360, 20, 720, 65);
        BlockMaker blockMaker = new BlockMaker(world);
        LevelMaker levelMaker =new LevelMaker(blockMaker, world);
        if(level == 1){
            blocks=levelMaker.level1Blocks();
            pigs=levelMaker.level1Pigs();
            birds=levelMaker.level1Birds();
            slingShot=new SlingShot(200,160,birds);
        }
        else if(level == 2){
            blocks=levelMaker.level2Blocks();
            pigs=levelMaker.level2Pigs();
            birds=levelMaker.level2Birds();
            slingShot=new SlingShot(180,160,birds);
        }
        else if(level == 3){
            blocks=levelMaker.level3Blocks();
            pigs=levelMaker.level3Pigs();
            birds=levelMaker.level3Birds();
            slingShot=new SlingShot(200,150,birds);
        }

    }

    private void handleEndGame(){
        boolean allpigsDead = true;
        boolean allbirdsDead = true;

        for(Pig p:pigs)
            if (!p.isDestroyed()) {
                allpigsDead = false;
                break;
            }

        for(Bird b : birds)
            if(!b.isDead()) {
                allbirdsDead = false;
                break;
            }

        if(allpigsDead){
            gameOver = true;
            won=true;
            return;
        }

        if(allbirdsDead){
            gameOver = true;
            won=false;
            return;
        }
    }

    private void triggerEndGame(int mouseX, int mouseY){
        if(won){
            handleButton(mouseX,mouseY,530,25,155,50,bluebutton, greybutton, this::win);
            font.getData().setScale(0.6f);
            font.draw(batch,"End Level",553,64);
            font.getData().setScale(1f);
            return;
        }

        handleButton(mouseX,mouseY,530,25,155,50,bluebutton, greybutton, this::restart);
        handleButton(mouseX,mouseY,370,25,155,50,redbutton, greybutton, this::lose);
        font.getData().setScale(0.6f);
        font.draw(batch,"Give Up",404,64);
        font.draw(batch, "Try Again", 404+149,64);
        font.getData().setScale(1f);

    }

    private void win(){
        if(!count) {
            count=true;
            for (Bird b : birds) {
                if (!b.isDead()) score += 5000;
            }
        }
        game.setScreen(new EndScreen(game,batch,getStars(),this, level));
    }

    private int getStars() {
        if(level==1){
            if(score==0) return 0;
            else if(score>45000) return 3;
            else if(score>30000) return 2;
            else if(score>5000) return 1;
        }
        else if(level==2){
            if(score==0) return 0;
            else if(score>60000) return 3;
            else if(score>45000) return 2;
            else if(score>5000) return 1;
        }
        else if(level==3){
            if(score==0) return 0;
            else if(score>100000) return 3;
            else if(score>65000) return 2;
            else if(score>5000) return 1;
        }
        return 0;
    }

    private void restart() {
        this.dispose();
        game.setScreen(new LevelScreen(game,batch,level));
    }



    private void lose() {
        game.setScreen(new EndScreen(game,batch,0,this, level));
    }
    // Declare your variables
    private float x1 = 0, y1 = 0;
    private float x2 = 400, y2 = 400;

    public void update() {
        // Get deltaTime from LibGDX (time since the last frame)
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Check if each key is pressed just this frame and update variables accordingly, multiplied by deltaTime
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            y1 += 10 * deltaTime;  // Increase y1 when up arrow is pressed
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            y1 -= 10 * deltaTime;  // Decrease y1 when down arrow is pressed
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            x1 -= 10 * deltaTime;  // Decrease x1 when left arrow is pressed
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            x1 += 10 * deltaTime;  // Increase x1 when right arrow is pressed
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            y2 += 10 * deltaTime;  // Increase y2 when 'W' is pressed
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            y2 -= 10 * deltaTime;  // Decrease y2 when 'S' is pressed
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            x2 -= 10 * deltaTime;  // Decrease x2 when 'A' is pressed
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            x2 += 10 * deltaTime;  // Increase x2 when 'D' is pressed
        }
    }

    public void printValues() {
        System.out.println("x1: " + x1 + ", y1: " + y1+" "+ "x2: " + x2 + ", y2: " + y2);
    }

    private void pause2(){
        game.setScreen(pauseScreen);
    }

    private void loadSprites(){
        pause=new Sprite(new Texture("pause.png"));
        altpause=new Sprite(new Texture("altpause.png"));
        bluebutton=new Sprite(new Texture("greenbutton.png"));
        greybutton=new Sprite(new Texture("greybutton.png"));
        redbutton=new Sprite(new Texture("redbutton.png"));
        background=new Texture("playscreenbg.jpg");
    }

    private void handleButton(int mouseX, int mouseY, int x, int y, int width, int height,
                              Sprite Button, Sprite altButton,
                              Runnable onClickFunction) {

        if(hover(mouseX,mouseY,x,y,Button)){
            batch.draw(altButton,x,y,width,height);
            if(Gdx.input.justTouched()){
                onClickFunction.run();
            }
        }
        else{
            batch.draw(Button,x,y,width,height);
        }
    }

    private boolean hover(int mouseX, int mouseY, int x, int y, Sprite button){
        return mouseX >= x && mouseX <= x + button.getWidth() &&
                mouseY >= y && mouseY <= y + button.getHeight();
    }

}
