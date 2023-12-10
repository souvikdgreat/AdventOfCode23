package problems.day3;

import problems.InputReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GearRatios {
    public static void main(String[] args) throws Exception {
        List<String> inputsString = InputReader.getInputFromFiles("/Users/souvik.chakraborty1thoughtworks.com/Documents/AdventOfCode/src/input/gearRatios.txt");
        List<Input> inputsList = parseInput(inputsString);

        Map<Integer, List<Input>> mapping = inputsList
                .stream()
                .collect(Collectors.groupingBy(input -> input.lineNumber));


        long sum = inputsList
                .stream()
                .filter(Input::isNumber)
                .filter(input -> isSchematicNumber(mapping, input, input.lineNumber))
                .map(it -> it.number)
                .map(Long::parseLong)
                .mapToInt(Long::intValue)
                .sum();
        System.out.println(sum);

        List<Input> missingInputs = inputsList
                .stream()
                .filter(input -> input.number.equals("*"))
                .collect(Collectors.toList());


        long result = missingInputs
                .stream()
                .map(missingInput ->
                        getMissingGearRatios(mapping, missingInput, missingInput.lineNumber)
                )
                .filter(it -> it.size() > 1)
                .map(it -> Long.parseLong(it.get(0).number) * Long.parseLong(it.get(1).number))
                .mapToLong(Long::longValue)
                .sum();

        System.out.println(result);
    }

    public static boolean isSchematicNumber(Map<Integer, List<Input>> mapping, Input input, int index) {
        List<Input> comparisonInputLists = mapping.get(index)
                .stream().filter(Predicate.not(Input::isNumber)).collect(Collectors.toList());

        if (index != mapping.size() - 1) {
            comparisonInputLists.addAll(
                    mapping.get(index + 1)
                            .stream().filter(Predicate.not(Input::isNumber))
                            .collect(Collectors.toList())
            );
        }

        if (index != 0) {
            comparisonInputLists.addAll(
                    mapping.get(index - 1)
                            .stream().filter(Predicate.not(Input::isNumber))
                            .collect(Collectors.toList())
            );
        }

        Set<Integer> indexForComparison = comparisonInputLists
                .stream().filter(Predicate.not(Input::isNumber))
                .flatMap(it -> Stream.of(it.startIndex, it.lastIndex))
                .collect(Collectors.toSet());

        return indexForComparison.contains(input.startIndex - 1) ||
                indexForComparison.contains(input.lastIndex + 1) ||
                indexForComparison.contains(input.startIndex) ||
                indexForComparison.contains(input.lastIndex) ||
                indexForComparison.stream().anyMatch(idx -> idx > input.startIndex && idx < input.lastIndex);
    }

    public static List<Input> getMissingGearRatios(Map<Integer, List<Input>> mapping, Input input, int index) {
        List<Input> comparisonInputLists = mapping.get(index)
                .stream().filter(Input::isNumber).collect(Collectors.toList());

        if (index != mapping.size() - 1) {
            comparisonInputLists.addAll(
                    mapping.get(index + 1)
                            .stream().filter(Input::isNumber)
                            .collect(Collectors.toList())
            );
        }

        if (index != 0) {
            comparisonInputLists.addAll(
                    mapping.get(index - 1)
                            .stream().filter(Input::isNumber)
                            .collect(Collectors.toList())
            );
        }

        List<Input> collect = comparisonInputLists
                .stream()
                .filter(
                        it ->
                                it.startIndex == input.startIndex ||
                                        it.lastIndex == input.startIndex ||
                                        input.startIndex - 1 == it.lastIndex ||
                                        input.lastIndex + 1 == it.startIndex ||
                                        it.lastIndex > input.startIndex && it.startIndex < input.startIndex
                )
                .collect(Collectors.toList());
        return collect;
    }

    public static List<Input> parseInput(List<String> inputStrings) {
        List<Input> inputs = new ArrayList<>();

        IntStream.range(0, inputStrings.size())
                .forEach(idx -> {
                    String inputString = inputStrings.get(idx);
                    try {
                        for (int i = 0; i < inputString.length(); i++) {
                            Character character = inputString.charAt(i);
                            if (character == '.') {
                                continue;
                            }
                            if (!Character.isDigit(character)) {
                                Input input = new Input(character.toString(), i, i, idx);
                                inputs.add(input);
                                continue;
                            }
                            StringBuilder number = new StringBuilder();
                            int startIndex = i;
                            while (Character.isDigit(character)) {
                                number.append(character);
                                i++;
                                if (i < inputString.length()) {
                                    character = inputString.charAt(i);
                                } else {
                                    break;
                                }
                            }
                            i--;
                            int lastIndex = i;
                            Input input = new Input(number.toString(), startIndex, lastIndex, idx);
                            inputs.add(input);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        return inputs;
    }
}


class Input {
    String number;
    int startIndex;
    int lastIndex;
    int lineNumber;

    public Input(String number, int startIndex, int lastIndex, int lineNumber) {
        this.number = number;
        this.startIndex = startIndex;
        this.lastIndex = lastIndex;
        this.lineNumber = lineNumber;
    }

    public boolean isNumber() {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if (this.number == null) {
            return false;
        }
        return pattern.matcher(this.number).matches();
    }

    @Override
    public String toString() {
        return "Input{" +
                "number='" + number + '\'' +
                ", startIndex=" + startIndex +
                ", lastIndex=" + lastIndex +
                ", lineNumber=" + lineNumber +
                '}';
    }
}