import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckOutOperation {

    private Connection connection;

    public CheckOutOperation() {
        connection = DatabaseConnection.getConnection();
    }

    public double getAmountPaid(String bookingID) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String sql = "SELECT PaymentAmount FROM paymentdetails WHERE BookingID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bookingID);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("PaymentAmount");
            } else {
                return 0.0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
        } finally {
            closeResources(resultSet, preparedStatement, null);
        }
    }

    public double calculateTotal(double totalCharges, double otherCharges, double discount) {
        return totalCharges + otherCharges - discount;
    }

    public double calculateBalance(double total, double amountPaid) {
        return total - amountPaid;
    }

    public boolean saveCheckOutDate(String bookingID, Date checkOutDate) {
        PreparedStatement preparedStatement = null;

        try {
            // SQL query to update CheckOutDate and Status
            String sql = "UPDATE checkincheckout SET CheckOutDate = ?, Status = 'Checked Out' WHERE BookingID = ?";
            preparedStatement = connection.prepareStatement(sql);
            //set format to the date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(checkOutDate);
            preparedStatement.setString(1, formattedDate);
            preparedStatement.setString(2, bookingID);
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(null, preparedStatement, null);
        }
    }


    public Date getCheckInDate(String bookingID) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String sql = "SELECT CheckInDate FROM checkincheckout WHERE BookingID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bookingID);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDate("CheckInDate");
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeResources(resultSet, preparedStatement, null);
        }
    }

    public long calculateNoOfDays(Date checkInDate, Date checkOutDate) {
        long diffInMillis = checkOutDate.getTime() - checkInDate.getTime();
        return diffInMillis / (1000 * 60 * 60 * 24); // Convert milliseconds to days
    }

    public String getRoomID(String bookingID) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String sql = "SELECT RoomNo FROM checkincheckout WHERE BookingID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bookingID);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("RoomNo");
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeResources(resultSet, preparedStatement, null);
        }
    }

    public double getPriceForOneDay(String roomID) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String sql = "SELECT PriceForOneDay FROM rooms WHERE RoomID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, roomID);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("PriceForOneDay");
            } else {
                return 0.0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
        } finally {
            closeResources(resultSet, preparedStatement, null);
        }
    }

    public double calculateTotalCharges(String bookingID, Date checkOutDate) {
        Date checkInDate = getCheckInDate(bookingID);
        if (checkInDate == null || checkOutDate == null) {
            return 0.0;
        }

        long noOfDays = calculateNoOfDays(checkInDate, checkOutDate);
        if (noOfDays <= 0) {
            return 0.0;
        }

        String roomNo = getRoomID(bookingID);
        if (roomNo == null) {
            return 0.0;
        }

        double priceForOneDay = getPriceForOneDay(roomNo);
        if (priceForOneDay <= 0) {
            return 0.0;
        }

        return noOfDays * priceForOneDay;
    }

    private void closeResources(ResultSet resultSet, PreparedStatement preparedStatement, Statement statement) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
