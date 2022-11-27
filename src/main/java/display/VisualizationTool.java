package display;

import graph.Path;
import graph.Point;
import main.Warehouse;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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




    public VisualizationTool(Warehouse warehouse, List<Path> path, List<Point> locationsToVisit){

        try {
            setRobotImg(ImageIO.read(new File("src/main/java/display/static/robot.png")));
            setHammerImg(ImageIO.read(new File("src/main/java/display/static/hammer.png")));
            setScrewImg(ImageIO.read(new File("src/main/java/display/static/screw.png")));
            setHardHatImg(ImageIO.read(new File("src/main/java/display/static/hardHat.png")));
            setAxeImg(ImageIO.read(new File("src/main/java/display/static/axe.png")));
            setWrenchImg(ImageIO.read(new File("src/main/java/display/static/wrench.png")));
            setTorchImg(ImageIO.read(new File("src/main/java/display/static/torch.png")));
            setNailsImg(ImageIO.read(new File("src/main/java/display/static/nails.png")));
            setBrickImg(ImageIO.read(new File("src/main/java/display/static/brick.png")));
            setBatteryImg(ImageIO.read(new File("src/main/java/display/static/battery.png")));
            setDrillImg(ImageIO.read(new File("src/main/java/display/static/drill.png")));
            setScrewdriverImg(ImageIO.read(new File("src/main/java/display/static/screwdriver.png")));
            setSawImg(ImageIO.read(new File("src/main/java/display/static/saw.png")));
            setCrowbarImg(ImageIO.read(new File("src/main/java/display/static/crowbar.png")));
            setPickaxeImg(ImageIO.read(new File("src/main/java/display/static/pickaxe.png")));
            setWiresImg(ImageIO.read(new File("src/main/java/display/static/wires.png")));
            setChainsawImg(ImageIO.read(new File("src/main/java/display/static/chainsaw.png")));


        } catch (IOException ex) {
            // handle exception...
        }

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

        setHammerImg(getHammerImg().getScaledInstance(getBoxSize()-getSpacing(), getBoxSize()-getSpacing(), Image.SCALE_SMOOTH));
        setScrewImg(getScrewImg().getScaledInstance(getBoxSize()-getSpacing(), getBoxSize()-getSpacing(), Image.SCALE_SMOOTH));
        setHardHatImg(getHardHatImg().getScaledInstance(getBoxSize()-getSpacing(), getBoxSize()-getSpacing(), Image.SCALE_SMOOTH));
        setAxeImg(getAxeImg().getScaledInstance(getBoxSize()-getSpacing(), getBoxSize()-getSpacing(), Image.SCALE_SMOOTH));
        setWrenchImg(getWrenchImg().getScaledInstance(getBoxSize()-getSpacing(), getBoxSize()-getSpacing(), Image.SCALE_SMOOTH));
        setTorchImg(getTorchImg().getScaledInstance(getBoxSize()-getSpacing(), getBoxSize()-getSpacing(), Image.SCALE_SMOOTH));
        setNailsImg(getNailsImg().getScaledInstance(getBoxSize()-getSpacing(), getBoxSize()-getSpacing(), Image.SCALE_SMOOTH));
        setBrickImg(getBrickImg().getScaledInstance(getBoxSize()-getSpacing(), getBoxSize()-getSpacing(), Image.SCALE_SMOOTH));
        setBatteryImg(getBatteryImg().getScaledInstance(getBoxSize()-getSpacing(), getBoxSize()-getSpacing(), Image.SCALE_SMOOTH));
        setDrillImg(getDrillImg().getScaledInstance(getBoxSize()-getSpacing(), getBoxSize()-getSpacing(), Image.SCALE_SMOOTH));
        setScrewdriverImg(getScrewdriverImg().getScaledInstance(getBoxSize()-getSpacing(), getBoxSize()-getSpacing(), Image.SCALE_SMOOTH));
        setSawImg(getSawImg().getScaledInstance(getBoxSize()-getSpacing(), getBoxSize()-getSpacing(), Image.SCALE_SMOOTH));
        setCrowbarImg(getCrowbarImg().getScaledInstance(getBoxSize()-getSpacing(), getBoxSize()-getSpacing(), Image.SCALE_SMOOTH));
        setPickaxeImg(getPickaxeImg().getScaledInstance(getBoxSize()-getSpacing(), getBoxSize()-getSpacing(), Image.SCALE_SMOOTH));
        setWiresImg(getWiresImg().getScaledInstance(getBoxSize()-getSpacing(), getBoxSize()-getSpacing(), Image.SCALE_SMOOTH));
        setChainsawImg(getChainsawImg().getScaledInstance(getBoxSize()-getSpacing(), getBoxSize()-getSpacing(), Image.SCALE_SMOOTH));


        //Hammer image (2,2)
        g.drawImage(getHammerImg(),getSpacing() + 2 * getBoxSize(),
                getSpacing() + 2 * getBoxSize(),
                this);
        // Screw image (4,4)
        g.drawImage(getScrewImg(),getSpacing() + 4 * getBoxSize(),
                getSpacing() + 4 * getBoxSize(),
                this);
        // Helmet (4,2)
        g.drawImage(getHardHatImg(),getSpacing() + 4 * getBoxSize(),
                getSpacing() + 2 * getBoxSize(),
                this);
        // Axe (5,5)
        g.drawImage(getAxeImg(),getSpacing() + 5 * getBoxSize(),
                getSpacing() + 5 * getBoxSize(),
                this);
        // Wrench (1,4)
        g.drawImage(getWrenchImg(),getSpacing() + 1 * getBoxSize(),
                getSpacing() + 4 * getBoxSize(),
                this);

        g.drawImage(getTorchImg(),getSpacing() + 1 * getBoxSize(),
                getSpacing() + 4 * getBoxSize(),
                this);

        g.drawImage(getNailsImg(),getSpacing() + 1 * getBoxSize(),
                getSpacing() + 4 * getBoxSize(),
                this);

        g.drawImage(getBrickImg(),getSpacing() + 1 * getBoxSize(),
                getSpacing() + 4 * getBoxSize(),
                this);

        g.drawImage(getBatteryImg(),getSpacing() + 1 * getBoxSize(),
                getSpacing() + 4 * getBoxSize(),
                this);

        g.drawImage(getDrillImg(),getSpacing() + 1 * getBoxSize(),
                getSpacing() + 4 * getBoxSize(),
                this);

        g.drawImage(getScrewdriverImg(),getSpacing() + 1 * getBoxSize(),
                getSpacing() + 4 * getBoxSize(),
                this);

        g.drawImage(getSawImg(),getSpacing() + 1 * getBoxSize(),
                getSpacing() + 4 * getBoxSize(),
                this);


        g.drawImage(getCrowbarImg(),getSpacing() + 1 * getBoxSize(),
                getSpacing() + 4 * getBoxSize(),
                this);

        g.drawImage(getPickaxeImg(),getSpacing() + 1 * getBoxSize(),
                getSpacing() + 4 * getBoxSize(),
                this);

        g.drawImage(getWiresImg(),getSpacing() + 1 * getBoxSize(),
                getSpacing() + 4 * getBoxSize(),
                this);

        g.drawImage(getChainsawImg(),getSpacing() + 1 * getBoxSize(),
                getSpacing() + 4 * getBoxSize(),
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
