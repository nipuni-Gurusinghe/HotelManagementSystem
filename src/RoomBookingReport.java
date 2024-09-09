import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Arrays;
import javax.swing.table.DefaultTableModel;

public class RoomBookingReport extends JFrame {

    public RoomBookingReport() {
        setTitle("Room Booking Report");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(new Navbar(), BorderLayout.NORTH);
        add(createBookingTable(), BorderLayout.CENTER);
        add(new FooterPanel(), BorderLayout.SOUTH);
        setVisible(true);
    }

    private JScrollPane createBookingTable() {
        String[] columnNames = {"Booking ID", "Room ID", "Full Name", "ID Number", "Check-In Date", "Check-Out Date", "Adults", "Childs"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection != null) {
                System.out.println("Connected to database.");
            }

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT BookingID, RoomID, FullName, IDNumber, CheckInDate, CheckOutDate, Adults, Childs FROM bookingdetails");

            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getString("BookingID"),
                        resultSet.getInt("RoomID"),
                        resultSet.getString("FullName"),
                        resultSet.getString("IDNumber"),
                        resultSet.getDate("CheckInDate"),
                        resultSet.getDate("CheckOutDate"),
                        resultSet.getInt("Adults"),
                        resultSet.getInt("Childs")
                };
                System.out.println("Row fetched: " + Arrays.toString(row));
                model.addRow(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        JTable table = new JTable(model);
        return new JScrollPane(table);
    }

    public static void main(String[] args) {
        new RoomBookingReport();
    }
}
