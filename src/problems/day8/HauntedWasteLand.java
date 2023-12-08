package problems.day8;

import problems.InputReader;

import java.util.*;
import java.util.stream.Collectors;

public class HauntedWasteLand {
    public static void main(String[] args) throws Exception {
        List<String> inputString = InputReader.getInputFromFiles("/Users/souvik.chakraborty1thoughtworks.com/Documents/AdventOfCode/src/input/wasteLand.txt");
        Input input = parseInput(inputString);
        getNoOfStepsPart2(input);
    }

    public static Input parseInput(List<String> inputStrings) {
        String steps = inputStrings.get(0);
        Map<String, Map.Entry<String, String>> routes = new HashMap<>();
        for (int i = 2; i < inputStrings.size(); i++) {
            String[] sourceAlongWithRoutes = inputStrings.get(i).split("=");

            String source = sourceAlongWithRoutes[0].trim();
            List<String> leftRightRoute = Arrays.stream(sourceAlongWithRoutes[1]
                            .replaceAll("[\\(\\)]", "")
                            .split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
            routes.put(source, Map.entry(leftRightRoute.get(0), leftRightRoute.get(1)));

        }

        return new Input(steps, routes);
    }

    private static void getNoOfStepsPart2(Input input) {
        List<String> sourcesThatStartsWithA = input.routes
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().endsWith("A"))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        System.out.println(
                LCMCalculator
                        .findLCMOfArray(sourcesThatStartsWithA
                                .stream()
                                .map(it -> getNoOfStepsToReachDestinationEndingWithZ(input.steps, input.routes, it))
                                .map(it -> (long) it)
                                .collect(Collectors.toList())
                        )
        );
    }

    private static int getNoOfStepsToReachDestinationEndingWithZ(String steps, Map<String, Map.Entry<String, String>> routes, String source) {
        List<Character> totalSteps = new ArrayList<>();
        int startDirectionIndex = 0;
        int endDirectionIndex = steps.length() - 1;

        while (true) {
            if (startDirectionIndex > endDirectionIndex) {
                startDirectionIndex = 0;
            }
            totalSteps.add(steps.charAt(startDirectionIndex));
            Map.Entry<String, String> entry = routes.get(source);
            if (steps.charAt(startDirectionIndex) == 'L') {
                source = entry.getKey();

            } else {
                source = entry.getValue();
            }
            if (source.endsWith("Z") && totalSteps.size() != 2) {
                break;
            }

            startDirectionIndex++;
        }
        return totalSteps.size();
    }


    private static int getNoOfSteps(Input input) {
        List<Character> totalSteps = new ArrayList<>();
        String source = "AAA";
        String destination = "ZZZ";

        String steps = input.steps;
        Map<String, Map.Entry<String, String>> routes = input.routes;
        int startDirectionIndex = 0;
        int endDirectionIndex = steps.length() - 1;

        while (true) {
            if (startDirectionIndex > endDirectionIndex) {
                startDirectionIndex = 0;
            }
            totalSteps.add(steps.charAt(startDirectionIndex));
            Map.Entry<String, String> entry = routes.get(source);
            if (steps.charAt(startDirectionIndex) == 'L') {
                source = entry.getKey();

            } else {
                source = entry.getValue();
            }
            if (source.equals(destination)) {
                break;
            }
            startDirectionIndex++;
        }
        return totalSteps.size();
    }

    static class Input {
        String steps;
        Map<String, Map.Entry<String, String>> routes;

        public Input(String steps, Map<String, Map.Entry<String, String>> routes) {
            this.steps = steps;
            this.routes = routes;
        }
    }
}