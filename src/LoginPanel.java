import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JFrame {
    private JPanel loginPanel;
    private JComboBox<String> roleComboBox;
    private JTextField userField;
    private JPasswordField passwordField;

    public LoginPanel() {
        loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        createLoginPanel();
    }

    private void createLoginPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(new JLabel("Role:"), gbc);

        gbc.gridx = 1;
        String[] roles = {"Visitor", "Admin", "Employee"};
        roleComboBox = new JComboBox<>(roles);
        loginPanel.add(roleComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(new JLabel("User Name:"), gbc);

        gbc.gridx = 1;
        userField = new JTextField(15);
        loginPanel.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        loginPanel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        JButton loginButton = new JButton("Login");
        loginPanel.add(loginButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        JButton createAccountButton = new JButton("Create Account");
        loginPanel.add(createAccountButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistrationPage();
            }
        });
    }

    private void handleLogin() {
        String role = (String) roleComboBox.getSelectedItem();
        String username = userField.getText();
        String password = new String(passwordField.getPassword());

        if (LoginOperation.validateLogin(role, username, password)) {
            JOptionPane.showMessageDialog(this, "Login Successful!");

            // Hide the login panel
            SwingUtilities.getWindowAncestor(loginPanel).dispose();

            switch (role) {
                case "Visitor":
                    new CustomerWelcomePage();
                    break;
                case "Admin":
                    new AdminDashBord();
                    break;
                case "Employee":
                    new EmployeeDashbord();
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Invalid role selected!");
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials or role!");
        }
    }

    public JPanel getPanel() {
        return loginPanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);

        LoginPanel unifiedLoginPanel = new LoginPanel();
        frame.add(unifiedLoginPanel.getPanel(), BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
