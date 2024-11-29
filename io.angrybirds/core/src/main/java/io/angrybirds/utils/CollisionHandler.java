package io.angrybirds.utils;

import com.badlogic.gdx.physics.box2d.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CollisionHandler implements ContactListener {
    private Set<CollisionPair> processedCollisions = new HashSet<>();
    private float minTimeBetweenDamage = 0.5f;
    private float accumulator = 0;
    private HashMap<String, Float> damageFactors;

    public CollisionHandler() {
        damageFactors = new HashMap<>();
        initializeDamageFactors();
    }

    private void initializeDamageFactors() {
        // Bird collisions
        damageFactors.put("Bird-Block", 3f);    // Birds do more damage to blocks
        damageFactors.put("Bird-Pig", 1.7f);      // Birds do even more damage to pigs
        damageFactors.put("Bird-Platform", 1f);  // Birds take more damage from platforms

        // Block collisions
        damageFactors.put("Block-Block", 0.1f);    // Normal damage between blocks
        damageFactors.put("Block-Pig", 0.5f);      // Blocks do moderate damage to pigs
        damageFactors.put("Block-Platform", 0.3f); // Blocks take less damage from platforms

        // Pig collisions
        damageFactors.put("Pig-Pig", 1f);       // Pigs do less damage to each other
        damageFactors.put("Pig-Platform", 0.8f);   // Pigs take less damage from platforms
    }

    private float getDamageFactor(Object objectA, Object objectB) {
        if (!(objectA instanceof Damageable) || !(objectB instanceof Damageable)) {
            return 0.2f; // Default factor for objects that don't implement CollisionType
        }

        String nameA = ((Damageable)objectA).getCollisionName();
        String nameB = ((Damageable)objectB).getCollisionName();

        // Always put names in alphabetical order for consistent lookup
        String key = nameA.compareTo(nameB) <= 0 ?
            nameA + "-" + nameB :
            nameB + "-" + nameA;

        Float factor = damageFactors.get(key);
        if (factor == null) {
            System.out.println("Warning: No damage factor defined for collision: " + key);
            return 1.0f; // Default factor if not specified
        }
        return factor;
    }

    private static class CollisionPair {
        private final Body bodyA;
        private final Body bodyB;
        private float lastDamageTime;

        public CollisionPair(Body bodyA, Body bodyB) {
            if (bodyA.hashCode() < bodyB.hashCode()) {
                this.bodyA = bodyA;
                this.bodyB = bodyB;
            } else {
                this.bodyA = bodyB;
                this.bodyB = bodyA;
            }
            this.lastDamageTime = 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CollisionPair)) return false;
            CollisionPair that = (CollisionPair) o;
            return (bodyA == that.bodyA && bodyB == that.bodyB) ||
                (bodyA == that.bodyB && bodyB == that.bodyA);
        }

        @Override
        public int hashCode() {
            return bodyA.hashCode() + bodyB.hashCode();
        }
    }

    public void update(float deltaTime) {
        accumulator += deltaTime;
        if (accumulator >= minTimeBetweenDamage) {
            processedCollisions.clear();
            accumulator = 0;
        }
    }

    @Override
    public void beginContact(Contact contact) {
//        System.out.println("Collision began");
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        CollisionPair pair = new CollisionPair(bodyA, bodyB);

        if (processedCollisions.add(pair)) {
            Object userDataA = bodyA.getUserData();
            Object userDataB = bodyB.getUserData();

            if (userDataA == null || userDataB == null) {
                return;
            }

            float damageFactor = getDamageFactor(userDataA, userDataB);

            float maxImpulse = 0;
            for (float imp : impulse.getNormalImpulses()) {
                maxImpulse = Math.max(maxImpulse, imp);
            }
            int damage = (int)(maxImpulse * damageFactor*2f);

//            System.out.println("Collision between " + userDataA.getClass().getSimpleName() +
//                " and " + userDataB.getClass().getSimpleName() +
//                " (Factor: " + damageFactor + ")");

            if (userDataA instanceof Damageable) {
//                System.out.println("Applying damage to " + userDataA.getClass().getSimpleName() +
//                    ": " + damage);
                ((Damageable)userDataA).takeDamage(damage);
            }

            if (userDataB instanceof Damageable) {
//                System.out.println("Applying damage to " + userDataB.getClass().getSimpleName() +
//                    ": " + damage);
                ((Damageable)userDataB).takeDamage(damage);
            }
        }
    }

    // Method to allow modification of damage factors at runtime
    public void setDamageFactor(String objectTypeA, String objectTypeB, float factor) {
        String key = objectTypeA.compareTo(objectTypeB) <= 0 ?
            objectTypeA + "-" + objectTypeB :
            objectTypeB + "-" + objectTypeA;
        damageFactors.put(key, factor);
    }

    public interface Damageable {
        void takeDamage(int damage);
        String getCollisionName();
    }
}
