package io.angrybirds.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import io.angrybirds.utils.BasicButton;
import io.angrybirds.Main;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;


public class LevelSelectScreen implements Screen {
    private final SpriteBatch batch;
    private Main game;

    private Texture background;
    private Texture button_sheet;
    private Sprite selectlevel;

    private Texture button_sheet2;

    private BitmapFont font;

    private List<BasicButton> buttons;



    public LevelSelectScreen(Main game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;


    }

    @Override
    public void show() {
        background=new Texture("playscreenbg.jpg");
        button_sheet = new Texture("buttons.png");
        button_sheet2 = new Texture("buttonsheet.png");

        selectlevel = new Sprite(button_sheet, 475, 330, 225, 45);


        buttons = new ArrayList<BasicButton>(asList(
                new BasicButton(new Sprite(button_sheet2, 755,-1, 235, 60), new Sprite(button_sheet2, 522,-1, 233, 60),240,290,this::gotoLevel1),
                new BasicButton(new Sprite(button_sheet2, 755,-1, 235, 60), new Sprite(button_sheet2, 522,-1, 233, 60),240,220,this::gotoLevel2),
                new BasicButton(new Sprite(button_sheet2, 755,-1, 235, 60), new Sprite(button_sheet2, 522,-1, 233, 60),240,150,this::gotoLevel3),
                new BasicButton(new Sprite(button_sheet2, 755,-1, 235, 60), new Sprite(button_sheet2, 522,-1, 233, 60),240,20,this::savedGames),
                new BasicButton(new Sprite(button_sheet, 254,-218,68,295),17,15,this::back)

                ));
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        font.getData().setScale(0.8f);

    }

    private void update(float v){
        // Mouse position
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Flip Y because LibGDX has (0,0) at bottom-left

        for(BasicButton b : buttons) b.update(mouseX,mouseY);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(v);

        batch.begin();
        batch.draw(background, 0, 0, 720,480);

        batch.draw(selectlevel, 255, 376);

        for(BasicButton b : buttons) b.drawZoomChange(batch);

        font.draw(batch, "Level 3", 304,197);
        font.draw(batch, "Level 2", 304,197+71);
        font.draw(batch, "Level 1", 304,197+71+71);
        font.getData().setScale(0.7f);
        font.draw(batch, "Saved Games",272,68);
        font.getData().setScale(0.8f);


        batch.end();
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
        background.dispose();
        button_sheet.dispose();
        button_sheet2.dispose();

        // Dispose of font to avoid memory leaks
        font.dispose();
    }

    private void gotoLevel1(){
        this.dispose();
        game.setScreen(new LevelScreen(game, batch,1));
    }
    private void gotoLevel2(){
        this.dispose();
        game.setScreen(new LevelScreen(game, batch,2));
    }
    private void gotoLevel3(){
        game.setScreen(new LevelScreen(game, batch,3));
    }
    private void back(){
        this.dispose();
        game.setScreen(new MainMenuScreen(game, batch));
    }
    private void savedGames() {
        this.dispose();
        game.setScreen(new SaveGameScreen(game, batch));
    }
}
