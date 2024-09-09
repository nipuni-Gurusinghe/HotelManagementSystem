import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Properties;

public class GetCheckOutList extends JFrame {

    private JTable checkOutTable;
    private JDatePickerImpl datePicker;

    public GetCheckOutList() {
        setTitle("Check-Out List");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set up the date picker
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        // Set up the search button
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadCheckOutData();
            }
        });

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(173, 216, 230));
        topPanel.add(new JLabel("Select Date:"));
        topPanel.add(datePicker);
        topPanel.add(searchButton);

        checkOutTable = new JTable();
        checkOutTable.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Booking ID", "First Name", "Room No", "Check-Out Date"}
        ));
        checkOutTable.setBackground(new Color(173, 216, 230));
        JTableHeader tableHeader = checkOutTable.getTableHeader();
        tableHeader.setBackground(new Color(173, 216, 230));
        JScrollPane scrollPane = new JScrollPane(checkOutTable);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        // Add components to frame
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void loadCheckOutData() {
        // Get selected date from the date picker
        java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();
        if (selectedDate == null) {
            JOptionPane.showMessageDialog(this, "Please select a date.");
            return;
        }

        // Format the date to match database format
        java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());

        // Call the backend operation to get the list of check-outs for the selected date
        List<Object[]> checkOutData = GetCheckOutListOperation.getCheckOutListByDate(sqlDate);

        // Populate the table model
        DefaultTableModel model = (DefaultTableModel) checkOutTable.getModel();
        model.setRowCount(0);
        for (Object[] row : checkOutData) {
            model.addRow(row);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GetCheckOutList());
    }
}
