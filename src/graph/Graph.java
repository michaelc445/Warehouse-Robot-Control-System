package graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Graph {
    private final HashMap<String,Node> vertices;
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
                    //System.out.println("up");
                    this.addNode(temp,i-1,j,upVal);
                }
                //down
                if (i+1 < inputMap.length){
                    //System.out.println("down");
                    int downVal =inputMap[i+1][j];
                    this.addNode(temp,i+1,j,downVal);
                }
                //left
                if (j-1 >= 0  &&  inputMap[i][j-1]!=1){
                    //System.out.println("left");
                    int leftVal =inputMap[i][j-1];
                    this.addNode(temp,i,j-1,leftVal);
                }
                //right
                if (j+1 < inputMap[i].length  &&  inputMap[i][j+1]!=1){
                    //System.out.println("right");
                    int rightVal =inputMap[i][j+1];
                    this.addNode(temp,i,j+1,rightVal);
                }

            }

        }
    }

    public ArrayList<String> dijkstra(Node start, Node  end){
        if (start==null  || end==null){
            return null;
        }
        String  find = end.getLocation();
        PriorityQueue<PQItem> pq = new PriorityQueue<>();
        HashMap<Node,Node> path = new HashMap<>();
        HashMap<Node,Boolean>  explored  =  new HashMap<>();
        exploreNode(start,pq,path,explored,0);
        while(pq.size() > 0){
            PQItem check = pq.remove();
            exploreNode(check.getNext(),pq,path,explored,check.getCost());
            if (check.getNext().getLocation().equals(find)){
                return getPath(start, end, path);
            }
        }
        return null;
    }
    private void exploreNode(Node start,PriorityQueue<PQItem> pq,HashMap<Node,Node> path,HashMap<Node,Boolean> explored,int cost){
        for (Map.Entry<String,Node> pair : start.getEdges().entrySet()){
            if(explored.containsKey(pair.getValue())){
                continue;
            }
            explored.put(pair.getValue(),true);
            PQItem newItem = new PQItem(cost+1,pair.getValue(),start);
            pq.add(newItem);
            path.put(pair.getValue(),start);
        }
    }
    public Node getNode(String location){
        if  (this.vertices.containsKey(location)) {
            return this.vertices.get(location);
        }
        return null;
    }
    private ArrayList<String> getPath(Node start, Node end, HashMap<Node,Node> path){

        ArrayList<String> resultPath = new ArrayList<>();

        if (start.getLocation().equals(end.getLocation())){
            return resultPath;
        }
        if (!path.containsKey(end)){
            return  null;
        }
        resultPath.add(end.getLocation());
        Node check = path.get(end);
        resultPath.add(check.getLocation());
        while (!check.getLocation().equals(start.getLocation())){
            check = path.get(check);
            resultPath.add(check.getLocation());
        }
        Collections.reverse(resultPath);
        return resultPath;

    }
}
