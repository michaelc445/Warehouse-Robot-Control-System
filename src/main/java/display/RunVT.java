package display;

import graph.Graph;

import javax.swing.*;
import java.awt.*;

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
        VisualizationTool vis = new VisualizationTool(inputGraph);
        frame.add(vis);
        frame.pack();
    }
}