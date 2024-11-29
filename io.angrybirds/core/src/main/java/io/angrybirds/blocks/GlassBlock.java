package io.angrybirds.blocks;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class GlassBlock extends Block {

    private static final float GLASSBLOCK_HEALTH = 150/1.4f;
    private static final int GLASSBLOCK_POINTS = 750;

    public GlassBlock(World world, float x, float y, float width, float height) {
        super(GLASSBLOCK_HEALTH, world, x, y, width, height,GLASSBLOCK_POINTS);


    }
    public GlassBlock(World world, float x, float y, Sprite d1,Sprite d2,Sprite d3,Sprite d4) {
        super(GLASSBLOCK_HEALTH, world, x, y, d1.getWidth()/2, d1.getHeight()/2,GLASSBLOCK_POINTS);
        super.setFullHealth(d1);
        super.setDamaged1(d1);
        super.setDamaged2(d2);
        super.setDamaged3(d3);
    }
}
