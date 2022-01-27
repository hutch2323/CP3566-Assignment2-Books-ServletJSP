<%--
  Created by IntelliJ IDEA.
  User: marcu
  Date: 2022-01-24
  Time: 4:43 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/header.jsp" %>
<html>
<head>
    <title>Add Author</title>
    <!-- CSS only -->
</head>
<body>
    <div id="heading" class="mt-4 p-4 bg-dark w-50 text-white m-auto">
        <h1 style="text-align:center">Add Author</h1>
    </div>
    <form method="post" action="LibraryData" class="w-50 m-auto mt-2">
        <input type="hidden" name="type" value="author">
<%--        <select id="program" name="program" size="1">--%>
<%--            <option>ASD</option>--%>
<%--            <option>Software Development</option>--%>
<%--            <option>CSN</option>--%>
<%--        </select>--%>
        <div class="mb-3">
            <label for="firstName" class="form-label">First Name:</label>
            <input type="text" name="firstName" id="firstName" class="form-control" required/>
        </div>
        <div class="mb-3">
            <label for="lastName" class="form-label">Last Name:</label>
            <input type="text" name="lastName" id="lastName" class="form-control" required/>
        </div>
        <div class="m-auto text-center">
            <input type="submit" class="btn btn-primary">
        </div>
    </form>
    <br/>
</body>
</html>
