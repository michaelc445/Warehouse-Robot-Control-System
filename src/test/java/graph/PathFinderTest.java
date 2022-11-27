package graph;

import main.Warehouse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import stock.ItemOrder;
import util.Parser;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PathFinderTest {
    private  static WarehouseGraph graph;
    private static List<Point> shelves;
    private static final Point START_POINT = new Point(0, 0);
    private static final Point END_POINT = new Point(0, 6);
    private  static final PathFinder pathFinder = new PathFinder();
    private static final Parser parser =  new Parser();
    private static final String MAP_FILE = "warehouse_map.csv";
    private static int[][] map;
    @BeforeAll
    static void setUp() {
        map = parser.parseMapLayout(MAP_FILE);
        graph =  new WarehouseGraph(map,START_POINT,END_POINT);
        shelves  = new Warehouse(graph).getShelveLocations();
    }
    @TestFactory
    Stream<DynamicTest> findShortestPathTest(){
        record TestCase(String name, Class<IllegalArgumentException> e, WarehouseGraph g, List<ItemOrder> items, List<Path> expected){
            public void check(){
                if (e != null) {
                    assertThrows(e, () -> pathFinder.findShortestPath(g, items));
                }else {
                    assertArrayEquals(expected.toArray(), pathFinder.findShortestPath(g, items).toArray(), name);
                }
            }
        }
        Stream<TestCase> testCases = Stream.of(
                new TestCase(
                        "no vertices in graph",
                        IllegalArgumentException.class,
                        new WarehouseGraph(new int[0][0], START_POINT, END_POINT),
                        List.of(
                                new ItemOrder("itemA",new Point(2,2),1,1),
                                new ItemOrder("itemB",new Point(3,4),1,1)
                        ),
                        null
                ),
                new TestCase(
                        "end point is null",
                        IllegalArgumentException.class,
                        new WarehouseGraph(map, START_POINT, null),
                        List.of(
                                new ItemOrder("itemA",new Point(2,2),1,1),
                                new ItemOrder("itemB",new Point(3,4),1,1)
                        ),
                        null
                ),
                new TestCase(
                        "start point is null",
                        IllegalArgumentException.class,
                        new WarehouseGraph(map, null, END_POINT),
                        List.of(
                                new ItemOrder("itemA",new Point(2,2),1,1),
                                new ItemOrder("itemB",new Point(3,4),1,1)
                        ),
                        null
                ),
                new TestCase(
                        "warehouse graph null",
                        IllegalArgumentException.class,
                        null,
                        List.of(
                                new ItemOrder("itemA",new Point(2,2),1,1),
                                new ItemOrder("itemB",new Point(3,4),1,1)
                        ),
                        null
                ),
                new TestCase(
                        "item list is null",
                        IllegalArgumentException.class,
                        graph,
                        null,
                        null
                ),
                new TestCase(
                        "success",
                        null,
                        graph,
                        List.of(
                                new ItemOrder("itemA",shelves.get(5),1,1),
                                new ItemOrder("itemB",shelves.get(10),1,1)
                        ),

                        List.of(
                            new Path(List.of(
                                    new Point(0, 0),
                                    new Point(0,1),
                                    new Point(0,2),
                                    new Point(0, 3),
                                    new Point(1, 3),
                                    new Point(2,3),
                                    new Point(2, 2)
                                )
                            ),
                            new Path(List.of(
                                    new Point(2, 2),
                                    new Point(2,3),
                                    new Point(3,3),
                                    new Point(4,3),
                                    new Point(4,4)
                                )
                            ),
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
                                )
                            )
                        )
                )


        );
        return DynamicTest.stream(testCases.iterator(), TestCase::name, TestCase::check);

    }

    @TestFactory
    Stream<DynamicTest> dijkstraTest(){
        record TestCase(String name, WarehouseGraph g,Point start, Point end, Path expected){
            public void check(){
                assertEquals(expected, pathFinder.dijkstra(g.getNodeByLocation(start),g.getNodeByLocation(end)));
            }
        }
        Stream<TestCase> testCases = Stream.of(
                new TestCase(
                        "start node not in graph",
                        graph,
                        new Point(-1,-3),
                        new Point(1,1),
                        null
                ),
                new TestCase(
                        "end node not in graph",
                        graph,
                        new Point(0,0),
                        new Point(-1,-5),
                        null
                ),
                new TestCase(
                        "success",
                        graph,
                        new Point(0,0),
                        new Point(5,4),
                        new Path(List.of(
                                new   Point(0,0),
                                new   Point(1,0),
                                new   Point(2,0),
                                new   Point(3,0),
                                new   Point(3,1),
                                new   Point(3,2),
                                new   Point(3,3),
                                new   Point(4,3),
                                new   Point(5,3),
                                new   Point(5,4)
                        ))
                )
        );
        return DynamicTest.stream(testCases.iterator(), TestCase::name, TestCase::check);
    }

    @TestFactory
    Stream<DynamicTest> accountForWeight() {
        record TestCase(String name,List<Point>optimalOrder,List<ItemOrder> orders,int maxWeight,Point endPoint,List<Point>expected){
            public void check(){
                List<Point> got = pathFinder.accountForWeight(optimalOrder,orders,maxWeight,endPoint);

                assertArrayEquals(expected.toArray(),got.toArray(),"got: "+new Path(got));
            }
        }
        Stream<TestCase> testCases = Stream.of(
                new TestCase(
                        "No change",
                        List.of(
                                new Point(1,1),
                                new Point(2,2)
                        ),
                        List.of(
                          new ItemOrder("itemA",new Point(1,1),1,1),
                                new ItemOrder("itemA",new Point(2,2),1,1)
                        ),
                        10,
                        graph.getEndNode().getLocation(),
                        List.of(
                                new Point(1,1),
                                new Point(2,2)
                        )

                ),
                new TestCase(
                        "full order weight",
                        List.of(
                                new Point(1,1),
                                new Point(2,2)
                        ),
                        List.of(
                                new ItemOrder("itemA",new Point(1,1),9,1),
                                new ItemOrder("itemA",new Point(2,2),1,1)
                        ),
                        10,
                        graph.getEndNode().getLocation(),
                        List.of(
                                new Point(1,1),
                                new Point(2,2)
                        )

                ),
                new TestCase(
                        "1 extra item ",
                        List.of(
                                new Point(1,1),
                                new Point(2,2)
                        ),
                        List.of(
                                new ItemOrder("itemA",new Point(1,1),10,1),
                                new ItemOrder("itemA",new Point(2,2),1,1)
                        ),
                        10,
                        graph.getEndNode().getLocation(),
                        List.of(
                                new Point(1,1),
                                graph.getEndNode().getLocation(),
                                new Point(2,2)
                        )

                ),
                new TestCase(
                        "multiple trips",
                        List.of(
                                new Point(1,1),
                                new Point(2,2)
                        ),
                        List.of(
                                new ItemOrder("itemA",new Point(1,1),10,3),
                                new ItemOrder("itemA",new Point(2,2),1,7)
                        ),
                        10,
                        graph.getEndNode().getLocation(),
                        List.of(
                                new Point(1,1),
                                graph.getEndNode().getLocation(),
                                new Point(1,1),
                                graph.getEndNode().getLocation(),
                                new Point(1,1),
                                graph.getEndNode().getLocation(),
                                new Point(1,1),
                                new Point(2,2)
                        )

                ),
                new TestCase(
                        "multiple trips with extra",
                        List.of(
                                new Point(1,1),
                                new Point(2,2)
                        ),
                        List.of(
                                new ItemOrder("itemA",new Point(1,1),10,3),
                                new ItemOrder("itemA",new Point(2,2),1,9)
                        ),
                        10,
                        graph.getEndNode().getLocation(),
                        List.of(
                                new Point(1,1),
                                graph.getEndNode().getLocation(),
                                new Point(1,1),
                                graph.getEndNode().getLocation(),
                                new Point(1,1),
                                graph.getEndNode().getLocation(),
                                new Point(1,1),
                                new Point(2,2),
                                graph.getEndNode().getLocation(),
                                new Point(2,2)
                        )

                )

        );
        return DynamicTest.stream(testCases.iterator(), TestCase::name, TestCase::check);
    }
}