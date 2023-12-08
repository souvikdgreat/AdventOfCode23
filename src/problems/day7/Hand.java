package problems.day7;

public class Hand {
    String cardsDealt;
    long bidAmount;
    HandType handType;

    public Hand(String cardsDealt, long bidAmount) {
        this.cardsDealt = cardsDealt;
        this.bidAmount = bidAmount;
        this.handType = getHandType();

    }

    public HandType getHandType() {
        return HandTypeIdentifier.getHandType(this);
    }
}
