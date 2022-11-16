package main;

import display.initVisualizationTool;
import graph.Path;
import graph.PathFinder;
import graph.Point;
import graph.WarehouseGraph;
import stock.Item;
import util.Parser;

import java.util.List;

public class Main {

    private static final String WAREHOUSE_MAP_FILE = "warehouse_map.csv";
    private static final Point START_POINT = new Point(0, 0);
    private static final Point END_POINT = new Point(0, 6);

    public static void main(String[] args) throws Exception {
        Parser parser = new Parser();
        int[][] warehouseMapLayout = parser.parseMapLayout(WAREHOUSE_MAP_FILE);

        WarehouseGraph warehouseGraph = new WarehouseGraph(warehouseMapLayout, START_POINT, END_POINT);

        Warehouse warehouse = new Warehouse(warehouseGraph);

        warehouse.addItem(new Item("Hammer", new Point(2,2)));
        warehouse.addItem(new Item("Screw", new Point(4,4)));
        warehouse.addItem(new Item("Helmet", new Point(4,2)));
        warehouse.addItem(new Item("Axe", new Point(5,5)));
        warehouse.addItem(new Item("Wrench", new Point(1,4)));

        new OrderSystem(warehouse);
    }
}