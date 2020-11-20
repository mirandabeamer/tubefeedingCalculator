<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Home Page</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet"> 
        <link href="${pageContext.request.contextPath}/css/tubefeeds.css" rel="stylesheet"> 
        <link href="https://fonts.googleapis.com/css2?family=Rammetto+One&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
    </head>
    <body>
        <div class="page-one">
            <div class="jumbotron col-lg-12">
                <div class="container">
                    <h1 id ="header"><i class="fas fa-seedling"></i> CliniKcal</h1>      
                    <p id="tagline">Resource center for tube feeding calculations and more!</p>
                </div>
            </div>
            <div class="container">
    <!--            <a href="${pageContext.request.contextPath}/displayFormulary">Log in</a>-->
                <h3 id="centerHeading">Tube Feeding Calculator</h3>
                <div class="col-lg-6">
                    <br/>
                    <div class="col-lg-12">
                        <p id="errorMessage">${errorMessage}</p>
                        <div class="col-lg-3"></div>
                        <button class="btn btn-default col-lg-3 active" id="bolus" value="bolus">Bolus</button>
                        <button class="btn btn-default col-lg-3" id="continuous" value="continuous">Continuous</button>
                    </div>
                    <div class="form-group">
                        <label for ="companyName">Select Company</label>
                        <select id="companyName" class="form-control" name="company">
                            <option value="default" class="disabled">Select company</option>
                            <c:forEach var="currentCompany" items="${companies}">
                                <option value="${currentCompany.companyId}"
                                        <c:if test="${currentCompany.companyId == companyId}">selected</c:if>>
                                    ${currentCompany.companyName}</option>
                                </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="formulaName">Select Formula</label>
                        <select id="formulaName" class="form-control" name="formula">
                            <c:forEach var="currentFormula" items="${formulas}">
                                <option value="${currentFormula.formulaId}"
                                        <c:if test="${currentFormula.formulaId == calculatedFormula.formulaId}">
                                            selected</c:if>>${currentFormula.formulaName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div id="rateHoursInput">
                        <div class="form-group">
                            <label for="rate">Bolus Volume (ml)</label>
                            <input id="rate" class="form-control" name="rate" type="number" value="${rate}" required>
                        </div>
                        <div class="form-group">
                            <label for="hours">Boluses per day</label>
                            <input id="hours" class="form-control" name="hours" type="number" value="${hours}" required>
                        </div>
                    </div>
                    <input style="display:none" id="regimen" name="regimen" value="bolus"/>
                    <input id="submit-button" type="submit" class="btn" value="Calculate"/>
                </div>

                <div class="col-lg-4" id="results">
                    <h4 id="regimenHeading"></h4>
                    <hr/>
                    <h5 id = "volumeHeading"></h5>
                    <table class="col-sm-4 table-condensed table" id="resultsTable">
                    </table>
                    <div class="copy">
                        <textarea class="copy col-lg-12" id="textToCopy"></textarea>
                        <button id="copy-btn">Copy</button>
                    </div>
                </div>

            </div>
        </div>
        <footer>
            <div class="containter">
                <p>© 2020 Clinikcal. All Rights Reserved.</p>
            </div>
        </footer>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/tubefeeds.js"></script>


    </body>
</html>

