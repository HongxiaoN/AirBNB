package QueriesToSupport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class advFindListByPostal {

    public static void main(String[] args) {
        System.out.println("-----------------------------------------------------");
        System.out.println("Welcome to AirBNB!\nThis ADVANCE function is for giving a specific Postal Code, and some filters return all listings with a specific distance.");
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

            int distance = 20;
            Scanner inputString = new Scanner(System.in);
            System.out.println("The DEFAULT distance is 20km.");
            System.out.println("Do you want to enter a specific distance around this location?[Y/N] The DEFAULT distance is 20km.");
            String specificDistance = inputString.nextLine().toUpperCase();

            if (!(specificDistance.equals("Y") || specificDistance.equals("N"))) {
                System.out.println("ERROR: Enter WRONG char");
                return;
            }

            if (specificDistance.equals("Y")) {
                Scanner inputDistance = new Scanner(System.in);
                System.out.println("Since you want to specific the distance, Please enter here: ");
                distance = inputDistance.nextInt();
                System.out.println(distance);
            }

            Scanner inputSortBY = new Scanner(System.in);
            System.out.println("Do you want to rank the lists by ascending or descending? [A/D]");
            String SortBY = inputSortBY.nextLine().toUpperCase();

            if (!(SortBY.equals("A") || SortBY.equals("D"))) {
                System.out.println("ERROR: Enter WRONG char");
                return;
            }

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

            if (SortBY.equals("A")) {
                ResultSet resultSet = st.executeQuery("SELECT L.*, Abs(postal_code - '" + postal + "') AS distance FROM lists AS L WHERE L.status = 1 " +
                        "AND NOT EXISTS ( SELECT * FROM reservations AS R WHERE L.lid = R.lid AND R.status = 1 AND (('" + start_date +
                        "' <= start_date AND '" + end_date+ "' > start_date) OR ('" + start_date + "' < end_date AND '" + end_date + "' >= end_date) OR ('" + start_date +
                        "' >= start_date AND '" + end_date + "' <= end_date)))" +
                        "HAVING distance < '" + distance + "' Order by distance ASC");
                System.out.println("-----------------------------------------------------");
                System.out.println("lid \thouse_type \tdistance \tlatitude \tlongitude \troomid \taddress \tpostal_code \tcity \t\tcountry " +
                        "\tbathroom_hair_dryer \tbathroom_cleaning_products \tbedroom_essentials \tbedroom_hangers " +
                        "\tkitchen_dishes \tkitchen_fridge \tdefault_price \tcreated_at \tstatus");
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt(1) + " \t\t" + resultSet.getString(2) + " \t\t"
                            + resultSet.getInt(19) + " \t\t\t" + resultSet.getInt(3) + " \t\t\t"
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
                System.out.println("-----------------------------------------------------\n");
            } else if (SortBY.equals("D")) {
                ResultSet resultSet = st.executeQuery("SELECT L.*, Abs(postal_code - '" + postal + "') AS distance FROM lists AS L WHERE L.status = 1 " +
                        "AND NOT EXISTS ( SELECT * FROM reservations AS R WHERE L.lid = R.lid AND R.status = 1 AND (('" + start_date +
                        "' <= start_date AND '" + end_date+ "' > start_date) OR ('" + start_date + "' < end_date AND '" + end_date + "' >= end_date) OR ('" + start_date +
                        "' >= start_date AND '" + end_date + "' <= end_date)))" +
                        "HAVING distance < '" + distance + "' Order by distance DESC");
                System.out.println("-----------------------------------------------------");
                System.out.println("lid \thouse_type \tdistance \tlatitude \tlongitude \troomid \taddress \tpostal_code \tcity \t\tcountry " +
                        "\tbathroom_hair_dryer \tbathroom_cleaning_products \tbedroom_essentials \tbedroom_hangers " +
                        "\tkitchen_dishes \tkitchen_fridge \tdefault_price \tcreated_at \tstatus");
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt(1) + " \t\t" + resultSet.getString(2) + " \t\t"
                            + resultSet.getInt(19) + " \t\t\t" + resultSet.getInt(3) + " \t\t\t"
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
            }

            System.out.println("finish");
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}
