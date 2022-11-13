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
     JButton addToOrderBtn;
     JButton removeFromOrderBtn;
     Set<Point> itemLocationsToVisit;
     Warehouse warehouse = new Warehouse();
     OrderSystem(){
         warehouse.addItem("Hammer", new Point(1,1));
         warehouse.addItem("Screw", new Point(2,2));
         warehouse.addItem("Helmet", new Point(3,3));
         warehouse.addItem("Axe", new Point(3,1));
         warehouse.addItem("Wrench", new Point(2,3));

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

         comboBox = new JComboBox<>(itemsInStock);

         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         this.setLayout(new FlowLayout());
         this.setVisible(true);
         this.add(addToOrderBtn);
         this.add(removeFromOrderBtn);
         this.add(comboBox);

         this.pack();

         addToOrderBtn.addActionListener(this);
         removeFromOrderBtn.addActionListener(this);
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
    }
}
