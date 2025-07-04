package io.angrybirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.angrybirds.screens.MainMenuScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
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
        setScreen(new MainMenuScreen(this, batch));

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
//        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        playBgMusic();
        super.render();

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
