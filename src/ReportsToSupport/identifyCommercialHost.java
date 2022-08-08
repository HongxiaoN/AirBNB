package ReportsToSupport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class identifyCommercialHost {
    public static void main(String[] args) {

        System.out.println("-----------------------------------------------------");
        System.out.println("Welcome to AirBNB!\nThis function is for finding the commercial hosts");
        System.out.println("-----------------------------------------------------\n");
        try {
            String url = "jdbc:mysql://localhost:3306/C43Project";
            Connection conn = DriverManager.getConnection(url, "root", "");
            Statement st = conn.createStatement();

            ResultSet count = st.executeQuery("SELECT COUNT(lists.lid) FROM lists " +
                    "INNER JOIN owns ON owns.lid=lists.lid WHERE lists.status=1 " +
                    "GROUP BY lists.country, owns.uid ORDER BY lists.country, lists.city, owns.uid, COUNT(lists.lid) DESC");
            double row = 0;
            double total = 0;
            while (count.next()) {
                total += count.getInt(1);
                row += 1;
            }
            double avg = total / row;
            double flag = 1.1 * avg;

            System.out.println("The following hosts are considered to be flagged since their number of listings are greater than flag value: " + flag);

            ResultSet resultSet = st.executeQuery("SELECT owns.uid, COUNT(lists.lid) FROM lists " +
                    "INNER JOIN owns ON owns.lid=lists.lid INNER JOIN users ON users.sin = owns.uid WHERE lists.status=1 AND users.status = 1 " +
                    "GROUP BY lists.country, lists.city, owns.uid HAVING COUNT(lists.lid) > '" + flag + "' ORDER BY lists.country, lists.city, owns.uid, COUNT(lists.lid) DESC");

            System.out.println("Host ID  Number of Listings");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + "\t\t" + resultSet.getInt(2));
            }
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }


    }
}
