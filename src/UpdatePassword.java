import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdatePassword extends JFrame {

    private JTextField employeeIDField;
    private JPasswordField currentPasswordField;
    private JPasswordField newPasswordField;
    private JButton updateButton;

    public UpdatePassword() {
        setTitle("Update Password");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Employee ID
        JLabel employeeIDLabel = new JLabel("Employee ID:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(employeeIDLabel, gbc);

        employeeIDField = new JTextField(20);
        gbc.gridx = 1;
        add(employeeIDField, gbc);

        // Current Password
        JLabel currentPasswordLabel = new JLabel("Current Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(currentPasswordLabel, gbc);

        currentPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        add(currentPasswordField, gbc);

        // New Password
        JLabel newPasswordLabel = new JLabel("New Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(newPasswordLabel, gbc);

        newPasswordField = new JPasswordField(20);
        gbc.gridx = 1;
        add(newPasswordField, gbc);

        // Update Button
        updateButton = new JButton("Update");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(updateButton, gbc);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdateButtonClick();
            }
        });

        setVisible(true);
    }

    private void handleUpdateButtonClick() {
        String employeeID = employeeIDField.getText();
        String currentPassword = new String(currentPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());

        if (employeeID.isEmpty() || currentPassword.isEmpty() || newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String checkQuery = "SELECT Password FROM Employee WHERE EmployeeID = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setString(1, employeeID);

                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        String storedPassword = rs.getString("Password");

                        if (storedPassword.equals(currentPassword)) {
                            String updateQuery = "UPDATE Employee SET Password = ? WHERE EmployeeID = ?";
                            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                                updateStmt.setString(1, newPassword);
                                updateStmt.setString(2, employeeID);

                                int rowsAffected = updateStmt.executeUpdate();
                                if (rowsAffected > 0) {
                                    JOptionPane.showMessageDialog(this, "Password updated successfully.");
                                } else {
                                    JOptionPane.showMessageDialog(this, "Failed to update password.", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Current password is incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Employee ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UpdatePassword());
    }
}

