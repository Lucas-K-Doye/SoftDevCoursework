import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Deck {
    private final int deckNumber;
    private int cards;
    private Card[] hand;

    public Deck(int deckNumber) {
        this.deckNumber = deckNumber;
        this.hand = new Card[5];
        this.cards = 0;
    }

    public int getDeckNumber() {
        return this.deckNumber;
    }
     

    public Card[] getHand() {
        return this.hand;
    }

    public String getHandValues() {
        String cards = "";
        for (Card card : this.hand) {
            if (card != null) { 
                cards = cards + card.getValue(); 
            }
        }
        return cards;
    }


    public void assignCard(Card card, int pos) {
        
        this.hand[pos] = card;

    }



    public Card pickUpCard() {
        // Return the card on the top of the deck
        Card onTopOfDeck = this.hand[0];
        Card[] newHand = new Card[5];
        System.arraycopy(this.hand, 1, newHand, 0, 4);
        this.hand = newHand;
        return onTopOfDeck;
    }





 
    public void discardCard(Card card) {
        // Is the array index empty (null) before adding card
        if (this.hand[3] == null) this.hand[3] = card;
        else this.hand[4] = card;
    }


 

    public void writeToFile() throws IOException {
        String path = "gameOutput" + File.separator + "deck" + deckNumber + "_output.txt";
        String output = "deck" + deckNumber + " contents: " + this.getHandValues();
    
        File f = new File(path);
        f.getParentFile().mkdirs(); // Ensure parent directories exist
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(f))) {
            writer.write(output);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            throw e; // Re-throw the exception for better debugging
        }
    }
    
   

}
    

