package com.grpetr.task.web.command;

import com.grpetr.task.db.DBManager;
import com.grpetr.task.db.dao.DAOFactory;
import com.grpetr.task.db.dao.ExpositionDAO;
import com.grpetr.task.db.dao.HallDAO;
import com.grpetr.task.db.entity.Exposition;
import com.grpetr.task.db.entity.Hall;
import com.grpetr.task.exception.AppException;
import com.grpetr.task.web.constants.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShowEditExpositionCommand extends Command {
    private static final long serialVersionUID = -6928837708131452174L;
    private static final Logger log = Logger.getLogger(ShowEditExpositionCommand.class);
    public DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws AppException, IOException, ServletException {
        log.debug("Edit Exposition Command starts");
        Exposition exposition = null;
        Connection con = null;
        String forward = Path.PAGE__ERROR_PAGE;
        int numberOfHalls;
        List<Hall> halls = null;
        List<Hall> selectedHalls = null;
        List selectedHallsChecked = new ArrayList();
        try {
            con = DBManager.getInstance().getConnection();
            ExpositionDAO expositionDAO = daoFactory.getExpositionDAO();
            int expositionId = Integer.parseInt(request.getParameter("exposition_id"));
            exposition = expositionDAO.getExpositionById(con, expositionId);
            request.setAttribute("exposition", exposition);
            log.trace("Set the exposition" + exposition);
            HallDAO hallDAO = daoFactory.getHallDAO();
            numberOfHalls = hallDAO.getHallsNumber(con);
            halls = hallDAO.getAllHalls(con, numberOfHalls, 0);
            // put halls items to the request
            request.setAttribute("halls", halls);
            log.trace("Set the request attribute: halls --> " + halls);
            log.trace("Found in DB: halls --> " + halls);

            selectedHalls = expositionDAO.getExpositionHallsByExpositionId(con, expositionId);
            // put halls items to the request
            request.setAttribute("selectedHalls", selectedHalls);
            log.trace("Set the request attribute: selectedHalls --> " + selectedHalls);
            log.trace("Found in DB: selectedHalls --> " + selectedHalls);
            int i = 0;
            for (Hall hall : halls) {
                selectedHallsChecked.add(i, 0);
                for (Hall hall1 : selectedHalls) {
                    if (hall.getId() == hall1.getId()) {
                        selectedHallsChecked.set(i, 1);
                    }

                }
                ++i;
            }
            request.setAttribute("selectedHallsChecked", selectedHallsChecked);
            request.setAttribute("showEditExposition", true);
            forward = Path.PAGE__ADMIN_EDIT_EXPOSITION;
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
        return forward;
    }
}
