package io.angrybirds.pigs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

public class SmallPig extends Pig{
    private Texture pigTexture;
    private final int SMALLPIG_HEALTH = 12500;
    private final int SMALLPIG_POINTS = 5000;

    public SmallPig(World world, int x, int y) {
        super(world, x, y, 12500, 5000);

        pigTexture = new Texture(Gdx.files.internal("birds.png"));
        // Load the bird sprites into an array
        Sprite[] HealthyPigSprites = new Sprite[] {
                new Sprite(pigTexture,847,1294,48,48),
                new Sprite(pigTexture,913,1239,48,48),
                new Sprite(pigTexture,984,1182,48,48),
        };

        Sprite[] dmg1PigSprites = new Sprite[] {
                new Sprite(pigTexture,911,1288,48,48),
                new Sprite(pigTexture,846,1343,48,48),
                new Sprite(pigTexture,1033,1181,48,48),
        };

        Sprite[] dmg2PigSprites = new Sprite[] {
                new Sprite(pigTexture,846,1393,48,48),
                new Sprite(pigTexture,1083,1181,48,48),
                new Sprite(pigTexture,776,220,48,48),
        };

        super.setFullHealthSprites(HealthyPigSprites);
        super.setDmg1Sprites(dmg1PigSprites);
        super.setDmg2Sprites(dmg2PigSprites);
        super.spriteSetter(Gdx.graphics.getDeltaTime());
        super.createBody(world, x, y);
    }

}
