<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Add Formula</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
    </head>
    <body>
        <div class="container">
            <h1>Tubefeeding Calculator </h1>
            <hr/>
            <nav class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation"><a href="${pageContext.request.contextPath}/displayHomePage">Home</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/displayFormulary">Formulary</a></li>
                </ul>
            </nav> 
                <h4>Add New Formula</h4>
                <div>
                    <form role="form" method="POST" action="addFormula">
                        <div>
                            <label for ="formulaName">Formula Name</label>
                            <input id="formulaName" name="formulaName" class="form-control">
                        </div>
                        <div>
                            <label for ="company">Company</label>
                            <select class="form-control" name="company" id="company">
                                <c:forEach var="currentCompany" items="${companies}">
                                    <option value="${currentCompany.companyId}">${currentCompany.companyName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div>
                            <label for="concentration">Formula concentration (kcal/mL)</label>
                            <input id="concentration" class="form-control" name="concentration"/>
                        </div>
                        <div>
                            <label for="protein">Protein(g): </label>
                            <input id="protein" class="form-control" name="protein"/>
                        </div>
                        <div>
                            <label for="carbs">Carbohydrates(g):</label>
                            <input id="carbs" class="form-control" name="carbs"/>
                        </div>
                        <div>
                            <label for="fat">Fat(g):</label>
                            <input id="fat" class="form-control" name="fat"/>
                        </div>
                        <div>
                            <label for="fiber">Fiber(g):</label>
                            <input id="fiber" class="form-control" name="fiber"/>
                        </div>
                        <div>
                            <label for="potassium">Potassium(mg):</label>
                            <input id="potassium" class="form-control" name="potassium">
                        </div>
                        <div>
                            <label for="phosphorus">Phosphorus(mg):</label>
                            <input id="phosphorus" class="form-control" name="phosphorus">
                        </div>
                        <div>
                            <label for="water">Water(mL):</label>
                            <input id="water" class="form-control" name="water">
                        </div>
                        <div>
                            <label for="mlToRdi">Milliliters to meet 100% of RDI's:</label>
                            <input id="mlToRdi" class="form-control" name="mlToRdi">
                        </div>
                        <div>
                            <button type="submit" class="btn btn-default">Submit</button>
                        </div>
                    </form>
                </div>

        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>

