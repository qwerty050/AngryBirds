package io.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import io.angrybirds.birds.Bird;


import java.util.ArrayList;
import java.util.Stack;

import static io.angrybirds.Main.PPM;

public class SlingShot {
    private final Vector2 position;
    private final Stack<Bird> birdStack;
    private Bird currentBird;

    // Slingshot configuration
    private static final float PULL_LIMIT = 75f / PPM;  // Convert to meters
    private static final float AREA_RADIUS = 75f / PPM;
    private static final float MIN_LAUNCH_FORCE = 15f;
    private static final float MAX_LAUNCH_FORCE = 25f;
    private static final float FORCE_SCALE_FACTOR = 1f;

    // Bird positioning
    private final Vector2 restPosition;
    private Vector2 dragPosition;
    private boolean isDragging;

    // Debug drawing
    private final ShapeRenderer shapeRenderer;
    private boolean debugMode = false;

    //Sprite Drawing
    private Sprite foreground;
    private Sprite background;

    private Body body;

    public SlingShot(float x, float y) {
        position = new Vector2(x, y);
        restPosition = new Vector2(x, y); // Bird sits slightly above slingshot
        dragPosition = restPosition.cpy();

        birdStack = new Stack<>();
        shapeRenderer = new ShapeRenderer();

        initializeInputProcessor();
    }

    public SlingShot(float x, float y, ArrayList<Bird> birds) {
        position = new Vector2(x/PPM, y/PPM);
        restPosition = new Vector2(x/PPM, y/PPM); // Bird sits slightly above slingshot
        dragPosition = restPosition.cpy();

        foreground=new Sprite(new Texture("birds.png"),564,1,38,199);
        background=new Sprite(new Texture("birds.png"),733,161,43,124);

        birdStack = new Stack<>();
        shapeRenderer = new ShapeRenderer();

        birdStack.addAll(birds);
        loadNextBird();
        initializeInputProcessor();
    }


    private void loadNextBird() {
        if (!birdStack.isEmpty()) {
            currentBird = birdStack.pop();
            currentBird.setPosition(new Vector2(position.x, position.y));
            isDragging = false;
        }
    }

