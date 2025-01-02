
import org.junit.Test;

import static org.junit.Assert.*;

public class CardTest {

    /**
     * Test that a Card object is created with the correct value.
     */
    @Test
    public void testCardCreation() {
        Card card = new Card(5);
        assertEquals("Card value should be 5", 5, card.getValue());
    }

    /**
     * Test that creating a Card with a negative value throws an IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeCardValue() {
        new Card(-1);
    }

    /**
     * Test that creating a Card with a zero value works correctly (assuming it's valid).
     */
    @Test
    public void testZeroCardValue() {
        Card card = new Card(0);
        assertEquals("Card value should be 0", 0, card.getValue());
    }

    /**
     * Test that two cards with the same value are considered equal (if equals is implemented).
     */
    @Test
    public void testEqualsMethod() {
        Card card1 = new Card(10);
        Card card2 = new Card(10);
        assertEquals("Cards with the same value should be equal", card1, card2);
    }

    /**
     * Test that the toString method provides a meaningful representation of the card.
     */
    @Test
    public void testToString() {
        Card card = new Card(7);
        assertEquals("Card toString should be 'Card{value=7}'", "Card{value=7}", card.toString());
    }
}
