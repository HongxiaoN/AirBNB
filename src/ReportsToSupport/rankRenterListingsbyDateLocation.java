package ReportsToSupport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class rankRenterListingsbyDateLocation {
    public static void main(String[] args) {

        System.out.println("-----------------------------------------------------");
        System.out.println("Welcome to AirBNB!\nThis function is for ranking renters within a time range or/and by city");
        System.out.println("-----------------------------------------------------\n");

        try {
            String url = "jdbc:mysql://localhost:3306/C43Project";
            Connection conn = DriverManager.getConnection(url, "root", "");
            Statement st = conn.createStatement();

            Scanner scInt = new Scanner(System.in);
            System.out.println("Input start time: ");
            int start_date = scInt.nextInt();
            System.out.println("Input end time: ");
            int end_date = scInt.nextInt();


            System.out.println("Do you want to rank renters in descending order by time period only (A) or by time period and city (B). Please enter your choice between A and B: ");
            System.out.println("Please note that nothing will be displayed if there is no bookings within the time range you entered");

            Scanner sc = new Scanner(System.in);
            char option1 = sc.next().charAt(0);
            char option = Character.toUpperCase(option1);
            if (option != 'A' && option != 'B') {
                System.out.println("Invalid input, please try again with either A or B");
            }

            if (option == 'A') {
                ResultSet resultSet = st.executeQuery("SELECT reservations.renterid, COUNT(reservations.lid) " +
                        "FROM reservations INNER JOIN users ON reservations.renterid = users.sin " +
                        "WHERE start_date > '" + start_date + "' AND end_date < '" + end_date + "' AND users.status=1 AND reservations.renterid != reservations.hostid " +
                        "GROUP BY reservations.renterid ORDER BY COUNT(reservations.lid) DESC");


                System.out.println("Renter ID\t Number of Bookings");
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt(1) + "\t\t\t " + resultSet.getInt(2));
                }

            } else if (option == 'B') {

                ResultSet resultSet = st.executeQuery("SELECT reservations.renterid, lists.city, COUNT(reservations.lid) " +
                        "FROM reservations INNER JOIN users ON reservations.renterid = users.sin INNER JOIN lists ON reservations.lid = lists.lid " +
                        "WHERE reservations.start_date > '" + start_date + "' AND reservations.end_date < '" + end_date + "' AND users.status=1 AND reservations.renterid != reservations.hostid " +
                        "GROUP BY reservations.renterid, lists.city HAVING COUNT(reservations.lid) >= 2 ORDER BY COUNT(reservations.lid) DESC");

                System.out.println("Renter ID \t City \t Number of Bookings");
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt(1) + "\t\t\t " + resultSet.getString(2) + "\t\t" + resultSet.getInt(3));
                }

            }

            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

    }
}
