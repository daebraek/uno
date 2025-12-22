import java.util.ArrayList;
public class Player {
    public String name;
    private ArrayList<Card> hand = new ArrayList<>();

    public Player(String name, ArrayList<Card> hand) {
        this.name = name;
        this.hand = hand;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

}
