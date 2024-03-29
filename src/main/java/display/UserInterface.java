/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package display;

import graph.Path;
import graph.PathFinder;
import graph.Point;
import main.Warehouse;
import stock.ItemOrder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;
import java.util.*;

import static main.Main.warehouse;

/**
 * @author art
 */
public class UserInterface extends javax.swing.JFrame {

    private List<String> items = warehouse.getItemNames();

    private static Set<ItemOrder> order = new HashSet<>();

    private static int itemsTaken = 0;

    private static String lastOrderDetails = "";

    private static int remainingCapacity = PathFinder.ROBOT_MAX_CAPACITY;

    String selectedItem = "";

    boolean isOrderListFocused = false;

    private static volatile VisualizationTool visualisation;

    int emulationSpeed = 500;

    private static final int ORDER_SIZE_MAX = 5;

    /**
     * Creates new form UserInterface
     */
    public UserInterface() {
        initComponents();
        setFrameDimensions();
        addToLogger("Warehouse Robotic Control System launched successfully");
        addToRobotLogger(robotASCIIEasterEgg, null);
    }

    private void setFrameDimensions() {
        int[][] mapLayout = warehouse.getMapLayout();

        int mapHeight = mapLayout.length;
        int mapWidth = mapLayout[0].length;
        int visualisationPanelWidth = mapWidth * VisualizationTool.boxSize;
        int visualisationPanelHeight = mapHeight * VisualizationTool.boxSize;

        if (visualisationPanelWidth < visualisationPanel.getMinimumSize().width
                || visualisationPanelHeight < visualisationPanel.getMinimumSize().height) {
            visualisationPanelWidth = (int) visualisationPanel.getMinimumSize().getWidth();
            visualisationPanelHeight = (int) visualisationPanel.getMinimumSize().getHeight();
            orderList.setFont(new Font("Arial", Font.PLAIN, 12));
        }
        int frameWidth = (this.getWidth() - visualisationPanel.getWidth() - orderProcessingSidePanel.getWidth()) + visualisationPanelWidth;
        int frameHeight = (this.getHeight() - visualisationPanel.getHeight()) + visualisationPanelHeight;

        this.setSize(new Dimension(frameWidth + 88, frameHeight + 3));
    }

