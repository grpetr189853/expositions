<%--
  Created by IntelliJ IDEA.
  User: petrenko
  Date: 07.02.21
  Time: 17:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:choose>
    <c:when test="${locale == 'ru'}">
        <fmt:setLocale value="ru"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="en"/>
    </c:otherwise>
</c:choose>
<fmt:setBundle basename="local" var="local"/>
<html>
<head>
    <title>Admin Create Hall</title>
    <link href="<c:url value="/css/bootstrap.min.css" />" type="text/css" rel="stylesheet"/>
    <link type="text/css" rel="stylesheet" href="css/main.css">
</head>
<body class="admin-page">
<div class="head-content">
    <div class="form-wrapper-left">
        <form action="controller" method="get">
            <fmt:message bundle="${local}" var="expositions_label" key="admin.button_labels.expositions"/>
            <input type="hidden" name="command" value="listExpositions">
            <button class="btn btn-primary btn-block" type="submit"><c:out
                    value="${expositions_label}"></c:out></button>
        </form>
        <form action="controller" method="get">
            <fmt:message bundle="${local}" var="halls_label" key="admin.button_labels.halls"/>
            <input type="hidden" name="command" value="listHalls">
            <button class="btn btn-primary btn-block" type="submit"><c:out value="${halls_label}"></c:out></button>
        </form>
        <form action="controller" method="get">
            <fmt:message bundle="${local}" var="users_label" key="admin.button_labels.users"/>
            <input type="hidden" name="command" value="listUsers">
            <button class="btn btn-primary btn-block" type="submit">${users_label}</button>
        </form>
    </div>
    <div class="form-wrapper-right">
        <tags:logout userLogin="${user.login}" userName="${user.name}" curr_lang="${locale}"/>
    </div>
</div>
<main>
    <div class="admin-block">
        <c:choose>
            <c:when test="${showCreateHall}">
                <form action="controller" method="post" id="hallForm">
                    <div class="mb-3 row">
                        <fmt:message bundle="${local}" var="create_hall" key="create_hall.button_label"/>
                        <label for="hall_name">${create_hall}</label>
                        <input type="text" name="hall_name" class="form-control" id="hall_name">
                    </div>
                    <input type="submit" class="btn btn-primary btn-block">
                    <input type="hidden" name="command" value="createHall">
                    <input type="hidden" name="sendRedirect" value="true">
                </form>
            </c:when>
        </c:choose>
    </div>
</main>

<script src="<c:url value="/js/jquery-3.5.1.min.js" />"></script>
<script src="<c:url value="/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/js/main.js" />"></script>
</body>
</html>
