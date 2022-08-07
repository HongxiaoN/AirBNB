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

        System.out.println("Please enter the SIN number of the host: ");
        Scanner scInt = new Scanner(System.in);
        int hostid1 = scInt.nextInt();
        System.out.println("Please enter your SIN number: ");
        int renterid1 = scInt.nextInt();
        System.out.println("Please enter the listing ID that you would like to proceed: ");
        int lid1 = scInt.nextInt();
        System.out.println("Please enter the start date: ");
        int start_date1 = scInt.nextInt();

        if (start_date1 < 20220101){
            System.out.println("Cannot make reservation that has already passed!");
            return;
        }

        System.out.println("Please enter the end date: ");
        int end_date1 = scInt.nextInt();
        if (end_date1 < 20220101 || start_date1 >= end_date1){
            System.out.println("Such end date is invalid!");
            return;
        }

        try {
            String url = "jdbc:mysql://localhost:3306/C43Project";
            Connection conn = DriverManager.getConnection(url,"root","");
            Statement st = conn.createStatement();


            ResultSet isHost = st.executeQuery("SELECT * FROM owns WHERE lid= '"+lid1+"' AND uid= '"+hostid1+"'");
            if (isHost.next() == false){
                System.out.println("The host listing combination you entered is invalid!");
                return;
            }

            ResultSet isUser = st.executeQuery("SELECT * FROM users WHERE sin= '"+renterid1+"' AND status=1");
            if (isUser.next() == false){
                System.out.println("You need to sign up for an account in order to proceed");
                return;
            }

            ResultSet isUser1 = st.executeQuery("SELECT * FROM users WHERE sin= '"+hostid1+"' AND status=1");
            if (isUser.next() == false){
                System.out.println("Such host does not exist");
                return;
            }

            ResultSet isConflict = st.executeQuery("SELECT * FROM reservations WHERE renterid= '"+renterid1+"' AND (('"+start_date1+"' < end_date AND '"+start_date1+"' >= start_date) OR ('"+end_date1+"' <= end_date AND '"+end_date1+"' > start_date)) AND status = 1");
            if (isConflict.next() == true){
                System.out.println("There is a conflict with your current reservation!");
                return;
            }

            ResultSet isCancelled = st.executeQuery("SELECT * FROM reservations WHERE hostid= '"+hostid1+"' AND renterid= '"+renterid1+"' AND lid= '"+lid1+"' AND (('"+start_date1+"' < end_date AND '"+start_date1+"' >= start_date) OR ('"+end_date1+"' <= end_date AND '"+end_date1+"' > start_date)) AND status=0 ");
            if (isCancelled.next() == true){

            }

            st.executeUpdate("INSERT INTO reservations " +
                    "VALUES ('"+hostid1+"','"+renterid1+"', '"+lid1+"', '"+start_date1+"','"+end_date1+"', 0, 3, NULL, 3, NULL)");

            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }


    }
}
