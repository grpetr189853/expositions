package com.grpetr.task.web.command;

import com.grpetr.task.db.DBManager;
import com.grpetr.task.db.constant.Language;
import com.grpetr.task.db.dao.DAOFactory;
import com.grpetr.task.db.dao.UserDAO;
import com.grpetr.task.exception.AppException;
import com.grpetr.task.web.constants.Path;
import com.grpetr.task.web.result.CommandResult;
import com.grpetr.task.web.result.ForwardResult;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class RegistrationCommand extends Command {
    public DAOFactory daoFactory = DAOFactory.getInstance();
    private static final long serialVersionUID = -3071536593627692473L;
    private static final Logger log = Logger.getLogger(RegistrationCommand.class);

    @Override
    public CommandResult execute(HttpServletRequest request,
                                 HttpServletResponse response) throws AppException, IOException, ServletException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String forward = Path.PAGE__ERROR_PAGE;
        String errorMessage;
        Connection con = null;
        if (login == null || password == null | email == null || name == null) {
            errorMessage = "Login/password/email/name cannot be empty";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return new ForwardResult(forward);
        }

        try {
            con = DBManager.getInstance().getConnection();
            UserDAO userDAO = daoFactory.getUserDAO();
            userDAO.register(con, name, login, password, email);
            forward = Path.PAGE__LOGIN;
            con.commit();
        } catch (SQLException e) {
            log.error("Cannot register user", e);
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
            throw new AppException("Cannot register user", e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return new ForwardResult(forward);
    }
}
