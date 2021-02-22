<%--
  Created by IntelliJ IDEA.
  User: petrenko
  Date: 15.02.21
  Time: 1:11
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
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
    <title>Expositions</title>
    <link href="<c:url value="/css/bootstrap.min.css" />" type="text/css" rel="stylesheet"/>
    <link type="text/css" rel="stylesheet" href="css/main.css">
</head>
<body class="admin-page">
<div class="head-content">
    <div class="form-wrapper-right">
        <%--<tags:language curr_lang="${locale}" curr_uri="${pageContext.request.requestURI}"/>--%>
    </div>
</div>
<main>
    <div class="admin-block">
        <form action="controller" method="get">
            <fmt:message bundle="${local}" var="start_date" key="nauthorized.label.start_date"/>
            <fmt:message bundle="${local}" var="end_date" key="nauthorized.label.end_date"/>
            <fmt:message bundle="${local}" var="filter" key="nauthorized.label.filter"/>
            <fmt:message bundle="${local}" var="reset_filter" key="nauthorized.label.reset_filter"/>
            <div class="mb-3 row">
                <label for="date_in">${start_date}</label>
                <input type="date" name="date_in" value="${requestScope.date_in}" id="date_in">
            </div>
            <div class="mb-3 row">
                <label for="date_out">${end_date}</label>
                <input type="date" name="date_out" value="${requestScope.date_out}" id="date_out">
            </div>
            <input type="hidden" name="command" value="filterExpositions">
            <button class="btn btn-primary btn-block" type="submit">${filter}</button>
        </form>
        <form action="controller">
            <input type="hidden" name="command" value="listExpositions">
            <button class="btn btn-primary btn-block" type="submit">${reset_filter}</button>

        </form>
        <c:choose>
            <c:when test="${showExpositions}">
                <div class="exposition-cards">
                    <fmt:message bundle="${local}" var="ticket_price" key="nauthorized.label.ticket_price"/>
                    <fmt:message bundle="${local}" var="tickets_count" key="nauthorized.label.tickets_count"/>
                    <fmt:message bundle="${local}" var="start_date" key="nauthorized.label.start_date"/>
                    <fmt:message bundle="${local}" var="end_date" key="nauthorized.label.end_date"/>
                    <fmt:message bundle="${local}" var="halls" key="nauthorized.label.halls"/>
                    <fmt:message bundle="${local}" var="exposition_label" key="nauthorized.label.exposition"/>
                    <fmt:message bundle="${local}" var="exposition_ex_label" key="nauthorized.label.exposition_ex"/>
                    <fmt:message bundle="${local}" var="photo_label" key="nauthorized.label.photo"/>
                    <fmt:message bundle="${local}" var="details" key="nauthorized.label.detail"/>
                    <div class="container">
                        <div class="row">
                            <c:forEach items="${ requestScope.expositions}" var="exposition">
                                <div class="col-md-3">
                                    <div class="card h-100">
                                        <div class="card-header">
                                            <img src="<c:out value="img/"/>${exposition.imgName}"
                                                 alt="Exposition Photo">
                                        </div>
                                        <div class="card-body">
                                            <div class="exposition-header">${fn:substring(exposition.theme , 0, 40)}...</div>
                                            <div class="exposition-detail">
                                                <div class="ticket-price">
                                                    <span class="ticket-price-header">${ticket_price}</span>
                                                    <span class="ticket-price-value"><c:out
                                                            value="${exposition.ticketPrice }"/></span>
                                                </div>
                                                <div class="tickets-count">
                                                    <span class="tickets-count-header">${tickets_count}</span>
                                                    <span class="tickets-count-value"><c:out
                                                            value="${exposition.ticketsCount }"/></span>
                                                </div>
                                                <div class="date-in">
                                                    <span class="date-in-header">${start_date}</span>
                                                    <span class="date-in-value"><c:out
                                                            value="${exposition.dateIn }"/></span>
                                                </div>
                                                <div class="date-out">
                                                    <span class="date-out-header">${end_date}</span>
                                                    <span class="date-out-value"><c:out
                                                            value="${exposition.dateOut }"/></span>
                                                </div>
                                                <div class="halls">
                                                    <span class="halls-header">${halls}</span>
                                                    <span class="halls-value"><c:out
                                                            value="${exposition.hallsNames }"/></span>
                                                </div>
                                            </div>
                                            <div class="test-tags">
                                                <span>${exposition_label}</span>
                                                <span>${exposition_ex_label}</span>
                                                <span>${photo_label}</span>
                                            </div>
                                            <div class="test-buttons-wrapper text-center">
                                                <a href="" class="test-button">${details}</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </c:when>
        </c:choose>
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
