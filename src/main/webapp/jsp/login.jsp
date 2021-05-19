<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:choose>
    <c:when test="${not empty sessionScope.locale}">
        <fmt:setLocale value="${sessionScope.locale}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="ru_RU"/>
    </c:otherwise>
</c:choose>
<fmt:setBundle basename="locale"/>

<html lang="${sessionScope.locale}">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="../css/login.css">
</head>
<body>
<header>
    <nav>
        <ul class="menu">
            <li>
                <a href="${pageContext.request.contextPath}/controller?command=main"><fmt:message
                        key="header.main"/></a>
            </li>
        </ul>
    </nav>
</header>
<div class="container">
    <div class="login-box">
        <h2>Login</h2>
        <form method="post" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="login">
            <div class="user-box">
                <input type="text" name="login">
                <label><fmt:message key="main.login"/></label>
            </div>
            <div class="user-box">
                <input type="password" name="password">
                <label><fmt:message key="main.password"/></label>
            </div>
            <a href="#">
                <span></span>
                <span></span>
                <span></span>
                <span></span>
                <button><fmt:message key="header.login"/></button>
            </a>
        </form>
    </div>
</div>
</body>
<footer>
</footer>
</html>