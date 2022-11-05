import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    @Test
    void parseFile() throws Exception{
        //ArrayList<String[]>  expected = new ArrayList<>();
        String fileName = "Test/TestMaps/testMap.csv";
        Graph  testGraph  = new Graph(fileName,true);
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
    void createDirected() {
    }

    @Test
    void createUndirected()   throws Exception {
        String fileName = "Test/TestMaps/testMap.csv";
        Graph  testGraph  = new Graph(fileName,false);
        int[][] got = testGraph.parseFile(fileName);
        for(int i =0; i< got.length; i++) {
            for (int j = 0; j < got[i].length; j++) {
                String name = String.format("%d,%d",i,j);
                System.out.print(testGraph.getVertices().get(name).getDegree()+",");
            }
            System.out.print("\n");
        }
    }

    @Test
    void getVertices() {
    }

    @Test
    void addNode() {
    }


}