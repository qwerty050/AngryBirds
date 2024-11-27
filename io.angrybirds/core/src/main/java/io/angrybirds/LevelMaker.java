package io.angrybirds;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import io.angrybirds.birds.Bird;
import io.angrybirds.birds.BlackBird;
import io.angrybirds.birds.BlueBird;
import io.angrybirds.birds.RedBird;
import io.angrybirds.blocks.Block;
import io.angrybirds.pigs.LargePig;
import io.angrybirds.pigs.MedPig;
import io.angrybirds.pigs.Pig;
import io.angrybirds.pigs.SmallPig;
import io.angrybirds.screens.LevelScreen;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import static java.util.Arrays.asList;

public class LevelMaker {
    private BlockMaker blockMaker;
    private World world;

    public LevelMaker(BlockMaker blockMaker, World world) {
        this.blockMaker = blockMaker;
        this.world=world;
    }


    public void makeLevel(int level, ArrayList<Block> blocks, ArrayList<Pig> pigs, ArrayList<Bird> birds, SlingShot slingShot) {
        if(level == 1){
            blocks=level1Blocks();
            pigs=level1Pigs();
            birds=level1Birds();
            slingShot=new SlingShot(200,170,birds);
        }
        else if(level == 2){
            blocks=level2Blocks();
            pigs=level2Pigs();
            birds=level2Birds();
            slingShot=new SlingShot(200,150,birds);
        }
        else if(level == 3){
            blocks=level3Blocks();
            pigs=level3Pigs();
            birds=level3Birds();
            slingShot=new SlingShot(200,150,birds);
        }
    }

//    public Screen level1(){
//        // Blocks
//        blocks = new ArrayList<>(asList(
//                blockMaker.WoodThick(475, 83 + 20, 1.57f),  // Adding 20 to y
//                blockMaker.WoodThick(559, 83 + 20, 1.57f),  // Adding 20 to y
//                blockMaker.Woodshort(477, 126 + 20, 1.57f), // Adding 20 to y
//                blockMaker.Woodshort(559, 126 + 20, 1.57f), // Adding 20 to y
//                blockMaker.StoneLongest(517, 150 + 20, 0),  // Adding 20 to y
//                blockMaker.StoneLongest(642, 150 + 20, 0),  // Adding 20 to y
//                blockMaker.WoodThick(475 + 125, 83 + 20, 1.57f), // Adding 20 to y
//                blockMaker.WoodThick(559 + 125, 83 + 20, 1.57f), // Adding 20 to y
//                blockMaker.Woodshort(477 + 125, 126 + 20, 1.57f), // Adding 20 to y
//                blockMaker.Woodshort(559 + 125, 126 + 20, 1.57f), // Adding 20 to y
//
//                blockMaker.Woodshort(493, 178 + 20, 1.57f), // Adding 20 to y
//                blockMaker.Woodshort(607, 178 + 20, 1.57f), // Adding 20 to y
//                blockMaker.Woodshortest(550, 178 + 20, 1.57f), // Adding 20 to y
//                blockMaker.WoodLong(678, 178 + 21 + 20, 1.57f), // Adding 20 to y
//
//                blockMaker.WoodThick(492, 220 + 20, 1.57f), // Adding 20 to y
//                blockMaker.WoodThick(550, 178 + 21 + 20, 1.57f), // Adding 20 to y
//                blockMaker.WoodThick(606, 220 + 20, 1.57f), // Adding 20 to y
//                blockMaker.Woodshortest(550, 178 + 21 + 21 + 20, 1.57f), // Adding 20 to y
//
//                blockMaker.StoneLongest(528, 220 + 27 + 20, 0), // Adding 20 to y
//                blockMaker.StoneLongest(633, 220 + 27 + 20, 0), // Adding 20 to y
//
//                blockMaker.Woodsquare(535, 262 + 20, 0),  // Adding 20 to y
//                blockMaker.Woodsquare(661, 262 + 20, 0),  // Adding 20 to y
//                blockMaker.Woodshort(553, 272 + 20, 1.57f), // Adding 20 to y
//                blockMaker.Woodshort(645, 273 + 20, 1.57f), // Adding 20 to y
//                blockMaker.Woodsquare(553, 303 + 20, 0),  // Adding 20 to y
//                blockMaker.Woodsquare(645, 303 + 20, 0),  // Adding 20 to y
//                blockMaker.StoneLongest(598, 320 + 20, 0), // Adding 20 to y
//                blockMaker.Woodsquare(600, 339 + 20, 0)   // Adding 20 to y
//        ));
//
//        // Pigs
//        pigs = new ArrayList<>(asList(
//                new SmallPig(world, 535, 283 + 20), // Adding 20 to y
//                new MedPig(world, 644, 176 + 20),   // Adding 20 to y
//                new MedPig(world, 601, 270 + 20),   // Adding 20 to y
//                new SmallPig(world, 521, 169 + 20)  // Adding 20 to y
//        ));
//
//        // Birds
//        birds = new ArrayList<>(asList(
//                new RedBird(world,100,100),
//                new BlueBird(world,200,100),
//                new RedBird(world,300,100)
//        ));
//
//        return new LevelScreen(world, game, batch, blocks, pigs, birds, new SlingShot(200, 150, birds));
//    }

