package graph;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.Parser;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseGraphTest {
    private static final Point START_POINT = new Point(0, 0);
    private static final Point END_POINT = new Point(0, 6);
    private static int[][] map;
    private static WarehouseGraph graph;
    @BeforeAll
    public static void  init() throws IOException {
        Parser mapParser =  new Parser();
        map  = mapParser.parseMapLayout("src/test/java/TestMaps/testMap.csv");
        graph =  new WarehouseGraph(map,START_POINT,END_POINT);
    }
    @Test
    void getVertices() {
        Map<Point,Node> vertices  = graph.getVertices();
        for(int i =0; i < map.length;i++){
            for(int j =0; j < map[i].length;j++){
                Point newPoint = new  Point(j,i);
                assertTrue(vertices.containsKey(newPoint));
            }
        }
    }

    @Test
    void getNodeByLocation() {
        Map<Point,Node>  vertices = graph.getVertices();
        for(int i =0; i < map.length;i++){
            for(int j =0; j < map[i].length;j++){
                Point newPoint = new  Point(j,i);
                assertTrue(vertices.containsKey(newPoint));
                assertEquals(vertices.get(newPoint).getLocation(), newPoint);
            }
        }
    }

    @Test
    void getStartNode() {
        assertEquals(graph.getStartNode(), graph.getNodeByLocation(START_POINT));
    }

    @Test
    void getEndNode() {
        assertEquals(graph.getEndNode(), graph.getNodeByLocation(END_POINT));
    }

    @Test
    void getWarehouseMapLayout() {
        int[][]  got = graph.getWarehouseMapLayout();
        int[][] expected = {
                {0,0,0,0,0,0,0},
                {0,1,1,0,1,1,0},
                {0,1,1,0,1,1,0},
                {0,0,0,0,0,0,0},
                {0,1,1,0,1,1,0},
                {0,1,1,0,1,1,0},
                {0,0,0,0,0,0,0}
        };
        assertEquals(got.length,expected.length);
        for  (int i=0; i < expected.length;  i++){
            assertEquals(got[i].length,expected[i].length);
            for (int  j=0;  j < expected[i].length;  j++){
                  assertEquals(expected[i][j], got[i][j]);
            }
        }

    }
}