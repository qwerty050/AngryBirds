package io.angrybirds.pigs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

public class MedPig extends Pig{
    private Texture pigTexture;
    private final int MedPig_HEALTH = 15000;
    private final int MedPig_POINTS = 7500;

    public MedPig(World world, int x, int y) {
        super(world, x, y, 15000,7500);

        pigTexture = new Texture(Gdx.files.internal("birds.png"));
        // Load the bird sprites into an array
        Sprite[] HealthyPigSprites = new Sprite[] {
                new Sprite(pigTexture,1024,967,77,77),
                new Sprite(pigTexture,472,1303,77,77),
                new Sprite(pigTexture,261,1378,77,77),
        };

        Sprite[] dmg1PigSprites = new Sprite[] {
                new Sprite(pigTexture,656,1050,77,77),
                new Sprite(pigTexture,944,967,77,77),
                new Sprite(pigTexture,472,1224,77,77),
        };

        Sprite[] dmg2PigSprites = new Sprite[] {
                new Sprite(pigTexture,655,1129,77,77),
                new Sprite(pigTexture,656,1208,77,77),
                new Sprite(pigTexture,655,1287,77,77),
        };

        super.setFullHealthSprites(HealthyPigSprites);
        super.setDmg1Sprites(dmg1PigSprites);
        super.setDmg2Sprites(dmg2PigSprites);
        super.spriteSetter(Gdx.graphics.getDeltaTime());
        super.createBody(world, x, y);
    }

}
