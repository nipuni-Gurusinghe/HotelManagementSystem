import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckSpecialWork extends JFrame {

    private JTextField employeeIDField;
    private JTextArea workDescriptionArea;
    private JButton okButton;

    public CheckSpecialWork() {
        setTitle("Check Special Work");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel employeeIDLabel = new JLabel("Employee ID:");
        employeeIDField = new JTextField();
        JButton enterButton = new JButton("Enter");
        okButton = new JButton("OK");

        inputPanel.add(employeeIDLabel);
        inputPanel.add(employeeIDField);
        inputPanel.add(new JLabel());
        inputPanel.add(enterButton);

        add(inputPanel, BorderLayout.NORTH);

        workDescriptionArea = new JTextArea();
        workDescriptionArea.setEditable(false);
        workDescriptionArea.setBorder(BorderFactory.createTitledBorder("Special Work Description"));

        add(new JScrollPane(workDescriptionArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(okButton);

        add(buttonPanel, BorderLayout.SOUTH);

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchSpecialWork();
            }
        });

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateWorkStatus();
            }
        });
    }

    private void fetchSpecialWork() {
        String employeeID = employeeIDField.getText().trim();
        if (employeeID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Employee ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT SpecialWork FROM Employee WHERE EmployeeID = ?")) {

            statement.setString(1, employeeID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String specialWork = resultSet.getString("SpecialWork");
                workDescriptionArea.setText(specialWork);
                okButton.setEnabled(true); // Enable OK button when special work is found
            } else {
                workDescriptionArea.setText("No special work found for this Employee ID.");
                okButton.setEnabled(false); // Disable OK button when no work is found
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data from database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateWorkStatus() {
        String employeeID = employeeIDField.getText().trim();
        if (employeeID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an Employee ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Employee SET WorkStatus = 'OK' WHERE EmployeeID = ?")) {

            statement.setString(1, employeeID);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Work status updated to 'OK'.", "Success", JOptionPane.INFORMATION_MESSAGE);
                workDescriptionArea.setText("Work status updated to 'OK'.");
                okButton.setEnabled(false); // Disable OK button after updating
            } else {
                JOptionPane.showMessageDialog(this, "No Employee found with the given ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating data in database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CheckSpecialWork().setVisible(true));
    }
}
