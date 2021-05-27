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
            <a href="">Film Ratings</a>
        </div>
        <div class="search-wrapper">
            <form>
                <input type="text" name="focus" required class="search-box" placeholder="Enter search film"/>
            </form>
        </div>
        <nav class="menu">
            <a href="${pageContext.request.contextPath}/controller?command=main"><fmt:message key="header.main"/></a>
            <c:choose>
                <c:when test="${not empty user}">
                    <a href="#"><fmt:message key="header.user"/></a>
                    <a href="${pageContext.request.contextPath}/controller?command=logout"><fmt:message
                            key="header.logout"/></a>
                </c:when>
                <c:otherwise>
                    <a href="#login"><fmt:message key="header.login"/></a>
                </c:otherwise>
            </c:choose>
            <div class="indicator"></div>
        </nav>
    </header>

    <div id="login">
        <div class="login-triangle"></div>
        <h2 class="login-header"><fmt:message key="header.login"/></h2>
        <form method="post" action="${pageContext.request.contextPath}/controller" class="login-container">
            <input type="hidden" name="command" value="login">
            <p><label>
                <input type="text" name="login" placeholder="Username">
            </label></p>
            <p><label>
                <input type="password" name="password" placeholder="Password">
            </label></p>
            <p><input type="submit" value="Log in"></p>
        </form>
    </div>


</div>
</body>
</html>
