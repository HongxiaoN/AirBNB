package InformationToRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class renterInsertReview {
    public static void main(String[] args) {

        System.out.println("-----------------------------------------------------");
        System.out.println("Welcome to AirBNB!\nThis function is for adding comments and reviews on the system by renter.");
        System.out.println("-----------------------------------------------------\n");

        System.out.println("Please enter the host ID: ");
        Scanner scInt = new Scanner(System.in);
        int hostid = scInt.nextInt();
        System.out.println("Please enter your SIN number: ");
        int renterid = scInt.nextInt();
        System.out.println("Please enter listing ID: ");
        int lid = scInt.nextInt();
        System.out.println("Please enter a rating between 1-5: ");
        int rate = scInt.nextInt();

        Scanner sc = new Scanner(System.in);
        System.out.println("Please type your comments within 100 words: ");
        String comment = sc.nextLine().toLowerCase();

        try {
            String url = "jdbc:mysql://localhost:3306/C43Project";
            Connection conn = DriverManager.getConnection(url,"root","");
            Statement st = conn.createStatement();

            if (rate < 1 || rate > 5){
                System.out.println("Invalid rating, should be an integer between 1 to 5!");
            }

            if (comment.length() > 1000){
                System.out.println("Your comment exceed the maximum length");

            }

            int isfind = st.executeUpdate("UPDATE reservations SET renter_rate='"+rate+"', renter_comment= '"+comment+"' WHERE renterid= '"+renterid+"' AND hostid= '"+hostid+"' AND lid= '"+lid+"' ");
            if (isfind != 1){
                System.out.println("Did not find a reservation combination with your input");
                return;
            }
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

    }
}
