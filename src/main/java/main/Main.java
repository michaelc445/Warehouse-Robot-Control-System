package main;

import display.initVisualizationTool;
import display.ui.UserInterface;
import graph.Path;
import graph.PathFinder;
import graph.Point;
import graph.WarehouseGraph;
import stock.Item;
import util.Parser;

import java.util.*;


public class Main {
    private static final String WAREHOUSE_MAP_FILE = "testMapMedium.csv";
    private static final Point START_POINT = new Point(0, 0);
    private static final Point END_POINT = new Point(0, 6);
    
    public static Warehouse warehouse;

    public static void main(String[] args) throws Exception {
        Parser parser = new Parser();
        int[][] warehouseMapLayout = parser.parseMapLayout(WAREHOUSE_MAP_FILE);

        WarehouseGraph warehouseGraph = new WarehouseGraph(warehouseMapLayout, START_POINT, END_POINT);

        warehouse = new Warehouse(warehouseGraph);
        List<Point> shelves= warehouse.getShelveLocations();
        String[] t = parser.parseItems("items.csv");
        Random rand = new Random();
        HashMap<Point,Boolean> explored = new HashMap<>();
        for(String name: t){
            Point randomPoint = shelves.get(rand.nextInt(shelves.size()));
            while (explored.containsKey(randomPoint)){
                randomPoint = shelves.get(rand.nextInt(shelves.size()));
            }
            explored.put(randomPoint,true);
            warehouse.addItem(new Item(name,randomPoint));
        }
//        warehouse.addItem(new Item("hammer", new Point(2,2)));
//        warehouse.addItem(new Item("screws", new Point(4,4)));
//        warehouse.addItem(new Item("helmet", new Point(4,2)));
//        warehouse.addItem(new Item("axe", new Point(5,5)));
//        warehouse.addItem(new Item("wrench", new Point(1,4)));

        launchUI();
    }

    private static void launchUI() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new UserInterface().setVisible(true);
        });
    }
}