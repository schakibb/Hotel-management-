package view.components.roomComponents;

import model.hotel.RoomType;
import net.miginfocom.swing.MigLayout;
import view.components.OurButton;
import view.components.items.MyTextField;
import view.components.sacrollBar.ScrollBar;
import view.components.table.Table;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.LinkedHashMap;

public  class  RoomsPanelManager extends JPanel {

    protected Table table;
    public String[] getColumnNames() {
        return new String[] { "RoomNumber", "RoomType", "Available" };
    }

    /**
     * LinkedHashMap to hold the room management panels (room management panel contains: a panel to manipulate room data and a table)
     * */

    private final LinkedHashMap<String, JPanel> roomsManagements;
    /**
     * Represents the big Panel that holds all four room categories.
     * */
    public RoomsPanelManager(){
        setLayout(new MigLayout("wrap 1, insets 20 0 20 0,center", "push[100%,fill]push", "push[]push"));
        setBackground(new Color(242, 242, 242));
        roomsManagements = new LinkedHashMap<>();
        createRoomManagementPanel();
    }


    private void createRoomManagementPanel() {

        JPanel panelToScroll = new JPanel(new MigLayout("wrap 1,center", "push[95%,fill]push", "20[]10[]20"));
        // Creating scroll pane for the table


        for (RoomType roomType : RoomType.values()){
            JPanel roomPanel = new JPanel(new MigLayout("wrap 3, insets 10", "[][fill][fill]"));
            Border border = BorderFactory.createTitledBorder(roomType.toString() + " rooms");
            roomPanel.setBorder(border);

            JLabel roomImage = new JLabel();
            ImageIcon icon=new ImageIcon("hotelproject/src/main/java/view/icons/"+roomType+"Room.png");
            icon=new ImageIcon(icon.getImage().getScaledInstance(250, 180, Image.SCALE_SMOOTH));
            roomImage.setIcon(icon);
            roomImage.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 1));

            JPanel roomInputPanel = new JPanel(new MigLayout("wrap 2, insets 5,gap 5", "[35%][grow]", "push[]10[]push"));
            roomInputPanel.setBorder(BorderFactory.createSoftBevelBorder(2));

            // Creating JLabels for room input
            JLabel roomPriceLb = new JLabel("Room price:");
            roomPriceLb.setFont(new Font("SansSerif", Font.BOLD, 14)); // Setting font for roomPriceLb
            JLabel roomAvailabilityLb = new JLabel("Availability:");
            roomAvailabilityLb.setFont(new Font("SansSerif", Font.BOLD, 14)); // Setting font for roomAvailabilityLb


            MyTextField roomPriceInput = new MyTextField("Price", "/view/icons/user.png");
            roomPriceInput.setPreferredSize(new Dimension(100, 30));
            JCheckBox roomAvailabilityCheckBox = new JCheckBox();
            roomAvailabilityCheckBox.setBackground(Color.WHITE);

            roomInputPanel.add(roomPriceLb);
            roomInputPanel.add(roomPriceInput, "pushx, growx");
            roomInputPanel.add(roomAvailabilityLb);
            roomInputPanel.add(roomAvailabilityCheckBox, "growx, width 30%");
            roomInputPanel.setBackground(Color.WHITE);

            JPanel buttonPanel = new JPanel(new MigLayout("wrap", "push[fill]push", "push[]10[]push"));

//        // Creating and configuring add button
            OurButton addButton = new OurButton("Add");
            addButton.setButtonBgColor(new Color(0, 112, 255));
      //      addButton.addActionListener(addActionListener(roomPriceInput ,roomAvailabilityCheckBox));
            buttonPanel.add(addButton, "w 50%, h 34");
//
//        // Creating and configuring update button
            OurButton updateButton = new OurButton("Update");
            updateButton.setButtonBgColor(new Color(0, 112, 255));
//        updateButton.addActionListener(updateActionListener());
            buttonPanel.add(updateButton, "w 50%, h 34");
//
//        // Creating and configuring delete button
            OurButton deleteButton = new OurButton("Delete");
            deleteButton.setButtonBgColor(new Color(0xED1B24));
//          deleteButton.addActionListener(deleteActionListener());
            buttonPanel.add(deleteButton, "w 50%, h 34");

