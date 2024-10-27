package io.angrybirds.blocks;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class GlassBlock extends Block {

    public GlassBlock(int x, int y, Sprite sprite, int strength, float rotation) {
        super(x,y,sprite,strength);
        super.setRotation(rotation);
    }
}
