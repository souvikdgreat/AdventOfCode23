package problems.camelCards;

import java.util.HashMap;
import java.util.Map;

public class HandTypeIdentifier {


    public static HandType getHandType(Hand hand) {
        String cardsDealt = hand.cardsDealt;
        Map<Character, Integer> cardCountMap = new HashMap<>();
        for (int i = 0; i < cardsDealt.length(); i++) {
            Character card = cardsDealt.charAt(i);
            if (cardCountMap.containsKey(card)) {
                cardCountMap.put(card, cardCountMap.get(card) + 1);
            } else {
                cardCountMap.put(card, 1);
            }
        }
        return getHandTypeByMap(cardCountMap);
    }

    public static HandType getHandTypeByMap(Map<Character, Integer> cardCountMap) {
        int highestCount = cardCountMap.entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(1)
                .map(Map.Entry::getValue)
                .findFirst().get();

        int secondHighestCount = cardCountMap.entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .skip(1)
                .limit(1)
                .map(Map.Entry::getValue)
                .findFirst().orElse(0);


        HandType handType;
        switch (highestCount) {
            case 5:
                handType = HandType.FIVE_OF_A_KIND;
                break;
            case 4:
                handType = HandType.FOUR_OF_A_KIND;
                break;
            case 3:
                if (secondHighestCount == 2) {
                    handType = HandType.FULL_HOUSE;
                } else {
                    handType = HandType.THREE_OF_A_KIND;
                }
                break;
            case 2:
                if (highestCount == secondHighestCount) {
                    handType = HandType.TWO_PAIR;
                } else {
                    handType = HandType.PAIR;
                }
                break;
            case 1:
                handType = HandType.HIGH_CARD;
                break;
            default:
                handType = HandType.UNKNOWN;
        }
        return handType;

    }

    public static void main(String[] args) {
        Hand hand = new Hand("77788", 765);
        System.out.println(getHandType(hand));
    }
}