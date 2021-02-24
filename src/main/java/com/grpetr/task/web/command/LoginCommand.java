package com.grpetr.task.web.command;

import com.grpetr.task.db.DBManager;
import com.grpetr.task.db.constant.AccessLevel;
import com.grpetr.task.db.dao.DAOFactory;
import com.grpetr.task.db.dao.UserDAO;
import com.grpetr.task.db.entity.User;
import com.grpetr.task.exception.AppException;
import com.grpetr.task.web.constants.Path;
import com.grpetr.task.web.result.CommandResult;
import com.grpetr.task.web.result.ForwardResult;
import org.apache.log4j.Logger;

import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginCommand extends Command {

    private static final long serialVersionUID = -3071536593627692473L;
    public DAOFactory daoFactory = DAOFactory.getInstance();
    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request,
                                 HttpServletResponse response) throws AppException, IOException, ServletException {

        log.debug("Login Command starts");

        HttpSession session = request.getSession();

        // obtain login and password from the request
        String login = request.getParameter("login");
        log.trace("Request parameter: loging --> " + login);

        String password = request.getParameter("password");

        // error handler
        String errorMessage = null;
        String forward = Path.PAGE__ERROR_PAGE;

        if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
            errorMessage = "Login/password cannot be empty";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return new ForwardResult(forward);
        }

        User user = null;
        Connection con = null;
        try {
            con = DBManager.getInstance().getConnection();
            UserDAO userDAO = daoFactory.getUserDAO();
            user = userDAO.login(con, login, password);
            con.commit();
        } catch (SQLException | LoginException e) {
            log.error("Cannot get user", e);
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
            throw new AppException("Cannot get user", e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


        if (user == null) {
            errorMessage = "Cannot find user with such login/password";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return new ForwardResult(forward);
        } else {
            AccessLevel userRole = AccessLevel.getAccessLevel(user);
            log.trace("userRole --> " + userRole);

            if (userRole == AccessLevel.ADMIN)
                forward = Path.PAGE__ADMIN_EXPOSITIONS;

            if (userRole == AccessLevel.USER)
                forward = Path.PAGE__HOME_USER_JSP;
//                forward = Path.COMMAND__LIST_EXPOSITIONS;

            session.setAttribute("user", user);
            log.trace("Set the session attribute: user --> " + user);

            session.setAttribute("userRole", userRole);
            log.trace("Set the session attribute: userRole --> " + userRole);

            log.info("User " + user + " logged as " + userRole.toString().toLowerCase());

        }

        log.debug("Command finished");
        return new ForwardResult(forward);
    }

}
