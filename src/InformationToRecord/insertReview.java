package InformationToRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class insertReview {
    public static void main(String[] args) {

        System.out.println("-----------------------------------------------------");
        System.out.println("Welcome to AirBNB!\nThis function is for adding comments and reviews on the system by host.");
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

            System.out.println("You can only write review for past reservation. Please enter start time for this reservation: ");
            int startdate = scInt.nextInt();
            if(startdate > 20220101){
                System.out.println("You can only write review for past reservation.");
                return;
            }
            System.out.println("You can only write review for past reservation. Please enter end time for this reservation: ");
            int enddate = scInt.nextInt();
            if(enddate < startdate){
                System.out.println("End date must be late than start date.");
                return;
            }

            if (enddate < 20211131){
                System.out.println("This reservation has already passed more than one month. It cannot be reviewed anymore");
                return;
            }

            // Check reservation is in reservations table
            ResultSet checkReservation = st.executeQuery("SELECT * FROM reservations WHERE hostid='" + hostid +"' AND renterid='"
                    + renterid + "' AND hostid != renterid AND lid='" + lid + "' AND start_date='" + startdate + "' AND end_date='"
                    + enddate + "' AND status=1");
            if (!checkReservation.next()) {
                System.out.println("ERROR: This reservation is not exist. Please check again!");
                return;
            }

            System.out.println("Please enter a rating between 1-5: ");
            int rate = scInt.nextInt();
            if (rate < 1 || rate > 5){
                System.out.println("Invalid rating, should be an integer between 1 to 5!");
                return;
            }

            Scanner sc = new Scanner(System.in);
            System.out.println("Please type your comments within 100 words: ");
            String comment = sc.nextLine();
            if (comment.length() > 1000){
                System.out.println("Your comment exceed the maximum length");
                return;
            }

            if(userid == hostid){
                int hostComment = st.executeUpdate("UPDATE reservations SET host_rate='"+rate+"', host_comment= '"+comment
                        +"' WHERE hostid='" + hostid +"' AND renterid='" + renterid + "' AND lid='"
                        + lid + "' AND start_date='" + startdate + "' AND end_date='" + enddate + "' AND status=1");
                if(hostComment == 1){
                    System.out.println("Successfully to update corresponding reservation's review. Your host id is " + userid);
                }
                else{
                    System.out.println("Fail to update corresponding reservation's review. Your host id is " + userid);
                }
            }
            else{
                int renterComment = st.executeUpdate("UPDATE reservations SET renter_rate='"+rate+"', renter_comment= '"+comment
                        +"' WHERE hostid='" + hostid +"' AND renterid='" + renterid + "' AND lid='"
                        + lid + "' AND start_date='" + startdate + "' AND end_date='" + enddate + "' AND status=1");
                if(renterComment == 1){
                    System.out.println("Successfully to update corresponding reservation's review. Your renter id is " + userid);
                }
                else{
                    System.out.println("Fail to update corresponding reservation's review. Your renter id is " + userid);
                }
            }
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }





    }

}
