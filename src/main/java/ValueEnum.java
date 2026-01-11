public enum ValueEnum {
    ZERO(true), ONE(true), TWO(true), THREE(true), FOUR(true), FIVE(true), SIX(true), SEVEN(true), EIGHT(true), NINE(true), REVERSE(true), PLUS_TWO(true), BLOCK(true), PLUS_FOUR(false), CHANGE_COLOR(false), SWAP_HANDS(false), BLANK_CARD(false);

    private boolean hasColor;

    ValueEnum(boolean hasColor) {
        this.hasColor = hasColor;
    }

   public boolean getHasColor() {
        return hasColor;
   }

    
}


