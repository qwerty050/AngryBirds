package io.angrybirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import io.angrybirds.pigs.MedPig;
import io.angrybirds.pigs.Pig;
import io.angrybirds.pigs.SmallPig;
import io.angrybirds.screens.LevelScreen;
import io.angrybirds.screens.MainMenuScreen;
import io.angrybirds.serial.LoadGame;

import java.util.ArrayList;

import static java.util.Arrays.asList;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    public static final float PPM = 11f;

    private SpriteBatch batch;
    Texture img;
    private Music bgmusic;
    private static final int WIDTH = 720;
    private static final int HEIGHT = 480;
    private static final String TITLE = "AngryBirds";
    private boolean isMuted;


    public static int getWidth(){ return WIDTH;}
    public static int getHeight(){ return HEIGHT;}
    public static String getTitle(){ return TITLE; }


    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("libgdx.png");
        bgmusic= Gdx.audio.newMusic(Gdx.files.internal("ThemeSong.mp3"));
        isMuted = false;

//        World world= new World(new Vector2(0,-9.8f), true);
//
//        LoadGame ss=new LoadGame(world,"saves/a.csv");
//        ss.loadGame();
//        setScreen(new LevelScreen(world,this,batch, ss.getLoadedBlocks(), ss.getLoadedPigs(),ss.getLoadedBirds(), ss.getSlingShot()));
        setScreen(new MainMenuScreen(this,batch));
    }

    public void playBgMusic(){
        if(!isMuted) {
            bgmusic.play();
        }
        else bgmusic.pause();
    }

    public void mute(){
        isMuted = true;
    }

    public void unmute(){
        isMuted = false;
    }

    public void muteunmute(){
        isMuted = !isMuted;
    }

    public boolean getisMuted(){
        return isMuted;
    }
    @Override
    public void render() {
        playBgMusic();
        super.render();

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
