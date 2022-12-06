package main;

import display.UserInterface;
import graph.Point;
import graph.WarehouseGraph;
import util.Parser;

import java.util.*;


public class Main {
    private static final String WAREHOUSE_MAP_FILE = "testMapMedium.csv";
    private static final String ITEM_FILE = "constructionItems.csv";
    private static final Point START_POINT = new Point(0, 0);
    private static final Point END_POINT = new Point(0, 6);

    public static Warehouse warehouse;
    public static UserInterface UI;

    public static void main(String[] args) throws Exception {
        Parser parser = new Parser();
        int[][] warehouseMapLayout = parser.parseMapLayout(WAREHOUSE_MAP_FILE);
        WarehouseGraph warehouseGraph = new WarehouseGraph(warehouseMapLayout, START_POINT, END_POINT);
        warehouse = new Warehouse(warehouseGraph);

        warehouse.addItem("Hammer",new Point(14,5),1);
        warehouse.addItem("Screw",new Point(3,19),1);
        warehouse.addItem("Helmet",new Point(12,17),1);

        warehouse.addItem("Axe",new Point(13,23),2);
        warehouse.addItem("Wrench",new Point(1,4),1);
        warehouse.addItem("Torch",new Point(35,19),1);

        warehouse.addItem("Nails",new Point(7,16),1);
        warehouse.addItem("Brick",new Point(15,20),2);
        warehouse.addItem("Battery",new Point(1,29),1);

        warehouse.addItem("Drill",new Point(31,4),2);
        warehouse.addItem("Screwdriver",new Point(25,26),1);
        warehouse.addItem("Saw",new Point(11,4),1);

        warehouse.addItem("Crowbar",new Point(32,11),2);
        warehouse.addItem("Pickaxe",new Point(26,4),1);
        warehouse.addItem("Wires",new Point(21,29),1);

        warehouse.addItem("Chainsaw",new Point(36,2),4);

        launchUI(args.length == 0);
    }

    private static void launchUI(boolean isVisible) throws InstantiationException, IllegalAccessException, javax.swing.UnsupportedLookAndFeelException {
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
        }
        //</editor-fold>

        UI = new UserInterface();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> UI.setVisible(isVisible));
    }
}