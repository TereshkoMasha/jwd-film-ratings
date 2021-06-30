<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:choose>
    <c:when test="${not empty sessionScope.locale}">
        <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en_US" scope="session"/>
    </c:otherwise>
</c:choose>
<fmt:setBundle basename="Locale"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <meta charset="utf-8">
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
                    <input id="user" type="text" name="login" required class="input" value="${fn:escapeXml(login)}">
                </div>
                <div class="group">
                    <label for="pass" class="label"><fmt:message key="main.password"/></label>
                    <input id="pass" name="password" required type="password" class="input"
                           data-type="password" value="${fn:escapeXml(password)}">
                </div>
                <c:choose>
                    <c:when test="${not empty error}">
                        <p><fmt:message key="${error}"/></p>
                    </c:when>
                    <c:otherwise>
                        <p></p>
                    </c:otherwise>
                </c:choose>
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
                    <input id=type="text" class="input" name="login" required
                           pattern="^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\d.-]{0,19}$">
                </div>
                <div class="group">
                    <label for="user" class="label"><fmt:message key="register.firstName"/></label>
                    <input type="text" class="input" name="firstName" required>
                </div>
                <div class="group">
                    <label for="user" class="label"><fmt:message key="register.lastName"/></label>
                    <input type="text" class="input" name="lastName" required>
                </div>
                <div class="group">
                    <label for="pass" class="label"><fmt:message key="main.password"/></label>
                    <input type="password" class="input" data-type="password" name="password" required
                           pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,40}$" minlength="8" maxlength="40">
                </div>
                <br>
                <div class="group"><input type="submit" class="button" value="Sign Up"></div>
                <div class="hr"></div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
