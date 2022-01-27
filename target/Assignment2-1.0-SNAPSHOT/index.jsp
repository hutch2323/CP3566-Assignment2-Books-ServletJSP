<%@ include file="/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Library Data</title>
</head>
<body>
<%
    if (request.getAttribute("type") != null){
        if (request.getAttribute("type").equals("book")){ %>
            <div id="alert" class="alert alert-success d-flex align-items-center m-auto" role="alert">
                <div class="text-center m-auto">
                    ${title} by ${firstName} ${lastName} has been added to the database
<%--                        <button id="closeBtn" class="btn btn-primary bg-success text-white" type="button" onClick="javascript:hideAlert()">Close</button>--%>
                </div>
            </div>
       <% } else if (request.getAttribute("type").equals("author")){ %>
            <div id="alert" class="alert alert-success d-flex align-items-center m-auto" role="alert">
                <div class="text-center m-auto">
                    ${firstName} ${lastName} has been added to the database
<%--                        <button id="closeBtn" class="btn btn-primary bg-success text-white" type="button" onClick="javascript:hideAlert()">Close</button>--%>
                </div>
            </div>
        <%}
    }
%>

<%--<script type="text/javascript">--%>
<%--   const hideAlert = () => {--%>
<%--       document.getElementById("alert").style.display = "none";--%>
<%--   };--%>
<%--</script>--%>

<div id="heading" class="mt-4 p-4 bg-dark w-50 text-white m-auto">
    <h1 style="text-align:center">Welcome to LibraryData</h1>
</div>
<div class="accordion w-50 m-auto mt-2" id="accordionExample">
    <div class="accordion-item">
        <h2 class="accordion-header" id="headingOne">
            <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                Adding A Book To The Database
            </button>
        </h2>
        <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
            <div class="accordion-body">
                To add a new book to the Library Database, click the <strong>Add Book</strong> link in the navigation bar,
                fill out the form, and click the submit button. To view your changes, check out the section on viewing books.
                To return to this page click the LibraryData logo.
            </div>
        </div>
    </div>
    <div class="accordion-item">
        <h2 class="accordion-header" id="headingTwo">
            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                Adding An Author To The Database
            </button>
        </h2>
        <div id="collapseTwo" class="accordion-collapse collapse" aria-labelledby="headingTwo" data-bs-parent="#accordionExample">
            <div class="accordion-body">
                To add a new author to the Library Database, click the <strong>Add Author</strong> link in the navigation bar,
                fill out the form, and click the submit button. To view your changes, check out the section on viewing authors.
                To return to this page click the LibraryData logo.
            </div>
        </div>
    </div>
    <div class="accordion-item">
        <h2 class="accordion-header" id="headingThree">
            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                Viewing Books In The Database
            </button>
        </h2>
        <div id="collapseThree" class="accordion-collapse collapse" aria-labelledby="headingThree" data-bs-parent="#accordionExample">
            <div class="accordion-body">
                To view the books in the database, click the <strong>View Books</strong> link in the navigation bar. To return to this page
                click the LibraryData logo.
            </div>
        </div>
    </div>
    <div class="accordion-item">
        <h2 class="accordion-header" id="headingFour">
            <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                Viewing Authors In The Database
            </button>
        </h2>
        <div id="collapseFour" class="accordion-collapse collapse" aria-labelledby="headingFour" data-bs-parent="#accordionExample">
            <div class="accordion-body">
                To view the authors in the database, click the <strong>View Authors</strong> link in the navigation bar. To return to this page
                click the LibraryData logo.
            </div>
        </div>
    </div>
</div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>