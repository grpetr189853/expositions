package com.grpetr.task.web.command;

import com.grpetr.task.db.DBManager;
import com.grpetr.task.db.dao.DAOFactory;
import com.grpetr.task.db.dao.ExpositionDAO;
import com.grpetr.task.db.entity.Exposition;
import com.grpetr.task.exception.AppException;
import com.grpetr.task.web.constants.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class BuyTicketCommand extends Command {
    private static final Logger log = Logger.getLogger(BuyTicketCommand.class);
    private static final long serialVersionUID = -6697492180545036573L;
    public DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws AppException, IOException, ServletException {

        log.debug("Bye ticket Command starts");
        // put menu items list to the request
        Exposition exposition = null;
        String forward = Path.PAGE__ERROR_PAGE;
        Connection con = null;
        int expositionId = Integer.parseInt(request.getParameter("exposition_id"));
        try {
            con = DBManager.getInstance().getConnection();
            ExpositionDAO expositionDAO = daoFactory.getExpositionDAO();
            exposition = expositionDAO.getExpositionById(con, expositionId);
            forward = Path.PAGE__HOME_USER_JSP;
            con.commit();
        } catch (SQLException e) {
            log.error("Cannot get expositions list", e);
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
            throw new AppException("Cannot obtain expositions list", e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        request.setAttribute("showCreateOrder", true);
        log.trace("Set the request attribute: showCreateOrder --> " + true);
        request.setAttribute("exposition_id", expositionId);
        log.trace("Set the request attribute: expositionId --> " + expositionId);
        request.setAttribute("exposition", exposition);
        log.trace("Set the request attribute: exposition --> " + exposition);


        log.debug("Command finished");
        return forward;
    }
}
