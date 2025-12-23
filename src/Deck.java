import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    Deque<Card> deck = new ArrayDeque<>();

    public Deck() {
    }

    // test|prints out first 11 cards of deck
    public void testPrint() {
        Iterator<Card> it = deck.iterator();

        int count = 0;

        while(it.hasNext() && count<=10) {
            Card card = it.next();
            System.out.println(card.color + " " + card.value);

            count++;
        }

    }

    // creates the deck and adds all 112 cards
    public void initializeDeck() {
        for (ColorEnum color : ColorEnum.values()) {

            if (color!=ColorEnum.WILD) {
                for (ValueEnum value : ValueEnum.values()) {

                    if (value.equals(ValueEnum.ZERO)) {
                        deck.addFirst(new Card(color, value));
                    }

                    if (value.getHasColor() && !value.equals(ValueEnum.ZERO)) {
                        deck.addFirst(new Card(color, value));
                        deck.addFirst(new Card(color, value));
                    }

                }
            } else {
                for (ValueEnum value : ValueEnum.values()) {

                    if (value.equals(ValueEnum.SWAP_HANDS)) {
                        deck.addFirst(new Card(color, value));
                    }

                    else if (value.equals(ValueEnum.BLANK_CARD)) {
                        deck.addFirst(new Card(color, ValueEnum.BLANK_CARD));
                        deck.addFirst(new Card(color, ValueEnum.BLANK_CARD));
                        deck.addFirst(new Card(color, ValueEnum.BLANK_CARD));
                    }

                    else if (!value.getHasColor()) {
                        deck.addFirst(new Card(color, value));
                        deck.addFirst(new Card(color, value));
                        deck.addFirst(new Card(color, value));
                        deck.addFirst(new Card(color, value));
                    }

                }
            }
        }
     
    }

    // prints the number of coloured and wild cards
    public void printDeckCount() {
        int hasColorCount =0;
        int wildCount = 0;

        for (Card card : deck) {
            if (card.value.getHasColor()) {
                hasColorCount++;
            } else {
                wildCount++;
            }
        }

        System.out.println("Number of coloured cards: "+hasColorCount);
        System.out.println("Number of wildcards: "+wildCount);

    }
 
    // shuffles cards twice
    public void shuffle() {
        int shuffleCount = 0;
        
        while (shuffleCount<=2) {
            List<Card> temp = new ArrayList<>(deck);
            Collections.shuffle(temp);

            deck.clear();
            deck.addAll(temp);

            shuffleCount++;
        }

    }
    


}
