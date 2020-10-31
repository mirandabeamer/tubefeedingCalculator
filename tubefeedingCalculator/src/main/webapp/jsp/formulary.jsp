<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Formulary</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
    </head>
    <body>
        <div class="container">
            <h1>Formulary Management </h1>
            <hr/>
            <nav class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation"><a href="${pageContext.request.contextPath}/displayHomePage">Home</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/displayAddFormulaPage">Add Formula</a></li>
                </ul>
            </nav> 
            <table class="table table-striped">
                <thead>
                    <tr>
                    <th scope="col">Formula Name</th>
                    <th scope="col">Company</th>
                    <th scope="col">Concentration</th>
                    <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="currentFormula" items="${formulas}">
                        <tr>
                            <td>${currentFormula.formulaName}</td>
                            <td>${currentFormula.company.companyName}</td>
                            <td>${currentFormula.formulaType.formulaType}</td>
                            <td>Edit | Delete </td>
                        </tr>
                    </c:forEach>
                    
                </tbody>
            </table>

        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>

