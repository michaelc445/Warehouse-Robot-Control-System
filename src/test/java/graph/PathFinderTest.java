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
    private  static final PathFinder pathFinder = new PathFinder();
    private static final Parser parser =  new Parser();
    private static final String MAP_FILE = "warehouse_map.csv";

    @Test
    void correctlyInitializedGraphAndListOfItems_findShortestPath_thenReturnShortestPathList() throws IOException {
        int[][] map = parser.parseMapLayout(MAP_FILE);
        graph =  new WarehouseGraph(map,START_POINT,END_POINT);
        List<Point> shelves  = new Warehouse(graph).getShelveLocations();

        ArrayList<Point> items = new ArrayList<>();
        items.add(shelves.get(5));
        items.add(shelves.get(10));

        List<Path> expected = List.of(
                new Path(List.of(
                        new Point(0, 0),
                        new Point(0,1),
                        new Point(0,2),
                        new Point(0, 3),
                        new Point(1, 3),
                        new Point(2,3),
                        new Point(2, 2))),
                new Path(List.of(
                        new Point(2, 2),
                        new Point(2,3),
                        new Point(3,3),
                        new Point(4,3),
                        new Point(4,4))),
                new Path(List.of(
                        new Point(4,4),
                        new Point(4,3),
                        new Point(3,3),
                        new Point(3,4),
                        new Point(3,5),
                        new Point(3,6),
                        new Point(2,6),
                        new Point(1,6),
                        new Point(0,6)
                ))
        );
        List<Path> actual = pathFinder.findShortestPath(graph ,items);

        assertEquals(expected, actual);
    }

    @Test
    void warehouseGraphIsNull_findShortestPath_thenThrowIllegalArgumentException() {
        WarehouseGraph graph = null;

        ArrayList<Point> items = new ArrayList<>();
        items.add(new Point(2, 2));
        items.add(new Point(4, 4));
        items.add(new Point(4, 2));
        items.add(new Point(5, 5));
        items.add(new Point (1, 4));

        assertThrows(IllegalArgumentException.class, () -> {
            pathFinder.findShortestPath(graph, items);
        });
    }

    @Test
    void noVerticesInWarehouseGraph_findShortestPath_thenThrowIllegalArgumentException() {
        WarehouseGraph graph = new WarehouseGraph(new int[0][0], START_POINT, END_POINT);

        ArrayList<Point> items = new ArrayList<>();
        items.add(new Point(2, 2));
        items.add(new Point(4, 4));
        items.add(new Point(4, 2));
        items.add(new Point(5, 5));
        items.add(new Point (1, 4));

        assertThrows(IllegalArgumentException.class, () -> {
            pathFinder.findShortestPath(graph, items);
        });
    }

    @Test
    void itemLocationListIsNULL_findShortestPath_thenThrowIllegalArgumentException() {
        ArrayList<Point> items = null;

        assertThrows(IllegalArgumentException.class, () -> {
            pathFinder.findShortestPath(graph, items);
        });
    }

    @Test
    void itemLocationListIsEmpty_findShortestPath_thenThrowIllegalArgumentException() {
        ArrayList<Point> items = new ArrayList<>();

        assertThrows(IllegalArgumentException.class, () -> {
            pathFinder.findShortestPath(graph, items);
        });
    }

    @Test
    void noStartingLocationInDistancePairMap_findShortestPath_thenThrowIllegalArgumentException() throws IOException {
        Point start = null;
        WarehouseGraph graph = new WarehouseGraph(new int[0][0], start, END_POINT);

        ArrayList<Point> items = new ArrayList<>();
        items.add(new Point(2, 2));
        items.add(new Point(4, 4));
        items.add(new Point(4, 2));
        items.add(new Point(5, 5));
        items.add(new Point (1, 4));

        assertThrows(IllegalArgumentException.class, () -> {
            pathFinder.findShortestPath(graph, items);
        });
    }

    @Test
    void dijkstra() throws IOException {
        int[][] map = parser.parseMapLayout(MAP_FILE);
        graph =  new WarehouseGraph(map,START_POINT,END_POINT);

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