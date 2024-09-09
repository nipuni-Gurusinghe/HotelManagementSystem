import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class AvailableRooms extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private AvailableRoomOperation availableRoomOperation;

    public AvailableRooms(String roomType, Date checkInDate, Date checkOutDate) {
        setTitle("Available Rooms");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        availableRoomOperation = new AvailableRoomOperation();


        Navbar navbar = new Navbar();
        add(navbar, BorderLayout.NORTH);

        // Create the table model with an Image column
        String[] columnNames = {"Room ID", "Status", "Price for One Day", "Booked Dates", "Image", "Action"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only the "Action" column is editable (for buttons)
            }

            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if (column == 5) {
                    return new ButtonRenderer();
                } else if (column == 4) {
                    return new ImageRenderer();
                }
                return super.getCellRenderer(row, column);
            }

            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                if (column == 5) {
                    return new ButtonEditor(new JCheckBox());
                }
                return super.getCellEditor(row, column);
            }
        };
        table.setRowHeight(100);//image ek pena widihata height ek wadi kara

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        populateTable(roomType, checkInDate, checkOutDate);
        FooterPanel footerPanel = new FooterPanel();
        add(footerPanel, BorderLayout.SOUTH);


        setVisible(true);
    }

    private void populateTable(String roomType, Date checkInDate, Date checkOutDate) {
        List<Object[]> roomDataList = availableRoomOperation.getAvailableRooms(roomType);
        for (Object[] row : roomDataList) {
            tableModel.addRow(row);
        }
    }

    // Renderer for the "Online Book" button
    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Online Book" : value.toString());
            return this;
        }
    }

    // Editor for the "Online Book" button
    private class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            label = (value == null) ? "Online Book" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                int selectedRow = table.getSelectedRow();
                String roomId = table.getValueAt(selectedRow, 0).toString();
                double priceForOneDay = (double) table.getValueAt(selectedRow, 2);

                // Pass roomId and priceForOneDay to the OnlineBooking class
                new OnlineBooking(roomId, priceForOneDay);
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

    // Renderer for the Image column
    private class ImageRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = new JLabel();
            if (value != null) {
                ImageIcon imageIcon = new ImageIcon((String) value);
                Image image = imageIcon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(image));
            }
            return label;
        }
    }

    public static void main(String[] args) {
        new AvailableRooms("Luxury", new Date(), new Date());
    }
}
