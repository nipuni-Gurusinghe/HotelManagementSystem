import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class CheckOut extends JFrame {

    private JTextField BookingIdField;
    private JDatePickerImpl checkOutDatePicker;
    private JTextField noOfDaysField;
    private JTextField totalChargesField;
    private JTextField otherChargesField;
    private JTextField discountField;
    private JTextField totalField;
    private JTextField amountPaidField;
    private JTextField balanceField;

    public CheckOut() {
        setTitle("Check Out");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        int bannerWidth = 400;
        int bannerHeight = 200;
        ImageIcon bannerIcon = new ImageIcon("C:\\Users\\AMASHA\\Desktop\\HotelManageent\\banner.jpg");
        Image bannerImage = bannerIcon.getImage().getScaledInstance(bannerWidth, bannerHeight, Image.SCALE_SMOOTH);
        JLabel bannerLabel = new JLabel(new ImageIcon(bannerImage));
        add(bannerLabel, BorderLayout.NORTH);
        add(new Navbar(), BorderLayout.NORTH);

        // Main Panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        BookingIdField = createTextField();
        checkOutDatePicker = createDatePicker();
        JButton enterButton = createButton("Enter");
        noOfDaysField = createTextField();
        totalChargesField = createTextField();
        otherChargesField = createTextField();
        discountField = createTextField();
        totalField = createTextField();
        amountPaidField = createTextField();
        balanceField = createTextField();

        // Add fields to the panel
        addLabelAndField(mainPanel, gbc, "Booking ID", BookingIdField, 0, 0);
        addLabelAndField(mainPanel, gbc, "Check Out Date", checkOutDatePicker, 1, 0, enterButton);
        addLabelAndField(mainPanel, gbc, "No. of Days", noOfDaysField, 2, 0);
        addLabelAndField(mainPanel, gbc, "Total Charges", totalChargesField, 3, 0);
        addLabelAndField(mainPanel, gbc, "Other Charges", otherChargesField, 4, 0);
        addLabelAndField(mainPanel, gbc, "Discount", discountField, 5, 0);
        addLabelAndField(mainPanel, gbc, "Total", totalField, 6, 0);
        addLabelAndField(mainPanel, gbc, "Amount Paid", amountPaidField, 7, 0);
        addLabelAndField(mainPanel, gbc, "Balance", balanceField, 8, 0);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        mainPanel.add(new JLabel(""), gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton checkOutButton = new JButton("Check Out");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(checkOutButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        mainPanel.add(buttonPanel, gbc);

        // Add main panel to the center
        add(mainPanel, BorderLayout.CENTER);
        FooterPanel footerPanel = new FooterPanel();
        add(footerPanel, BorderLayout.SOUTH);

        pack();
        setSize(800, 700);
        setLocationRelativeTo(null);  // Center the window
        setVisible(true);

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleEnterButtonClick();
            }
        });

        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCheckOutButtonClick();
            }
        });
    }

    private void handleEnterButtonClick() {
        String bookingId = BookingIdField.getText();
        Date checkOutDate = (Date) checkOutDatePicker.getModel().getValue();

        if (bookingId.isEmpty() || checkOutDate == null) {
            JOptionPane.showMessageDialog(this, "Please fill in both Booking ID and Check Out Date.");
            return;
        }

        CheckOutOperation operation = new CheckOutOperation();
        try {
            Date checkInDate = operation.getCheckInDate(bookingId);

            if (checkInDate != null) {
                long noOfDays = operation.calculateNoOfDays(checkInDate, checkOutDate);
                noOfDaysField.setText(String.valueOf(noOfDays));

                double totalCharges = operation.calculateTotalCharges(bookingId, checkOutDate);
                totalChargesField.setText(String.format("%.2f", totalCharges));

                // Calculate and display Total
                double otherCharges = parseDouble(otherChargesField.getText());
                double discount = parseDouble(discountField.getText());
                double total = operation.calculateTotal(totalCharges, otherCharges, discount);
                totalField.setText(String.format("%.2f", total));

                // Fetch and display Amount Paid
                double amountPaid = operation.getAmountPaid(bookingId);
                amountPaidField.setText(String.format("%.2f", amountPaid));

                // Calculate and display Balance
                double balance = operation.calculateBalance(total, amountPaid);
                balanceField.setText(String.format("%.2f", balance));
            } else {
                JOptionPane.showMessageDialog(this, "Booking ID not found or Check-In Date is missing.");
            }
        } finally {
            operation.closeConnection();
        }
    }


    private void handleCheckOutButtonClick() {
        String bookingId = BookingIdField.getText();
        Date checkOutDate = (Date) checkOutDatePicker.getModel().getValue();

        if (bookingId.isEmpty() || checkOutDate == null) {
            JOptionPane.showMessageDialog(this, "Please fill in both Booking ID and Check Out Date.");
            return;
        }

        CheckOutOperation operation = new CheckOutOperation();
        try {
            boolean success = operation.saveCheckOutDate(bookingId, checkOutDate);
            if (success) {
                JOptionPane.showMessageDialog(this, "Check-out successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save check-out date.");
            }
        } finally {
            operation.closeConnection();
        }
    }

    // Helper method to parse double values with default 0.0
    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }


    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 25));
        return textField;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 25));
        return button;
    }

    //date picker ek hadanna
    private JDatePickerImpl createDatePicker() {
        UtilDateModel model = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        return datePicker;
    }

    private void addLabelAndField(JPanel panel, GridBagConstraints gbc, String labelText, JComponent field, int row, int col, JButton... buttons) {
        gbc.gridx = col;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0.1;
        panel.add(new JLabel(labelText), gbc);

        JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fieldPanel.add(field);
        for (JButton button : buttons) {
            fieldPanel.add(button);
        }

        gbc.gridx = col + 1;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0.9;
        panel.add(fieldPanel, gbc);
    }

    // Date label formatter
    private class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CheckOut());
    }
}
