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
            Connection conn = DriverManager.getConnection(url, "root", "");
            Statement st = conn.createStatement();

            ResultSet resultSet = st.executeQuery("SELECT * FROM users WHERE sin= '" + sin + "' AND status=1");
            if (resultSet.next() == true) {

                // Change all this user's as renter AND host reservation status to 0.
                int updateRenterReservation = st.executeUpdate("UPDATE reservations SET status=0, cancelled_by = renterid WHERE renterid= '" + sin
                        + "' And start_date >= 20220101 And status = 1");
                System.out.println("Successfully to delete " + updateRenterReservation + " reservation for this user as renter.");

                int updateHostReservation = st.executeUpdate("UPDATE reservations SET status=0, cancelled_by = hostid WHERE hostid= '" + sin
                        + "' And start_date >= 20220101 And status = 1");
                System.out.println("Successfully to delete " + updateHostReservation + " reservation for this user as host.");

                // Change all list belong to this user status to 0
                int updateList = st.executeUpdate("UPDATE lists NATURAL JOIN owns SET lists.status = 0 " +
                        "WHERE owns.uid = '" + sin + "' AND lists.status = 1");
                System.out.println("Successfully to delete " + updateList + " list(s) for this user as host.");

                // Finally delete this user.
                int isupdate = st.executeUpdate("UPDATE users SET status=0 WHERE sin= '" + sin + "' ");
                if (isupdate != 1) {
                    System.out.println("Failed to delete user");
                } else {
                    System.out.println("Successfully to delete user");
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
