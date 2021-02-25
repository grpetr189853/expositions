<%--
  Created by IntelliJ IDEA.
  User: petrenko
  Date: 31.01.21
  Time: 0:56
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
    <title>Registration</title>
    <link href="<c:url value="css/bootstrap.min.css" />" type="text/css" rel="stylesheet"/>
    <link type="text/css" rel="stylesheet" href="css/main.css">
</head>
<body class="registration-page">
<main>
    <div class="registration-block">
        <fmt:message bundle="${local}" var="input_data" key="login.label.input_data"/>
        <fmt:message bundle="${local}" var="name_label" key="register.label.name"/>
        <fmt:message bundle="${local}" var="email_label" key="register.label.email"/>
        <fmt:message bundle="${local}" var="login_label" key="register.label.login"/>
        <fmt:message bundle="${local}" var="password_label" key="register.label.password"/>
        <fmt:message bundle="${local}" var="registration_label" key="register.label.registration"/>
        <fmt:message bundle="${local}" var="login_with_your_data" key="register.label.login_with_your_data"/>
        <fmt:message bundle="${local}" var="login_with" key="register.label.login_with"/>
        <fmt:message bundle="${local}" var="or_login" key="login.label.or_login"/>
        <fmt:message bundle="${local}" var="to_main" key="login.label.to_main"/>
        <fmt:message bundle="${local}" var="main" key="login.label.main"/>

        <h2>${input_data}</h2>
        <form action="controller" method="post">
            <input type="hidden" name="command" value="registration"/>
            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon"><i class="fa fa-user ti-user"></i></span>
                    <input type="text" name="name" class="form-control" placeholder="${name_label}" required>
                </div>
            </div>

            <hr class="hr-xs">

            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon"><i class="fa fa-user ti-user"></i></span>
                    <input type="text" name="email" class="form-control" placeholder="${email_label}" required>
                </div>
            </div>

            <hr class="hr-xs">

            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon"><i class="fa fa-user ti-user"></i></span>
                    <input type="text" name="login" class="form-control" placeholder="${login_label}" required>
                </div>
            </div>

            <hr class="hr-xs">

            <div class="form-group">
                <div class="input-group">
                    <span class="input-group-addon"><i class="fa fa-lock ti-unlock"></i></span>
                    <input type="password" name="password" class="form-control" placeholder="${password_label}" required>
                </div>
            </div>

            <!--            <hr class="hr-xs">

                        <div class="form-group">
                            <div class="input-group">
                                <span class="input-group-addon"><i class="fa fa-lock ti-unlock"></i></span>
                                <input type="password" class="form-control" placeholder="Повторите суперпароль">
                            </div>
                        </div> -->

            <button class="btn btn-primary btn-block" type="submit">${registration_label}</button>
            <div class="registration-footer">
                <h6>${or_login}</h6>
                <ul class="social-icons">
                    <li><a class="facebook" href="#"><i class="fa fa-facebook"></i></a></li>
                    <li><a class="twitter" href="#"><i class="fa fa-twitter"></i></a></li>
                    <li><a class="linkedin" href="#"><i class="fa fa-linkedin"></i></a></li>
                </ul>
            </div>

        </form>
    </div>

    <div class="registration-links">
        <p class="text-center">${login_with_your_data} <a class="txt-brand" href="login.jsp"><font
                color=#29aafe>${login_with}</font></a></p>
    </div>
    <div class="main-links">
        <p class="text-center">${to_main} <a class="txt-brand" href="index.jsp"><font color=#29aafe>${main}</font></a>
        </p>
    </div>
</main>


<script src="<c:url value="js/jquery-3.5.1.min.js" />"></script>
<script src="<c:url value="js/bootstrap.min.js" />"></script>
</body>
</html>
