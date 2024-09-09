import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AssignWorkOperation {

    public static void assignWork(String employeeID, String employeeName) {
        System.out.println("Assign work button clicked for EmployeeID: " + employeeID); // Debugging

        // Create a new JFrame for the work assignment form
        JFrame workFrame = new JFrame("Assign Work");
        workFrame.setSize(400, 300);
        workFrame.setLayout(new GridBagLayout());
        workFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // GridBagConstraints to manage layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);


        JLabel workLabel = new JLabel("Enter Work:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        workFrame.add(workLabel, gbc);


        JTextArea workArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(workArea);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        workFrame.add(scrollPane, gbc);


        JButton enterButton = new JButton("Enter");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        workFrame.add(enterButton, gbc);


        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String work = workArea.getText();

                if (work != null && !work.trim().isEmpty()) {
                    Connection con = null;
                    try {
                        con = DatabaseConnection.getConnection();
                        // check the current WorkStatus and SpecialWork values
                        String checkQuery = "SELECT SpecialWork, WorkStatus FROM Employee WHERE EmployeeID = ?";
                        PreparedStatement checkStmt = con.prepareStatement(checkQuery);
                        checkStmt.setString(1, employeeID);
                        ResultSet rs = checkStmt.executeQuery();

                        if (rs.next()) {
                            String currentSpecialWork = rs.getString("SpecialWork");
                            String workStatus = rs.getString("WorkStatus");

                            if ("OK".equals(workStatus)) {
                                if (currentSpecialWork != null && !currentSpecialWork.trim().isEmpty()) {
                                    String updateQuery = "UPDATE Employee SET SpecialWork = ?, WorkStatus = 'Pending' WHERE EmployeeID = ?";
                                    PreparedStatement updateStmt = con.prepareStatement(updateQuery);
                                    updateStmt.setString(1, work);
                                    updateStmt.setString(2, employeeID);

                                    int rowsUpdated = updateStmt.executeUpdate();
                                    if (rowsUpdated > 0) {
                                        JOptionPane.showMessageDialog(workFrame, "Work assigned successfully and status updated to Pending!");
                                    } else {
                                        JOptionPane.showMessageDialog(workFrame, "Failed to assign work.");
                                    }
                                    updateStmt.close();
                                } else {
                                    JOptionPane.showMessageDialog(workFrame, "No existing work to update.", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(workFrame, "WorkStatus is not OK. Cannot assign new work.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(workFrame, "Employee not found.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        checkStmt.close();
                        con.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(workFrame, "An error occurred while assigning work.", "Error", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        if (con != null) {
                            try {
                                con.close();
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    workFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(workFrame, "Work description cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        workFrame.setVisible(true);
    }
}
