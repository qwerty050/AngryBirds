package io.test;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;


public class BlockMaker {
    private Texture woodblock;
    private Texture stoneblock;
    private Texture glassblock;
    private World world;

    public BlockMaker(World world) {
        this.world = world;
        woodblock = new Texture(Gdx.files.internal("blocks_wood.png"));
        stoneblock = new Texture(Gdx.files.internal("blocks_stone.png"));
        glassblock = new Texture(Gdx.files.internal("blocks_glass.png"));

    }

    public Block WoodLong(int x, int y, float rotation) {
        Sprite sprite1 = new Sprite(stoneblock, 288,168,205,21);
        Sprite sprite2 = new Sprite(stoneblock, 288,190,205,21);
        Sprite sprite3 = new Sprite(stoneblock, 288,212,205,21);
        Sprite sprite4 = new Sprite(stoneblock, 288,212,205,21);
        Block block=new WoodBlock(world,x,y,sprite1,sprite2,sprite3,sprite3);
        block.getBody().setTransform(block.getBody().getPosition(), rotation);
        return block;
    }


}
