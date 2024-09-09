import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginOperation {

    public static boolean validateLogin(String role, String username, String password) {
        Connection conn = DatabaseConnection.getConnection();
        String tableName = getTableName(role);

        if (tableName == null) {
            return false;
        }

        String query = "SELECT * FROM " + tableName + " WHERE UserName = ? AND Password = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String getTableName(String role) {
        switch (role) {
            case "Admin":
                return "admin";
            case "Visitor":
                return "customer";
            case "Employee":
                return "employee";
            default:
                return null;
        }
    }
}
