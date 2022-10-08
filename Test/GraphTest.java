import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    @Test
    void parseFile() throws Exception{
        ArrayList<String[]>  expected = new ArrayList<>();
        String fileName = "Test/TestMaps/testMap.csv";
        Graph  testGraph  = new Graph(fileName,true);
        expected.add(new String[]{"0","0","0","0"});
        expected.add(new String[]{"0","item1","item2","0"});
        expected.add(new String[]{"0","0","0","0"});
        ArrayList<String[]> got = testGraph.parseFile(fileName);
        for  (int i=0; i < expected.size();  i++){
            for (int  j=0;  j < expected.get(i).length;  j++){
                assertEquals(expected.get(i)[j], got.get(i)[j]);
            }
        }
    }
    @Test
    void createDirected() {
    }

    @Test
    void createUndirected() {
    }
}