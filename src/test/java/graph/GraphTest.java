package graph;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    @Test
    void parseFile() throws Exception{
        String fileName = "src/test/java/TestMaps/testMap.csv";
        Graph testGraph  = new Graph(fileName,true);
        int[][] expected = {
                {0,0,0,0,0,0,0},
                {0,1,1,0,1,1,0},
                {0,1,1,0,1,1,0},
                {0,0,0,0,0,0,0},
                {0,1,1,0,1,1,0},
                {0,1,1,0,1,1,0},
                {0,0,0,0,0,0,0}
        };
        int[][] got = testGraph.parseFile(fileName);
        for  (int i=0; i < expected.length;  i++){
            for (int  j=0;  j < expected[i].length;  j++){
                  assertEquals(expected[i][j], got[i][j]);
            }
        }
    }


    @Test
    void createUndirected()   throws Exception {
        String fileName = "src/test/java/TestMaps/testMap.csv";
        Graph  testGraph  = new Graph(fileName,false);
        int[][] expected  = {{2,3,3,3,3,3,2},
                {2,1,1,2,1,1,2},
                {2,1,1,2,1,1,2},
                {3,4,4,4,4,4,3},
                {2,1,1,2,1,1,2},
                {2,1,1,2,1,1,2},
                {2,3,3,3,3,3,2}};
        for(int i =0; i< expected.length; i++) {
            for (int j = 0; j < expected[i].length; j++) {
                Point  location  = new Point(j,i);
                assertEquals(testGraph.getVertices().get(location).getDegree(),expected[i][j]);
            }
        }
    }

    @Test
    void getVertices() throws Exception {
        String fileName = "src/test/java/TestMaps/testMap.csv";
        Graph  testGraph  = new Graph(fileName,false);
        int[][] map = testGraph.getMap();
        int  expectedSize = map.length*map[0].length;
        HashMap<Point, Node> vertices = testGraph.getVertices();
        assertEquals(vertices.size(),expectedSize);
        for  (int i =0; i < map.length;i++){
            for(int j =0; j < map[i].length;j++){
                Point location = new Point(j,i);
                assertNotNull(vertices.get(location));
            }
        }
    }

    @Test
    void addNode() throws Exception {
        String fileName = "src/test/java/TestMaps/testMap.csv";
        Graph  testGraph  = new Graph(fileName,false);
        int size = testGraph.getVertices().size();
        Point p1 = new Point(0,0);
        Node node = testGraph.getNode(p1);
        testGraph.addNode(node,-1,-1,4);
        assertEquals(testGraph.getVertices().size(),size+1);
        assertNotNull(testGraph.getNode(new Point(-1,-1)));
    }

    @Test
    void dijkstra() throws Exception {
        String fileName = "src/test/java/TestMaps/testMap.csv";
        Graph  testGraph  = new Graph(fileName,false);
        Point p1 = new Point(0,0);
        Point p2 = new Point(5,4);
        Node node1 = testGraph.getNode(p1);
        Node node2 =  testGraph.getNode(p2);
        ArrayList<Point>  got  = testGraph.dijkstra(node1,node2);
        ArrayList<Point> expected   =  new ArrayList<>();
        expected.add(new   Point(0,0));
        expected.add(new   Point(1,0));
        expected.add(new   Point(2,0));
        expected.add(new   Point(3,0));
        expected.add(new   Point(3,1));
        expected.add(new   Point(3,2));
        expected.add(new   Point(3,3));
        expected.add(new   Point(4,3));
        expected.add(new   Point(5,3));
        expected.add(new   Point(5,4));
        assertEquals(expected,got);
        //node not in graph
        Node node3 = testGraph.getNode(new Point(-2,-3));
        got = testGraph.dijkstra(node1,node3);
        assertNull(got);
    }

    @Test
    void  getPathMultipleNodes() throws Exception {
        String fileName = "src/test/java/TestMaps/testMap.csv";
        Graph  testGraph  = new Graph(fileName,false);
        Node start= testGraph.getNode(new Point(0,0));
        Node end = testGraph.getNode(new Point(0,5));
        ArrayList<Point> test = new ArrayList<>();
        test.add(new Point(0,2));
        test.add(new Point(0,4));
        test.add(new Point(0,6));
        ArrayList<ArrayList<Point>> got  = testGraph.getPathMultipleNodes(start,end,test);
        ArrayList<ArrayList<Point>> expected = new ArrayList<>();
        ArrayList<Point> temp1 = new  ArrayList<>();
        ArrayList<Point> temp2 = new  ArrayList<>();
        ArrayList<Point> temp3 = new  ArrayList<>();
        ArrayList<Point> temp4 = new  ArrayList<>();
        temp1.add(new Point(0,0));
        temp1.add(new Point(0,1));
        temp1.add(new Point(0,2));
        temp2.add(new Point(0,2));
        temp2.add(new Point(0,3));
        temp2.add(new Point(0,4));
        temp3.add(new Point(0,4));
        temp3.add(new Point(0,5));
        temp3.add(new Point(0,6));
        temp4.add(new Point(0,6));
        temp4.add(new Point(0,5));
        expected.add(temp1);
        expected.add(temp2);
        expected.add(temp3);
        expected.add(temp4);
        assertEquals(expected,got);
    }


    @Test
    void distanceBetweenAllShelves() throws Exception {
        String fileName = "src/test/java/TestMaps/testMap.csv";
        Graph  testGraph  = new Graph(fileName,false);
        HashMap<Point,HashMap<Point,Integer>> result = testGraph.distanceBetweenAllShelves();
        ArrayList<Point>  shelves = testGraph.getShelves();
        for (int i =0; i < shelves.size();i++){
            assertTrue(result.containsKey(shelves.get(i)));
            HashMap<Point,Integer> temp = result.get(shelves.get(i));
            for (int j =0; j < shelves.size();j++){
                if(i == j){
                    continue;
                }
                assertTrue(temp.containsKey(shelves.get(j)));
            }
            assertTrue(result.containsKey(testGraph.getStartPoint()));
            assertTrue(result.containsKey(testGraph.getEndPoint()));
        }
        HashMap<Point,Integer> start = result.get(testGraph.getStartPoint());
        HashMap<Point,Integer> end = result.get(testGraph.getEndPoint());
        assertNotNull(start);
        assertNotNull(end);
        for (Point shelf : shelves) {
            assertTrue(start.containsKey(shelf));
            assertTrue(end.containsKey(shelf));
        }
        assertTrue(start.containsKey(testGraph.getEndPoint()));
        assertTrue(end.containsKey(testGraph.getStartPoint()));
    }

    @Test
    void shortestOrderPath() throws Exception {
        String fileName = "src/test/java/TestMaps/testMap.csv";
        Graph  testGraph  = new Graph(fileName,false);
        //TODO  finish  test
        Node start = testGraph.getNode(testGraph.getStartPoint());
        Node end = testGraph.getNode(testGraph.getEndPoint());
        List<Point>  shelves  = testGraph.getShelves();

        ArrayList<Point> items = new ArrayList<>();
        items.add(shelves.get(0));
        items.add(shelves.get(1));
        items.add(shelves.get(2));
        items.add(shelves.get(3));
        items.add(shelves.get(4));
        List<Point> got= testGraph.shortestOrderPath(start,end,items);
        assertNotNull(got);

    }
}