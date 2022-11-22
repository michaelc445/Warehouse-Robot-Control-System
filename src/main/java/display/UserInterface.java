/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package display;

import graph.Path;
import graph.PathFinder;
import graph.Point;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.*;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import static main.Main.warehouse;
import main.Warehouse;
import util.Parser;

/**
 *
 * @author art
 */
public class UserInterface extends javax.swing.JFrame {

    String[] items = warehouse.getItemNames().toArray(String[]::new);

    Set<String> order = new HashSet<>();

    String selectedItem = "";

    boolean isOrderListFocused = false;

    VisualizationTool visualisation;

    int emulationSpeed = 500;

    /**
     * Creates new form UserInterface
     */
    public UserInterface() {
        initComponents();
        setFrameDimensions();
    }

    private void setFrameDimensions() {
        int[][] mapLayout = warehouse.getMapLayout();

        int mapHeight  =  mapLayout.length;
        int mapWidth = mapLayout[0].length;
        int visualisationPanelWidth = mapWidth * VisualizationTool.boxSize - 10;
        int visualisationPanelHeight = mapHeight * VisualizationTool.boxSize + 2;

        if (visualisationPanelWidth < visualisationPanel.getMinimumSize().width
                || visualisationPanelHeight < visualisationPanel.getMinimumSize().height) {
            visualisationPanelWidth = (int) visualisationPanel.getMinimumSize().getWidth();
            visualisationPanelHeight = (int) visualisationPanel.getMinimumSize().getHeight();
            orderList.setFont(new Font("Arial",Font.PLAIN,12));
        }
        int frameWidth = (this.getWidth() - visualisationPanel.getWidth()) + visualisationPanelWidth;
        int frameHeight = (this.getHeight() - visualisationPanel.getHeight()) + visualisationPanelHeight;

        this.setSize(new Dimension(frameWidth, frameHeight));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        maxSizeDialog = new javax.swing.JDialog();
        maximumOrderLimitLabel = new javax.swing.JLabel();
        grandButton = new javax.swing.JButton();
        uesrControlPanel = new javax.swing.JPanel();
        orderSystemPanel = new javax.swing.JPanel();
        orderSystemLabel = new javax.swing.JLabel();
        removeItemButton = new javax.swing.JButton();
        addItemButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        orderList = new javax.swing.JList<>();
        clearOrderButton = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        orderProgressLabel = new javax.swing.JLabel();
        orderProgressBar = new javax.swing.JProgressBar();
        jSeparator4 = new javax.swing.JSeparator();
        processOrderButton = new javax.swing.JButton();
        itemListPanel = new javax.swing.JPanel();
        itemListLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        itemList = new javax.swing.JList<>();
        jSeparator1 = new javax.swing.JSeparator();
        selectItemHintLabel = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        maxItemsHintLabel = new javax.swing.JLabel();
        devLogPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        logTextArea = new javax.swing.JTextArea();
        emulationSpeedSlider = new javax.swing.JSlider();
        emulationSpeedLabel = new javax.swing.JLabel();
        fillingPanelForNewFeatures = new javax.swing.JPanel();
        visualisationPanel = new javax.swing.JPanel();

        maxSizeDialog.setTitle("Order List Is Full");
        maxSizeDialog.setAlwaysOnTop(true);
        maxSizeDialog.setLocation(new java.awt.Point(300, 400));
        maxSizeDialog.setMinimumSize(new java.awt.Dimension(520, 150));
        maxSizeDialog.setName("dialog_window"); // NOI18N
        maxSizeDialog.setResizable(false);

        maximumOrderLimitLabel.setFont(new java.awt.Font("Fira Sans", 0, 24)); // NOI18N
        maximumOrderLimitLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        maximumOrderLimitLabel.setText("The Order List has reached maximum size!");

        grandButton.setFont(new java.awt.Font("Fira Sans", 0, 36)); // NOI18N
        grandButton.setText("Grand!");
        grandButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grandButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout maxSizeDialogLayout = new javax.swing.GroupLayout(maxSizeDialog.getContentPane());
        maxSizeDialog.getContentPane().setLayout(maxSizeDialogLayout);
        maxSizeDialogLayout.setHorizontalGroup(
                maxSizeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(maxSizeDialogLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(maxSizeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(maximumOrderLimitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(grandButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        maxSizeDialogLayout.setVerticalGroup(
                maxSizeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(maxSizeDialogLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(maximumOrderLimitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(grandButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Warehouse Robotic Control System");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("mainFrame"); // NOI18N
        setSize(new java.awt.Dimension(600, 400));

        uesrControlPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        uesrControlPanel.setMinimumSize(new java.awt.Dimension(250, 100));
        uesrControlPanel.setPreferredSize(new java.awt.Dimension(350, 624));

        orderSystemPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        orderSystemLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        orderSystemLabel.setText("Order System");
        orderSystemLabel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        removeItemButton.setText("Remove");
        removeItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeItemButtonActionPerformed(evt);
            }
        });

        addItemButton.setText("Add");
        addItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addItemButtonActionPerformed(evt);
            }
        });

        orderList.setFont(new java.awt.Font("Fira Sans", 0, 24)); // NOI18N
        orderList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        orderList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                orderListValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(orderList);

        clearOrderButton.setFont(new java.awt.Font("Fira Sans", 0, 15)); // NOI18N
        clearOrderButton.setText("Clear/Next Order");
        clearOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearOrderButtonActionPerformed(evt);
            }
        });

        orderProgressLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        orderProgressLabel.setText("Order Progress");

        processOrderButton.setBackground(new java.awt.Color(228, 255, 250));
        processOrderButton.setText("Process Order");
        processOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                processOrderButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout orderSystemPanelLayout = new javax.swing.GroupLayout(orderSystemPanel);
        orderSystemPanel.setLayout(orderSystemPanelLayout);
        orderSystemPanelLayout.setHorizontalGroup(
                orderSystemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(orderSystemPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(orderSystemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jSeparator4)
                                        .addComponent(removeItemButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(addItemButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jSeparator3)
                                        .addComponent(orderSystemLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(clearOrderButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(orderProgressLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addGroup(orderSystemPanelLayout.createSequentialGroup()
                                                .addComponent(orderProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(processOrderButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        orderSystemPanelLayout.setVerticalGroup(
                orderSystemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(orderSystemPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(orderSystemLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addItemButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(removeItemButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(processOrderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clearOrderButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addComponent(orderProgressLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(orderProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        itemListPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        itemListLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        itemListLabel.setText("Item Database");
        itemListLabel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        itemList.setFont(new java.awt.Font("Fira Sans", 0, 18)); // NOI18N
        itemList.setModel(new javax.swing.DefaultComboBoxModel<>(items));
        itemList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                itemListKeyPressed(evt);
            }
        });
        itemList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                itemListValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(itemList);

        javax.swing.GroupLayout itemListPanelLayout = new javax.swing.GroupLayout(itemListPanel);
        itemListPanel.setLayout(itemListPanelLayout);
        itemListPanelLayout.setHorizontalGroup(
                itemListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, itemListPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(itemListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(itemListLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        itemListPanelLayout.setVerticalGroup(
                itemListPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(itemListPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(itemListLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane3)
                                .addContainerGap())
        );

        selectItemHintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        selectItemHintLabel.setText("Select item by clicking on it or press Enter");

        maxItemsHintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        maxItemsHintLabel.setText("(Max. 5 items per order)");

        javax.swing.GroupLayout uesrControlPanelLayout = new javax.swing.GroupLayout(uesrControlPanel);
        uesrControlPanel.setLayout(uesrControlPanelLayout);
        uesrControlPanelLayout.setHorizontalGroup(
                uesrControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(uesrControlPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(uesrControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(maxItemsHintLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jSeparator2)
                                        .addComponent(selectItemHintLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                                        .addComponent(jSeparator1)
                                        .addGroup(uesrControlPanelLayout.createSequentialGroup()
                                                .addComponent(orderSystemPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(itemListPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        uesrControlPanelLayout.setVerticalGroup(
                uesrControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(uesrControlPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(uesrControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(orderSystemPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(itemListPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(selectItemHintLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(maxItemsHintLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        getContentPane().add(uesrControlPanel, java.awt.BorderLayout.LINE_START);

        devLogPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        logTextArea.setEditable(false);
        logTextArea.setBackground(new java.awt.Color(0, 0, 0));
        logTextArea.setColumns(20);
        logTextArea.setForeground(new java.awt.Color(153, 255, 204));
        logTextArea.setRows(5);
        jScrollPane1.setViewportView(logTextArea);

        emulationSpeedSlider.setMajorTickSpacing(200);
        emulationSpeedSlider.setMaximum(1000);
        emulationSpeedSlider.setMinimum(50);
        emulationSpeedSlider.setMinorTickSpacing(50);
        emulationSpeedSlider.setPaintTicks(true);
        emulationSpeedSlider.setSnapToTicks(true);
        emulationSpeedSlider.setToolTipText("Robot speed");
        emulationSpeedSlider.setValue(500);
        emulationSpeedSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                emulationSpeedSliderStateChanged(evt);
            }
        });

        emulationSpeedLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        emulationSpeedLabel.setText("Emulation Speed");
        emulationSpeedLabel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout devLogPanelLayout = new javax.swing.GroupLayout(devLogPanel);
        devLogPanel.setLayout(devLogPanelLayout);
        devLogPanelLayout.setHorizontalGroup(
                devLogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, devLogPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(devLogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(emulationSpeedSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                                        .addComponent(emulationSpeedLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
                                .addContainerGap())
        );
        devLogPanelLayout.setVerticalGroup(
                devLogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, devLogPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(devLogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(devLogPanelLayout.createSequentialGroup()
                                                .addComponent(emulationSpeedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(emulationSpeedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addContainerGap())
        );

        getContentPane().add(devLogPanel, java.awt.BorderLayout.PAGE_END);

        fillingPanelForNewFeatures.setBackground(new java.awt.Color(255, 255, 255));
        fillingPanelForNewFeatures.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        fillingPanelForNewFeatures.setMaximumSize(new java.awt.Dimension(2, 32767));
        fillingPanelForNewFeatures.setPreferredSize(new java.awt.Dimension(1, 624));

        javax.swing.GroupLayout fillingPanelForNewFeaturesLayout = new javax.swing.GroupLayout(fillingPanelForNewFeatures);
        fillingPanelForNewFeatures.setLayout(fillingPanelForNewFeaturesLayout);
        fillingPanelForNewFeaturesLayout.setHorizontalGroup(
                fillingPanelForNewFeaturesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
        );
        fillingPanelForNewFeaturesLayout.setVerticalGroup(
                fillingPanelForNewFeaturesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 598, Short.MAX_VALUE)
        );

        getContentPane().add(fillingPanelForNewFeatures, java.awt.BorderLayout.LINE_END);

        visualisationPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        visualisationPanel.setMinimumSize(new java.awt.Dimension(300, 450));
        visualisationPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        javax.swing.GroupLayout visualisationPanelLayout = new javax.swing.GroupLayout(visualisationPanel);
        visualisationPanel.setLayout(visualisationPanelLayout);
        visualisationPanelLayout.setHorizontalGroup(
                visualisationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 499, Short.MAX_VALUE)
        );
        visualisationPanelLayout.setVerticalGroup(
                visualisationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 598, Short.MAX_VALUE)
        );

        getContentPane().add(visualisationPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>

    private void grandButtonActionPerformed(java.awt.event.ActionEvent evt) {
        maxSizeDialog.setVisible(false);
    }

    private void itemListValueChanged(javax.swing.event.ListSelectionEvent evt) {
        if (!evt.getValueIsAdjusting()) {
            selectedItem = itemList.getSelectedValue();
            isOrderListFocused = false;
        }
    }

    private void itemListKeyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            addSelectedItemToOrderList();
        }
    }

    private void processOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (order.isEmpty()){
            addToLogger("Empty order. Cannot process the order!");
        }
        else {
            launchController();
        }
    }

    private void clearOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {
        order.clear();
        updateOrderScrollPanel();
        addToLogger("Order list was cleared");
    }

    private void orderListValueChanged(javax.swing.event.ListSelectionEvent evt) {
        if (!evt.getValueIsAdjusting()) {
            selectedItem = orderList.getSelectedValue();
            isOrderListFocused = true;
        }
    }

    private void addItemButtonActionPerformed(java.awt.event.ActionEvent evt) {
        addSelectedItemToOrderList();
    }

    private void removeItemButtonActionPerformed(java.awt.event.ActionEvent evt) {
        removeSelectedItemFromOrderList();
    }

    private void emulationSpeedSliderStateChanged(javax.swing.event.ChangeEvent evt) {
        changeEmulationSpeedBySliderVal();
    }

    private void updateOrderScrollPanel() {
        orderList.removeAll();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String item : order) {
            listModel.addElement(item);
        }
        orderList.setModel(listModel);
    }

    // Variables declaration - do not modify
    private javax.swing.JButton addItemButton;
    private javax.swing.JButton clearOrderButton;
    private javax.swing.JPanel devLogPanel;
    private javax.swing.JLabel emulationSpeedLabel;
    private javax.swing.JSlider emulationSpeedSlider;
    private javax.swing.JPanel fillingPanelForNewFeatures;
    private javax.swing.JButton grandButton;
    private javax.swing.JList<String> itemList;
    private javax.swing.JLabel itemListLabel;
    private javax.swing.JPanel itemListPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    public static javax.swing.JTextArea logTextArea;
    private javax.swing.JLabel maxItemsHintLabel;
    private javax.swing.JDialog maxSizeDialog;
    private javax.swing.JLabel maximumOrderLimitLabel;
    private javax.swing.JList<String> orderList;
    private javax.swing.JProgressBar orderProgressBar;
    private javax.swing.JLabel orderProgressLabel;
    private javax.swing.JLabel orderSystemLabel;
    private javax.swing.JPanel orderSystemPanel;
    private javax.swing.JButton processOrderButton;
    private javax.swing.JButton removeItemButton;
    private javax.swing.JLabel selectItemHintLabel;
    private javax.swing.JPanel uesrControlPanel;
    private javax.swing.JPanel visualisationPanel;

    private void launchController() {
        ArrayList<Point> locationsToVisit;

        locationsToVisit = new ArrayList<>();

        for (String item : order){
            locationsToVisit.add(warehouse.getItemLocation(item));
        }

        PathFinder pathFinder = new PathFinder();
        List<Path> shortestPath = pathFinder.findShortestPath(warehouse, locationsToVisit);

        showVisualisation(warehouse, shortestPath, locationsToVisit);
    }

    protected void showVisualisation(Warehouse warehouse, List<Path> shortestPath, ArrayList<Point> locationsToVisit) {
        SwingUtilities.invokeLater(() -> {
            this.remove(visualisationPanel);
            visualisation = new VisualizationTool(warehouse, shortestPath, locationsToVisit);
            visualisation.setLocation(visualisationPanel.getWidth() / 2, visualisationPanel.getHeight() / 2 );
            changeEmulationSpeedBySliderVal();
            this.add(visualisation);
            this.invalidate();
            this.revalidate();
        });
    }

    private void addToLogger(String newMessage) {
        StringBuilder loggerMessageBuilder = new StringBuilder();
        loggerMessageBuilder.append(logTextArea.getText())
                .append(System.lineSeparator())
                .append(newMessage);
        logTextArea.setText(loggerMessageBuilder.toString());
    }

    private void addSelectedItemToOrderList() {
        if (order.size() == 5) {
            maxSizeDialog.setVisible(true);
            addToLogger("Maximum Order list size is reached");
        } else {
            if (!order.contains(selectedItem)) {
                if (Arrays.stream(items).toList().contains(selectedItem)) {
                    order.add(selectedItem);
                    addToLogger("Item  " + selectedItem + " added to order");
                    updateOrderScrollPanel();
                } else {
                    addToLogger("Item is not present in the Item DB or wasn't selected");
                }
            } else {
                addToLogger("Item  " + selectedItem + " is already in the order list");
            }
        }
    }

    private void removeSelectedItemFromOrderList() {
        if (isOrderListFocused) {
            if (order.isEmpty()) {
                addToLogger("Remove pressed, but the order list is empty");
            } else if (selectedItem != null){
                if (Arrays.stream(items).toList().contains(selectedItem)) {
                    order.remove(selectedItem);
                    addToLogger("Item  " + selectedItem + " removed from order");
                    updateOrderScrollPanel();
                } else {
                    addToLogger("Item is not present in the Item DB or wasn't selected");
                }
            }
        } else {
            addToLogger("Please, select item from the Order List to remove it (not form DB)");
        }
    }

    private void changeEmulationSpeedBySliderVal() {
        emulationSpeed = emulationSpeedSlider.getValue();
        if (visualisation != null) {
            if (emulationSpeed == emulationSpeedSlider.getMaximum()) {
                emulationSpeed -= emulationSpeedSlider.getMinimum();
            }
            visualisation.getTimer().setDelay(emulationSpeedSlider.getMaximum() - emulationSpeed);
        }
    }
}
