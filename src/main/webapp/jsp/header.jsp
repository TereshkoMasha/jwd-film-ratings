<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>

<html lang="${sessionScope.locale}">
<head>
    <title></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <script src="${pageContext.request.contextPath}/js/header.js" async></script>
    <script src="${pageContext.request.contextPath}/js/search.js" async></script>
</head>

<c:choose>
    <c:when test="${not empty locale}">
        <fmt:setLocale value="${locale}" scope="session"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en_US" scope="session"/>
    </c:otherwise>
</c:choose>
<fmt:setBundle basename="Locale" scope="session"/>

<body>
<div>
    <header class="header" id="logo-menu">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/controller?command=change-language&locale=${'by_BY'}"><img
                    src="${pageContext.request.contextPath}/img/by.png" alt="bel"></a>
            <a href="${pageContext.request.contextPath}/controller?command=change-language&locale=${'en_US'}"><img
                    src="${pageContext.request.contextPath}/img/eng.png" alt="eng"></a>
            <a href="${pageContext.request.contextPath}/controller?command=change-language&locale=${'ru_RU'}"><img
                    src="${pageContext.request.contextPath}/img/rus.png" alt="rus"></a>
            <a href="${pageContext.request.contextPath}/controller?command=main&page=0" style="margin-left: 40px">Film
                Ratings</a>
        </div>
        <div class="search-wrapper">
            <form method="post" accept-charset="utf-8"
                  action="${pageContext.request.contextPath}/controller?command=movie-search&page=${1}">
                <input type="text" name="movie" required class="search-box"
                       placeholder="<fmt:message key="movie.placeholder"/>"/>
            </form>
        </div>

        <nav class="menu">
            <a href="${pageContext.request.contextPath}/controller?command=main"><fmt:message key="header.main"/></a>
            <c:choose>
                <c:when test="${not empty user}">
                    <c:if test="${role eq 1}">
                        <a href="${pageContext.request.contextPath}/controller?command=show_users">
                            <fmt:message key="admin.main"/> </a>
                    </c:if>
                    <a href="${pageContext.request.contextPath}/controller?command=view-user-profile" >Profile</a>
                    <a href="${pageContext.request.contextPath}/controller?command=logout">
                        <fmt:message key="header.logout"/></a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/jsp/login.jsp"><fmt:message key="header.login"/></a>
                </c:otherwise>
            </c:choose>
            <div class="indicator"></div>
        </nav>
    </header>
</div>
</body>
</html>
