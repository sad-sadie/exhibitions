<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
  <title>Detailed Statistics</title>

  <style>
    body {
      margin: 0;
      font-family: Arial, Helvetica, sans-serif;
    }

    h1 {
      text-align: center;
    }


    .topnav .logged_user {
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

    .topnav .login-container {
      float: right;
    }

    .change-language {
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

    .detailed-statistic {
      margin-left: 30%;
      font-family: Arial, Helvetica, sans-serif;
      border-collapse: collapse;
      width: 40%;
    }

    .detailed-statistic td, .statistic th {
      border: 1px solid #ddd;
      padding: 8px;
    }

    .detailed-statistic tr:nth-child(even) {
      background-color: #f2f2f2;
    }

    .detailed-statistic tr:hover {
      background-color: #ddd;
    }

    .detailed-statistic th {
      padding-top: 12px;
      padding-bottom: 12px;
      text-align: left;
      background-color: #00bfff;
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
<h1><fmt:message key='detailedStatistics.topic'/> "${param.exhibitionTheme}"</h1>
<hr>

<table class="detailed-statistic">
    <tr>
        <th><fmt:message key='detailedStatistics.table.userLogin'/></th>
        <th><fmt:message key='detailedStatistics.table.numberOfBoughtTickets'/></th>
    </tr>
    <c:forEach var="stat" items="${detailedStats}" >
        <tr>
            <td>${stat.key}</td>
            <td>${stat.value}</td>
        </tr>
    </c:forEach>
</table>
<%--<customTag:detailedStatistics detailedStatistics="${detailedStatistics}">
  <th><fmt:message key='detailedStatistics.table.userLogin'/></th>
  <th><fmt:message key='detailedStatistics.table.numberOfBoughtTickets'/></th>
</customTag:detailedStatistics>--%>

</body>
</html>
