
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;




public class CardGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = playerChecker(scanner);
        String filePath = fileChecker(scanner);
        File pack = packLoader(filePath);
        System.out.println("Thank you proceed to program");
        Player[] players = new Player[n];
        for (int i = 0; i < n; i++) {
            players[i] = new Player(i + 1); 
        }
        Deck[] decks = new Deck[n];
        for (int i = 0; i < n; i++) {
            decks[i] = new Deck(i + 1); 
        }
        assignCards(n, pack, decks, players);
        gameEngine(n, pack, players, decks);



    }
    public static void gameEngine(int n, File pack, Player[] players, Deck[] decks) {
        for (Player player : players) {
            player.start();
        }

        boolean isGameWon = false;

        for (Player player : players) {
            if (player.hasWon()) {
                System.out.println("Player" + player.getNumber() + "has won the game.");
            }
        }

        int winner = -1;
        int turns = 0;
        while (!isGameWon) {
            int playerNumber = turns++ % n;
            int discardToDeck = (playerNumber + 1) % n;
            int drawFromDeck = playerNumber;

            synchronized (players[playerNumber]) {
                decks[discardToDeck].discardCard(
                        players[playerNumber].takeTurn(
                                decks[drawFromDeck].pickUpCard(), discardToDeck, drawFromDeck));
            }
            if (players[playerNumber].hasWon()) {
                isGameWon = true;
                winner = players[playerNumber].getNumber();
            }
        }
        for (Player player : players) {
            synchronized (player) {
                player.playerHasWon(winner);
            }

            try {
               decks[player.getNumber() - 1].writeToFile();
            } catch (IOException e) {
                System.err.println("Error handling deck file write: " + e.getMessage());
                e.printStackTrace();
            } 
     

        }

    }


    public static int playerChecker(Scanner scanner) {
        int players;
        while (true) {
            System.out.println("Enter the number of players");
            try {
                players = Integer.parseInt(scanner.nextLine());
                if (players < 1) {
                    System.out.println("Invalid input");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
                continue;
            }
            return players;
        }
    }


    public static String fileChecker(Scanner scanner) {
        String filePath;
        while (true) {
            System.out.println("Please enter the location of the pack file: ");
            filePath = scanner.nextLine();
            File file = new File(filePath);

            if (!file.exists()) {
                System.out.println("Error: File does not exist. Please try again.");
                continue;
            }
            if (!file.canRead()) {
                System.out.println("Error: File cannot be read. Please check permissions.");
                continue;
            }

            try (Scanner fileScanner = new Scanner(file)) {
                boolean valid = true;
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    if (!line.matches("\\d+")) { 
                        System.out.println("Error: File contains invalid data. Only integers are allowed.");
                        valid = false;
                        break;
                    }
                }
                if (!valid) continue;
            } catch (FileNotFoundException e) {
                System.out.println("Error: Unexpected issue opening the file. Please try again.");
                continue;
            }

            break;
        }
        return filePath;
    }
    
    public static File packLoader(String filePath) {
        File pack = new File(filePath);
        if (pack.exists() && pack.canRead() && pack.isFile()) {
            return pack;
        } else {
            System.out.println("Please enter a valid file path.");
            return null;
        }
    }
 

    public static void assignCards(int n, File pack, Deck[] decks, Player[] players) {
        try {
            Scanner packScanner = new Scanner(pack);
    
            // Distribute cards to players in a round-robin fashion
            for (int i = 0; i < 4; i++) { // 4 cards per player
                for (int j = 0; j < n; j++) { // n players
                    if (packScanner.hasNextLine()) {
                        int cardValue = Integer.parseInt(packScanner.nextLine());
                        players[j].assignCard(new Card(cardValue), i); // Assign card to player
                    } else {
                        throw new IllegalArgumentException("Pack file does not contain enough cards for players.");
                    }
                }
            }
    
            // Distribute cards to decks in a round-robin fashion
            for (int i = 0; i < 4; i++) { // 4 cards per deck
                for (int j = 0; j < n; j++) { // n decks
                    if (packScanner.hasNextLine()) {
                        int cardValue = Integer.parseInt(packScanner.nextLine());
                        decks[j].assignCard(new Card(cardValue), i); // Assign card to deck
                    } else {
                        throw new IllegalArgumentException("Pack file does not contain enough cards for decks.");
                    }
                }
            }
    
            packScanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Pack file not found: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    



}

