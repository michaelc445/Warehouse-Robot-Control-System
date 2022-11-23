package graph;

import java.util.ArrayList;
import java.util.List;

public class Path {
    private final List<Point> path;
    private final int cost;
    public Path(List<Point> path){
        this.path =  path;
        this.cost = path.size()-1;
    }
    public   int getCost(){
        return this.cost;
    }
    public List<Point> getPath(){
        return this.path;
    }

    public  Path  reversed(){
        ArrayList<Point> newPath = new ArrayList<>();
        for (int i = this.path.size()-1;i>=0;i--){
            newPath.add(this.path.get(i));
        }
        return  new Path(newPath);
    }
    @Override
    public boolean equals(Object other){
        if (this==other){
            return true;
        }
        if(other == null || getClass() != other.getClass()){
            return false;
        }
        Path o = (Path) other;
        if (o.getCost() != this.cost){
            return false;
        }
        for (int i =0; i < this.path.size();i++){
            if (!this.path.get(i).equals(o.getPath().get(i))){
                return false;
            }
        }
        return true;
    }
    public String toString(){
        StringBuilder  result  =  new StringBuilder();
        for (Point p :  this.path){
            result.append(p.toString());
            result.append("->");
        }
        result.deleteCharAt(result.length()-1);
        result.deleteCharAt(result.length()-1);
        return result.toString();
    }

}
