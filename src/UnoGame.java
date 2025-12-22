import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Scanner;

public class UnoGame {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {


        Deck deckHandler = new Deck();
        Deque<Card> deck = deckHandler.deck;

        // Deque<Card> pile = new ArrayDeque<>();

        deckHandler.initializeDeck();
        deckHandler.printDeckCount();


        startGame();

        scanner.close();



    }


}
