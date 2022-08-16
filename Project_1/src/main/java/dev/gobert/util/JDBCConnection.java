package dev.gobert.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCConnection {

    //Singleton Design
    private static Connection conn = null;

    public static Connection getConnection() {

        //Establish a Connection if one does not exist, then/otherwise return the established connection
        if (conn == null) {
            try {
                FileInputStream input = new FileInputStream("src/main/resources/connection.properties");
                Properties props = new Properties();
                props.load(input);

                String endpoint = props.getProperty("endpoint");
                String url = "jdbc:postgresql://" + endpoint + "/postgres";
                String username = props.getProperty("username");
                String password = props.getProperty("password");

                conn = DriverManager.getConnection(url, username, password);
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }

        }
        return conn;
    }

    /*
    THIS IS FOR TESTING PURPOSES ONLY
    THIS IS NOT NEEDED TO USE JDBC
    */
//    public static void main(String[] args) {
//
//        Connection conn1 = getConnection();
//        System.out.println(conn1);
//    }
}
