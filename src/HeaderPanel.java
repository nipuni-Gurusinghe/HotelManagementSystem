import javax.swing.*;
import java.awt.*;

class HeaderPanel extends JFrame {
    private JPanel headerPanel;

    public HeaderPanel() {
        headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(Color.DARK_GRAY);

        addNavigationBar();
        addTitle();
        addImage();
    }

    private void addNavigationBar() {
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        navPanel.setBackground(new Color(173, 216, 230));

        // Home Button
        JButton homeButton = new JButton("Home");
        homeButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(headerPanel).dispose();
            WelcomePage ui = new WelcomePage();
            ui.createAndShowGUI();
        });


        JButton contactUsButton = new JButton("Contact Us");
        contactUsButton.addActionListener(e -> {
            try {
                String whatsappUrl = "https://wa.me/+94701424978";
                Desktop.getDesktop().browse(new java.net.URI(whatsappUrl));
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Unable to open WhatsApp. Please check your internet connection or try again later.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        navPanel.add(homeButton);

        navPanel.add(contactUsButton);

        headerPanel.add(navPanel, BorderLayout.NORTH);
    }

    private void addTitle() {
        JLabel titleLabel = new JLabel("HOTEL MANAGEMENT SYSTEM", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
    }

    private void addImage() {
        JLabel imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon("C:\\Users\\AMASHA\\Desktop\\HotelManageent\\WhatsApp Image 2024-08-30 at 19.32.51.jpeg");

        // Image scaling
        ImageIcon scaledIcon = new ImageIcon(icon.getImage().getScaledInstance(800, 300, Image.SCALE_DEFAULT));

        imageLabel.setIcon(scaledIcon);
        headerPanel.add(imageLabel, BorderLayout.SOUTH);
    }
    public JPanel getPanel() {
        return headerPanel;
    }
}
