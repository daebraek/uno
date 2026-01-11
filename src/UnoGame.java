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
            Card pileCard = pile.getFirst();

            if (pileCard.value == ValueEnum.BLOCK && pileCard.active) {
                System.out.print(player.name + ", you've been blocked. Press any key to continue. ");
                scanner.nextLine();

                advance();

                player = players.get(currentPlayer);

                pileCard.active = false;
                
            }
            
            System.out.println(player.name + "'s Turn");
            System.out.println();
            
            
            System.out.println("First Card in the Pile: " + pileCard);
            System.out.println();


            playRound(player, pileCard);
            

            if (checkWin(player)) {
                isGameOver = true;
            }


            if (pile.getFirst().value == ValueEnum.REVERSE && pile.getFirst().active) {
                System.out.println("The direction of play has been reversed.");
                direction = -1;

                pile.getFirst().active = false;
            }

            if (pile.getFirst().value == ValueEnum.CHANGE_COLOR && pile.getFirst().active) {
                System.out.print(player.name + ", pick a color: ");
                String color = scanner.nextLine();

                pile.getFirst().color = ColorEnum.valueOf(color.trim().toUpperCase());

                pile.getFirst().active = false;
            }

            if (pile.getFirst().value == ValueEnum.SWAP_HANDS && pile.getFirst().active) {
                printPlayers();
                System.out.print(player.name + ", pick a player number: ");

                int number = scanner.nextInt();
                scanner.nextLine();

                System.out.print("What color do you want this to be?: ");
                String color = scanner.nextLine();
                pile.getFirst().color = ColorEnum.valueOf(color.trim().toUpperCase());

                Player swappedPlayer = players.get(number-1);
                

                ArrayList<Card> temp1 = new ArrayList<>(swappedPlayer.getHand());
                ArrayList<Card> temp2 = new ArrayList<>(player.getHand());

                swappedPlayer.getHand().addAll(temp2);
                swappedPlayer.getHand().retainAll(temp2);

                player.getHand().addAll(temp1);
                player.getHand().retainAll(temp1);

                pile.getFirst().active = false;
                pile.getFirst().color = pile.getFirst().nextColor;
            }

            if (pile.getFirst().value == ValueEnum.BLANK_CARD && pile.getFirst().active) { 
                System.out.println("Here are your options.");
                System.out.println("1. Pick a number for a player to draw to until they find it.");
                System.out.println("2. Everyone (but you) picks up 5 cards");
                System.out.println("3. Pick a player to draw double the cards in their hand.");
                //System.out.println("4. Communism shuffle.");
                System.out.println();

                System.out.print("Pick a number to represent your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        printPlayers();
                        System.out.print("Pick a player number: ");
                        Player drawPlayer = players.get(scanner.nextInt() - 1);
                        scanner.nextLine();

                        System.out.println("In letters, what number should be drawn to: ");
                        String drawNumber = scanner.nextLine();

                        do {
                            drawCard(drawPlayer);
                        } while (drawPlayer.getHand().getLast().value != ValueEnum.valueOf(drawNumber.trim().toUpperCase()));


                        break;
                    case 2:
                        System.out.println("this is five players");
                        for (Player p : players) {
                            if (p != player) {
                                drawCard(p);
                                drawCard(p);
                                drawCard(p);
                                drawCard(p);
                                drawCard(p);
                            }
                            
                        }

                        break;

                    case 3: 
                        printPlayers();
                        System.out.print("Pick a player number: ");
                        Player p = players.get(scanner.nextInt() -1);
                        scanner.nextLine();  
                        
                        for (int i = 0; i<p.getHand().size()*2; i++) {
                            System.out.println("removing" + i);
                            drawCard(p);
                        }

                        break;

                    default:
                        break;
                }

                System.out.print("What color do you want this to be?: ");
                String color = scanner.nextLine();
                pile.getFirst().nextColor = ColorEnum.valueOf(color.trim().toUpperCase());

                pile.getFirst().active = false;

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



            if (!isDrawNeeded(player, pileCard) ) {
                System.out.println(player.name + ", what card do you want to play? Type the color and value.");
                String color = scanner.nextLine().toUpperCase();
                String value = scanner.nextLine().toUpperCase();

                
                Card playerCard = stringToCard(color, value);
                System.out.println(playerCard);

                if (playerCard.equals(new Card(ColorEnum.WILD, ValueEnum.PLUS_FOUR))) {
                    System.out.print("What color do you want this to be?: ");

                    String nextColor = scanner.nextLine();
                    
                    playerCard.nextColor = ColorEnum.valueOf(nextColor.trim().toUpperCase());

                } 

                // TODO: try out a try-catch approach with adding the card to the pile before asking for color change
                if (isMoveValid(playerCard, pileCard) && hand.contains(playerCard)) { 
                    hand.get(hand.indexOf(playerCard)).nextColor = playerCard.nextColor;
                    pile.addFirst(hand.remove(hand.indexOf(playerCard)));
                } else {
                    System.out.println("Card not allowed");
                } 

            } else if (isDrawNeeded(player, pileCard) && pileCard.equals(new Card(ColorEnum.WILD, ValueEnum.PLUS_FOUR))) {
                System.out.print(player.name + ", gotta pick up 4 cards, bud. Press any key to continue: ");
                scanner.nextLine();

                drawCard(player);
                drawCard(player);
                drawCard(player);
                drawCard(player);

                System.out.println(pileCard.nextColor);

                pileCard.color = pileCard.nextColor;
            } else if (isDrawNeeded(player, pileCard) && pileCard.value == ValueEnum.PLUS_TWO && pileCard.active) {
                System.out.print(player.name + ", gotta pick up 2 cards, bud. Press any key to continue: ");
                scanner.nextLine();

                drawCard(player);
                drawCard(player);

                pileCard.active = false;
            } else {
                System.out.print(player.name + ", gotta draw a card bud. Press any key to draw a card: ");
                scanner.nextLine();

                drawCard(player);
            }
    }
 
    
    public boolean isDrawNeeded(Player player, Card pileCard) {
        ArrayList<Card> hand = player.getHand();

        if (pileCard.equals(new Card(ColorEnum.WILD, ValueEnum.PLUS_FOUR)) && !player.getHand().contains(pileCard)) {
            return true;
        } else if (pileCard.value == ValueEnum.PLUS_TWO && pileCard.active) {
            for (Card card : hand) {
                if (card.value == ValueEnum.PLUS_TWO) {
                    return false;
                }
            }
        } else {
            for (Card card : hand) {
                if(card.color == ColorEnum.WILD) {
                    return false;
                }

                else if (card.color == pileCard.color || card.value == pileCard.value) {
                    return false;
                }
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
    // TODO: implement Lanterna for optimal terminal gameplay


}

