import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    private HashMap<String,Node> vertices;
    private int[][] map;
    public Graph(String fileName,boolean directed) throws Exception {
        this.vertices = new HashMap<>();

        int[][]  parsedInputFile =  parseFile(fileName);
        if (directed){
            this.createDirected(parsedInputFile);
        }
        else{
            this.createUndirected(parsedInputFile);
        }
    }
    public int[][] parseFile(String fileName) throws Exception {
        File inFile = new File(fileName);
        BufferedReader bufferedReader  = new BufferedReader(new FileReader(inFile));
        String  line;
        ArrayList<ArrayList<Integer>> parsedInputFile =  new ArrayList<>();
        while ((line = bufferedReader.readLine())!= null){
            ArrayList<Integer> temp = new ArrayList<>();
            for( String val: line.split(",")){
                temp.add(Integer.parseInt(val));
            }
            parsedInputFile.add(temp);
        }
        bufferedReader.close();
        int[][] result = new int[parsedInputFile.size()][parsedInputFile.get(0).size()];
        int i  =  0;
        for (ArrayList<Integer> intList : parsedInputFile){
            int[] temp  =  new  int[intList.size()];
            for (int  j  =0;  j<  intList.size();  j++){
                temp[j]=intList.get(j);
            }
            result[i]=temp;
            i++;
        }
        this.map=result;
        return result;
    }
    public void createDirected(int[][] inputMap){

    }
    public  HashMap<String,Node> getVertices(){
        return this.vertices;
    }
    public void addNode(Node mainNode,int i,int j, int val){
        String down = String.format("%d,%d",i,j);
        if  (this.vertices.containsKey(down)){
            mainNode.addEdge(this.vertices.get(down));
        }else{
            Node downNode = new Node(val,down);
            mainNode.addEdge(downNode);
            this.vertices.put(down,downNode);
        }
    }
    public void createUndirected(int[][] inputMap){
        String name;
        for(int i =0; i< inputMap.length; i++){
            for (int j = 0; j < inputMap[i].length;j++){
                name = String.format("%d,%d",i,j);
                Node temp;
                int val =inputMap[i][j];
                if (!this.vertices.containsKey(name)){
                    temp = new Node(val,name);
                    this.vertices.put(name,temp);
                }else{
                    temp = this.vertices.get(name);
                }
                if (val == 1){
                    if (i-1 >=0 && inputMap[i-1][j]==0){
                        this.addNode(temp,i-1,j,inputMap[i-1][j]);
                    }
                    if (i+1 <inputMap.length && inputMap[i+1][j] ==0){
                        this.addNode(temp,i+1,j,inputMap[i+1][j]);
                    }
                    continue;
                }
                //up
                if (i-1 >= 0 ){
                    int upVal =inputMap[i-1][j];
                    this.addNode(temp,i-1,j,upVal);
                }
                //down
                if (i+1 < inputMap.length){
                    int downVal =inputMap[i+1][j];
                    this.addNode(temp,i+1,j,downVal);
                }
                //left
                if (j-1 >= 0  &&  inputMap[i][j-1]!=1){
                    int leftVal =inputMap[i][j-1];
                    this.addNode(temp,i,j-1,leftVal);
                }
                //right
                if (j+1 < inputMap[i].length  &&  inputMap[i][j+1]!=1){
                    int rightVal =inputMap[i][j+1];
                    this.addNode(temp,i+1,j,rightVal);
                }
            }
        }
    }
}
