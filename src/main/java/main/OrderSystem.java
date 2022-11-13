package main;

import graph.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

public class OrderSystem extends JFrame implements ActionListener {
     JComboBox<Object> comboBox;
     Set<Point> itemLocationsToVisit;
     Warehouse warehouse = new Warehouse();
     OrderSystem(){
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         this.setLayout(new FlowLayout());

         warehouse.addItem("Hammer", new Point(1,1));
         warehouse.addItem("Screw", new Point(2,2));
         warehouse.addItem("Helmet", new Point(3,3));


        Object[] itemsInStock = warehouse.getItemNames();
        itemLocationsToVisit = new HashSet<>();

        comboBox = new JComboBox<>(itemsInStock);
        comboBox.addActionListener(this);

        this.add(comboBox);

        this.pack();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == comboBox){
            String selectedItem = (String) comboBox.getSelectedItem();

            itemLocationsToVisit.add(warehouse.getItemLocation(selectedItem));

            System.out.println(itemLocationsToVisit);
        }
    }
}
