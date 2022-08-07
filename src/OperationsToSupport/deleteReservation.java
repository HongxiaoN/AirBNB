package OperationsToSupport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class deleteReservation {
    public static void main(String[] args) {

        System.out.println("-----------------------------------------------------");
        System.out.println("Welcome to AirBNB!\nThis function is for deleting an existing reservation on the system.");
        System.out.println("-----------------------------------------------------\n");

        Scanner scInt = new Scanner(System.in);
        System.out.println("Please enter host ID: ");
        int hostid = scInt.nextInt();
        System.out.println("Please enter renter ID: ");
        int renterid = scInt.nextInt();
        System.out.println("Please enter listing ID: ");
        int lid = scInt.nextInt();

        try {
            String url = "jdbc:mysql://localhost:3306/C43Project";
            Connection conn = DriverManager.getConnection(url,"root","");
            Statement st = conn.createStatement();

            ResultSet resultSet = st.executeQuery("SELECT * FROM reservations WHERE hostid= '" + hostid + "' AND renterid = '" +renterid+"' AND lid= '"+lid+"' AND status=1");
            if (resultSet.next() == true) {
                int isupdate = st.executeUpdate("UPDATE reservations SET status=0 WHERE hostid= '" + hostid + "' AND renterid = '"+renterid+"' AND lid= '"+lid+"' ");
                if (isupdate != 1) {
                    System.out.println("Failed to delete reservation");
                }

            } else {
                System.out.println("Such reservation does not exist");

            }

            System.out.println("Successful");


            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }







    }
}
