package main;

import graph.Graph;
import graph.Point;

public class Main {
    public static void main(String[] args) throws Exception {
        String graphFile = "src/test/java/TestMaps/testMap.csv";
        Graph inputGraph  = new Graph(graphFile,false);
        Warehouse warehouse = new Warehouse();
        warehouse.addItem("Hammer", new Point(2,2));
        warehouse.addItem("Screw", new Point(4,4));
        warehouse.addItem("Helmet", new Point(4,2));
        warehouse.addItem("Axe", new Point(5,5));
        warehouse.addItem("Wrench", new Point(1,4));

        new OrderSystem(inputGraph);
    }
}