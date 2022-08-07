package OperationsToSupport;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class deleteUser {
    public static void main(String[] args) {
        System.out.println("-----------------------------------------------------");
        System.out.println("Welcome to AirBNB!\nThis function is for deleting an existing user on the system.");
        System.out.println("-----------------------------------------------------\n");

        System.out.println("Please enter your SIN number: ");
        Scanner scInt = new Scanner(System.in);
        int sin = scInt.nextInt();

        try {
            String url = "jdbc:mysql://localhost:3306/C43Project";
            Connection conn = DriverManager.getConnection(url,"root","");
            Statement st = conn.createStatement();

            ResultSet resultSet = st.executeQuery("SELECT * FROM users WHERE sin= '" + sin + "' AND status=1");
            if (resultSet.next() == true) {
                int isupdate = st.executeUpdate("UPDATE users SET status=0 WHERE sin= '" + sin + "' ");
                if (isupdate != 1) {
                    System.out.println("Failed to delete user");
                }

            } else {
                System.out.println("Such user does not exist");

            }

            System.out.println("Successful");


            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }










    }
}
