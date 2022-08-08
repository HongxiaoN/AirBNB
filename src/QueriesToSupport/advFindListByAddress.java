package QueriesToSupport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class advFindListByAddress {
    public static void main(String[] args) {
        System.out.println("-----------------------------------------------------");
        System.out.println("Welcome to AirBNB!\nThis ADVANCE function is for giving a specific ADDRESS, return all listings with a specific distance.");
        System.out.println("-----------------------------------------------------\n");

        try {
            Scanner scInt = new Scanner(System.in);
            System.out.println("Postal Code should be a four digit number from 0 - 9999 ");
            System.out.println("Please enter the Postal Code: ");
            int postal = scInt.nextInt();

            if (postal < 0 || postal > 9999) {
                System.out.println("ERROR: Postal Code should be a four digit number from 0 to 9999 ");
                return;
            }

            System.out.println("What is your city?");
            Scanner inputCity = new Scanner(System.in);
            String city = inputCity.nextLine().toLowerCase();

            System.out.println("What is your country?");
            Scanner inputCountry = new Scanner(System.in);
            String country = inputCountry.nextLine().toLowerCase();

            // Specific a time period
            Scanner inputTime = new Scanner(System.in);
            System.out.println("Please enter the start date, It should in the range of 20220101 - 20230101");
            int start_date = inputTime.nextInt();

            if (start_date < 20220101) {
                System.out.println("Cannot make reservation that has already passed!");
                return;
            }
            System.out.println("Please enter the end date,  It should in the range of 20220101 - 20230101");
            int end_date = inputTime.nextInt();
            if (end_date < 20220101 || start_date >= end_date || end_date > 20230101) {
                System.out.println("Such end date is invalid!");
                return;
            }

            String url = "jdbc:mysql://localhost:3306/C43Project";
            Connection conn = DriverManager.getConnection(url, "root", "");
            Statement st = conn.createStatement();

            ResultSet resultSet = st.executeQuery("SELECT * FROM lists AS L WHERE L.status = 1 AND " +
                    "postal_code = '" + postal + "' AND city = '" + city + "' AND country = '" + country +
                    "'AND NOT EXISTS ( SELECT * FROM reservations AS R WHERE L.lid = R.lid AND R.status = 1 AND (('" + start_date +
                    "' <= start_date AND '" + end_date+ "' > start_date) OR ('" + start_date + "' < end_date AND '" + end_date + "' >= end_date) OR ('" + start_date +
                    "' >= start_date AND '" + end_date + "' <= end_date)))");

            System.out.println("-----------------------------------------------------");
            System.out.println("lid \thouse_type \tlatitude \tlongitude \troomid \taddress \tpostal_code \tcity \t\tcountry " +
                    "\tbathroom_hair_dryer \tbathroom_cleaning_products \tbedroom_essentials \tbedroom_hangers " +
                    "\tkitchen_dishes \tkitchen_fridge \tdefault_price \tcreated_at \tstatus");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " \t\t" + resultSet.getString(2) + " \t\t"
                        + resultSet.getInt(3) + " \t\t\t"
                        + resultSet.getInt(4) + " \t\t\t" + resultSet.getInt(5) + " \t\t"
                        + resultSet.getString(6) + " \t\t" + resultSet.getInt(7) + " \t\t\t"
                        + resultSet.getString(8) + " \t" + resultSet.getString(9) + " \t\t"
                        + resultSet.getBoolean(10) + " \t\t\t\t\t" + resultSet.getBoolean(11) + " \t\t\t\t\t\t"
                        + resultSet.getBoolean(12) + " \t\t\t\t" + resultSet.getBoolean(13) + " \t\t\t\t"
                        + resultSet.getBoolean(14) + " \t\t\t" + resultSet.getBoolean(15) + " \t\t\t"
                        + resultSet.getInt(16) + " \t\t\t" + resultSet.getInt(17) + " \t"
                        + resultSet.getInt(18)
                );
            }
            System.out.println("-----------------------------------------------------");
            System.out.println("finish");
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}