    public ArrayList<Block> level1Blocks(){
        // Blocks
        return new ArrayList<>(asList(
                blockMaker.WoodThick(475, 83 + 20, 1.57f),  // Adding 20 to y
                blockMaker.WoodThick(559, 83 + 20, 1.57f),  // Adding 20 to y
                blockMaker.Woodshort(477, 126 + 20, 1.57f), // Adding 20 to y
                blockMaker.Woodshort(559, 126 + 20, 1.57f), // Adding 20 to y
                blockMaker.StoneLongest(517, 150 + 20, 0),  // Adding 20 to y
                blockMaker.StoneLongest(642, 150 + 20, 0),  // Adding 20 to y
                blockMaker.WoodThick(475 + 125, 83 + 20, 1.57f), // Adding 20 to y
                blockMaker.WoodThick(559 + 125, 83 + 20, 1.57f), // Adding 20 to y
                blockMaker.Woodshort(477 + 125, 126 + 20, 1.57f), // Adding 20 to y
                blockMaker.Woodshort(559 + 125, 126 + 20, 1.57f), // Adding 20 to y

                blockMaker.Woodshort(493, 178 + 20, 1.57f), // Adding 20 to y
                blockMaker.Woodshort(607, 178 + 20, 1.57f), // Adding 20 to y
                blockMaker.Woodshortest(550, 178 + 20, 1.57f), // Adding 20 to y
                blockMaker.WoodLong(678, 178 + 21 + 20, 1.57f), // Adding 20 to y

                blockMaker.WoodThick(492, 220 + 20, 1.57f), // Adding 20 to y
                blockMaker.WoodThick(550, 178 + 21 + 20, 1.57f), // Adding 20 to y
                blockMaker.WoodThick(606, 220 + 20, 1.57f), // Adding 20 to y
                blockMaker.Woodshortest(550, 178 + 21 + 21 + 20, 1.57f), // Adding 20 to y

                blockMaker.StoneLongest(528, 220 + 27 + 20, 0), // Adding 20 to y
                blockMaker.StoneLongest(633, 220 + 27 + 20, 0), // Adding 20 to y

                blockMaker.Woodsquare(535, 262 + 20, 0),  // Adding 20 to y
                blockMaker.Woodsquare(661, 262 + 20, 0),  // Adding 20 to y
                blockMaker.Woodshort(553, 272 + 20, 1.57f), // Adding 20 to y
                blockMaker.Woodshort(645, 273 + 20, 1.57f), // Adding 20 to y
                blockMaker.Woodsquare(553, 303 + 20, 0),  // Adding 20 to y
                blockMaker.Woodsquare(645, 303 + 20, 0),  // Adding 20 to y
                blockMaker.StoneLongest(598, 320 + 20, 0), // Adding 20 to y
                blockMaker.Woodsquare(600, 339 + 20, 0)   // Adding 20 to y
        ));
    }

    public ArrayList<Pig> level1Pigs(){
        // Pigs
        return new ArrayList<>(asList(
                new SmallPig(world, 535, 283 + 20), // Adding 20 to y
                new MedPig(world, 644, 176 + 20),   // Adding 20 to y
                new MedPig(world, 601, 270 + 20),   // Adding 20 to y
                new SmallPig(world, 521, 169 + 20)  // Adding 20 to y
        ));
    }

