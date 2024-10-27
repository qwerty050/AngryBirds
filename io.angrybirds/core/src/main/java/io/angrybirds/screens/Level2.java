package io.angrybirds.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import io.angrybirds.*;
import io.angrybirds.birds.Bird;
import io.angrybirds.birds.WhiteBird;
import io.angrybirds.birds.YellowBird;
import io.angrybirds.blocks.Block;
import io.angrybirds.pigs.LargePig;
import io.angrybirds.pigs.MedPig;
import io.angrybirds.pigs.Pig;

import java.util.ArrayList;
import java.util.Arrays;

public class Level2 implements Screen {
    private final SpriteBatch batch;
    private Main game;
    private Texture background;
    private Sprite pause;
    private Sprite altpause;
    ArrayList<Bird> birds;
    Bird bird1;
    Bird bird2;
    Bird bird3;
    Texture birdSheet;
    io.angrybirds.SlingShot SlingShot;
    PauseScreen pauseScreen;
    private int numStars;
    private Sprite bluebutton;
    private Sprite greybutton;
    private Sprite redbutton;
    private ArrayList<Block> blocks;
    Block block;
    SpriteMaker spriteMaker;
    private ArrayList<Pig> pigs;
    private BitmapFont font;

    public Level2(Main game, SpriteBatch batch) {
        this.game =game;
        this.batch = batch;
        bird1= new YellowBird(5,106);
        bird2= new WhiteBird(32,106);
        bird3= new YellowBird(71,106);
        birds = new ArrayList<>();
        birds.add(bird1);
        birds.add(bird2);
        birds.add(bird3);
        SlingShot=new SlingShot(92,104);
        pauseScreen=new PauseScreen(game,batch,this);
        numStars=2;
        spriteMaker=new SpriteMaker();
        blocks= new ArrayList<Block>(Arrays.asList(
                spriteMaker.woodlogShort(395,151,90f),
                spriteMaker.woodlogthick(395,101,90),
                spriteMaker.stoneLogMed(375,175,0),
                spriteMaker.woodlogShort(445,151,90f),
                spriteMaker.woodlogthick(445,101,90),
                spriteMaker.woodbox(502,91,0),
                spriteMaker.woodlogShort(481,131,90f),
                spriteMaker.woodbox(502,151,0),
                spriteMaker.stoneLogMed(466,175,0),
                spriteMaker.woodbox(552,91,0),
                spriteMaker.woodlogShort(531,131,90f),
                spriteMaker.woodbox(552,151,0),
                spriteMaker.woodlogShort(396,201,90f),
                spriteMaker.woodlogthick(396,232,90),
                spriteMaker.woodlogthick(442,210,90f),
                spriteMaker.woodlogVShort(463,189,90f),
                spriteMaker.woodlogVShort(463,250,90f),
                spriteMaker.stoneLogMed(378,266,0),
                spriteMaker.woodlogShort(475,201,90f),
                spriteMaker.woodlogthick(475,232,90),
                spriteMaker.woodlogMed(488,221,90),
                spriteMaker.stoneLogMed(462,267,0)
        ));
        pigs = new ArrayList<>(Arrays.asList(
                new MedPig(444,188),
                new MedPig(523,188),
                new LargePig(484,281)
        ));

    }

    @Override
    public void show() {
        background=new Texture("playscreenbg.jpg");
        birdSheet=new Texture("birds.png");
        pause=new Sprite(new Texture("pause.png"));
        altpause=new Sprite(new Texture("altpause.png"));
        bluebutton=new Sprite(new Texture("greenbutton.png"));
        greybutton=new Sprite(new Texture("greybutton.png"));
        redbutton=new Sprite(new Texture("redbutton.png"));
        font = new BitmapFont(Gdx.files.internal("font.fnt"));

    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0,720,480);
//        handleMovement();


        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();


        handleButton(mouseX,mouseY,15,380,85,85,pause,altpause, this::pause2);
        SlingShot.draw(batch);
        for(Bird bird : birds) {
            bird.draw(batch);
        }


        for(Block block : blocks) {
            block.draw(batch);
        }

        for(Pig pig : pigs){
            pig.draw(batch);
        }

        handleButton(mouseX,mouseY,530,25,155,50,bluebutton, greybutton, this::restart);
        handleButton(mouseX,mouseY,370,25,155,50,redbutton, greybutton, this::lose);
        font.getData().setScale(0.6f);
        font.draw(batch,"Give Up",404,64);
        font.draw(batch, "Try Again", 404+149,64);
        batch.end();
//        System.out.println(img1X+" "+img1Y+" "+img1Width+" "+img1Height);
    }

    private void lose() {
        this.dispose();
        game.setScreen(new LoseScreen(game,batch,this));
    }

    private void restart() {
        try {
            // Dispose of the current screen to release resources
            this.dispose();

            // Use reflection to create a new instance of the current level's class
            Screen newLevel = this.getClass()
                    .getConstructor(Main.class, SpriteBatch.class)
                    .newInstance(game, batch);

            // Set the new instance as the active screen
            game.setScreen(newLevel);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any instantiation errors (e.g., show an error or fallback)
        }
    }

    private void pause2(){
        game.setScreen(pauseScreen);
    }

    private void win(){
        game.setScreen(new WinScreen(game,batch,numStars,this));
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
//        // Dispose of textures
//        background.dispose();
//        birdSheet.dispose();
//        pause.getTexture().dispose();
//        altpause.getTexture().dispose();
//        bluebutton.getTexture().dispose();
//        greybutton.getTexture().dispose();
//
//        // Dispose of each bird's texture and sprites
//        for (Bird bird : birds) {
//            bird.getSprite().getTexture().dispose(); // Assumes Bird class has a getSprite() method
//        }
//
//        // Dispose of each block's texture
//        for (Block block : blocks) {
//            block.getSprite().getTexture().dispose(); // Assumes Block class has a getSprite() method
//        }
//
//        // Dispose of each pig's texture
//        for (Pig pig : pigs) {
//            pig.getSprite().getTexture().dispose(); // Assumes Pig class has a getSprite() method
//        }
//
//        // Dispose of font
//        font.dispose();
    }
    private int img1X = 0, img1Y = 0, img1Width = 100, img1Height = 100;
    private void handleMovement() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            img1Y += 100 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            img1Y -= 100 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            img1X += 100 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            img1X -= 100 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            img1Height += 100 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            img1Height -= 100 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            img1Width += 100 * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            img1Width -= 100 * Gdx.graphics.getDeltaTime();
        }
    }


    private void handleButton(int mouseX, int mouseY, int x, int y, int width, int height,
                              Sprite Button, Sprite altButton,
                              Runnable onClickFunction) {

        if(hover(mouseX,mouseY,x,y,width,height,Button)){
            batch.draw(altButton,x,y,width,height);
            if(Gdx.input.justTouched()){
                onClickFunction.run();
            }
        }
        else{
            batch.draw(Button,x,y,width,height);
        }
    }

    private boolean hover(int mouseX, int mouseY, int x, int y,int width,int height, Sprite button){
        return mouseX >= x && mouseX <= x + width &&
                mouseY >= y && mouseY <= y + height;
    }
}
