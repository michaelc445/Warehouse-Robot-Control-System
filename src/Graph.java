import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    private HashMap<String,Node> vertices;

    public Graph(String fileName,boolean directed) throws Exception {
        this.vertices = new HashMap<>();

        ArrayList<String[]>  parsedInputFile =  parseFile(fileName);
        if (directed){
            this.createDirected(parsedInputFile);
        }
        else{
            this.createUndirected(parsedInputFile);
        }
    }
    public ArrayList<String[]> parseFile(String fileName) throws Exception {
        File inFile = new File(fileName);
        BufferedReader bufferedReader  = new BufferedReader(new FileReader(inFile));
        String  line;
        ArrayList<String[]> parsedInputFile =  new ArrayList<>();
        while ((line = bufferedReader.readLine())!= null){
            String[] splitLine = line.split(",");
            parsedInputFile.add(splitLine);
        }
        bufferedReader.close();

        return parsedInputFile;
    }
    public void createDirected(ArrayList<String[]> inputMap){

    }
    public void createUndirected(ArrayList<String[]>  inputMap){

    }
}
