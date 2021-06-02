<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <title>Film Ratings</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
<c:import url="header.jsp"/>
<div class="login-wrap">
    <div class="login-html">
        <input id="tab-1" type="radio" name="tab" class="sign-in" checked><label for="tab-1" class="tab"><fmt:message
            key="header.login"/></label>
        <input id="tab-2" type="radio" name="tab" class="sign-up"><label for="tab-2" class="tab"><fmt:message
            key="header.register"/></label>
        <div class="login-form">
            <form method="post" action="${pageContext.request.contextPath}/controller" class="sign-in-htm">
                <input type="hidden" name="command" value="login">
                <div class="group">
                    <label for="user" class="label"><fmt:message key="main.login"/></label>
                    <input id="user" type="text" name="login" required class="input">
                </div>
                <div class="group">
                    <label for="pass" class="label"><fmt:message key="main.password"/></label>
                    <input id="pass" name="password" required  type="password" class="input"
                           data-type="password">
                </div>
                <br><br>
                <div class="group">
                    <input type="submit" class="button" value="Sign In">
                </div>
                <div class="hr"></div>
            </form>
            <form method="post" action="${pageContext.request.contextPath}/controller" class="sign-up-htm">
                <input type="hidden" name="command" value="registration">
                <div class="group">
                    <label for="user" class="label"><fmt:message key="register.login"/></label>
                    <input id="user" type="text" class="input" name="login" required>
                </div>
                <div class="group">
                    <label for="user" class="label"><fmt:message key="register.firstName"/></label>
                    <input id="user" type="text" class="input" name="firstName" required>
                </div>
                <div class="group">
                    <label for="user" class="label"><fmt:message key="register.lastName"/></label>
                    <input id="user" type="text" class="input" name="lastName" required>
                </div>
                <div class="group">
                    <label for="pass" class="label"><fmt:message key="main.password"/></label>
                    <input id="pass" type="password" class="input" data-type="password" name="password" required
                           pattern="[0-9a-zA-Z]{4,8}">
                </div>
                <div class="group">
                    <label for="pass" class="label"><fmt:message key="register.email"/></label>
                    <input id="pass" type="text" class="input" name="email" required>
                </div>
                <br>
                <div class="group">
                    <input type="submit" class="button" value="Sign Up">
                </div>
                <div class="hr"></div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
