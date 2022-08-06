package InformationToRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class insertChangePrice {
    public static void main(String[] args) {


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
