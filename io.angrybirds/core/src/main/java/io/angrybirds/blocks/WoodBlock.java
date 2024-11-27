package io.angrybirds.blocks;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class WoodBlock extends Block {
    private static final float WOODBLOCK_HEALTH = 15000f;
    private static final float WOODBLOCK_POINTS = 500f;

    public WoodBlock(World world, float x, float y, float width, float height) {
        super(WOODBLOCK_HEALTH, world, x, y, width, height,500);
    }


    public WoodBlock(World world, float x, float y, Sprite d1,Sprite d2,Sprite d3,Sprite d4) {
        super(WOODBLOCK_HEALTH, world, x, y, d1.getWidth()/2, d1.getHeight()/2,500);

        super.setFullHealth(d1);
        super.setDamaged1(d1);
        super.setDamaged2(d2);
        super.setDamaged3(d3);
    }

}
