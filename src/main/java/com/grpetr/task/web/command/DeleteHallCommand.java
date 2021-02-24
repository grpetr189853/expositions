package com.grpetr.task.web.command;

import com.grpetr.task.db.DBManager;
import com.grpetr.task.db.dao.DAOFactory;
import com.grpetr.task.db.dao.HallDAO;
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

public class DeleteHallCommand extends Command {

    private static final Logger log = Logger.getLogger(DeleteHallCommand.class);
    private static final long serialVersionUID = 3680084401489477421L;
    public DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request,
                                 HttpServletResponse response) throws AppException, IOException, ServletException {
        log.debug("Delete hall Command starts");
        request.getSession().removeAttribute("sendRedirectExpositions");
        request.getSession().removeAttribute("sendRedirectHalls");
        // delete hall
        boolean deleteHall = false;
        Integer hallId = null;
        String forward = Path.PAGE__ERROR_PAGE;
        Connection con = null;
        try {
            hallId = Integer.parseInt(request.getParameter("hall_id"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            hallId = null;
        }
        if (hallId != null) {
            deleteHall = true;
        }
        if (deleteHall == true) {
            try {
                con = DBManager.getInstance().getConnection();
                HallDAO hallDAO = daoFactory.getHallDAO();
                if (hallDAO.deleteHall(con, hallId)) {
                    forward = Path.COMMAND__LIST_HALLS;
                    request.getSession().setAttribute("sendRedirectHalls", true);
                }
                con.commit();
            } catch (Exception e) {
                log.error("Cannot delete hall", e);
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
                throw new AppException("Cannot delete hall", e);
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
