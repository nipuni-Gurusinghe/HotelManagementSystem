import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashBord extends JFrame {



    public AdminDashBord() {
        setTitle("Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize and add the header panel with Navbar
        JPanel headerPanel = new JPanel(new BorderLayout());
        Navbar navbar = new Navbar();
        headerPanel.add(navbar, BorderLayout.NORTH);
        add(headerPanel, BorderLayout.NORTH);

        // Adding the main content area
        add(createMainContent(), BorderLayout.CENTER);


        FooterPanel footerPanel = new FooterPanel();
        add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }



    private JPanel createMainContent() {
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBackground(Color.WHITE);

        JLabel dashboardLabel = new JLabel("Admin Dashboard", JLabel.LEFT);
        dashboardLabel.setFont(new Font("Serif", Font.BOLD, 24));
        dashboardLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainContentPanel.add(dashboardLabel, BorderLayout.NORTH);

        JPanel quickLinksPanel = new JPanel();
        quickLinksPanel.setLayout(new GridLayout(3, 4, 10, 10));
        quickLinksPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        quickLinksPanel.setBackground(Color.WHITE);

        String[] linkTitles = {
                "Add Employee/Admins", "Room Booking Report", "Assign a Special Works","CheckIn","CheckOut",
                "Check-In List", "Change salary and bonus", "Calculate Employee Salaries", "Add new rooms"
        };

        String[] linkSubtitles = {
                "Add Employee/Admins", "Room Booking Report",
                "Assign a Work","ChekIn visitors","CheckOut visitors","Check-In Report", "Chenge salary and bonus", "Calculate Salaries for Employees", "Add new rooms to the system"
        };

        Color[] colors = {
                new Color(51, 255, 207, 255),
                new Color(51, 153, 255),
                new Color(0, 204, 153),
                new Color(51, 153, 255),
                new Color(255, 102, 204),
                new Color(0, 153, 51),
                new Color(0, 153, 51),
               new Color(51, 255, 163),
                new Color(153, 102, 255),
                new Color(102, 255, 178)
        };

        for (int i = 0; i < linkTitles.length; i++) {
            JPanel quickLinkPanel = createQuickLinkPanel(linkTitles[i], linkSubtitles[i], colors[i]);

            if (linkTitles[i].equals("Add Employee/Admins")) {
                JButton button = (JButton) quickLinkPanel.getComponent(2);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        openAddEmployeeForm();
                    }
                });
            }

            if (linkTitles[i].equals("Add new rooms")) {
                JButton button = (JButton) quickLinkPanel.getComponent(2);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        openAddRoomForm();
                    }
                });

            }
            if (linkTitles[i].equals("CheckIn")) {
                JButton button = (JButton) quickLinkPanel.getComponent(2);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new CheckInForm();
                    }
                });
            }
            if (linkTitles[i].equals("CheckOut")) {
                JButton button = (JButton) quickLinkPanel.getComponent(2);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new CheckOut();
                    }
                });
            }
            if (linkTitles[i].equals("Check-In List")) {
                JButton button = (JButton) quickLinkPanel.getComponent(2);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new GetCheckInList();
                    }
                });
            }

            if (linkTitles[i].equals("Change salary and bonus")) {
                JButton button = (JButton) quickLinkPanel.getComponent(2);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        AddSalaryBonus addSalaryBonusFrame = new AddSalaryBonus();
                        addSalaryBonusFrame.setVisible(true);  // Make the frame visible
                    }
                });
            }


            if (linkTitles[i].equals("Assign a Special Works")) {
                JButton button = (JButton) quickLinkPanel.getComponent(2);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new AssignWorks();
                    }
                });
            }

            if (linkTitles[i].equals("Room Booking Report")) {
                JButton button = (JButton) quickLinkPanel.getComponent(2);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new RoomBookingReport();
                    }
                });
            }


            if (linkTitles[i].equals("Calculate Employee Salaries")) {
                JButton button = (JButton) quickLinkPanel.getComponent(2);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        CalculateSalary calculateSalaryFrame = new CalculateSalary();
                        calculateSalaryFrame.setVisible(true);  // Make the frame visible
                    }
                });
            }


            quickLinksPanel.add(quickLinkPanel);
        }

        mainContentPanel.add(quickLinksPanel, BorderLayout.CENTER);


        return mainContentPanel;
    }

    private JPanel createQuickLinkPanel(String title, String subtitle, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(color);
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel(subtitle, JLabel.CENTER);
        subtitleLabel.setFont(new Font("Serif", Font.PLAIN, 14));
        subtitleLabel.setForeground(Color.WHITE);

        JButton button = new JButton("Check Now");
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(subtitleLabel, BorderLayout.CENTER);
        panel.add(button, BorderLayout.SOUTH);

        return panel;
    }

    private void openAddRoomForm() {
        JDialog addRoomDialog = new JDialog(this, "Add Room", true);
        addRoomDialog.setSize(400, 350);
        addRoomDialog.setLayout(new GridLayout(5, 2, 10, 10));

        addRoomDialog.add(new JLabel("Room Type:"));
        JComboBox<String> roomTypeComboBox = new JComboBox<>(new String[]{"Luxury", "De-Luxury", "Cottage", "Party Areas"});
        addRoomDialog.add(roomTypeComboBox);

        addRoomDialog.add(new JLabel("Status:"));
        JComboBox<String> statusComboBox = new JComboBox<>(new String[]{"Available", "Not Available"});
        addRoomDialog.add(statusComboBox);

        addRoomDialog.add(new JLabel("Price for One Day:"));
        JTextField priceField = new JTextField();
        addRoomDialog.add(priceField);

        addRoomDialog.add(new JLabel("Image Path:"));
        JTextField imagePathField = new JTextField();
        addRoomDialog.add(imagePathField);

        JButton addButton = new JButton("Add Room");
        addRoomDialog.add(addButton);

        JButton cancelButton = new JButton("Cancel");
        addRoomDialog.add(cancelButton);

        addButton.addActionListener(e -> {
            String roomType = (String) roomTypeComboBox.getSelectedItem();
            String status = (String) statusComboBox.getSelectedItem();
            String priceText = priceField.getText();
            String imagePath = imagePathField.getText();

            double price = 0;
            try {
                price = Double.parseDouble(priceText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(addRoomDialog, "Invalid price. Please enter a valid number.");
                return;
            }

            if (AdminDashbordOperations.addRoom(roomType, status, price, imagePath)) {
                JOptionPane.showMessageDialog(addRoomDialog, "Room added successfully!");
            } else {
                JOptionPane.showMessageDialog(addRoomDialog, "Failed to add room.");
            }

            addRoomDialog.dispose();
        });

        cancelButton.addActionListener(e -> addRoomDialog.dispose());
        addRoomDialog.setLocationRelativeTo(this);
        addRoomDialog.setVisible(true);
    }

    private void openAddEmployeeForm() {
        JDialog addEmployeeDialog = new JDialog(this, "Add Employee", true);
        addEmployeeDialog.setSize(400, 350);
        addEmployeeDialog.setLayout(new GridLayout(8, 2, 10, 10));

        addEmployeeDialog.add(new JLabel("Full Name:"));
        JTextField fullNameField = new JTextField();
        addEmployeeDialog.add(fullNameField);

        addEmployeeDialog.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        addEmployeeDialog.add(passwordField);

        addEmployeeDialog.add(new JLabel("Mobile Number:"));
        JTextField mobileNumberField = new JTextField();
        addEmployeeDialog.add(mobileNumberField);

        addEmployeeDialog.add(new JLabel("Email Address:"));
        JTextField emailAddressField = new JTextField();
        addEmployeeDialog.add(emailAddressField);

        addEmployeeDialog.add(new JLabel("Role:"));
        JComboBox<String> roleComboBox = new JComboBox<>(new String[]{
                "Admin",
                "HouseKeeper",
                "Chef",
                "Receptionists",
                "Kitchen Helper",
                "Waiter",
                "Porter",
                "Security"
        });
        addEmployeeDialog.add(roleComboBox);

        addEmployeeDialog.add(new JLabel("Username:"));
        JTextField usernameField = new JTextField();
        addEmployeeDialog.add(usernameField);

        addEmployeeDialog.add(new JLabel("Image Path:"));
        JTextField imagePathField = new JTextField();
        addEmployeeDialog.add(imagePathField);

        JButton addButton = new JButton("Add Employee");
        addEmployeeDialog.add(addButton);

        JButton deleteButton = new JButton("Delete");
        addEmployeeDialog.add(deleteButton);

        addButton.addActionListener(e -> {
            String fullName = fullNameField.getText();
            String password = new String(passwordField.getPassword());
            String mobileNumber = mobileNumberField.getText();
            String emailAddress = emailAddressField.getText();
            String role = (String) roleComboBox.getSelectedItem();
            String username = usernameField.getText();
            String imagePath = imagePathField.getText();

            if (AdminDashbordOperations.addEmployee(fullName, password, mobileNumber, emailAddress, role, username, imagePath)) {
                JOptionPane.showMessageDialog(addEmployeeDialog, "Employee added successfully!");
            } else {
                JOptionPane.showMessageDialog(addEmployeeDialog, "Failed to add employee.");
            }

            addEmployeeDialog.dispose();
        });

        deleteButton.addActionListener(e -> openDeleteEmployeeForm());

        addEmployeeDialog.setLocationRelativeTo(this);
        addEmployeeDialog.setVisible(true);
    }
    private void openDeleteEmployeeForm() {
        JDialog deleteEmployeeDialog = new JDialog(this, "Delete Employee", true);
        deleteEmployeeDialog.setSize(300, 150);
        deleteEmployeeDialog.setLayout(new GridLayout(3, 2, 10, 10));

        deleteEmployeeDialog.add(new JLabel("Employee ID:"));
        JTextField employeeIdField = new JTextField();
        deleteEmployeeDialog.add(employeeIdField);

        JButton removeButton = new JButton("Remove from System");
        deleteEmployeeDialog.add(removeButton);

        JButton cancelButton = new JButton("Cancel");
        deleteEmployeeDialog.add(cancelButton);

        removeButton.addActionListener(e -> {
            String employeeId = employeeIdField.getText();

            if (employeeId.isEmpty()) {
                JOptionPane.showMessageDialog(deleteEmployeeDialog, "Please enter an Employee ID.");
                return;
            }

            if (AdminDashbordOperations.removeEmployee(employeeId)) {
                JOptionPane.showMessageDialog(deleteEmployeeDialog, "Employee removed successfully!");
            } else {
                JOptionPane.showMessageDialog(deleteEmployeeDialog, "Failed to remove employee.");
            }

            deleteEmployeeDialog.dispose();
        });

        cancelButton.addActionListener(e -> deleteEmployeeDialog.dispose());

        deleteEmployeeDialog.setLocationRelativeTo(this);
        deleteEmployeeDialog.setVisible(true);
    }


    public static void main(String[] args) {
        new AdminDashBord();
    }
}
