package display;

import graph.Graph;
import graph.Path;
import graph.Point;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class initVisualizationTool {
    public initVisualizationTool(Graph inputGraph, ArrayList<Path> path, ArrayList<Point> locationsToVisit) {
        JFrame frame = new JFrame();
        frame.setTitle("Visualization tool");
        frame.setSize( 700 + 24 , 700 + 48);
        Dimension minSize = new Dimension();
        minSize.height=700+48;
        minSize.width=700+24;
        frame.setMinimumSize(minSize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        VisualizationTool vis = new VisualizationTool(inputGraph, path, locationsToVisit);
        frame.add(vis);
        frame.pack();
    }
}
