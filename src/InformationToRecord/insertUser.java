package InformationToRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class insertUser {
    public static void main(String[] args) {

        System.out.println("-----------------------------------------------------");
        System.out.println("Welcome to AirBNB!\nThis function is for creating new user to the system.");
        System.out.println("-----------------------------------------------------\n");

        System.out.println("Please enter your SIN number: ");
        Scanner scInt = new Scanner(System.in);
        int sin = scInt.nextInt();
        System.out.println("Please enter your name: ");
        Scanner sc = new Scanner(System.in);    //System.in is a standard input stream
        String username = sc.nextLine().toLowerCase();
        System.out.println("Please enter your email: ");
        String email = sc.nextLine().toLowerCase();
        System.out.println("Please enter your default address: ");
        String address = sc.nextLine().toLowerCase();
        System.out.println("Please enter your date of birth in the form of YYMMDD such as Aug 8th 2022 -> 20220808: ");
        int birthday = scInt.nextInt();
        if (birthday > 20040101) {
            System.out.println("A user must be at least 18 years old to register!");
            return;
        }

        System.out.println("Please enter your occupation: ");
        String occupation = sc.nextLine().toLowerCase();
        System.out.println("Please enter your card number: ");
        int card = scInt.nextInt();

        try {
            String url = "jdbc:mysql://localhost:3306/C43Project";
            Connection conn = DriverManager.getConnection(url, "root", "");
            Statement st = conn.createStatement();

            ResultSet resultSet1 = st.executeQuery("SELECT * FROM users WHERE sin= '" + sin + "' AND status=0");
            if (resultSet1.next() == true) {
                int isupdate = st.executeUpdate("UPDATE users SET status=1 WHERE sin= '" + sin + "' ");
                if (isupdate != 1) {
                    System.out.println("Failed to add back user");
                }

            } else {
                st.executeUpdate("INSERT INTO users " +
                        "VALUES ('" + sin + "', 1, '" + username + "', '" + email + "', '" + address + "','" + birthday + "', '" + occupation + "','" + card + "')");
            }
            System.out.println("Successful");

            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}

