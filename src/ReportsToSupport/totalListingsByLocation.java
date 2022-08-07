package ReportsToSupport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class totalListingsByLocation {
    public static void main(String[] args) {
        System.out.println("-----------------------------------------------------");
        System.out.println("Welcome to AirBNB!\nThis function is for providing the total number of bookings by location");
        System.out.println("-----------------------------------------------------\n");

        try {
            String url = "jdbc:mysql://localhost:3306/C43Project";
            Connection conn = DriverManager.getConnection(url, "root", "");
            Statement st = conn.createStatement();

            System.out.println("Do you want to group by country only (A) or by country and city (B) or by country, city and postal code (C). Please enter your input from A or B or C ");
            System.out.println("Please note that nothing will be displayed if there is no bookings within the time range you entered");

            Scanner scChar = new Scanner(System.in);
            char option1 = scChar.next().charAt(0);
            char option = Character.toUpperCase(option1);
            if (option != 'A' && option != 'B' && option != 'C') {
                System.out.println("Invalid input, please try again between A, B or C");
            }

            if (option == 'A') {
                ResultSet resultSet = st.executeQuery("SELECT country, COUNT(lid) FROM lists GROUP BY country");

                System.out.println("Country\t\t Number of Bookings");
                while (resultSet.next()){
                    System.out.println(resultSet.getString(1) + "\t\t" + resultSet.getInt(2));
                }
            } else if (option == 'B') {
//
                ResultSet resultSet = st.executeQuery("SELECT country, city, COUNT(lid) FROM lists GROUP BY country, city");

                System.out.println("Country \t City\t Number of Bookings");
                while (resultSet.next()){
                    System.out.println(resultSet.getString(1) + "\t\t\t " + resultSet.getString(2) + "\t\t\t" + resultSet.getInt(3));
                }

            } else{
                ResultSet resultSet = st.executeQuery("SELECT country, city, postal_code, COUNT(lid) FROM lists GROUP BY country, city, postal_code");

                System.out.println("Country \t City\t Postal Code\t Number of Bookings");
                while (resultSet.next()){
                    System.out.println(resultSet.getString(1) + "\t\t\t " + resultSet.getString(2) + "\t\t\t" + resultSet.getString(3) + "\t\t\t"+ resultSet.getInt(4));
                }
            }
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }






    }
}
