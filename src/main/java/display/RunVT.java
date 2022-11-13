package display;

import graph.Graph;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class RunVT extends JFrame {
    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame();
        frame.setTitle("Visualization tool");
        frame.setSize( 700 + 24 , 700 + 48);
        Dimension minSize = new Dimension();
        minSize.height=700+48;
        minSize.width=700+24;
        frame.setMinimumSize(minSize);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        //frame.setResizable(false);
        String fileName = "src/test/java/TestMaps/testMap.csv";
        Graph  inputGraph  = new Graph(fileName,false);
        ArrayList<graph.Point> order = new ArrayList<>();
        order.add(new graph.Point(2,2));
        VisualizationTool vis = new VisualizationTool(inputGraph, order);
        frame.add(vis);
        frame.pack();
    }
}