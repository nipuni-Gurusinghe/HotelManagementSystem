import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddSalaryBonus extends JFrame {

    private JTextField adminIDField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JTextField basicSalaryField;
    private JTextField bonusField;
    private JButton updateButton;

    public AddSalaryBonus() {
        //frame ek seetup karanw
        setTitle("Add Salary and Bonus");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

       //navebar class ek use krala navbar ek danw
        Navbar navbar = new Navbar();
        add(navbar, BorderLayout.NORTH);

        // Create the main panel for form input
        JPanel mainPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(173, 216, 230)); // Light lavender background

        JLabel adminIDLabel = new JLabel("Admin ID:");
        adminIDField = new JTextField();
        mainPanel.add(adminIDLabel);
        mainPanel.add(adminIDField);


        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);

        JLabel roleLabel = new JLabel("Role:");
        roleComboBox = new JComboBox<>(new String[] {
                "HouseKeeper", "Chef", "Receptionists", "Kitchen Helper", "Waiter", "Porter", "Security"
        });
        mainPanel.add(roleLabel);
        mainPanel.add(roleComboBox);


        JLabel basicSalaryLabel = new JLabel("Basic Salary:");
        basicSalaryField = new JTextField();
        mainPanel.add(basicSalaryLabel);
        mainPanel.add(basicSalaryField);


        JLabel bonusLabel = new JLabel("Bonus:");
        bonusField = new JTextField();
        mainPanel.add(bonusLabel);
        mainPanel.add(bonusField);

        // Update Button
        updateButton = new JButton("Update");
        mainPanel.add(new JLabel()); //sapace ekk hadanna empty label ekk
        mainPanel.add(updateButton);

        //main panel eka frame ekta set
        add(mainPanel, BorderLayout.CENTER);


        FooterPanel footerPanel = new FooterPanel();
        add(footerPanel, BorderLayout.SOUTH);


        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSalaryAndBonus();
            }
        });
    }

    private void updateSalaryAndBonus() {
        String adminID = adminIDField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();
        String basicSalary = basicSalaryField.getText();
        String bonus = bonusField.getText();

        if (adminID.isEmpty() || password.isEmpty() || basicSalary.isEmpty() || bonus.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String adminQuery = "SELECT * FROM Admin WHERE AdminID = ? AND Password = ?";
            try (PreparedStatement adminStmt = conn.prepareStatement(adminQuery)) {
                adminStmt.setString(1, adminID);
                adminStmt.setString(2, password);

                try (ResultSet adminRs = adminStmt.executeQuery()) {
                    if (adminRs.next()) {
                        String updateQuery = "UPDATE Role SET BasicSalary = ?, Bonus = ?, AdminID = ? WHERE Role = ?";
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                            updateStmt.setDouble(1, Double.parseDouble(basicSalary));
                            updateStmt.setDouble(2, Double.parseDouble(bonus));
                            updateStmt.setString(3, adminID);
                            updateStmt.setString(4, role);

                            int rowsAffected = updateStmt.executeUpdate();
                            if (rowsAffected > 0) {
                                JOptionPane.showMessageDialog(this, "Salary and Bonus updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(this, "Failed to update Salary and Bonus.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid AdminID or Password.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AddSalaryBonus frame = new AddSalaryBonus();
            frame.setVisible(true);
        });
    }
}