    public ArrayList<Bird> level1Birds(){
        // Birds
        return new ArrayList<>(asList(
                new RedBird(world,100,100),
                new BlueBird(world,150,100),
                new RedBird(world,300,100)
        ));
    }



//    public Screen level2(){
//        // Blocks
//        blocks = new ArrayList<>(asList(
//                blockMaker.WoodCube(506, 83 + 20, 0),                // Adding 20 to y
//                blockMaker.WoodCube(544, 83 + 20, 0),                // Adding 20 to y
//                blockMaker.WoodCube(506 + 120, 83 + 20, 0),          // Adding 20 to y
//                blockMaker.WoodCube(506 + 120 + 40, 83 + 20, 0),     // Adding 20 to y
//                blockMaker.WoodCube(506 + 120, 83 + 40 + 20, 0),     // Adding 20 to y
//                blockMaker.WoodCube(506 + 120 + 40, 83 + 40 + 20, 0),// Adding 20 to y
//
//                blockMaker.Woodshort(506 - 120, 83 + 20, 1.57f),     // Adding 20 to y
//                blockMaker.Woodshort(506 - 120 + 40, 83 + 20, 1.57f),// Adding 20 to y
//                blockMaker.Woodshort(500, 83 + 40 + 20, 1.57f),      // Adding 20 to y
//                blockMaker.Woodshort(551, 83 + 40 + 20, 1.57f),      // Adding 20 to y
//                blockMaker.Woodshort(620, 83 + 80 + 20, 1.57f),      // Adding 20 to y
//                blockMaker.Woodshort(620 + 51, 83 + 80 + 20, 1.57f), // Adding 20 to y
//
//                blockMaker.WoodLong(506 - 100, 110 + 20, 0),         // Adding 20 to y
//                blockMaker.WoodLong(525, 149 + 20, 0),               // Adding 20 to y
//                blockMaker.StoneLong(645, 190 + 20, 0),              // Adding 20 to y
//                blockMaker.StoneLong(645, 190 + 26 + 20 + 20, 0),    // Adding 20 to y
//                blockMaker.WoodCube(506 + 120, 190 + 26 + 20, 0),    // Adding 20 to y
//                blockMaker.WoodCube(506 + 120 + 40, 190 + 26 + 20, 0),// Adding 20 to y
//
//                blockMaker.WoodThick(395, 133 + 20, 1.57f),          // Adding 20 to y
//                blockMaker.WoodThick(417, 133 + 20, 1.57f),          // Adding 20 to y
//                blockMaker.WoodLong(506 - 100, 133 + 20 + 20, 0),    // Adding 20 to y
//                blockMaker.Woodshort(506 - 120, 133 + 33 + 22 + 20, 1.57f), // Adding 20 to y
//                blockMaker.Woodshort(506 - 120 + 40, 133 + 33 + 22 + 20, 1.57f), // Adding 20 to y
//                blockMaker.WoodLong(506 - 100, 133 + 20 + 33 + 22 + 20, 0), // Adding 20 to y
//                blockMaker.Woodshort(506 - 120 + 5, 133 + 33 + 22 + 22 + 33 + 20, 1.57f), // Adding 20 to y
//                blockMaker.Woodshort(506 - 120 + 40 - 5, 133 + 33 + 22 + 33 + 22 + 20, 1.57f), // Adding 20 to y
//                blockMaker.WoodLong(506 - 100, 133 + 20 + 33 + 22 + 33 + 22 + 20, 0), // Adding 20 to y
//
//                blockMaker.WoodThick(645 - 10, 190 + 26 + 20 + 33 + 20, 1.57f), // Adding 20 to y
//                blockMaker.WoodThick(645 + 22 - 10, 190 + 26 + 20 + 33 + 20, 1.57f), // Adding 20 to y
//                blockMaker.StoneLong(645, 190 + 26 + 20 + 33 + 20 + 33, 0), // Adding 20 to y
////                blockMaker.Woodshort(620, 190 + 26 + 20 + 33 + 20 + 33 + 20, 1.57f), // Adding 20 to y
////                blockMaker.Woodshort(620 + 51, 190 + 26 + 20 + 33 + 20 + 33 + 20, 1.57f), // Adding 20 to y
////                blockMaker.StoneLong(645, 190 + 26 + 20 + 33 + 20 + 33 + 20 + 20, 0), // Adding 20 to y
//
//                blockMaker.WoodLongest(504, 207 + 20, 1.57f),        // Adding 20 to y
//                blockMaker.WoodLongest(546, 207 + 20, 1.57f),        // Adding 20 to y
//
//                blockMaker.StoneLong(524, 263 + 20, 0),               // Adding 20 to y
//                blockMaker.StoneLong(524, 314 + 20, 0),               // Adding 20 to y
//
//                blockMaker.StoneThick(496, 289 + 20, 1.57f),          // Adding 20 to y
//                blockMaker.StoneThick(546, 289 + 20, 1.57f)           // Adding 20 to y
//        ));
//
//        // Pigs
//        pigs = new ArrayList<>(asList(
//                new MedPig(world, 644, 318 + 20),  // Adding 20 to y
//                new LargePig(world, 407, 293 + 20),// Adding 20 to y
//                new SmallPig(world, 526, 169 + 20),// Adding 20 to y
//                new SmallPig(world, 645, 169 + 5),// Adding 20 to y
//                new SmallPig(world, 407, 169 + 20) // Adding 20 to y
//        ));
//
//        // Birds
//        birds = new ArrayList<>(asList(
//                // Birds array is empty
//        ));
//
//        return new LevelScreen(world, game, batch, blocks, pigs, birds, new SlingShot(100, 100));
//    }

