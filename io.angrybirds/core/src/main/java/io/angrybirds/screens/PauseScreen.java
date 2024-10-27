package io.angrybirds.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    public PauseScreen(Main game, SpriteBatch batch, Screen screen) {
        this.game = game;
        this.batch = batch;
        this.unpausedScreen = screen;
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

    @Override
    public void render(float delta) {
        // Render the unpaused screen
        if (unpausedScreen != null) {
            unpausedScreen.render(delta);
        }
        // Draw a dimming overlay
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 0.1f);  // Clear with a dim background color

        batch.begin();
        // Draw a semi-transparent black rectangle over the entire screen to dim it
        batch.setColor(0, 0, 0, 0.5f);  // 0.5f is for 50% transparency
        batch.draw(new Texture("blackscreen.png"), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setColor(1, 1, 1, 1);  // Reset color

        // Render the pause menu elements
        handleMovement();
        batch.draw(menu, -370, -30, 580, 530);

        // Mouse position
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        handleButton(mouseX, mouseY, 15, 380, 85, 85, pause, altpause, this::unpause);
        handleButton(mouseX, mouseY, 77, 282, 75, 75, restart, altrestart, this::restart);
        handleButton(mouseX, mouseY, 77, 188, 75, 75, levelselect, altlevelselect, this::levelselect);
        if(game.getisMuted()) handleButton(mouseX, mouseY, 77, 94, 75, 75, altaudio, audio, this::audio);
        else handleButton(mouseX, mouseY, 77, 94, 75, 75, audio, altaudio, this::audio);

        batch.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void audio() {
        game.muteunmute();
    }

    private void levelselect() {
        this.dispose();
        game.setScreen(new LevelSelectScreen(game,batch));
    }

    private void restart() {
        try {
            // Dispose of the current screen to release resources
            this.dispose();

            // Use reflection to create a new instance of the current level's class
            Screen newLevel = unpausedScreen.getClass()
                    .getConstructor(Main.class, SpriteBatch.class)
                    .newInstance(game, batch);

            // Set the new instance as the active screen
            game.setScreen(newLevel);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any instantiation errors (e.g., show an error or fallback)
        }
    }

    private void unpause(){
        this.dispose();
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
        // Dispose of textures to free up memory
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
}
