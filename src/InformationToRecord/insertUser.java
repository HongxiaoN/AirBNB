package InformationToRecord;

import java.sql.Connection;
import java.sql.DriverManager;
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
        String username = sc.nextLine();
        System.out.println("Please enter your email: ");
        String email = sc.nextLine();
        System.out.println("Please enter your default address: ");
        String address = sc.nextLine();
        System.out.println("Please enter your date of birth in the form of YYMMDD such as Aug 8th 2022 -> 20220808: ");
        int birthday = scInt.nextInt();
        if (birthday > 20040101){
            System.out.println("A user must be at least 18 years old to register!");
            return;
        }

        System.out.println("Please enter your occupation: ");
        String occupation = sc.nextLine();
        System.out.println("Please enter your card number: ");
        int card = scInt.nextInt();



        try {
            String url = "jdbc:mysql://localhost:3306/C43Project";
            Connection conn = DriverManager.getConnection(url,"root","");
            Statement st = conn.createStatement();
            st.executeUpdate("INSERT INTO users " +
                    "VALUES ('"+sin+"','"+username+"', '"+email+"', '"+address+"','"+birthday+"', '"+occupation+"','"+card+"')");

            conn.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

    }
}

