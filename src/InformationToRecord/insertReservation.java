package InformationToRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class insertReservation {
    public static void main(String[] args) {
        System.out.println("-----------------------------------------------------");
        System.out.println("Welcome to AirBNB!\nThis function is for making a new reservation for a specific time range on the system.");
        System.out.println("-----------------------------------------------------\n");

        try {
            String url = "jdbc:mysql://localhost:3306/C43Project";
            Connection conn = DriverManager.getConnection(url, "root", "");
            Statement st = conn.createStatement();

            Scanner scInt = new Scanner(System.in);
            System.out.println("Please enter the listing ID that you would like to proceed: ");
            int lid1 = scInt.nextInt();

            System.out.println("The following time period associated with the listing you entered are NOT available.");
            System.out.println("lid  start_date   end_date");
            ResultSet listlist = st.executeQuery("SELECT lid, start_date, end_date FROM reservations WHERE lid='" + lid1 + "' AND status=1");
            while (listlist.next()) {
                System.out.println(listlist.getInt(1) + "   " + listlist.getInt(2) + "     " + listlist.getInt(3));
            }

            System.out.println("Please enter the SIN number of the host: ");
            int hostid1 = scInt.nextInt();
            System.out.println("Please enter your SIN number: ");
            int renterid1 = scInt.nextInt();
            ResultSet isUser = st.executeQuery("SELECT * FROM users WHERE sin= '" + renterid1 + "' AND status=1");
            if (!isUser.next()) {
                System.out.println("You need to sign up for an account in order to proceed");
                return;
            }

            System.out.println("Please enter the start date: ");
            int start_date1 = scInt.nextInt();

            if (start_date1 < 20220101) {
                System.out.println("Cannot make reservation that has already passed!");
                return;
            }
            System.out.println("Please enter the end date: ");
            int end_date1 = scInt.nextInt();
            if (end_date1 < 20220101 || start_date1 >= end_date1) {
                System.out.println("Such end date is invalid!");
                return;
            }

            //print out change price table and also default price for the other time period
            ResultSet changeprice = st.executeQuery("SELECT * FROM changeprice WHERE lid= '" + lid1 + "' AND (('" + start_date1 + "' < end_date AND '" + start_date1 + "' >= start_date) OR ('" + end_date1 + "' <= end_date AND '" + end_date1 + "' > start_date)) ");
            while (changeprice.next()) {
                System.out.println(listlist.getInt(1) + " " + listlist.getInt(2) + " " + listlist.getInt(3) + " " + listlist.getInt(4));
            }

            ResultSet defaultprice = st.executeQuery("SELECT default_price FROM lists WHERE lid= '" + lid1 + "' ");
            defaultprice.next();
            System.out.println("All other time period beside above time period are set to default price of " + defaultprice.getInt(1));

            ResultSet isHost = st.executeQuery("SELECT * FROM owns WHERE lid= '" + lid1 + "' AND uid= '" + hostid1 + "'");
            if (!isHost.next()) {
                System.out.println("The host listing combination you entered is invalid!");
                return;
            }

            ResultSet isUser1 = st.executeQuery("SELECT * FROM users WHERE sin= '" + hostid1 + "' AND status=1");
            if (!isUser1.next()) {
                System.out.println("Such host does not exist");
                return;
            }

            ResultSet isConflictrenter = st.executeQuery("SELECT * FROM reservations WHERE renterid= '"
                    + renterid1 + "' AND (('" + start_date1 + "' < end_date AND '" + start_date1
                    + "' >= start_date) OR ('" + end_date1 + "' <= end_date AND '" + end_date1 + "' > start_date)) AND status = 1");
            if (isConflictrenter.next()) {
                System.out.println("There is a conflict with your current reservation!");
                return;
            }

            ResultSet isConflictlist = st.executeQuery("SELECT * FROM reservations WHERE lid= '" + lid1
                    + "' AND (('" + start_date1 + "' < end_date AND '" + start_date1 + "' >= start_date) OR ('" + end_date1 + "' <= end_date AND '" + end_date1 + "' > start_date)) AND status = 1");

            if (isConflictlist.next()) {
                System.out.println("There is a conflict with listing's reservation!");
                return;
            }

            ResultSet isCancelled = st.executeQuery("SELECT * FROM reservations WHERE hostid= '" + hostid1 + "' AND renterid= '"
                    + renterid1 + "' AND lid= '" + lid1 + "' AND start_date= '" + start_date1 + "' AND end_date= '"
                    + end_date1 + "' AND status=0 ");

            if (isCancelled.next()) {
                ResultSet iscancelled_by = st.executeQuery("SELECT * FROM reservations WHERE hostid= '" + hostid1 + "' AND renterid= '"
                        + renterid1 + "' AND lid= '" + lid1 + "' AND  start_date= '" + start_date1 + "' AND end_date= '"
                        + end_date1 + "' AND status=0 AND cancelled_by= '" + renterid1 + "' ");

                if (iscancelled_by.next()) {
                    int isupdated = st.executeUpdate("UPDATE reservations SET status=1, cancelled_by = NULL WHERE hostid= '" + hostid1 + "' AND renterid= '" +
                            renterid1 + "' AND lid= '" + lid1 + "' AND  start_date= '" + start_date1 + "' AND end_date= '" + end_date1 + "' ");

                    if (isupdated != 1) {
                        System.out.println("Failed to add back cancelled reservation");
                    }
                    return;
                } else {
                    System.out.println("This reservation has been cancelled by another user");
                    return;
                }
            } else {
                st.executeUpdate("INSERT INTO reservations " +
                        "VALUES ('" + hostid1 + "','" + renterid1 + "', '" + lid1 + "', '" + start_date1 + "','" + end_date1 + "', 1,NULL, 3, NULL, 3, NULL)");
            }
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }


    }
}
