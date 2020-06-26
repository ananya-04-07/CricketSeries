package com.cricketgame;

import java.sql.*;

public class MysqlCon {
    static Connection connection = null;
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/Cricket?",System.getenv("USER_NAME"),System.getenv("PASS_WORD"));
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Player");
            System.out.println(rs);
            while (rs.next())
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return connection;
    }
    public static void main(String[] args) {
        System.out.println(MysqlCon.getConnection());
    }
}