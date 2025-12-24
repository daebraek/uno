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
                Card card = pile.getFirst();

                player.printHand();
                System.out.println("What card do you want to play? Type the color and value");
                String color = scanner.nextLine().toUpperCase();
                String value = scanner.nextLine().toUpperCase();

                

                


                }
            }
        }
    }

    public void stringToCard(String color, String value) {
       Card card = new Card(ColorEnum.valueOf(color.toUpperCase()), ValueEnum.valueOf(value.toUpperCase()))
    }


    // checks if the player's card is a valid move
    public boolean isMoveValid(Card playCard, Card oppCard) {
        ColorEnum playColor = playCard.color;
        ColorEnum oppColor = oppCard.color;

        ValueEnum playValue = playCard.value;
        ValueEnum oppValue = oppCard.value;

        if (playColor.equals(ColorEnum.WILD)) {
            return true;
        }

        else if (playColor.equals(oppColor) || playValue.equals(oppValue)) {
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
