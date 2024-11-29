package io.angrybirds.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.angrybirds.Main;
import io.angrybirds.serial.LoadGame;
import io.angrybirds.utils.BasicButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class SaveGameScreen implements Screen {
    private Main game;
    private SpriteBatch batch;
    private ArrayList<String> fileNames;
    private int numFiles;
    private ArrayList<BasicButton> buttons;
    private BitmapFont font;
    private Texture background;
    private Texture background2;
    private BasicButton backButton;

    // Camera and viewport for scrolling
    private OrthographicCamera camera;
    private Viewport viewport;
    private float cameraOffsetY = 0;
    private float scrollSpeed = 100f;

    public SaveGameScreen(Main game, SpriteBatch batch) {
        this.game = game;
        this.batch = batch;

        // Initialize fileNames before numFiles() call
        fileNames = new ArrayList<>();

        background = new Texture("playscreenbg.jpg");
        background2 = new Texture("playscreenbg2.png");
        font = new BitmapFont(Gdx.files.internal("font.fnt"));
        font.getData().setScale(0.8f);

        numFiles = numFiles();

        // Setup camera for scrolling
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 720, 480);
        viewport = new FitViewport(720, 480, camera);

        buttons = new ArrayList<>();
        backButton= new BasicButton(new Sprite(new Texture("buttons.png"), 254, -218, 68, 295), 17, 15, this::back);

        buttons.addAll(makeButtons());
    }

    private int numFiles() {
        String folderPath = "saves";  // Relative path
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> !name.startsWith("."));

            if (files != null) {
                fileNames.clear();  // Clear previous files
                for (int i = files.length - 1; i >= 0; i--) {
                    if (files[i].isFile()) {
                        fileNames.add(files[i].getName());
                    }
                }
                return fileNames.size();
            }
        }
        return 0;
    }

    private ArrayList<BasicButton> makeButtons() {
        ArrayList<BasicButton> buttons = new ArrayList<>();
        int y = 290;

        for (int i = 0; i < numFiles; i++) {
            int finalI = i;
            String name = fileNames.get(finalI);
            Runnable task = () -> loadGame(name);
            y = 290 - (i * 70);  // Adjust vertical positioning

            BasicButton button = new BasicButton(
                    new Sprite(new Texture("buttonsheet.png"), 755, -1, 235, 60),
                    new Sprite(new Texture("buttonsheet.png"), 522, -1, 233, 60),
                    240, y, task
            );
            buttons.add(button);
        }

        return buttons;
    }

    public void update() {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        // Scroll handling with up and down keys
//        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
//            cameraOffsetY -= scrollSpeed;
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
//            cameraOffsetY += scrollSpeed;
//        }
//
//        // Limit scroll bounds
//        float maxScroll = Math.max(0, numFiles * 70 - 480);
//        cameraOffsetY = Math.max(0, Math.min(cameraOffsetY, maxScroll));

//        camera.position.y = cameraOffsetY;
//        camera.update();
//
//        batch.setProjectionMatrix(camera.combined);

        // Update buttons considering camera offset
        for (BasicButton b : buttons) {
            b.update(mouseX, mouseY + (int)cameraOffsetY);
        }
        backButton.update(mouseX, mouseY + (int)cameraOffsetY);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();

        batch.begin();
        batch.draw(background, 0, 0, 720, 480);

        // Draw buttons with scroll offset
        for (BasicButton b : buttons) {
            b.drawZoomChange(batch);
        }

        // Draw file names
        for (int i = 0; i < numFiles; i++) {
            font.getData().setScale(0.4f);
            font.draw(batch, fileNames.get(i), 290, 330 - (i * 70) - cameraOffsetY);

        }

        batch.draw(background2, 0, 0, 720, 480);
        backButton.drawZoomChange(batch);
        batch.end();
    }

    private void loadGame(String fileName) {
        World world = new World(new Vector2(0, -9.8f), true);
        LoadGame ss = new LoadGame(world, "saves/" + fileName);
        ss.loadGame();
        game.setScreen(new LevelScreen(world, game, batch, ss.getLoadedBlocks(), ss.getLoadedPigs(), ss.getLoadedBirds(), ss.getSlingShot(), ss.getLevel()));
    }

    private void back() {
        this.dispose();
        game.setScreen(new LevelSelectScreen(game, batch));
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void dispose() {
        background.dispose();
        background2.dispose();
        font.dispose();
//        for (BasicButton button : buttons) {
//            button.dispose();
//        }
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}