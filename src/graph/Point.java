package graph;

public class Point {
    private final int x;
    private final int y;

    public Point(int x, int y){
        this.x = x;
        this.y=y;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    @Override
    public boolean equals(Object other){
        if (this==other)return true;
        if(other.getClass()!=getClass())return false;
        Point o = (Point) other;
        return this.x==o.x && this.y==o.y;
    }
    public String toString(){
        return String.format("%d,%d",this.x,this.y);
    }
    @Override
    public  int hashCode(){
        String res = String.format("%d,%d",this.x,this.y);
        return res.hashCode();
    }
}
