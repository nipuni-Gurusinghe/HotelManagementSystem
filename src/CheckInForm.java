import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;

public class CheckInForm extends JFrame {

    private JPanel headerPanel;

    public CheckInForm() {
        setTitle("Check In");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main content panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add the navigation bar using Navbar class
        headerPanel = new JPanel(new BorderLayout());
        Navbar navbar = new Navbar();  // Create an instance of Navbar
        headerPanel.add(navbar, BorderLayout.NORTH);
        mainPanel.add(headerPanel);


        // Add the banner image with specified width and height
        int bannerWidth = 800; // specify the desired width
        int bannerHeight = 100; // specify the desired height
        ImageIcon bannerIcon = new ImageIcon("C:\\Users\\AMASHA\\Desktop\\HotelManageent\\banner.jpg");
        Image bannerImage = bannerIcon.getImage().getScaledInstance(bannerWidth, bannerHeight, Image.SCALE_SMOOTH);
        JLabel bannerLabel = new JLabel(new ImageIcon(bannerImage));

        JPanel bannerPanel = new JPanel();
        bannerPanel.add(bannerLabel);
        mainPanel.add(bannerPanel);

        // Form fields panel
        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel);

        // Add the footer panel using FooterPanel class
        FooterPanel footerPanel = new FooterPanel();
        mainPanel.add(footerPanel);

        // Add the main panel to the frame
        add(mainPanel);
        setVisible(true);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(9, 2, 10, 10)); // Adjusted to 9 rows to accommodate the Check-In Date field

        // Form fields
        JTextField bookingIDField = new JTextField(15);
        JTextField idCardNoField = new JTextField(15);
        JTextField firstNameField = new JTextField(15);
        JTextField lastNameField = new JTextField(15);

        // JDatePicker implementation
        SqlDateModel model = new SqlDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        JTextField vehicleField = new JTextField(15);
        JTextField plateNoField = new JTextField(15);
        JTextField roomNumberField = new JTextField(15);

        formPanel.add(new JLabel("BookingID:"));
        formPanel.add(bookingIDField);
        formPanel.add(new JLabel("ID Card No:"));
        formPanel.add(idCardNoField);
        formPanel.add(new JLabel("First Name:"));
        formPanel.add(firstNameField);
        formPanel.add(new JLabel("Last Name:"));
        formPanel.add(lastNameField);
        formPanel.add(new JLabel("Check-In Date:"));
        formPanel.add(datePicker);
        formPanel.add(new JLabel("Vehicle:"));
        formPanel.add(vehicleField);
        formPanel.add(new JLabel("Plate No:"));
        formPanel.add(plateNoField);
        formPanel.add(new JLabel("Room Number:"));
        formPanel.add(roomNumberField);

        JButton checkInButton = new JButton("Check In");
        formPanel.add(new JLabel());
        formPanel.add(checkInButton);

        checkInButton.addActionListener(e -> {
            // Get data from text fields
            String bookingID = bookingIDField.getText();
            String idCardNo = idCardNoField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            java.sql.Date checkInDate = (java.sql.Date) datePicker.getModel().getValue();
            String vehicle = vehicleField.getText();
            String plateNo = plateNoField.getText();
            String roomNumber = roomNumberField.getText();
            CheckInOperation operation = new CheckInOperation();
            boolean success = operation.saveCheckInDetails(bookingID, idCardNo, firstName, lastName, checkInDate, vehicle, plateNo, roomNumber);
            if (success) {
                JOptionPane.showMessageDialog(this, "Check-In Successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Check-In Failed. Please try again.");
            }
        });

        return formPanel;
    }

    public static void main(String[] args) {
        CheckInForm checkInForm = new CheckInForm();
        checkInForm.setVisible(true);
    }
}
