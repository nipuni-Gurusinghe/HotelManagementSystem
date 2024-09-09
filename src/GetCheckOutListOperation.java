import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class GetCheckOutListOperation {

    public static List<Object[]> getCheckOutListByDate(Date checkOutDate) {
        List<Object[]> checkOutList = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT BookingID, FirstName, RoomNo, CheckOutDate FROM CheckInCheckOut WHERE Status = 'Checked Out' AND CheckOutDate = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDate(1, checkOutDate);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String bookingID = resultSet.getString("BookingID");
                String firstName = resultSet.getString("FirstName");
                String roomNo = resultSet.getString("RoomNo");
                String date = resultSet.getString("CheckOutDate");

                checkOutList.add(new Object[]{bookingID, firstName, roomNo, date});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return checkOutList;
    }
}
