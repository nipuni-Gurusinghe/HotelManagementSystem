import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

public class AvailableRoomOperation {

    public List<Object[]> getAvailableRooms(String roomType) {
        List<Object[]> roomDataList = new ArrayList<>();
        Connection connection;
        Statement statement;
        ResultSet resultSet;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.createStatement();


            String query = String.format(
                    "SELECT r.RoomID, r.Status, r.PriceForOneDay, r.Image, " +
                            "bd.CheckInDate, bd.CheckOutDate " +
                            "FROM rooms r " +
                            "LEFT JOIN bookingdetails bd ON r.RoomID = bd.RoomID " +
                            "WHERE r.RoomType = '%s'", roomType
            );

            resultSet = statement.executeQuery(query);

            // Use a Map to store booking dates for each RoomID
            Map<String, Object[]> roomDataMap = new HashMap<>();

            while (resultSet.next()) {
                String roomId = resultSet.getString("RoomID");
                String status = resultSet.getString("Status");
                double priceForOneDay = resultSet.getDouble("PriceForOneDay");
                String imagePath = resultSet.getString("Image");
                Date checkInDate = resultSet.getDate("CheckInDate");
                Date checkOutDate = resultSet.getDate("CheckOutDate");

                String bookedDates = checkInDate != null && checkOutDate != null
                        ? String.format("%s to %s", dateFormat.format(checkInDate), dateFormat.format(checkOutDate))
                        : "Available";

                if (roomDataMap.containsKey(roomId)) {
                    // Append the booked dates to the existing entry with a line break
                    Object[] existingData = roomDataMap.get(roomId);
                    String existingDates = (String) existingData[3];
                    existingData[3] = existingDates.equals("Available") ? bookedDates : existingDates + "<br>" + bookedDates;
                } else {
                    // Add a new entry to the map with HTML tags
                    roomDataMap.put(roomId, new Object[]{
                            roomId, status, priceForOneDay, "<html>" + bookedDates + "</html>", imagePath, "Online Book"});
                }
            }

            // Convert the map values to a list
            roomDataList.addAll(roomDataMap.values());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roomDataList;
    }
}
