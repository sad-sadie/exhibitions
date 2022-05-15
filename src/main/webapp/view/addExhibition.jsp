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
    <title>Add exhibition</title>

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

        .add_exhibition_container {
            padding: 50px 30%;
        }

        .add_exhibition_container form {
            border: 3px solid #f1f1f1;
        }

        .add_exhibition_container input[type=text], .add_exhibition_container input[type=date],
        .add_exhibition_container input[type=time], .add_exhibition_container input[type=number],
        .add_exhibition_container input[type=file], .add_exhibition_container select {
            width: 100%;
            padding: 12px 20px;
            margin: 8px 0;
            display: inline-block;
            border: 1px solid #e6f0eb;
            box-sizing: border-box;
        }

        .add_exhibition_container option {
            background-color: #ccc;
            color: white;
            font-size: 14px;
        }

        .add_exhibition_container button {
            background-color: #3d5d8f;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            cursor: pointer;
            width: 100%;
        }

        .add_exhibition_container button:hover {
            opacity: 0.8;
        }

    </style>

</head>
<body>
<div class="topnav">
    <a class="active" href="../home?command=getHomePage"><fmt:message key='topnav.menu.home'/></a>
    <a href="registration.jsp"><fmt:message key='topnav.menu.registration'/></a>


    <c:if test="${sessionScope.user.role == 'ADMIN'}">
        <a href="addExhibition.jsp"><fmt:message key='topnav.menu.addExhibition'/></a>
        <a href="addHall.jsp"><fmt:message key='topnav.menu.addHall'/></a>
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
<h1><fmt:message key='addExhibition.topic'/></h1>
<hr>
<form action="../home" method="post" enctype="multipart/form-data">
    <div class="add_exhibition_container">
        <input name="command" type="hidden" value="addExhibition">

        <label><fmt:message key='addExhibition.form.theme'/></label>
        <input type="text" name="theme" placeholder="<fmt:message key='addExhibition.form.enterTheme'/>">

        <label><fmt:message key='addExhibition.form.description'/></label>
        <input type="text" name="description" placeholder="<fmt:message key='addExhibition.form.enterDescription'/>">

        <label><fmt:message key='addExhibition.form.startDate'/></label>
        <input type="date" name="startDate">

        <label><fmt:message key='addExhibition.form.endDate'/></label>
        <input type="date" name="endDate">

        <label><fmt:message key='addExhibition.form.startTime'/></label>
        <input type="time" name="startTime">

        <label><fmt:message key='addExhibition.form.endTime'/></label>
        <input type="time" name="endTime">

        <label><fmt:message key='addExhibition.form.price'/></label>
        <input type="number" name="price" step="0.01" placeholder="0.00" min="0.01">

        <label><fmt:message key='addExhibition.form.halls'/></label><br>
        <select name="chosenHalls" multiple="multiple">
            <c:forEach items="${halls}" var="hall">
                <option value="${hall.id}">${hall.name}</option>
                <br>
            </c:forEach>

        </select><br>

        <button type="submit"><fmt:message key='addExhibition.form.createExhibition'/></button>
    </div>
</form>

    <c:if test="${sessionScope.error == 'exhibitionExists'}">
        <script>
            alert("exhibition with such theme already exists")
        </script>
        ${sessionScope.error = null}
    </c:if>
    <c:if test="${sessionScope.error == 'exhibitionTheme'}">
        <script>
            alert("wrong theme input")
        </script>
        ${sessionScope.error = null}
    </c:if>
    <c:if test="${sessionScope.error == 'exhibitionDescription'}">
        <script>
            alert("wrong description input")
        </script>
        ${sessionScope.error = null}
    </c:if>
    <c:if test="${sessionScope.error == 'exhibitionPrice'}">
        <script>
            alert("wrong price input")
        </script>
        ${sessionScope.error = null}
    </c:if>
    <c:if test="${sessionScope.error == 'exhibitionStartDate'}">
        <script>
            alert("wrong start date input")
        </script>
        ${sessionScope.error = null}
    </c:if>
    <c:if test="${sessionScope.error == 'exhibitionEndDate'}">
        <script>
            alert("wrong end date input")
        </script>
        ${sessionScope.error = null}
    </c:if>
    <c:if test="${sessionScope.error == 'exhibitionDates'}">
        <script>
            alert("wrong dates input")
        </script>
        ${sessionScope.error = null}
    </c:if>
    <c:if test="${sessionScope.error == 'exhibitionTimes'}">
        <script>
            alert("wrong times input")
        </script>
        ${sessionScope.error = null}
    </c:if>
    <c:if test="${sessionScope.error == 'exhibitionStartTime'}">
        <script>
            alert("wrong start time input")
        </script>
        ${sessionScope.error = null}
    </c:if>
    <c:if test="${sessionScope.error == 'exhibitionEndTime'}">
        <script>
            alert("wrong end time input")
        </script>
        ${sessionScope.error = null}
    </c:if>
    <c:if test="${sessionScope.error == 'exhibitionHalls'}">
        <script>
            alert("wrong halls input")
        </script>
        ${sessionScope.error = null}
    </c:if>

</body>
</html>
