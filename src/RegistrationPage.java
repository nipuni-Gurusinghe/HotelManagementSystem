import javax.swing.*;
import java.awt.*;

public class RegistrationPage extends JFrame {

    private JTextField fullNameField, mobileNumberField, emailAddressField, usernameField;
    private JPasswordField passwordField;

    public RegistrationPage() {
        setTitle(" Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createRegistrationPanel(), BorderLayout.CENTER);
        add(new FooterPanel(), BorderLayout.SOUTH);
        setVisible(true);
    }


    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.DARK_GRAY);
        JLabel bannerLabel = new JLabel();
        ImageIcon bannerIcon = new ImageIcon("C:\\Users\\AMASHA\\Desktop\\HotelManageent\\hotel.jpg");

        Image scaledImage = bannerIcon.getImage().getScaledInstance(800, 100, Image.SCALE_SMOOTH);
        bannerIcon = new ImageIcon(scaledImage);
        bannerLabel.setIcon(bannerIcon);
        headerPanel.add(bannerLabel, BorderLayout.CENTER);
        Navbar navbar = new Navbar();
        headerPanel.add(navbar, BorderLayout.NORTH);

        return headerPanel;
    }

    private JPanel createRegistrationPanel() {
        JPanel registrationPanel = new JPanel();
        registrationPanel.setLayout(new BoxLayout(registrationPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("REGISTERED With Us");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        registrationPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        registrationPanel.add(titleLabel);
        registrationPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        String[] labels = {"FULL NAME", "MOBILE NUMBER", "EMAIL ADDRESS", "USERNAME", "PASSWORD"};

        fullNameField = new JTextField(30);
        mobileNumberField = new JTextField(30);
        emailAddressField = new JTextField(30);
        usernameField = new JTextField(30);
        passwordField = new JPasswordField(30);

        JTextField[] fields = {fullNameField, mobileNumberField, emailAddressField, usernameField, passwordField};

        for (int i = 0; i < labels.length; i++) {
            JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JLabel fieldLabel = new JLabel(labels[i]);
            fieldLabel.setPreferredSize(new Dimension(150, 25));
            fieldPanel.add(fieldLabel);
            fieldPanel.add(fields[i]);
            registrationPanel.add(fieldPanel);
            registrationPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton signUpButton = new JButton("SIGN UP");

        signUpButton.addActionListener(e -> {
            String fullName = fullNameField.getText();
            String password = new String(passwordField.getPassword());
            String mobileNumber = mobileNumberField.getText();
            String emailAddress = emailAddressField.getText();
            String username = usernameField.getText();

            if (RegistrationOperation.addCustomer(fullName, password, mobileNumber, emailAddress, username)) {
                JOptionPane.showMessageDialog(this, "Registration Successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Registration Failed.");
            }
        });

        buttonPanel.add(signUpButton);
        registrationPanel.add(buttonPanel);

        return registrationPanel;
    }

    public static void main(String[] args) {
        new RegistrationPage();
    }
}
