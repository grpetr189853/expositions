<%--
  Created by IntelliJ IDEA.
  User: petrenko
  Date: 07.02.21
  Time: 17:18
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
    <title>Admin Page Expositions</title>
    <link href="<c:url value="/css/bootstrap.min.css" />" type="text/css" rel="stylesheet"/>
    <link type="text/css" rel="stylesheet" href="css/main.css">
</head>
<body class="admin-page">
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
    <div class="admin-block">
        <c:choose>
            <c:when test="${showExpositions}">
                <table class="sort table" align="center">
                    <thead>
                    <tr>
                        <fmt:message bundle="${local}" var="table_header_theme" key="admin.table_header.theme" />
                        <fmt:message bundle="${local}" var="table_header_ticket_price" key="admin.table_header.ticket_price" />
                        <fmt:message bundle="${local}" var="table_header_tickets_count" key="admin.table_header.tickets_count" />
                        <fmt:message bundle="${local}" var="table_header_date_in" key="admin.table_header.date_in" />
                        <fmt:message bundle="${local}" var="table_header_date_out" key="admin.table_header.date_out" />
                        <fmt:message bundle="${local}" var="table_header_halls" key="admin.table_header.halls" />
                        <td>ID</td>
                        <td><c:out value="${table_header_theme}"></c:out></td>
                        <td><c:out value="${table_header_ticket_price}"></c:out></td>
                        <td><c:out value="${table_header_tickets_count}"></c:out></td>
                        <td><c:out value="${table_header_date_in}"></c:out></td>
                        <td><c:out value="${table_header_date_out}"></c:out></td>
                        <td><c:out value="${table_header_halls}"></c:out></td>
                    </tr>
                    </thead>
                    <c:forEach items="${ requestScope.expositions}" var="exposition">
                        <tr>
                            <td><c:out value="${exposition.id }" /></td>
                            <td><c:out value="${exposition.theme }" />
                                <form action="controller" method="get">
                                    <fmt:message bundle="${local}" key="admin.button_labels.show_exposition_picture" var="show_exposition_picture_label"/>
                                    <input type="hidden" name="command" value="showExpositionPhoto">
                                    <input type="hidden" name="exposition_id" value="${exposition.id}">
                                    <input class="btn btn-primary btn-xs" type="submit" value="${show_exposition_picture_label}">
                                </form>
                            </td>
                            <td><c:out value="${exposition.ticketPrice }" /></td>
                            <td><c:out value="${exposition.ticketsCount }" /></td>
                            <td><c:out value="${exposition.dateIn }" /></td>
                            <td><c:out value="${exposition.dateOut }" /></td>
                            <td><c:out value="${exposition.hallsNames }" /></td>

                            <td class="user-button button-td">
                                <form action="controller" method="get">
                                    <fmt:message bundle="${local}" key="admin.button_labels.show_statistic" var="statistic_label"/>
                                    <fmt:message bundle="${local}" key="admin.button_labels.edit_exposition" var="edit_exposition_label"/>
                                    <input type="hidden" name="exposition_id" value="${exposition.id}">
                                    <input type="hidden" name="command" value="showEditExposition">
                                    <input class="btn btn-primary btn-block" type="submit" value="${edit_exposition_label}">
                                </form>
                            <%--</td>--%>
                            <%--<td class="user-button button-td">--%>
                                <form action="controller" method="get">
                                    <fmt:message bundle="${local}" key="admin.button_labels.show_statistic" var="statistic_label"/>
                                    <input type="hidden" name="exposition_id" value="${exposition.id}">
                                    <input type="hidden" name="command" value="showExpositionStatistic">
                                    <input class="btn btn-primary btn-block" type="submit" value="${statistic_label}">
                                </form>
                            <%--</td>--%>
                            <%--<td class="user-button button-td">--%>
                                <form action="controller" method="post">
                                    <fmt:message bundle="${local}" key="admin.button_labels.cancel" var="cancel_label"/>
                                    <input type="hidden" name="exposition_id" value="${exposition.id}">
                                    <input type="hidden" name="command" value="deleteExposition">
                                    <input type="hidden" name="sendRedirect" value="true">
                                    <input class="btn btn-primary btn-block" type="submit" value="${cancel_label}">
                                </form>
                            </td>
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
                        <li class="page-item <c:if test="${currentPage == 1}">disabled</c:if>"><a class="page-link" href="controller?command=listExpositions&page=${currentPage - 1}"><c:out value="${pagination_previous}"></c:out></a></li>
                        <c:forEach begin="1" end="${noOfPages}" var="i">
                            <c:choose>
                                <c:when test="${currentPage eq i}">
                                    <li class="page-item active"><a class="page-link" href="#">${i}</a></li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item"><a  class="page-link" href="controller?command=listExpositions&page=${i}">${i}</a></li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                            <%--For displaying Next link --%>
                            <%--<c:if test="${currentPage lt noOfPages}">--%>
                        <fmt:message key="page.next" var="next"/>
                        <li class="page-item <c:if test="${currentPage == noOfPages}">disabled</c:if>" ><a class="page-link" href="controller?command=listExpositions&page=${currentPage + 1}" aria-disabled="<c:out value="${currentPage == noOfPages}"></c:out>"><c:out value="${pagination_next}"></c:out></a></li>
                            <%--</c:if>--%>
                    </ul>
                </div>
                <%--Pagination--%>
                <form action="controller" method="get">
                    <input type="hidden" name="command" value="showCreateExposition">
                    <fmt:message bundle="${local}" key="admin.button_labels.create_exposition" var="create_exposition_label"/>
                    <button class="btn btn-primary btn-block" type="submit">${create_exposition_label}</button>
                </form>
            </c:when>
            <c:when test="${sendRedirectExpositions}">
                <fmt:message bundle="${local}" key="admin.button_labels.back_to_expositions_list" var="back_to_expositions_label"/>
                <a href="controller?command=listExpositions" class="btn btn-primary btn-block"><c:out value="${back_to_expositions_label}"></c:out></a>
            </c:when>
        </c:choose>
    </div>
</main>

<script src="<c:url value="/js/jquery-3.5.1.min.js" />"></script>
<script src="<c:url value="/js/bootstrap.min.js" />"></script>
<script src="<c:url value="/js/main.js" />"></script>
</body>
</html>
