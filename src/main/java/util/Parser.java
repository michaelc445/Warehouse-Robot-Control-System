package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;

/**
 * The Parser class is a stateless class with methods to parse the data from files
 */
public class Parser {

    private static final String DELIMITER = ",";

    /**
     * Parses data from the .csv file containing a warehouse map layout represented by the 0 and 1
     * @param warehouseMapFile - filename of the file in the 'resources' folder
     * @return int[][] warehouseMapLayout
     */
    public int[][] parseMapLayout(String warehouseMapFile) {
        List<int[]> rows;
        try (Stream<String> fileStream = getFileFromResourcesAsStream(warehouseMapFile)) {
            rows = fileStream
                    .map(line -> line.split(DELIMITER))
                    .map(Parser::mapStringArrToIntArr)
                    .toList();
        }
        int layoutWidth = rows.size();
        int layoutLength = rows.get(0).length;
        return rows.toArray(new int[layoutWidth][layoutLength]);
    }

    private Stream<String> getFileFromResourcesAsStream(String fileName) {
        var inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("File not found! File: " + fileName);
        } else {
            return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines();
        }
    }

    private static int[] mapStringArrToIntArr(String[] array) {
        return Stream.of(array).mapToInt(Integer::parseInt).toArray();
    }

}
