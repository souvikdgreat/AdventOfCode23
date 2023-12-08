package problems;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Trebuchet {
    static Map<String, Integer> wordMapping;

    static {
        wordMapping = Map.ofEntries(
                Map.entry("one", 1),
                Map.entry("two", 2),
                Map.entry("three", 3),
                Map.entry("four", 4),
                Map.entry("five", 5),
                Map.entry("six", 6),
                Map.entry("seven", 7),
                Map.entry("eight", 8),
                Map.entry("nine", 9),
                Map.entry("1", 1),
                Map.entry("2", 2),
                Map.entry("3", 3),
                Map.entry("4", 4),
                Map.entry("5", 5),
                Map.entry("6", 6),
                Map.entry("7", 7),
                Map.entry("8", 8),
                Map.entry("9", 9)

        );
    }


    public static int countOfOccurrences(String str, String subStr) {
        return (str.length() - str.replaceAll(Pattern.quote(subStr), "").length()) / subStr.length();
    }

    public static void main(String[] args) throws Exception {
        List<String> inputs = InputReader.getInputFromFiles("/Users/souvik.chakraborty1thoughtworks.com/Documents/AdventOfCode/src/input/trebuchet.txt");


        int result = inputs
                .stream()
                .map(input -> {
                    List<Map.Entry<Integer, Integer>> sortedEntries = new ArrayList<>();
                    wordMapping
                            .entrySet()
                            .stream()
                            .filter(word -> input.contains(word.getKey()))
                            .forEach(word -> {
                                if (countOfOccurrences(input, word.getKey()) > 1) {
                                    sortedEntries.add(Map.entry(input.indexOf(word.getKey()), word.getValue()));
                                    sortedEntries.add(Map.entry(input.lastIndexOf(word.getKey()), word.getValue()));
                                } else {
                                    sortedEntries.add(Map.entry(input.indexOf(word.getKey()), word.getValue()));
                                }
                            });
                    sortedEntries.sort(Map.Entry.comparingByKey());
                    return sortedEntries.get(0).getValue() * 10 + sortedEntries.get(sortedEntries.size() - 1).getValue();
                })
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println(result);
    }
}