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
    <title></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <script src="${pageContext.request.contextPath}/js/header.js" async></script>
    <script src="${pageContext.request.contextPath}/js/search.js" async></script>
</head>
<body>
<div>
    <header class="header" id="logo-menu">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/controller?command=main">Film Ratings</a>
        </div>
        <div class="search-wrapper">
            <form method="post" action="${pageContext.request.contextPath}/controller?command=film-search">
                <input type="text" name="film" required class="search-box" placeholder="Enter search film"/>
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
                    <a href="#"><fmt:message key="header.user"/></a>
                    <a href="${pageContext.request.contextPath}/controller?command=logout">
                        <fmt:message key="header.logout"/></a>
                </c:when>
                <c:otherwise>
                    <a href="login.jsp"><fmt:message key="header.login"/></a>
                </c:otherwise>
            </c:choose>
            <div class="indicator"></div>
        </nav>
    </header>
</div>
</body>
</html>
