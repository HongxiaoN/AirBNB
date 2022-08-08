package QueriesToSupport;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class advFindFullyByPostal {
    public static void main(String[] args) {
        System.out.println("-----------------------------------------------------");
        System.out.println("Welcome to AirBNB!\nThis ADVANCE FULLY Filter function is for giving a specific Postal Code, and some filters return all listings with a specific distance.");
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

            System.out.println("For those amenities, if you want to have it, enter '1'. Otherwise, enter '0'.");
            System.out.println("Do you want bathroom to have hair dryer?  [0/1]");
            Scanner input_bathroom_hair_dryer = new Scanner(System.in);
            int bathroom_hair_dryer = input_bathroom_hair_dryer.nextInt();
            if(bathroom_hair_dryer != 0 && bathroom_hair_dryer != 1){
                System.out.println("You enter wrong number for confirmation.");
                return;
            }

            System.out.println("Do you want bathroom to have bathroom_cleaning_products?  [0/1]");
            Scanner input_bathroom_cleaning_products = new Scanner(System.in);
            int bathroom_cleaning_products = input_bathroom_cleaning_products.nextInt();
            if(bathroom_cleaning_products != 0 && bathroom_cleaning_products != 1){
                System.out.println("You enter wrong number for confirmation.");
                return;
            }

            System.out.println("Do you want bedroom to have bedroom_essentials?  [0/1]");
            Scanner input_bedroom_essentials = new Scanner(System.in);
            int bedroom_essentials = input_bedroom_essentials.nextInt();
            if(bedroom_essentials != 0 && bedroom_essentials != 1){
                System.out.println("You enter wrong number for confirmation.");
                return;
            }

            System.out.println("Do you want bedroom to have bedroom_hangers?  [0/1]");
            Scanner input_bedroom_hangers = new Scanner(System.in);
            int bedroom_hangers = input_bedroom_hangers.nextInt();
            if(bedroom_hangers != 0 && bedroom_hangers != 1){
                System.out.println("You enter wrong number for confirmation.");
                return;
            }

            System.out.println("Do you want kitchen to have kitchen_dishes?  [0/1]");
            Scanner input_kitchen_dishes = new Scanner(System.in);
            int kitchen_dishes = input_kitchen_dishes.nextInt();
            if(kitchen_dishes != 0 && kitchen_dishes != 1){
                System.out.println("You enter wrong number for confirmation.");
                return;
            }

            System.out.println("Do you want kitchen to have kitchen_fridge?  [0/1]");
            Scanner input_kitchen_fridge = new Scanner(System.in);
            int kitchen_fridge = input_kitchen_fridge.nextInt();
            if(kitchen_fridge != 0 && kitchen_fridge != 1){
                System.out.println("You enter wrong number for confirmation.");
                return;
            }

            System.out.println("What page range are you looking? The price should in 0 - 9999");
            System.out.println("What is lower bound for price range");
            Scanner input_start_price = new Scanner(System.in);
            int start_price = input_start_price.nextInt();
            if(start_price < 0 || start_price > 9999){
                System.out.println("You enter wrong number for lower bound.");
                return;
            }

            System.out.println("What is upper bound for price range");
            Scanner input_end_price = new Scanner(System.in);
            int end_price = input_end_price.nextInt();
            if(end_price < 0 || end_price > 9999 || end_price < start_price){
                System.out.println("You enter wrong number for upper bound.");
                return;
            }

            String url = "jdbc:mysql://localhost:3306/C43Project";
            Connection conn = DriverManager.getConnection(url, "root", "");
            Statement st = conn.createStatement();

            if (SortBY.equals("A")) {
//                ResultSet resultSet = st.executeQuery("SELECT L.*, Abs(postal_code - '" + postal + "') AS distance FROM lists AS L WHERE L.status = 1 " +
//                        "AND NOT EXISTS ( SELECT * FROM reservations AS R WHERE L.lid = R.lid AND R.status = 1 AND (('" + start_date +
//                        "' <= start_date AND '" + end_date + "' > start_date) OR ('" + start_date + "' < end_date AND '" + end_date + "' >= end_date) OR ('" + start_date +
//                        "' >= start_date AND '" + end_date + "' <= end_date)))" +
//                        "HAVING distance < '" + distance + "' Order by distance ASC");

                ResultSet resultSet = st.executeQuery(
                        "SELECT * FROM((SELECT L.lid, L.house_type, L.latitude, L.longitude, L.roomid, L.address, L.postal_code, " +
                                "L.city, L.country, L.bathroom_hair_dryer, L.bathroom_cleaning_products, L.bedroom_essentials, " +
                                "L.bedroom_hangers, L.kitchen_dishes, L.kitchen_fridge, L.created_at, L.status, " +
                                "Abs(postal_code - '" + postal + "') AS distance, C1.price AS NEW " +
                                "FROM lists AS L JOIN changeprice AS C1 ON L.lid = C1.lid " +
                                "WHERE L.status = 1 AND C1.start_date <= '" + start_date + "' AND C1.end_date >= '" + start_date + "' AND EXISTS" +
                                " (SELECT * FROM changeprice AS C WHERE C.start_date <= '" + start_date + "' && C.end_date >= '" + start_date + "' AND C.lid =L.lid) AND NOT EXISTS (SELECT * FROM reservations AS R WHERE L.lid = R.lid AND R.status = 1 " +
                                " AND (('" + start_date + "' <= start_date AND '" + end_date
                                + "' > start_date) OR ('" + start_date + "' < end_date AND '" + end_date + "' >= end_date) OR ('" + start_date
                                + "' >= start_date AND '" + end_date + "' <= end_date))) " +
                                "HAVING distance < '" + distance + "') UNION " +
                                "(SELECT L.lid, L.house_type, L.latitude, L.longitude, L.roomid, L.address, L.postal_code, " +
                                "L.city, L.country, L.bathroom_hair_dryer, L.bathroom_cleaning_products, L.bedroom_essentials, " +
                                "L.bedroom_hangers, L.kitchen_dishes, L.kitchen_fridge, L.created_at, L.status, " +
                                "Abs(postal_code - '" + postal + "') AS distance, L.default_price AS NEW " +
                                "FROM lists AS L " +
                                "WHERE L.status = 1 AND NOT EXISTS " +
                                " (SELECT * FROM changeprice AS C WHERE C.start_date <= '" + start_date + "' && C.end_date >= '" + start_date + "' AND C.lid = L.lid) AND NOT EXISTS (SELECT * FROM reservations AS R WHERE L.lid = R.lid AND R.status = 1 " +
                                " AND (('" + start_date + "' <= start_date AND '" + end_date
                                + "' > start_date) OR ('" + start_date + "' < end_date AND '" + end_date + "' >= end_date) OR ('" + start_date
                                + "' >= start_date AND '" + end_date + "' <= end_date))) "
                                +"HAVING distance < '" + distance + "')) a WHERE NEW > '" + start_price + "' AND NEW < '" + end_price
                                + "' AND bathroom_hair_dryer = '" + bathroom_hair_dryer + "' AND bathroom_cleaning_products = '" + bathroom_cleaning_products
                                + "' AND bedroom_essentials = '" + bedroom_essentials + "' AND bedroom_hangers = '" + bedroom_hangers
                                + "' AND kitchen_dishes = '" + kitchen_dishes + "' AND kitchen_fridge = '" + kitchen_fridge
                                + "' ORDER BY NEW ASC");

                System.out.println("-----------------------------------------------------");
                System.out.println("lid \thouse_type \tprice \tdistance \tlatitude \tlongitude \troomid \taddress \tpostal_code \tcity \t\tcountry " +
                        "\tbathroom_hair_dryer \tbathroom_cleaning_products \tbedroom_essentials \tbedroom_hangers " +
                        "\tkitchen_dishes \tkitchen_fridge \tdefault_price \tcreated_at \tstatus");
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt(1) + " \t\t" + resultSet.getString(2) + " \t\t"
                            + resultSet.getInt(19) + " \t" + resultSet.getInt(18) + " \t\t\t"
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
                System.out.println("-----------------------------------------------------\n");
            } else if (SortBY.equals("D")) {
                ResultSet resultSet = st.executeQuery(
                        "SELECT * FROM((SELECT L.lid, L.house_type, L.latitude, L.longitude, L.roomid, L.address, L.postal_code, " +
                                "L.city, L.country, L.bathroom_hair_dryer, L.bathroom_cleaning_products, L.bedroom_essentials, " +
                                "L.bedroom_hangers, L.kitchen_dishes, L.kitchen_fridge, L.created_at, L.status, " +
                                "Abs(postal_code - '" + postal + "') AS distance, C1.price AS NEW " +
                                "FROM lists AS L JOIN changeprice AS C1 ON L.lid = C1.lid " +
                                "WHERE L.status = 1 AND C1.start_date <= '" + start_date + "' AND C1.end_date >= '" + start_date + "' AND EXISTS" +
                                " (SELECT * FROM changeprice AS C WHERE C.start_date <= '" + start_date + "' && C.end_date >= '" + start_date + "' AND C.lid =L.lid) AND NOT EXISTS (SELECT * FROM reservations AS R WHERE L.lid = R.lid AND R.status = 1 " +
                                " AND (('" + start_date + "' <= start_date AND '" + end_date
                                + "' > start_date) OR ('" + start_date + "' < end_date AND '" + end_date + "' >= end_date) OR ('" + start_date
                                + "' >= start_date AND '" + end_date + "' <= end_date))) " +
                                "HAVING distance < '" + distance + "') UNION " +
                                "(SELECT L.lid, L.house_type, L.latitude, L.longitude, L.roomid, L.address, L.postal_code, " +
                                "L.city, L.country, L.bathroom_hair_dryer, L.bathroom_cleaning_products, L.bedroom_essentials, " +
                                "L.bedroom_hangers, L.kitchen_dishes, L.kitchen_fridge, L.created_at, L.status, " +
                                "Abs(postal_code - '" + postal + "') AS distance, L.default_price AS NEW " +
                                "FROM lists AS L " +
                                "WHERE L.status = 1 AND NOT EXISTS " +
                                " (SELECT * FROM changeprice AS C WHERE C.start_date <= '" + start_date + "' && C.end_date >= '" + start_date + "' AND C.lid = L.lid) AND NOT EXISTS (SELECT * FROM reservations AS R WHERE L.lid = R.lid AND R.status = 1 " +
                                " AND (('" + start_date + "' <= start_date AND '" + end_date
                                + "' > start_date) OR ('" + start_date + "' < end_date AND '" + end_date + "' >= end_date) OR ('" + start_date
                                + "' >= start_date AND '" + end_date + "' <= end_date))) "
                                +"HAVING distance < '" + distance + "')) a WHERE NEW > '" + start_price + "' AND NEW < '" + end_price
                                + "' AND bathroom_hair_dryer = '" + bathroom_hair_dryer + "' AND bathroom_cleaning_products = '" + bathroom_cleaning_products
                                + "' AND bedroom_essentials = '" + bedroom_essentials + "' AND bedroom_hangers = '" + bedroom_hangers
                                + "' AND kitchen_dishes = '" + kitchen_dishes + "' AND kitchen_fridge = '" + kitchen_fridge
                                + "' ORDER BY NEW DESC");

                System.out.println("-----------------------------------------------------");
                System.out.println("lid \thouse_type \tprice \tdistance \tlatitude \tlongitude \troomid \taddress \tpostal_code \tcity \t\tcountry " +
                        "\tbathroom_hair_dryer \tbathroom_cleaning_products \tbedroom_essentials \tbedroom_hangers " +
                        "\tkitchen_dishes \tkitchen_fridge \tdefault_price \tcreated_at \tstatus");
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt(1) + " \t\t" + resultSet.getString(2) + " \t\t"
                            + resultSet.getInt(19) + " \t" + resultSet.getInt(18) + " \t\t\t"
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
            }

            System.out.println("finish");
            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}
