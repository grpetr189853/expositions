package com.grpetr.task.web.constants;

public class Path {
    // pages
    public static final String PAGE__INDEX = "/index.jsp";
    public static final String PAGE__LOGIN = "/login.jsp";
    public static final String PAGE__ERROR_PAGE = "/jsp/error_page.jsp";
    public static final String PAGE__HOME_USER_JSP = "/jsp/user/user.jsp";
    public static final String PAGE__ADMIN_EXPOSITIONS = "/jsp/admin/expositions.jsp";
    public static final String PAGE__ADMIN_HALLS = "/jsp/admin/halls.jsp";
    public static final String PAGE__ADMIN_USERS = "/jsp/admin/users.jsp";
    public static final String PAGE__ADMIN_CREATE_EXPOSITION = "/jsp/admin/create_exposition.jsp";
    public static final String PAGE__ADMIN_CREATE_HALL = "/jsp/admin/create_hall.jsp";
    public static final String PAGE__ADMIN_SHOW_STATISTIC = "/jsp/admin/show_statistic.jsp";
    public static final String PAGE__USER_SHOW_BOUGHT_ORDERS = "/jsp/user/show_bought_orders.jsp";
    public static final String PAGE__NON_AUTORIZED_USER_EXPOSITIONS = "/jsp/non-authorized/expositions.jsp";
    public static final String PAGE__ADMIN_EDIT_EXPOSITION = "/jsp/admin/edit_exposition.jsp";
    public static final String PAGE__ADMIN_EXPOSITION_PHOTO = "/jsp/admin/exposition_photo.jsp";
    public static final String PAGE__NON_AUTHORIZED_USER_EXPOSITION_PHOTO ="/jsp/non-authorized/exposition_photo.jsp";
    // commands
    public static final String COMMAND__LIST_EXPOSITIONS = "/controller?command=listExpositions";
    public static final String COMMAND__LIST_HALLS = "/controller?command=listHalls";

}
