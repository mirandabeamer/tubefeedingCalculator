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
    </head>
    <body>
        <div class="jumbotron col-lg-12">
            <div class="container">
            <h1 id ="header">CliniKcal</h1>      
            <p id="tagline">Resource center for tube feeding calculations and more!</p>
            </div>
        </div>
        <div class="container">
<!--            <a href="${pageContext.request.contextPath}/displayFormulary">Log in</a>-->
            <h3 id="centerHeading">Tube feeding Calculator</h3>
            <div class="col-lg-6">
                <br/>
                <div class="col-lg-12">
                    <p>${errorMessage}</p>
                    <div class="col-lg-3"></div>
                    <button class="btn btn-default col-lg-3
                            <c:if test="${regimen == 'bolus'}">active</c:if>" id="bolus" value="bolus">Bolus</button>
                    <button class="btn btn-default col-lg-3
                            <c:if test="${regimen == 'continuous'}">active</c:if>" id="continuous" value="continuous">Continuous</button>
                </div>
                <form method="POST" action="calculateFeeds">
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
                        <c:if test="${regimen == 'continuous'}">
                            <div class="form-group">
                                <label for="rate">Rate (ml)</label>
                                <input id="rate" class="form-control" name="rate" type="number" value="${rate}" required>
                            </div>
                            <div class="form-group">
                                <label for="hours">Hours per day</label>
                                <input id="hours" class="form-control" name="hours" type="number" value="${hours}" required>
                            </div>
                        </c:if>
                        <c:if test="${regimen == 'bolus'}">
                            <div class="form-group">
                                <label for="rate">Bolus Volume (ml)</label>
                                <input id="rate" class="form-control" name="rate" type="number" value="${rate}" required>
                            </div>
                            <div class="form-group">
                                <label for="hours">Boluses per day</label>
                                <input id="hours" class="form-control" name="hours" type="number" value="${hours}" required>
                            </div>
                        </c:if>
                    </div>
                    <input style="display:none" id="regimen" name="regimen" value="bolus"/>
                    <button id="calculate" type="submit" class="btn">Calculate</button>
                </form>
            </div>
            <c:if test="${calculatedFormula.formulaName !=null}">
                <div class="col-lg-4">
                    <h4>
                        <c:if test="${regimen=='bolus'}">${rate}ml ${calculatedFormula.formulaName} ${hours}x per day</c:if>
                        <c:if test="${regimen =='continuous'}"> ${calculatedFormula.formulaName} @ ${rate}ml x ${hours}h per day</c:if>
                    </h4>
                    <hr/>
                    <h5>Total daily volume: ${volume}ml</h5>
                    <table class="col-sm-4 table-condensed table" id="resultsTable">
                        <thead>
                            <tr>
                                <th scope="col" class="col-sm-8"><h4>Calories</h4></td>
                                <th scope="col" class="col-sm-4"><h5><c:out value="${calculatedFormula.concentration}"/></h5></td>
                            </tr>
                        </thead>
                        <tr>
                            <td>Protein</td>
                            <td><c:out value="${calculatedFormula.protein}"/>g</td>
                        </tr>
                        <tr>
                            <td>Fat</td>
                            <td><c:out value="${calculatedFormula.fat}"/> g</td>
                        </tr>
                        <tr>
                            <td>Carbohydrates </td>
                            <td><c:out value="${calculatedFormula.carbohydrate}"/>g</td>
                        </tr>
                        <tr>
                            <td>Fiber</td>
                            <td><c:out value="${calculatedFormula.fiber}"/>g</td>
                        </tr>
                        <tr>
                            <td>Water</td>
                            <td><c:out value="${calculatedFormula.water}"/>ml</td>
                        </tr>
                        <tr>
                            <td>Phosphorus</td>
                            <td><c:out value="${calculatedFormula.phosphorus}"/>mg</td>
                        </tr>
                        <tr>
                            <td>Potassium</td>
                            <td><c:out value="${calculatedFormula.potassium}"/>mg</td>
                        </tr>
                        <tr>
                            <td>ml required to meet 100% of RDI's</td>
                            <td><c:out value="${calculatedFormula.mlForRdi}"/>ml</td>
                        </tr>
                    </table>
                </c:if>
            </div>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/tubefeeds.js"></script>


    </body>
</html>

