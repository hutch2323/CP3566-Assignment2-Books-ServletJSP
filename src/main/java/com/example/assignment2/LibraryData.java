package com.example.assignment2;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "LibraryData", value = "/LibraryData")
public class LibraryData extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String requestType = request.getParameter("type");
//        String requestType = "";
        out.println("<html><body>");
        out.println("<h1>" + requestType + "</h1>");
        Connection conn = DBConnection.initDatabase();

//        if (requestType.equals("")){
//            String querySQL = "Select * from titles";
//            List<Book> bookList = new ArrayList<>();
//            try {
//                Connection conn = DBConnection.initDatabase();
//                Statement stmt = conn.createStatement();
//                ResultSet rs = stmt.executeQuery(querySQL);
//                stmt.close();
//                conn.close();
//
//                while (rs.next()) {
//                    Book book = new Book(rs.getString("isbn"), rs.getString("title"),
//                            rs.getInt("editionNumber"), rs.getString("copyright"));
//                    bookList.add(book);
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//            out.println("<table><tr><th>ISBN</th><th>Title</th><th>Edition</th><th>Copyright</th></tr>");
//            for(Book book : bookList){
//                out.println("<tr><td>" + book.getIsbn() + "</td><td>" + book.getTitle() + "</td><td>"
//                        + book.getEditionNumber() + "</td><td>" + book.getCopyright() + "</td></tr>");
//            }
//        }
        out.println("</body></html>");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");

        if (type.equals("author")){
            int authorID = Integer.parseInt(request.getParameter("authorID"));
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
        } else if (type.equals("book")){

        }
    }
}
