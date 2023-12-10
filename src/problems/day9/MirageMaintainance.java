package problems.day9;

import problems.InputReader;

import java.util.*;
import java.util.stream.Collectors;

public class MirageMaintainance {
    public static void main(String[] args) throws Exception {
        List<String> inputStrings = InputReader.getInputFromFiles("/Users/souvik.chakraborty1thoughtworks.com/Documents/AdventOfCode/src/input/mirageMaintainance.txt");
        List<Input> inputs = parseInput(inputStrings);
        long result = inputs
                .stream()
                .map(MirageMaintainance::getNextExtraPolatedValueForward)
                .mapToLong(Long::longValue)
                .sum();

        System.out.println(result);

        result = inputs
                .stream()
                .map(MirageMaintainance::getNextExtraPolatedValueBackward)
                .mapToLong(Long::longValue)
                .sum();

        System.out.println(result);
    }


    static List<Input> parseInput(List<String> inputStrings) {
        return inputStrings
                .stream()
                .map(inputString -> inputString.split(" "))
                .map(Arrays::asList)
                .map(Input::new)
                .collect(Collectors.toList());
    }

    static Long getNextExtraPolatedValueForward(Input input) {
        List<Long> numbers = input.numbers;
        List<List<Long>> intermediatryList = new ArrayList<>();
        intermediatryList.add(numbers);
        List<Long> tempList = numbers;

        while (Collections.frequency(tempList, 0L) != tempList.size()) {
            List<Long> intermediateList = new ArrayList<>();
            for (int i = 0; i < tempList.size() - 1; i++) {
                intermediateList.add(tempList.get(i + 1) - tempList.get(i));
            }
            intermediatryList.add(intermediateList);
            tempList = intermediateList;
        }

        if (!intermediatryList.get(intermediatryList.size() - 1).isEmpty()) {
            List<Long> lastIntermediaryList = intermediatryList.get(intermediatryList.size() - 1);
            Long incrementValue = lastIntermediaryList.get(lastIntermediaryList.size() - 1);
            for (int i = intermediatryList.size() - 2; i >= 0; i--) {
                List<Long> intermediateList = intermediatryList.get(i);
                Long lastValue = intermediateList.get(intermediateList.size() - 1);
                intermediateList.add(incrementValue + lastValue);
                incrementValue = incrementValue + lastValue;
            }

        }
        List<Long> firstIntermediaryList = intermediatryList.get(0);

        return firstIntermediaryList.get(firstIntermediaryList.size() - 1);
    }

    static Long getNextExtraPolatedValueBackward(Input input) {
        LinkedList<Long> numbers = new LinkedList<>(input.numbers);
        List<LinkedList<Long>> intermediatryList = new ArrayList<>();
        intermediatryList.add(numbers);

        LinkedList<Long> tempList = numbers;

        while (Collections.frequency(tempList, 0L) != tempList.size()) {
            LinkedList<Long> intermediateList = new LinkedList<>();
            for (int i = 0; i < tempList.size() - 1; i++) {
                intermediateList.add(tempList.get(i + 1) - tempList.get(i));
            }
            intermediatryList.add(intermediateList);
            tempList = intermediateList;
        }

        List<Long> lastIntermediaryList = intermediatryList.get(intermediatryList.size() - 1);
        lastIntermediaryList.add(0L);
        for (int i = intermediatryList.size() - 2; i >= 0; i--) {
            LinkedList<Long> intermediateList = intermediatryList.get(i);
            intermediateList.addFirst(intermediateList.getFirst() - lastIntermediaryList.get(0));
            lastIntermediaryList = intermediateList;
        }
        LinkedList<Long> firstIntermediaryList = intermediatryList.get(0);

        return firstIntermediaryList.getFirst();
    }
}

class Input {
    List<Long> numbers;

    public Input(List<String> numbers) {
        this.numbers = numbers.stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }
}
