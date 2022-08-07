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

        try {
            String url = "jdbc:mysql://localhost:3306/C43Project";
            Connection conn = DriverManager.getConnection(url,"root","");
            Statement st = conn.createStatement();

            Scanner scInt = new Scanner(System.in);

            System.out.println("Please enter host ID: ");
            int hostid = scInt.nextInt();
            // Check host is in users
            ResultSet checkHost = st.executeQuery("SELECT sin FROM users WHERE sin='" + hostid + "' AND status=1");
            if (!checkHost.next()) {
                System.out.println("ERROR: the host: " + hostid + ", you enter are not exist! please check the sin is correct");
                return;
            }

            // Get renterid and check validation
            System.out.println("Please enter renter ID: ");
            int renterid = scInt.nextInt();
            // Check renter is in users
            ResultSet checkRenter = st.executeQuery("SELECT sin FROM users WHERE sin='" + renterid + "' AND status=1");
            if (!checkRenter.next()) {
                System.out.println("ERROR: the renter: " + renterid + ", you enter are not exist! please check the sin is correct");
                return;
            }

            System.out.println("Who are you? Please enter your sin: ");
            int userid = scInt.nextInt();
            // Check user is in users
            if(userid != hostid && userid != renterid){
                System.out.println("ERROR: You are NOT either a host nor renter, you can not change any reservation");
                return;
            }

            System.out.println("Please enter listing ID: ");
            int lid = scInt.nextInt();
            // Check renter is in users
            ResultSet checkLid = st.executeQuery("SELECT * FROM lists WHERE lid='" + lid + "' AND status=1");
            if (!checkLid.next()) {
                System.out.println("ERROR: the lid: " + lid + ", you enter are not exist! please check the sin is correct");
                return;
            }

            System.out.println("Please enter start time for this reservation: ");
            int starttime = scInt.nextInt();
            System.out.println("Please enter end time for this reservation: ");
            int endtime = scInt.nextInt();

            // Check reservation is in reservations table
            ResultSet checkReservation = st.executeQuery("SELECT * FROM lists WHERE hostid='" + hostid +"' AND renterid='"
                    + renterid + "' AND lid=" + lid + "' AND start_date='" + starttime + "' AND end_date='"
                    + endtime + "' AND status=1");
            if (!checkReservation.next()) {
                System.out.println("ERROR: This reservation is not exist. Please check again!");
                return;
            }

            int isupdate = st.executeUpdate("UPDATE reservations SET status=0, cancelled_by='" + userid
                    + "' WHERE hostid='" + hostid +"' AND renterid='" + renterid + "' AND lid="
                    + lid + "' AND start_date='" + starttime + "' AND end_date='" + endtime + "' AND status=1");
            if (isupdate != 1) {
                System.out.println("Failed to delete reservation.");
            }
            else {
                System.out.println("Successfully to delete reservation. Your user id is " + userid);
            }

            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }







    }
}
