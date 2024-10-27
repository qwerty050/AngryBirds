package io.angrybirds.blocks;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class WoodBlock extends Block {

    public WoodBlock(int x, int y, Sprite sprite, int strength, float rotation) {
        super(x,y,sprite,strength);
        super.setRotation(rotation);
    }

}
