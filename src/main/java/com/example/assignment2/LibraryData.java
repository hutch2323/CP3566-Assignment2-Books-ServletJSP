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
                "    <title>View " + requestType.substring(0, 1).toUpperCase() + requestType.substring(1) + "</title>\n" +
                "</head><body class='w-75 m-auto d-block'>");
        out.println("<div class='mt-4 p-4 bg-dark w-75 text-white m-auto'>" +
                "<h1 style='text-align:center'>" + requestType.substring(0, 1).toUpperCase() + requestType.substring(1) + "</h1>" +
                "</div>");

        try{
            if (requestType.equals("books")){
                out.println(generateBookDisplay(getBooks()));
            } else if (requestType.equals("authors")) {
                out.println(generateAuthorDisplay(getAuthors()));
            }
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
            Author author = new Author(0, firstName, lastName);

            try {
                if (checkForDuplicateAuthor(author)){
                    request.setAttribute("error-type", "author");
                } else {
                    insertAuthor(firstName, lastName);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            request.setAttribute("type", "author");
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);

        } else if (type.equals("book")){
            String isbn = request.getParameter("isbn");
            String title = request.getParameter("title");
            int edition = Integer.parseInt(request.getParameter("edition"));
            String copyright = request.getParameter("copyright");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");

            Book book = new Book(isbn, title, edition, copyright);
            Author author = new Author(0, firstName, lastName);
            try {
                if (checkForDuplicateBook(book)){
                    request.setAttribute("error-type", "book");
                } else if (checkForDuplicateAuthor(author)) {
                    // if author exists, upload book and AuthorISBN, but author not added again
                    insertBook(isbn, title, edition, copyright);
                    insertAuthorISBN(getAuthorID(firstName, lastName), isbn);
                } else {
                    insertBook(isbn, title, edition, copyright);
                    insertAuthor(firstName, lastName);
                    insertAuthorISBN(getAuthorID(firstName, lastName), isbn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            request.setAttribute("type", "book");
            request.setAttribute("title", title);
            request.setAttribute("firstName", firstName);
            request.setAttribute("lastName", lastName);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }
    }

    public boolean checkForDuplicateBook(Book book) throws SQLException {
        String querySQL = "Select * from titles " +
                "where isbn = ?";

        try (Connection conn = DBConnection.initDatabase();
             PreparedStatement pstmt = conn.prepareStatement(querySQL)) {

            pstmt.setString(1, book.getIsbn());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() == false) {
                return false;
            } else {
                return true;
            }
        }
    }

    public boolean checkForDuplicateAuthor(Author author) throws SQLException {
        String querySQL = "Select * from authors " +
                "where firstName = ? and lastName = ?";

        try (Connection conn = DBConnection.initDatabase();
             PreparedStatement pstmt = conn.prepareStatement(querySQL)) {

            pstmt.setString(1, author.getFirstName());
            pstmt.setString(2, author.getLastName());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() == false) {
                return false;
            } else {
                return true;
            }
        }
    }

    public String generateBookDisplay(List<Book> bookList){
        String html = "<table class='" + "table table-striped w-75 mt-2 m-auto" + "'><tr><th>ISBN</th><th>Title</th><th>Edition</th><th>Copyright</th><th>Authors</th></tr>";
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

            html += "<tr><td>" + book.getIsbn() + "</td><td>" + book.getTitle() + "</td><td>"
                    + book.getEditionNumber() + "</td><td>" + book.getCopyright() + "</td><td>"
                    + authorString + "</td></tr>";
        }
        return html;
    }

    public String generateAuthorDisplay(List<Author> authorList){
        String html = "<table class='" + "table table-striped w-75 mt-2 m-auto" + "'><tr><th>Author ID</th><th>First Name</th><th>Last Name</th><th>Books</th></tr>";
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

            html += "<tr><td>" + author.getAuthorID() + "</td><td>" + author.getFirstName() + "</td><td>"
                    + author.getLastName() + "</td><td>" + bookString + "</td></tr>";
        }
        return html;
    }
    public List<Book> getBooks() throws SQLException {
        List<Book> bookList = new ArrayList<>();
        String querySQL = "Select * from titles";

        String sql = "Select a.authorID, a.firstName, a.lastName " +
                "from authors a join authorisbn ai " +
                "ON a.authorID = ai.authorID " +
                "JOIN titles t " +
                "ON ai.isbn = t.isbn " +
                "where t.isbn = ?";

        try (Connection conn = DBConnection.initDatabase();
            Statement stmt = conn.createStatement();
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            ResultSet rs = stmt.executeQuery(querySQL);

            while (rs.next()) {
                Book book = new Book(rs.getString("isbn"), rs.getString("title"),
                        rs.getInt("editionNumber"), rs.getString("copyright"));

                pstmt.setString(1, book.getIsbn());
                ResultSet rsAuthors = pstmt.executeQuery();

                while (rsAuthors.next()) {
                    Author author = new Author(rsAuthors.getInt("authorID"),
                            rsAuthors.getString("firstName"), rsAuthors.getString("lastName"));
                    book.getAuthorList().add(author);
                }
                bookList.add(book);
            }
        }

        return bookList;
    }

    public List<Author> getAuthors() throws SQLException {
        List<Author> authorList = new ArrayList<>();
        String querySQL = "Select * from authors";

        String sql = "Select t.isbn, t.title, t.editionNumber, t.copyright " +
                "from titles t join authorisbn ai " +
                "ON t.isbn = ai.isbn " +
                "JOIN authors a " +
                "ON ai.authorID = a.authorID " +
                "where a.authorID = ?";

        try (Connection conn = DBConnection.initDatabase();
             Statement stmt = conn.createStatement();
             PreparedStatement pstmt = conn.prepareStatement(sql)){

            ResultSet rs = stmt.executeQuery(querySQL);

            while (rs.next()) {
                Author author = new Author(rs.getInt("authorID"), rs.getString("firstName"),
                        rs.getString("lastName"));

                pstmt.setInt(1, author.getAuthorID());
                ResultSet rsBooks = pstmt.executeQuery();

                while (rsBooks.next()) {
                    Book book = new Book(rsBooks.getString("isbn"), rsBooks.getString("title"),
                            rsBooks.getInt("editionNumber"), rsBooks.getString("copyright"));
                    author.getBookList().add(book);
                }
                authorList.add(author);
            }
        }
        return authorList;
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
