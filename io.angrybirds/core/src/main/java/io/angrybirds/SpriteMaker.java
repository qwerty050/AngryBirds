package io.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteMaker {
    private Texture woodblock;

    public SpriteMaker() {
        woodblock=new Texture(Gdx.files.internal("blocks_wood.png"));
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

}
