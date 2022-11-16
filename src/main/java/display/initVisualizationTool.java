package display;

import graph.Path;
import graph.Point;
import graph.WarehouseGraph;
import main.Warehouse;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class initVisualizationTool {
    public initVisualizationTool(Warehouse warehouse, List<Path> path, List<Point> locationsToVisit) {
        JFrame frame = new JFrame();
        frame.setTitle("Visualization tool");
        frame.setSize( 700 + 24 , 700 + 48);
        Dimension minSize = new Dimension();
        minSize.height=700+48;
        minSize.width=700+24;
        frame.setMinimumSize(minSize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        VisualizationTool vis = new VisualizationTool(warehouse, path, locationsToVisit);
        frame.add(vis);
        frame.pack();
    }
}
