import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class GetCheckinlistOperation {

    public static List<Object[]> getCheckInListByDate(Date checkInDate) {
        List<Object[]> checkInList = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT BookingID, FirstName, RoomNo, CheckInDate FROM CheckInCheckOut " +
                    "WHERE Status = 'Checked In' AND DATE(CheckInDate) = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDate(1, checkInDate);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String bookingID = resultSet.getString("BookingID");
                String firstName = resultSet.getString("FirstName");
                String roomNo = resultSet.getString("RoomNo");
                Date date = resultSet.getDate("CheckInDate");

                checkInList.add(new Object[]{bookingID, firstName, roomNo, date.toString()});
            }
        } catch (Exception e) {
            // Print stack trace to console for debugging
            e.printStackTrace();

            // Show a dialog with detailed error message
            JOptionPane.showMessageDialog(null, "Error fetching data: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        if (checkInList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No data found for the selected date.",
                    "No Data", JOptionPane.INFORMATION_MESSAGE);
        }

        return checkInList;
    }
}