            buttonPanel.setBackground(Color.WHITE);

            roomPanel.add(roomImage, "center");
            roomPanel.add(roomInputPanel, "center,pushx, growx");
            roomPanel.add(buttonPanel, "center,pushx, growx");

            roomPanel.setBackground(Color.WHITE);
            ////////////////////////table UI////////////////////////

            JScrollPane spTable = new JScrollPane();
            // Creating table
            table = new Table();


            // Setting border for the scroll pane
            spTable.setBorder(null);

            // Setting model for the table
            table.setModel(new DefaultTableModel(new Object[][] {}, getColumnNames()) {
                private final boolean[] canEdit = new boolean[getColumnNames().length];
                {
                    Arrays.fill(canEdit, false);
                }
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
            });
            // Setting table view in the scroll pane
            spTable.setViewportView(table);



            // Setting vertical scrollbar for the table
            spTable.setVerticalScrollBar(new ScrollBar());
            // Setting background color for the vertical scrollbar and viewport
            spTable.getVerticalScrollBar().setBackground(Color.WHITE);
            spTable.getViewport().setBackground(Color.WHITE);

            // Setting corner panel color to match background
            JPanel corner = new JPanel();
            corner.setBackground(Color.WHITE);
            spTable.setCorner(JScrollPane.UPPER_RIGHT_CORNER, corner);
            spTable.setBorder(BorderFactory.createEmptyBorder());



            //Adding extra panel to add a Table later
            JPanel container=new JPanel();
            container.setLayout(new MigLayout("wrap 1,center","[grow,fill]","[grow,fill]"));
            container.setBackground(Color.WHITE);

            //button to hide the table
            OurButton HideTableButton = new OurButton("Show");
            HideTableButton.setButtonBgColor(new Color(0, 112, 255));
            HideTableButton.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(spTable.isVisible()){
                                spTable.setVisible(false);
                                HideTableButton.setText("Hide");
                                container.remove(spTable);
                                container.revalidate();
                                container.repaint();
                            }else{
                                spTable.setVisible(true);
                                container.add(spTable, "push, grow, gap 60 10 10 10");
                                HideTableButton.setText("Show");

                            }
                        }
                    }
            );


            container.add(roomPanel,"push, grow, gap 10 10 10 10");
            container.add(HideTableButton , "align center , pushx, growx , w 50%");

            panelToScroll.add(container, "push, grow");
            roomsManagements.put(roomType.toString(), container);
        }
        panelToScroll.setBackground(new Color(242, 242, 242));
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(panelToScroll);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setVerticalScrollBar(new ScrollBar());
        scrollPane.setHorizontalScrollBar(new ScrollBar());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBackground(new Color(242, 242, 242));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        add(scrollPane, "push, grow");
    }

//    public ActionListener addActionListener(MyTextField roomPriceInput , JCheckBox roomAvailabilityCheckBox) {
//        return new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    String roomPrice =roomPriceInput.getText();
//                    boolean roomAvailability = roomAvailabilityCheckBox.isSelected();
//
//                    // Create a new row with the room number, price, and availability
//                    Object[] newRow = new Object[] {table.getRowCount() + 1, roomPrice, roomAvailability};
//
//                    // Add the new row to the table's model
//                    DefaultTableModel model = (DefaultTableModel) table.getModel();
//                    model.addRow(newRow);
//                } catch (Exception ex) {
//
//                }
//            }
//        };

//    private void addTablesToRoomsManagements(){
//        for (String roomType : roomsManagements.keySet()){
//            JPanel roomManagement = roomsManagements.get(roomType);
//            // TODO: Add table to roomManagement
//
//        }
//    }


}
