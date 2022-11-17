package main;

import display.initVisualizationTool;
import display.ui.UserInterface;
import graph.Path;
import graph.PathFinder;
import graph.Point;
import graph.WarehouseGraph;
import stock.Item;
import util.Parser;

import java.util.List;


public class Main {
    private static final String WAREHOUSE_MAP_FILE = "warehouse_map.csv";
    private static final Point START_POINT = new Point(0, 0);
    private static final Point END_POINT = new Point(0, 6);
    
    public static Warehouse warehouse;

    public static void main(String[] args) throws Exception {
        Parser parser = new Parser();
        int[][] warehouseMapLayout = parser.parseMapLayout(WAREHOUSE_MAP_FILE);

        WarehouseGraph warehouseGraph = new WarehouseGraph(warehouseMapLayout, START_POINT, END_POINT);

        warehouse = new Warehouse(warehouseGraph);

        warehouse.addItem(new Item("Hammer", new Point(2,2)));
        warehouse.addItem(new Item("Screw", new Point(4,4)));
        warehouse.addItem(new Item("Helmet", new Point(4,2)));
        warehouse.addItem(new Item("Axe", new Point(5,5)));
        warehouse.addItem(new Item("Wrench", new Point(1,4)));

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