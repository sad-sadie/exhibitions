<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test="${empty language}">
    <c:set var="language" scope="session" value="${pageContext.request.locale.language}"/>
</c:if>
<c:if test="${!empty language}">
    <fmt:setLocale value="${language}" scope="session"/>
</c:if>

<fmt:setBundle basename="resources"/>


<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Exhibitions</title>

    <style>
        body {
            margin: 0;
            font-family: Arial, Helvetica, sans-serif;
        }

        h1 {
            text-align: center;
        }

        .topnav.logged_user {
            padding: 6px;
            margin-top: 8px;
            font-size: 17px;
            border: none;
            width: 270px;
        }

        .topnav {
            overflow: hidden;
            background-color: #e9e9e9;
        }

        .topnav a {
            float: left;
            display: block;
            color: black;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
            font-size: 17px;
        }

        .topnav a:hover {
            background-color: #ddd;
            color: black;
        }

        .topnav a.active {
            background-color: #3d5d8f;
            color: white;
        }

        .change-language {
            float: right;
        }

        .topnav .login-container {
            float: right;
        }

        .topnav input[type=text] {
            padding: 6px;
            margin-top: 8px;
            font-size: 17px;
            border: none;
            width: 120px;
        }

        .topnav input[type=password] {
            padding: 6px;
            margin-top: 8px;
            font-size: 17px;
            border: none;
            width: 120px;
        }

        .topnav .login-container button {
            float: right;
            padding: 6px 10px;
            margin-top: 8px;
            margin-right: 16px;
            background-color: #555;
            color: white;
            font-size: 17px;
            border: none;
            cursor: pointer;
        }

        .topnav .login-container button:hover {
            background-color: #3d5d8f;
        }

        .exhibitions_container button {
            background-color: #3d5d8f;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            cursor: pointer;
            width: 100%;
        }

        .exhibitions_container {
            padding: 50px 20%;
        }


        .exhibitions td {
            font-size: 13pt;
            padding: 5px 10px;

            border: 10px groove;
            border-color: #404040 #919191 #404040 #919191;
        }

        .exhibitions table {
            caption-side: top;

        }

        .pagination {
            position: relative;
            margin-bottom: 100px;
        }

        .pagination-inner {
            position: absolute;
            padding-left: 50%;
        }

        .pagination a {
            background-color: #d9d9d9;
            float: left;
            margin-right: 5px;
            display: block;
            color: black;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
            font-size: 17px;
        }

        .pagination a:hover {
            background-color: #b0b0b0;
            color: black;
        }

        .pagination a.active {
            background-color: green;
            color: white;
        }
    </style>

</head>
<body>
<div class="topnav">
    <a class="active" href="../home?command=getHomePage"><fmt:message key='topnav.menu.home'/></a>
    <a href="view/registration.jsp"><fmt:message key='topnav.menu.registration'/></a>


    <c:if test="${sessionScope.user.role == 'ADMIN'}">
        <a href="view/addExhibition.jsp"><fmt:message key='topnav.menu.addExhibition'/></a>
        <a href="view/addHall.jsp"><fmt:message key='topnav.menu.addHall'/></a>
        <a href="../home?command=getStatistics"><fmt:message key='topnav.menu.exhibitionStatistics'/></a>
    </c:if>

    <c:choose>
        <c:when test="${sessionScope.user == null}">
            <div class="login-container">
                <form action="../home" method="get">
                    <input name="command" type="hidden" value="logIn">
                    <input type="text" placeholder="<fmt:message key='topnav.input.login'/>" name="login">
                    <input type="password" placeholder="<fmt:message key='topnav.input.password'/>" name="password">
                    <button type="submit"><fmt:message key='topnav.button.login'/></button>
                </form>
            </div>
        </c:when>
        <c:otherwise>
            <div class="login-container">
                <form action="../home" method="get">
                    <input name="command" type="hidden" value="logOut">
                    <button type="submit"><fmt:message key='topnav.button.logOut'/></button>
                </form>
                <div class="logged_user"><fmt:message key='topnav.info.loggedAs'/> ${sessionScope.user.login}</div>
            </div>
        </c:otherwise>
    </c:choose>

    <div class="change-language">
        <c:choose>
            <c:when test="${sessionScope['javax.servlet.jsp.jstl.fmt.locale.session'] eq 'en'}">
                <a class="active" href="">ENG</a>
            </c:when>
            <c:otherwise>
                <a href="../home?command=chooseLanguage&language=en">ENG</a>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${sessionScope['javax.servlet.jsp.jstl.fmt.locale.session'] eq 'uk'}">
                <a class="active" href="">УКР</a>
            </c:when>
            <c:otherwise>
                <a href="../home?command=chooseLanguage&language=uk">УКР</a>
            </c:otherwise>
        </c:choose>

    </div>
</div>

<div class="exhibitions_container">
    <c:if test="${exhibitions != null}">

        <c:set var="language" scope="page" value="${sessionScope['javax.servlet.jsp.jstl.fmt.locale.session']}" />

        <c:set var="i" value="1"/>

        <table align="center" class="exhibitions" cellspacing="9">
            <c:forEach var="exhibition" items="${exhibitions}">

                <tr>
                    <td><b>${exhibition.theme}</b><br>
                            ${exhibition.description} <br>

                        <br><b><fmt:message key='index.exhibition.time'/>: <fmt:message
                                key='index.exhibition.from'/></b> ${exhibition.startDate}
                        <b><fmt:message
                                key='index.exhibition.to'/></b> ${exhibition.endDate} ${exhibition.startTime}-${exhibition.endTime} <br>
                        <b><fmt:message key='index.exhibition.place'/>:</b>
                        <c:forEach var="hall" items="${exhibition.getHalls()}">
                            ${hall.name} <br>
                        </c:forEach>

                        <b><fmt:message key='index.exhibition.price'/>:</b> ${exhibition.price} <fmt:message
                                key='index.exhibition.uah'/>
                        <br><br>
                        <c:if test="${user.role == 'ADMIN' || user.role == 'USER'}">
                            <form action="home" method="post">
                                <input name="command" type="hidden" value="buyTicket">
                                <input name="exhibitionId" type="hidden" value="${exhibition.id}">
                                <button type="submit"><fmt:message key='index.exhibition.buyTickets'/></button>
                            </form>
                        </c:if>

                        <c:if test="${user.role == 'ADMIN'}">
                            <form action="home" method="get">
                                <input name="command" type="hidden" value="cancelExhibition">
                                <input name="canceledExhibitionId" type="hidden" value="${exhibition.id}">
                                <button type="submit"><fmt:message
                                        key='index.exhibition.cancelExhibition'/></button>
                            </form>
                        </c:if>
                    </td>
                </tr>

                <c:set var="i" value="${i+1}"/>

            </c:forEach>

        </table>
    </c:if>
    <div class="pagination">
        <div class="pagination-inner">
            <c:forEach var="i" begin="1" end="${numberOfPages}">
                <c:choose>
                    <c:when test="${currentPage == i}">
                        <a class="active" href="home?command=getExhibitions&pageNum=${i}&sortType=${param.sortType}">${i}</a>
                    </c:when>
                    <c:otherwise>
                        <a href="home?command=getExhibitions&pageNum=${i}&sortType=${param.sortType}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>