    public ArrayList<Block> level2Blocks(){
        // Blocks
        return new ArrayList<>(asList(
                blockMaker.WoodCube(506, 83 + 20, 0),                // Adding 20 to y
                blockMaker.WoodCube(544, 83 + 20, 0),                // Adding 20 to y
                blockMaker.WoodCube(506 + 120, 83 + 20, 0),          // Adding 20 to y
                blockMaker.WoodCube(506 + 120 + 40, 83 + 20, 0),     // Adding 20 to y
                blockMaker.WoodCube(506 + 120, 83 + 40 + 20, 0),     // Adding 20 to y
                blockMaker.WoodCube(506 + 120 + 40, 83 + 40 + 20, 0),// Adding 20 to y

                blockMaker.Woodshort(506 - 120, 83 + 20, 1.57f),     // Adding 20 to y
                blockMaker.Woodshort(506 - 120 + 40, 83 + 20, 1.57f),// Adding 20 to y
                blockMaker.Woodshort(500, 83 + 40 + 20, 1.57f),      // Adding 20 to y
                blockMaker.Woodshort(551, 83 + 40 + 20, 1.57f),      // Adding 20 to y
                blockMaker.Woodshort(620, 83 + 80 + 20, 1.57f),      // Adding 20 to y
                blockMaker.Woodshort(620 + 51, 83 + 80 + 20, 1.57f), // Adding 20 to y

                blockMaker.WoodLong(506 - 100, 110 + 20, 0),         // Adding 20 to y
                blockMaker.WoodLong(525, 149 + 20, 0),               // Adding 20 to y
                blockMaker.StoneLong(645, 190 + 20, 0),              // Adding 20 to y
                blockMaker.StoneLong(645, 190 + 26 + 20 + 20, 0),    // Adding 20 to y
                blockMaker.WoodCube(506 + 120, 190 + 26 + 20, 0),    // Adding 20 to y
                blockMaker.WoodCube(506 + 120 + 40, 190 + 26 + 20, 0),// Adding 20 to y

                blockMaker.WoodThick(395, 133 + 20, 1.57f),          // Adding 20 to y
                blockMaker.WoodThick(417, 133 + 20, 1.57f),          // Adding 20 to y
                blockMaker.WoodLong(506 - 100, 133 + 20 + 20, 0),    // Adding 20 to y
                blockMaker.Woodshort(506 - 120, 133 + 33 + 22 + 20, 1.57f), // Adding 20 to y
                blockMaker.Woodshort(506 - 120 + 40, 133 + 33 + 22 + 20, 1.57f), // Adding 20 to y
                blockMaker.WoodLong(506 - 100, 133 + 20 + 33 + 22 + 20, 0), // Adding 20 to y
                blockMaker.Woodshort(506 - 120 + 5, 133 + 33 + 22 + 22 + 33 + 20, 1.57f), // Adding 20 to y
                blockMaker.Woodshort(506 - 120 + 40 - 5, 133 + 33 + 22 + 33 + 22 + 20, 1.57f), // Adding 20 to y
                blockMaker.WoodLong(506 - 100, 133 + 20 + 33 + 22 + 33 + 22 + 20, 0), // Adding 20 to y

                blockMaker.WoodThick(645 - 10, 190 + 26 + 20 + 33 + 20, 1.57f), // Adding 20 to y
                blockMaker.WoodThick(645 + 22 - 10, 190 + 26 + 20 + 33 + 20, 1.57f), // Adding 20 to y
                blockMaker.StoneLong(645, 190 + 26 + 20 + 33 + 20 + 33, 0), // Adding 20 to y
//                blockMaker.Woodshort(620, 190 + 26 + 20 + 33 + 20 + 33 + 20, 1.57f), // Adding 20 to y
//                blockMaker.Woodshort(620 + 51, 190 + 26 + 20 + 33 + 20 + 33 + 20, 1.57f), // Adding 20 to y
//                blockMaker.StoneLong(645, 190 + 26 + 20 + 33 + 20 + 33 + 20 + 20, 0), // Adding 20 to y

                blockMaker.WoodLongest(504, 207 + 20, 1.57f),        // Adding 20 to y
                blockMaker.WoodLongest(546, 207 + 20, 1.57f),        // Adding 20 to y

                blockMaker.StoneLong(524, 263 + 20, 0),               // Adding 20 to y
                blockMaker.StoneLong(524, 314 + 20, 0),               // Adding 20 to y

                blockMaker.StoneThick(496, 289 + 20, 1.57f),          // Adding 20 to y
                blockMaker.StoneThick(546, 289 + 20, 1.57f)           // Adding 20 to y
        ));
    }

    public ArrayList<Pig> level2Pigs(){
        // Pigs
        return new ArrayList<>(asList(
                new MedPig(world, 644, 318 + 20),  // Adding 20 to y
                new LargePig(world, 407, 293 + 20),// Adding 20 to y
                new SmallPig(world, 526, 169 + 20),// Adding 20 to y
                new SmallPig(world, 645, 169 + 5),// Adding 20 to y
                new SmallPig(world, 407, 169 + 20) // Adding 20 to y
        ));
    }

