package graph;

import java.util.HashMap;
public class Node {
    private int value;
    private Point location;
    private HashMap<Point,Node> edges;
    private  int degree;
    public Node(int value, Point location){
        if (location==null)return;
        this.value = value;
        this.location=location;
        this.edges = new HashMap<>();
        this.degree=0;
    }
    public int getValue(){
        return this.value;
    }
    public  Point  getLocation(){
        return this.location;
    }
    public HashMap<Point,Node> getEdges(){
        return this.edges;
    }
    public void addEdge(Node neighbour){
        if (neighbour==null || neighbour ==this || this.edges.containsKey(neighbour.getLocation()))return;
        this.edges.put(neighbour.getLocation(),neighbour);
        this.degree+=1;
    }
    public int getDegree(){
        return this.degree;
    }
    public void removeEdge(Node neighbour){
        this.edges.remove(neighbour.getLocation());
    }
    public String toString(){
        return String.format("%s",this.location.toString());
    }

}
