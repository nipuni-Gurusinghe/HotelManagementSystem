import javax.swing.*;
import java.awt.*;

public class WelcomePage extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WelcomePage ui = new WelcomePage();
            ui.createAndShowGUI();
        });
    }
    void createAndShowGUI() {
        JFrame frame = new JFrame("Hotel Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLayout(new BorderLayout());
        HeaderPanel headerPanel = new HeaderPanel();
        frame.add(headerPanel.getPanel(), BorderLayout.NORTH);
        LoginPanel loginPanel = new LoginPanel();
        frame.add(loginPanel.getPanel(), BorderLayout.CENTER);
        FooterPanel footerPanel = new FooterPanel();
        frame.add(footerPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
