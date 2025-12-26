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


/*         for (Player player : game.players) {
            player.printHand();
        } */

        //game.deckHandler.printDeckCount();

        game.playGame();

    }


    public void startGame() {
    }

    // main game logic
    public void playGame() {
        boolean isGameOver = false;

        do {
            pile.addFirst(deck.pop());
        } while (pile.getFirst().color==ColorEnum.WILD);



        while (!isGameOver) {
            
            for (Player player : players) {
                System.out.println(player.name+"'s Turn");
                System.out.println();
                

                Card pileCard = pile.getFirst();
                
                System.out.println("First Card in the Pile: "+pileCard);
                System.out.println();

                player.printHand();
                ArrayList<Card> hand = player.getHand();
                
                System.out.println(player.name+", what card do you want to play? Type the color and value.");
                String color = scanner.nextLine().toUpperCase();
                String value = scanner.nextLine().toUpperCase();

                Card playerCard = stringToCard(color, value);
                System.out.println(playerCard);
                System.out.println(hand.contains(playerCard));

                if (hand.contains(playerCard)) {       
                    pile.addFirst(hand.remove(hand.indexOf(playerCard)));
                } else {
                    System.out.println("Card not allowed");
                } 

                if (player.getHand().size() == 0) {
                    System.out.println(player.name + " wins the game!!");
                    isGameOver = true;
                    break;
                }

                

            }

        }



    }
    

    public Card stringToCard(String color, String value) {
       return new Card(ColorEnum.valueOf(color.trim().toUpperCase()), ValueEnum.valueOf(value.trim().toUpperCase()));
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

        while(dealCount<=3) {
            for (Player player : players) {
                player.getHand().add(deck.pop());
            }
            dealCount++;
        }
    }

    // prompts user to configure players and updates player array 
    public void initializePlayers() {
        System.out.println("Player[s], welcome to Uno 🎲");

        System.out.print("How many players for this game🧍?: ");
        
        int playersCount = scanner.nextInt();
        scanner.nextLine();

        int count = 1;

        while (count<=playersCount) {
            System.out.print("Player " + count + ", what is your name?: ");
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

    public void printPile() {
        for (Card card : pile) {
            System.out.println(card);
        }
    }





}

