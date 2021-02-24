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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

public class ChangeLanguageCommand extends Command {
    private static final Logger log = Logger.getLogger(ChangeLanguageCommand.class);
    private static final long serialVersionUID = -2651459596565529143L;
    public DAOFactory daoFactory = DAOFactory.getInstance();

    public CommandResult execute(HttpServletRequest request,
                                 HttpServletResponse response) throws AppException, IOException, ServletException {
        HttpSession session = request.getSession();
        Locale newLocale = new Locale(request.getParameter("locale"));
        UserDAO userDAO = daoFactory.getUserDAO();
        Connection con = null;
        User user= (User)session.getAttribute("user");
        try {
            con = DBManager.getInstance().getConnection();
            userDAO.setUserLanguage(con, user.getUserId(), String.valueOf(newLocale));
            con.commit();
        } catch (SQLException e){
            log.error("Cannot change language", e);
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
            throw new AppException("Cannot change user language", e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        session.setAttribute("locale", newLocale);
        if (session.getAttribute("userRole") == AccessLevel.ADMIN) {
            return new ForwardResult(Path.PAGE__ADMIN_EXPOSITIONS);
        } else if (session.getAttribute("userRole") == AccessLevel.USER) {
            return new ForwardResult(Path.PAGE__HOME_USER_JSP);
        }
        return new ForwardResult(Path.PAGE__NON_AUTORIZED_USER_EXPOSITIONS);
    }
}