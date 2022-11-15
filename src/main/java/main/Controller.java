package main;
import display.initVisualizationTool;
import graph.Graph;
import graph.Node;
import graph.Path;
import java.util.ArrayList;
import java.util.HashSet;

public class Controller {
    Warehouse warehouse = new Warehouse();
    ArrayList<graph.Point> locationsToVisit;

    public Controller(Graph inputGraph, HashSet<String> order){

        Node start = inputGraph.getNode(inputGraph.getStartPoint());
        Node end = inputGraph.getNode(inputGraph.getEndPoint());
        locationsToVisit = new ArrayList<>();

        //perform a database lookup to fetch item locations
        for (String item : order){
            locationsToVisit.add(warehouse.getItemLocation(item));
        }

        //calculate a path for the robot through the warehouse visiting all locations
        ArrayList<Path> path = inputGraph.shortestOrderPath(start,end, locationsToVisit);


        //send this path to the visualization tool
        new initVisualizationTool(inputGraph, path, locationsToVisit);

    }


}
