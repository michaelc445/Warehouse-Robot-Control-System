package display;

import graph.Path;
import graph.Point;
import graph.WarehouseGraph;
import main.Warehouse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class VisualizationTool extends JPanel {
    private List<Path> path;
    private final List<Point> shelves;
    private final int[][] map;

    private Point chargingAreaLocation;
    private Point dispatchAreaLocation;
    private int mapHeight;
    private int mapWidth;
    private int step;
    private int curX;
    private int curY;
    private final int pathSize;
    private int spacing = 1;
    private int boxSize = 25;
    private Timer timer;
    private final List<graph.Point> order;

    public VisualizationTool(Warehouse warehouse, List<Path> path, List<Point> locationsToVisit){
        //timer for each update of the paths progression
        setTimer(new Timer(500, new StepListener()));
        getTimer().start();
        this.shelves = warehouse.getShelveLocations();

        this.path = path;
        this.order = locationsToVisit;
        int pathLength = 0;
        for (Path p : this.path){
            pathLength += p.getPath().size();
        }
        this.pathSize=pathLength;
        this.map = warehouse.getMapLayout();
        this.mapHeight  =  this.map.length;
        this.mapWidth = this.map[0].length;
        this.dispatchAreaLocation= warehouse.getDispatchedArea();
        this.chargingAreaLocation= warehouse.getChargingArea();

    }
    public void paintComponent(Graphics g){

        //create white background
        g.setColor(Color.WHITE);
        g.fillRect(0,0,getBoxSize()*getMapWidth(),getBoxSize()*getMapHeight());
        // create gray spaces to represent the empty space
        g.setColor(Color.GRAY);

        for (int i = 0; i < this.mapWidth; i++){
            for (int j = 0; j < this.mapHeight; j++){
                g.fillRect(getSpacing() + i* getBoxSize(),
                        getSpacing() + j* getBoxSize(),
                        getBoxSize() - getSpacing(),
                        getBoxSize() - getSpacing());
            }
        }
        // creates black spaces to represent the selves
        g.setColor(Color.BLACK);
        for (Point shelve: this.shelves) {
            //System.out.println(map[i][0]);
            g.fillRect(getSpacing() + shelve.getX() * getBoxSize(),
                    getSpacing() + shelve.getY() * getBoxSize(),
                    getBoxSize() - getSpacing(),
                    getBoxSize() - getSpacing());
        }

        // creates yellow spaces to highlight the path the robot will take
        g.setColor(Color.YELLOW);
        for (Path points : getPath()) {
            for(Point p : points.getPath()) {
                g.fillRect(getSpacing() + p.getX() * getBoxSize(),
                        getSpacing() + p.getY() * getBoxSize(),
                        getBoxSize() - getSpacing(),
                        getBoxSize() - getSpacing());
            }
        }
        // creates orange space to represent the charging area
        g.setColor(Color.ORANGE);
        g.fillRect(getSpacing() + getChargingAreaLocation().getX() * getBoxSize(),
                getSpacing() + getChargingAreaLocation().getY() * getBoxSize(),
                getBoxSize() - getSpacing(),
                getBoxSize() - getSpacing());

        // creates blue space to represent the dispatch area
        g.setColor(Color.BLUE);
        g.fillRect(getSpacing() + getDispatchAreaLocation().getX() * getBoxSize(),
                getSpacing() + getDispatchAreaLocation().getY() * getBoxSize(),
                getBoxSize() - getSpacing(),
                getBoxSize() - getSpacing());

        for(Point item:  this.order){
            g.fillRect(getSpacing() + item.getX() * getBoxSize(),
                    getSpacing() + item.getY() * getBoxSize(),
                    getBoxSize() - getSpacing(),
                    getBoxSize() - getSpacing());
        }
        // creates red space to represent the robot's current location
        g.setColor(Color.RED);
        g.fillRect(getSpacing() + getPath().get(this.curY).getPath().get(this.curX).getX() * getBoxSize(),
                getSpacing() + getPath().get(this.curY).getPath().get(this.curX).getY() * getBoxSize(),
                getBoxSize() - getSpacing(),
                getBoxSize() - getSpacing());

    }
    // getters and setters
    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
    public void setCurx(int x){
        if (x < this.path.get(this.curY).getPath().size()) {
            this.curX = x;
        }else{
            if (this.curY+1 < this.path.size()){
                this.curY +=1;
                this.curX=0;
            }
        }
    }
    public int getCurX(){

        return this.curX;
    }

    public int getSpacing() {
        return spacing;
    }
    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }
    public int getBoxSize() {
        return boxSize;
    }

    public void setBoxSize(int boxSize) {
        this.boxSize = boxSize;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public List<Path> getPath() {
        return path;
    }

    public void setPath(List<Path> path) {
        this.path = path;
    }

    public int[][] getMap() {
        return map;
    }



    public int getMapHeight() {
        return mapHeight;
    }

    public void setMapHeight(int mapHeight) {
        this.mapHeight = mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public void setMapWidth(int mapWidth) {
        this.mapWidth = mapWidth;
    }

    public Point getChargingAreaLocation() {
        return chargingAreaLocation;
    }

    public void setChargingAreaLocation(Point chargingAreaLocation) {
        this.chargingAreaLocation = chargingAreaLocation;
    }

    public Point getDispatchAreaLocation() {
        return dispatchAreaLocation;
    }

    public void setDispatchAreaLocation(Point dispatchAreaLocation) {
        this.dispatchAreaLocation = dispatchAreaLocation;
    }


    private class StepListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(getStep() < pathSize-1){
                setStep(getStep() + 1);
                setCurx(getCurX()+1);
                repaint();
            }
            else{
                //step = 0;
                getTimer().stop();
            }

        }
    }


}