    private void initializeInputProcessor() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Vector2 touchPoint = convertToWorldCoordinates(screenX, screenY);
                if (currentBird != null && touchPoint.dst(restPosition) <= AREA_RADIUS) {
                    isDragging = true;
                    dragPosition.set(touchPoint);
                    return true;
                }
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (isDragging && currentBird != null) {
//                    stretch.loop();
                    Vector2 touchPoint = convertToWorldCoordinates(screenX, screenY);
                    Vector2 pullVector = new Vector2(touchPoint).sub(position);

                    // Limit pull distance to the defined pull limit
                    if (pullVector.len() > PULL_LIMIT) {
                        pullVector.setLength(PULL_LIMIT);
                    }

                    dragPosition.set(position).add(pullVector);
                    currentBird.getBody().setActive(false);
                    currentBird.setPosition(new Vector2(dragPosition.x, dragPosition.y));
                    return true;
                }
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (isDragging && currentBird != null) {
                    dragPosition.set(restPosition);
                    Vector2 touchPoint = convertToWorldCoordinates(screenX, screenY);
                    Vector2 launchVector = new Vector2(position).sub(touchPoint);

                    // Calculate force magnitude based on pull distance
                    float forceMagnitude = calculateLaunchForce(launchVector);

                    // Normalize launch vector and scale by calculated force
                    Vector2 launchForce = launchVector.nor().scl(forceMagnitude);

                    // Debug print force details
                    System.out.printf("Launch Vector: %s, Force Magnitude: %.2f\n", launchVector, forceMagnitude);

                    // Launch the bird
                    currentBird.getBody().setActive(true);
                    currentBird.launch(launchForce);

                    // Reset state and load the next bird
                    currentBird = null;
                    isDragging = false;
//                    stretch.stop();
                    loadNextBird();
                    return true;
                }
                return false;
            }
        });
    }

    private float calculateLaunchForce(Vector2 launchVector) {
        // Calculate force based on pull distance
        float pullDistance = Math.min(launchVector.len(), PULL_LIMIT);

        // Map pull distance to a proportional launch force
        float normalizedForce = pullDistance / PULL_LIMIT;
        float forceMagnitude = normalizedForce * (MAX_LAUNCH_FORCE - MIN_LAUNCH_FORCE) + MIN_LAUNCH_FORCE;

        // Optional: Apply additional scaling factor if needed
        forceMagnitude *= FORCE_SCALE_FACTOR;

        return forceMagnitude;
    }

    private Vector2 convertToWorldCoordinates(int screenX, int screenY) {
        return new Vector2(
                screenX / PPM,
                (Gdx.graphics.getHeight() - screenY) / PPM
        );
    }

    public void draw(SpriteBatch batch) {
        // Debug drawing to help visualize drag area and pull limit
        if (debugMode) {
            batch.end();
//            drawDebug();
            drawRectangleBetweenPoints(
                    new Vector2(dragPosition.x * PPM, dragPosition.y * PPM),
                    new Vector2((position.x + 18/PPM) * PPM, (position.y + 4/PPM) * PPM),
                    8f
            );;
            drawRectangleBetweenPoints(
                    new Vector2(dragPosition.x * PPM, dragPosition.y * PPM),
                    new Vector2((position.x -12/PPM) * PPM, (position.y + 20/PPM) * PPM),
                    8f
            );;
            batch.begin();
        }

        float pixelX = position.x * PPM;
        float pixelY = position.y * PPM;

        batch.draw(foreground,
                pixelX - 7,
                pixelY - 70,
                background.getWidth() / 1.5f,
                background.getHeight() / 1.5f
        );
        batch.draw(background,
                pixelX - 21,
                pixelY - 34,
                background.getWidth() / 2f,
                background.getHeight() / 2f
        );
    }

    private void drawDebug() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1);

        // Convert to pixel coordinates for drawing
        float pixelX = position.x * PPM;
        float pixelY = position.y * PPM;

        shapeRenderer.circle(pixelX, pixelY, 3);
        shapeRenderer.circle(pixelX, pixelY, AREA_RADIUS * PPM);
        shapeRenderer.circle(pixelX, pixelY, PULL_LIMIT * PPM);

        // ... rest of debug drawing
        shapeRenderer.end();
    }

    public void drawRectangleBetweenPoints(Vector2 startPoint, Vector2 endPoint, float rectangleWidth) {
        // Calculate the direction vector
        Vector2 direction = endPoint.cpy().sub(startPoint).nor();

        // Calculate perpendicular vector for rectangle width
        Vector2 perpendicular = new Vector2(-direction.y, direction.x).scl(rectangleWidth / 2);

        // Calculate rectangle vertices
        Vector2 topLeft = startPoint.cpy().add(perpendicular);
        Vector2 topRight = startPoint.cpy().sub(perpendicular);
        Vector2 bottomRight = endPoint.cpy().sub(perpendicular);
        Vector2 bottomLeft = endPoint.cpy().add(perpendicular);

        // Draw the rectangle
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(47f/255f, 22f/255f, 8f/255f, 1f);

        shapeRenderer.triangle(
                topLeft.x, topLeft.y,
                topRight.x, topRight.y,
                bottomRight.x, bottomRight.y
        );

        shapeRenderer.triangle(
                topLeft.x, topLeft.y,
                bottomRight.x, bottomRight.y,
                bottomLeft.x, bottomLeft.y
        );

        shapeRenderer.end();
    }
    public void dispose() {
        shapeRenderer.dispose();
    }

    public void setDebugMode(boolean enabled) {
        this.debugMode = enabled;
    }
}

