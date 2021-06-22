<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<c:choose>
    <c:when test="${not empty sessionScope.locale}">
        <fmt:setLocale value="${sessionScope.locale}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="ru_RU"/>
    </c:otherwise>
</c:choose>
<fmt:setBundle basename="Locale"/>

<html lang="${sessionScope.locale}">
<head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
</head>
<body>
<div class="footer"></div>
</body>
</html>
