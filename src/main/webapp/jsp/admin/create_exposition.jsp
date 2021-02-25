<%--
  Created by IntelliJ IDEA.
  User: petrenko
  Date: 07.02.21
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="username" uri="/WEB-INF/tld/usernameDescriptor.tld" %>
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
    <title>Admin Create Exposition <username:usernameTagHandler userId="${user.userId}" lang="${locale}"/></title>
    <link href="<c:url value="/css/bootstrap.min.css" />" type="text/css" rel="stylesheet"/>
    <link type="text/css" rel="stylesheet" href="css/main.css">
</head>
<body class="admin-page">
<div class="head-content">

    <div class="form-wrapper-left">
        <form action="controller" method="get">
            <input type="hidden" name="command" value="listExpositions">
            <fmt:message bundle="${local}" var="expositions_label" key="admin.button_labels.expositions"/>
            <button class="btn btn-primary btn-block" type="submit"><c:out
                    value="${expositions_label}"></c:out></button>
        </form>
        <form action="controller" method="get">
            <input type="hidden" name="command" value="listHalls">
            <fmt:message bundle="${local}" var="halls_label" key="admin.button_labels.halls"/>
            <button class="btn btn-primary btn-block" type="submit"><c:out value="${halls_label}"></c:out></button>
        </form>
        <form action="controller" method="get">
            <input type="hidden" name="command" value="listUsers">
            <fmt:message bundle="${local}" var="users_label" key="admin.button_labels.users"/>
            <button class="btn btn-primary btn-block" type="submit">${users_label}</button>
        </form>
    </div>
    <div class="form-wrapper-right">
        <tags:logout userLogin="${user.login}" userName="${user.name}" curr_lang="${locale}"/>
        <tags:language curr_lang="${locale}" curr_uri="${pageContext.request.requestURI}"/>
    </div>
</div>
<main>
    <div class="admin-block">
        <c:choose>
            <c:when test="${showCreateExposition}">
                <form action="controller" method="post" id="expositionForm" enctype="multipart/form-data">
                    <input type="hidden" name="command" value="createExposition">
                    <div class="mb-3 row">
                        <fmt:message bundle="${local}" var="theme_label" key="create_exposition.label.theme"/>
                        <label for="theme">${theme_label}</label>
                        <input type="text" name="theme" class="form-control" id="theme">
                    </div>
                    <div class="mb-3 row">
                        <fmt:message bundle="${local}" var="ticket_price_label"
                                     key="create_exposition.label.ticket_price"/>
                        <label for="ticket_price">${ticket_price_label}</label>
                        <input type="text" name="ticket_price" class="form-control" id="ticket_price">
                    </div>
                    <div class="mb-3 row">
                        <fmt:message bundle="${local}" var="tickets_count_label"
                                     key="create_exposition.label.tickets_count"/>
                        <label for="tickets_count">${tickets_count_label}</label>
                        <input type="text" name="tickets_count" class="form-control" id="tickets_count">
                    </div>
                    <div class="mb-3 row">
                        <fmt:message bundle="${local}" var="start_date_label" key="create_exposition.label.start_date"/>
                        <label for="date_in">${start_date_label}</label>
                        <input type="date" name="date_in" class="form-control" id="date_in">
                    </div>
                    <div class="mb-3 row">
                        <fmt:message bundle="${local}" var="end_date_label" key="create_exposition.label.end_date"/>
                        <label for="date_out">${end_date_label}</label>
                        <input type="date" name="date_out" class="form-control" id="date_out">
                    </div>
                    <div class="mb-3 row">
                        <fmt:message bundle="${local}" var="halls_label" key="create_exposition.label.halls"/>
                        <label for="halls">${halls_label}</label>
                        <select class="form-select" multiple aria-label="multiple select example" name="halls"
                                id="halls">
                            <c:forEach items="${ requestScope.halls}" var="hall">
                                <option value="<c:out value="${hall.id}"></c:out>"><c:out
                                        value="${hall.hallName}"></c:out></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3 row">
                        <fmt:message bundle="${local}" var="photo_label" key="create_exposition.label.photo"/>
                        <label for="file">${photo_label}</label>
                        <input type="file" name="file" class="form-control" id="file">
                    </div>
                    <fmt:message bundle="${local}" var="create_button_label"
                                 key="create_exposition.button_label.create"/>
                    <input type="submit" class="btn btn-primary btn-block" value="${create_button_label}">
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
