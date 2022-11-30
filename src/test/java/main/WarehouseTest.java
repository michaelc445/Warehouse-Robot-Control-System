package main;
import graph.Point;
import graph.WarehouseGraph;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.Parser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class WarehouseTest {
    private static Warehouse warehouse;
    private static final Point START_POINT = new Point(0, 0);
    private static final Point END_POINT = new Point(0, 6);
    @BeforeAll
    public  static void setUp(){
        Parser mapParser =  new Parser();
        int[][] map = mapParser.parseMapLayout("warehouse_map.csv");
        WarehouseGraph graph = new WarehouseGraph(map, START_POINT, END_POINT);
        warehouse  = new Warehouse(graph);
    }
    @Test
    void addItem() {
        warehouse.addItem("test1", new Point(1,1),1);
        assertEquals(new Point(1,1),warehouse.getItemLocation("test1"));
    }

    @Test
    void getItemNames() {

        warehouse.addItem("test1", new Point(1,1),1);
        warehouse.addItem("test2", new Point(2,2),1);
        List<String> expected =new ArrayList<>();
        expected.add("test1 1kg");
        expected.add("test2 1kg");
        List<String> got = warehouse.getItemNames();
        Collections.sort(got);
        Collections.sort(expected);
        assertEquals(expected,got);
    }

    @Test
    void getItemLocation() {
        warehouse.addItem("test1", new Point(1,1),1);
        assertEquals( new Point(1,1),warehouse.getItemLocation("test1"));
    }

    @Test
    void removeItemByName() {
        warehouse.addItem("test1", new Point(1,1),1);
        warehouse.addItem("test2", new Point(2,2),1);
        warehouse.removeItemByName("test2");
        List<String> expected   = new ArrayList<>();
        expected.add("test1 1kg");
        assertEquals(expected,warehouse.getItemNames());
    }
}
