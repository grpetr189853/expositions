<%--
  Created by IntelliJ IDEA.
  User: petrenko
  Date: 31.01.21
  Time: 14:06
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
    <title>User page</title>
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
            <fmt:message bundle="${local}" var="show_bought_tickets" key="user.buttons.show_bought_tickets"/>
            <input type="hidden" name="command" value="showBoughtTickets">
            <input type="hidden" name="userId" value="${user.userId}">
            <button class="btn btn-primary btn-block" type="submit">${show_bought_tickets}</button>
        </form>
    </div>
    <div class="form-wrapper-right">
        <tags:logout userLogin="${user.login}" userName="${user.name}" curr_lang="${locale}"/>
        <tags:language curr_lang="${locale}" curr_uri="${pageContext.request.requestURI}"/>
    </div>
</div>
<main>
    <div class="user-block">
        <c:choose>
            <c:when test="${showExpositions}">
                <table class="sort table" align="center">
                    <thead>
                    <tr>
                        <fmt:message bundle="${local}" var="exposition_theme" key="user.header.exposition_theme"/>
                        <fmt:message bundle="${local}" var="ticket_price" key="user.header.ticket_price"/>
                        <fmt:message bundle="${local}" var="start_date" key="user.header.start_date"/>
                        <fmt:message bundle="${local}" var="end_date" key="user.header.end_date"/>
                        <fmt:message bundle="${local}" var="halls" key="user.header.halls"/>
                        <td>ID</td>
                        <td>${exposition_theme}</td>
                        <td>${ticket_price}</td>
                        <td>${start_date}</td>
                        <td>${end_date}</td>
                        <td>${halls}</td>
                    </tr>
                    </thead>
                    <c:forEach items="${ requestScope.expositions}" var="exposition">
                        <tr>
                            <td><c:out value="${exposition.id }"/></td>
                            <td><c:out value="${exposition.theme }"/></td>
                            <td><c:out value="${exposition.ticketPrice }"/></td>
                            <td><c:out value="${exposition.dateIn }"/></td>
                            <td><c:out value="${exposition.dateOut }"/></td>
                            <td><c:out value="${exposition.hallsNames }"/></td>
                            <c:if test="${empty isAdmin}">
                                <td class="user-button">
                                    <form action="controller" method="post">
                                        <fmt:message bundle="${local}" key="user.buttons.buy_ticket" var="buy_ticket"/>
                                        <input type="hidden" name="exposition_id" value="${exposition.id}">
                                        <input type="hidden" name="command" value="buyTicket">
                                        <input class="btn btn-primary btn-block" type="submit" value="${buy_ticket}">
                                    </form>
                                </td>
                            </c:if>
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
                                                             href="controller?command=listExpositions&page=${i}">${i}</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                            <%--For displaying Next link --%>
                            <%--<c:if test="${currentPage lt noOfPages}">--%>
                            <%--<fmt:message key="page.next" var="next"/>--%>
                        <li class="page-item <c:if test="${currentPage == noOfPages}">disabled</c:if>"><a
                                class="page-link" href="controller?command=listExpositions&page=${currentPage + 1}"
                                aria-disabled="<c:out value="${currentPage == noOfPages}"></c:out>"><c:out
                                value="${pagination_next}"></c:out></a></li>
                            <%--</c:if>--%>
                    </ul>
                </div>
                <%--Pagination--%>
            </c:when>
            <c:when test="${showCreateOrder}">
                <table class="sort table" align="center">
                    <thead>
                    <tr>
                        <fmt:message bundle="${local}" var="exposition_theme" key="user.header.exposition_theme"/>
                        <fmt:message bundle="${local}" var="ticket_price" key="user.header.ticket_price"/>
                        <fmt:message bundle="${local}" var="start_date" key="user.header.start_date"/>
                        <fmt:message bundle="${local}" var="end_date" key="user.header.end_date"/>
                        <fmt:message bundle="${local}" var="halls" key="user.header.halls"/>
                        <td>ID</td>
                        <td>${exposition_theme}</td>
                        <td>${ticket_price}</td>
                        <td>${start_date}</td>
                        <td>${end_date}</td>
                        <td>${halls}</td>
                    </tr>
                    </thead>
                    <tr>
                        <td><c:out value="${exposition_id}"/></td>
                        <td><c:out value="${exposition.theme}"/></td>
                        <td><c:out value="${exposition.ticketPrice}"/></td>
                        <td><c:out value="${exposition.dateIn }"/></td>
                        <td><c:out value="${exposition.dateOut}"/></td>
                        <td><c:out value="${exposition.hallsNames}"/></td>
                    </tr>
                </table>


                <div class="image-wrapper">
                    <img src="<c:out value="img/"/>${exposition.imgName}" alt="Exposition Photo">
                </div>


                <form action="controller" method="post" style="margin-top:20px;">
                    <input type="hidden" name="exposition_id" value="${exposition.id}">
                    <fmt:message bundle="${local}" key="user.buttons.buy_ticket" var="buy_ticket"/>
                    <fmt:message bundle="${local}" key="user.label.additional_info" var="additional_info"/>
                    <input type="hidden" name="command" value="createOrder">
                    <label for="additional_info">${additional_info}</label>
                    <textarea name="additional_info" id="additional_info" cols="30" rows="1"></textarea>
                    <input class="btn btn-primary btn-block" type="submit" value="${buy_ticket}" style="display:block; margin: 20px auto;">
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
