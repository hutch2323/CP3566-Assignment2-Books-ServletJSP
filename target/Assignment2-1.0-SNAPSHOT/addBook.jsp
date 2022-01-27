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
    <title>Add Book</title>
</head>
<body class="d-block">
    <div id="heading" class="mt-4 p-4 bg-dark text-white w-50 m-auto">
        <h1 style="text-align:center">Add Book</h1>
    </div>
    <form method="post" action="LibraryData" class="w-50 mt-2 m-auto">
        <input type="hidden" name="type" value="book">
<%--        <select id="program" name="program" size="1">--%>
<%--            <option>ASD</option>--%>
<%--            <option>Software Development</option>--%>
<%--            <option>CSN</option>--%>
<%--        </select>--%>
        <div class="mb-3">
            <label for="isbn" class="form-label">ISBN:</label>
            <input type="text" name="isbn" id="isbn" class="form-control" required/>
        </div>
        <div class="mb-3">
            <label for="title" class="form-label">Title:</label>
            <input type="text" name="title" id="title" class="form-control" required/>
        </div>
        <div class="mb-3">
            <label for="edition" class="form-label">Edition Number:</label>
            <input type="number" name="edition" id="edition" class="form-control" required/>
        </div>
        <div class="mb-3">
            <label for="copyright" class="form-label">Copyright:</label>
            <input type="text" name="copyright" id="copyright" class="form-control" required/>
        </div>
        <div class="mb-3">
            <label for="firstName" class="form-label">Author First Name:</label>
            <input type="text" name="firstName" id="firstName" class="form-control" required/>
        </div>
        <div class="mb-3">
            <label for="lastName" class="form-label">Author Last Name:</label>
            <input type="text" name="lastName" id="lastName" class="form-control" required/>
        </div>
        <div class="m-auto text-center">
            <input type="submit" class="btn btn-primary">
            <a href="index.jsp" class="btn btn-primary">Cancel</a>
        </div>
    </form>
</body>
</html>
