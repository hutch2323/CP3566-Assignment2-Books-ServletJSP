<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<%--<a href="">Add Book</a><br/>--%>
<a href="addAuthor.jsp">Add Author</a><br/>
<a href="LibraryData?type=books">View Books</a><br/>
<a href="LibraryData">View Authors</a>
</body>
</html>