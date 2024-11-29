package io.angrybirds.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import io.angrybirds.utils.BasicButton;
import io.angrybirds.Main;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class MainMenuScreen implements Screen {
    private Main game;
    private SpriteBatch batch;
    private Texture background;
    private Texture logo;
    private Texture button_sheet;
    private Sprite crossbutton;
    private boolean mute;

    private List<BasicButton> buttons;

    public MainMenuScreen(Main game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;

        background = new Texture("playscreenbg.jpg");
        logo = new Texture("logo.png");
        button_sheet = new Texture("buttons.png");

        mute=false;

        initButtons();
    }

    @Override
    public void show() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
    }

    private void update(){
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Flip Y because LibGDX has (0,0) at bottom-left

        for(BasicButton b : buttons) b.update(mouseX,mouseY);
    }

    @Override
    public void render(float v) {
        update();

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        batch.begin();

        batch.draw(background, 0, 0, 720f, 480f);
        batch.draw(logo, 130F, 310F, logo.getWidth() / 1.5f, logo.getHeight() / 1.5f);

        for(BasicButton b : buttons) b.drawZoom(batch);

        if(mute) batch.draw(crossbutton, 29, 32);

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
        background.dispose();
        logo.dispose();
        button_sheet.dispose();
        crossbutton.getTexture().dispose();
    }

    private void audio() {
        mute=!mute;
        game.muteunmute();
    }

    private void exit() {
        Gdx.app.exit();
    }

    private void play() {
        this.dispose();
        game.setScreen(new LevelSelectScreen(game, batch));
    }

    private void initButtons(){
        crossbutton=new Sprite(button_sheet, 218,518,44,41);

        buttons = new ArrayList<BasicButton>(asList(
                new BasicButton(new Sprite(button_sheet,0,0,170,100),285,110, this::play),
                new BasicButton(new Sprite(button_sheet, 118,285,55,66),639,18, this::exit),
                new BasicButton(new Sprite(button_sheet,62,594,55,60),21,20,this::audio)
        ));
    }
}
