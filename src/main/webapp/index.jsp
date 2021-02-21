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
    <title>Index</title>
    <link href="<c:url value="css/bootstrap.min.css" />" type="text/css" rel="stylesheet"/>
    <link type="text/css" rel="stylesheet" href="css/main.css">
</head>
<body class="index-page">
<div class="head-content">
    <div class="form-wrapper-right">
        <tags:language curr_lang="${locale}" curr_uri="${pageContext.request.requestURI}"/>
    </div>
</div>
<main class="text-center">
    <fmt:message bundle="${local}" var="actions" key="index.label.actions" />
    <fmt:message bundle="${local}" var="exposition_label" key="index.label.expositions_label" />
    <fmt:message bundle="${local}" var="list_expositions" key="index.label.list_expositions" />
    <fmt:message bundle="${local}" var="we_are_specialist" key="index.label.we_are_specialist" />
    <fmt:message bundle="${local}" var="login" key="index.label.login" />
    <fmt:message bundle="${local}" var="register" key="index.label.register" />
    <div class="section clearfix">
        <div class="title-text">
            <h2>${we_are_specialist}</h2>
            <h1>${exposition_label}</h1>
            <p>${actions}
            </p>
            <div class="row main-row">
                <div class="main-button-wrapper col-3">
                    <div class="button"><a href="registration.jsp" class="btn btn-primary btn-block">${register}</a></div>
                    <div class="button"><a href="login.jsp" class="btn btn-primary btn-block">${login}</a></div>
                    <form action="controller" method="get" style="display: contents;">
                        <input type="hidden" name="command" value="listExpositions">
                        <%--<button class="btn btn-primary btn-block" >ЕКСПОЗИЦИИ</button>--%>
                        <div class="button"><button href="" class="btn btn-primary btn-block" type="submit">${list_expositions}</button></div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</main>

<script src="<c:url value="js/jquery-3.5.1.min.js" />"></script>
<script src="<c:url value="js/bootstrap.min.js" />"></script>
</body>
</html>
