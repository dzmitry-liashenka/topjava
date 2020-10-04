<%--
  Created by IntelliJ IDEA.
  User: mitry
  Date: 21.09.20
  Time: 21:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<p><a href="MealController?action=add">Add Meal</a></p>
<c>
    <table class="table" border="1">
        <thead>
        <tr>
            <th>ID</th>
            <th>Date</th>
            <th>Description</th>
            <th>Excess</th>
            <th colspan="2">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="meal" items="${mealsTo}">
                <c:if test="${meal.excess eq true}">
                    <tr style="color: green">
                        <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate"
                                       type="date"/>
                        <fmt:formatDate value="${parsedDate}" var="newParsedDate" type="date"
                                        pattern="yyyy-MM-dd HH:mm"/>
                        <td><c:out value="${meal.id}"/></td>
                        <td><c:out value="${newParsedDate}"/></td>
                        <td><c:out value="${meal.description}"/></td>
                        <td><c:out value="${meal.excess}"/></td>
                        <td><a href="MealController?action=edit&mealId=<c:out value="${meal.id}" />">Update</a></td>
                        <td><a href="MealController?action=delete&mealId=<c:out value="${meal.id}" />">Delete</a></td></td>
                    </tr>
                </c:if>
                <c:if test="${meal.excess eq false}">
                    <tr style="color: red">
                        <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate"
                                       type="date"/>
                        <fmt:formatDate value="${parsedDate}" var="newParsedDate" type="date"
                                        pattern="yyyy-MM-dd HH:mm"/>
                        <td><c:out value="${meal.id}"/></td>
                        <td><c:out value="${newParsedDate}"/></td>
                        <td><c:out value="${meal.description}"/></td>
                        <td><c:out value="${meal.excess}"/></td>
                        <td><a href="MealController?action=edit&mealId=<c:out value="${meal.id}" />">Update</a></td>
                        <td><a href="MealController?action=delete&mealId=<c:out value="${meal.id}" />">Delete</a></td></td>
                    </tr>
                </c:if>
        </c:forEach>
        </tbody>
    </table>
</c>


</body>
</html>