    public ArrayList<Bird> level2Birds(){
        // Birds
        return new ArrayList<>(asList(
                new BlackBird(world,80,110),
                new BlueBird(world,130,100),
                new RedBird(world,100,100)
        ));

    }


//    public Screen level3(){
//        // Blocks
//        blocks = new ArrayList<>(asList(
//                // 1st tower
//                blockMaker.GlassShort(327, 83 + 20, 1.57f),                  // Adding 20 to y
//                blockMaker.GlassShort(327 + 20, 83 + 20, 1.57f),              // Adding 20 to y
//                blockMaker.GlassShort(327 + 40, 83 + 20, 1.57f),              // Adding 20 to y
//                blockMaker.GlassShort(327, 83 + 42 + 20, 1.57f),              // Adding 20 to y
//                blockMaker.GlassShort(327 + 20, 83 + 42 + 20, 1.57f),          // Adding 20 to y
//                blockMaker.GlassShort(327 + 40, 83 + 42 + 20, 1.57f),          // Adding 20 to y
//
//                blockMaker.GlassSquare(327, 158 + 20, 0),                      // Adding 20 to y
//                blockMaker.GlassSquare(327 + 20, 158 + 20, 0),                  // Adding 20 to y
//                blockMaker.GlassSquare(327 + 40, 158 + 20, 0),                  // Adding 20 to y
//                blockMaker.GlassSquare(327, 158 + 20 + 20, 0),                  // Adding 20 to y
//                blockMaker.GlassSquare(327 + 20, 158 + 20 + 20, 0),              // Adding 20 to y
//                blockMaker.GlassSquare(327 + 40, 158 + 20 + 20, 0),              // Adding 20 to y
//                blockMaker.GlassSquare(327, 158 + 40 + 20, 0),                  // Adding 20 to y
//                blockMaker.GlassSquare(327 + 20, 158 + 40 + 20, 0),              // Adding 20 to y
//                blockMaker.GlassSquare(327 + 40, 158 + 40 + 20, 0),              // Adding 20 to y
//
//                // 2nd tower
//                blockMaker.GlassShort(327 + 100, 83 + 20, 1.57f),               // Adding 20 to y
//                blockMaker.GlassShort(327 + 20 + 100, 83 + 20, 1.57f),           // Adding 20 to y
//                blockMaker.GlassShort(327 + 40 + 100, 83 + 20, 1.57f),           // Adding 20 to y
//
//                blockMaker.GlassSquare(327 + 100, 114 + 20, 0),                  // Adding 20 to y
//                blockMaker.GlassSquare(327 + 20 + 100, 114 + 20, 0),              // Adding 20 to y
//                blockMaker.GlassSquare(327 + 40 + 100, 114 + 20, 0),              // Adding 20 to y
//                blockMaker.GlassSquare(327 + 100, 114 + 20 + 20, 0),              // Adding 20 to y
//                blockMaker.GlassSquare(327 + 20 + 100, 114 + 20 + 20, 0),          // Adding 20 to y
//                blockMaker.GlassSquare(327 + 40 + 100, 114 + 20 + 20, 0),          // Adding 20 to y
//                blockMaker.GlassSquare(327 + 100, 114 + 40 + 20, 0),              // Adding 20 to y
//                blockMaker.GlassSquare(327 + 20 + 100, 114 + 40 + 20, 0),          // Adding 20 to y
//                blockMaker.GlassSquare(327 + 40 + 100, 114 + 40 + 20, 0),          // Adding 20 to y
//
//                // 3rd tower
//                blockMaker.GlassShort(327 + 100 + 100, 83 + 20, 1.57f),           // Adding 20 to y
//                blockMaker.GlassShort(327 + 20 + 100 + 100, 83 + 20, 1.57f),       // Adding 20 to y
//                blockMaker.GlassShort(327 + 40 + 100 + 100, 83 + 20, 1.57f),       // Adding 20 to y
//
//                blockMaker.GlassSquare(327 + 100 + 100, 114 + 20, 0),              // Adding 20 to y
//                blockMaker.GlassSquare(327 + 20 + 100 + 100, 114 + 20, 0),          // Adding 20 to y
//                blockMaker.GlassSquare(327 + 40 + 100 + 100, 114 + 20, 0),          // Adding 20 to y
//                blockMaker.GlassSquare(327 + 100 + 100, 114 + 20 + 20, 0),          // Adding 20 to y
//                blockMaker.GlassSquare(327 + 20 + 100 + 100, 114 + 20 + 20, 0),      // Adding 20 to y
//                blockMaker.GlassSquare(327 + 40 + 100 + 100, 114 + 20 + 20, 0),      // Adding 20 to y
//                blockMaker.GlassSquare(327 + 100 + 100, 114 + 40 + 20, 0),          // Adding 20 to y
//                blockMaker.GlassSquare(327 + 20 + 100 + 100, 114 + 40 + 20, 0),      // Adding 20 to y
//                blockMaker.GlassSquare(327 + 40 + 100 + 100, 114 + 40 + 20, 0),      // Adding 20 to y
//
//                // 4th tower
//                blockMaker.Woodshort(327 + 300, 83 + 20, 1.57f),                   // Adding 20 to y
//                blockMaker.Woodshort(327 + 20 + 300, 83 + 20, 1.57f),               // Adding 20 to y
//                blockMaker.Woodshort(327 + 40 + 300, 83 + 20, 1.57f),               // Adding 20 to y
//                blockMaker.Woodshort(327 + 300, 83 + 42 + 20, 1.57f),               // Adding 20 to y
//                blockMaker.Woodshort(327 + 20 + 300, 83 + 42 + 20, 1.57f),           // Adding 20 to y
//                blockMaker.Woodshort(327 + 40 + 300, 83 + 42 + 20, 1.57f),           // Adding 20 to y
//
//                blockMaker.Woodsquare(327 + 300, 158 + 20, 0),                      // Adding 20 to y
//                blockMaker.Woodsquare(327 + 20 + 300, 158 + 20, 0),                  // Adding 20 to y
//                blockMaker.Woodsquare(327 + 40 + 300, 158 + 20, 0),                  // Adding 20 to y
//                blockMaker.Woodsquare(327 + 300, 158 + 20 + 20, 0),                  // Adding 20 to y
//                blockMaker.Woodsquare(327 + 20 + 300, 158 + 20 + 20, 0),              // Adding 20 to y
//                blockMaker.Woodsquare(327 + 40 + 300, 158 + 20 + 20, 0),              // Adding 20 to y
//                blockMaker.Woodsquare(327 + 300, 158 + 40 + 20, 0),                  // Adding 20 to y
//                blockMaker.Woodsquare(327 + 20 + 300, 158 + 40 + 20, 0),              // Adding 20 to y
//                blockMaker.Woodsquare(327 + 40 + 300, 158 + 40 + 20, 0),              // Adding 20 to y
//
//                blockMaker.GlassLongest(347, 216 + 20, 0),                           // Adding 20 to y
//                blockMaker.GlassLongest(444, 173 + 20, 0),                           // Adding 20 to y
//                blockMaker.GlassLongest(444 + 100, 173 + 20, 0),                     // Adding 20 to y
//                blockMaker.StoneLongest(347 + 300, 216 + 20, 0),                     // Adding 20 to y
//
//                blockMaker.GlassShort(311, 243 + 20, 1.57f),                         // Adding 20 to y
//                blockMaker.GlassShort(385, 243 + 20, 1.57f),                         // Adding 20 to y
//
//                blockMaker.StoneThick(494, 200 + 20, 1.57f),                         // Adding 20 to y
//
//                blockMaker.GlassShort(445, 200 + 20, 1.57f),                         // Adding 20 to y
//                blockMaker.GlassShort(550, 200 + 20, 1.57f),                         // Adding 20 to y
//                blockMaker.GlassShort(608, 241 + 20, 1.57f),                         // Adding 20 to y
//                blockMaker.GlassShort(678, 241 + 20, 1.57f),                         // Adding 20 to y
//
//                blockMaker.GlassLongest(346, 268 + 20, 0),                           // Adding 20 to y
//                blockMaker.StoneLongest(647, 268 + 20, 0),                           // Adding 20 to y
//
//                blockMaker.GlassLong(453, 225 + 20, 0),                              // Adding 20 to y
//                blockMaker.GlassLong(537, 225 + 20, 0),                              // Adding 20 to y
//                blockMaker.GlassLong(500, 225 + 20 + 20, 0),                          // Adding 20 to y
//
//                blockMaker.GlassShort(346, 279 + 20, 0),                             // Adding 20 to y
//                blockMaker.GlassShort(643, 276 + 20, 0),                             // Adding 20 to y
//
//                blockMaker.GlassLongest(497, 292 + 20, 1.57f),                       // Adding 20 to y
//                blockMaker.GlassShort(497, 348 + 20, 0),                             // Adding 20 to y
//                blockMaker.StoneCube(497, 375 + 20, 0)                               // Adding 20 to y
//        ));
//
//        // Pigs
//        pigs = new ArrayList<>(asList(
//                new MedPig(world, 347, 238 + 20),           // Adding 20 to y
//                new MedPig(world, 643, 238 + 20),           // Adding 20 to y
//                new SmallPig(world, 463, 188 + 15),         // Adding 20 to y
//                new SmallPig(world, 529, 188 + 15),         // Adding 20 to y
//                new LargePig(world, 498, 420 + 20)          // Adding 20 to y
//        ));
//
//        // Birds
//        birds = new ArrayList<>(asList(
//        ));
//
//        return new LevelScreen(world, game, batch, blocks, pigs, birds, new SlingShot(100, 100));
//    }

