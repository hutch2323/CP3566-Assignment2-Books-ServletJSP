<%--
  Created by IntelliJ IDEA.
  User: marcu
  Date: 2022-01-26
  Time: 3:44 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="styles/styles.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body class='w-75 m-auto d-block'>
<div class="bg-dark w-100 text-center" style="display:block; text-align: center">
    <a href="index.jsp"><img src="images/libraryData.png" alt="Library Database Logo"></a>
</div>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid navbar-nav">
        <ul class="navbar-nav w-100 nav-fill mx-auto order-0">
            <li class="nav-item fs-5">
                <a class="nav-item nav-link active" href="addBook.jsp">Add Book</a>
            </li>
            <li class="nav-item fs-5">
                <a class="nav-item nav-link active" href="addAuthor.jsp">Add Author</a>
            </li>
            <li class="nav-item fs-5">
                <a class="nav-item nav-link active" href="LibraryData?type=books">View Books</a>
            </li>
            <li class="nav-item fs-5">
                <a class="nav-item nav-link active" href="LibraryData?type=authors">View Authors</a>
            </li>
        </ul>
    </div>
</nav>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>
