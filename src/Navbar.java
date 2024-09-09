import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URI;

public class Navbar extends JPanel {

    public Navbar() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(new Color(173, 216, 230));
        JButton homeButton = new JButton("Home");
        JButton contactUsButton = new JButton("Contact Us");

        homeButton.addActionListener(createHomeAction());
        contactUsButton.addActionListener(createContactUsAction());

        add(homeButton);
        add(contactUsButton);
    }


    private ActionListener createHomeAction() {
        return e -> {
            SwingUtilities.getWindowAncestor(this).dispose();
            WelcomePage ui = new WelcomePage();
            ui.createAndShowGUI();
        };
    }

    private ActionListener createContactUsAction() {
        return e -> {
            try {
                String whatsappUrl = "https://wa.me/+94701424978";
                Desktop.getDesktop().browse(new URI(whatsappUrl));
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Unable to open WhatsApp. Please check your internet connection or try again later.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        };
    }
}
