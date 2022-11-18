package main;

import display.ui.UserInterface;
import graph.Point;
import graph.WarehouseGraph;
import util.Parser;
import java.io.IOException;
import java.util.*;


public class Main {
    private static final String WAREHOUSE_MAP_FILE = "src/main/resources/testMapMedium.csv";
    private static final String ITEM_FILE = "src/main/resources/items.csv";
    private static final Point START_POINT = new Point(0, 0);
    private static final Point END_POINT = new Point(0, 6);
    
    public static Warehouse warehouse;

    public static void main(String[] args) throws Exception {
        Parser parser = new Parser();
        int[][] warehouseMapLayout = parser.parseMapLayout(WAREHOUSE_MAP_FILE);
        WarehouseGraph warehouseGraph = new WarehouseGraph(warehouseMapLayout, START_POINT, END_POINT);
        warehouse = new Warehouse(warehouseGraph);
        List<Point> shelves= warehouse.getShelveLocations();
        String[] t = parser.parseItems(ITEM_FILE);
        Random rand = new Random();
        HashMap<Point,Boolean> explored = new HashMap<>();
        for(String name: t){
            Point randomPoint = shelves.get(rand.nextInt(shelves.size()));
            while (explored.containsKey(randomPoint)){
                randomPoint = shelves.get(rand.nextInt(shelves.size()));
            }
            explored.put(randomPoint,true);
            warehouse.addItem(name,randomPoint);
        }
        
        launchUI();
    }

    private static void launchUI() throws InstantiationException, IllegalAccessException, javax.swing.UnsupportedLookAndFeelException {
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new UserInterface().setVisible(true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}