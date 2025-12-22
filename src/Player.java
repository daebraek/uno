import java.util.ArrayList;
public class Player {
    public String name;
    private ArrayList<Card> hand;

    public Player(String name) {
        this.name = name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

}
