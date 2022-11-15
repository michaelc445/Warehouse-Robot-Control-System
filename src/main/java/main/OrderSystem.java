package main;

import graph.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

public class OrderSystem extends JFrame implements ActionListener {
    Graph inputGraph;
    JComboBox<Object> comboBox;
     JButton addToOrderBtn;
     JButton sendOrderBtn;
     JButton removeFromOrderBtn;
     HashSet<String> order;
     Warehouse warehouse = new Warehouse();


     public OrderSystem(Graph inputGraph){
         this.inputGraph = inputGraph;
         Object[] itemsInStock = warehouse.getItemNames();
         order = new HashSet<>();
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
            order.add(selectedItem);
            System.out.println(order);
            System.out.printf("Added %s to order\n",(selectedItem));
        }
        else if (e.getSource() == removeFromOrderBtn) {
            order.remove(selectedItem);
            System.out.println(order);
            System.out.printf("Removed %s from order\n",(selectedItem));
        }
        else if (e.getSource() == sendOrderBtn){
            if (order.isEmpty()){
                System.out.println("Empty order!");
            }
            else {
                //send the order to the WRCS!!
                new Controller(inputGraph, order);
            }
        }
    }
}
