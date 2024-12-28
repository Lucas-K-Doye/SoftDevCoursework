import java.util.List;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends Thread {

    private final Card[] hand;
    private final int playerNumber;
    private int cards;
    private final String path;
    
    public Player(int playerNumber) {
        this.hand = new Card[5];
        this.cards = 0;
        this.playerNumber = playerNumber;
        this.path = "gameOutput" + File.separator + "player" + this.playerNumber + ".txt";
        try {
            File f = new File(this.path);
            f.getParentFile().mkdirs();
            if (!f.createNewFile()) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(path));
                writer.write("");
            }
        } catch (IOException e) {
            System.out.printf("Failed to create player output file for player %d", playerNumber);
        }
    }

    public int getNumber() {
        return playerNumber;
    }

    public void assignCard(Card card, int pos) {
        this.hand[pos] = card;
    }

    public String getCurrentHand() {
        return this.hand[0].getValue() + " " + this.hand[1].getValue()
             + " " + this.hand[2].getValue() + " " + this.hand[3].getValue();
    }

    public boolean hasWon() {
        Card firstCard = this.hand[0];
        int counter = 0;
        for (Card card :
                this.hand) {
            if (counter++ == 4) break;
            if (card.getValue() != firstCard.getValue()) return false;
        }
        System.out.printf("Player %d has won%n", playerNumber);
        return true;
    }

   public Card takeTurn(Card pickUp, int discardDeckNumber, int pickUpDeckNumber) {
        boolean isPreferred = true;
        Random picker = new Random();
        Card currentCard;
        short swapIndex;
        do {
            swapIndex = (short) picker.nextInt(4);
            currentCard = this.hand[swapIndex];
            if (currentCard.getValue() != playerNumber) isPreferred = false;
        } while (isPreferred);

        // Increment deck number for text file.
        pickUpDeckNumber++;
        discardDeckNumber++;

        this.hand[swapIndex] = pickUp; // Add the picked up card to hand.
        if (pickUp == null) throw new AssertionError("Picked up card is null");

        String currentHand = getCurrentHand();

        writeToPlayerFile("Player " + this.playerNumber + " draws " + pickUp.getValue() + " from deck " + pickUpDeckNumber);
        writeToPlayerFile("Player " + this.playerNumber + " discards " + currentCard.getValue() + " to deck " + discardDeckNumber);
        writeToPlayerFile("Player " + this.playerNumber + " current hand " + currentHand);

        return currentCard;
    }

    public void playerHasWon(int playerNumber) {
        StringBuilder winOutput = new StringBuilder();
        // check if player number is self
        if (playerNumber == this.playerNumber) {
            winOutput.append("player ").append(playerNumber).append(" wins");
        } else {
            winOutput.append("player ").append(playerNumber).append(" has informed player ")
                    .append(this.playerNumber).append(" that player ").append(playerNumber).append(" has won");
        }
        writeToPlayerFile(winOutput.toString());
        writeToPlayerFile("player " + this.playerNumber + " exits");

        StringBuilder handOutput = new StringBuilder("player ").append(this.playerNumber).append(' ');
        if (playerNumber == this.playerNumber) {
            handOutput.append("final ");
        }
        String currentHand = getCurrentHand();;
        handOutput.append("hand ").append(currentHand);
        writeToPlayerFile(handOutput.toString());
    }


   private void writeToPlayerFile(String output) {
        // open, write to, and close players file
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            writer.write(output);
            writer.newLine();
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.printf("Could not find the file for player %d at %s%n", playerNumber, path);
        } catch (IOException e) {
            System.out.printf("Error writing to player%d file (%s)%n", playerNumber, path);
        }
    }
}
    


  









