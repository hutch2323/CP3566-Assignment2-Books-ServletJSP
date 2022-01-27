package com.example.assignment2;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.assignment2.DBConnection;

@WebServlet(name = "LibraryData", value = "/LibraryData")
public class LibraryData extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String requestType = request.getParameter("type");
        request.getRequestDispatcher("/header.jsp").include(request, response);
        out.println("<html><head>\n" +
                "    <title>Add Author</title>\n" +
                "</head><body class='w-75 m-auto d-block'>");
        out.println("<div class='mt-4 p-4 bg-dark w-75 text-white m-auto'>" +
                "<h1 style='text-align:center'>" + requestType.substring(0, 1).toUpperCase() + requestType.substring(1) + "</h1>" +
                "</div>");

//        try{
//            Connection conn = DBConnection.initDatabase();
//        } catch (Exception e){S
//
//        }
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnection.initDatabase();
            stmt = conn.createStatement();

            if (requestType.equals("books")){
                String querySQL = "Select * from titles";
                List<Book> bookList = new ArrayList<>();
                try {

                    ResultSet rs = stmt.executeQuery(querySQL);

                    while (rs.next()) {
                        Book book = new Book(rs.getString("isbn"), rs.getString("title"),
                                rs.getInt("editionNumber"), rs.getString("copyright"));

                        String sql = "Select a.authorID, a.firstName, a.lastName " +
                                "from authors a join authorisbn ai " +
                                "ON a.authorID = ai.authorID " +
                                "JOIN titles t " +
                                "ON ai.isbn = t.isbn " +
                                "where t.isbn = ?";

                        pstmt = conn.prepareStatement(sql);
                        pstmt.setString(1, book.getIsbn());
                        ResultSet rsAuthors = pstmt.executeQuery();

                        while (rsAuthors.next()) {
                            Author author = new Author(rsAuthors.getInt("authorID"),
                                    rsAuthors.getString("firstName"), rsAuthors.getString("lastName"));
                            book.getAuthorList().add(author);
                        }
                        bookList.add(book);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }

                out.println("<table class='" + "table table-striped w-75 mt-2 m-auto" + "'><tr><th>ISBN</th><th>Title</th><th>Edition</th><th>Copyright</th><th>Authors</th></tr>");
                for(Book book : bookList){
                    int count = 0;
                    String authorString = "";
                    for (Author author : book.getAuthorList()) {
                        if (book.getAuthorList().size() == 1){
                            authorString += author.getFirstName() + " " + author.getLastName();
                        } else if ((count + 1) == book.getAuthorList().size()) {
                            authorString += "and " + author.getFirstName() + " " + author.getLastName();
                        } else if (book.getAuthorList().size() == 2) {
                            authorString += author.getFirstName() + " " + author.getLastName() + " ";
                        } else {
                            authorString += author.getFirstName() + " " + author.getLastName() + ", ";
                        }
                        count++;
                    }

                    out.println("<tr><td>" + book.getIsbn() + "</td><td>" + book.getTitle() + "</td><td>"
                            + book.getEditionNumber() + "</td><td>" + book.getCopyright() + "</td><td>"
                            + authorString + "</td></tr>");
                }



            }  else if (requestType.equals("authors")) {
                String querySQL = "Select * from authors";
                List<Author> authorList = new ArrayList<>();
                try {
                    ResultSet rs = stmt.executeQuery(querySQL);

                    while (rs.next()) {
                        Author author = new Author(rs.getInt("authorID"), rs.getString("firstName"),
                                rs.getString("lastName"));

                        String sql = "Select t.isbn, t.title, t.editionNumber, t.copyright " +
                                "from titles t join authorisbn ai " +
                                "ON t.isbn = ai.isbn " +
                                "JOIN authors a " +
                                "ON ai.authorID = a.authorID " +
                                "where a.authorID = ?";

                        pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1, author.getAuthorID());
                        ResultSet rsBooks = pstmt.executeQuery();

                        while (rsBooks.next()) {
                            Book book = new Book(rsBooks.getString("isbn"), rsBooks.getString("title"),
                                    rsBooks.getInt("editionNumber"), rsBooks.getString("copyright"));
                            author.getBookList().add(book);
                        }
                        authorList.add(author);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }

                out.println("<table class='" + "table table-striped w-75 mt-2 m-auto" + "'><tr><th>Author ID</th><th>First Name</th><th>Last Name</th><th>Books</th></tr>");
                for(Author author : authorList){
                    int count = 0;
                    String bookString = "";
                    for (Book book : author.getBookList()) {
                        if (author.getBookList().size() == 1){
                            bookString += book.getTitle();
                        }else if ((count + 1) == author.getBookList().size()) {
                            bookString += "and " + book.getTitle();
                        } else if (book.getAuthorList().size() == 2) {
                            bookString += book.getTitle() + " ";
                        } else {
                            bookString += book.getTitle() + ", ";
                        }
                        count++;
                    }

                    out.println("<tr><td>" + author.getAuthorID() + "</td><td>" + author.getFirstName() + "</td><td>"
                            + author.getLastName() + "</td><td>" + bookString + "</td></tr>");
                }
            }
            conn.close();
            stmt.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.println("</table></body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        String type = request.getParameter("type");

        if (type.equals("author")){
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");

            insertAuthor(firstName, lastName);

            out.println("<p>" + firstName + " " + lastName + " was successfully added to the database.</p>");
            out.println("</br><a href=\"index.jsp\">Continue</a>");
        } else if (type.equals("book")){
            String isbn = request.getParameter("isbn");
            String title = request.getParameter("title");
            int edition = Integer.parseInt(request.getParameter("edition"));
            String copyright = request.getParameter("copyright");

            insertBook(isbn, title, edition, copyright);

            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");

            insertAuthor(firstName, lastName);

            insertAuthorISBN(getAuthorID(firstName, lastName), isbn);

            out.println("<p>" + title + " by " + firstName + " " + lastName
                    + " was successfully added to the database.</p>");
            out.println("</br><a href=\"index.jsp\">Continue</a>");
        }
        out.println("</body></html>");
    }

    public int getAuthorID(String firstName, String lastName){
        String authorIDQuery = "Select authorID from authors " +
                "where firstName = ? And lastName = ?";
        try(Connection conn = DBConnection.initDatabase();
            PreparedStatement pstmt = conn.prepareStatement(authorIDQuery);){
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            ResultSet rs = pstmt.executeQuery();
            rs.last();
            return rs.getInt("authorID");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        }
    }

    public void insertAuthor(String firstName, String lastName){
        String SQLAuthors= "INSERT into authors (firstName, lastName)" +
                "Values (?, ?)";
        try(Connection conn = DBConnection.initDatabase();
            PreparedStatement pstmt = conn.prepareStatement(SQLAuthors);){
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insertAuthorISBN(int authorID, String isbn){
        String SQLAuthorISBN = "INSERT into authorISBN (authorID, isbn)" +
                "Values (?, ?)";
        try(Connection conn = DBConnection.initDatabase();
            PreparedStatement pstmt = conn.prepareStatement(SQLAuthorISBN);){
            pstmt.setInt(1, authorID);
            pstmt.setString(2, isbn);
            pstmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void insertBook(String isbn, String title, int edition, String copyright){
        String SQLBooks = "INSERT into titles (isbn, title, editionNumber, copyright)" +
                "Values (?, ?, ?, ?)";
        try(Connection conn = DBConnection.initDatabase();
            PreparedStatement pstmt = conn.prepareStatement(SQLBooks);){
            pstmt.setString(1, isbn);
            pstmt.setString(2, title);
            pstmt.setInt(3, edition);
            pstmt.setString(4, copyright);
            pstmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
