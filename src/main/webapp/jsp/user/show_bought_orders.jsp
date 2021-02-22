<%--
  Created by IntelliJ IDEA.
  User: petrenko
  Date: 08.02.21
  Time: 13:36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <title>Bought orders</title>
    <link href="<c:url value="/css/bootstrap.min.css" />" type="text/css" rel="stylesheet"/>
    <link type="text/css" rel="stylesheet" href="css/main.css">
</head>
<body class="user-page">
<div class="head-content">
    <div class="form-wrapper-left">
        <form action="controller" method="get">
            <input type="hidden" name="command" value="listExpositions">
            <fmt:message bundle="${local}" var="expositions_label" key="admin.button_labels.expositions"/>
            <button class="btn btn-primary btn-block" type="submit"><c:out
                    value="${expositions_label}"></c:out></button>
        </form>
        <form action="controller" method="get">
            <input type="hidden" name="command" value="showBoughtTickets">
            <input type="hidden" name="userId" value="${user.userId}">
            <button class="btn btn-primary btn-block" type="submit">Просмотреть купленные билеты</button>
        </form>
    </div>
    <div class="form-wrapper-right">
        <tags:logout userLogin="${user.login}" userName="${user.name}" curr_lang="${locale}"/>
        <%--<tags:language curr_lang="${locale}" curr_uri="${pageContext.request.requestURI}"/>--%>
    </div>
</div>
<main>
    <div class="user-block">
        <table class="sort table" align="center">
            <thead>
            <tr>
                <fmt:message bundle="${local}" var="exposition_theme" key="show_bought_orders.label.exposition_theme"/>
                <fmt:message bundle="${local}" var="start_date" key="show_bought_orders.label.start_date"/>
                <fmt:message bundle="${local}" var="end_date" key="show_bought_orders.label.end_date"/>
                <fmt:message bundle="${local}" var="additional_info" key="show_bought_orders.label.additional_info"/>
                <fmt:message bundle="${local}" var="cost" key="show_bought_orders.label.cost"/>
                <td>${exposition_theme}</td>
                <td>${start_date}</td>
                <td>${end_date}</td>
                <td>${additional_info}</td>
                <td>${cost}</td>
            </tr>
            </thead>
            <c:forEach items="${ requestScope.tickets}" var="ticket">
                <tr>
                    <td><c:out value="${ticket.expositionTheme}"></c:out></td>
                    <td><c:out value="${ticket.dateIn}"></c:out></td>
                    <td><c:out value="${ticket.dateOut}"></c:out></td>
                    <td><c:out value="${ticket.additionalInfo}"></c:out></td>
                    <td><c:out value="${ticket.cost}"></c:out></td>
                </tr>
            </c:forEach>
        </table>
        <%--Pagination--%>
        <div class="paging text-center">
            <%--For displaying Page numbers.
            The when condition does not display a link for the current page--%>
            <fmt:message bundle="${local}" key="admin.pagination_labels.previous" var="pagination_previous"/>
            <fmt:message bundle="${local}" key="admin.pagination_labels.next" var="pagination_next"/>
            <ul class="pagination">
                <li class="page-item <c:if test="${currentPage == 1}">disabled</c:if>"><a class="page-link"
                                                                                          href="controller?command=listExpositions&page=${currentPage - 1}"><c:out
                        value="${pagination_previous}"></c:out></a></li>
                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:choose>
                        <c:when test="${currentPage eq i}">
                            <li class="page-item active"><a class="page-link" href="#">${i}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item"><a class="page-link"
                                                     href="controller?command=listExpositions&page=${i}">${i}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <%--For displaying Next link --%>
                <%--<c:if test="${currentPage lt noOfPages}">--%>
                <%--<fmt:message key="page.next" var="next"/>--%>
                <li class="page-item <c:if test="${currentPage == noOfPages}">disabled</c:if>"><a class="page-link"
                                                                                                  href="controller?command=listExpositions&page=${currentPage + 1}"
                                                                                                  aria-disabled="<c:out value="${currentPage == noOfPages}"></c:out>"><c:out
                        value="${pagination_next}"></c:out></a></li>
                <%--</c:if>--%>
            </ul>
        </div>
        <%--Pagination--%>
    </div>
</main>


<script src="<c:url value="/js/jquery-3.5.1.min.js" />"></script>
<script src="<c:url value="/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/js/main.js" />"></script>
</body>
</html>
