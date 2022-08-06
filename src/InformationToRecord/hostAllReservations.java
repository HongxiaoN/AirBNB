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

        System.out.println("hostid renterid lid start_date  end_date  status host_rate host+comment renter_rate renter_comment");

        try {
            String url = "jdbc:mysql://localhost:3306/C43Project";
            Connection conn = DriverManager.getConnection(url,"root","");
            Statement st = conn.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM reservations WHERE hostid= '"+sin+"'");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + "      " + resultSet.getInt(2) + "        "  + resultSet.getInt(3) + "  "  + resultSet.getInt(4) + "    "  + resultSet.getInt(5) + "  "  + resultSet.getBoolean(6) + "  " + resultSet.getInt(7) + "         " + resultSet.getString(8) + "         " + resultSet.getInt(9) + "           "  + resultSet.getString(10) );
            }

            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

    }

}
