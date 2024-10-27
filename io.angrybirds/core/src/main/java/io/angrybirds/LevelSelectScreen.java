package io.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class LevelSelectScreen implements Screen {
    private final SpriteBatch batch;
    private Main game;
    private Texture img;
    private Texture background;
    private Texture button_sheet;
    private Sprite selectlevel;
    private Sprite backbutton;
    private Texture button_sheet2;
    private Sprite levelbutton;
    private Sprite pressedlevelbutton;
    private BitmapFont font;

    public LevelSelectScreen(Main game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;
    }

    @Override
    public void show() {
        img = new Texture("libgdx.png");
        background=new Texture("playscreenbg.jpg");
        button_sheet = new Texture("buttons.png");
        button_sheet2 = new Texture("buttonsheet.png");
        selectlevel = new Sprite(button_sheet, 475, 333, 225, 45);
        backbutton=new Sprite(button_sheet, 254,-218,68,295);
        levelbutton =new Sprite(button_sheet2, 755,-1, 235, 60);
        pressedlevelbutton = new Sprite(button_sheet2, 522,-1, 233, 60);
        font = new BitmapFont(Gdx.files.internal("font.fnt"));

    }
//    private float img1X = 500, img1Y = 300, img1Width = 150, img1Height = 100;

    @Override
    public void render(float v) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, 720,480);
        batch.draw(selectlevel, 255, 376);
//        // Handle movement with arrow keys
//        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
//            img1Y += 100 * Gdx.graphics.getDeltaTime();
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
//            img1Y -= 100 * Gdx.graphics.getDeltaTime();
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//            img1X += 100 * Gdx.graphics.getDeltaTime();
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//            img1X -= 100 * Gdx.graphics.getDeltaTime();
//        }
//
//        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
//            img1Height += 100 * Gdx.graphics.getDeltaTime();
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
//            img1Height -= 100 * Gdx.graphics.getDeltaTime();
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
//            img1Width += 100 * Gdx.graphics.getDeltaTime();
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
//            img1Width -= 100 * Gdx.graphics.getDeltaTime();
//        }


        // Mouse position
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Flip Y because LibGDX has (0,0) at bottom-left

//        exitbutton= new Sprite(button_sheet, (int) img1X, (int) img1Y, (int) img1Width, (int) img1Height);


        backbutton(mouseX, mouseY);
        if(hover(mouseX,mouseY,240,290,levelbutton)){
            batch.draw(pressedlevelbutton,240,290);
            if(Gdx.input.isTouched()){
                this.dispose();
                game.setScreen(new Level1(game,batch));
            }
        }
        else{
            batch.draw(levelbutton,240,290);
        }

        if(hover(mouseX,mouseY,240,220,levelbutton)){
            batch.draw(pressedlevelbutton,240,220);
            if(Gdx.input.isTouched()){
                this.dispose();
                game.setScreen(new Level2(game,batch));
            }
        }
        else{
            batch.draw(levelbutton,240,220);
        }

        if(hover(mouseX,mouseY,240,150,levelbutton)){
            batch.draw(pressedlevelbutton,240,150);
            if(Gdx.input.isTouched()){
                this.dispose();
                game.setScreen(new MainMenuScreen(game,batch));
            }
        }
        else{
            batch.draw(levelbutton,240,150);
        }
        font.getData().setScale(0.8f);
        font.draw(batch, "Level 3", 304,197);
        font.draw(batch, "Level 2", 304,197+71);
        font.draw(batch, "Level 1", 304,197+71+71);


        batch.end();

//        System.out.println(img1X+" " +img1Y+" "+ img1Width);
    }

    private boolean hover(int mouseX, int mouseY,int x, int y, Sprite button){
        return mouseX >= x && mouseX <= x + button.getWidth() &&
                mouseY >= y && mouseY <= y + button.getHeight();
    }

    private void backbutton(int mouseX, int mouseY) {
        // Back button properties
        float backButtonX = 17;
        float backButtonY = 15;
        float backButtonWidth = backbutton.getWidth();
        float backButtonHeight = backbutton.getHeight();

        // Check if the mouse is over the back button
        boolean isHovering = mouseX >= backButtonX && mouseX <= backButtonX + backButtonWidth &&
                mouseY >= backButtonY && mouseY <= backButtonY + backButtonHeight;

        // If hovering, increase the size of the button
        float backButtonScale = isHovering ? 1.05f : 1.0f;
        float scaledWidth = backButtonWidth * backButtonScale;
        float scaledHeight = backButtonHeight * backButtonScale;
        float backButtonCenterX = backButtonX + backButtonWidth / 2;
        float backButtonCenterY = backButtonY + backButtonHeight / 2;

        // Handle click event
        if (isHovering && Gdx.input.justTouched()) { // Use justTouched() to avoid continuous click events
            this.dispose();
            game.setScreen(new MainMenuScreen(game, batch));
        }

        // Draw the button, scaled if hovering
        batch.draw(backbutton,
                backButtonCenterX - scaledWidth / 2,
                backButtonCenterY - scaledHeight / 2,
                scaledWidth,
                scaledHeight);
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

    }
}
