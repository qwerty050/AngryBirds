package io.angrybirds.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.angrybirds.Main;

public class GameFinishedScreen implements Screen {
    private Main game;
    private SpriteBatch batch;
    private Sprite menu;
    private Sprite cross;
    private Sprite altcross;
    private Screen previousScreen;
    private Texture black;
    private BitmapFont font;


    public GameFinishedScreen(Main game, SpriteBatch batch, Screen previousScreen) {
        this.game = game;
        this.batch = batch;
        this.previousScreen = previousScreen;
    }

    @Override
    public void show() {
        menu = new Sprite(new Texture("bluebox.png"));
        cross = new Sprite(new Texture("menucross.png"));
        altcross = new Sprite(new Texture("altmenucross.png"));
        black=new Texture("blackscreen.png");
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
    }

    @Override
    public void render(float v) {
        // Render the unpaused screen
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();


        batch.begin();
        // Draw a semi-transparent black rectangle over the entire screen to dim it
        batch.setColor(0, 0, 0, 0.5f);  // 0.5f is for 50% transparency
        batch.draw(black, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setColor(1, 1, 1, 1);  // Reset color
        batch.draw(menu, 175, 125, 370, 230);

        handleButton(mouseX,mouseY,520,324,36,38,cross,altcross,this::levelselect);

        font.draw(batch, "Game Finished", 241,255);

        batch.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

    }

    private void levelselect() {
        this.dispose();
        game.setScreen(new MainMenuScreen(game, batch));
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
        // Dispose textures and sprites to release resources
        menu.getTexture().dispose();
        cross.getTexture().dispose();
        altcross.getTexture().dispose();
        black.dispose();

        // Dispose font
        font.dispose();
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
