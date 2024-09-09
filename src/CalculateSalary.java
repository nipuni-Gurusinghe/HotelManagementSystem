import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CalculateSalary extends JFrame {

    private JTextField employeeIDField;
    private JTextField basicSalaryField;
    private JTextField fullSalaryField;
    private JButton calculateButton;

    public CalculateSalary() {
        setTitle("Calculate Salary");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        Navbar navbar = new Navbar();
        add(navbar, BorderLayout.NORTH);

        // main panel for input
        JPanel mainPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel employeeIDLabel = new JLabel("Employee ID:");
        employeeIDField = new JTextField();
        mainPanel.add(employeeIDLabel);
        mainPanel.add(employeeIDField);

        calculateButton = new JButton("Calculate");
        calculateButton.setPreferredSize(new Dimension(120, 30)); // Decrease size here
        calculateButton.setBackground(new Color(0, 178, 255, 242)); // Blue background
        calculateButton.setForeground(Color.WHITE); // White text
        calculateButton.setFont(new Font("Arial", Font.BOLD, 14)); // Optional: Change font

        mainPanel.add(new JLabel());
        mainPanel.add(calculateButton);

        JLabel basicSalaryLabel = new JLabel("Basic Salary:");
        basicSalaryField = new JTextField();
        basicSalaryField.setEditable(false);
        mainPanel.add(basicSalaryLabel);
        mainPanel.add(basicSalaryField);

        JLabel fullSalaryLabel = new JLabel("Full Salary:");
        fullSalaryField = new JTextField();
        fullSalaryField.setEditable(false);
        mainPanel.add(fullSalaryLabel);
        mainPanel.add(fullSalaryField);

        // Add main panel to  frame
        add(mainPanel, BorderLayout.CENTER);
        FooterPanel footerPanel = new FooterPanel();
        add(footerPanel, BorderLayout.SOUTH);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateSalary();
            }
        });
    }

    private void calculateSalary() {
        String employeeID = employeeIDField.getText();

        if (employeeID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Employee ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String roleQuery = "SELECT Role FROM Employee WHERE EmployeeID = ?";
            try (PreparedStatement roleStmt = conn.prepareStatement(roleQuery)) {
                roleStmt.setString(1, employeeID);

                try (ResultSet roleRs = roleStmt.executeQuery()) {
                    if (roleRs.next()) {
                        String role = roleRs.getString("Role");

                        String salaryQuery = "SELECT BasicSalary, Bonus FROM Role WHERE Role = ?";
                        try (PreparedStatement salaryStmt = conn.prepareStatement(salaryQuery)) {
                            salaryStmt.setString(1, role);

                            try (ResultSet salaryRs = salaryStmt.executeQuery()) {
                                if (salaryRs.next()) {
                                    double basicSalary = salaryRs.getDouble("BasicSalary");
                                    double bonus = salaryRs.getDouble("Bonus");
                                    double fullSalary = basicSalary + bonus;
                                    // Display salaries in the text fields
                                    basicSalaryField.setText(String.valueOf(basicSalary));
                                    fullSalaryField.setText(String.valueOf(fullSalary));
                                } else {
                                    JOptionPane.showMessageDialog(this, "Role not found in Role table.", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Employee ID not found in Employee table.", "Error", JOptionPane.ERROR_MESSAGE);
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
            CalculateSalary frame = new CalculateSalary();
            frame.setVisible(true);
        });
    }
}
