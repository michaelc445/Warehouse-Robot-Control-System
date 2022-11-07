package graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import org.paukov.combinatorics3.Generator;

public class Graph {
    private final HashMap<Point,Node> vertices;
    private final ArrayList<Point>  shelves;
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
    }
    public Point getStartPoint(){
        return this.startPoint;
    }
    public Point getEndPoint(){
        return this.endPoint;
    }

    public ArrayList<Path> shortestOrderPath(Node start, Node end, ArrayList<Point> items){
        if(items==null  || items.size()==0){
            return null;
        }
        List<List<Point>> permutationList = Generator.permutation(items).simple().stream().toList();
        int min = Integer.MAX_VALUE;
        List<Point> result = new ArrayList<>();
        items.add(start.getLocation());
        items.add(end.getLocation());
        HashMap<Point,HashMap<Point,Path>>  distancePairMap = this.createItemPairDistanceMap(items);
        for (List<Point> points : permutationList) {
            int cost = this.pathCost(start, end, points,distancePairMap);
            if (cost < min) {
                min = cost;
                result = points;
            }
        }
        return this.extractPaths(distancePairMap,result);
    }
    private ArrayList<Path> extractPaths(HashMap<Point,HashMap<Point,Path>>  distancePairMap,List<Point> path){
        if (distancePairMap==null || distancePairMap.size()==0){
            return null;
        }
        if(path.size()==0)  return null;
        if (this.startPoint==null || !distancePairMap.containsKey(this.startPoint)){
            return null;
        }
        ArrayList<Path> result = new ArrayList<>();
        if (!distancePairMap.get(startPoint).containsKey(path.get(0)))  return  null;

        Path firstPath = distancePairMap.get(this.startPoint).get(path.get(0));
        result.add(firstPath);
        for (int i  =0; i  < path.size()-1;i++){
            Point p1  =  path.get(i);
            Point p2  =  path.get(i+1);
            if (!distancePairMap.containsKey(p1)  ||  !distancePairMap.get(p1).containsKey(p2))  return  null;
            Path nextPath = distancePairMap.get(path.get(i)).get(path.get(i+1));
            result.add(nextPath);
        }
        Path lastPath = distancePairMap.get(path.get(path.size()-1)).get(this.endPoint);
        result.add(lastPath);
        return  result;
    }
    private  HashMap<Point,HashMap<Point,Path>> createItemPairDistanceMap(ArrayList<Point> items){
        HashMap<Point,HashMap<Point,Path>> result = new HashMap<>();
        for (int i=0; i < items.size();i++){
            for(int j=0; j < items.size();j++){
                if (i==j) continue;

                Point  p1  =items.get(i);
                Point p2 = items.get(j);
                if (result.containsKey(p1) && result.get(p1).containsKey(p2))continue;
                Path distance =  this.dijkstra(this.getNode(p1),this.getNode(p2));
                if (!result.containsKey(p1)){
                    result.put(p1,new HashMap<>());
                }
                result.get(p1).put(p2,distance);
                if(!result.containsKey(p2)){
                    result.put(p2,new HashMap<>());
                }
                result.get(p2).put(p1,distance.reversed());
            }
        }
        return result;
    }
    private int  pathCost(Node start,Node end,List<Point> items,HashMap<Point,HashMap<Point,Path>> distances){
        if (items  == null || items.size()==0){
            return  0;
        }
        Point  s  =  start.getLocation();
        HashMap<Point,Path> startToFirst = distances.get(s);

        int cost = startToFirst.get(items.get(0)).getCost();
        for (int i =0; i < items.size()-1;i++){
            startToFirst=distances.get(items.get(i));
            Point get =  items.get(i+1);
            cost += startToFirst.get(get).getCost();
        }
        HashMap<Point,Path> lastItemToDropOff = distances.get(items.get(items.size()-1));
        cost += lastItemToDropOff.get(end.getLocation()).getCost();
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
    public Path dijkstra(Node start, Node  end){
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
    private Path getPath(Node start, Node end, HashMap<Node,Node> path){

        ArrayList<Point> resultPath = new ArrayList<>();

        if (start.getLocation().equals(end.getLocation())){
            return new Path(resultPath);
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
        return new Path(resultPath);

    }
    public ArrayList<Path> getPathMultipleNodes(Node start, Node end, ArrayList<Point> items){
        ArrayList<Path> result = new ArrayList<>();
        if (items.size()==0){
            result.add(this.dijkstra(start,end));
            return result;
        }
        Path first = this.dijkstra(start,this.getNode(items.get(0)));
        if (first ==  null){
            return new ArrayList<>();
        }
        result.add(first);
        for(int  i =0; i < items.size()-1;i++){
            Path temp = this.dijkstra(this.getNode(items.get(i)),this.getNode(items.get(i+1)));
            if (temp ==  null){
                return new ArrayList<>();
            }
            result.add(temp);
        }
        Path finish = this.dijkstra(this.getNode(items.get(items.size()-1)),end);
        if  (finish == null){
            return new ArrayList<>();
        }
        result.add(finish);
        return result;
    }

}
