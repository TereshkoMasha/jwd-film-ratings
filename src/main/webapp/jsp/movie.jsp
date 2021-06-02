<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Movie</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/movie.css">
    <script src="${pageContext.request.contextPath}/js/rating.js" async></script>
</head>

<body>
<c:import url="header.jsp"/>
<div class="movie-container">
    <div class="movie-card">
        <div class="container-movie">
            <a href="#"><img src="${pageContext.request.contextPath}${film.poster}" alt="${film.name}"
                             class="cover"></a>
            <div class="hero">
                <div class="details">
                    <div class="title1">
                        <c:out value="${film.name} (${film.releaseYear})"/>
                    </div>
                    <div class="title2">
                        <c:out value="${film.tagline}"/>
                    </div>
                    <c:if test="${film.tagline == null}">
                        <br>
                    </c:if>
                    <div class="title3">
                        О фильме
                    </div>
                </div>
            </div>
            <div class="description">
                <div class="column2">
                    <table cellpadding="0" cellspacing="0" border="0">
                        <tbody>
                        <tr>
                            <td>Год производства</td>
                            <td><c:out value="${film.releaseYear}"/></td>
                        </tr>
<%--                        <tr>--%>
<%--                            <td>Страна</td>--%>
<%--                            <td><c:out value="${film.releaseCountry.name}"/></td>--%>
<%--                        </tr>--%>
                        <tr>
                            <td>Жанр</td>
                            <td><c:out value="${film.genre}"/></td>
                        </tr>
                        <tr>
                            <td>Слоган</td>
                            <td><c:out value="${film.tagline}"/></td>
                        </tr>
                        <%--                    <tr>--%>
                        <%--                        <td>Режиссер</td>--%>
                        <%--                        <td><c:out value="${films[0].director}"/></td>--%>
                        <%--                    </tr>--%>
                        <tr>
                            <td>Время</td>
                            <td><c:out value="${film.duration}"/></td>
                        </tr>
                        </tbody>
                    </table>
                </div> <!-- end column2 -->
            </div> <!-- end description -->
        </div> <!-- end container -->
    </div> <!-- end movie-card -->
    <h2>Оставьте свой отзыв</h2>
    <textarea placeholder="Type here" rows="20" name="comment[text]" id="comment_text" cols="40"
              class="ui-autocomplete-input" autocomplete="off" role="textbox" aria-autocomplete="list"
              aria-haspopup="true">
    </textarea>
</div>
</div>
<br>

</body>
</html>
