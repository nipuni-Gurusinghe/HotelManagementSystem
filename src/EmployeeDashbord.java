import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeDashbord extends JFrame {

    private JPanel headerPanel;

    public EmployeeDashbord() {
        setTitle("Employee Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        headerPanel = new JPanel(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        headerPanel.add(new Navbar(), BorderLayout.NORTH);
        add(new EmployeeDashBordMainContent(), BorderLayout.CENTER);
        add(new FooterPanel(), BorderLayout.SOUTH);
        setVisible(true);
    }

    // Main content
    class EmployeeDashBordMainContent extends JPanel {
        public EmployeeDashBordMainContent() {
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);


            JLabel dashboardLabel = new JLabel("Employee Dashboard", JLabel.LEFT);
            dashboardLabel.setFont(new Font("Serif", Font.BOLD, 24));
            dashboardLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            add(dashboardLabel, BorderLayout.NORTH);
            JPanel quickLinksPanel = new JPanel();
            quickLinksPanel.setLayout(new GridLayout(1, 3, 10, 10));
            quickLinksPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            quickLinksPanel.setBackground(Color.WHITE);

            String[] linkTitles = {"Check Special Works", "Update Password", "Calculate Salary"};
            String[] linkSubtitles = {
                    "View assigned special works", "Update Password", "Calculate your salary"
            };
            Color[] colors = {
                    new Color(255, 153, 51), new Color(51, 153, 255), new Color(0, 204, 153)
            };

            // Add quick links
            for (int i = 0; i < linkTitles.length; i++) {
                String title = linkTitles[i];
                String subtitle = linkSubtitles[i];
                Color color = colors[i];

                ActionListener actionListener = null;
                if (title.equals("Calculate Salary")) {
                    actionListener = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            new CalculateSalary().setVisible(true);
                        }
                    };
                } else if (title.equals("Update Password")) {
                    actionListener = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            new UpdatePassword().setVisible(true);
                        }
                    };
                }

                if (title.equals("Check Special Works")) {
                    actionListener = new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            new CheckSpecialWork().setVisible(true);
                        }
                    };
                }


                quickLinksPanel.add(createQuickLinkPanel(title, subtitle, color, actionListener));
            }

            add(quickLinksPanel, BorderLayout.CENTER);
        }

        private JPanel createQuickLinkPanel(String title, String subtitle, Color color, ActionListener actionListener) {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.setBackground(color);
            panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

            JLabel titleLabel = new JLabel(title, JLabel.CENTER);
            titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
            titleLabel.setForeground(Color.WHITE);

            JLabel subtitleLabel = new JLabel(subtitle, JLabel.CENTER);
            subtitleLabel.setFont(new Font("Serif", Font.PLAIN, 14));
            subtitleLabel.setForeground(Color.WHITE);

            JButton button = new JButton("Check Now");
            button.setFocusPainted(false);
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
            button.addActionListener(actionListener);

            panel.add(titleLabel, BorderLayout.NORTH);
            panel.add(subtitleLabel, BorderLayout.CENTER);
            panel.add(button, BorderLayout.SOUTH);

            return panel;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmployeeDashbord());
    }
}
