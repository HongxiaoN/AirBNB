package QueriesToSupport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class findListsBaseAddress {
    public static void main(String[] args) {
        System.out.println("-----------------------------------------------------");
        System.out.println("Welcome to AirBNB!\nThis function is for giving a specific ADDRESS, return all listings with a specific distance.");
        System.out.println("-----------------------------------------------------\n");

        try {
            Scanner scInt = new Scanner(System.in);
            System.out.println("Postal Code should be a four digit number from 0 - 9999 ");
            System.out.println("Please enter the Postal Code: ");
            int postal = scInt.nextInt();

            if (postal < 0 || postal > 9999) {
                System.out.println("ERROR: Postal Code should be a four digit number from -9999 to 9999 ");
                return;
            }

            System.out.println("What is your city?");
            Scanner inputCity = new Scanner(System.in);
            String city = inputCity.nextLine().toLowerCase();

            System.out.println("What is your country?");
            Scanner inputCountry = new Scanner(System.in);
            String country = inputCountry.nextLine().toLowerCase();

            String url = "jdbc:mysql://localhost:3306/C43Project";
            Connection conn = DriverManager.getConnection(url, "root", "");
            Statement st = conn.createStatement();

            ResultSet resultSet = st.executeQuery("SELECT * FROM lists AS L WHERE L.status = 1 AND " +
                    "postal_code = '" + postal + "' AND city = '" + city + "' AND country = '" + country + "'");

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
