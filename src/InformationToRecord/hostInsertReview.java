package InformationToRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class hostInsertReview {
    public static void main(String[] args) {

        System.out.println("-----------------------------------------------------");
        System.out.println("Welcome to AirBNB!\nThis function is for adding comments and reviews on the system by host.");
        System.out.println("-----------------------------------------------------\n");

        System.out.println("Please enter your SIN number: ");
        Scanner scInt = new Scanner(System.in);
        int hostid = scInt.nextInt();
        System.out.println("Please enter renter ID: ");
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

            ResultSet resultSet = st.executeQuery("SELECT * FROM lists WHERE lid= '"+lid+"' AND exist = 1 ");
            if (resultSet.next() == false){
                System.out.println("Such listing does not exist");
                return;
            }

            ResultSet resultSet1 = st.executeQuery("SELECT * FROM users WHERE lid= '"+renterid+"' AND exist = 1 ");
            if (resultSet.next() == false){
                System.out.println("Such renter does not exist");
                return;
            }

            ResultSet resultSet2 = st.executeQuery("SELECT * FROM users WHERE lid= '"+hostid+"' AND exist = 1 ");
            if (resultSet.next() == false){
                System.out.println("Such host does not exist");
                return;
            }

            if (rate < 1 || rate > 5){
                System.out.println("Invalid rating, should be an integer between 1 to 5!");
            }

            if (comment.length() > 1000){
                System.out.println("Your comment exceed the maximum length");

            }

            int isfind = st.executeUpdate("UPDATE reservations SET host_rate='"+rate+"', host_comment= '"+comment+"' WHERE renterid= '"+renterid+"' AND hostid= '"+hostid+"' AND lid= '"+lid+"' ");
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
