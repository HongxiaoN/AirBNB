package InformationToRecord;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class insertOwns {
    public static void main(String[] args) {

        System.out.println("-----------------------------------------------------");
        System.out.println("Welcome to AirBNB!\nThis function is for making a new reservation for a specific time range on the system.");
        System.out.println("-----------------------------------------------------\n");

        System.out.println("Please enter the SIN number of the host: ");
        Scanner scInt = new Scanner(System.in);
        int hostid = scInt.nextInt();
        System.out.println("Please enter the listing ID: ");
        int lid = scInt.nextInt();

        try {
            String url = "jdbc:mysql://localhost:3306/C43Project";
            Connection conn = DriverManager.getConnection(url,"root","");
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO owns " +
                    "VALUES ('"+hostid+"','"+lid+"')");

            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }


    }

}
