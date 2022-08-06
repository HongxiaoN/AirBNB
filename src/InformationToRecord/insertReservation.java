package InformationToRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

public class insertReservation {
    public static void main(String[] args) {
        System.out.println("-----------------------------------------------------");
        System.out.println("Welcome to AirBNB!\nThis function is for making a new reservation for a specific time range on the system.");
        System.out.println("-----------------------------------------------------\n");

        System.out.println("Please enter the SIN number of the host: ");
        Scanner scInt = new Scanner(System.in);
        int hostid = scInt.nextInt();
        System.out.println("Please enter your SIN number: ");
        int renterid = scInt.nextInt();
        System.out.println("Please enter the listing ID that you would like to proceed: ");
        int lid = scInt.nextInt();
        System.out.println("Please enter the start date: ");
        int start_date = scInt.nextInt();

        if (start_date < 20220101){
            System.out.println("Cannot make reservation that has already passed!");
            return;
        }

        System.out.println("Please enter the end date: ");
        int end_date = scInt.nextInt();
        if (end_date < 20220101 || start_date >= end_date){
            System.out.println("Such end date is invalid!");
            return;
        }

        try {
            String url = "jdbc:mysql://localhost:3306/C43Project";
            Connection conn = DriverManager.getConnection(url,"root","");
            Statement st = conn.createStatement();

            









            st.executeUpdate("INSERT INTO reservation " +
                    "VALUES ('"+sin+"','"+username+"', '"+email+"', '"+address+"','"+birthday+"', '"+occupation+"','"+card+"')");

            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }


    }
}
