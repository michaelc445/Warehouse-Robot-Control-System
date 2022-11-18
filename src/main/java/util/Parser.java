package util;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        File inFile = new File(warehouseMapFile);
        BufferedReader bufferedReader  = new BufferedReader(new FileReader(inFile));
        String  line;
        ArrayList<int[]> parsedInputFile =  new ArrayList<>();
        while ((line = bufferedReader.readLine())!= null){
            String[] t = line.split(CSV_DELIMITER);
            parsedInputFile.add(Arrays.stream(t).mapToInt(Integer::parseInt).toArray());
        }
        bufferedReader.close();
        return parsedInputFile.toArray(int[][]::new);
    }

    public String[] parseItems(String itemsFile) throws IOException {
        File inFile = new File(itemsFile);
        BufferedReader bufferedReader  = new BufferedReader(new FileReader(inFile));
        String  line;
        ArrayList<String> parsedInputFile =  new ArrayList<>();
        while ((line = bufferedReader.readLine())!= null){
            String[] t = line.split(CSV_DELIMITER);
            parsedInputFile.addAll(List.of(t));
        }
        bufferedReader.close();
        return parsedInputFile.toArray(String[]::new);
    }

}
