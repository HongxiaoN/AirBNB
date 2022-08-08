package ReportsToSupport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class popularPhrases {
    public static void main(String[] args) {
        System.out.println("-----------------------------------------------------");
        System.out.println("Welcome to AirBNB!\nThis function is for showing the most popular word in a single list.");
        System.out.println("-----------------------------------------------------\n");

        try {
            String url = "jdbc:mysql://localhost:3306/C43Project";
            Connection conn = DriverManager.getConnection(url, "root", "");
            Statement st = conn.createStatement();

            Scanner scInt = new Scanner(System.in);
            System.out.println("What is list id?");
            int lid = scInt.nextInt();

            // Check renter is in users
            ResultSet checkLid = st.executeQuery("SELECT * FROM lists WHERE lid='" + lid + "' AND status=1");
            if (!checkLid.next()) {
                System.out.println("ERROR: the lid: " + lid + ", you enter are not exist! please check the lid is correct");
                return;
            }

            ResultSet checkRenter = st.executeQuery("select substring_index(substring_index(r.title, ' ', n.n), ' ', -1) as word," +
                    "count(*)" +
                    "from results r join" +
                    "numbers n\n" +
                    "on n.n <= length(title) - length(replace(title, ' ', '')) + 1\n" +
                    "group by word;");

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }



    }
}
