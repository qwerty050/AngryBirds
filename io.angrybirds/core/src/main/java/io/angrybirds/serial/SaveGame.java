package io.angrybirds.serial;

import com.badlogic.gdx.physics.box2d.Body;
import io.angrybirds.SlingShot;
import io.angrybirds.birds.Bird;
import io.angrybirds.blocks.Block;
import io.angrybirds.pigs.Pig;


import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static io.angrybirds.Main.PPM;
import static java.time.format.DateTimeFormatter.*;

public class SaveGame {
    private ArrayList<Block> blocks;
    private ArrayList<Pig> pigs;
    private ArrayList<Bird> birds;
    private SlingShot slingshot;
    private String timestamp;
    private int level;
    // Constructor
    public SaveGame(ArrayList<Block> blocks, ArrayList<Pig> pigs, ArrayList<Bird> birds, SlingShot slingshot, int level) {
        this.blocks = blocks;
        this.pigs = pigs;
        this.birds = birds;
        this.slingshot = slingshot;
        this.level = level;

        long timestamp = System.currentTimeMillis();
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        DateTimeFormatter formatter = ofPattern("MM-dd_HH-mm-ss");

        this.timestamp = dateTime.format(formatter);
    }
    // Getters and Setters
    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }

    public ArrayList<Pig> getPigs() {
        return pigs;
    }

    public void setPigs(ArrayList<Pig> pigs) {
        this.pigs = pigs;
    }

    public ArrayList<Bird> getBirds() {
        return birds;
    }

    public void setBirds(ArrayList<Bird> birds) {
        this.birds = birds;
    }

    public SlingShot getSlingshot() {
        return slingshot;
    }

    public void setSlingshot(SlingShot slingshot) {
        this.slingshot = slingshot;
    }

    public void saveGame() throws IOException {
        // Ensure saves directory exists
        File savesDir = new File("saves");
        if (!savesDir.exists()) {
            savesDir.mkdirs();
        }

        // Generate filename with unique ID and timestamp
        String filename = String.format("saves/%s.csv", timestamp);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Write header
            writer.write("AngryBirdGameSave");
            writer.newLine();

            writer.write("Blocks:");
            for (Block block : blocks) {
                if (!block.isDestroyed()) writer.write(bodyInfo(block.getID(), block.getBody(), (int) (block.getHealth())));
            }

            writer.newLine();
            writer.write("Pigs:");
            for (Pig pig : pigs) {
                if (!pig.isDestroyed()) writer.write(bodyInfo(pig.getID(), pig.getBody(), pig.getHealth()));
            }

            writer.newLine();
            writer.write("Birds:");
            for (Bird bird : birds) {
                if (!bird.isDead()) writer.write(bodyInfo(bird.getID(), bird.getBody(), 0));
            }
            writer.newLine();
            writer.write(level);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }


    private String bodyInfo(int ID, Body body, int health) {
        return ID + ":" +                  // ID at index 0
                body.getPosition().x*PPM + ":" +  // X world coordinate
                body.getPosition().y*PPM + ":" +  // Y world coordinate
                body.getAngle() + ":" +     // Rotation at index 3
                health + ":" +              // Health at index 4
                body.getLinearVelocity().x + ":" +  // X velocity at index 5
                body.getLinearVelocity().y + ";";   // Y velocity at index 6
    }
}