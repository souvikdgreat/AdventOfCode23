package problems.day7;

import problems.InputReader;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class GamePlay {
    static Map<HandType, Integer> handTypeRankings;
    static Map<Character, Integer> cardRankings;

    static {
        handTypeRankings = new HashMap<>();
        HandType[] handTypes = HandType.values();
        for (int i = 0; i < handTypes.length; i++) {
            handTypeRankings.put(handTypes[i], i);
        }

        cardRankings = new HashMap<>();
        cardRankings.put('A', 13);
        cardRankings.put('K', 12);
        cardRankings.put('Q', 11);
        cardRankings.put('T', 10);
        cardRankings.put('9', 9);
        cardRankings.put('8', 8);
        cardRankings.put('7', 7);
        cardRankings.put('6', 6);
        cardRankings.put('5', 5);
        cardRankings.put('4', 4);
        cardRankings.put('3', 3);
        cardRankings.put('2', 2);
        cardRankings.put('J', 1);
    }


    public static void main(String[] args) throws Exception {
        List<String> inputs = InputReader.getInputFromFiles("/Users/souvik.chakraborty1thoughtworks.com/Documents/AdventOfCode/src/input/camelCards.txt");

        List<Hand> hands = parseInput(inputs);

        Comparator<Hand> cardOrderComparator = (hand1, hand2) -> {
            char[] card1 = hand1.cardsDealt.toCharArray();
            char[] card2 = hand2.cardsDealt.toCharArray();
            int cardOrder = 0;
            for (int i = 0; i < card1.length; i++) {
                if (card1[i] != card2[i]) {
                    int card1Rank = cardRankings.get(card1[i]);
                    int card2Rank = cardRankings.get(card2[i]);
                    if (card1Rank > card2Rank) {
                        cardOrder = 1;
                    } else {
                        cardOrder = -1;
                    }
                    break;
                }
            }
            return cardOrder;
        };

        Comparator<Hand> handTypeComparator = (hand1, hand2) -> {
            HandType handType1 = hand1.getHandType();
            HandType handType2 = hand2.getHandType();
            if (handTypeRankings.get(handType1).equals(handTypeRankings.get(handType2))) {
                return 0;
            }

            if (handTypeRankings.get(handType1) > handTypeRankings.get(handType2)) {
                return 1;
            }
            return -1;
        };


        Comparator<Hand> handTypeAndThenCardOrderComparator = handTypeComparator
                .thenComparing(cardOrderComparator);

        List<Hand> result = hands
                .stream()
                .sorted(handTypeAndThenCardOrderComparator)
                .collect(Collectors.toList());

        System.out.println(result);

        BigInteger sum = BigInteger.ZERO;

        for (int i = 0; i < result.size(); i++) {
            BigInteger bigInteger = new BigInteger(result.get(i).bidAmount + "").multiply(new BigInteger((i + 1) + ""));
            sum = sum.add(bigInteger);
        }

        System.out.println(sum);
    }


    public static List<Hand> parseInput(List<String> inputs) {
        List<Hand> hands = new ArrayList<>();

        inputs.forEach(input -> {
            String[] parsedInput = input.split(" ");
            String cardsDealt = parsedInput[0];
            int biddingAmount = Integer.parseInt(parsedInput[1]);
            Hand hand = new Hand(cardsDealt, biddingAmount);
            hands.add(hand);
        });

        return hands;
    }
}

