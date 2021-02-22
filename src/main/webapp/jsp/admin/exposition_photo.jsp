<%--
  Created by IntelliJ IDEA.
  User: petrenko
  Date: 12.02.21
  Time: 13:30
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <title>Exposition Photo</title>
    <link href="<c:url value="/css/bootstrap.min.css" />" type="text/css" rel="stylesheet"/>
    <link type="text/css" rel="stylesheet" href="css/main.css">
    <!-- Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500" rel="stylesheet">
</head>
<body class="admin-page exposition-photo">
<div class="head-content">
    <div class="form-wrapper-left">
        <form action="controller" method="get">
            <input type="hidden" name="command" value="listExpositions">
            <fmt:message bundle="${local}" var="expositions_label" key="admin.button_labels.expositions" />
            <button class="btn btn-primary btn-block" type="submit"><c:out value="${expositions_label}"></c:out></button>
        </form>
        <form action="controller" method="get">
            <input type="hidden" name="command" value="listHalls">
            <fmt:message bundle="${local}" var="halls_label" key="admin.button_labels.halls" />
            <button class="btn btn-primary btn-block" type="submit"><c:out value="${halls_label}"></c:out></button>
        </form>
        <form action="controller" method="get">
            <input type="hidden" name="command" value="listUsers">
            <fmt:message bundle="${local}" var="users_label" key="admin.button_labels.users" />
            <button class="btn btn-primary btn-block" type="submit"><c:out value="${users_label}"></c:out></button>
        </form>
    </div>
    <div class="form-wrapper-right">
        <tags:logout userLogin="${user.login}" userName="${user.name}" curr_lang="${locale}"/>
        <%--<tags:language curr_lang="${locale}" curr_uri="${pageContext.request.requestURI}"/>--%>
    </div>
</div>
<main>
    <div class="container admin-block">
        <fmt:message bundle="${local}" var="exposition_theme" key="exposition_photo.header.exposition_theme" />
        <fmt:message bundle="${local}" var="ticket_price" key="exposition_photo.header.ticket_price" />
        <fmt:message bundle="${local}" var="tickets_count" key="exposition_photo.header.tickets_count" />
        <fmt:message bundle="${local}" var="start_date" key="exposition_photo.header.start_date" />
        <fmt:message bundle="${local}" var="end_date" key="exposition_photo.header.end_date" />
        <fmt:message bundle="${local}" var="exposition_halls" key="exposition_photo.header.exposition_halls" />
        <div class="left-column">
            <img src="<c:out value="img/"/>${exposition.imgName}" alt="Exposition Photo">
        </div>
        <div class="right-column">
            <div class="exposition-theme">
                <span>${exposition_theme}:</span><span> <c:out value="${exposition.theme }" /></span>
            </div>
            <div class="ticket-price">
                <span>${ticket_price}: </span><span><c:out value="${exposition.ticketPrice}"/></span>
            </div>
            <div class="tickets-count">
                <span>${tickets_count}:</span><span><c:out value="${exposition.ticketsCount}"/></span>
            </div>
            <div class="date-in">
                <span>${start_date}: </span><span> <c:out value="${exposition.dateIn}"/></span>
            </div>
            <div class="date-out">
                <span>${end_date}: </span><span><c:out value="${exposition.dateOut}"/></span>
            </div>
            <div class="halls">
                <span>${exposition_halls}: </span><span><c:out value="${exposition.hallsNames }"/></span>
            </div>
        </div>

        <%--<h4 style="text-align: left;">${exposition_theme}: <c:out value="${exposition.theme }" /></h4>--%>
        <%--<h5 style="text-align: left;">${ticket_price}: <c:out value="${exposition.ticketPrice}"/></h5>--%>
        <%--<h5 style="text-align: left;">${tickets_count}: <c:out value="${exposition.ticketsCount}"/></h5>--%>
        <%--<h5 style="text-align: left;">${start_date}: <c:out value="${exposition.dateIn}"/></h5>--%>
        <%--<h5 style="text-align: left;">${end_date}: <c:out value="${exposition.dateOut}"/></h5>--%>
        <%--<h5 style="text-align: left;">${exposition_halls}: <c:out value="${exposition.hallsNames }"/></h5>--%>

    </div>
</main>


<script src="<c:url value="/js/jquery-3.5.1.min.js" />"></script>
<script src="<c:url value="/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/js/main.js" />"></script>
</body>
</html>
