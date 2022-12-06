package display;

import main.Main;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static main.Main.main;

class UserInterfaceTest {

    UserInterface UI;

    @BeforeEach
    void initUI() throws Exception {
        main(new String[]{"Launch UI in invisible mode"});
        UI = Main.UI;
        UI.clearContentOfOrderList();
    }

    @Test
    void when_ui_is_active_then_WRCS_switches_to_active_state() {
        String expectedInitialMessage = "Warehouse Robotic Control System launched successfully";
        String actualLogger = UI.getLoggerText().trim();

        assertEquals(expectedInitialMessage, actualLogger);
    }

    @Test
    void when_order_list_is_empty_then_get_an_error_message() {
        String expectedMessage = System.lineSeparator() +
                "Warehouse Robotic Control System launched successfully" + System.lineSeparator() +
                "Empty order. Cannot process the order!";

        UI.processOrder();

        assertAll(() -> {
            assertTrue(UI.getOrderList().isEmpty());
            assertEquals(expectedMessage, UI.getLoggerText());
        });
    }


    @Test
    void when_quantity_is_updated_then_order_list_quantity_is_updated() {
        String selectedItem = "Hammer 1kg";
        int initialQty = 1;
        int updatedQty = 7;
        String expectedMessage = System.lineSeparator() +
                "Warehouse Robotic Control System launched successfully" + System.lineSeparator() +
                "Item  Hammer of quantity (1) was added to order" + System.lineSeparator() +
                "The quantity of item 'Hammer' was updated (1) -> (7)";

        UI.selectedItem = selectedItem;
        UI.setQuantity(initialQty);
        UI.addSelectedItemToOrderList();

        UI.setQuantity(updatedQty);
        UI.addSelectedItemToOrderList();

        assertEquals(expectedMessage, UI.getLoggerText());
    }

    @Test
    void when_present_item_is_removed_then_order_list_is_updated() {
        String selectedItem = "Chainsaw 4kg";
        String expectedMessage = System.lineSeparator() +
                "Warehouse Robotic Control System launched successfully" + System.lineSeparator() +
                "Item  Chainsaw of quantity (1) was added to order" + System.lineSeparator() +
                "Item  Chainsaw removed from order";

        UI.selectedItem = selectedItem;
        UI.addSelectedItemToOrderList();

        UI.isOrderListFocused = true;
        UI.removeSelectedItemFromOrderList();

        assertAll(() -> {
            assertEquals(expectedMessage, UI.getLoggerText());
            assertTrue(UI.getOrderList().isEmpty());
        });
    }

    @Test
    void when_valid_order_with_locations_less_than_constraint_then_get_correct_loggers() throws InterruptedException {
        String firstItem = "Hammer 1kg";
        String secondItem = "Wrench 1kg";
        String thirdItem = "Saw 1kg";

        int firstQty = 2;
        int secondQty = 4;
        int thirdQty = 6;

        addItemToOrderList(firstItem, firstQty);
        addItemToOrderList(secondItem, secondQty);
        addItemToOrderList(thirdItem, thirdQty);

        UI.processOrder();

        System.out.println("Waiting for Visualisation Tool to initialise...");

        waitForVisualisationToolInitialises();

        System.out.println("Visualisation Tool was initialised successfully...");
        System.out.println("Running emulation....");

        UI.setEmulationSpeedToMinimum();

        waitTillEmulationIsFinished();
        System.out.println("Emulation finished!");

        String expectedLogger = System.lineSeparator() +
                "Warehouse Robotic Control System launched successfully" + System.lineSeparator() +
                "Item  Hammer of quantity (2) was added to order" + System.lineSeparator() +
                "Item  Wrench of quantity (4) was added to order" + System.lineSeparator() +
                "Item  Saw of quantity (6) was added to order" + System.lineSeparator() +
                "Robot started processing order" + System.lineSeparator() +
                "Item taken. Name: Wrench, Quantity: 4" + System.lineSeparator() +
                "Item taken. Name: Saw, Quantity: 6" + System.lineSeparator() +
                "Items delivered to Dispatched Area" + System.lineSeparator() +
                "Item taken. Name: Hammer, Quantity: 2" + System.lineSeparator() +
                "Items delivered to Dispatched Area" + System.lineSeparator() +
                "Order List cleared" + System.lineSeparator() +
                "Order info: [{name='Wrench', quantity=4}, {name='Hammer', quantity=2}, {name='Saw', quantity=6}]; Total weight = 12 kg" + System.lineSeparator() +
                "Order is completed!";
        String expectedRobotLogger = System.lineSeparator() +
                "PROCESSING" + System.lineSeparator() +
                System.lineSeparator() +
                "TAKEN" + System.lineSeparator() +
                "Wrench" + System.lineSeparator() +
                "Weight: 1" + System.lineSeparator() +
                "Qty: 4" + System.lineSeparator() +
                System.lineSeparator() +
                "TAKEN" + System.lineSeparator() +
                "Saw" + System.lineSeparator() +
                "Weight: 1" + System.lineSeparator() +
                "Qty: 6" + System.lineSeparator() +
                System.lineSeparator() +
                "DELIVERED" + System.lineSeparator() +
                System.lineSeparator() +
                "TAKEN" + System.lineSeparator() +
                "Hammer" + System.lineSeparator() +
                "Weight: 1" + System.lineSeparator() +
                "Qty: 2" + System.lineSeparator() +
                System.lineSeparator() +
                "DELIVERED" + System.lineSeparator() +
                System.lineSeparator() +
                "COMPLETED" + System.lineSeparator();

        assertAll(() -> {
            assertEquals(expectedLogger, UI.getLoggerText());
            assertEquals(expectedRobotLogger, UI.getRobotLoggerText());
        });

        System.out.println("-".repeat(50));

    }

