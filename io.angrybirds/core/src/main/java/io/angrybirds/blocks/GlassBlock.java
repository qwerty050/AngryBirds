package io.angrybirds.blocks;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class GlassBlock extends Block {

    private static final float GlassBlock_HEALTH = 15000f;
    private static final float GlassBlock_POINTS = 750;

    public GlassBlock(World world, float x, float y, float width, float height) {
        super(GlassBlock_HEALTH, world, x, y, width, height,750);


    }
    public GlassBlock(World world, float x, float y, Sprite d1,Sprite d2,Sprite d3,Sprite d4) {
        super(GlassBlock_HEALTH, world, x, y, d1.getWidth()/2, d1.getHeight()/2,750);
        super.setFullHealth(d1);
        super.setDamaged1(d1);
        super.setDamaged2(d2);
        super.setDamaged3(d3);
    }
}
