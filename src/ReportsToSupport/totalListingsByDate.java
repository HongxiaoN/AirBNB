package ReportsToSupport;

import java.sql.*;
import java.util.Scanner;

public class totalListingsByDate {
    public static void main(String[] args) {

        System.out.println("-----------------------------------------------------");
        System.out.println("Welcome to AirBNB!\nThis function is for providing the total number of bookings in a " +
                "specific date range by city, or by zip code within a city.");
        System.out.println("-----------------------------------------------------\n");

        try {
            String url = "jdbc:mysql://localhost:3306/C43Project";
            Connection conn = DriverManager.getConnection(url, "root", "");
            Statement st = conn.createStatement();

            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter the start date in the form of YYMMDD: ");
            int start_date = sc.nextInt();

            System.out.println("Please enter the end date in the form of YYMMDD: ");
            int end_date = sc.nextInt();

            if (end_date < start_date) {
                System.out.println("End date should be later than the start date. Please retry");
                return;
            }

            Scanner scString = new Scanner(System.in);


            System.out.println("Do you want to group by city only (A) or by postal code within a specific city (B). Please enter your input from A or B ");
            System.out.println("Please note that nothing will be displayed if there is no bookings within the time range you entered");
            Scanner scChar = new Scanner(System.in);
            char option1 = scChar.next().charAt(0);
            char option = Character.toUpperCase(option1);
            if (option != 'A' && option != 'B') {
                System.out.println("Invalid input, please try again with either A or B");
            }

            if (option == 'A') {
                ResultSet resultSet = st.executeQuery("SELECT lists.city, COUNT(lists.lid) FROM lists INNER JOIN reservations ON reservations.lid = lists.lid " +
                        "WHERE start_date > '" + start_date + "' AND end_date <  '" + end_date + "' GROUP BY lists.city");

                System.out.println("City\t\t Number of Bookings");
                while (resultSet.next()){
                    System.out.println(resultSet.getString(1) + "\t\t" + resultSet.getInt(2));
                }
            } else if (option == 'B') {
                ResultSet resultSet = st.executeQuery("SELECT lists.city, lists.postal_code, COUNT(lists.lid) FROM lists INNER JOIN reservations " +
                        "ON reservations.lid = lists.lid WHERE start_date > '" + start_date + "' AND end_date <  '" + end_date + "' " +
                        "GROUP BY lists.city, lists.postal_code ");


                System.out.println("City \t Postal Code\t Number of Bookings");
                while (resultSet.next()){
                    System.out.println(resultSet.getString(1) + "\t\t" + resultSet.getInt(2) + "\t\t\t " + resultSet.getInt(3));
                }

            }

            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }


    }
}
