package display;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class VisualizationTool extends JPanel {

    //private int[][] path = {{0,0},{0,1},{0,2},{0,3},{1,3},{2,3},{3,3},{4,3},{3,3},{2,3},{1,3},{0,3},{0,2},{0,1},{0,0} };

    //Map and path hard coded for testing
    private int[][] path = {{0,0},{0,1},{0,2},{0,3},{1,3},{2,3},{3,3},{4,3},{3,3},{3,4},{3,4},{3,5},{3,6},{4,6},{5,6},{6,6} };
    private int[][] map = {
            {1,1}, {2,1} , {4,1} ,{5,1},
            {1,2}, {2,2} , {4,2} ,{5,2},
            {1,4}, {2,4} , {4,4} ,{5,4},
            {1,5}, {2,5} , {4,5} ,{5,5},

    };

    private int[] chargingAreaLocation = {0,0};
    private int[] dispatchAreaLocation = {6,6};
    private int mapHeight = 20;
    private int mapWidth = 20;
    private int step;
    private int spacing = 1;
    private int boxSize = 25;
    private Timer timer;

    public VisualizationTool(){
        //timer for each update of the paths progression
        setTimer(new Timer(500, new StepListener()));
        getTimer().start();
    }
    public void paintComponent(Graphics g){

        //create white background
        g.setColor(Color.WHITE);
        g.fillRect(0,0,getBoxSize()*getMapWidth(),getBoxSize()*getMapHeight());
        // create gray spaces to represent the empty space
        g.setColor(Color.GRAY);
        for (int i = 0; i < getMapWidth(); i++){
            for (int j = 0; j < getMapHeight(); j++){
                g.fillRect(getSpacing() + i* getBoxSize(),
                        getSpacing() + j* getBoxSize(),
                        getBoxSize() - getSpacing(),
                        getBoxSize() - getSpacing());
            }
        }
        // creates black spaces to represent the selves
        g.setColor(Color.BLACK);
        for (int[] ints : getMap()) {
            //System.out.println(map[i][0]);
            g.fillRect(getSpacing() + ints[0] * getBoxSize(),
                    getSpacing() + ints[1] * getBoxSize(),
                    getBoxSize() - getSpacing(),
                    getBoxSize() - getSpacing());
        }

        // creates yellow spaces to highlight the path the robot will take
        g.setColor(Color.YELLOW);
        for (int[] ints : getPath()) {
            g.fillRect(getSpacing() + ints[0] * getBoxSize(),
                    getSpacing() + ints[1] * getBoxSize(),
                    getBoxSize() - getSpacing(),
                    getBoxSize() - getSpacing());
        }
        // creates orange space to represent the charging area
        g.setColor(Color.ORANGE);
        g.fillRect(getSpacing() + getChargingAreaLocation()[0] * getBoxSize(),
                getSpacing() + getChargingAreaLocation()[1] * getBoxSize(),
                getBoxSize() - getSpacing(),
                getBoxSize() - getSpacing());

        // creates blue space to represent the dispatch area
        g.setColor(Color.BLUE);
        g.fillRect(getSpacing() + getDispatchAreaLocation()[0] * getBoxSize(),
                getSpacing() + getDispatchAreaLocation()[1] * getBoxSize(),
                getBoxSize() - getSpacing(),
                getBoxSize() - getSpacing());

        // creates red space to represent the robot's current location
        g.setColor(Color.RED);
        g.fillRect(getSpacing() + getPath()[getStep()][0] * getBoxSize(),
                getSpacing() + getPath()[getStep()][1] * getBoxSize(),
                getBoxSize() - getSpacing(),
                getBoxSize() - getSpacing());
        System.out.println("Step "+ getStep());

    }
    // getters and setters
    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
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

    public int[][] getPath() {
        return path;
    }

    public void setPath(int[][] path) {
        this.path = path;
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
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

    public int[] getChargingAreaLocation() {
        return chargingAreaLocation;
    }

    public void setChargingAreaLocation(int[] chargingAreaLocation) {
        this.chargingAreaLocation = chargingAreaLocation;
    }

    public int[] getDispatchAreaLocation() {
        return dispatchAreaLocation;
    }

    public void setDispatchAreaLocation(int[] dispatchAreaLocation) {
        this.dispatchAreaLocation = dispatchAreaLocation;
    }


    private class StepListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(getStep() < getPath().length-1){
                setStep(getStep() + 1);
                repaint();

            }
            else{
                //step = 0;
                getTimer().stop();
            }

        }
    }


}