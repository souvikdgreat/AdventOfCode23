package problems;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ScratchCards {
    public static void main(String[] args) throws Exception {
        List<String> inputs = InputReader.getInputFromFiles("/Users/souvik.chakraborty1thoughtworks.com/Documents/AdventOfCode/src/input/scratchCards.txt");
        List<Card> cards = parseInput(inputs);
        int result = cards
                .stream()
                .map(ScratchCards::getIntersection)
                .map(it -> Math.pow(2, it - 1))
                .mapToInt(Double::intValue)
                .sum();
        System.out.println(result);
        System.out.println(cardsTotal(cards));
    }

    public static int cardsTotal(List<Card> cards) {
        Map<Integer, Integer> originalMap = new HashMap<>();
        Map<Integer, Integer> copyMap = new HashMap<>();

        for (Card card : cards) {
            int cardNumber = card.number;
            originalMap.put(cardNumber, 1);
            int copiesNumber = copyMap.get(cardNumber) != null ? copyMap.get(cardNumber) : 0;
            int intersection = getIntersection(card);
            int incrementValue = copiesNumber + originalMap.get(cardNumber);
            for (int j = cardNumber + 1; j <= cardNumber + intersection; j++) {
                if (copyMap.containsKey(j)) {
                    copyMap.put(j, copyMap.get(j) + incrementValue);
                } else {
                    copyMap.put(j, incrementValue);
                }
            }
        }

        return originalMap
                .entrySet()
                .stream()
                .map(entry -> {
                    if (copyMap.containsKey(entry.getKey())) {
                        return entry.getValue() + copyMap.get(entry.getKey());
                    } else return entry.getValue();
                })
                .mapToInt(Integer::intValue)
                .sum();

    }


    private static void putCardsInMap(Map<Integer, Integer> cardNumberWithCount, int index, int value) {
        if (cardNumberWithCount.containsKey(index)) {
            cardNumberWithCount.put(index, value);
        } else {
            cardNumberWithCount.put(index, value);
        }
    }

    public static int getIntersection(Card card) {
        Set<Integer> dealtHandTemp = new HashSet<>(card.dealtHand);
        Set<Integer> winningCardsTemp = new HashSet<>(card.winningCards);

        winningCardsTemp.removeAll(dealtHandTemp);
        return card.winningCards.size() - winningCardsTemp.size();
    }

    public static List<Card> parseInput(List<String> inputs) {
        List<Card> cards = new ArrayList<>();
        inputs
                .forEach(input -> {
                    String[] inputOutputString = input.split("\\|");
                    String[] inputStrings = inputOutputString[0].split(":");

                    String[] cardDetailsInfo = inputStrings[0].split(" ");
                    int cardNumber = Integer.parseInt(
                            cardDetailsInfo[cardDetailsInfo.length - 1]
                    );


                    List<Integer> dealtHand = Arrays.stream(inputStrings[1].split(" "))
                            .filter(Predicate.not(String::isBlank))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());

                    String outputString = inputOutputString[1];
                    List<Integer> winningCards = Arrays.stream(outputString.split(" "))
                            .filter(Predicate.not(String::isBlank))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());
                    Card card = new Card(dealtHand, winningCards, cardNumber);
                    cards.add(card);
                });
        return cards;
    }
}


class Card {
    Integer number;
    List<Integer> dealtHand;
    List<Integer> winningCards;

    public Card(List<Integer> dealtHand, List<Integer> winningCards, Integer number) {
        this.dealtHand = dealtHand;
        this.winningCards = winningCards;
        this.number = number;
    }
}