<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta charset="utf-8">
    <title><c:out value="${movie.name}"/></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/movie.css">
    <script src="${pageContext.request.contextPath}/js/rating.js" async></script>
</head>

<c:choose>
    <c:when test="${not empty sessionScope.locale}">
        <fmt:setLocale value="${sessionScope.locale}" scope="session"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en_US" scope="session"/>
    </c:otherwise>
</c:choose>
<fmt:setBundle basename="Locale"/>

<body>
<c:import url="header.jsp"/>
<div class="movie-container">
    <c:if test="${sessionScope.review == null}">
        <form id="jsform" method="post"
              action=" ${pageContext.request.contextPath}/controller?command=movie-info&id=${movie.id}" hidden>
        </form>
        <script type="text/javascript">
            document.getElementById('jsform').submit();
        </script>
    </c:if>
    <div class="movie-card">
        <a href="#"><img src="${pageContext.request.contextPath}${movie.poster}" alt="${movie.name}"
                         class="cover"></a>
        <div class="hero">
            <div class="details">
                <div class="title1">
                    <c:out value="${movie.name} (${movie.releaseYear})"/>
                    <c:if test="${not empty rating}">
                        <c:out value="${rating}"/>
                    </c:if>
                </div>
                <div class="title2">
                    <c:out value="${movie.tagline}"/>
                </div>
                <c:if test="${movie.tagline == null}">
                    <br>
                </c:if>
                <div class="title3"><fmt:message key="movie.about"/></div>
            </div>
        </div>
        <div class="description">
            <div class="column2">
                <table cellpadding="0" cellspacing="0" border="0">
                    <tbody>
                    <tr>
                        <td><fmt:message key="movie.release.year"/></td>
                        <td><c:out value="${movie.releaseYear}"/></td>
                    </tr>
                    <tr>
                        <td><fmt:message key="movie.genre"/></td>
                        <td><c:out value="${fn:toLowerCase(movie.genre)}"/></td>
                    </tr>
                    <tr>
                        <td><fmt:message key="movie.tagline"/></td>
                        <c:choose>
                            <c:when test="${not empty movie.tagline}">
                                <td><c:out value="${movie.tagline}"/></td>
                            </c:when>
                            <c:otherwise>
                                <td>-</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                    <tr>
                        <td><fmt:message key="movie.cast"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty actors}">
                                    <c:forEach var="actor" items="${actors}" varStatus="loop">
                                        <c:choose>
                                            <c:when test="${loop.count eq actors.size()}">
                                                <c:out value="${actor.name}"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${actor.name},"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>-</c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td><fmt:message key="movie.director"/></td>
                        <c:choose>
                            <c:when test="${not empty director}">
                                <td><c:out value="${director.name}"/></td>
                            </c:when>
                            <c:otherwise>
                                <td> -</td>
                            </c:otherwise>
                        </c:choose>
                    </tr>
                    <tr>
                        <td><fmt:message key="movie.duration"/></td>
                        <td><c:out value="${movie.duration}"/></td>
                    </tr>
                    <tr>
                        <td><fmt:message key="movie.country"/></td>
                        <td><c:out value="${movie.country.name}"/></td>
                    </tr>
                    </tbody>
                </table>
            </div> <!-- end column2 -->
        </div> <!-- end description -->
    </div> <!-- end container -->
</div>
<div class="comment-container">
    <div class="about-column">
        <h2><fmt:message key="movie.description"/></h2>
        <p><c:out value="${movie.description}"/></p>
        <h2><fmt:message key="movie.comment"/></h2>
        <c:choose>
            <c:when test="${not empty review}">
                <c:choose>
                    <c:when test="${page eq 1}">
                        <c:set var="count" value="${0}"></c:set>
                    </c:when>
                    <c:otherwise>
                        <c:set var="count" value="${page*3 - 3}"></c:set>
                    </c:otherwise>
                </c:choose>
                <c:forEach var="review" items="${review}" begin="${count}" end="${count + 2}" varStatus="status">
                    <c:if test="${not empty review.text}">
                        <div class="comment-block">
                            <p class="comment-text" style="margin-left: 0px"><c:out value="${review.text}"/></p>
                            <div class="bottom-comment">${users[status.index].name} - ${review.rating.id}</div>
                        </div>
                        <br>
                    </c:if>
                </c:forEach>
                <c:if test="${review.size() > 1}">
                    <div class="pagination p2">
                        <ul>
                            <c:forEach begin="1" end="${movies.size()/3}" varStatus="loop">
                                <a href="${pageContext.request.contextPath}/controller?command=movie-info&page=${loop.count}&id=${movie.id}">
                                    <li>${loop.count}</li>
                                </a>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>
            </c:when>
            <c:otherwise>
                <p class="comment-text"><fmt:message key="movie.first.comment"/></p>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${not empty user}">
                <c:choose>
                    <c:when test="${user.status.id != 0}">
                        <form method="post"
                              action="${pageContext.request.contextPath}/controller?command=leave-comment&id=${movie.id}">
                            <h2><fmt:message key="movie.leave.comment"/></h2>
                            <textarea placeholder="Type here" rows="20" name="comment_text" id="comment_text" cols="40"
                                      class="ui-autocomplete-input" autocomplete="off" role="textbox"
                                      aria-autocomplete="list"
                                      aria-haspopup="true"></textarea>
                            <br>
                            <fieldset class="rating" style="margin-left: 45px" aria-required="true">
                                <input type="radio" id="star5" required name="rating" value="5"/><label class="full"
                                                                                                        for="star5"
                                                                                                        title="Awesome - 5 stars"></label>
                                <input type="radio" id="star4" required name="rating" value="4"/><label class="full"
                                                                                                        for="star4"
                                                                                                        title="Pretty good - 4 stars"></label>

                                <input type="radio" id="star3" required name="rating" value="3"/><label class="full"
                                                                                                        for="star3"
                                                                                                        title="Meh - 3 stars"></label>

                                <input type="radio" id="star2" required name="rating" value="2"/><label class="full"
                                                                                                        for="star2"
                                                                                                        title="Kinda bad - 2 stars"></label>

                                <input type="radio" id="star1" required name="rating" value="1"/><label class="full"
                                                                                                        for="star1"
                                                                                                        title="Bad - 1 star"></label>
                            </fieldset>
                            <button type="submit" style="margin-left: 400px; margin-top: 5px; margin-bottom: 90px">
                                <fmt:message key="movie.review"/></button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <p style="margin-bottom: 30px"><fmt:message key="user.ban"/></p>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <br>
                <br>
                <p style="margin-bottom: 120px"><fmt:message key="movie.first.comment.reg"/></p>
            </c:otherwise>
        </c:choose>
        <jsp:include page="footer.jsp"/>
    </div>
</div><!-- end movie-container -->
</body>
</html>
