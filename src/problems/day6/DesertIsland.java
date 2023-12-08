package problems.day6;

import problems.InputReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DesertIsland {
    public static void main(String[] args) throws Exception {
        List<String> inputs = InputReader.getInputFromFiles("/Users/souvik.chakraborty1thoughtworks.com/Documents/AdventOfCode/src/input/desertIsland.txt");

        List<RaceInput> raceInputs = getRaceInputs(inputs);

        long marginOfError = raceInputs
                .stream()
                .mapToLong(DesertIsland::noOfWaysToWinTheRace)
                .reduce(1, (i1, i2) -> i1 * i2);

        System.out.println(marginOfError);
    }

    public static List<RaceInput> getRaceInputs(List<String> inputs) {
        List<RaceInput> raceInputs = new ArrayList<>();

        List<Long> times = Arrays.stream(
                        inputs.get(0).split(":")[1]
                                .trim()
                                .split(" ")
                )
                .filter(Predicate.not(String::isBlank))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        List<Long> distance = Arrays.stream(
                        inputs.get(1).split(":")[1]
                                .trim()
                                .split(" "))
                .filter(Predicate.not(String::isBlank))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        IntStream.range(0, times.size())
                .forEach(idx -> {
                    RaceInput raceInput = new RaceInput(times.get(idx), distance.get(idx));
                    raceInputs.add(raceInput);
                });
        return raceInputs;
    }

    public static long noOfWaysToWinTheRace(RaceInput raceInput) {
        long noOfWaysToWinTheRace = 0;
        long time = raceInput.time;
        long distance = raceInput.distance;

        for (long i = 1; i < time; i++) {
            long remainingTime = time - i;
            if (remainingTime * i > distance) {
                noOfWaysToWinTheRace++;
            }
        }
        return noOfWaysToWinTheRace;
    }

}

class RaceInput {
    long time;
    long distance;

    public RaceInput(Long time, Long distance) {
        this.time = time;
        this.distance = distance;
    }
}