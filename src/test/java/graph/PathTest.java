package graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PathTest {

    @Test
    void getCost() {
        Point  p1  =  new Point(0,0);
        Point  p2  =  new Point(0,1);
        ArrayList<Point> points = new ArrayList<>();
        points.add(p1);
        points.add(p2);
        Path newPath  = new Path(points);
        assertEquals(newPath.getCost(),1);
    }

    @Test
    void getPath() {
        Point  p1  =  new Point(0,0);
        Point  p2  =  new Point(0,1);
        ArrayList<Point> points = new ArrayList<>();
        points.add(p1);
        points.add(p2);
        Path newPath  = new Path(points);
        assertEquals(newPath.getPath(),points);
    }

    @Test
    void reversed() {
        Point  p1  =  new Point(0,0);
        Point  p2  =  new Point(0,1);
        ArrayList<Point> points = new ArrayList<>();
        points.add(p1);
        points.add(p2);
        Path newPath  = new Path(points);
        ArrayList<Point>  path1  =  newPath.getPath();
        Path  reversedPath = newPath.reversed();
        ArrayList<Point>  path2  =  reversedPath.getPath();
        for(int i =0; i < path1.size();i++){
            assertEquals(path1.get(i),path2.get(path2.size()-1-i));
        }
        assertEquals(newPath.getCost(),reversedPath.getCost());
    }

    @Test
    void testEquals() {
        Point  p1  =  new Point(0,0);
        Point  p2  =  new Point(0,1);
        Point  p3  =  new Point(0,0);
        Point  p4  =  new Point(0,1);
        ArrayList<Point> points1 = new ArrayList<>();
        points1.add(p1);
        points1.add(p2);
        Path path1  = new Path(points1);
        ArrayList<Point> points2 = new ArrayList<>();
        points2.add(p3);
        points2.add(p4);
        Path path2  = new Path(points2);
        assertEquals(path1,path2);
        ArrayList<Point>  points3 = new ArrayList<>();
        points3.add(new Point(0,5));
        points3.add(new Point(0,6));
        Path path3 = new Path(points3);
        assertNotEquals(path1,path3);
    }

    @Test
    void testToString() {
    }
}