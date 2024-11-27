package io.angrybirds.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import io.angrybirds.LevelMaker;
import io.angrybirds.Main;

public class PauseScreen implements Screen {
    private Main game;
    private SpriteBatch batch;
    private Screen unpausedScreen;
    private Sprite pause;
    private Sprite altpause;
    private Sprite restart;
    private Sprite altrestart;
    private Sprite audio;
    private Sprite altaudio;
    private Sprite levelselect;
    private Sprite altlevelselect;
    private Sprite menu;
    private boolean ispaused;
    private boolean isrestart;
    private boolean islevelselect;
    private int level;

    public PauseScreen(Main game, SpriteBatch batch, Screen screen) {
        this.game = game;
        this.batch = batch;
        this.unpausedScreen = screen;
        ispaused = false;
        isrestart = false;
        islevelselect = false;
    }

    public PauseScreen(Main game, SpriteBatch batch, LevelScreen screen, int level) {
        this.game = game;
        this.batch = batch;
        this.unpausedScreen = screen;
        this.level = level;
        ispaused = false;
        isrestart = false;
        islevelselect = false;
    }

    @Override
    public void show() {
        pause= new Sprite(new Texture(Gdx.files.internal("resume.png")));
        altpause= new Sprite(new Texture(Gdx.files.internal("altresume.png")));
        restart= new Sprite(new Texture(Gdx.files.internal("restart.png")));
        altrestart= new Sprite(new Texture(Gdx.files.internal("altrestart.png")));
        audio= new Sprite(new Texture(Gdx.files.internal("audio.png")));
        altaudio= new Sprite(new Texture(Gdx.files.internal("altaudio.png")));
        levelselect= new Sprite(new Texture(Gdx.files.internal("levelselect.png")));
        altlevelselect= new Sprite(new Texture(Gdx.files.internal("altlevelselect.png")));
        menu= new Sprite(new Texture(Gdx.files.internal("bluebox.png")));
    }

    public void update(float v){
        // Mouse position
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        if(Gdx.input.isButtonJustPressed(Input.Keys.S)){
            unpause();
        }

        ispaused=updateButton(mouseX, mouseY, 15, 380, 85, 85, pause, altpause, this::unpause);
        isrestart=updateButton(mouseX, mouseY, 77, 282, 75, 75, restart, altrestart, this::restart);
        islevelselect=updateButton(mouseX, mouseY, 77, 188, 75, 75, levelselect, altlevelselect, this::levelselect);
        updateButton(mouseX, mouseY, 77, 94, 75, 75,altaudio, audio,this::audio);
    }

    @Override
    public void render(float delta) {
        update(delta);


        batch.begin();

        // Render the pause menu elements
        batch.draw(menu, -370, -30, 580, 530);

        renderButton(ispaused,15, 380, 85, 85, pause, altpause);
        renderButton(isrestart,77, 282, 75, 75, restart, altrestart);
        renderButton(islevelselect,77, 188, 75, 75, levelselect, altlevelselect);
        renderButton(!game.getisMuted(),77, 94, 75, 75,altaudio, audio);

        batch.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void audio() {
        game.muteunmute();
    }

    private void levelselect() {

        game.setScreen(new LevelSelectScreen(game,batch));
//        unpausedScreen.dispose();
        this.dispose();
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
        pause.getTexture().dispose();
        altpause.getTexture().dispose();
        restart.getTexture().dispose();
        altrestart.getTexture().dispose();
        audio.getTexture().dispose();
        altaudio.getTexture().dispose();
        levelselect.getTexture().dispose();
        altlevelselect.getTexture().dispose();
        menu.getTexture().dispose();
    }

    private boolean updateButton(int mouseX, int mouseY, int x, int y, int width, int height,
                              Sprite Button, Sprite altButton,
                              Runnable onClickFunction) {

        if(hover(mouseX,mouseY,x,y,Button)){
            if(Gdx.input.justTouched()){
                onClickFunction.run();
            }
            return true;
        }
        else{
            return false;
        }
    }

    private void renderButton(Boolean bool,  int x, int y, int width, int height, Sprite Button, Sprite altButton) {
        if (bool) batch.draw(altButton,x,y,width,height);
        else batch.draw(Button,x,y,width,height);
    }

    private boolean hover(int mouseX, int mouseY, int x, int y, Sprite button){
        return mouseX >= x && mouseX <= x + button.getWidth() &&
                mouseY >= y && mouseY <= y + button.getHeight();
    }
}