    @Test
    void when_valid_order_with_locations_exceeding_constraint_then_get_warning_message() {
        String firstItem = "Hammer 1kg";
        String secondItem = "Wrench 1kg";
        String thirdItem = "Saw 1kg";
        String fourthItem = "Torch 1kg";
        String fifthItem = "Chainsaw 4kg";
        String sixthItem = "Helmet 1kg";

        addItemToOrderList(firstItem, 2);
        addItemToOrderList(secondItem, 4);
        addItemToOrderList(thirdItem, 6);
        addItemToOrderList(fourthItem, 6);
        addItemToOrderList(fifthItem, 6);
        addItemToOrderList(sixthItem, 6);

        UI.setMaxDialogInvisible();


        String expectedLogger = System.lineSeparator() +
                "Warehouse Robotic Control System launched successfully" + System.lineSeparator() +
                "Item  Hammer of quantity (2) was added to order" + System.lineSeparator() +
                "Item  Wrench of quantity (4) was added to order" + System.lineSeparator() +
                "Item  Saw of quantity (6) was added to order" + System.lineSeparator() +
                "Item  Torch of quantity (6) was added to order" + System.lineSeparator() +
                "Item  Chainsaw of quantity (6) was added to order" + System.lineSeparator() +
                "Maximum Order List size was reached";

            assertEquals(expectedLogger, UI.getLoggerText());
    }

