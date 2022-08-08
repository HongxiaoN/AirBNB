package HostToolkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class advInsertList {
    public static void main(String[] args) {
        System.out.println("-----------------------------------------------------");
        System.out.println("Welcome to AirBNB.");
        System.out.println("This ADVANCE function is for insert a new listing for a host(user) AND suggest the price and amenities." +
                "with checking input validation");
        System.out.println("-----------------------------------------------------\n");

        System.out.println("You will ADVANCE create a new listing");
        System.out.println("At first, what is your sin? It should be 9 digit number!");
        Scanner input_sin = new Scanner(System.in);    //System.in is a standard input stream
        int sin = input_sin.nextInt();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/C43Project", "root", ""
            );
            Statement statement = connection.createStatement();

            System.out.println("-----------------------------------------------------\n");
            // Check sin is in users
            ResultSet checkSIN = statement.executeQuery("SELECT sin FROM users WHERE sin='" + sin + "' AND status=1");
            if (!checkSIN.next()) {
                System.out.println("ERROR: the sin(user): " + sin + ", you enter are not exist! please check you sin is correct");
                return;
            }

            // Get Listing type
            Scanner inputType = new Scanner(System.in);
            System.out.println("This user is exist");
            System.out.println("The listing type should be 'house', 'apartment' or 'room'.");
            System.out.println("What is listing type:");
            String type = inputType.nextLine().toLowerCase();              //reads string
            if (!(type.equals("house") || type.equals("apartment") || type.equals("room"))) {
                System.out.println("ERROR: The type u insert is not in correct format.");
                return;
            }

            System.out.println("Latitude should be a 4 digit int. SHOULD in  -9999 to 9999. What is latitude?");
            Scanner inputLatitude = new Scanner(System.in);
            int latitude = inputLatitude.nextInt();
            if (latitude < -9999 || latitude > 9999) {
                System.out.println("ERROR: Latitude should be a 4 digit int. SHOULD in -9999 to 9999. You enter wrong.");
                return;
            }

            System.out.println("longitude should be a 4 digit int. SHOULD in -9999 to 9999. What is longitude?");
            Scanner inputLongitude = new Scanner(System.in);
            int longitude = inputLongitude.nextInt();
            if (longitude < -9999 || longitude > 9999) {
                System.out.println("ERROR1: longitude should be a 4 digit int. SHOULD in -9999 to 9999. You enter wrong.");
                return;
            }

            System.out.println("An address for apartment MUST include apartment number. EX: 1234-30 Town Centre Crt.");
            System.out.println("SO What is Address?");
            Scanner inputAddress = new Scanner(System.in);
            String address = inputAddress.nextLine().toLowerCase();

            // Default room id is 0
            int roomid = 0;

            // Check type is room, if yes, enter roomid.
            if (type.equals("r") || type.equals("room")) {
                System.out.println("The type u input is a room, so please enter room id, it should be from 1 - 99. CANNOT be 0");
                System.out.println("SO What is roomid?");

                // Change room id to correct
                Scanner inputroomid = new Scanner(System.in);
                roomid = inputroomid.nextInt();
                if (roomid <= 0 || roomid > 99) {
                    roomid = 0;
                    System.out.println("The room id u enter is wrong. please check");
                    return;
                }
            }

            System.out.println("What is your post code? POST CODE is a four digit number");
            Scanner inputPOSTCODE = new Scanner(System.in);
            int postcode = inputPOSTCODE.nextInt();

            System.out.println("What is your city?");
            Scanner inputCity = new Scanner(System.in);
            String city = inputCity.nextLine().toLowerCase();

            System.out.println("What is your country?");
            Scanner inputCountry = new Scanner(System.in);
            String country = inputCountry.nextLine().toLowerCase();

            // Check is this location are been used, USE unique combination: ADDRESS + ROOMID + city + country
            ResultSet checkAddress = statement.executeQuery("SELECT * FROM lists WHERE address='"
                    + address + "' AND roomid='" + roomid + "' AND city ='"
                    + city + "' AND country='" + country + "' AND status=1");
            if (checkAddress.next()) {
                System.out.println("ERROR: the house address you enter is exist! please check you address and roomid is correct");
                return;
            }

            //If this list are owned by this user, but he/she deleted it. We will change exist from 0 to 1.
            ResultSet checkHouseAvailable = statement.executeQuery("SELECT * FROM lists NATURAL JOIN owns WHERE address='"
                    + address + "' AND roomid='" + roomid + "' AND city ='"
                    + city + "' AND country='" + country + "' AND status=0 AND uid='" + sin + "'");

            if (checkHouseAvailable.next()) {
                int updateAvailable = statement.executeUpdate("UPDATE lists SET status=1 WHERE address='"
                        + address + "' AND roomid='" + roomid + "' AND city ='"
                        + city + "' AND country='" + country + "' AND status=0");
                if (updateAvailable == 1) {
                    System.out.println("The system detect you own this list before, Already automatically re-add this list for u :).");
                    System.out.println("If you want to update other info, please go to other update function to do that.");
                } else {
                    System.out.println("Failed to update this list");
                }
                return;
            }

            // At here, mean this is a new list not in the table before.
            System.out.println("For those amenities, if you list have it, enter '1'. Otherwise, enter '0'.");
            System.out.println("Does this list's bathroom have hair dryer?  [0/1]");
            System.out.println("I suggest you should have it, it may increase you price.");
            Scanner input_bathroom_hair_dryer = new Scanner(System.in);
            int bathroom_hair_dryer = input_bathroom_hair_dryer.nextInt();
            if (bathroom_hair_dryer != 0 && bathroom_hair_dryer != 1) {
                System.out.println("You enter wrong number for confirmation.");
                return;
            }

            System.out.println("Does this list's bathroom have bathroom_cleaning_products?  [0/1] ");
            System.out.println("I suggest you should have it, it may increase you price.");
            Scanner input_bathroom_cleaning_products = new Scanner(System.in);
            int bathroom_cleaning_products = input_bathroom_cleaning_products.nextInt();
            if (bathroom_cleaning_products != 0 && bathroom_cleaning_products != 1) {
                System.out.println("You enter wrong number for confirmation.");
                return;
            }

            System.out.println("Does this list's bedroom have bedroom_essentials?  [0/1]");
            System.out.println("I suggest you should have it, it may increase you price.");
            Scanner input_bedroom_essentials = new Scanner(System.in);
            int bedroom_essentials = input_bedroom_essentials.nextInt();
            if (bedroom_essentials != 0 && bedroom_essentials != 1) {
                System.out.println("You enter wrong number for confirmation.");
                return;
            }

            System.out.println("Does this list's bedroom have bedroom_hangers?  [0/1]");
            System.out.println("I suggest you should have it, it may increase you price.");
            Scanner input_bedroom_hangers = new Scanner(System.in);
            int bedroom_hangers = input_bedroom_hangers.nextInt();
            if (bedroom_hangers != 0 && bedroom_hangers != 1) {
                System.out.println("You enter wrong number for confirmation.");
                return;
            }

            System.out.println("Does this list's kitchen have kitchen_dishes?  [0/1]");
            System.out.println("I suggest you should have it, it may increase you price.");
            Scanner input_kitchen_dishes = new Scanner(System.in);
            int kitchen_dishes = input_kitchen_dishes.nextInt();
            if (kitchen_dishes != 0 && kitchen_dishes != 1) {
                System.out.println("You enter wrong number for confirmation.");
                return;
            }

            System.out.println("Does this list's kitchen have kitchen_fridge?  [0/1]");
            System.out.println("I suggest you should have it, it may increase you price.");
            Scanner input_kitchen_fridge = new Scanner(System.in);
            int kitchen_fridge = input_kitchen_fridge.nextInt();
            if (kitchen_fridge != 0 && kitchen_fridge != 1) {
                System.out.println("You enter wrong number for confirmation.");
                return;
            }

            // GET a price suggestion, for check 100km from this location, what average price for this type of list, And add $20 for get each more amenities
            ResultSet resultSet = statement.executeQuery(
                    "SELECT NEW, house_type FROM((SELECT L.lid, L.house_type, L.latitude, L.longitude, L.roomid, L.address, L.postal_code, " +
                            "L.city, L.country, L.bathroom_hair_dryer, L.bathroom_cleaning_products, L.bedroom_essentials, " +
                            "L.bedroom_hangers, L.kitchen_dishes, L.kitchen_fridge, L.created_at, L.status, " +
                            "SQRT( POW((L.longitude - '" + longitude + "'), 2) + POW((L.latitude - '" + latitude + "'), 2)) AS distance, C1.price AS NEW " +
                            "FROM lists AS L JOIN changeprice AS C1 ON L.lid = C1.lid " +
                            "WHERE L.status = 1 AND C1.start_date <= 20220101 AND C1.end_date >= 20220101 AND EXISTS" +
                            " (SELECT * FROM changeprice AS C WHERE C.start_date <= 20220101 && C.end_date >= 20220101 AND C.lid =L.lid) " +
                            "HAVING distance < 100) UNION " +
                            "(SELECT L.lid, L.house_type, L.latitude, L.longitude, L.roomid, L.address, L.postal_code, " +
                            "L.city, L.country, L.bathroom_hair_dryer, L.bathroom_cleaning_products, L.bedroom_essentials, " +
                            "L.bedroom_hangers, L.kitchen_dishes, L.kitchen_fridge, L.created_at, L.status, " +
                            "SQRT( POW((L.longitude - '" + longitude + "'), 2) + POW((L.latitude - '" + latitude + "'), 2)) AS distance, L.default_price AS NEW " +
                            "FROM lists AS L " +
                            "WHERE L.status = 1 AND NOT EXISTS " +
                            " (SELECT * FROM changeprice AS C WHERE C.start_date <=  20220101 && C.end_date >= 20220101 AND C.lid = L.lid) " +
                            "HAVING distance < 100)) a WHERE house_type = '" + type + "'");

            double row = 0;
            double total = 0;
            while (resultSet.next()) {
                total += resultSet.getInt(1);
                row += 1;
            }
            double avgPrice = total / row;
            System.out.println(avgPrice);

            avgPrice = avgPrice + 20 * (bathroom_hair_dryer + bathroom_cleaning_products + bedroom_essentials +
                                        bedroom_hangers + kitchen_dishes + kitchen_fridge);

            System.out.println(avgPrice);

            System.out.println("\nThe system find the average price for your list area is " + avgPrice);
            System.out.println("What is the default price for this list?");
            Scanner input_default_price = new Scanner(System.in);
            int default_price = input_default_price.nextInt();

            System.out.println("What is the list id? You can create a 5 digit number from 0 - 99999");
            Scanner input_listID = new Scanner(System.in);
            int listID = input_listID.nextInt();

            //INSERT a new list for this user.
            int newList = statement.executeUpdate("INSERT INTO lists VALUES ('"
                    + listID + "', '" + type + "', '" + latitude + "', '" + longitude + "', '" + roomid
                    + "', '" + address + "', '" + postcode + "', '" + city + "', '" + country
                    + "', '" + bathroom_hair_dryer + "', '" + bathroom_cleaning_products
                    + "', '" + bedroom_essentials + "', '" + bedroom_hangers + "', '" + kitchen_dishes
                    + "', '" + kitchen_fridge + "', '" + default_price + "', '20220101', '1')");

            if (newList == 1) {
                System.out.println("success add list in the list table, NOW add relation to user and list.");
                int newOwns = statement.executeUpdate("INSERT INTO owns VALUES ('" + sin + "', '" + listID + "')");
                if (newOwns == 1) {
                    System.out.println("success add relation in the owns table.");
                } else {
                    System.out.println("Unable to create a relation between this user and list.");
                }

            } else {
                System.out.println("This lid is already used, please choose again.");
            }
            System.out.println("finish");
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
