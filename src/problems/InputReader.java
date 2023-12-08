package problems;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class InputReader {
    public static List<String> getInputFromFiles(String path) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
        List<String> input = new ArrayList<>();

        String line = br.readLine();

        while (line != null) {
            input.add(line);
            line = br.readLine();
        }
        return input;
    }
}
