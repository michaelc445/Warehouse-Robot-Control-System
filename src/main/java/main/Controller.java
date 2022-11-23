package main;
import graph.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Controller {


    public Controller(Warehouse warehouse, Set<String> order) {
        ArrayList<Point> locationsToVisit;

        locationsToVisit = new ArrayList<>();

        //perform a database lookup to fetch item locations
        for (String item : order){
            locationsToVisit.add(warehouse.getItemLocation(item));
        }

        //calculate a path for the robot through the warehouse visiting all locations
        PathFinder pathFinder = new PathFinder();
        List<Path> shortestPath = pathFinder.findShortestPath(warehouse.getWarehouseGraph(), locationsToVisit);

        //send this path to the visualization tool
//        new initVisualizationTool(warehouse, shortestPath, locationsToVisit);

    }


}