    public ArrayList<Block> level3Blocks(){
        // Blocks
        return new ArrayList<>(asList(
                // 1st tower
                blockMaker.GlassShort(327, 83 + 20, 1.57f),                  // Adding 20 to y
                blockMaker.GlassShort(327 + 20, 83 + 20, 1.57f),              // Adding 20 to y
                blockMaker.GlassShort(327 + 40, 83 + 20, 1.57f),              // Adding 20 to y
                blockMaker.GlassShort(327, 83 + 42 + 20, 1.57f),              // Adding 20 to y
                blockMaker.GlassShort(327 + 20, 83 + 42 + 20, 1.57f),          // Adding 20 to y
                blockMaker.GlassShort(327 + 40, 83 + 42 + 20, 1.57f),          // Adding 20 to y

                blockMaker.GlassSquare(327, 158 + 20, 0),                      // Adding 20 to y
                blockMaker.GlassSquare(327 + 20, 158 + 20, 0),                  // Adding 20 to y
                blockMaker.GlassSquare(327 + 40, 158 + 20, 0),                  // Adding 20 to y
                blockMaker.GlassSquare(327, 158 + 20 + 20, 0),                  // Adding 20 to y
                blockMaker.GlassSquare(327 + 20, 158 + 20 + 20, 0),              // Adding 20 to y
                blockMaker.GlassSquare(327 + 40, 158 + 20 + 20, 0),              // Adding 20 to y
                blockMaker.GlassSquare(327, 158 + 40 + 20, 0),                  // Adding 20 to y
                blockMaker.GlassSquare(327 + 20, 158 + 40 + 20, 0),              // Adding 20 to y
                blockMaker.GlassSquare(327 + 40, 158 + 40 + 20, 0),              // Adding 20 to y

                // 2nd tower
                blockMaker.GlassShort(327 + 100, 83 + 20, 1.57f),               // Adding 20 to y
                blockMaker.GlassShort(327 + 20 + 100, 83 + 20, 1.57f),           // Adding 20 to y
                blockMaker.GlassShort(327 + 40 + 100, 83 + 20, 1.57f),           // Adding 20 to y

                blockMaker.GlassSquare(327 + 100, 114 + 20, 0),                  // Adding 20 to y
                blockMaker.GlassSquare(327 + 20 + 100, 114 + 20, 0),              // Adding 20 to y
                blockMaker.GlassSquare(327 + 40 + 100, 114 + 20, 0),              // Adding 20 to y
                blockMaker.GlassSquare(327 + 100, 114 + 20 + 20, 0),              // Adding 20 to y
                blockMaker.GlassSquare(327 + 20 + 100, 114 + 20 + 20, 0),          // Adding 20 to y
                blockMaker.GlassSquare(327 + 40 + 100, 114 + 20 + 20, 0),          // Adding 20 to y
                blockMaker.GlassSquare(327 + 100, 114 + 40 + 20, 0),              // Adding 20 to y
                blockMaker.GlassSquare(327 + 20 + 100, 114 + 40 + 20, 0),          // Adding 20 to y
                blockMaker.GlassSquare(327 + 40 + 100, 114 + 40 + 20, 0),          // Adding 20 to y

                // 3rd tower
                blockMaker.GlassShort(327 + 100 + 100, 83 + 20, 1.57f),           // Adding 20 to y
                blockMaker.GlassShort(327 + 20 + 100 + 100, 83 + 20, 1.57f),       // Adding 20 to y
                blockMaker.GlassShort(327 + 40 + 100 + 100, 83 + 20, 1.57f),       // Adding 20 to y

                blockMaker.GlassSquare(327 + 100 + 100, 114 + 20, 0),              // Adding 20 to y
                blockMaker.GlassSquare(327 + 20 + 100 + 100, 114 + 20, 0),          // Adding 20 to y
                blockMaker.GlassSquare(327 + 40 + 100 + 100, 114 + 20, 0),          // Adding 20 to y
                blockMaker.GlassSquare(327 + 100 + 100, 114 + 20 + 20, 0),          // Adding 20 to y
                blockMaker.GlassSquare(327 + 20 + 100 + 100, 114 + 20 + 20, 0),      // Adding 20 to y
                blockMaker.GlassSquare(327 + 40 + 100 + 100, 114 + 20 + 20, 0),      // Adding 20 to y
                blockMaker.GlassSquare(327 + 100 + 100, 114 + 40 + 20, 0),          // Adding 20 to y
                blockMaker.GlassSquare(327 + 20 + 100 + 100, 114 + 40 + 20, 0),      // Adding 20 to y
                blockMaker.GlassSquare(327 + 40 + 100 + 100, 114 + 40 + 20, 0),      // Adding 20 to y

                // 4th tower
                blockMaker.Woodshort(327 + 300, 83 + 20, 1.57f),                   // Adding 20 to y
                blockMaker.Woodshort(327 + 20 + 300, 83 + 20, 1.57f),               // Adding 20 to y
                blockMaker.Woodshort(327 + 40 + 300, 83 + 20, 1.57f),               // Adding 20 to y
                blockMaker.Woodshort(327 + 300, 83 + 42 + 20, 1.57f),               // Adding 20 to y
                blockMaker.Woodshort(327 + 20 + 300, 83 + 42 + 20, 1.57f),           // Adding 20 to y
                blockMaker.Woodshort(327 + 40 + 300, 83 + 42 + 20, 1.57f),           // Adding 20 to y

                blockMaker.Woodsquare(327 + 300, 158 + 20, 0),                      // Adding 20 to y
                blockMaker.Woodsquare(327 + 20 + 300, 158 + 20, 0),                  // Adding 20 to y
                blockMaker.Woodsquare(327 + 40 + 300, 158 + 20, 0),                  // Adding 20 to y
                blockMaker.Woodsquare(327 + 300, 158 + 20 + 20, 0),                  // Adding 20 to y
                blockMaker.Woodsquare(327 + 20 + 300, 158 + 20 + 20, 0),              // Adding 20 to y
                blockMaker.Woodsquare(327 + 40 + 300, 158 + 20 + 20, 0),              // Adding 20 to y
                blockMaker.Woodsquare(327 + 300, 158 + 40 + 20, 0),                  // Adding 20 to y
                blockMaker.Woodsquare(327 + 20 + 300, 158 + 40 + 20, 0),              // Adding 20 to y
                blockMaker.Woodsquare(327 + 40 + 300, 158 + 40 + 20, 0),              // Adding 20 to y

                blockMaker.GlassLongest(347, 216 + 20, 0),                           // Adding 20 to y
                blockMaker.GlassLongest(444, 173 + 20, 0),                           // Adding 20 to y
                blockMaker.GlassLongest(444 + 100, 173 + 20, 0),                     // Adding 20 to y
                blockMaker.StoneLongest(347 + 300, 216 + 20, 0),                     // Adding 20 to y

                blockMaker.GlassShort(311, 243 + 20, 1.57f),                         // Adding 20 to y
                blockMaker.GlassShort(385, 243 + 20, 1.57f),                         // Adding 20 to y

                blockMaker.StoneThick(494, 200 + 20, 1.57f),                         // Adding 20 to y

                blockMaker.GlassShort(445, 200 + 20, 1.57f),                         // Adding 20 to y
                blockMaker.GlassShort(550, 200 + 20, 1.57f),                         // Adding 20 to y
                blockMaker.GlassShort(608, 241 + 20, 1.57f),                         // Adding 20 to y
                blockMaker.GlassShort(678, 241 + 20, 1.57f),                         // Adding 20 to y

                blockMaker.GlassLongest(346, 268 + 20, 0),                           // Adding 20 to y
                blockMaker.StoneLongest(647, 268 + 20, 0),                           // Adding 20 to y

                blockMaker.GlassLong(453, 225 + 20, 0),                              // Adding 20 to y
                blockMaker.GlassLong(537, 225 + 20, 0),                              // Adding 20 to y
                blockMaker.GlassLong(500, 225 + 20 + 20, 0),                          // Adding 20 to y

                blockMaker.GlassShort(346, 279 + 20, 0),                             // Adding 20 to y
                blockMaker.GlassShort(643, 276 + 20, 0),                             // Adding 20 to y

                blockMaker.GlassLongest(497, 292 + 20, 1.57f),                       // Adding 20 to y
                blockMaker.GlassShort(497, 348 + 20, 0),                             // Adding 20 to y
                blockMaker.StoneCube(497, 375 + 20, 0)                               // Adding 20 to y
        ));

    }

    public ArrayList<Pig> level3Pigs(){
        // Pigs
        return new ArrayList<>(asList(
                new MedPig(world, 347, 238 + 20),           // Adding 20 to y
                new MedPig(world, 643, 238 + 20),           // Adding 20 to y
                new SmallPig(world, 463, 188 + 15),         // Adding 20 to y
                new SmallPig(world, 529, 188 + 15),         // Adding 20 to y
                new LargePig(world, 498, 420 + 20)          // Adding 20 to y
        ));
    }

    public ArrayList<Bird> level3Birds(){
        // Birds
        return new ArrayList<>(asList(
                new BlackBird(world,80,110),
                new BlueBird(world,130,100),
                new BlackBird(world, 100, 110)
        ));
    }
}
