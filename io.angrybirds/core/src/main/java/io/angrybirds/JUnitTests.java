//package io.angrybirds;
//
//
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.World;
//import io.angrybirds.blocks.Block;
//
//import io.angrybirds.screens.LevelScreen;
//import io.angrybirds.utils.BlockMaker;
//
//import org.junit.Before;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
//
//public class JUnitTests {
//    private Main mockGame;
//    private SpriteBatch mockBatch;
//    private LevelScreen levelScreen;
//
//
//
//    @Test
////    public void testLevel1Initialization() {
////        // Test level 1 initialization
////        levelScreen = new LevelScreen(mockGame, mockBatch, 1);
////
////        // Verify basic initialization
////        assertNotNull(levelScreen);
////    }
////    @Test
////    public void testLevel2Initialization() {
////        // Test level 1 initialization
////        levelScreen = new LevelScreen(mockGame, mockBatch, 1);
////
////        // Verify basic initialization
////        assertNotNull(levelScreen);
////    }
////    @Test
////    public void testLevel3Initialization() {
////        // Test level 1 initialization
////        levelScreen = new LevelScreen(mockGame, mockBatch, 1);
////
////        // Verify basic initialization
////        assertNotNull(levelScreen);
////    }
//
//
////    @Test
////    void testHandleEndGame() {
////        levelScreen = new LevelScreen(mockGame, mockBatch, 1);
////
////        // Create mock lists with all elements destroyed
////        ArrayList<Pig> allDeadPigs = new ArrayList<>();
////        Pig mockPig = mock(Pig.class);
////        when(mockPig.isDestroyed()).thenReturn(true);
////        allDeadPigs.add(mockPig);
////
////        ArrayList<Bird> allDeadBirds = new ArrayList<>();
////        Bird mockBird = mock(Bird.class);
////        when(mockBird.isDead()).thenReturn(true);
////        allDeadBirds.add(mockBird);
////
////        // Use reflection or add a method to set these for testing
////        // levelScreen.setPigs(allDeadPigs);
////        // levelScreen.setBirds(allDeadBirds);
////
////        // Call method to check end game logic
////        // This would require making handleEndGame() method package-private or public for testing
////        // levelScreen.handleEndGame();
////
////        // Assert game over conditions
////        // assertTrue(levelScreen.isGameOver());
////        // assertTrue(levelScreen.isWon());
////    }
//    @Before
//    public void setUp() {}
//
//    @Test
//    public void damageBlock() {
//        levelScreen = new LevelScreen(mockGame, mockBatch, 1);
//        // Create mock blocks and pigs with known update() return values
//        BlockMaker bm=new BlockMaker(new World(new Vector2(0,9.8f), true));
//        Block mockBlock = bm.WoodCube(0,0,0) ;
//        int initialHealth= (int) mockBlock.getHealth();
//        mockBlock.takeDamage(10);
//        int finalHealth= (int) mockBlock.getHealth();
//
//        assertEquals(initialHealth-10,finalHealth);
//    }
//
////    @Test
////    public void testLevelProgression() {
////        // Test different level initializations
////        for (int level = 1; level <= 3; level++) {
////            levelScreen = new LevelScreen(mockGame, mockBatch, level);
////
////            // Verify that different levels are initialized correctly
////            assertNotNull(levelScreen);
////            // Add more specific assertions about level-specific elements
////        }
////    }
//
////    @Test
////    void testWinCondition() {
////        levelScreen = new LevelScreen(mockGame, mockBatch, 1);
////
////        // Simulate winning condition
////        // This might require modifying the method or adding a test-specific method
////        // levelScreen.simulateAllPigsDead();
////
////        // Call win method
////        levelScreen.win();
////
////        // Verify game state changes
////        // This would depend on how you want to track win state
////        // You might need to mock the EndScreen or add verification methods
////    }
//
////    @Test
////    void testGetStars() {
////        levelScreen = new LevelScreen(mockGame, mockBatch, 1);
////
////        // Test star calculation for different scores
////        // This will depend on your specific star calculation logic
////        int stars1 = levelScreen.getStars();
////
////        // Simulate different score scenarios
////        // levelScreen.setScore(highScore);
////        // int stars2 = levelScreen.getStars();
////
////        // Assertions would depend on your specific star calculation
////        // assertEquals(expectedStars, stars1);
////    }
//}