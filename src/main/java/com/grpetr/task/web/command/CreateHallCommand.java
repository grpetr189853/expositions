package com.grpetr.task.web.command;

import com.grpetr.task.db.DBManager;
import com.grpetr.task.db.dao.DAOFactory;
import com.grpetr.task.db.dao.HallDAO;
import com.grpetr.task.exception.AppException;
import com.grpetr.task.web.constants.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class CreateHallCommand extends Command {
    private static final Logger log = Logger.getLogger(CreateHallCommand.class);
    private static final long serialVersionUID = -732098672003814539L;
    public DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws AppException, IOException, ServletException {

        log.debug("Create Hall Command starts");
        String forward = Path.PAGE__ERROR_PAGE;
        int newRowId = 0;
        String errorMessage = null;
        request.getSession().removeAttribute("sendRedirectExpositions");
        request.getSession().removeAttribute("sendRedirectHalls");
        Connection con = null;
        // put menu items list to the request
        try {
            con = DBManager.getInstance().getConnection();
            String hallName = request.getParameter("hall_name");
            if (hallName.isEmpty()) {
                errorMessage = "Hall name cannot be empty";
                request.setAttribute("errorMessage", errorMessage);
                log.error("errorMessage --> " + errorMessage);
                forward = Path.PAGE__ERROR_PAGE;
                return forward;
            }
            HallDAO hallDAO = daoFactory.getHallDAO();
            newRowId = hallDAO.setNewHall(con, hallName);
            if (newRowId != 0) {
                forward = Path.PAGE__ADMIN_HALLS;
                request.getSession().setAttribute("sendRedirectHalls", true);
                log.trace("Set the request attribute: sendRedirectHalls --> " + true);
            }
            con.commit();
        } catch (Exception ex) {
            log.error("Cannot create hall", ex);
            try {
                con.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            throw new AppException("Cannot cannot create hall", ex);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


        log.debug("Command finished");
        return forward;
    }
}
