<%--
  Created by IntelliJ IDEA.
  User: mitry
  Date: 29.09.20
  Time: 22:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>

<html>
<head>
    <title>Edit Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit Meal</h2>
<fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="date"/>
<fmt:formatDate value="${parsedDate}" var="newParsedDate" type="date"
                pattern="yyyy-MM-dd HH:mm"/>

<form method="POST" action='MealController' name="frmAddMeal" accept-charset="UTF-8">
    <input type="hidden", name="mealId" value="${meal.id}"/>
    DateTime :    <input type="datetime-local" name="date" value="${newParsedDate}" /><br>
    Description : <input type="text" name="description" value="<c:out value="${meal.description}" />"/><br>
    Calories :    <input type="text" name="calories" value="<c:out value="${meal.calories}" />"/><br><br>
    <input type="submit" value="Submit" />
    <input type="button" value="Cancel" onclick="window.location.replace('meals')"/>
</form>
</body>
</html>
