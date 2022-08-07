package QueriesToSupport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class findListsByLatitude {
    public static void main(String[] args) {
        System.out.println("-----------------------------------------------------");
        System.out.println("Welcome to AirBNB!\nThis function is for giving a specific latitude and longitude, return all listings with a specific distance.");
        System.out.println("-----------------------------------------------------\n");

        try {
            Scanner scInt = new Scanner(System.in);
            System.out.println("LATITUDE should be a four digit number from -9999 to 9999 ");
            System.out.println("Please enter the LATITUDE: ");
            int latitude = scInt.nextInt();

            if (latitude < -9999 || latitude > 9999) {
                System.out.println("ERROR: LATITUDE should be a four digit number from -9999 to 9999 ");
                return;
            }

            System.out.println("LONGITUDE should be a four digit number from -9999 to 9999 ");
            System.out.println("Please enter the LONGITUDE: ");
            int longitude = scInt.nextInt();

            if (longitude < -9999 || longitude > 9999) {
                System.out.println("ERROR: LONGITUDE should be a four digit number from -9999 to 9999 ");
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

            System.out.println("Do you want to rank the lists by PRICE or DISTANCE? [P/D]");
            Scanner inputRank = new Scanner(System.in);
            String rank = inputRank.nextLine().toUpperCase();

            if (!(rank.equals("P") || rank.equals("D"))) {
                System.out.println("ERROR: Enter WRONG char");
                return;
            }

            Scanner inputSortBY = new Scanner(System.in);
            System.out.println("Do you want to rank the lists by ascending or descending? [A/D]");
            String SortBY = inputSortBY.nextLine().toUpperCase();

            if (!(SortBY.equals("A") || SortBY.equals("D"))) {
                System.out.println("ERROR: Enter WRONG char");
                return;
            }

            String url = "jdbc:mysql://localhost:3306/C43Project";
            Connection conn = DriverManager.getConnection(url, "root", "");
            Statement st = conn.createStatement();

            if (rank.equals("D") && SortBY.equals("A")) {
                ResultSet resultSet = st.executeQuery("SELECT *, SQRT( POW((L.longitude - '" + longitude
                        + "'), 2) + POW((L.latitude - '" + latitude + "'), 2)) AS distance FROM lists AS L WHERE L.status = 1 HAVING distance < '"
                        + distance + "' Order by distance ASC");
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
            } else if (rank.equals("D") && SortBY.equals("D")) {
                ResultSet resultSet = st.executeQuery("SELECT *, SQRT( POW((L.longitude - '" + longitude
                        + "'), 2) + POW((L.latitude - '" + latitude + "'), 2)) AS distance FROM lists AS L WHERE L.status = 1 HAVING distance < '"
                        + distance + "' Order by distance DESC");
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
            } else if (rank.equals("P") && SortBY.equals("A")) {
                ResultSet resultSet = st.executeQuery(
                        "SELECT * FROM((SELECT L.lid, L.house_type, L.latitude, L.longitude, L.roomid, L.address, L.postal_code, " +
                                "L.city, L.country, L.bathroom_hair_dryer, L.bathroom_cleaning_products, L.bedroom_essentials, " +
                                "L.bedroom_hangers, L.kitchen_dishes, L.kitchen_fridge, L.created_at, L.status, " +
                                "SQRT( POW((L.longitude - '" + longitude + "'), 2) + POW((L.latitude - '" + latitude + "'), 2)) AS distance, C1.price AS NEW " +
                                "FROM lists AS L JOIN changeprice AS C1 ON L.lid = C1.lid " +
                                "WHERE L.status = 1 AND C1.start_date <= 20220101 AND C1.end_date >= 20220102 AND EXISTS" +
                                " (SELECT * FROM changeprice AS C WHERE C.start_date <= 20220101 && C.end_date >= 20220102 AND C.lid =L.lid)" +
                                "HAVING distance < '" + distance + "') UNION " +
                                "(SELECT L.lid, L.house_type, L.latitude, L.longitude, L.roomid, L.address, L.postal_code, " +
                                "L.city, L.country, L.bathroom_hair_dryer, L.bathroom_cleaning_products, L.bedroom_essentials, " +
                                "L.bedroom_hangers, L.kitchen_dishes, L.kitchen_fridge, L.created_at, L.status, " +
                                "SQRT( POW((L.longitude - '" + longitude + "'), 2) + POW((L.latitude - '" + latitude + "'), 2)) AS distance, L.default_price AS NEW " +
                                "FROM lists AS L " +
                                "WHERE L.status = 1 AND NOT EXISTS " +
                                " (SELECT * FROM changeprice AS C WHERE C.start_date <= 20220101 && C.end_date >= 20220102 AND C.lid = L.lid)" +
                                "HAVING distance < '" + distance + "')) a ORDER BY NEW ASC");

                System.out.println("-----------------------------------------------------");
                System.out.println("lid \thouse_type \tdistance \tprice \tlatitude \tlongitude \troomid \taddress \tpostal_code \tcity \t\tcountry " +
                        "\tbathroom_hair_dryer \tbathroom_cleaning_products \tbedroom_essentials \tbedroom_hangers " +
                        "\tkitchen_dishes \tkitchen_fridge \tdefault_price \tcreated_at \tstatus");
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt(1) + " \t\t" + resultSet.getString(2) + " \t\t"
                            + resultSet.getInt(18) + " \t\t\t" + resultSet.getInt(19) + " \t"
                            + resultSet.getInt(3) + " \t\t\t"
                            + resultSet.getInt(4) + " \t\t\t" + resultSet.getInt(5) + " \t\t"
                            + resultSet.getString(6) + " \t\t" + resultSet.getInt(7) + " \t\t\t"
                            + resultSet.getString(8) + " \t" + resultSet.getString(9) + " \t\t"
                            + resultSet.getBoolean(10) + " \t\t\t\t\t" + resultSet.getBoolean(11) + " \t\t\t\t\t\t"
                            + resultSet.getBoolean(12) + " \t\t\t\t" + resultSet.getBoolean(13) + " \t\t\t\t"
                            + resultSet.getBoolean(14) + " \t\t\t" + resultSet.getBoolean(15) + " \t\t\t"
                            + resultSet.getInt(16) + " \t\t" + resultSet.getInt(17) + " \t\t\t"
                            + resultSet.getInt(18)
                    );
                }
                System.out.println("-----------------------------------------------------");
            } else if (rank.equals("P") && SortBY.equals("D")) {
                ResultSet resultSet = st.executeQuery(
                        "SELECT * FROM((SELECT L.lid, L.house_type, L.latitude, L.longitude, L.roomid, L.address, L.postal_code, " +
                                "L.city, L.country, L.bathroom_hair_dryer, L.bathroom_cleaning_products, L.bedroom_essentials, " +
                                "L.bedroom_hangers, L.kitchen_dishes, L.kitchen_fridge, L.created_at, L.status, " +
                                "SQRT( POW((L.longitude - '" + longitude + "'), 2) + POW((L.latitude - '" + latitude + "'), 2)) AS distance, C1.price AS NEW " +
                                "FROM lists AS L JOIN changeprice AS C1 ON L.lid = C1.lid " +
                                "WHERE L.status = 1 AND C1.start_date <= 20220101 AND C1.end_date >= 20220102 AND EXISTS" +
                                " (SELECT * FROM changeprice AS C WHERE C.start_date <= 20220101 && C.end_date >= 20220102 AND C.lid =L.lid)" +
                                "HAVING distance < '" + distance + "') UNION " +
                                "(SELECT L.lid, L.house_type, L.latitude, L.longitude, L.roomid, L.address, L.postal_code, " +
                                "L.city, L.country, L.bathroom_hair_dryer, L.bathroom_cleaning_products, L.bedroom_essentials, " +
                                "L.bedroom_hangers, L.kitchen_dishes, L.kitchen_fridge, L.created_at, L.status, " +
                                "SQRT( POW((L.longitude - '" + longitude + "'), 2) + POW((L.latitude - '" + latitude + "'), 2)) AS distance, L.default_price AS NEW " +
                                "FROM lists AS L " +
                                "WHERE L.status = 1 AND NOT EXISTS " +
                                " (SELECT * FROM changeprice AS C WHERE C.start_date <= 20220101 && C.end_date >= 20220102 AND C.lid = L.lid)" +
                                "HAVING distance < '" + distance + "')) a ORDER BY NEW DESC");

                System.out.println("-----------------------------------------------------");
                System.out.println("lid \thouse_type \tdistance \tprice \tlatitude \tlongitude \troomid \taddress \tpostal_code \tcity \t\tcountry " +
                        "\tbathroom_hair_dryer \tbathroom_cleaning_products \tbedroom_essentials \tbedroom_hangers " +
                        "\tkitchen_dishes \tkitchen_fridge \tdefault_price \tcreated_at \tstatus");
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt(1) + " \t\t" + resultSet.getString(2) + " \t\t"
                            + resultSet.getInt(18) + " \t\t\t" + resultSet.getInt(19) + " \t"
                            + resultSet.getInt(3) + " \t\t\t"
                            + resultSet.getInt(4) + " \t\t\t" + resultSet.getInt(5) + " \t\t"
                            + resultSet.getString(6) + " \t\t" + resultSet.getInt(7) + " \t\t\t"
                            + resultSet.getString(8) + " \t" + resultSet.getString(9) + " \t\t"
                            + resultSet.getBoolean(10) + " \t\t\t\t\t" + resultSet.getBoolean(11) + " \t\t\t\t\t\t"
                            + resultSet.getBoolean(12) + " \t\t\t\t" + resultSet.getBoolean(13) + " \t\t\t\t"
                            + resultSet.getBoolean(14) + " \t\t\t" + resultSet.getBoolean(15) + " \t\t\t"
                            + resultSet.getInt(16) + " \t\t" + resultSet.getInt(17) + " \t\t\t"
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
