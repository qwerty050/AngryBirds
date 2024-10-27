package io.angrybirds.blocks;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class StoneBlock extends Block {

    public StoneBlock(int x, int y, Sprite sprite, int strength, float rotation) {
        super(x,y,sprite,strength);
        super.setRotation(rotation);
    }

}
