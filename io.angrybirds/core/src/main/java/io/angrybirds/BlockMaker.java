package io.angrybirds;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import io.angrybirds.blocks.Block;
import io.angrybirds.blocks.GlassBlock;
import io.angrybirds.blocks.StoneBlock;
import io.angrybirds.blocks.WoodBlock;


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

    public Block StoneLongest(int x, int y, float rotation) {
        Sprite sprite1 = new Sprite(stoneblock, 248,170,205,20);
        Sprite sprite2 = new Sprite(stoneblock, 248,190,205,20);
        Sprite sprite3 = new Sprite(stoneblock, 248,211,205,20);
        Sprite sprite4 = new Sprite(stoneblock, 248,232,205,20);
        Block block=new StoneBlock(world,x,y,sprite1,sprite2,sprite3,sprite4);
        block.getBody().setTransform(block.getBody().getPosition(), rotation);
        return block;
    }

    public Block WoodThick(int x, int y, float rotation) {
        Sprite sprite1 = new Sprite(woodblock, 245,83,85,41);
        Sprite sprite2 = new Sprite(woodblock, 330,83,85,41);
        Sprite sprite3 = new Sprite(woodblock, 415,83,85,41);
        Sprite sprite4 = new Sprite(woodblock, 245,125,85,41);
        Block block=new WoodBlock(world,x,y,sprite1,sprite2,sprite3,sprite4);
        block.getBody().setTransform(block.getBody().getPosition(), rotation);
        return block;
    }
    public Block Woodshort(int x, int y, float rotation) {
        Sprite sprite1 = new Sprite(woodblock, 288,345,85,21);
        Sprite sprite2 = new Sprite(woodblock, 288+85,345,85,21);
        Sprite sprite3 = new Sprite(woodblock, 288,345+21,85,21);
        Sprite sprite4 = new Sprite(woodblock, 288+85,345+21,85,21);
        Block block=new WoodBlock(world,x,y,sprite1,sprite2,sprite3,sprite4);
        block.getBody().setTransform(block.getBody().getPosition(), rotation);
        return block;
    }
    public Block Woodshortest(int x, int y, float rotation) {
        Sprite sprite1 = new Sprite(woodblock, 457,256,43,21);
        Sprite sprite2 = new Sprite(woodblock, 457,256+21,43,21);
        Sprite sprite3 = new Sprite(woodblock, 457,256+21+21,43,21);
        Sprite sprite4 = new Sprite(woodblock, 457,256+21+21+21,43,21);
        Block block=new WoodBlock(world,x,y,sprite1,sprite2,sprite3,sprite4);
        block.getBody().setTransform(block.getBody().getPosition(), rotation);
        return block;
    }
    public Block Woodsquare(int x, int y, float rotation) {
        Sprite sprite1 = new Sprite(woodblock, 2,337,42,42);
        Sprite sprite2 = new Sprite(woodblock, 86,337,42,42);
        Sprite sprite3 = new Sprite(woodblock, 332,125,42,42);
        Sprite sprite4 = new Sprite(woodblock, 375,125,42,42);
        Block block=new WoodBlock(world,x,y,sprite1,sprite2,sprite3,sprite4);
        block.getBody().setTransform(block.getBody().getPosition(), rotation);
        return block;
    }
    public Block WoodLongest(int x, int y, float rotation) {
        Sprite sprite1 = new Sprite(woodblock, 288,168,205,21);
        Sprite sprite2 = new Sprite(woodblock, 288,190,205,21);
        Sprite sprite3 = new Sprite(woodblock, 288,212,205,21);
        Sprite sprite4 = new Sprite(woodblock, 288,212+21,205,21);
        Block block=new WoodBlock(world,x,y,sprite1,sprite2,sprite3,sprite4);
        block.getBody().setTransform(block.getBody().getPosition(), rotation);
        return block;
    }
    public Block WoodLong(int x, int y, float rotation) {
        Sprite sprite1 = new Sprite(woodblock, 288,212+21+21,166,21);
        Sprite sprite2 = new Sprite(woodblock, 288,212+21*3,166,21);
        Sprite sprite3 = new Sprite(woodblock, 288,212+21*4,166,21);
        Sprite sprite4 = new Sprite(woodblock, 288,212+21*5,166,21);
        Block block=new WoodBlock(world,x,y,sprite1,sprite2,sprite3,sprite4);
        block.getBody().setTransform(block.getBody().getPosition(), rotation);
        return block;
    }
    public Block StoneLong(int x, int y, float rotation) {
        Sprite sprite1 = new Sprite(stoneblock, 248,257,170,20);
        Sprite sprite2 = new Sprite(stoneblock, 248,277,170,20);
        Sprite sprite3 = new Sprite(stoneblock, 248,297,170,20);
        Sprite sprite4 = new Sprite(stoneblock, 248,317,170,20);
        Block block=new StoneBlock(world,x,y,sprite1,sprite2,sprite3,sprite4);
        block.getBody().setTransform(block.getBody().getPosition(), rotation);
        return block;
    }
    public Block StoneThick(int x, int y, float rotation) {
        Sprite sprite1 = new Sprite(stoneblock, 247,83,84,41);
        Sprite sprite2 = new Sprite(stoneblock, 247+85,83,84,41);
        Sprite sprite3 = new Sprite(stoneblock, 247+85+85,83,84,41);
        Sprite sprite4 = new Sprite(stoneblock, 247,83+43,84,41);
        Block block=new StoneBlock(world,x,y,sprite1,sprite2,sprite3,sprite4);
        block.getBody().setTransform(block.getBody().getPosition(), rotation);
        return block;
    }
    public Block WoodCube(int x, int y, float rotation) {
        Sprite sprite1 = new Sprite(woodblock, 0,1,83,83);
        Sprite sprite2 = new Sprite(woodblock, 0,84,83,83);
        Sprite sprite3 = new Sprite(woodblock, 0,84+84,83,83);
        Sprite sprite4 = new Sprite(woodblock, 0,84+84+83,83,83);
        Block block=new WoodBlock(world,x,y,sprite1,sprite2,sprite3,sprite4);
        block.getBody().setTransform(block.getBody().getPosition(), rotation);
        return block;
    }
    public Block GlassLongest(int x, int y, float rotation) {
        Sprite sprite1 = new Sprite(glassblock, 288,213,205,20);
        Sprite sprite2 = new Sprite(glassblock, 288,192,205,20);
        Sprite sprite3 = new Sprite(glassblock, 288,234,205,20);
        Sprite sprite4 = new Sprite(glassblock, 288,170,205,20);
        Block block=new GlassBlock(world,x,y,sprite1,sprite2,sprite3,sprite4);
        block.getBody().setTransform(block.getBody().getPosition(), rotation);
        return block;
    }
    public Block GlassShort(int x, int y, float rotation) {
        Sprite sprite1 = new Sprite(glassblock, 288,345,85,21);
        Sprite sprite2 = new Sprite(glassblock, 288+85,345,85,21);
        Sprite sprite3 = new Sprite(glassblock, 288,345+21,85,21);
        Sprite sprite4 = new Sprite(glassblock, 288+85,345+21,85,21);
        Block block=new GlassBlock(world,x,y,sprite1,sprite2,sprite3,sprite4);
        block.getBody().setTransform(block.getBody().getPosition(), rotation);
        return block;
    }
    public Block GlassSquare(int x, int y, float rotation) {
        Sprite sprite1 = new Sprite(glassblock, 415,127,43,43);
        Sprite sprite2 = new Sprite(glassblock, 457,127,43,43);
        Sprite sprite3 = new Sprite(glassblock, 244,170,43,43);
        Sprite sprite4 = new Sprite(glassblock, 244,170+43,43,43);
        Block block=new GlassBlock(world,x,y,sprite1,sprite2,sprite3,sprite4);
        block.getBody().setTransform(block.getBody().getPosition(), rotation);
        return block;
    }

    public Block GlassLong(int x, int y, float rotation) {
        Sprite sprite1 = new Sprite(glassblock, 288,261,170,20);
        Sprite sprite2 = new Sprite(glassblock, 288,261+21,170,20);
        Sprite sprite3 = new Sprite(glassblock, 288,261+21,170,20);
        Sprite sprite4 = new Sprite(glassblock, 288,261+21,170,20);
        Block block=new GlassBlock(world,x,y,sprite1,sprite2,sprite3,sprite4);
        block.getBody().setTransform(block.getBody().getPosition(), rotation);
        return block;
    }

    public Block StoneCube(int x, int y, float rotation) {
        Sprite sprite1 = new Sprite(stoneblock, 0,1,83,83);
        Sprite sprite2 = new Sprite(stoneblock, 0,84,83,83);
        Sprite sprite3 = new Sprite(stoneblock, 0,84+84,83,83);
        Sprite sprite4 = new Sprite(stoneblock, 0,84+84+83,83,83);
        Block block=new StoneBlock(world,x,y,sprite1,sprite2,sprite3,sprite4);
        block.getBody().setTransform(block.getBody().getPosition(), rotation);
        return block;
    }

}
