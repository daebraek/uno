import java.util.Objects;

public class Card {
    ColorEnum color;
    ValueEnum value;
    ColorEnum nextColor;
    boolean active = true;

    public Card(ColorEnum color, ValueEnum value) {
        this.color = color;
        this.value = value;
    }

    @Override
    public String toString() {
        return color+" "+value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Card other = (Card) obj;
        return color == other.color && value == other.value;
}

    @Override
    public int hashCode() {
        return Objects.hash(color, value);
    }

}
