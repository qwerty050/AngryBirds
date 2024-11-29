package io.angrybirds.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BasicButton {
    private Sprite button;
    private Sprite pressedButton;
    private int x;
    private int y;
    private float width;
    private float height;
    private Runnable onClick;
    private Boolean isHovering;
    private Boolean isActive;
    private Boolean handle;

    public BasicButton(Sprite button, Sprite pressedButton, int x, int y, Runnable onClick) {
        this.button = button;
        this.pressedButton = pressedButton;

        this.x = x;
        this.y = y;
        this.width = button.getWidth();
        this.height = button.getHeight();

        this.onClick = onClick;

        this.isHovering = false;
        this.isActive = true;
        this.handle = false;
    }

    public BasicButton(Sprite button, Sprite pressedButton, int x, int y,int width, int height, Runnable onClick) {
        this.button = button;
        this.pressedButton = pressedButton;

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.onClick = onClick;

        this.isHovering = false;
        this.isActive = true;
        this.handle = false;
    }

    public BasicButton(Sprite button, int x, int y, Runnable onClick) {
        this.button = button;
        this.pressedButton = button;

        this.x = x;
        this.y = y;
        this.width = button.getWidth();
        this.height = button.getHeight();

        this.onClick = onClick;

        this.isHovering = false;
        this.isActive = true;
        this.handle = false;
    }

    public void update(int mouseX, int mouseY) {
        if(isActive)
        {
            isHovering = checkHover(mouseX, mouseY);
            if (isHovering && Gdx.input.justTouched()) {
                onClick.run();
            }

            if(handle){
                buttonPlacement();
                System.out.println(x+" "+y);
            }

        }
    }

    private boolean checkHover(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width &&
                mouseY >= y && mouseY <= y + height;
    }

    public void draw(SpriteBatch batch) {
        if(isActive) batch.draw(isHovering? pressedButton:button, x, y, width, height);
    }

    public void drawZoom(SpriteBatch batch) {
        if(isActive)
        { // Adjust size based on hover
            float playButtonScale = isHovering ? 1.1f : 1.0f;
            float scaledWidth = width * playButtonScale;
            float scaledHeight = height * playButtonScale;
            float playButtonCenterX = x + width / 2;
            float playButtonCenterY = y + height / 2;

            batch.draw(button,
                    playButtonCenterX - scaledWidth / 2,
                    playButtonCenterY - scaledHeight / 2,
                    scaledWidth,
                    scaledHeight);
        }
    }

    public void drawZoomChange(SpriteBatch batch) {
        if(isActive)
        {// Adjust size based on hover
            float playButtonScale = isHovering ? 0.95f : 1.0f;
            float scaledWidth = width * playButtonScale;
            float scaledHeight = height * playButtonScale;
            float playButtonCenterX = x + width / 2;
            float playButtonCenterY = y + height / 2;

            batch.draw(isHovering ? pressedButton : button,
                    playButtonCenterX - scaledWidth / 2,
                    playButtonCenterY - scaledHeight / 2,
                    scaledWidth,
                    scaledHeight);
        }
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public void buttonPlacement(){

        if(Gdx.input.isButtonJustPressed(Input.Keys.UP)) y+= (int) (50*Gdx.graphics.getDeltaTime());
        if(Gdx.input.isButtonJustPressed(Input.Keys.DOWN)) y-= (int) (50*Gdx.graphics.getDeltaTime());
        if(Gdx.input.isButtonJustPressed(Input.Keys.RIGHT)) x-= (int) (50*Gdx.graphics.getDeltaTime());
        if(Gdx.input.isButtonJustPressed(Input.Keys.LEFT)) x+= (int) (50*Gdx.graphics.getDeltaTime());

    }

    public void setHandle(boolean b) {
        handle=b;
    }
}
