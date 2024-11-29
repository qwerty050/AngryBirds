package io.angrybirds.pigs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

public class LargePig extends Pig{
    private Texture pigTexture;
    private final int LargePig_HEALTH = (int) (17500/1.4);
    private final int LargePig_POINTS= 10000;

    public LargePig(World world, int x, int y) {
        super(world, x, y, 175,10000);

        pigTexture = new Texture(Gdx.files.internal("birds.png"));
        // Load the bird sprites into an array
        Sprite[] HealthyPigSprites = new Sprite[] {
                new Sprite(pigTexture,585,685,95,95),
                new Sprite(pigTexture,683,685,95,95),
                new Sprite(pigTexture,373,980,95,95),
        };

        Sprite[] dmg1PigSprites = new Sprite[] {
                new Sprite(pigTexture,782,685,95,95),
                new Sprite(pigTexture,977,685,95,95),
                new Sprite(pigTexture,373,882,95,95),
        };

        Sprite[] dmg2PigSprites = new Sprite[] {
                new Sprite(pigTexture,880,685,95,95),
                new Sprite(pigTexture,373,784,95,95),
                new Sprite(pigTexture,373,1077,95,95),
        };

        super.setFullHealthSprites(HealthyPigSprites);
        super.setDmg1Sprites(dmg1PigSprites);
        super.setDmg2Sprites(dmg2PigSprites);
        super.spriteSetter(Gdx.graphics.getDeltaTime());
        super.setID(23);
        super.createBody(world, x, y);
    }

}
