package ReportsToSupport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class largestCancellationRenterHost {
    public static void main(String[] args) {
        System.out.println("-----------------------------------------------------");
        System.out.println("Welcome to AirBNB!\nThis function is for finding the renter or host with the largest number of cancellation");
        System.out.println("-----------------------------------------------------\n");

        try {
            String url = "jdbc:mysql://localhost:3306/C43Project";
            Connection conn = DriverManager.getConnection(url, "root", "");
            Statement st = conn.createStatement();

            System.out.println("Do you want to see renters (A) or host (B)? Please enter either A or B");
            Scanner sc = new Scanner(System.in);
            char option1 = sc.next().charAt(0);
            char option = Character.toUpperCase(option1);
            if (option == 'A') {

                ResultSet resultSet = st.executeQuery("SELECT renterid, COUNT(lid) FROM reservations " +
                        "WHERE status = 0 AND renterid = cancelled_by GROUP BY renterid ORDER BY COUNT(lid) DESC");
                System.out.println("RenterID  Number of Cancellation");
                resultSet.next();
                System.out.println(resultSet.getInt(1) + "\t\t" + resultSet.getInt(2));


            } else if (option == 'B') {
                ResultSet resultSet = st.executeQuery("SELECT hostid, COUNT(lid) FROM reservations " +
                        "WHERE status = 0 AND hostid = cancelled_by GROUP BY hostid ORDER BY COUNT(lid) DESC");
                System.out.println("HostID  Number of Cancellation");
                resultSet.next();
                System.out.println(resultSet.getInt(1) + "\t\t" + resultSet.getInt(2));

            } else {
                System.out.println("Invalid input, please try again with either A or B");
            }
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }


    }
}
