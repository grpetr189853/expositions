package com.grpetr.task.web.command;

import com.grpetr.task.db.DBManager;
import com.grpetr.task.db.dao.DAOFactory;
import com.grpetr.task.db.dao.ExpositionDAO;
import com.grpetr.task.exception.AppException;
import com.grpetr.task.web.constants.Path;
import com.grpetr.task.web.result.CommandResult;
import com.grpetr.task.web.result.RedirectResult;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class DeleteExpositionCommand extends Command {

    private static final Logger log = Logger.getLogger(DeleteExpositionCommand.class);
    private static final long serialVersionUID = 7035978923634475984L;
    public DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request,
                                 HttpServletResponse response) throws AppException, IOException, ServletException {

        log.debug("Delete Exposition Command starts");
        request.getSession().removeAttribute("sendRedirectExpositions");
        request.getSession().removeAttribute("sendRedirectHalls");
        // delete exposition
        boolean deleteExposition = false;
        String forward = Path.PAGE__ERROR_PAGE;

        Integer expositionId = null;
        Connection con = null;
        try {
            ;
            expositionId = Integer.parseInt(request.getParameter("exposition_id"));
        } catch (NumberFormatException e) {
            expositionId = null;
        }
        if (expositionId != null) {
            deleteExposition = true;
        }
        if (deleteExposition == true) {
            try {
                con = DBManager.getInstance().getConnection();
                ExpositionDAO expositionDAO = daoFactory.getExpositionDAO();

                if (expositionDAO.deleteExposition(con, expositionId)) {
                    forward = Path.COMMAND__LIST_EXPOSITIONS;
                    request.getSession().setAttribute("sendRedirectExpositions", true);
                }
                con.commit();
            } catch (Exception e) {
                log.error("Cannot delete exposition", e);
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
                throw new AppException("Cannot delete exposition", e);
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        log.debug("Command finished");
        return new RedirectResult(forward);
    }

}
