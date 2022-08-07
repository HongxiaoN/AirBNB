package OperationsToSupport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class deleteListing {
    public static void main(String[] args) {
        System.out.println("-----------------------------------------------------");
        System.out.println("Welcome to AirBNB.");
        System.out.println("This deleteListing function is for delete a new listing for a host(user) " +
                "with checking input validation");
        System.out.println("-----------------------------------------------------\n");


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/C43Project", "root", ""
            );
            Statement statement = connection.createStatement();

            System.out.println("You will delete a listing. CAUTION: you CAN only delete your own listings.");
            System.out.println("At first, what is your sin? It should be 9 digit number!");
            Scanner input_sin = new Scanner(System.in);    //System.in is a standard input stream
            int sin = input_sin.nextInt();
            ResultSet checkSIN = statement.executeQuery("SELECT sin FROM users WHERE sin='" + sin + "' AND status=1");
            if (!checkSIN.next()) {
                System.out.println("ERROR: the sin(user): " + sin + ", you enter are not exist! please check you sin is correct");
                return;
            }

            System.out.println("what is your lid? It should be 5 digit number from 0 - 99999!");
            Scanner input_lid = new Scanner(System.in);    //System.in is a standard input stream
            int lid = input_lid.nextInt();
            ResultSet checklid = statement.executeQuery("SELECT * FROM lists WHERE lid='" + lid + " AND status=1'");
            if (!checklid.next()) {
                System.out.println("ERROR: the lid: " + lid + ", you enter are not exist! please check you lid is correct");
                return;
            }

            ResultSet checkOwn = statement.executeQuery("SELECT * FROM owns WHERE uid='" + sin + "' AND lid='" + lid + "'");
            if (!checkOwn.next()) {
                System.out.println("ERROR: You don't own this list!");
                return;
            }

            int updateReservations = statement.executeUpdate("UPDATE reservations SET status = 0, cancelled_by = hostid WHERE lid='"
                    + lid + "' AND start_date >=20220101 AND end_date>=20220101 AND status = 1");

            System.out.println("Successful cancel " + updateReservations + " reservations that book your house.");

            int updateList = statement.executeUpdate("UPDATE lists SET status = 0 WHERE lid='" + lid + "'");
            if (updateList == 1) {
                System.out.println("Successful delete this list that u own.");
                return;
            } else {
                System.out.println("Fail to update the status");
            }

            System.out.println("finish");
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
