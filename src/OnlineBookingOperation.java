import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OnlineBookingOperation {

    public static String generateBookingId() {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "SELECT MAX(CAST(SUBSTRING(BookingID, 2) AS UNSIGNED)) AS MaxBookingID FROM bookingdetails";
        String newBookingId = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int maxId = resultSet.getInt("MaxBookingID");
                int numericId = maxId + 1;
                newBookingId = "B" + String.format("%03d", numericId);
            } else {
                newBookingId = "B001"; // Start from B001 if no previous ID found
            }
        } catch (SQLException e) {
            e.printStackTrace();
            newBookingId = null;
        }

        return newBookingId;
    }

    public static boolean saveBookingDetails(
            String bookingId, String roomId, String fullName, String idNumber,
            String checkInDate, String checkOutDate, String adults,
            String childs) {

        Connection connection = DatabaseConnection.getConnection();
        String sql = "INSERT INTO bookingdetails (BookingID, RoomID, FullName, IDNumber, CheckInDate, CheckOutDate, Adults, Childs) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, bookingId);
            preparedStatement.setString(2, roomId);
            preparedStatement.setString(3, fullName);
            preparedStatement.setString(4, idNumber);
            preparedStatement.setString(5, checkInDate);
            preparedStatement.setString(6, checkOutDate);
            preparedStatement.setString(7, adults);
            preparedStatement.setString(8, childs);

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean savePaymentDetails(String bookingId, double amount) {
        Connection connection = DatabaseConnection.getConnection();
        String sql = "INSERT INTO paymentdetails (BookingID, PaymentAmount, PaymentDate) VALUES (?, ?, NOW())";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, bookingId);
            preparedStatement.setDouble(2, amount);

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
