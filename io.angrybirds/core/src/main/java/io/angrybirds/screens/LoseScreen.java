package io.angrybirds.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.angrybirds.Main;

public class LoseScreen implements Screen {
    private Main game;
    private SpriteBatch batch;
    private Sprite nextLevel;
    private Sprite altNextLevel;
    private Sprite restartLevel;
    private Sprite altRestartLevel;
    private Sprite levelSelection;
    private Sprite altLevelSelection;
    private int numStars;
    private Sprite starplaceleft;
    private Sprite starplacecentre;
    private Sprite starplaceright;
    private Sprite starleft;
    private Sprite starcentre;
    private Sprite starright;
    private Sprite menu;
    private Texture background;
    private Screen levelwon;
    private Texture black;


    public LoseScreen(Main game, SpriteBatch batch, Screen screen) {
        this.game = game;
        this.batch = batch;
        levelwon = screen;
    }

    @Override
    public void show() {
        nextLevel = new Sprite(new Texture("nextlevel.png"));
        altNextLevel = new Sprite(new Texture("altnextlevel.png"));
        restartLevel = new Sprite(new Texture("restart.png"));
        altRestartLevel = new Sprite(new Texture("altrestart.png"));
        levelSelection = new Sprite(new Texture("levelselect.png"));
        altLevelSelection = new Sprite(new Texture("altlevelselect.png"));
        starplaceleft = new Sprite(new Texture("starplaceleft.png"));
        starplacecentre = new Sprite(new Texture("starplacecentre.png"));
        starplaceright = new Sprite(new Texture("starplaceright.png"));
        starleft = new Sprite(new Texture("starleft.png"));
        starcentre = new Sprite(new Texture("starcentre.png"));
        starright = new Sprite(new Texture("starright.png"));
        menu = new Sprite(new Texture("bluebox.png"));
        black=new Texture("blackscreen.png");

        background = new Texture("playscreenbg.jpg");
    }

    @Override
    public void render(float v) {

        // Render the unpaused screen
        if (levelwon != null) {
            levelwon.render(v);
        }

        // Draw a dimming overlay
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 0.1f);  // Clear with a dim background color

        batch.begin();
        // Draw a semi-transparent black rectangle over the entire screen to dim it
        batch.setColor(0, 0, 0, 0.5f);  // 0.5f is for 50% transparency
        batch.draw(black, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setColor(1, 1, 1, 1);  // Reset color



        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        batch.draw(menu, 175, 125, 370, 230);
        renderstarplaces();


        handleButton(mouseX,mouseY, 257,134,65,65,levelSelection,altLevelSelection,this::levelselect);
        handleButton(mouseX,mouseY,332,134,65,65, restartLevel,altRestartLevel,this::restart);
//        handleButton(mouseX,mouseY,407,134,65,65,nextLevel,altNextLevel,this::nextlevel);
        batch.draw(altNextLevel,407,134,65,65);




//        handleMovement();

        batch.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
//        System.out.println(img1X+" "+img1Y+" "+img1Width+" "+img1Height);
//        System.out.println(mouseX+" "+mouseY);
    }

    private void nextlevel() {
        this.dispose();
        game.setScreen(new Level2(game,batch));
    }

    private void restart() {
        try {
            // Dispose of the current screen to release resources
            this.dispose();

            // Use reflection to create a new instance of the current level's class
            Screen newLevel = levelwon.getClass()
                    .getConstructor(Main.class, SpriteBatch.class)
                    .newInstance(game, batch);

            // Set the new instance as the active screen
            game.setScreen(newLevel);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any instantiation errors (e.g., show an error or fallback)
        }
    }

    private void levelselect() {
        this.dispose();
        game.setScreen(new LevelSelectScreen(game,batch));
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
        nextLevel.getTexture().dispose();
        altNextLevel.getTexture().dispose();
        restartLevel.getTexture().dispose();
        altRestartLevel.getTexture().dispose();
        levelSelection.getTexture().dispose();
        altLevelSelection.getTexture().dispose();
        starplaceleft.getTexture().dispose();
        starplacecentre.getTexture().dispose();
        starplaceright.getTexture().dispose();
        starleft.getTexture().dispose();
        starcentre.getTexture().dispose();
        starright.getTexture().dispose();
        menu.getTexture().dispose();
        background.dispose();
        black.dispose();
    }
    private void renderstarplaces(){
        batch.draw(starplacecentre,294,225);
        batch.draw(starplaceright,431,225);
        batch.draw(starplaceleft,184,225);
    }
    private void render1star(){
        batch.draw(starleft,97,211);
    }
    private void render2star(){
        this.render1star();
        batch.draw(starcentre,262,232);
    }
    private void render3star(){
        this.render2star();
        batch.draw(starright,414,214);
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

//
//    private int img1X = 0, img1Y = 0, img1Width = 100, img1Height = 100;
//    private void handleMovement() {
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
//    }
}
