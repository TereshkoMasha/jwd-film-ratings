<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css">
    <title>Error</title>
</head>
<body>
<div id="main">
    <div class="fof">
        <h1><c:out value="${error}"/></h1>
        <h3><a href="${pageContext.request.contextPath}/controller?command=main&page=1">Перейти на главную</a></h3>
    </div>
</div>
</body>
</html>
