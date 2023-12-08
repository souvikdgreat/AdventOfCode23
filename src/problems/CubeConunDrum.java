package problems;

import java.util.*;
import java.util.stream.Collectors;

public class CubeConunDrum {
    public static void main(String[] args) throws Exception {
        List<String> inputs = InputReader.getInputFromFiles("/Users/souvik.chakraborty1thoughtworks.com/Documents/AdventOfCode/src/input/cubeConundrum.txt");
        List<Game> games = getListOfGamesFromInput(inputs);


        List<Ball> balls = List.of(
                new Ball(12L, "red"),
                new Ball(13L, "green"),
                new Ball(14L, "blue")
        );


        Rule rule = new Rule(balls);

        long validGamesSum = games
                .stream()
                .filter(game -> GameValidator.isValid(game, rule))
                .map(it -> it.number)
                .mapToLong(Long::longValue)
                .sum();
        System.out.println(validGamesSum);

        //part 2
        Map<Long, Map<String, Long>> gamesWithBalls = new HashMap<>();
        games.forEach(game -> {
            Map<String, Long> ballWithMaxNumber = game
                    .rounds
                    .stream()
                    .map(it -> it.balls)
                    .flatMap(Collection::stream)
                    .collect(Collectors.groupingBy(
                            it -> it.color,
                            Collectors.collectingAndThen(
                                    Collectors.toList(),
                                    itr -> itr.stream().map(itr1 -> itr1.number).mapToLong(Long::longValue).max().orElse(0L)
                            )
                    ));
            gamesWithBalls.put(game.number, ballWithMaxNumber);
        });

        long result = 0L;
        Set<Map.Entry<Long, Map<String, Long>>> entries = gamesWithBalls.entrySet();

        for (Map.Entry<Long, Map<String, Long>> entry : entries) {
            long product = 1L;
            Map<String, Long> value = entry.getValue();
            for (Map.Entry<String, Long> innerEntry : value.entrySet()) {
                product *= innerEntry.getValue();
            }
            result += product;
        }
        System.out.println(result);
    }

    public static List<Game> getListOfGamesFromInput(List<String> inputs) {
        List<Game> games = new ArrayList<>();

        inputs.forEach(input -> {
            String[] gameWithBallsAndRounds = input.split(":");
            String gameString = gameWithBallsAndRounds[0];
            String ballsAndRoundsString = gameWithBallsAndRounds[1];
            String[] noOfRoundsWithBalls = ballsAndRoundsString.split(";");
            List<Round> rounds = new ArrayList<>();
            Arrays.asList(noOfRoundsWithBalls)
                    .forEach(roundString -> {
                        List<Ball> balls = new ArrayList<>();
                        String[] ballsStringForEachRound = roundString.split(",");
                        Arrays.asList(ballsStringForEachRound)
                                .forEach(ballStringForEachRound -> {
                                    String[] ballAndColorString = ballStringForEachRound.trim().split(" ");
                                    Ball ball = new Ball(Long.parseLong(ballAndColorString[0]), ballAndColorString[1]);
                                    balls.add(ball);
                                });
                        Round round = new Round(balls);
                        rounds.add(round);
                    });
            Game game = new Game(rounds, Long.parseLong(gameString.split(" ")[1]));
            games.add(game);
        });
        return games;
    }
}


class Ball {
    Long number;
    String color;

    public Ball(Long number, String color) {
        this.number = number;
        this.color = color;
    }
}


class Round {
    List<Ball> balls;

    public Round(List<Ball> balls) {
        this.balls = balls;
    }
}

class Game {
    List<Round> rounds;
    Long number;

    public Game(List<Round> rounds, Long number) {
        this.rounds = rounds;
        this.number = number;
    }
}

class Rule {
    List<Ball> balls;

    public Rule(List<Ball> balls) {
        this.balls = balls;
    }
}

class GameValidator {
    public static boolean isValid(Game game, Rule rule) {
        Map<String, Long> ballWithValidCount = rule.balls.stream().collect(Collectors.toMap(it -> it.color, it -> it.number));
        boolean isValid = true;
        for (Round round : game.rounds) {
            List<Ball> balls = round.balls;
            for (Ball ball : balls) {
                if (!(ballWithValidCount.containsKey(ball.color) && ball.number <= ballWithValidCount.get(ball.color))) {
                    isValid = false;
                    break;
                }
            }
        }
        return isValid;
    }
}


