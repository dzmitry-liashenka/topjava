<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %><%--
  Created by IntelliJ IDEA.
  User: mitry
  Date: 21.09.20
  Time: 21:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <title>Meals</title>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>Meals</h2>
    <p><a href="MealController?action=add">Add Meal</a></p>
    <c>
        <table class="table" border="1" cellpadding="8" cellspacing="0">
            <thead>
            <tr>
                <th>ID</th>
                <th>Date</th>
                <th>Description</th>
                <th>Excess</th>
                <th colspan="2">Action</th>
            </tr>
            </thead>
            <c:forEach var="meal" items="${mealsTo}">
                <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealTo"/>
                <tr style="${meal.excess ? 'color: red' : 'color: green'}">
<%--                    <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate"--%>
<%--                                   type="date"/>--%>
<%--                    <fmt:formatDate value="${parsedDate}" var="newParsedDate" type="date"--%>
<%--                                    pattern="yyyy-MM-dd HH:mm"/>--%>
                    <td><c:out value="${meal.id}"/></td>
<%--                    <td><c:out value="${newParsedDate}"/></td>--%>
                    <td><%=TimeUtil.toString(meal.getDateTime())%></td>
                    <td><c:out value="${meal.description}"/></td>
                    <td><c:out value="${meal.excess}"/></td>
                    <td><a href="MealController?action=edit&mealId=<c:out value="${meal.id}" />">Update</a></td>
                    <td><a href="MealController?action=delete&mealId=<c:out value="${meal.id}" />">Delete</a></td>
                </tr>
            </c:forEach>
        </table>
    </c>
</section>
</body>
</html>
