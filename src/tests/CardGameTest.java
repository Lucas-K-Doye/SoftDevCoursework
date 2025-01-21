
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CardGameTest {

    private Player[] players;
    private Deck[] decks;
    private File packFile;

    /**
     * Set up common resources for tests.
     */
    @Before
    public void setUp() throws IOException {
        // Create a temporary pack file with card values
        packFile = new File("testPack.txt");
        try (FileWriter writer = new FileWriter(packFile)) {
            for (int i = 1; i <= 20; i++) {
                writer.write(i + "\n");
            }
        }

        // Create players and decks
        int n = 4;
        players = new Player[n];
        decks = new Deck[n];
        for (int i = 0; i < n; i++) {
            players[i] = new Player(i + 1);
            decks[i] = new Deck(i + 1);
        }
    }

    /**
     * Test that cards are assigned correctly to players and decks.
     */
    @Test
    public void testAssignCards() {
        CardGame.assignCards(4, packFile, decks, players);

        // Validate players received correct cards
        for (int i = 0; i < players.length; i++) {
            Card[] hand = players[i].getHand();
            assertNotNull("Player's hand should not be null", hand);
            assertEquals("Player should have 4 cards", 4, hand.length);
            assertNotNull("Player's cards should not be null", hand[0]);
        }

        // Validate decks received correct cards
        for (int i = 0; i < decks.length; i++) {
            Card[] deckHand = decks[i].getHand();
            assertNotNull("Deck's hand should not be null", deckHand);
            assertEquals("Deck should have 4 cards", 4, deckHand.length);
            assertNotNull("Deck's cards should not be null", deckHand[0]);
        }
    }

    /**
     * Test the main game engine flow.
     */
    @Test
    public void testGameEngine() {
        CardGame.assignCards(4, packFile, decks, players);

        // Mock a player winning after one turn
        players[0].assignCard(new Card(1), 0);
        players[0].assignCard(new Card(1), 1);
        players[0].assignCard(new Card(1), 2);
        players[0].assignCard(new Card(1), 3);

        CardGame.gameEngine(4, packFile, players, decks);

        // Validate game ends with a winner
        for (Player player : players) {
            if (player.hasWon()) {
                assertTrue("At least one player should win the game", player.hasWon());
                return; // Test passed if a winner is found
            }
        }

        fail("No player won the game, but a winner was expected.");
    }

    /**
     * Test player input validation.
     */
    @Test
    public void testPlayerChecker() {
        Scanner scanner = new Scanner("4\n");
        int playersCount = CardGame.playerChecker(scanner);
        assertEquals("Should return the correct number of players", 4, playersCount);
    }

    /**
     * Test pack file validation.
     */
    @Test
    public void testFileChecker() {
        Scanner scanner = new Scanner(packFile.getAbsolutePath() + "\n");
        String filePath = CardGame.fileChecker(scanner);
        assertEquals("Should return the correct file path", packFile.getAbsolutePath(), filePath);
    }

    /**
     * Cleanup after tests.
     */
    @Test
    public void tearDown() {
        if (packFile.exists()) {
            assertTrue("Temporary pack file should be deleted", packFile.delete());
        }
    }
}

