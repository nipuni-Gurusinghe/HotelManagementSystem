import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationOperation {

    public static boolean addCustomer(String fullName, String password, String mobileNumber,
                                      String emailAddress, String username) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO customer (FullName, Password, MobileNumber, EmailAddress, Username) VALUES (?, ?, ?, ?, ?)")) {

            statement.setString(1, fullName);
            statement.setString(2, password);
            statement.setString(3, mobileNumber);
            statement.setString(4, emailAddress);
            statement.setString(5, username);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
