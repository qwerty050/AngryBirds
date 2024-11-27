package io.angrybirds.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import io.angrybirds.Main;

public class MainMenuScreen implements Screen {
    private Main game;
    private SpriteBatch batch;
    private Texture background;
    private Texture logo;
    private Texture button_sheet;
    private Sprite playbutton;
    private Sprite exitbutton;
    private Sprite audiobutton;
    private Sprite crossbutton;
    private boolean mute;

    public MainMenuScreen(Main game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;
    }

    @Override
    public void show() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        background = new Texture("playscreenbg.jpg");
        logo = new Texture("logo.png");
        button_sheet = new Texture("buttons.png");
        playbutton = new Sprite(button_sheet,0,0,170,100);
        exitbutton= new Sprite(button_sheet, 118,285,55,66);
        audiobutton=new Sprite(button_sheet,62,594,55,60);
        crossbutton=new Sprite(button_sheet, 218,518,44,41);
        mute=false;
    }
    @Override
    public void render(float v) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Flip Y because LibGDX has (0,0) at bottom-left

        batch.begin();


        // Draw background and logo
        batch.draw(background, 0, 0, 720f, 480f);
        batch.draw(logo, 130F, 310F, (float) (logo.getWidth() / 1.5), (float) (logo.getHeight() / 1.5));

        // Handle buttons
        handlePlayButton(mouseX, mouseY);
        handleExitButton(mouseX, mouseY);
        handleAudioButton(mouseX, mouseY);

        batch.end();
    }

    private void handlePlayButton(int mouseX, int mouseY) {
        // Play button properties
        float playButtonX = 285;
        float playButtonY = 125;
        float playButtonWidth = playbutton.getWidth();
        float playButtonHeight = playbutton.getHeight();

        // Check if hovering
        boolean isHovering = mouseX >= playButtonX && mouseX <= playButtonX + playButtonWidth &&
                mouseY >= playButtonY && mouseY <= playButtonY + playButtonHeight;

        // Adjust size based on hover
        float playButtonScale = isHovering ? 1.1f : 1.0f;
        float scaledWidth = playButtonWidth * playButtonScale;
        float scaledHeight = playButtonHeight * playButtonScale;
        float playButtonCenterX = playButtonX + playButtonWidth / 2;
        float playButtonCenterY = playButtonY + playButtonHeight / 2;

        // Draw the play button
        batch.draw(playbutton,
                playButtonCenterX - scaledWidth / 2,
                playButtonCenterY - scaledHeight / 2,
                scaledWidth,
                scaledHeight);

        // Check if clicked
        if (isHovering && Gdx.input.isTouched()) {
            this.dispose();
            game.setScreen(new LevelSelectScreen(game, batch));
        }
    }

    private void handleExitButton(int mouseX, int mouseY) {
        // Exit button properties
        float exitButtonX = 639;
        float exitButtonY = 18;
        float exitButtonWidth = exitbutton.getWidth();
        float exitButtonHeight = exitbutton.getHeight();

        // Check if hovering
        boolean isHovering = hover(mouseX, mouseY, (int) exitButtonX, (int) exitButtonY, exitbutton);

        // Adjust size based on hover
        float exitButtonScale = isHovering ? 1.1f : 1.0f;
        float scaledWidth = exitButtonWidth * exitButtonScale;
        float scaledHeight = exitButtonHeight * exitButtonScale;
        float exitButtonCenterX = exitButtonX + exitButtonWidth / 2;
        float exitButtonCenterY = exitButtonY + exitButtonHeight / 2;

        // Draw the exit button
        batch.draw(exitbutton,
                exitButtonCenterX - scaledWidth / 2,
                exitButtonCenterY - scaledHeight / 2,
                scaledWidth,
                scaledHeight);

        // Check if clicked
        if (isHovering && Gdx.input.isTouched()) {
            Gdx.app.exit();
        }
    }

    private void handleAudioButton(int mouseX, int mouseY) {
        // Audio button properties
        int audioButtonX = 21;
        int audioButtonY = 20;

        // Check if hovering
        boolean isHovering = hover(mouseX, mouseY, audioButtonX, audioButtonY, audiobutton);

        // Draw the audio button and cross if muted
        if (isHovering) {
            if (!game.getisMuted()) {
                batch.draw(audiobutton, audioButtonX, audioButtonY);
                batch.draw(crossbutton, 29, 32);
            } else {
                batch.draw(audiobutton, audioButtonX, audioButtonY);
            }
            if (Gdx.input.justTouched()) {
                game.muteunmute();
            }
        } else {
            if (game.getisMuted()) {
                batch.draw(audiobutton, audioButtonX, audioButtonY);
                batch.draw(crossbutton, 29, 32);
            } else {
                batch.draw(audiobutton, audioButtonX, audioButtonY);
            }
        }
    }

    private boolean hover(int mouseX, int mouseY, int x, int y, Sprite button) {
        return mouseX >= x && mouseX <= x + button.getWidth() &&
                mouseY >= y && mouseY <= y + button.getHeight();
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
        playbutton.getTexture().dispose();
        exitbutton.getTexture().dispose();
        audiobutton.getTexture().dispose();
        crossbutton.getTexture().dispose();
    }
}
