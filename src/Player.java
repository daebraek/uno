import java.util.ArrayList;
public class Player {
    public String name;
    private ArrayList<Card> hand = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void printHand() {
        System.out.println(name + "'s hand:");
        for (Card card : hand) {
            System.out.println(card.color + " " + card.value);
        }
        System.out.println();
    }

}
