package com.example.assignment2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    static private String DB_URL = "jdbc:mariadb://localhost:3300/books";
    static private String USER = "root";
    static private String PASS = "password";

    public static Connection initDatabase(){
        // Open a connection
        try{
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            return conn;
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error connecting with database. Closing application");
            System.exit(1);
            return null;
        } finally {
//            conn.close();
        }
    }

    public static void main(String[] args) throws SQLException {
        String querySQL = "Select * from titles";
        Statement stmt = null;
        List<Book> bookList = new ArrayList<>();
        stmt = initDatabase().createStatement();
        ResultSet rs = stmt.executeQuery(querySQL);
        while (rs.next()) {
            Book book = new Book(rs.getString("isbn"), rs.getString("title"),
                    rs.getInt("editionNumber"), rs.getString("copyright"));
            bookList.add(book);
        }
        for(Book book : bookList){
            System.out.println(book.getTitle());
        }
    }
}
