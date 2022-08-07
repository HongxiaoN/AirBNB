package InformationToRecord;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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

        System.out.println("Please enter your SIN number: ");
        int uid = scInt.nextInt();

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

            ResultSet resultSet2 = st.executeQuery("SELECT * FROM users WHERE sin= '"+uid+"' AND status = 1 ");
            if (resultSet2.next() == false){
                System.out.println("Such host does not exist");
                return;
            }

            ResultSet resultSet1 = st.executeQuery("SELECT * FROM owns WHERE uid= '"+uid+"' AND lid= '"+lid+"' ");
            if (resultSet1.next() == false){
                System.out.println("You do not have the permission to change price for the listing you chose");
            }

            ResultSet resultSet = st.executeQuery("SELECT * FROM lists WHERE lid= '"+lid+"' AND status = 1 ");
            if (resultSet.next() == false){
                System.out.println("Such listing does not exist");
                return;
            }

            st.executeUpdate("INSERT INTO changeprice " +
                    "VALUES ('"+lid+"','"+start_date+"', '"+end_date+"', '"+price+"')");

            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

    }
}
