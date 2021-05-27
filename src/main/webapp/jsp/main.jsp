<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:choose>
    <c:when test="${not empty sessionScope.locale}">
        <fmt:setLocale value="${sessionScope.locale}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en_US"/>
    </c:otherwise>
</c:choose>
<fmt:setBundle basename="locale"/>

<html lang="${sessionScope.locale}">
<head>
    <meta charset="UTF-8">
    <title>Film Ratings</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
</head>

<body>
<c:import url="header.jsp"/>
<div class="side-menu">
    <div class="category-wrap">
        <h3><fmt:message key="menu.films"/></h3>
        <ul>
<%--            <c:forEach var="genre" items="${genres}">--%>
<%--                <li><a href="">${genre.name}</a></li>--%>
<%--            </c:forEach>--%>

        </ul>
    </div>
</div>
<div class="main">
    <c:forEach var="film" items="${films}">
        <img src="${pageContext.request.contextPath}${film.poster}" alt="${film.name}">
    </c:forEach>
</div>
<c:import url="footer.jsp"/>
<div class="info"></div>
</body>
</html>