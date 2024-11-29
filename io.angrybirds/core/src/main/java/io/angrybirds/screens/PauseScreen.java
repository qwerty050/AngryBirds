package io.angrybirds.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.angrybirds.utils.BasicButton;
import io.angrybirds.Main;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class PauseScreen implements Screen {
    private Main game;
    private SpriteBatch batch;
    private Screen unpausedScreen;

    private Sprite audio;
    private Sprite altaudio;

    private Sprite menu;

    private int level;

    private List<BasicButton> buttons;

    public PauseScreen(Main game, SpriteBatch batch, Screen screen) {
        this.game = game;
        this.batch = batch;
        this.unpausedScreen = screen;

    }

    public PauseScreen(Main game, SpriteBatch batch, LevelScreen screen, int level) {
        this.game = game;
        this.batch = batch;
        this.unpausedScreen = screen;
        this.level = level;
    }

    @Override
    public void show() {
        altaudio= new Sprite(new Texture("buttons.png"), 218,518,44,41);

        menu= new Sprite(new Texture(Gdx.files.internal("bluebox.png")));

        buttons = new ArrayList<BasicButton>(asList(
                new BasicButton( new Sprite(new Texture(Gdx.files.internal("resume.png"))),new Sprite(new Texture(Gdx.files.internal("altresume.png"))),15, 380, 85, 85,this::unpause),
                new BasicButton(new Sprite(new Texture(Gdx.files.internal("restart.png"))), new Sprite(new Texture(Gdx.files.internal("altrestart.png"))),77, 312, 75, 75,this::restart),
                new BasicButton(new Sprite(new Texture(Gdx.files.internal("levelselect.png"))),new Sprite(new Texture(Gdx.files.internal("altlevelselect.png"))),77, 188+32, 75, 75,this::levelselect),
                new BasicButton(new Sprite(new Texture(Gdx.files.internal("audio.png"))), new Sprite(new Texture(Gdx.files.internal("altaudio.png"))),77, 94+30, 75, 75,this::audio),
                new BasicButton(new Sprite(new Texture("buttonsheet.png"), 755,-1, 235, 60), new Sprite(new Texture("buttonsheet.png"), 522,-1, 233, 60),50,50,150-20,60,this::saveGame)

                ));
//        buttons.get(4).setHandle(true);
    }

    private void saveGame() {
        unpausedScreen.resume();
    }

    public void update(float v){
        // Mouse position
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        for(BasicButton b : buttons) b.update(mouseX, mouseY);
    }

    @Override
    public void render(float delta) {
        update(delta);
        batch.begin();

        // Render the pause menu elements
        batch.draw(menu, -370, -30, 580, 530);

        for(BasicButton b : buttons) b.drawZoomChange(batch);
//
        if(game.getisMuted()) batch.draw(altaudio,84, 136, 65,65);

        batch.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void audio() {
        game.muteunmute();
    }

    private void levelselect() {
        this.dispose();
        unpausedScreen.dispose();
        game.setScreen(new LevelSelectScreen(game,batch));
    }

    private void restart() {
        this.dispose();
        unpausedScreen.dispose();
        game.setScreen(new LevelScreen(game,batch, level));
    }

    private void unpause(){
        game.setScreen(unpausedScreen);
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
        altaudio.getTexture().dispose();
        menu.getTexture().dispose();
    }

}
