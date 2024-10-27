package io.angrybirds;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    private SpriteBatch batch;
    Texture img;

    private static final int WIDTH = 720;
    private static final int HEIGHT = 480;
    private static final String TITLE = "AngryBirds";



    public static int getWidth(){ return WIDTH;}
    public static int getHeight(){ return HEIGHT;}
    public static String getTitle(){ return TITLE; }


    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("libgdx.png");
        setScreen(new MainMenuScreen(this, batch));

    }

    @Override
    public void render() {
//        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
