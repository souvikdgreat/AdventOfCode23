package problems.day8;

import java.util.List;

public class LCMCalculator {
    public static long findGCD(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static long findLCM(long a, long b) {
        return (a * b) / findGCD(a, b);
    }

    public static long findLCMOfArray(List<Long> numbers) {
        long lcm = 1;
        for (long number : numbers) {
            lcm = findLCM(lcm, number);
        }
        return lcm;
    }
}