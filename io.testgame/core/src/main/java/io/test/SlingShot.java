package io.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Stack;

public class SlingShot {
    private final Vector2 position;
    private final Stack<Bird> birdStack;
    private Bird currentBird;

    // Slingshot configuration
    private static final float PULL_LIMIT = 75f;
    private static final float AREA_RADIUS = 75;
    private static final float MIN_LAUNCH_FORCE = 200f;
    private static final float MAX_LAUNCH_FORCE = 1000f;
    private static final float FORCE_SCALE_FACTOR = 13f;

    // Bird positioning
    private final Vector2 restPosition;
    private Vector2 dragPosition;
    private boolean isDragging;

    // Debug drawing
    private final ShapeRenderer shapeRenderer;
    private boolean debugMode = false;

    public SlingShot(float x, float y) {
        position = new Vector2(x, y);
        restPosition = new Vector2(x, y); // Bird sits slightly above slingshot
        dragPosition = restPosition.cpy();

        birdStack = new Stack<>();
        shapeRenderer = new ShapeRenderer();

        initializeInputProcessor();
    }

    public SlingShot(float x, float y, ArrayList<Bird> birds) {
        position = new Vector2(x, y);
        restPosition = new Vector2(x, y); // Bird sits slightly above slingshot
        dragPosition = restPosition.cpy();

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
        return new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
    }

    public void draw(SpriteBatch batch) {
        // Debug drawing to help visualize drag area and pull limit
        if (debugMode) {
            batch.end();
            drawDebug();
            batch.begin();
        }
    }

    private void drawDebug() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 0, 0, 1);

        // Draw slingshot as a circle at the launch position
        shapeRenderer.circle(position.x, position.y, 3); // Small circle for slingshot position

        // Draw pull limit and drag area as circles
        shapeRenderer.circle(position.x, position.y, AREA_RADIUS);
        shapeRenderer.circle(position.x, position.y, PULL_LIMIT);

        // Draw line to current bird position if dragging
        if (isDragging && currentBird != null) {
            shapeRenderer.line(position, dragPosition);
        }

        shapeRenderer.end();
    }

    public void dispose() {
        shapeRenderer.dispose();
    }

    public void setDebugMode(boolean enabled) {
        this.debugMode = enabled;
    }
}

