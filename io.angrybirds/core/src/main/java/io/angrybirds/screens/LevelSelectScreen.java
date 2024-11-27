package io.angrybirds.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import io.angrybirds.LevelMaker;
import io.angrybirds.Main;


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
    private Boolean level1;
    private Boolean level2;
    private Boolean level3;


    private float scaledWidth, scaledHeight;
    private final float backButtonX = 17;
    private final float backButtonY = 15;
    private float backButtonWidth;
    private float backButtonHeight;
    private float backButtonCenterX;
    private float backButtonCenterY;

    public LevelSelectScreen(Main game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;

        this.level1 = false;
        this.level2 = false;
        this.level3 = false;
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
        font.getData().setScale(0.8f);

        backButtonWidth = backbutton.getWidth();
        backButtonHeight = backbutton.getHeight();
        backButtonCenterX = backButtonX + backButtonWidth / 2;
        backButtonCenterY = backButtonY + backButtonHeight / 2;
    }

    private void update(float v){
        // Mouse position
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Flip Y because LibGDX has (0,0) at bottom-left

        level1=handleButton(mouseX,mouseY,240,290,levelbutton, this::gotoLevel1);
        level2=handleButton(mouseX,mouseY,240,220,levelbutton, this::gotoLevel2);
        level3=handleButton(mouseX,mouseY,240,150,levelbutton, this::gotoLevel3);
        updateBackButton(mouseX,mouseY);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(v);

        batch.begin();
        batch.draw(background, 0, 0, 720,480);

        batch.draw(selectlevel, 255, 376);

        if(level1) batch.draw(pressedlevelbutton,240,290);
        else batch.draw(levelbutton,240,290);

        if(level2) batch.draw(pressedlevelbutton,240,220);
        else batch.draw(levelbutton,240,220);

        if(level3) batch.draw(pressedlevelbutton,240,150);
        else batch.draw(levelbutton,240,150);

        batch.draw(backbutton, backButtonCenterX - scaledWidth / 2, backButtonCenterY - scaledHeight / 2, scaledWidth, scaledHeight);

        font.draw(batch, "Level 3", 304,197);
        font.draw(batch, "Level 2", 304,197+71);
        font.draw(batch, "Level 1", 304,197+71+71);


        batch.end();

    }

    private boolean hover(int mouseX, int mouseY,int x, int y, Sprite button){
        return mouseX >= x && mouseX <= x + button.getWidth() &&
                mouseY >= y && mouseY <= y + button.getHeight();
    }

    public void updateBackButton(int mouseX, int mouseY) {
        boolean isHovering = mouseX >= backButtonX && mouseX <= backButtonX + backButtonWidth &&
                mouseY >= backButtonY && mouseY <= backButtonY + backButtonHeight;

        // If hovering, increase the size of the button
        float backButtonScale = isHovering ? 1.05f : 1.0f;
        scaledWidth = backButtonWidth * backButtonScale;
        scaledHeight = backButtonHeight * backButtonScale;

        // Handle click event
        if (isHovering && Gdx.input.justTouched()) {
            this.dispose();
            game.setScreen(new MainMenuScreen(game, batch));
        }
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
        img.dispose();
        background.dispose();
        button_sheet.dispose();
        button_sheet2.dispose();

        // Dispose of font to avoid memory leaks
        font.dispose();
    }

    private boolean handleButton(int mouseX, int mouseY, int x, int y,Sprite sprite, Runnable func){
        if(hover(mouseX,mouseY,x,y,sprite)){
            if(Gdx.input.isTouched()){
                func.run();
            }
            return true;
        }
        else{
            return false;
        }
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

}