    private void initComponents() {

        maxSizeDialog = new javax.swing.JDialog();
        maximumOrderLimitLabel = new javax.swing.JLabel();
        grandButton = new javax.swing.JButton();
        jMenu1 = new javax.swing.JMenu();
        uesrControlPanel = new javax.swing.JPanel();
        orderSystemPanel = new javax.swing.JPanel();
        orderSystemLabel = new javax.swing.JLabel();
        removeItemButton = new javax.swing.JButton();
        addItemButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        orderList = new javax.swing.JList<>();
        clearOrderButton = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        processOrderButton = new javax.swing.JButton();
        qtySpinner = new javax.swing.JSpinner();
        qtyLabel = new javax.swing.JLabel();
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
        orderProcessingSidePanel = new javax.swing.JPanel();
        orderQueue1Label = new javax.swing.JLabel();
        orderQueue2Label = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        robotLoggerTextArea = new javax.swing.JTextArea();
        jSeparator6 = new javax.swing.JSeparator();
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

        jMenu1.setText("jMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Warehouse Robotic Control System");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("mainFrame"); // NOI18N
        setSize(new java.awt.Dimension(600, 400));

        uesrControlPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        uesrControlPanel.setMinimumSize(new java.awt.Dimension(250, 100));
        uesrControlPanel.setPreferredSize(new java.awt.Dimension(300, 624));

        orderSystemPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        orderSystemPanel.setPreferredSize(new java.awt.Dimension(140, 530));

        orderSystemLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        orderSystemLabel.setText("Order System");
        orderSystemLabel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        removeItemButton.setText("Remove");
        removeItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeItemButtonActionPerformed(evt);
            }
        });

        addItemButton.setText("Add/Update");
        addItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addItemButtonActionPerformed(evt);
            }
        });

        orderList.setFont(new java.awt.Font("Fira Sans", 0, 13)); // NOI18N
        orderList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        orderList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                orderListValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(orderList);

        clearOrderButton.setBackground(new java.awt.Color(255, 255, 204));
        clearOrderButton.setFont(new java.awt.Font("Fira Sans", 0, 15)); // NOI18N
        clearOrderButton.setText("Clear Order");
        clearOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearOrderButtonActionPerformed(evt);
            }
        });

        processOrderButton.setBackground(new java.awt.Color(204, 255, 204));
        processOrderButton.setFont(new java.awt.Font("Fira Sans", 1, 18)); // NOI18N
        processOrderButton.setText("Process");
        processOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                processOrderButtonActionPerformed(evt);
            }
        });

        qtySpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 50, 1));
        qtySpinner.setValue(1);

        qtyLabel.setText("QTY:");

        javax.swing.GroupLayout orderSystemPanelLayout = new javax.swing.GroupLayout(orderSystemPanel);
        orderSystemPanel.setLayout(orderSystemPanelLayout);
        orderSystemPanelLayout.setHorizontalGroup(
                orderSystemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(orderSystemPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(orderSystemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(orderSystemPanelLayout.createSequentialGroup()
                                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(qtyLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(qtySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(orderSystemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(clearOrderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(orderSystemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(removeItemButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                                .addComponent(addItemButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(processOrderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        orderSystemPanelLayout.setVerticalGroup(
                orderSystemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(orderSystemPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(orderSystemLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(orderSystemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(orderSystemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(qtyLabel)
                                                .addComponent(qtySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(addItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(removeItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(clearOrderButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(processOrderButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        itemListPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        itemListLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        itemListLabel.setText("Item Database");
        itemListLabel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        itemList.setFont(new java.awt.Font("Fira Sans", 0, 15)); // NOI18N
        itemList.setModel(new javax.swing.DefaultComboBoxModel<>(items.toArray(String[]::new)));
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
                                .addComponent(itemListLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                                .addContainerGap())
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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
        selectItemHintLabel.setText("Select item by clicking/pressing Enter");

        maxItemsHintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        maxItemsHintLabel.setText("(Max. locations: 5, Carrying capacity: 10 kg)");

        javax.swing.GroupLayout uesrControlPanelLayout = new javax.swing.GroupLayout(uesrControlPanel);
        uesrControlPanel.setLayout(uesrControlPanelLayout);
        uesrControlPanelLayout.setHorizontalGroup(
                uesrControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(uesrControlPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(uesrControlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(selectItemHintLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jSeparator2)
                                        .addComponent(jSeparator1)
                                        .addGroup(uesrControlPanelLayout.createSequentialGroup()
                                                .addComponent(orderSystemPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(itemListPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(maxItemsHintLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        devLogPanel.setPreferredSize(new java.awt.Dimension(852, 70));

        logTextArea.setEditable(false);
        logTextArea.setBackground(new java.awt.Color(0, 0, 0));
        logTextArea.setColumns(20);
        logTextArea.setFont(new java.awt.Font("Fira Mono", 0, 11)); // NOI18N
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
                                        .addComponent(emulationSpeedLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(emulationSpeedSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 910, Short.MAX_VALUE)
                                .addContainerGap())
        );
        devLogPanelLayout.setVerticalGroup(
                devLogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, devLogPanelLayout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(devLogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(devLogPanelLayout.createSequentialGroup()
                                                .addComponent(emulationSpeedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(emulationSpeedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(38, 38, 38))
        );

        getContentPane().add(devLogPanel, java.awt.BorderLayout.PAGE_END);

        orderProcessingSidePanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        orderProcessingSidePanel.setMaximumSize(new java.awt.Dimension(120, 32767));
        orderProcessingSidePanel.setMinimumSize(new java.awt.Dimension(100, 0));
        orderProcessingSidePanel.setPreferredSize(new java.awt.Dimension(100, 624));

        orderQueue1Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        orderQueue1Label.setText("Robot");
        orderQueue1Label.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        orderQueue2Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        orderQueue2Label.setText("Operation");
        orderQueue2Label.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        robotLoggerTextArea.setEditable(false);
        robotLoggerTextArea.setBackground(new java.awt.Color(0, 0, 0));
        robotLoggerTextArea.setColumns(8);
        robotLoggerTextArea.setFont(new java.awt.Font("Fira Mono", 0, 11)); // NOI18N
        robotLoggerTextArea.setForeground(new java.awt.Color(255, 255, 102));
        robotLoggerTextArea.setRows(5);
        jScrollPane5.setViewportView(robotLoggerTextArea);

        javax.swing.GroupLayout orderProcessingSidePanelLayout = new javax.swing.GroupLayout(orderProcessingSidePanel);
        orderProcessingSidePanel.setLayout(orderProcessingSidePanelLayout);
        orderProcessingSidePanelLayout.setHorizontalGroup(
                orderProcessingSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, orderProcessingSidePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(orderProcessingSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(orderQueue2Label, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                                        .addComponent(orderQueue1Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jSeparator6))
                                .addContainerGap())
                        .addComponent(jScrollPane5)
        );
        orderProcessingSidePanelLayout.setVerticalGroup(
                orderProcessingSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, orderProcessingSidePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(orderQueue1Label)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(orderQueue2Label)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        getContentPane().add(orderProcessingSidePanel, java.awt.BorderLayout.LINE_END);

        visualisationPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        visualisationPanel.setMaximumSize(new java.awt.Dimension(1100, 32767));
        visualisationPanel.setMinimumSize(new java.awt.Dimension(300, 300));
        visualisationPanel.setPreferredSize(new java.awt.Dimension(1000, 600));

        javax.swing.GroupLayout visualisationPanelLayout = new javax.swing.GroupLayout(visualisationPanel);
        visualisationPanel.setLayout(visualisationPanelLayout);
        visualisationPanelLayout.setHorizontalGroup(
                visualisationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 816, Short.MAX_VALUE)
        );
        visualisationPanelLayout.setVerticalGroup(
                visualisationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 628, Short.MAX_VALUE)
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
        processOrder();
    }

    void processOrder() {
        if (order.isEmpty()) {
            addToLogger("Empty order. Cannot process the order!");
        } else if (visualisation == null) {
            lastOrderDetails = order + "; Total weight = "
                    + order.stream()
                    .mapToInt(item -> item.quantity() * item.weight())
                    .sum()
                    + " kg";
            launchController();
        } else if (visualisation.isOrderFinished()) {
            launchController();
        } else {
            addToLogger("Waiting for robot to finish the last order");
        }
    }

    private void clearOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {
        clearOrderList();
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

    private static void updateOrderScrollPanel() {
        orderList.removeAll();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (ItemOrder item : order) {
            listModel.addElement(item.name() + ", " + item.quantity());
        }
        orderList.setModel(listModel);
    }

    // Variables declaration - do not modify
    private static javax.swing.JButton addItemButton;
    private static javax.swing.JButton clearOrderButton;
    private javax.swing.JPanel devLogPanel;
    private javax.swing.JLabel emulationSpeedLabel;
    private javax.swing.JSlider emulationSpeedSlider;
    private javax.swing.JButton grandButton;
    private javax.swing.JList<String> itemList;
    private javax.swing.JLabel itemListLabel;
    private javax.swing.JPanel itemListPanel;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator6;
    public static javax.swing.JTextArea logTextArea;
    private javax.swing.JLabel maxItemsHintLabel;
    private javax.swing.JDialog maxSizeDialog;
    private javax.swing.JLabel maximumOrderLimitLabel;
    private static javax.swing.JList<String> orderList;
    private javax.swing.JPanel orderProcessingSidePanel;
    private javax.swing.JLabel orderQueue1Label;
    private javax.swing.JLabel orderQueue2Label;
    private javax.swing.JLabel orderSystemLabel;
    private javax.swing.JPanel orderSystemPanel;
    private javax.swing.JButton processOrderButton;
    private javax.swing.JLabel qtyLabel;
    private static javax.swing.JSpinner qtySpinner;
    private static javax.swing.JButton removeItemButton;
    private static javax.swing.JTextArea robotLoggerTextArea;
    private javax.swing.JLabel selectItemHintLabel;
    private javax.swing.JPanel uesrControlPanel;
    private javax.swing.JPanel visualisationPanel;
    private List<Image> images;
    private int boxSize;
    private int spacing;
    // End of variables declaration


    private void launchController() {
        addItemButton.setEnabled(false);
        removeItemButton.setEnabled(false);
        clearOrderButton.setEnabled(false);

        robotLoggerTextArea.setText("");
        addToLogger("Robot started processing order");
        addToRobotLogger("processing", null);

        ArrayList<Point> locationsToVisit = new ArrayList<>();
        for (ItemOrder item : order) {
            locationsToVisit.add(item.location());
        }
        this.boxSize = 24;
        this.spacing = 1;
        PathFinder pathFinder = new PathFinder();
        List<Path> shortestPath = pathFinder.findShortestPath(warehouse.getWarehouseGraph(), order.stream().toList());
        if (this.images == null) {
            try {
                this.images = List.of(
                        ImageIO.read(new File("src/main/java/display/static/robot.png")).getScaledInstance(getBoxSize() - getSpacing(), getBoxSize() - getSpacing(), Image.SCALE_SMOOTH),
                        ImageIO.read(new File("src/main/java/display/static/hammer.png")).getScaledInstance(getBoxSize() - getSpacing(), getBoxSize() - getSpacing(), Image.SCALE_SMOOTH),
                        ImageIO.read(new File("src/main/java/display/static/screw.png")).getScaledInstance(getBoxSize() - getSpacing(), getBoxSize() - getSpacing(), Image.SCALE_SMOOTH),
                        ImageIO.read(new File("src/main/java/display/static/hardHat.png")).getScaledInstance(getBoxSize() - getSpacing(), getBoxSize() - getSpacing(), Image.SCALE_SMOOTH),
                        ImageIO.read(new File("src/main/java/display/static/axe.png")).getScaledInstance(getBoxSize() - getSpacing(), getBoxSize() - getSpacing(), Image.SCALE_SMOOTH),
                        ImageIO.read(new File("src/main/java/display/static/wrench.png")).getScaledInstance(getBoxSize() - getSpacing(), getBoxSize() - getSpacing(), Image.SCALE_SMOOTH),
                        ImageIO.read(new File("src/main/java/display/static/torch.png")).getScaledInstance(getBoxSize() - getSpacing(), getBoxSize() - getSpacing(), Image.SCALE_SMOOTH),
                        ImageIO.read(new File("src/main/java/display/static/nails.png")).getScaledInstance(getBoxSize() - getSpacing(), getBoxSize() - getSpacing(), Image.SCALE_SMOOTH),
                        ImageIO.read(new File("src/main/java/display/static/brick.png")).getScaledInstance(getBoxSize() - getSpacing(), getBoxSize() - getSpacing(), Image.SCALE_SMOOTH),
                        ImageIO.read(new File("src/main/java/display/static/battery.png")).getScaledInstance(getBoxSize() - getSpacing(), getBoxSize() - getSpacing(), Image.SCALE_SMOOTH),
                        ImageIO.read(new File("src/main/java/display/static/drill.png")).getScaledInstance(getBoxSize() - getSpacing(), getBoxSize() - getSpacing(), Image.SCALE_SMOOTH),
                        ImageIO.read(new File("src/main/java/display/static/screwdriver.png")).getScaledInstance(getBoxSize() - getSpacing(), getBoxSize() - getSpacing(), Image.SCALE_SMOOTH),
                        ImageIO.read(new File("src/main/java/display/static/saw.png")).getScaledInstance(getBoxSize() - getSpacing(), getBoxSize() - getSpacing(), Image.SCALE_SMOOTH),
                        ImageIO.read(new File("src/main/java/display/static/crowbar.png")).getScaledInstance(getBoxSize() - getSpacing(), getBoxSize() - getSpacing(), Image.SCALE_SMOOTH),
                        ImageIO.read(new File("src/main/java/display/static/pickaxe.png")).getScaledInstance(getBoxSize() - getSpacing(), getBoxSize() - getSpacing(), Image.SCALE_SMOOTH),
                        ImageIO.read(new File("src/main/java/display/static/wires.png")).getScaledInstance(getBoxSize() - getSpacing(), getBoxSize() - getSpacing(), Image.SCALE_SMOOTH),
                        ImageIO.read(new File("src/main/java/display/static/chainsaw.png")).getScaledInstance(getBoxSize() - getSpacing(), getBoxSize() - getSpacing(), Image.SCALE_SMOOTH)
                );
            } catch (Exception e) {


            }
        }
        showVisualisation(warehouse, shortestPath, locationsToVisit);
    }

    public int getBoxSize() {
        return this.boxSize;
    }

    public int getSpacing() {
        return this.spacing;
    }

    protected void showVisualisation(Warehouse warehouse, List<Path> shortestPath, ArrayList<Point> locationsToVisit) {
        SwingUtilities.invokeLater(() -> {
            this.remove(visualisationPanel);
            if (visualisation != null) {
                this.remove(visualisation);
            }
            changeEmulationSpeedBySliderVal(); // to instantiate initial Speed value, if it was changed before Processing order
            visualisation = new VisualizationTool(warehouse, shortestPath, locationsToVisit, this.images);
            visualisation.setLocation(visualisationPanel.getWidth() / 2, visualisationPanel.getHeight() / 2);
            changeEmulationSpeedBySliderVal();
            this.add(visualisation);
            this.invalidate();
            this.revalidate();
        });
    }

    private static void addToLogger(String newMessage) {
        String loggerMessageBuilder = logTextArea.getText() +
                System.lineSeparator() +
                newMessage;
        logTextArea.setText(loggerMessageBuilder);
    }

    private static void addToRobotLogger(String command, ItemOrder itemOrder) {
        StringBuilder loggerMessageBuilder = new StringBuilder();
        loggerMessageBuilder.append(robotLoggerTextArea.getText());
        switch (command.toLowerCase()) {
            case "taken" -> {
                loggerMessageBuilder.append(System.lineSeparator())
                        .append(command.toUpperCase())
                        .append(System.lineSeparator())
                        .append(itemOrder.name().split(" ")[0])
                        .append(System.lineSeparator())
                        .append("Weight: ").append(itemOrder.weight())
                        .append(System.lineSeparator())
                        .append("Qty: ").append(itemsTaken)
                        .append(System.lineSeparator());
            }
            case "delivered", "processing", "completed" -> {
                loggerMessageBuilder.append(System.lineSeparator())
                        .append(command.toUpperCase())
                        .append(System.lineSeparator());
            }
            default -> {
                loggerMessageBuilder.append(System.lineSeparator())
                        .append(command)
                        .append(System.lineSeparator());
            }
        }
        robotLoggerTextArea.setText(loggerMessageBuilder.toString());
    }

    void addSelectedItemToOrderList() {
        if (order.size() == ORDER_SIZE_MAX && order.stream().noneMatch(item -> item.name().equals(selectedItem.split(",")[0]))) {
            maxSizeDialog.setVisible(true);
            addToLogger("Maximum Order List size was reached");
        } else {
            Optional<ItemOrder> optionalItem = getItemIfPresentInOrderList();
            int quantity = (int) qtySpinner.getValue();

            if (optionalItem.isEmpty()) {
                String name = selectedItem.split(" ")[0];
                if (items.contains(selectedItem)) {
                    order.add(new ItemOrder(selectedItem, warehouse.getItemLocation(name), quantity, warehouse.getItem(name).weight()));
                    addToLogger("Item  " + selectedItem.split(" ")[0] + " of quantity (" + quantity + ") was added to order");
                    updateOrderScrollPanel();
                } else {
                    addToLogger("No such item in the Item DB or it wasn't selected");
                }
            } else {
                ItemOrder previousItem = optionalItem.get();
                if (previousItem.quantity() == quantity) {
                    addToLogger("The quantity of selected item hasn't been changed in the order. Nothing to update.");
                } else {
                    String name = selectedItem.split(" ")[0];
                    order.remove(previousItem);
                    order.add(new ItemOrder(selectedItem, warehouse.getItemLocation(name), quantity, warehouse.getItem(name).weight()));
                    updateOrderScrollPanel();
                    String updateMessageMask = "The quantity of item '%s' was updated (%d) -> (%d)";
                    String updateMessage = String.format(updateMessageMask, previousItem.name().split(" ")[0], previousItem.quantity(), quantity);
                    addToLogger(updateMessage);
                }
            }
        }
    }

    private Optional<ItemOrder> getItemIfPresentInOrderList() {

        return order.stream().filter(itemOrder -> itemOrder.name().equals(selectedItem)).findFirst();
    }

    void removeSelectedItemFromOrderList() {
        if (isOrderListFocused) {
            if (order.isEmpty()) {
                addToLogger("'Remove' pressed, but the order list is empty");
            } else if (selectedItem != null) {
                String selectedItemName = selectedItem.split(",")[0];
                if (items.contains(selectedItemName)) {
                    ItemOrder itemToRemove = order.stream()
                            .filter(itemOrder -> itemOrder.name().equals(selectedItemName))
                            .findAny().orElseThrow(() -> new IllegalArgumentException("Item " + selectedItemName + " is not in the order list"));
                    order.remove(itemToRemove);
                    addToLogger("Item  " + selectedItemName.split(" ")[0] + " removed from order");
                    updateOrderScrollPanel();
                } else {
                    addToLogger("No such item in the Item DB or it wasn't selected");
                }
            }
        } else {
            addToLogger("Please, select item from the Order List to remove it (not form DB)");
        }
    }

    void changeEmulationSpeedBySliderVal() {
        emulationSpeed = emulationSpeedSlider.getValue();
        if (visualisation != null) {
            if (emulationSpeed == emulationSpeedSlider.getMaximum()) {
                emulationSpeed -= emulationSpeedSlider.getMinimum();
            }
            visualisation.getTimer().setDelay(emulationSpeedSlider.getMaximum() - emulationSpeed);
        }
    }

    static void clearOrderList() {
        order.clear();
        updateOrderScrollPanel();
        addToLogger("Order List cleared");
    }


    static class StepListener implements ActionListener {

        private static ItemOrder itemOrder = null;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!visualisation.isOrderFinished()) {
                Point previousRobotCoords = getCurrentRobotLocation(new Point(visualisation.getCurX(), visualisation.getCurY()));

                visualisation.setStep(visualisation.getStep() + 1);
                visualisation.setCurx(visualisation.getCurX() + 1);
                visualisation.repaint();

                Point currentRobotCoordinates = getCurrentRobotLocation(new Point(visualisation.getCurX(), visualisation.getCurY()));
                if (previousRobotCoords != currentRobotCoordinates) {
                    Point indexesOfLocation = new Point(visualisation.getCurX(), visualisation.getCurY());
                    Point currRobotLocation = getCurrentRobotLocation(indexesOfLocation);
                    monitorOrderCompletion(currRobotLocation);
                    if (itemOrder != null)
                        monitorDroppingItems(currRobotLocation);
                }
            } else {
                visualisation.getTimer().stop();
                clearOrderList();
                addToLogger("Order info: " + lastOrderDetails);
                addToLogger("Order is completed!");
                addToRobotLogger("Completed", null);

                addItemButton.setEnabled(true);
                removeItemButton.setEnabled(true);
                clearOrderButton.setEnabled(true);
                visualisation = null;
                itemOrder = null;
            }
        }

        private static Point getCurrentRobotLocation(Point indexes) {
            return visualisation.getPath().get(indexes.getY()).getPath().get(indexes.getX());
        }

        private static void monitorDroppingItems(Point robotsCurrentPoint) {
            Point dispatchedArea = visualisation.getDispatchAreaLocation();
            if (dispatchedArea.equals(robotsCurrentPoint)) {
                addToRobotLogger("Delivered", itemOrder);
                addToLogger("Items delivered to Dispatched Area");
                remainingCapacity = 10;
            }
        }

        private static void monitorOrderCompletion(Point currRobotLocation) {
            List<Point> locationsToVisit = visualisation.getOrder();
            if (locationsToVisit.contains(currRobotLocation)) { // if robot current location is the location of the item
                // find item from the orderList
                itemOrder = order.stream()
                        .filter(item -> item.location().equals(currRobotLocation))
                        .findFirst()
                        .orElse(new ItemOrder("No name", new Point(0, 0), 0, 0));

                itemsTaken = Math.min(remainingCapacity / itemOrder.weight(), itemOrder.quantity());

                order.remove(itemOrder);
                order.add(new ItemOrder(itemOrder.name(), itemOrder.location(), itemOrder.quantity() - itemsTaken, itemOrder.weight()));

                String message = String.format("Item taken. Name: %s, Quantity: %d", itemOrder.name().split(" ")[0], itemsTaken);
                addToLogger(message);

                addToRobotLogger("Taken", itemOrder);

                remainingCapacity -= itemsTaken * itemOrder.weight();
            }
        }
    }

    public String getLoggerText() {
        return logTextArea.getText();
    }

    public String getRobotLoggerText() {
        return robotLoggerTextArea.getText();
    }

    public void setQuantity(int quantity) {
        qtySpinner.setValue(quantity);
    }

    public int getQuantity() {
        return (int) qtySpinner.getValue();
    }

    public Set<ItemOrder> getOrderList() {
        return order;
    }

    public VisualizationTool getVisualisation() {
        return visualisation;
    }

    public void setEmulationSpeedToMinimum() {
        visualisation.getTimer().setDelay(0);
    }

    public void setMaxDialogInvisible() {
        maxSizeDialog.setVisible(false);
        this.remove(maxSizeDialog);
    }

    public void clearContentOfOrderList() {
        order.clear();
    }

    private static final String robotASCIIEasterEgg = """




            ▒█▓▒
                ▓
               █████
             █████████
            █░▓░███░▓░█
            ███████████
             ██▓▒▒▒▓██
               ░░░░░
             ▒▒▓▓▓▓▓▒▒
             ▓▒█████▒▓
            ▒▓ █████ ▓▒
            ▒▓ ▓▓ ▓▓ ▓▒
               ▓▓ ▓▓


              ╔════╗
              ║╔╗╔╗║
              ╚╝║║╚╝
              ──║║──
              ──║║──
              ──╚╝──
              ─╔═══╗
              ─║╔══╝
              ─║╚══╗
              ─║╔══╝
              ─║╚══╗
              ─╚═══╝
              ─╔═══╗
              ─║╔═╗║
              ─║║─║║
              ─║╚═╝║
              ─║╔═╗║
              ─╚╝─╚╝
              ─╔═╗╔═╗
              ─║║╚╝║║
              ─║╔╗╔╗║
              ─║║║║║║
              ─║║║║║║
              ─╚╝╚╝╚╝
              
              ─╔═══╗─
              ─║╔══╝─
              ─║╚══╗─
              ─╚══╗║─
              ─╔══╝║─
              ─╚═══╝─


                        """;
}
