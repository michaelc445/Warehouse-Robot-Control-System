package graph;

import main.Warehouse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.Parser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PathFinderTest {
    private  static WarehouseGraph graph;
    private static final Point START_POINT = new Point(0, 0);
    private static final Point END_POINT = new Point(0, 6);
    private  static  PathFinder pathFinder;

    @BeforeAll
    public static void setUp() throws IOException {
        Parser mapParser =  new Parser();
        int[][] map = mapParser.parseMapLayout("warehouse_map.csv");
        graph =  new WarehouseGraph(map,START_POINT,END_POINT);
        pathFinder = new PathFinder();
    }

    @Test
    void findShortestPath() {
        Warehouse warehouse = new Warehouse(graph);
        List<Point> shelves  = warehouse.getShelveLocations();
        ArrayList<Point> items = new ArrayList<>();
        items.add(shelves.get(5));
        items.add(shelves.get(10));
        items.add(shelves.get(6));
        items.add(shelves.get(15));
        items.add(shelves.get(8));
        List<Path> got= pathFinder.findShortestPath(warehouse,items);
        assertNotNull(got);
    }

    @Test
    void dijkstra() {
        PathFinder   p = new PathFinder();
        Point p1 = new Point(0,0);
        Point p2 = new Point(5,4);
        Node node1 = graph.getNodeByLocation(p1);
        Node node2 =  graph.getNodeByLocation(p2);
        Path  got  = p.dijkstra(node1,node2);
        ArrayList<Point> ex  =  new ArrayList<>();
        ex.add(new   Point(0,0));
        ex.add(new   Point(1,0));
        ex.add(new   Point(2,0));
        ex.add(new   Point(3,0));
        ex.add(new   Point(3,1));
        ex.add(new   Point(3,2));
        ex.add(new   Point(3,3));
        ex.add(new   Point(4,3));
        ex.add(new   Point(5,3));
        ex.add(new   Point(5,4));
        Path expected = new Path(ex);
        assertEquals(expected,got);
        //node not in graph
        Node node3 = graph.getNodeByLocation(new Point(-2,-3));
        got = p.dijkstra(node1,node3);
        assertNull(got);
    }
}