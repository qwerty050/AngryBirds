    package io.angrybirds;

    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.Input;
    import com.badlogic.gdx.Screen;
    import com.badlogic.gdx.graphics.GL20;
    import com.badlogic.gdx.graphics.Texture;
    import com.badlogic.gdx.graphics.g2d.BitmapFont;
    import com.badlogic.gdx.graphics.g2d.Sprite;
    import com.badlogic.gdx.graphics.g2d.SpriteBatch;
    import com.badlogic.gdx.math.Vector2;
    import com.badlogic.gdx.utils.ScreenUtils;

    import java.time.temporal.Temporal;
    import java.util.ArrayList;
    import java.util.Arrays;

    public class Level1 implements Screen {
        private final SpriteBatch batch;
        private Main game;
        private Texture background;
        private Sprite pause;
        private Sprite altpause;
        ArrayList<Bird> birds;
        Bird bird1;
        Bird bird2;
        Bird bird3;
        Texture birdSheet;
        SlingShot SlingShot;
        PauseScreen pauseScreen;
        private int numStars;
        private Sprite bluebutton;
        private Sprite greybutton;
        private ArrayList<Block> blocks;
        Block block;
        SpriteMaker spriteMaker;
        private ArrayList<Pig> pigs;
        private BitmapFont font;

        public Level1(Main game, SpriteBatch batch) {
            this.game =game;
            this.batch = batch;
            bird1= new RedBird(11,106);
            bird2= new RedBird(38,106);
            bird3= new RedBird(61,106);
            birds = new ArrayList<>();
            birds.add(bird1);
            birds.add(bird2);
            birds.add(bird3);
            SlingShot=new SlingShot(92,104);
            pauseScreen=new PauseScreen(game,batch,this);
            numStars=2;
            spriteMaker=new SpriteMaker();
            blocks= new ArrayList<Block>(Arrays.asList(
                    spriteMaker.woodlogShort(395,151,90f),
                    spriteMaker.woodlogthick(395,101,90),
                    spriteMaker.woodlogMed(375,175,0),
                    spriteMaker.woodlogShort(445,151,90f),
                    spriteMaker.woodlogthick(445,101,90),
                    spriteMaker.woodbox(502,91,0),
                    spriteMaker.woodlogShort(481,131,90f),
                    spriteMaker.woodbox(502,151,0),
                    spriteMaker.woodlogMed(466,175,0),
                    spriteMaker.woodbox(552,91,0),
                    spriteMaker.woodlogShort(531,131,90f),
                    spriteMaker.woodbox(552,151,0),
                    spriteMaker.woodlogShort(396,201,90f),
                    spriteMaker.woodlogthick(396,232,90),
                    spriteMaker.woodlogthick(442,210,90f),
                    spriteMaker.woodlogVShort(463,189,90f),
                    spriteMaker.woodlogVShort(463,250,90f),
                    spriteMaker.woodlogMed(378,266,0),
                    spriteMaker.woodlogShort(475,201,90f),
                    spriteMaker.woodlogthick(475,232,90),
                    spriteMaker.woodlogMed(488,221,90),
                    spriteMaker.woodlogMed(462,267,0)
            ));
            pigs = new ArrayList<>(Arrays.asList(
                    new MedPig(444,188),
                    new MedPig(523,188),
                    new MedPig(484,281)
            ));

        }

        @Override
        public void show() {
            background=new Texture("playscreenbg.jpg");
            birdSheet=new Texture("birds.png");
            pause=new Sprite(new Texture("pause.png"));
            altpause=new Sprite(new Texture("altpause.png"));
            bluebutton=new Sprite(new Texture("greenbutton.png"));
            greybutton=new Sprite(new Texture("greybutton.png"));
            font = new BitmapFont(Gdx.files.internal("font.fnt"));
        }

        @Override
        public void render(float v) {
            ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();
            batch.draw(background, 0, 0,720,480);
//            handleMovement();


            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();


            handleButton(mouseX,mouseY,15,380,85,85,pause,altpause, this::pause2);
            SlingShot.draw(batch);
            for(Bird bird : birds) {
                bird.draw(batch);
            }


            for(Block block : blocks) {
                block.draw(batch);
            }

            for(Pig pig : pigs){
                pig.draw(batch);
            }
            handleButton(mouseX,mouseY,530,25,155,50,bluebutton, greybutton, this::win);
            font.getData().setScale(0.6f);
            font.draw(batch,"End Level",553,64);
            batch.end();
            System.out.println(img1X+" "+img1Y+" "+img1Width+" "+img1Height);
        }

        private void pause2(){
            game.setScreen(pauseScreen);
        }

        private void win(){
            game.setScreen(new WinScreen(game,batch,numStars,this));
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


        private void handleButton(int mouseX, int mouseY, int x, int y, int width, int height,
                                  Sprite Button, Sprite altButton,
                                  Runnable onClickFunction) {

            if(hover(mouseX,mouseY,x,y,Button)){
                batch.draw(altButton,x,y,width,height);
                if(Gdx.input.isTouched()){
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

    }
