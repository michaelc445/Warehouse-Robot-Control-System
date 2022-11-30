package display;

import graph.Path;
import graph.Point;
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
    static int boxSize = 24;
    private Timer timer;
    private final List<graph.Point> order;


    private Image robotImg;
    private Image axeImg;
    private Image hammerImg;
    private Image hardHatImg;
    private Image screwImg;
    private Image wrenchImg;
    private Image torchImg;
    private Image nailsImg;
    private Image brickImg;
    private Image batteryImg;
    private Image drillImg;
    private Image screwdriverImg;
    private Image sawImg;
    private Image crowbarImg;
    private Image pickaxeImg;
    private Image wiresImg;
    private Image chainsawImg;




    public VisualizationTool(Warehouse warehouse, List<Path> path, List<Point> locationsToVisit, List<Image> images){
        setRobotImg(images.get(0));
        setHammerImg(images.get(1));
        setScrewImg(images.get(2));
        setHardHatImg(images.get(3));
        setAxeImg(images.get(4));
        setWrenchImg(images.get(5));
        setTorchImg(images.get(6));
        setNailsImg(images.get(7));
        setBrickImg(images.get(8));
        setBatteryImg(images.get(9));
        setDrillImg(images.get(10));
        setScrewdriverImg(images.get(11));
        setSawImg(images.get(12));
        setCrowbarImg(images.get(13));
        setPickaxeImg(images.get(14));
        setWiresImg(images.get(15));
        setChainsawImg(images.get(16));

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
                g.fillRect(spacing + i* getBoxSize(),
                        spacing + j* getBoxSize(),
                        getBoxSize() - spacing,
                        getBoxSize() - spacing);
            }
        }
        // creates black spaces to represent the selves
        g.setColor(Color.BLACK);
        for (Point shelve: this.shelves) {
            g.fillRect(spacing + shelve.getX() * getBoxSize(),
                    spacing + shelve.getY() * getBoxSize(),
                    getBoxSize() - spacing,
                    getBoxSize() - spacing);
        }

        // creates yellow spaces to highlight the path the robot will take
        g.setColor(Color.YELLOW);
        for (Path points : getPath()) {
            for(Point p : points.getPath()) {
                g.fillRect(spacing + p.getX() * getBoxSize(),
                        spacing + p.getY() * getBoxSize(),
                        getBoxSize() - spacing,
                        getBoxSize() - spacing);
            }
        }
        // creates orange space to represent the charging area
        g.setColor(Color.ORANGE);
        g.fillRect(spacing + getChargingAreaLocation().getX() * getBoxSize(),
                spacing + getChargingAreaLocation().getY() * getBoxSize(),
                getBoxSize() - spacing,
                getBoxSize() - spacing);

        // creates blue space to represent the dispatch area
        g.setColor(Color.BLUE);
        g.fillRect(spacing + getDispatchAreaLocation().getX() * getBoxSize(),
                spacing + getDispatchAreaLocation().getY() * getBoxSize(),
                getBoxSize() - spacing,
                getBoxSize() - spacing);

        for(Point item:  this.order){
            g.fillRect(spacing + item.getX() * getBoxSize(),
                    spacing + item.getY() * getBoxSize(),
                    getBoxSize() - spacing,
                    getBoxSize() - spacing);


        }


        // creates red space to represent the robot's current location
        setRobotImg(getRobotImg().getScaledInstance(getBoxSize()-getSpacing(), getBoxSize()-getSpacing(), Image.SCALE_SMOOTH));
        g.setColor(Color.RED);
        g.fillRect(spacing + getPath().get(this.curY).getPath().get(this.curX).getX() * getBoxSize(),
                spacing + getPath().get(this.curY).getPath().get(this.curX).getY() * getBoxSize(),
                getBoxSize() - spacing,
                getBoxSize() - spacing);
        g.drawImage(getRobotImg(),getSpacing() + getPath().get(this.curY).getPath().get(this.curX).getX() * getBoxSize(),
                getSpacing() + getPath().get(this.curY).getPath().get(this.curX).getY() * getBoxSize(),
                this);




        //Hammer image (14,5)
        g.drawImage(getHammerImg(),getSpacing() + 14 * getBoxSize(),
                getSpacing() + 5 * getBoxSize(),
                this);
        // Screw image (3,19)
        g.drawImage(getScrewImg(),getSpacing() + 3 * getBoxSize(),
                getSpacing() + 19 * getBoxSize(),
                this);
        // Helmet (12,17)
        g.drawImage(getHardHatImg(),getSpacing() + 12 * getBoxSize(),
                getSpacing() + 17 * getBoxSize(),
                this);
        // Axe (13,23)
        g.drawImage(getAxeImg(),getSpacing() + 13 * getBoxSize(),
                getSpacing() + 23 * getBoxSize(),
                this);
        // Wrench (1,4)
        g.drawImage(getWrenchImg(),getSpacing() + getBoxSize(),
                getSpacing() + 4 * getBoxSize(),
                this);
        // Torch (35,19)
        g.drawImage(getTorchImg(),getSpacing() + 35 * getBoxSize(),
                getSpacing() + 19 * getBoxSize(),
                this);
        // Nails (7,16)
        g.drawImage(getNailsImg(),getSpacing() + 7 * getBoxSize(),
                getSpacing() + 16 * getBoxSize(),
                this);
        // Brick (15,20)
        g.drawImage(getBrickImg(),getSpacing() + 15 * getBoxSize(),
                getSpacing() + 20 * getBoxSize(),
                this);
        // Battery (1,29)
        g.drawImage(getBatteryImg(),getSpacing() + getBoxSize(),
                getSpacing() + 29 * getBoxSize(),
                this);
        // Drill (31,4)
        g.drawImage(getDrillImg(),getSpacing() + 31 * getBoxSize(),
                getSpacing() + 4 * getBoxSize(),
                this);
        // Screwdriver (25,26)
        g.drawImage(getScrewdriverImg(),getSpacing() + 25 * getBoxSize(),
                getSpacing() + 26 * getBoxSize(),
                this);
        // Saw (11,4)
        g.drawImage(getSawImg(),getSpacing() + 11 * getBoxSize(),
                getSpacing() + 4 * getBoxSize(),
                this);
        // Crowbar (32,11)
        g.drawImage(getCrowbarImg(),getSpacing() + 32 * getBoxSize(),
                getSpacing() + 11 * getBoxSize(),
                this);
        // Pickaxe (26,4)
        g.drawImage(getPickaxeImg(),getSpacing() + 26 * getBoxSize(),
                getSpacing() + 4 * getBoxSize(),
                this);
        // Wires (21,29)
        g.drawImage(getWiresImg(),getSpacing() + 21 * getBoxSize(),
                getSpacing() + 29 * getBoxSize(),
                this);
        // Chainsaw ()
        g.drawImage(getChainsawImg(),getSpacing() + 36 * getBoxSize(),
                getSpacing() + 2 * getBoxSize(),
                this);

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

    public void setBoxSize(int newSize) {
        boxSize = newSize;
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

    public Image getHammerImg() {
        return hammerImg;
    }

    public void setHammerImg(Image hammerImg) {
        this.hammerImg = hammerImg;
    }

    public Image getScrewImg() {
        return screwImg;
    }

    public void setScrewImg(Image screwImg) {
        this.screwImg = screwImg;
    }

    public Image getHardHatImg() {
        return hardHatImg;
    }

    public void setHardHatImg(Image hardHatImg) {
        this.hardHatImg = hardHatImg;
    }

    public Image getAxeImg() {
        return axeImg;
    }

    public void setAxeImg(Image axeImg) {
        this.axeImg = axeImg;
    }

    public Image getWrenchImg() {
        return wrenchImg;
    }

    public void setWrenchImg(Image wrenchImg) {
        this.wrenchImg = wrenchImg;
    }

    public Image getRobotImg() {
        return robotImg;
    }

    public void setRobotImg(Image robotImg) {
        this.robotImg = robotImg;
    }

    public Image getTorchImg() {
        return torchImg;
    }

    public void setTorchImg(Image torchImg) {
        this.torchImg = torchImg;
    }

    public Image getNailsImg() {
        return nailsImg;
    }

    public void setNailsImg(Image nailsImg) {
        this.nailsImg = nailsImg;
    }

    public Image getBrickImg() {
        return brickImg;
    }

    public void setBrickImg(Image brickImg) {
        this.brickImg = brickImg;
    }

    public Image getBatteryImg() {
        return batteryImg;
    }

    public void setBatteryImg(Image batteryImg) {
        this.batteryImg = batteryImg;
    }

    public Image getDrillImg() {
        return drillImg;
    }

    public void setDrillImg(Image drillImg) {
        this.drillImg = drillImg;
    }

    public Image getScrewdriverImg() {
        return screwdriverImg;
    }

    public void setScrewdriverImg(Image screwdriverImg) {
        this.screwdriverImg = screwdriverImg;
    }

    public Image getSawImg() {
        return sawImg;
    }

    public void setSawImg(Image sawImg) {
        this.sawImg = sawImg;
    }

    public Image getCrowbarImg() {
        return crowbarImg;
    }

    public void setCrowbarImg(Image crowbarImg) {
        this.crowbarImg = crowbarImg;
    }

    public Image getPickaxeImg() {
        return pickaxeImg;
    }

    public void setPickaxeImg(Image pickaxeImg) {
        this.pickaxeImg = pickaxeImg;
    }

    public Image getChainsawImg() {
        return chainsawImg;
    }

    public void setChainsawImg(Image chainsawImg) {
        this.chainsawImg = chainsawImg;
    }

    public Image getWiresImg() {
        return wiresImg;
    }

    public void setWiresImg(Image wiresImg) {
        this.wiresImg = wiresImg;
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
