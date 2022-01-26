<%--
  Created by IntelliJ IDEA.
  User: marcu
  Date: 2022-01-24
  Time: 4:43 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Book</title>
</head>
<body>
    <h3>Add Book</h3>
    <br/>
    <form method="post" action="LibraryData">
        <input type="hidden" name="type" value="book">
<%--        <select id="program" name="program" size="1">--%>
<%--            <option>ASD</option>--%>
<%--            <option>Software Development</option>--%>
<%--            <option>CSN</option>--%>
<%--        </select>--%>
            <label for="isbn">ISBN:</label>
            <input type="text" name="isbn" id="isbn"/><br/>
            <label for="title">Title:</label>
            <input type="text" name="title" id="title"/><br/>
            <label for="edition">Edition Number:</label>
            <input type="text" name="edition" id="edition"/><br/>
            <label for="copyright">Copyright:</label>
            <input type="text" name="copyright" id="copyright"/><br/>
            <label for="firstName">Author First Name:</label>
            <input type="text" name="firstName" id="firstName"/><br/>
            <label for="lastName">Author Last Name:</label>
            <input type="text" name="lastName" id="lastName"/>

        <br/>
        <br/>
        <input type="submit">
        <a href="index.jsp">Back</a>
    </form>
    <br/>
</body>
</html>
