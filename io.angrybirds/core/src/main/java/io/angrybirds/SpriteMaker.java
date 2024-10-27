package io.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import io.angrybirds.blocks.GlassBlock;
import io.angrybirds.blocks.StoneBlock;
import io.angrybirds.blocks.WoodBlock;

public class SpriteMaker {
    private Texture woodblock;
    private Texture stoneblock;
    private Texture glassblock;

    public SpriteMaker() {
        woodblock=new Texture(Gdx.files.internal("blocks_wood.png"));
        stoneblock=new Texture(Gdx.files.internal("blocks_stone.png"));
        glassblock=new Texture(Gdx.files.internal("blocks_glass.png"));
    }
    //Wood blocks
    public WoodBlock woodlogShort(int x, int y, float rotation) {
        return new WoodBlock(x,y, new Sprite(woodblock, 288,345,83,20),1,rotation);
    }
    public WoodBlock woodlogVShort(int x, int y, float rotation) {
        return new WoodBlock(x,y, new Sprite(woodblock, 458,256,41,22),1,rotation);
    }
    public WoodBlock woodlogthick(int x, int y ,float rotation){
        return new WoodBlock(x,y, new Sprite(woodblock, 246,82,84,42),1,rotation);
    }
    public WoodBlock woodbox(int x, int y, float rotation){
        return new WoodBlock(x,y, new Sprite(woodblock, 2,337,41,41),1,rotation);
    }
    public WoodBlock woodlogMed(int x, int y, float rotation){
        return new WoodBlock(x,y, new Sprite(woodblock, 288,279,168,20),1,rotation);
    }

    public GlassBlock glassLogShort(int x, int y, float rotation) {
        return new GlassBlock(x, y, new Sprite(glassblock, 288, 345, 83, 20), 1, rotation);
    }
    public GlassBlock glassLogVShort(int x, int y, float rotation) {
        return new GlassBlock(x, y, new Sprite(glassblock, 458, 256, 41, 22), 1, rotation);
    }
    public GlassBlock glassLogThick(int x, int y, float rotation) {
        return new GlassBlock(x, y, new Sprite(glassblock, 246, 82, 84, 42), 1, rotation);
    }
    public GlassBlock glassBox(int x, int y, float rotation) {
        return new GlassBlock(x, y, new Sprite(glassblock, 2, 337, 41, 41), 1, rotation);
    }
    public GlassBlock glassLogMed(int x, int y, float rotation) {
        return new GlassBlock(x, y, new Sprite(glassblock, 288, 279, 168, 20), 1, rotation);
    }

    public StoneBlock stoneLogShort(int x, int y, float rotation) {
        return new StoneBlock(x, y, new Sprite(stoneblock, 288, 345, 83, 20), 1, rotation);
    }
    public StoneBlock stoneLogVShort(int x, int y, float rotation) {
        return new StoneBlock(x, y, new Sprite(stoneblock, 458, 256, 41, 22), 1, rotation);
    }
    public StoneBlock stoneLogThick(int x, int y, float rotation) {
        return new StoneBlock(x, y, new Sprite(stoneblock, 246, 82, 84, 42), 1, rotation);
    }
    public StoneBlock stoneBox(int x, int y, float rotation) {
        return new StoneBlock(x, y, new Sprite(stoneblock, 2, 337, 41, 41), 1, rotation);
    }
    public StoneBlock stoneLogMed(int x, int y, float rotation) {
        return new StoneBlock(x, y, new Sprite(stoneblock, 288, 279, 168, 20), 1, rotation);
    }


}
