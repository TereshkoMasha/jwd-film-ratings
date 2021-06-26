<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tg" uri="tag" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Film Ratings</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
</head>
<c:choose>
    <c:when test="${not empty sessionScope.locale}">
        <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en_US" scope="session"/>
    </c:otherwise>
</c:choose>
<fmt:setBundle basename="Locale"/>

<body>
<jsp:include page="header.jsp"/>
<div class="side-menu">
    <div class="category-wrap">
        <c:set var="enumValues" value="${genres}"/>
        <h3><fmt:message key="menu.genre"/></h3>
        <ul>
            <a href="${pageContext.request.contextPath}/controller?command=main">
                <li><tg:genre genre="All"/></li>
            </a>
            <c:forEach items="${enumValues}" var="enumValue">
                <a href="${pageContext.request.contextPath}/controller?command=sort-movie-by-genre&genre=${enumValue}">
                    <li><tg:genre genre="${enumValue}"/></li>
                </a>
            </c:forEach>
        </ul>
    </div>
</div>
<div class="main">
    <c:import url="movie-card.jsp"/>
    <div class="image_box">
        <div class="container">
            <c:choose>
                <c:when test="${page eq 1}">
                    <c:set var="count" value="${1}"></c:set>
                </c:when>
                <c:otherwise>
                    <c:set var="count" value="${page*3 - 3}"></c:set>
                </c:otherwise>
            </c:choose>
            <c:forEach var="movie" items="${movies}" begin="${count}" end="${count + 2}">
                <div class="movie">
                    <div class="movie-header"
                         style="background-image: url('${pageContext.request.contextPath}${movie.poster}'); background-size: cover">
                    </div>
                    <div class="movie-content">
                        <div class="movie-content-header">
                            <a href="${pageContext.request.contextPath}/controller?command=movie-info&id=${movie.id}&page=${1}">
                                <h3 class="movie-title">${movie.name}</h3>
                            </a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <c:if test="${movies.size() > 1}">
            <div class="pagination p2">
                <ul>
                    <c:forEach begin="1" end="${movies.size()/3 + 1 }" varStatus="loop">
                        <a href="${pageContext.request.contextPath}/controller?command=main&page=${loop.count}">
                            <li>${loop.count}</li>
                        </a>
                    </c:forEach>
                </ul>

            </div>
        </c:if>
    </div>
</div>
</body>
</html>