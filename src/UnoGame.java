import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Scanner;
import java.util.ArrayList;

public class UnoGame {

    private int currentPlayer = 0;
    private int direction = 1;
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

        firstCard();

        while (!isGameOver) {
            
            Player player = players.get(currentPlayer);
            
            System.out.println(player.name + "'s Turn");
            System.out.println();
            

            Card pileCard = pile.getFirst();
            
            System.out.println("First Card in the Pile: " + pileCard);
            System.out.println();

            if (pileCard.value==ValueEnum.PLUS_TWO && plusTwoCheck(player, pileCard)) {
                drawCard(player);
                drawCard(player);
            } else if (pileCard.value == ValueEnum.PLUS_FOUR) {
                drawCard(player);
                drawCard(player);
                drawCard(player);
                drawCard(player);
            } else {
                playRound(player, pileCard);
            }


            if (checkWin(player)) {
                isGameOver = true;
            }


            if (pile.getFirst().value == ValueEnum.BLOCK) {
                System.out.print(player.name + ", you've been blocked. Press any key to continue. ");
                scanner.nextLine();

                advance();
            }

            if (pile.getFirst().value == ValueEnum.REVERSE) {
                System.out.println("The direction of play has been reversed.");
                direction = -1;
            }

            if (pile.getFirst().value == ValueEnum.CHANGE_COLOR) {
                System.out.print(player.name = ", pick a color: ");
                String color = scanner.nextLine();

                pile.getFirst().color = ColorEnum.valueOf(color.trim().toUpperCase());
            }

            if (pile.getFirst().value == ValueEnum.SWAP_HANDS) {
                printPlayers();
                System.out.print(player.name + ", pick a player number: ");

                int number = scanner.nextInt();
                scanner.nextLine();

                Player swappedPlayer = players.get(number-1);
                

                ArrayList<Card> temp1 = new ArrayList<>(swappedPlayer.getHand());
                ArrayList<Card> temp2 = new ArrayList<>(player.getHand());

                swappedPlayer.getHand().addAll(temp2);
                swappedPlayer.getHand().retainAll(temp2);

                player.getHand().addAll(temp1);
                player.getHand().retainAll(temp1);

            
            }

            if (pile.getFirst().value == ValueEnum.BLANK_CARD) {
                System.out.println("Here are your options.");
                System.out.println("1. Pick a number for a player to draw to until they find it.");
                System.out.println("2. Everyone picks up 5 cards");
                System.out.println("3. Pick a player to draw double the cards in their hand.");
                System.out.println("4. Communism shuffle.");
                System.out.println();

                System.out.print("Pick a number to represent your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        
                        break;
                
                    default:
                        break;
                }

            }


            advance();


        }
    }

    public void advance() {
        currentPlayer = (currentPlayer + direction + players.size()) % players.size();
    }

    public void ifCardSpecial(Card pileCard, Player player) {

    }

    public void firstCard() {
        do {
            pile.addFirst(deck.pop());
        } while (pile.getFirst().color==ColorEnum.WILD);
    }

    public void playRound(Player player, Card pileCard) {
            player.printHand();
            ArrayList<Card> hand = player.getHand();

            if (!isDrawNeeded(player, pileCard)) {
                System.out.println(player.name + ", what card do you want to play? Type the color and value.");
                String color = scanner.nextLine().toUpperCase();
                String value = scanner.nextLine().toUpperCase();



                Card playerCard = stringToCard(color, value);
                System.out.println(playerCard);

                if (isMoveValid(playerCard, pileCard) && hand.contains(playerCard)) {       
                    pile.addFirst(hand.remove(hand.indexOf(playerCard)));
                } else {
                    System.out.println("Card not allowed");
                } 


            } else {
                System.out.print("You can't play this round. You need to draw. Press any key to draw");
                scanner.nextLine();
                drawCard(player);
            }
    }

    public boolean plusTwoCheck(Player player, Card pileCard) {
        ArrayList<Card> hand = player.getHand();

        for (Card card : hand) {
            if(card.value == ValueEnum.PLUS_TWO) {
                return false;
            }
        }

        return true;

    } 
    
    public boolean isDrawNeeded(Player player, Card pileCard) {
        ArrayList<Card> hand = player.getHand();

        for (Card card : hand) {
            if(card.color == ColorEnum.WILD) {
                return false;
            }

            else if (card.color == pileCard.color || card.value == pileCard.value) {
                return false;
            }
        }

        return true;

    }

    // returns boolean based on whether a player has won
    public boolean checkWin(Player player) {
        if (player.getHand().size() == 0) {
            System.out.println(player.name + " wins the game!!");
            return true;
        } else {
            return false;
        }
    }
    
    // draws card
    public void drawCard(Player player) {
        player.getHand().add(deck.removeFirst());
    }
    
    // returns a new Card object from user input
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


    // TODO: add auto play
    // TODO: add special card operations


}

