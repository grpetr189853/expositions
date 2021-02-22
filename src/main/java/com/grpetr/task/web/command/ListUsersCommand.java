package com.grpetr.task.web.command;

import com.grpetr.task.db.DBManager;
import com.grpetr.task.db.dao.DAOFactory;
import com.grpetr.task.db.dao.UserDAO;
import com.grpetr.task.db.entity.Hall;
import com.grpetr.task.db.entity.User;
import com.grpetr.task.exception.AppException;
import com.grpetr.task.web.constants.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ListUsersCommand extends Command {
    private static final Logger log = Logger.getLogger(ListUsersCommand.class);
    private static final int PER_PAGE = 10;
    private static final long serialVersionUID = -653907179490046233L;
    public DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws AppException, IOException, ServletException {

        log.debug("List Users Command starts");
        int page = 1;
        if (null != request.getParameter("page")) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int offset = (page - 1) * PER_PAGE;
        Connection con = null;
        int numberOfUsers = 0;
        // get users list
        List<User> users = null;
        try {
            con = DBManager.getInstance().getConnection();
            UserDAO userDAO = daoFactory.getUserDAO();
            users = userDAO.getAllUsers(con, PER_PAGE, offset);
            numberOfUsers = userDAO.getUsersNumber(con);
            log.trace("Found in DB: users --> " + users);
            con.commit();
        } catch (SQLException e) {
            log.error("Cannot get users list", e);
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            throw new AppException("Cannot obtain users list", e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        log.trace("Found in DB: users --> " + users);

        int noOfPages = (int) Math.ceil(numberOfUsers * 1.0 / PER_PAGE);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        // put menu items list to the request
        request.setAttribute("users", users);
        log.trace("Set the request attribute: users --> " + users);
        request.setAttribute("showUsers", true);
        log.trace("Set the request attribute: showUsers --> " + true);

        log.debug("Command finished");
        return Path.PAGE__ADMIN_USERS;
    }
}
