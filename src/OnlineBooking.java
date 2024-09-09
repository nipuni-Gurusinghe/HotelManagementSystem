import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

public class OnlineBooking extends JFrame {
    private JTextField textRoomId, textPrice, textFullName, textIDNumber;
    private JDatePickerImpl datePickerCheckIn, datePickerCheckOut;
    private JComboBox<String> comboAdults, comboChilds;
    private JButton submitButton;
    private JTextField textBookingId;
    private String bookingId;
    private double priceForOneDay;

    public OnlineBooking(String roomId, double priceForOneDay) {
        this.priceForOneDay = priceForOneDay;
        setTitle("Online Booking");
        setLayout(new BorderLayout());

        // Generate Booking ID
        bookingId = OnlineBookingOperation.generateBookingId();
        if (bookingId == null) {
            JOptionPane.showMessageDialog(null, "Error generating Booking ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createBookingFormPanel(roomId), BorderLayout.CENTER);
        add(new FooterPanel(), BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 700);
        setVisible(true);
    }
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        Navbar navbar = new Navbar();
        headerPanel.add(navbar, BorderLayout.NORTH);

        return headerPanel;
    }

    private JPanel createBookingFormPanel(String roomId) {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        initializeComponents(roomId, priceForOneDay);
        addComponentsToPanel(formPanel, gbc);

        return formPanel;
    }

    private void initializeComponents(String roomId, double priceForOneDay) {
        textRoomId = new JTextField(15);
        textPrice = new JTextField(15);
        textFullName = new JTextField(15);
        textIDNumber = new JTextField(15);
        textBookingId = new JTextField(15);

        textRoomId.setText(roomId);
        textRoomId.setEditable(false);
        textPrice.setText(String.valueOf(priceForOneDay));
        textPrice.setEditable(false);
        textBookingId.setEditable(false);

        // Initialize the properties for the JDatePickers
        Properties p = new Properties();
        SqlDateModel checkInModel = new SqlDateModel();
        JDatePanelImpl checkInDatePanel = new JDatePanelImpl(checkInModel, p);
        datePickerCheckIn = new JDatePickerImpl(checkInDatePanel, new DateLabelFormatter());

        SqlDateModel checkOutModel = new SqlDateModel();
        JDatePanelImpl checkOutDatePanel = new JDatePanelImpl(checkOutModel, p);
        datePickerCheckOut = new JDatePickerImpl(checkOutDatePanel, new DateLabelFormatter());

        String[] adultOptions = {"1", "2", "3", "4", "5", "6"};
        comboAdults = new JComboBox<>(adultOptions);

        String[] childOptions = {"None", "1", "2", "3", "4", "5", "6"};
        comboChilds = new JComboBox<>(childOptions);

        submitButton = new JButton("SUBMIT");
    }

    private void addComponentsToPanel(JPanel formPanel, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Room ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(textRoomId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Price for One Day:"), gbc);
        gbc.gridx = 1;
        formPanel.add(textPrice, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(textFullName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("ID Number:"), gbc);
        gbc.gridx = 1;
        formPanel.add(textIDNumber, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Check-in Date:"), gbc);
        gbc.gridx = 1;
        formPanel.add(datePickerCheckIn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Check-out Date:"), gbc);
        gbc.gridx = 1;
        formPanel.add(datePickerCheckOut, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(new JLabel("Adults:"), gbc);
        gbc.gridx = 1;
        formPanel.add(comboAdults, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(new JLabel("Childs:"), gbc);
        gbc.gridx = 1;
        formPanel.add(comboChilds, gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        formPanel.add(submitButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        formPanel.add(new JLabel("Your Booking ID:"), gbc);
        gbc.gridx = 1;
        formPanel.add(textBookingId, gbc);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fullName = textFullName.getText();
                String idNumber = textIDNumber.getText();
                String checkInDate = datePickerCheckIn.getJFormattedTextField().getText();
                String checkOutDate = datePickerCheckOut.getJFormattedTextField().getText();
                String adults = (String) comboAdults.getSelectedItem();
                String childs = (String) comboChilds.getSelectedItem();
                boolean success = OnlineBookingOperation.saveBookingDetails(
                        bookingId, textRoomId.getText(), fullName, idNumber, checkInDate, checkOutDate,
                        adults, childs
                );

                if (success) {
                    textBookingId.setText(bookingId);
                    submitButton.setEnabled(false); // Disable the button after successful submission
                    showPaymentDialog(priceForOneDay);
                } else {
                    JOptionPane.showMessageDialog(null, "Error saving booking details. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void showPaymentDialog(double priceForOneDay) {
        JDialog paymentDialog = new JDialog(this, "Payment", true);
        paymentDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelMessage = new JLabel("Partial payment is required to confirm booking.");
        JLabel labelCardNo = new JLabel("Card Number:");
        JLabel labelCvv = new JLabel("CVV:");
        JLabel labelAmount = new JLabel("Amount:");

        JTextField textCardNo = new JTextField(15);
        JTextField textCvv = new JTextField(4);
        JTextField textAmount = new JTextField(15);
        textAmount.setText(String.format("%.2f", priceForOneDay / 2));
        textAmount.setEditable(false);

        JButton payButton = new JButton("Pay");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        paymentDialog.add(labelMessage, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        paymentDialog.add(labelCardNo, gbc);
        gbc.gridx = 1;
        paymentDialog.add(textCardNo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        paymentDialog.add(labelCvv, gbc);
        gbc.gridx = 1;
        paymentDialog.add(textCvv, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        paymentDialog.add(labelAmount, gbc);
        gbc.gridx = 1;
        paymentDialog.add(textAmount, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        paymentDialog.add(payButton, gbc);

        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double amount = Double.parseDouble(textAmount.getText());
                boolean paymentSuccess = OnlineBookingOperation.savePaymentDetails(bookingId, amount);
                if (paymentSuccess) {
                    JOptionPane.showMessageDialog(null, "Booking successful! Booking ID: " + bookingId, "Success", JOptionPane.INFORMATION_MESSAGE);
                    paymentDialog.dispose(); // Close the payment dialog
                } else {
                    JOptionPane.showMessageDialog(null, "Payment failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        paymentDialog.setSize(400, 250);
        paymentDialog.setLocationRelativeTo(this);
        paymentDialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OnlineBooking("101", 1200.00));
    }

    // Helper class for date formatting in JDatePicker
    public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

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
}
