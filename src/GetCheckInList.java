import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;
import java.util.Properties;

public class GetCheckInList extends JFrame {

    private JTable checkInTable;
    private JDatePickerImpl datePicker;

    public GetCheckInList() {
        setTitle("Check-In List");
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

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadCheckInData();
            }
        });

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select Date:"));
        topPanel.add(datePicker);
        topPanel.add(searchButton);

        checkInTable = new JTable();
        checkInTable.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Booking ID", "First Name", "Room No", "Check-In Date"}
        ));

        JScrollPane scrollPane = new JScrollPane(checkInTable);

        // Adding Navbar at the top
        Navbar navbar = new Navbar();
        add(navbar, BorderLayout.NORTH);

        // Adding topPanel and scrollPane to the center
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(topPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // Adding Footer at the bottom
        FooterPanel footer = new FooterPanel();
        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadCheckInData() {
        java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();
        if (selectedDate == null) {
            JOptionPane.showMessageDialog(this, "Please select a date.");
            return;
        }
        // Format the date to match the database format
        Date sqlDate = new Date(selectedDate.getTime());
        System.out.println("Selected Date (java.sql.Date): " + sqlDate);

        // Call the backend operation to get the list of check-ins for the selected date
        List<Object[]> checkInData = GetCheckinlistOperation.getCheckInListByDate(sqlDate);

        // Populate the table
        DefaultTableModel model = (DefaultTableModel) checkInTable.getModel();
        model.setRowCount(0);  // Clear the existing data
        for (Object[] row : checkInData) {
            model.addRow(row);
        }
        if (checkInData.isEmpty()) {
            System.out.println("No check-in data found for the selected date.");
        } else {
            System.out.println("Check-in data loaded successfully.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GetCheckInList());
    }
}
