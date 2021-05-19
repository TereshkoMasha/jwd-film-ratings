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
    <title>MovieRating</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/about.css">
    <link rel="stylesheet" type="text/css" href="../css/button.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/home_page.js" async></script>
</head>

<header>
    <div class="search-wrapper">
        <form>
            <input type="text" name="focus" required class="search-box" placeholder="Enter search film"/>
        </form>
    </div>

    <ul class="menu">
        <nav>
            <li><a href="login.jsp"><fmt:message key="menu.films"/></a></li>
            <c:choose>
                <c:when test="${not empty user}">
                    <li><a href="#"><fmt:message key="header.user"/></a></li>
                    <li><a href="${pageContext.request.contextPath}/controller?command=logout"><fmt:message
                            key="header.logout"/></a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="login.jsp"><fmt:message key="header.login"/></a></li>
                </c:otherwise>
            </c:choose>
        </nav>
        <div class="indicator"></div>
    </ul>
</header>
<div class="info">
    <div class="col">
        <div class="category-wrap" style="left: 119.4px;">
            <h3><fmt:message key="menu.films"/></h3>
            <ul>
                <li><a href="">Новинки</a></li>
            </ul>
        </div>
    </div>
</div>


<div class="info">
    <div class="col">
        <p><img src="../img/wolfwalkers-posters.jpg"></p>
    </div>
    <div class="col-menu">
        <div class="col"><p><img src="../img/wolfwalkers-posters.jpg"></p></div>
        <div class="col"><p><img src="../img/wolfwalkers-posters.jpg"></p></div>
        <div class="col"><p><img src="../img/wolfwalkers-posters.jpg"></p></div>
        <div class="col"><p><img src="../img/wolfwalkers-posters.jpg"></p></div>
        <div class="col"><p><img src="../img/wolfwalkers-posters.jpg"></p></div>
    </div>

</div>

<footer></footer>

</body>
</html>

