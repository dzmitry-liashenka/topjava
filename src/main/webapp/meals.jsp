<%--
  Created by IntelliJ IDEA.
  User: mitry
  Date: 21.09.20
  Time: 21:08
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<c>
    <table class="table">
        <tbody>
        <c:forEach var="meal" items="${mealsTo}">
            <tr>
                <c:if test="${meal.excess eq true}">
                    <td style="color: green">
                        <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate"
                                       type="date"/>
                        <fmt:formatDate value="${parsedDate}" var="newParsedDate" type="date"
                                        pattern="yyyy-MM-dd HH:mm"/>
                        Date: <c:out value="${newParsedDate}"/>
                        Description: <c:out value="${meal.description}"/>
                        Excess is <c:out value="${meal.excess}"/>
                    </td>
                </c:if>
                <c:if test="${meal.excess eq false}">
                    <td style="color: red">
                        <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate"
                                       type="date"/>
                        <fmt:formatDate value="${parsedDate}" var="newParsedDate" type="date"
                                        pattern="yyyy-MM-dd HH:mm"/>
                        Date: <c:out value="${newParsedDate}"/>
                        Description: <c:out value="${meal.description}"/>
                        Excess is <c:out value="${meal.excess}"/>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c>
</body>
</html>
