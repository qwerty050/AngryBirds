package io.angrybirds.birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;

public class RedBird extends Bird {
    private Sprite[] redBirdSprites;

    public RedBird(World world ,int x, int y) {
        super(world, x, y);

        // Load the bird sprites into an array
        redBirdSprites = new Sprite[] {
            new Sprite(new Texture("redbird1.png")),
            new Sprite(new Texture("redbird2.png")),
            new Sprite(new Texture("redbird3.png"))
        };

        super.setID(1);
        super.setBirdSprites(redBirdSprites);
        super.spriteSetter(Gdx.graphics.getDeltaTime());
        super.setFlyingSprite(new Sprite(new Texture("birds.png"),962,1374,44,41));
        super.setDeadSprite(new Sprite(new Texture("birds.png"),962,1284,44,44));
        super.createBody(world,x,y);
        super.getBody().setActive(false);
    }




}
