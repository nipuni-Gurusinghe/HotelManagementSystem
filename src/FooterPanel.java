import javax.swing.*;
import java.awt.*;

public class FooterPanel extends JPanel {

    public FooterPanel() {
        setLayout(new GridLayout(1, 3)); // 1 row, 3 columns
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(100, 150)); // Set the height to 150

        // Create Address panel
        JPanel addressPanel = createFooterSection("Address", "No 27/45,\n Habaraduwa,\n Galle");

        // Load and resize Facebook and Instagram icons
        ImageIcon facebookIcon = new ImageIcon(new ImageIcon("C:\\Users\\AMASHA\\Desktop\\HotelManageent\\fb.png").getImage().getScaledInstance(20, 30, Image.SCALE_SMOOTH));
        ImageIcon instagramIcon = new ImageIcon(new ImageIcon("C:\\Users\\AMASHA\\Desktop\\HotelManageent\\insta.jpeg").getImage().getScaledInstance(20, 30, Image.SCALE_SMOOTH));

        // Create JLabels for Facebook and Instagram with icons
        JLabel facebookLabel = new JLabel(" Citron Resort", facebookIcon, JLabel.LEFT);
        JLabel instagramLabel = new JLabel(" Citron_Galle", instagramIcon, JLabel.LEFT);

        // Adjust alignment
        facebookLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        instagramLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Create a panel to hold the social media icons and labels
        JPanel socialMediaPanel = new JPanel();
        socialMediaPanel.setLayout(new BoxLayout(socialMediaPanel, BoxLayout.Y_AXIS));
        socialMediaPanel.setBackground(Color.LIGHT_GRAY);

        // Add the social media labels to the panel
        socialMediaPanel.add(facebookLabel);
        socialMediaPanel.add(instagramLabel);

        // Adjust the spacing between components in addressPanel
        addressPanel.setLayout(new BoxLayout(addressPanel, BoxLayout.Y_AXIS));
        addressPanel.add(Box.createVerticalStrut(5)); // Add small space between address and social icons
        addressPanel.add(socialMediaPanel);

        // Add the Address panel with social media icons to the footer
        add(addressPanel);
        add(createFooterSection("Sales", "Sales Enquiries\nTelephone : 070144978\nE-mail : info@gmail.com"));
        add(createFooterSection("About Us", "Citron Villa Hotel is a charming boutique hotel offering serene accommodations in a picturesque setting. With beautifully designed rooms and personalized service, it’s the perfect escape for relaxation and rejuvenation. Ideal for those seeking comfort and tranquility."));
    }

    // Method to create a section in the footer
    private JPanel createFooterSection(String title, String content) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.LIGHT_GRAY);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea contentArea = new JTextArea(content);
        contentArea.setWrapStyleWord(true);
        contentArea.setLineWrap(true);
        contentArea.setEditable(false);
        contentArea.setBackground(Color.LIGHT_GRAY);
        contentArea.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(contentArea);

        return panel;
    }
}
