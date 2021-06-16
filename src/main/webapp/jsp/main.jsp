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
        <c:set var="enumValues" value="${genres}"/>
        <h3><fmt:message key="menu.genre"/></h3>
        <ul>
            <a href="${pageContext.request.contextPath}/controller?command=main">
                <li>ALL</li>
            </a>
            <c:forEach items="${enumValues}" var="enumValue">
                <a href="${pageContext.request.contextPath}/controller?command=sort-movie-by-genre&genre=${enumValue}">
                    <li>${enumValue}</li>
                </a>
            </c:forEach>
        </ul>
    </div>
</div>
<div class="main">
    <c:import url="movie-card.jsp"/>
    <div class="image_box">
        <div class="container">
            <c:forEach var="movie" items="${movies}" begin="1">
                <div class="movie">
                    <div class="movie-header"
                         style="background-image: url('${pageContext.request.contextPath}${movie.poster}'); background-size: cover">
                    </div>
                    <div class="movie-content">
                        <div class="movie-content-header">
                            <a href="${pageContext.request.contextPath}/controller?command=movie-info&id=${movie.id}">
                                <h3 class="movie-title">${movie.name}</h3>
                            </a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

</body>
</html>