import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

class CustomerWelcomePage {
    private JFrame frame;

    public CustomerWelcomePage() {
        initialize();
        setVisible(true);
    }

    public void setVisible(boolean b) {
        frame.setVisible(b);
    }


    private void initialize() {
        // Frame setup
        frame = new JFrame("Citron Villa");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setLayout(new BorderLayout());

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(Color.DARK_GRAY);

        JLabel headerLabel = new JLabel("Citron Resort", SwingConstants.CENTER);
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel, BorderLayout.NORTH);

        frame.add(headerPanel, BorderLayout.NORTH);

        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        JLabel bannerLabel = createBannerLabel();
        contentPanel.add(bannerLabel, BorderLayout.CENTER);
        JPanel reservationPanel = createReservationPanel();
        contentPanel.add(reservationPanel, BorderLayout.EAST);
        JPanel welcomePanel = createWelcomePanel();
        contentPanel.add(welcomePanel, BorderLayout.SOUTH);

        frame.add(contentPanel, BorderLayout.CENTER);
        frame.add(new FooterPanel(), BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private JLabel createBannerLabel() {
        ImageIcon bannerImage = new ImageIcon("C:\\Users\\AMASHA\\Desktop\\HotelManageent\\hotel.jpg");
        Image img = bannerImage.getImage().getScaledInstance(1600, 600, Image.SCALE_SMOOTH);
        bannerImage = new ImageIcon(img);
        JLabel bannerLabel = new JLabel(bannerImage);

        return bannerLabel;
    }

    private JPanel createReservationPanel() {
        JPanel reservationPanel = new JPanel(new GridBagLayout());
        reservationPanel.setBackground(new Color(173, 216, 230)); // Light Blue color

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel checkAvailabilityLabel = new JLabel("Check Availability");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        reservationPanel.add(checkAvailabilityLabel, gbc);

        gbc.gridwidth = 1;

        JLabel roomTypeLabel = new JLabel("Room Type:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        reservationPanel.add(roomTypeLabel, gbc);

        String[] roomTypes = {"Luxury", "De-Luxury", "Cottage", "Party areas"};
        JComboBox<String> roomTypeComboBox = new JComboBox<>(roomTypes);
        gbc.gridx = 1;
        reservationPanel.add(roomTypeComboBox, gbc);

        JLabel checkInLabel = new JLabel("Check-in:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        reservationPanel.add(checkInLabel, gbc);

        UtilDateModel checkInModel = new UtilDateModel();
        Properties p = new Properties();
        JDatePanelImpl checkInDatePanel = new JDatePanelImpl(checkInModel, p);
        JDatePickerImpl checkInDatePicker = new JDatePickerImpl(checkInDatePanel, new DateLabelFormatter());
        gbc.gridx = 1;
        reservationPanel.add(checkInDatePicker, gbc);


        JLabel checkOutLabel = new JLabel("Check-out:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        reservationPanel.add(checkOutLabel, gbc);

        UtilDateModel checkOutModel = new UtilDateModel();
        JDatePanelImpl checkOutDatePanel = new JDatePanelImpl(checkOutModel, p);
        JDatePickerImpl checkOutDatePicker = new JDatePickerImpl(checkOutDatePanel, new DateLabelFormatter());
        gbc.gridx = 1;
        reservationPanel.add(checkOutDatePicker, gbc);

        JLabel adultsLabel = new JLabel("Adults:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        reservationPanel.add(adultsLabel, gbc);

        JTextField adultsField = new JTextField(5);
        gbc.gridx = 1;
        reservationPanel.add(adultsField, gbc);

        JLabel kidsLabel = new JLabel("Kids:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        reservationPanel.add(kidsLabel, gbc);

        JTextField kidsField = new JTextField(5);
        gbc.gridx = 1;
        reservationPanel.add(kidsField, gbc);

        JButton searchButton = new JButton("SEARCH");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        reservationPanel.add(searchButton, gbc);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String roomType = (String) roomTypeComboBox.getSelectedItem();
                java.util.Date checkInDate = (java.util.Date) checkInDatePicker.getModel().getValue();
                java.util.Date checkOutDate = (java.util.Date) checkOutDatePicker.getModel().getValue();

                if (checkInDate != null && checkOutDate != null) {
                    new AvailableRooms(roomType, checkInDate, checkOutDate);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select both check-in and check-out dates.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return reservationPanel;
    }


    private static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }
            return "";
        }
    }

    private JPanel createWelcomePanel() {
        JPanel welcomePanel = new JPanel(new BorderLayout());

        JPanel roomPanel = new JPanel(new GridLayout(1, 4));
        String[] roomTypes = {" Cottage", "Super Deluxe", "Deluxe Room", "Party areas"};
        String[] roomImagePaths = {
                "C:\\Users\\AMASHA\\Desktop\\HotelManageent\\Cottage.jpg",
                "C:\\Users\\AMASHA\\Desktop\\HotelManageent\\luxery.jpeg",
                "C:\\Users\\AMASHA\\Desktop\\HotelManageent\\delux.jpg",
                "C:\\Users\\AMASHA\\Desktop\\HotelManageent\\PartyAreas.jpg"
        };

        for (int i = 0; i < roomTypes.length; i++) {
            JPanel roomTypePanel = new JPanel(new BorderLayout());


            JLabel roomLabel = new JLabel(roomTypes[i], SwingConstants.CENTER);
            roomLabel.setFont(new Font("Arial", Font.BOLD, 18));
            roomTypePanel.add(roomLabel, BorderLayout.NORTH);

            // Load and add room image
            ImageIcon roomImageIcon = new ImageIcon(roomImagePaths[i]);

            // Optionally, scale the image to fit within the JLabel
            Image roomImage = roomImageIcon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
            roomImageIcon = new ImageIcon(roomImage);

            JLabel roomImageLabel = new JLabel(roomImageIcon, SwingConstants.CENTER);
            roomTypePanel.add(roomImageLabel, BorderLayout.CENTER);

            roomPanel.add(roomTypePanel);
        }

        welcomePanel.add(roomPanel, BorderLayout.CENTER);

        return welcomePanel;
    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CustomerWelcomePage();
            }
        });
    }
}
