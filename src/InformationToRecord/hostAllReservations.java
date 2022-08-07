package InformationToRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class hostAllReservations {

    public static void main(String[] args) {

        System.out.println("-----------------------------------------------------");
        System.out.println("Welcome to AirBNB!\nThis function is for showing complete history and future bookings with your listings on the system.");
        System.out.println("-----------------------------------------------------\n");

        System.out.println("Please enter your SIN number: ");
        Scanner scInt = new Scanner(System.in);
        int sin = scInt.nextInt();


        try {
            String url = "jdbc:mysql://localhost:3306/C43Project";
            Connection conn = DriverManager.getConnection(url, "root", "");
            Statement st = conn.createStatement();

            // Check user is in users
            ResultSet checkHost = st.executeQuery("SELECT sin FROM users WHERE sin='" + sin + "' AND status=1");
            if (!checkHost.next()) {
                System.out.println("ERROR: the host: " + sin + ", you enter are not exist! please check the sin is correct");
                return;
            } else {
                System.out.println("hostid renterid lid start_date  end_date  status cancelled_by host_rate host+comment renter_rate renter_comment");
                ResultSet resultSet = st.executeQuery("SELECT * FROM reservations WHERE hostid= '" + sin + "'");
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt(1) + "      " + resultSet.getInt(2) + "        " + resultSet.getInt(3)
                            + "    " + resultSet.getInt(4) + "   " + resultSet.getInt(5) + "  "
                            + resultSet.getBoolean(6) + "         " + resultSet.getInt(7) + "     "
                            + resultSet.getString(8) + "         " + resultSet.getString(9) + "           "
                            + resultSet.getString(10) + "      " + resultSet.getString(11));
                }
            }
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

    }

}
