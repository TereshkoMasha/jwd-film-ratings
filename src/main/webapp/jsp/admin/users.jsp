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
    <title>Users</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/users.css">
</head>
<body>
<c:import url="../header.jsp"/>
<br>
<section>
    <div class="tbl-header">
        <table cellpadding="0" cellspacing="0" border="0">
            <thead>
            <tr>
                <th>№</th>
                <th>Username</th>
                <th>Name</th>
                <th>Email</th>
                <th>Rating</th>
                <th>Status</th>
                <th>Role</th>
            </tr>
            </thead>
        </table>
    </div>
    <div class="tbl-content">
        <c:set var="count" value="0" scope="page" />
        <table cellpadding="0" cellspacing="0" border="0">
            <tbody>

            <c:forEach var="user" items="${users_list}">
                <tr>
                    <c:set var="count" value="${count + 1}" scope="page"/>
                    <td>${count}</td>
                    <td>${user.login}</td>
                    <td>${user.name}</td>
                    <td>${user.email}</td>
                    <td>${user.rating}</td>
                    <td>${user.status}</td>
                    <td>${user.role}</td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>
</section>
</body>
</html>
