package InformationToRecord;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

public class insertChangePrice {
    public static void main(String[] args) {
        System.out.println("-----------------------------------------------------");
        System.out.println("Welcome to AirBNB!\nThis function is for changing the price of a listing for a specific time period on the system.");
        System.out.println("-----------------------------------------------------\n");

        System.out.println("Please enter the listing ID: ");
        Scanner scInt = new Scanner(System.in);
        int lid = scInt.nextInt();
        System.out.println("Please enter the start date: ");
        int start_date = scInt.nextInt();
        System.out.println("Please enter the end date: ");
        int end_date = scInt.nextInt();
        System.out.println("Please enter the price: ");
        int price = scInt.nextInt();
//
        try {
            String url = "jdbc:mysql://localhost:3306/C43Project";
            Connection conn = DriverManager.getConnection(url,"root","");
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO changeprice " +
                    "VALUES ('"+lid+"','"+start_date+"', '"+end_date+"', '"+price+"')");

            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

    }
}
