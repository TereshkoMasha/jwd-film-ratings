<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:choose>
    <c:when test="${not empty sessionScope.locale}">
        <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en_US" scope="session"/>
    </c:otherwise>
</c:choose>
<fmt:setBundle basename="Locale" scope="session"/>

<html lang="${sessionScope.locale}">
<head>
    <meta charset="utf-8">
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/movie-card.css">
</head>
<body>
<div class="movie-card">
    <div class="container-movie">
        <a href="${pageContext.request.contextPath}/controller?command=movie-info&id=${movies[0].id}"><img
                src="${pageContext.request.contextPath}${movies[0].poster}" alt="${movies[0].name}"
                class="cover"></a>
        <div class="hero">
            <div class="details">
                <div class="title1"><c:out value="${movies[0].name}"/></div>
            </div> <!-- end details -->
        </div> <!-- end hero -->
        <div class="description">
            <div class="column1">
                <span class="tag">${fn:toLowerCase(movies[0].genre)}</span>
            </div> <!-- end column1 -->
            <div class="column2">
                <p><c:out value="${movies[0].description}"/></p>
            </div> <!-- end column2 -->
        </div> <!-- end description -->
    </div> <!-- end container -->
</div> <!-- end movie-card -->
</body>
</html>
