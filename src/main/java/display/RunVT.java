package display;

import javax.swing.*;

public class RunVT extends JFrame {
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setTitle("Visualization tool");
        frame.setSize( 700 + 24 , 700 + 48);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        //frame.setResizable(false);
        frame.add(new VisualizationTool());
    }
}