package ReportsToSupport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class rankHostTotalListings {
    public static void main(String[] args) {

        System.out.println("-----------------------------------------------------");
        System.out.println("Welcome to AirBNB!\nThis function is for ranking host listings by location");
        System.out.println("-----------------------------------------------------\n");

        try {
            String url = "jdbc:mysql://localhost:3306/C43Project";
            Connection conn = DriverManager.getConnection(url, "root", "");
            Statement st = conn.createStatement();

            System.out.println("Do you want to rank in descending order by country only (A) or by country and city (B) Please enter your input from A or B");
            System.out.println("Please note that nothing will be displayed if there is no bookings within the time range you entered");

            Scanner scChar = new Scanner(System.in);
            char option1 = scChar.next().charAt(0);
            char option = Character.toUpperCase(option1);
            if (option != 'A' && option != 'B') {
                System.out.println("Invalid input, please try again between A or B");
            }

            if (option == 'A') {
                ResultSet resultSet = st.executeQuery("SELECT lists.country, owns.uid, COUNT(lists.lid) FROM lists " +
                        "INNER JOIN owns ON owns.lid=lists.lid INNER JOIN users ON users.sin = owns.uid WHERE lists.status=1 AND users.status=1 " +
                        "GROUP BY lists.country, owns.uid ORDER BY country, uid, COUNT(lists.lid) DESC");

                System.out.println("Country\t\t Host ID \t Number of Listings");
                while (resultSet.next()) {
                    System.out.println(resultSet.getString(1) + "\t\t\t" + resultSet.getInt(2) + "\t\t\t" + resultSet.getInt(3));
                }
            } else {
                ResultSet resultSet = st.executeQuery("SELECT lists.country, lists.city, owns.uid, COUNT(lists.lid) FROM lists " +
                        "INNER JOIN owns ON owns.lid=lists.lid INNER JOIN users ON users.sin = owns.uid WHERE lists.status=1 AND users.status=1 " +
                        "GROUP BY lists.country, lists.city, owns.uid ORDER BY lists.country, lists.city, owns.uid, COUNT(lists.lid) DESC");

                System.out.println("Country\t\t City \t\t Host ID \t\t Number of Listings");
                while (resultSet.next()) {
                    System.out.println(resultSet.getString(1) + "\t\t\t" + resultSet.getString(2) + "\t\t\t" + resultSet.getInt(3) + "\t\t\t" + resultSet.getInt(4));
                }
            }

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }


    }
}
