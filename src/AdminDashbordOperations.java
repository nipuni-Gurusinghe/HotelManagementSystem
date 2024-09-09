import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminDashbordOperations {

    public static boolean addRoom(String roomType, String status, double price, String imagePath) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO rooms (RoomType, Status, PriceForOneDay, Image) VALUES (?, ?, ?, ?)")) {

            statement.setString(1, roomType);
            statement.setString(2, status);
            statement.setDouble(3, price);
            statement.setString(4, imagePath);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }


    public static boolean removeEmployee(String employeeId) {
        String query = "DELETE FROM employee WHERE EmployeeID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, employeeId);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }



    public static boolean addEmployee(String fullName, String password, String mobileNumber,
                                      String emailAddress, String role, String username, String imagePath) {
        String query;


        if ("Admin".equalsIgnoreCase(role)) {
            query = "INSERT INTO admin (FullName, Password, MobileNumber, EmailAddress, Username, Image) VALUES (?, ?, ?, ?, ?, ?)";
        } else {

            query = "INSERT INTO employee (FullName, Password, MobileNumber, EmailAddress, Role, UserName, Image) VALUES (?, ?, ?, ?, ?, ?, ?)";
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, fullName);
            statement.setString(2, password);
            statement.setString(3, mobileNumber);
            statement.setString(4, emailAddress);

            if ("Admin".equalsIgnoreCase(role)) {
                statement.setString(5, username);
                statement.setString(6, imagePath);
            } else {
                statement.setString(5, role);
                statement.setString(6, username);
                statement.setString(7, imagePath);
            }

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }


}