    @Test
    void when_valid_order_with_total_weight_bigger_than_max_capacity_then_robot_splits_order() {
        String firstItem = "Hammer 1kg";
        String secondItem = "Wrench 1kg";
        String thirdItem = "Saw 1kg";
        String fourthItem = "Torch 1kg";
        String fifthItem = "Chainsaw 4kg";
        String sixthItem = "Helmet 1kg";

        addItemToOrderList(firstItem, 2);
        addItemToOrderList(secondItem, 4);
        addItemToOrderList(thirdItem, 6);
        addItemToOrderList(fourthItem, 6);
        addItemToOrderList(fifthItem, 10);

        UI.processOrder();

        System.out.println("Waiting for Visualisation Tool to initialise...");

        waitForVisualisationToolInitialises();

        System.out.println("Visualisation Tool was initialised successfully...");
        System.out.println("Running emulation....");

        UI.setEmulationSpeedToMinimum();

        waitTillEmulationIsFinished();
        System.out.println("Emulation finished!");

        String expectedLogger = System.lineSeparator() +
                "Warehouse Robotic Control System launched successfully" + System.lineSeparator() +
                "Item  Hammer of quantity (2) was added to order" + System.lineSeparator() +
                "Item  Wrench of quantity (4) was added to order" + System.lineSeparator() +
                "Item  Saw of quantity (6) was added to order" + System.lineSeparator() +
                "Item  Torch of quantity (6) was added to order" + System.lineSeparator() +
                "Item  Chainsaw of quantity (10) was added to order" + System.lineSeparator() +
                "Robot started processing order" + System.lineSeparator() +
                "Item taken. Name: Wrench, Quantity: 4" + System.lineSeparator() +
                "Item taken. Name: Saw, Quantity: 6" + System.lineSeparator() +
                "Items delivered to Dispatched Area" + System.lineSeparator() +
                "Item taken. Name: Chainsaw, Quantity: 2" + System.lineSeparator() +
                "Items delivered to Dispatched Area" + System.lineSeparator() +
                "Item taken. Name: Chainsaw, Quantity: 2" + System.lineSeparator() +
                "Items delivered to Dispatched Area" + System.lineSeparator() +
                "Item taken. Name: Chainsaw, Quantity: 2" + System.lineSeparator() +
                "Items delivered to Dispatched Area" + System.lineSeparator() +
                "Item taken. Name: Chainsaw, Quantity: 2" + System.lineSeparator() +
                "Items delivered to Dispatched Area" + System.lineSeparator() +
                "Item taken. Name: Chainsaw, Quantity: 2" + System.lineSeparator() +
                "Item taken. Name: Torch, Quantity: 2" + System.lineSeparator() +
                "Items delivered to Dispatched Area" + System.lineSeparator() +
                "Item taken. Name: Torch, Quantity: 4" + System.lineSeparator() +
                "Item taken. Name: Hammer, Quantity: 2" + System.lineSeparator() +
                "Items delivered to Dispatched Area" + System.lineSeparator() +
                "Order List cleared" + System.lineSeparator() +
                "Order info: [{name='Wrench', quantity=4}, {name='Torch', quantity=6}, {name='Chainsaw', quantity=10}, {name='Hammer', quantity=2}, {name='Saw', quantity=6}]; Total weight = 58 kg" + System.lineSeparator() +
                "Order is completed!";


        String expectedRobotLogger = System.lineSeparator() +
                "PROCESSING" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "TAKEN" + System.lineSeparator() +
                "Wrench" + System.lineSeparator() +
                "Weight: 1" + System.lineSeparator() +
                "Qty: 4" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "TAKEN" + System.lineSeparator() +
                "Saw" + System.lineSeparator() +
                "Weight: 1" + System.lineSeparator() +
                "Qty: 6" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "DELIVERED" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "TAKEN" + System.lineSeparator() +
                "Chainsaw" + System.lineSeparator() +
                "Weight: 4" + System.lineSeparator() +
                "Qty: 2" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "DELIVERED" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "TAKEN" + System.lineSeparator() +
                "Chainsaw" + System.lineSeparator() +
                "Weight: 4" + System.lineSeparator() +
                "Qty: 2" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "DELIVERED" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "TAKEN" + System.lineSeparator() +
                "Chainsaw" + System.lineSeparator() +
                "Weight: 4" + System.lineSeparator() +
                "Qty: 2" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "DELIVERED" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "TAKEN" + System.lineSeparator() +
                "Chainsaw" + System.lineSeparator() +
                "Weight: 4" + System.lineSeparator() +
                "Qty: 2" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "DELIVERED" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "TAKEN" + System.lineSeparator() +
                "Chainsaw" + System.lineSeparator() +
                "Weight: 4" + System.lineSeparator() +
                "Qty: 2" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "TAKEN" + System.lineSeparator() +
                "Torch" + System.lineSeparator() +
                "Weight: 1" + System.lineSeparator() +
                "Qty: 2" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "DELIVERED" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "TAKEN" + System.lineSeparator() +
                "Torch" + System.lineSeparator() +
                "Weight: 1" + System.lineSeparator() +
                "Qty: 4" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "TAKEN" + System.lineSeparator() +
                "Hammer" + System.lineSeparator() +
                "Weight: 1" + System.lineSeparator() +
                "Qty: 2" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "DELIVERED" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "COMPLETED" + System.lineSeparator();

        assertAll(() -> {
            assertEquals(expectedLogger, UI.getLoggerText());
            assertEquals(expectedRobotLogger, UI.getRobotLoggerText());
        });

        System.out.println("-".repeat(50));

    }

    private void waitTillEmulationIsFinished() {
        while (UI.getVisualisation().getTimer().isRunning()) { }
    }

    private void waitForVisualisationToolInitialises() {
        while (UI.getVisualisation() == null) { }
    }

    private void addItemToOrderList(String selectedItem, int qty) {
        UI.setQuantity(qty);
        UI.selectedItem = selectedItem;
        UI.addSelectedItemToOrderList();
    }
}
