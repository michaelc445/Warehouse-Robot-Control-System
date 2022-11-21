package util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * The Parser class is a stateless class with methods to parse the data from files
 */
public class Parser {

    private static final String CSV_DELIMITER = ",";

    /**
     * Parses data from the .csv file containing a warehouse map layout represented by the 0 and 1
     * @param warehouseMapFile - filename of the file in the 'resources' folder
     * @return int[][] warehouseMapLayout
     */
    public int[][] parseMapLayout(String warehouseMapFile) throws IOException {
        List<int[]> rows;
        try (Stream<String> fileStream = getFileFromResourcesAsStream(warehouseMapFile)) {
            rows = fileStream
                    .map(line -> line.split(CSV_DELIMITER))
                    .map(Parser::mapStringArrToIntArr)
                    .toList();

        }
        return rows.toArray(int[][]::new);
    }

    public String[] parseItems(String itemsFile)  {
        List<String> items = new ArrayList<>();
        try (Stream<String> fileStream = getFileFromResourcesAsStream(itemsFile)) {
            fileStream.forEach(line -> {
                String[] itemArray = line.split(CSV_DELIMITER);
                items.addAll(Arrays.asList(itemArray));
            });
        }
        return items.toArray(String[]::new);
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
