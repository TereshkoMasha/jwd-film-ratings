<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<c:choose>
    <c:when test="${not empty locale}">
        <fmt:setLocale value="${locale}" scope="session"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en_US" scope="session"/>
    </c:otherwise>
</c:choose>
<fmt:setBundle basename="Locale" scope="session"/>

<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
    <title>Error</title>
</head>
<body>
<div id="main">
    <div class="fof">
        <c:choose>
            <c:when test="${error ne null}">
                <h1><fmt:message key="${error}"/></h1>
            </c:when>
            <c:otherwise>
                <h1><fmt:message key="error.message.404"/></h1>
            </c:otherwise>
        </c:choose>
        <h3><a href="${pageContext.request.contextPath}/controller?command=main&page=1">
            <fmt:message key="error.main.page"/>
        </a></h3>
    </div>
</div>
</body>
</html>
