import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Scanner;
import java.util.ArrayList;

public class UnoGame {

    private Scanner scanner = new Scanner(System.in);
    private ArrayList<Player> players = new ArrayList<>();
    private Deck deckHandler = new Deck();
    private Deque<Card> deck = deckHandler.deck;
    private Deque<Card> pile = new ArrayDeque<>();


    public static void main(String[] args) throws Exception {

        UnoGame game = new UnoGame();

        game.initializePlayers();
        game.dealCards();


        for (Player player : game.players) {
            player.printHand();
        }

        game.deckHandler.printDeckCount();

    }


    public void startGame() {
    }

    // main game logic
    public void playGame() {
        boolean isGameOver = false;

        pile.add(deck.pop());

        while (!isGameOver) {

            for (Player player : players) {
                Card pileCard = pile.getFirst();

                player.printHand();
                ArrayList<Card> hand = player.getHand();
                
                System.out.println("What card do you want to play? Type the color and value");
                String color = scanner.nextLine().toUpperCase();
                String value = scanner.nextLine().toUpperCase();

                Card playerCard = stringToCard(color, value);

                if (isMoveValid(playerCard, pileCard) && hand.contains(playerCard)) {       
                    pile.add(hand.remove(hand.indexOf(playerCard)));
                }  

            }
        }
    }
    

    public Card stringToCard(String color, String value) {
       return new Card(ColorEnum.valueOf(color.toUpperCase()), ValueEnum.valueOf(value.toUpperCase()));
    }


    // returns true if the player's card is a valid move
    public boolean isMoveValid(Card playCard, Card pileCard) {
        ColorEnum playColor = playCard.color;
        ColorEnum pileColor = pileCard.color;

        ValueEnum playValue = playCard.value;
        ValueEnum pileValue = pileCard.value;

        if (playColor == ColorEnum.WILD) {
            return true;
        }

        else if (playColor == pileColor || playValue == pileValue) {
            return true;
        }

        return false;
    }

    // shuffles deck and deals cards to players
    public void dealCards() {
        deckHandler.initializeDeck();
        deckHandler.shuffle();
        
        int dealCount = 1;

        while(dealCount<=7) {
            for (Player player : players) {
                player.getHand().add(deck.pop());
            }
            dealCount++;
        }
    }

    // prompts user to configure players and updates player array 
    public void initializePlayers() {
        System.out.println("Player[s], welcome to Uno 🎲");

        System.out.println("How many players for this game🧍?");
        
        int playersCount = scanner.nextInt();
        scanner.nextLine();

        int count = 1;

        while (count<=playersCount) {
            System.out.println("Player " + count + ", what is your name?");
            String playerName = scanner.nextLine();

            players.add(new Player(playerName));

            count++;
        }
    }

    // prints the players' names alongside their number
    public void printPlayers() {
        
        for (Player player : players) {
            int playerNumber = players.indexOf(player) + 1;
            System.out.println("Player " + playerNumber + ": " + player.name);
        }
    }
}
