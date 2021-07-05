<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="pag-tag" uri="http://mypagtag" %>
<!DOCTYPE>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/profile.css">
    <title>Profile</title>
</head>
<body>
<jsp:include page="header.jsp"/>
<body class="profile-page sidebar-collapse">
<div class="wrapper">
    <div class="page-header clear-filter" filter-color="orange">
        <div class="page-header-image" data-parallax="true"
             style="background-image:url('https://images.unsplash.com/photo-1557053819-aa6046add523?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=889&q=80');">
        </div>
        <div class="container">
            <div class="photo-container">
                <a><img src="${pageContext.request.contextPath}/img/image_2021-07-04_23-53-52.png" alt=""></a>
            </div>
            <h3 class="title">${user.name}</h3>
            <p class="category">${user.role} - ${user.status}</p>
            <div class="content">
                <div class="social-description">
                    <h2>${review.size()}</h2>
                    <p>Reviews</p>
                </div>
            </div>
        </div>
    </div>
    <div class="section">
        <div class="container">
            <h3 class="title">Comments</h3>
            <c:forEach var="review" items="${review}" begin="${page}" end="${page + 2}">
                <div class="comment-block">
                    <p class="comment-text" style="margin-left: 0px"><c:out value="${review.text}"/></p>
                    <div class="bottom-comment">${user.name} - ${review.rating.id}</div>
                </div>
                <br>
            </c:forEach>
            <div class="pagination"><pag-tag:pag-tag reviewOnProfileListSize="${review.size()}"/></div>
        </div>
        <br><br><br>
        <jsp:include page="footer.jsp"/>
    </div>
</div>
</body>
</html>
