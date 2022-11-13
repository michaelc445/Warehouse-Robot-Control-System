package main;

import display.VisualizationTool;
import graph.Graph;
import graph.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class OrderSystem extends JFrame implements ActionListener {
     JComboBox<Object> comboBox;
     JButton addToOrderBtn;
     JButton sendOrderBtn;
     JButton removeFromOrderBtn;
     HashSet<Point> itemLocationsToVisit;
     Warehouse warehouse = new Warehouse();
     OrderSystem(){
         warehouse.addItem("Hammer", new Point(2,2));
         warehouse.addItem("Screw", new Point(4,4));
         warehouse.addItem("Helmet", new Point(4,2));
         warehouse.addItem("Axe", new Point(5,5));
         warehouse.addItem("Wrench", new Point(1,4));

         Object[] itemsInStock = warehouse.getItemNames();
         itemLocationsToVisit = new HashSet<>();
         addToOrderBtn = new JButton();
         addToOrderBtn.setBounds(200,100,100,50);
         addToOrderBtn.setText("Add to Order");
         addToOrderBtn.setFocusable(false);
         removeFromOrderBtn = new JButton();
         removeFromOrderBtn.setBounds(200,100,100,50);
         removeFromOrderBtn.setText("Remove from Order");
         removeFromOrderBtn.setFocusable(false);
         sendOrderBtn = new JButton();
         sendOrderBtn.setText("Complete Order");
         sendOrderBtn.setBounds(200,100,100,50);
         removeFromOrderBtn.setText("Remove from Order");
         sendOrderBtn.setFocusable(false);



         comboBox = new JComboBox<>(itemsInStock);

         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         this.setLayout(new FlowLayout());
         this.setVisible(true);
         this.add(addToOrderBtn);
         this.add(removeFromOrderBtn);
         this.add(comboBox);
         this.add(sendOrderBtn);

         this.pack();

         addToOrderBtn.addActionListener(this);
         removeFromOrderBtn.addActionListener(this);
         sendOrderBtn.addActionListener(this);
         comboBox.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedItem = (String) comboBox.getSelectedItem();
        if (e.getSource() == addToOrderBtn) {
            itemLocationsToVisit.add(warehouse.getItemLocation(selectedItem));
            System.out.println(itemLocationsToVisit);
        }
        else if (e.getSource() == removeFromOrderBtn) {
            itemLocationsToVisit.remove(warehouse.getItemLocation(selectedItem));
            System.out.println(itemLocationsToVisit);
        }
        else if (e.getSource() == sendOrderBtn){
            if (itemLocationsToVisit.isEmpty()){
                System.out.println("Empty order!");
            }
            else {
                JFrame frame = new JFrame();
                frame.setTitle("Visualization tool");
                frame.setSize(700 + 24, 700 + 48);
                Dimension minSize = new Dimension();
                minSize.height = 700 + 48;
                minSize.width = 700 + 24;
                frame.setMinimumSize(minSize);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                String fileName = "src/test/java/TestMaps/testMap.csv";
                Graph inputGraph = null;
                try {
                    inputGraph = new Graph(fileName, false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                ArrayList<graph.Point> itemLocationList = new ArrayList<>();
                itemLocationList.addAll(itemLocationsToVisit);
                VisualizationTool vis = new VisualizationTool(inputGraph, itemLocationList);
                frame.add(vis);
                frame.pack();
            }
        }
    }
}
