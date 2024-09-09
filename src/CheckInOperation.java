import java.sql.*;

public class CheckInOperation {

    public boolean saveCheckInDetails(String bookingID, String idCardNo, String firstName, String lastName, Date checkInDate,
                                      String vehicle, String plateNo, String roomNumber) {
        Connection connection = null;
        PreparedStatement checkStmt = null;
        PreparedStatement insertStmt = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();

            String checkSql = "SELECT 1 FROM BookingDetails WHERE BookingID = ?";
            checkStmt = connection.prepareStatement(checkSql);
            checkStmt.setString(1, bookingID);
            resultSet = checkStmt.executeQuery();

            if (resultSet.next()) {
                String insertSql = "INSERT INTO CheckInCheckOut (BookingID, ID_Number, FirstName, LastName, Vehicle, PlateNo, RoomNo, CheckInDate, Status) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                insertStmt = connection.prepareStatement(insertSql);
                insertStmt.setString(1, bookingID);
                insertStmt.setString(2, idCardNo);
                insertStmt.setString(3, firstName);
                insertStmt.setString(4, lastName);
                insertStmt.setString(5, vehicle);
                insertStmt.setString(6, plateNo);
                insertStmt.setString(7, roomNumber);
                insertStmt.setDate(8, checkInDate);
                insertStmt.setString(9, "Checked In");

                int rowsAffected = insertStmt.executeUpdate();
                return rowsAffected > 0;

            } else {
                System.out.println("BookingID not found in BookingDetails table.");
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (checkStmt != null) checkStmt.close();
                if (insertStmt != null) insertStmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
