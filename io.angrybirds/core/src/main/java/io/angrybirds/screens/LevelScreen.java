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
import io.angrybirds.pigs.SmallPig;
import io.angrybirds.serial.SaveGame;
import io.angrybirds.utils.*;
import io.angrybirds.birds.Bird;
import io.angrybirds.blocks.Block;
import io.angrybirds.pigs.Pig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.angrybirds.Main.PPM;
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
    private Texture background;
    private BitmapFont font;

    private boolean gameOver;
    private boolean won;
    private boolean count;

    private int level;
    private int score;

    private List<BasicButton> buttons;
    boolean pp=false;


    public LevelScreen(Main game, SpriteBatch batch, int level) {
        this.game = game;
        this.batch = batch;

        this.level = level;

        gameOver = false;
        won = false;
        count = false;


        pauseScreen = new PauseScreen(game, batch,this, level);
        font = new BitmapFont(Gdx.files.internal("font.fnt"));

        initButtons();
        initWorld();
        initLevel();
        SmallPig pig=new SmallPig(world,720/2,480/2);
        pig.getBody().setActive(false);
    }

    public LevelScreen(World world, Main game, SpriteBatch batch,  ArrayList<Block> blocks, ArrayList<Pig> pigs, ArrayList<Bird> birds, SlingShot slingShot, int level) {
        this.game = game;
        this.batch = batch;

        this.level=level;

        gameOver = false;
        won = false;
        count = false;

        pauseScreen = new PauseScreen(game, batch,this, 1);
        font = new BitmapFont(Gdx.files.internal("font.fnt"));

        initButtons();

        this.world=world;
        debugRenderer = new Box2DDebugRenderer();

        collisionHandler = new CollisionHandler();
        world.setContactListener(collisionHandler);

        camera = new OrthographicCamera(
                Gdx.graphics.getWidth() / (PPM),
                Gdx.graphics.getHeight() / (PPM)
        );
        camera.position.set(
                Gdx.graphics.getWidth() / (2f * PPM),
                Gdx.graphics.getHeight() / (2f * PPM),
                0
        );
        camera.update();

        this.blocks = blocks;
        this.pigs = pigs;
        this.birds = birds;
        this.slingShot = slingShot;
        Platform platform = new Platform(world,360, 20, 720, 65);
        platform.createScreenBoundaries(world, 720, 480);

    }

    @Override
    public void show() {
    }

    public void update(float delta) throws IOException {
        world.step(1 / 60f, 8, 3);
        collisionHandler.update(delta);

        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        for(Block b:blocks)  score+=b.update();

        for(Pig p:pigs)  score+=p.update();

        for(BasicButton b: buttons) b.update(mouseX, mouseY);

        for(Bird b:birds) b.update();

//        System.out.println(blocks.get(0).getBody().getPosition().x*PPM);
//        System.out.println(blocks.get(0).getBody().getAngle());

//        System.out.println(pigs.get(0).getBody().getPosition().x);
        handleEndGame();
        camera.update();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if(Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            pp=!pp;
        }

        if(!pp) {
            try {
                update(delta);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        slingShot.setDebugMode(true);

        debugRenderer.render(world, camera.combined);

        batch.begin();

        batch.draw(background, 0, -20,720,500);

        for(Pig p:pigs) p.draw(batch);

        for(Block block:blocks) block.draw(batch);
        slingShot.draw(batch);

        for(Bird b: birds) b.draw(batch);

        for(BasicButton b: buttons) b.drawZoomChange(batch);

        if(gameOver) triggerEndGame();

        font.getData().setScale(0.8f);
        font.draw(batch,""+ score,540,450);

        batch.end();
    }

    @Override
    public void pause() {
        game.setScreen(pauseScreen);
    }

    @Override
    public void resume() {
        SaveGame save=new SaveGame(blocks,pigs,birds,slingShot,level);
        try {
            save.saveGame();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("saved!!!");
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
        world = new World(new Vector2(0,-9.8f), true);
        debugRenderer = new Box2DDebugRenderer();

        collisionHandler = new CollisionHandler();
        world.setContactListener(collisionHandler);

        camera = new OrthographicCamera(
                Gdx.graphics.getWidth() / (PPM),
                Gdx.graphics.getHeight() / (PPM)
        );
        camera.position.set(
                Gdx.graphics.getWidth() / (2f * PPM),
                Gdx.graphics.getHeight() / (2f * PPM),
                0
        );
        camera.update();
    }

    private void initLevel() {
        Platform platform = new Platform(world,360, 20, 720, 65);
        platform.createScreenBoundaries(world, 720, 480);

        BlockMaker blockMaker = new BlockMaker(world);
        LevelMaker levelMaker =new LevelMaker(blockMaker, world);
        if(level == 1){
            blocks=levelMaker.level1Blocks();
            pigs=levelMaker.level1Pigs();
            birds=levelMaker.level1Birds();
            slingShot=new SlingShot(100,160,birds);
        }
        else if(level == 2){
            blocks=levelMaker.level2Blocks();
            pigs=levelMaker.level2Pigs();
            birds=levelMaker.level2Birds();
            slingShot=new SlingShot(100,160,birds);
        }
        else if(level == 3){
            blocks=levelMaker.level3Blocks();
            pigs=levelMaker.level3Pigs();
            birds=levelMaker.level3Birds();
            slingShot=new SlingShot(100,160,birds);
        }
    }

    private void initButtons(){
        background=new Texture("playscreenbg.jpg");

        buttons=new ArrayList<>(asList(
                new BasicButton(new Sprite(new Texture("pause.png")),new Sprite(new Texture("altpause.png")),15,380,85,85,this::pause),
                new BasicButton(new Sprite(new Texture("greenbutton.png")),new Sprite(new Texture("greybutton.png")),530,25,155,50, this::win),
                new BasicButton(new Sprite(new Texture("greenbutton.png")),new Sprite(new Texture("greybutton.png")),530,25,155,50, this::restart),
                new BasicButton(new Sprite(new Texture("redbutton.png")),new Sprite(new Texture("greybutton.png")),370,25,155,50, this::lose)
                ));
        buttons.get(1).setActive(false);
        buttons.get(2).setActive(false);
        buttons.get(3).setActive(false);
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

    private void triggerEndGame(){
        if(won){
            buttons.get(1).setActive(true);
            buttons.get(2).setActive(false);
            buttons.get(3).setActive(false);
            font.getData().setScale(0.6f);
            font.draw(batch,"End Level",553,64);
            font.getData().setScale(1f);
            return;
        }

        buttons.get(2).setActive(true);
        buttons.get(3).setActive(true);
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
        return LevelMaker.getStars(level,score);
    }

    private void restart() {
        this.dispose();
        game.setScreen(new LevelScreen(game,batch,level));
    }

    private void lose() {
        game.setScreen(new EndScreen(game,batch,0,this, level));
    }

    private void bodyMover(Body body){
        boolean alreadyInactive= !body.isActive();
        if(!alreadyInactive) body.setActive(false);

        int x=0;
        int y=0;

        if(Gdx.input.isButtonJustPressed(Input.Keys.UP)) y+= (int) (1*Gdx.graphics.getDeltaTime());
        if(Gdx.input.isButtonJustPressed(Input.Keys.DOWN)) y-= (int) (1*Gdx.graphics.getDeltaTime());
        if(Gdx.input.isButtonJustPressed(Input.Keys.RIGHT)) x-= (int) (1*Gdx.graphics.getDeltaTime());
        if(Gdx.input.isButtonJustPressed(Input.Keys.LEFT)) x+= (int) (1*Gdx.graphics.getDeltaTime());

        body.setTransform(x,y,0);

        if(Gdx.input.isButtonJustPressed(Input.Keys.ENTER)) System.out.println(x+" "+y);
    }

}
