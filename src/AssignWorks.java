import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class AssignWorks extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public AssignWorks() {
        setTitle("Assign Works");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());


        getContentPane().setBackground(new Color(173, 216, 230));


        Navbar navbar = new Navbar();
        add(navbar, BorderLayout.NORTH);

        // Create the table model
        String[] columnNames = {"Employee ID", "Name", "Role", "Image", "Action"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only the "Action" column is editable (for buttons)
            }

            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if (column == 3) {
                    return new ImageRenderer();
                } else if (column == 4) {
                    return new ButtonRenderer();
                }
                return super.getCellRenderer(row, column);
            }

            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                if (column == 4) {
                    return new ButtonEditor(new JCheckBox());
                }
                return super.getCellEditor(row, column);
            }
        };
        table.setRowHeight(60);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Populate the table with employee data
        populateTable();


        FooterPanel footerPanel = new FooterPanel();
        add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void populateTable() {
        try {
            Connection con = DatabaseConnection.getConnection();
            Statement stmt = con.createStatement();
            String query = "SELECT EmployeeID, FullName, Role, Image FROM Employee";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String employeeID = rs.getString("EmployeeID");
                String fullName = rs.getString("FullName");
                String role = rs.getString("Role");
                String imagePath = rs.getString("Image");

                // Load the image from the path
                ImageIcon imageIcon = null;
                if (imagePath != null && !imagePath.trim().isEmpty()) {
                    File imageFile = new File(imagePath);
                    if (imageFile.exists()) {
                        BufferedImage img = ImageIO.read(imageFile);
                        Image dimg = img.getScaledInstance(90, 60, Image.SCALE_SMOOTH);
                        imageIcon = new ImageIcon(dimg);
                    }
                }

                // Add the employee details and the image to the table model
                tableModel.addRow(new Object[]{employeeID, fullName, role, imageIcon, "Assign Work"});
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Renderer for the image
    private class ImageRenderer extends JLabel implements TableCellRenderer {
        public ImageRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            if (value != null) {
                setIcon((ImageIcon) value);
            } else {
                setIcon(null); // Handle case where there is no image
            }
            return this;
        }
    }

    // Renderer for the "Assign Work" button
    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "Assign Work" : value.toString());
            return this;
        }
    }

    // Editor for the "Assign Work" button
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
            label = (value == null) ? "Assign Work" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                int selectedRow = table.getSelectedRow();
                String employeeID = table.getValueAt(selectedRow, 0).toString();
                String employeeName = table.getValueAt(selectedRow, 1).toString();

                // Pass employeeID and employeeName to the AssignWorkOperation class or another operation
                AssignWorkOperation.assignWork(employeeID, employeeName);
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

    public static void main(String[] args) {
        new AssignWorks();
    }
}
