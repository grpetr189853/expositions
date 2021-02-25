<%--
  Created by IntelliJ IDEA.
  User: petrenko
  Date: 07.02.21
  Time: 17:55
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
    <title>Admin Show Statistic <username:usernameTagHandler userId="${user.userId}" lang="${locale}"/></title>
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
            <button class="btn btn-primary btn-block" type="submit"><c:out value="${users_label}"></c:out></button>
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
            <c:when test="${showExpositionStatistic}">
                <table class="sort table" align="center">
                    <thead>
                    <tr>
                        <fmt:message bundle="${local}" var="user_name" key="exposition_statistic.label.username"/>
                        <fmt:message bundle="${local}" var="exposition_theme"
                                     key="exposition_statistic.label.exposition_theme"/>
                        <fmt:message bundle="${local}" var="additional_info"
                                     key="exposition_statistic.label.additional_info"/>
                        <fmt:message bundle="${local}" var="cost" key="exposition_statistic.label.cost"/>
                        <td>${user_name}</td>
                        <td>${exposition_theme}</td>
                        <td>${additional_info}</td>
                        <td>${cost}</td>
                    </tr>
                    </thead>
                    <c:forEach items="${ requestScope.statistics}" var="statistic">
                        <tr>
                            <td><c:out value="${statistic.userName }"/></td>
                            <td><c:out value="${statistic.expositionName  }"/></td>
                            <td><c:out value="${statistic.additionalInfo }"/></td>
                            <td><c:out value="${statistic.cost }"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
        </c:choose>
    </div>
</main>

<script src="<c:url value="/js/jquery-3.5.1.min.js" />"></script>
<script src="<c:url value="/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/js/main.js" />"></script>
</body>
</html>
