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
     JButton addItemToWarehouseBtn;
     JButton removeItemFromWarehouseBtn;
     Set<Point> itemLocationsToVisit;
     Warehouse warehouse = new Warehouse();
     OrderSystem(){
         warehouse.addItem("Hammer", new Point(1,1));
         warehouse.addItem("Screw", new Point(2,2));
         warehouse.addItem("Helmet", new Point(3,3));

         Object[] itemsInStock = warehouse.getItemNames();
         itemLocationsToVisit = new HashSet<>();
         addItemToWarehouseBtn = new JButton();
         addItemToWarehouseBtn.setBounds(200,100,100,50);
         addItemToWarehouseBtn.setText("Add to Warehouse");
         addItemToWarehouseBtn.setFocusable(false);
         removeItemFromWarehouseBtn = new JButton();
         removeItemFromWarehouseBtn.setBounds(200,100,100,50);
         removeItemFromWarehouseBtn.setText("Remove from Warehouse");
         removeItemFromWarehouseBtn.setFocusable(false);

         comboBox = new JComboBox<>(itemsInStock);

         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         this.setLayout(new FlowLayout());
         this.setVisible(true);
         this.add(addItemToWarehouseBtn);
         this.add(removeItemFromWarehouseBtn);
         this.add(comboBox);

         this.pack();

         addItemToWarehouseBtn.addActionListener(this);
         removeItemFromWarehouseBtn.addActionListener(this);
         comboBox.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == comboBox){
            String selectedItem = (String) comboBox.getSelectedItem();
            itemLocationsToVisit.add(warehouse.getItemLocation(selectedItem));
            System.out.println(itemLocationsToVisit);
        }
        else if (e.getSource() == addItemToWarehouseBtn) {
            System.out.println("poo");
        }
        else if (e.getSource() == removeItemFromWarehouseBtn) {
            System.out.println("pee");
        }
    }
}
