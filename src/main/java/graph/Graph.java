package graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import org.paukov.combinatorics3.Generator;

public class Graph {
    private final HashMap<Point,Node> vertices;
    private  final ArrayList<Point>  shelves;
    private final HashMap<Point,HashMap<Point,Integer>> distances;
    private final int[][] map;
    private final Point startPoint;
    private final Point endPoint;
    public Graph(String fileName,boolean directed) throws Exception {
        this.vertices = new HashMap<>();

        int[][]  parsedInputFile =  parseFile(fileName);
        if (!directed){
            this.createUndirected(parsedInputFile);
        }
        this.map= parsedInputFile;
        this.startPoint = new Point(0,0);
        this.endPoint = new Point(0,6);

        this.shelves  = this.createShelveList(parsedInputFile);
        this.distances = this.distanceBetweenAllShelves();

    }
    public Point getStartPoint(){
        return this.startPoint;
    }
    public Point getEndPoint(){
        return this.endPoint;
    }

    //TODO this is too slow on large maps with  lots of shelves. change this to floyd warshall instead of dijkstra
    //then extract the shelf values
    public HashMap<Point,HashMap<Point,Integer>> distanceBetweenAllShelves(){
        HashMap<Point,HashMap<Point,Integer>>  result = new  HashMap<>();
        this.shelves.add(this.startPoint);
        this.shelves.add(this.endPoint);
        for (int i =0; i < this.shelves.size()-1;i++){
            Node  from = this.getNode(this.shelves.get(i));
            if (from == null){
                continue;
            }
            if (!result.containsKey(from.getLocation())){
                result.put(from.getLocation(),new HashMap<>());
            }
            for (int j =i+1; j < this.shelves.size();j++){
                Node to =this.getNode(this.shelves.get(j));
                if (to == null){
                    continue;
                }
                if  (!result.get(from.getLocation()).containsKey(to.getLocation())){
                    int distance =  this.dijkstra(from,to).size()-1;
                    result.get(from.getLocation()).put(to.getLocation(),distance);
                    if (!result.containsKey(to.getLocation())){
                        result.put(to.getLocation(),new HashMap<>());
                    }
                    result.get(to.getLocation()).put(from.getLocation(),distance);
                }
            }
        }
        this.shelves.remove(this.startPoint);
        this.shelves.remove(this.endPoint);
        return  result;
    }
    public List<Point> shortestOrderPath(Node start,Node end,ArrayList<Point> items){
        List<List<Point>> permutationList = Generator.permutation(items).simple().stream().toList();
        int min = Integer.MAX_VALUE;
        List<Point> result = new ArrayList<>();
        for (List<Point> points : permutationList) {
            int cost = this.pathCost(start, end, points);
            if (cost < min) {
                min = cost;
                result = points;
            }
        }
        return result;
    }
    private int  pathCost(Node start,Node end,List<Point> items){
        if (items  == null || items.size()==0){
            return  0;
        }
        HashMap<Point,HashMap<Point,Integer>> distances = this.distances;
        Point  s  =  start.getLocation();
        HashMap<Point,Integer> startToFirst = distances.get(s);

        int cost = startToFirst.get(items.get(0));
        for (int i =0; i < items.size()-1;i++){
            startToFirst=distances.get(items.get(i));
            Point get =  items.get(i+1);
            cost += startToFirst.get(get);
        }
        HashMap<Point,Integer> lastItemToDropOff = distances.get(items.get(items.size()-1));
        cost += lastItemToDropOff.get(end.getLocation());
        return cost;

    }
    private ArrayList<Point> createShelveList(int[][] map){
        ArrayList<Point> shelveList = new ArrayList<>();
        for (int i =0; i  < map.length;i++){
            for(int j=0;  j  < map[i].length;j++){
                if (map[i][j]==1){
                    shelveList.add(new  Point(j,i));
                }
            }
        }
        return shelveList;
    }
    public ArrayList<Point> getShelves(){
        return this.shelves;
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
        return result;
    }

    public  HashMap<Point,Node> getVertices(){
        return this.vertices;
    }
    public void addNode(Node mainNode, int y, int x, int val){
        Point  location = new Point(x,y);
        if  (this.vertices.containsKey(location)){
            mainNode.addEdge(this.vertices.get(location));
        }else{
            Node otherNode = new Node(val,location);
            mainNode.addEdge(otherNode);
            this.vertices.put(location,otherNode);
        }
    }
    public void createUndirected(int[][] inputMap){
        for(int i =0; i< inputMap.length; i++){
            for (int j = 0; j < inputMap[i].length;j++){
                Point point  =new Point(j,i);
                Node temp;
                int val =inputMap[i][j];
                if (!this.vertices.containsKey(point)){
                    temp = new Node(val,point);
                    this.vertices.put(point,temp);
                }else{
                    temp = this.vertices.get(point);
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
                    this.addNode(temp,i,j+1,rightVal);
                }

            }

        }
    }
    public int[][] getMap(){
        return this.map;
    }
    public ArrayList<Point> dijkstra(Node start, Node  end){
        if (start==null  || end==null){
            return null;
        }
        Point find = end.getLocation();
        PriorityQueue<PQItem> pq = new PriorityQueue<>();
        HashMap<Node,Node> path = new HashMap<>();
        HashMap<Node,Boolean>  explored  =  new HashMap<>();
        exploreNode(start,pq,path,explored,0);
        while(pq.size() > 0){
            PQItem check = pq.remove();
            exploreNode(check.next(),pq,path,explored,check.cost());
            if (check.next().getLocation().equals(find)){
                return getPath(start, end, path);
            }
        }
        return null;
    }
    private void exploreNode(Node start,PriorityQueue<PQItem> pq,HashMap<Node,Node> path,HashMap<Node,Boolean> explored,int cost){
        for (Map.Entry<Point,Node> pair : start.getEdges().entrySet()){
            if(explored.containsKey(pair.getValue())){
                continue;
            }
            explored.put(pair.getValue(),true);
            PQItem newItem = new PQItem(cost+1,pair.getValue(),start);
            pq.add(newItem);
            path.put(pair.getValue(),start);
        }
    }
    public Node getNode(Point location){
        if  (this.vertices.containsKey(location)) {
            return this.vertices.get(location);
        }
        return null;
    }
    private ArrayList<Point> getPath(Node start, Node end, HashMap<Node,Node> path){

        ArrayList<Point> resultPath = new ArrayList<>();

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
    public ArrayList<ArrayList<Point>> getPathMultipleNodes(Node start, Node end, ArrayList<Point> items){
        ArrayList<ArrayList<Point>> result = new ArrayList<>();
        if (items.size()==0){
            result.add(this.dijkstra(start,end));
            return result;
        }
        ArrayList<Point> first = this.dijkstra(start,this.getNode(items.get(0)));
        if (first ==  null){
            return new ArrayList<>();
        }
        result.add(first);
        for(int  i =0; i < items.size()-1;i++){
            ArrayList<Point> temp = this.dijkstra(this.getNode(items.get(i)),this.getNode(items.get(i+1)));
            if (temp ==  null){
                return new ArrayList<>();
            }
            result.add(temp);
        }
        ArrayList<Point> finish = this.dijkstra(this.getNode(items.get(items.size()-1)),end);
        if  (finish == null){
            return new ArrayList<>();
        }
        result.add(finish);
        return result;
    }
}
