package io.angrybirds.serial;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import io.angrybirds.SlingShot;
import io.angrybirds.birds.Bird;
import io.angrybirds.birds.BlackBird;
import io.angrybirds.birds.BlueBird;
import io.angrybirds.birds.RedBird;
import io.angrybirds.blocks.Block;
import io.angrybirds.pigs.LargePig;
import io.angrybirds.pigs.MedPig;
import io.angrybirds.pigs.Pig;
import io.angrybirds.pigs.SmallPig;
import io.angrybirds.utils.BlockMaker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static io.angrybirds.Main.PPM;

public class LoadGame {
    private ArrayList<Block> loadedBlocks;
    private ArrayList<Pig> loadedPigs;
    private ArrayList<Bird> loadedBirds;
    private SlingShot slingShot;
    private String filename;
    private World world;
    private int level;

    public LoadGame(World world, String filename) {
        this.world = world;
        this.filename = filename;
        loadedBirds=new ArrayList<>();
        loadedPigs=new ArrayList<>();
        loadedBlocks=new ArrayList<>();
        this.level=1;
        slingShot=null;
    }

    public void loadGame() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            // Skip header
            String line = reader.readLine();

            // Read blocks
            line = reader.readLine();
            if (line != null && line.startsWith("Blocks:")) {
                System.out.println(line);
                String[] bodyinfo = line.substring(7).split(";");
                System.out.println("in load game");
                System.out.println(bodyinfo.length);
                for (String s : bodyinfo) {
                    BlockMaker bm = new BlockMaker(world);
                    if (!s.isEmpty()) {
                        String[] bodylist = s.split(":");
                        if (bodylist.length >= 7) {  // Ensure we have enough elements
                            loadedBlocks.add(bm.makeBlockbyID(
                                    Integer.parseInt(bodylist[0]),  // ID
                                    Float.parseFloat(bodylist[1]),  // X world coordinate
                                    Float.parseFloat(bodylist[2]),  // Y world coordinate
                                    Float.parseFloat(bodylist[3]),  // Rotation
                                    new Vector2(Float.parseFloat(bodylist[5]), Float.parseFloat(bodylist[6])),  // Velocity
                                    Integer.parseInt(bodylist[4])  // Health
                            ));
                        }
                    }
                }
            }

            // Read pigs
            line = reader.readLine();
            if (line != null && line.startsWith("Pigs:")) {
                String[] bodyinfo = line.substring(5).split(";");
                for (String s : bodyinfo) {
                    if (!s.isEmpty()) {
                        String[] bodylist = s.split(":");
                        if (bodylist.length >= 7) {  // Ensure enough elements
                            Pig pig = null;
                            float x = Float.parseFloat(bodylist[1]);
                            float y = Float.parseFloat(bodylist[2]);

                            switch(Integer.parseInt(bodylist[0])){
                                case 21:
                                    pig = new SmallPig(world, (int)x, (int)y);
                                    break;
                                case 22:
                                    pig = new MedPig(world, (int)x, (int)y);
                                    break;
                                case 23:
                                    pig = new LargePig(world, (int)x, (int)y);
                                    break;
                                default:
                                    System.out.println("Unknown pig type: " + bodylist[0]);
                            }

                            if (pig != null) {
                                pig.getBody().setTransform(x/PPM, y/PPM, Float.parseFloat(bodylist[3]));
                                pig.setHealth(Integer.parseInt(bodylist[4]));
                                pig.getBody().setLinearVelocity(
                                        new Vector2(
                                                Float.parseFloat(bodylist[5]),
                                                Float.parseFloat(bodylist[6])
                                        )
                                );
                                loadedPigs.add(pig);
                            }
                        }
                    }
                }
            }

            // Read Birds
            line = reader.readLine();
            if (line != null && line.startsWith("Birds:")) {
                String[] bodyinfo = line.substring(6).split(";");
                for (String s : bodyinfo) {
                    if (!s.isEmpty()) {
                        String[] bodylist = s.split(":");
                        if (bodylist.length >= 7) {  // Ensure enough elements
                            Bird bird = null;
                            float x = Float.parseFloat(bodylist[1]);
                            float y = Float.parseFloat(bodylist[2]);

                            switch(Integer.parseInt(bodylist[0])){
                                case 1:
                                    bird = new RedBird(world, (int)x, (int)y);
                                    break;
                                case 2:
                                    bird = new BlueBird(world, (int)x, (int)y);
                                    break;
                                case 3:
                                    bird = new BlackBird(world, (int)x, (int)y);
                                    break;
                                default:
                                    System.out.println("Unknown bird type: " + bodylist[0]);
                            }

                            if (bird != null) {
                                bird.getBody().setTransform(x/PPM, y/PPM, Float.parseFloat(bodylist[3]));
                                bird.getBody().setLinearVelocity(
                                        new Vector2(
                                                Float.parseFloat(bodylist[5]),
                                                Float.parseFloat(bodylist[6])
                                        )
                                );
                                loadedBirds.add(bird);
                            }
                        }
                    }
                }
            }
            line=reader.readLine();
            if (line != null && line.startsWith("1") &&line.startsWith("2") &&line.startsWith("3")) {
                this.level=Integer.parseInt(line);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Block> getLoadedBlocks(){
        return loadedBlocks;
    }

    public ArrayList<Pig> getLoadedPigs(){
        return loadedPigs;
    }

    public ArrayList<Bird> getLoadedBirds(){
        return loadedBirds;
    }

    public SlingShot getSlingShot(){
        ArrayList<Bird> slingshotbirds= new ArrayList<>();

        for(Bird bird: loadedBirds){
            if(!(bird.getBody().getLinearVelocity().x>0)) {
                slingshotbirds.add(bird);
            }
            else bird.getBody().setActive(true);
        }
        slingShot=new SlingShot(100,160,slingshotbirds);

        return slingShot;
    }

    public int getLevel() {
        return level;
    }
}
