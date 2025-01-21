
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class DeckTest {

    /**
     * Test that a Deck object is initialized with the correct deck number.
     */
    @Test
    public void testDeckInitialization() {
        Deck deck = new Deck(1);
        assertNotNull("Deck should be initialized", deck);
        assertEquals("Deck number should be 1", 1, deck.getDeckNumber());
    }

    /**
     * Test assigning a card to a specific position in the deck.
     */
    @Test
    public void testAssignCard() {
        Deck deck = new Deck(1);
        Card card = new Card(5);
        deck.assignCard(card, 0);
        assertEquals("First card in the deck should have value 5", 5, deck.getHand()[0].getValue());
    }

    /**
     * Test that picking up a card returns the correct card and shifts the deck.
     */
    @Test
    public void testPickUpCard() {
        Deck deck = new Deck(1);
        deck.assignCard(new Card(3), 0);
        deck.assignCard(new Card(5), 1);

        Card pickedUp = deck.pickUpCard();
        assertEquals("Picked up card should have value 3", 3, pickedUp.getValue());
        assertNull("The last card in the deck should now be null", deck.getHand()[4]);
    }

    /**
     * Test discarding a card adds it to the deck in the correct position.
     */
    @Test
    public void testDiscardCard() {
        Deck deck = new Deck(1);
        Card card = new Card(7);
        deck.discardCard(card);
        assertEquals("The discarded card should be placed in the correct position", 7, deck.getHand()[3].getValue());
    }

    /**
     * Test that writing the deck to a file creates the file and writes the correct contents.
     */
    @Test
    public void testWriteToFile() {
        Deck deck = new Deck(1);
        deck.assignCard(new Card(5), 0);
        deck.assignCard(new Card(7), 1);

        try {
            deck.writeToFile();
            File file = new File("gameOutput/deck1_output.txt");
            assertTrue("Output file should exist", file.exists());

            Scanner scanner = new Scanner(file);
            String content = scanner.nextLine();
            assertTrue("File content should contain the deck information", content.contains("deck1 contents: 5 7"));
            scanner.close();
        } catch (IOException e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }

    /**
     * Test that attempting to pick up a card from an empty deck throws an exception.
     */
    @Test(expected = IllegalStateException.class)
    public void testPickUpFromEmptyDeck() {
        Deck deck = new Deck(1);
        deck.pickUpCard();
    }
}
