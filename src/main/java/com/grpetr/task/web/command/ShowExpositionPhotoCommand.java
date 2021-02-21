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

public class ShowExpositionPhotoCommand extends Command {
    private static final Logger log = Logger.getLogger(ShowExpositionPhotoCommand.class);
    private static final long serialVersionUID = 6195593925878671846L;
    public DAOFactory daoFactory = DAOFactory.getInstance();


    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws AppException, IOException, ServletException {
        log.debug("Show Exposition Photo Command starts");
        // put menu items list to the request

        int expositionId = Integer.parseInt(request.getParameter("exposition_id"));
        String forward = Path.PAGE__ERROR_PAGE;
        Connection con = null;
        Exposition exposition  = null;
        try {
            con = DBManager.getInstance().getConnection();
            ExpositionDAO expositionDAO  = daoFactory.getExpositionDAO();
            exposition = expositionDAO.getExpositionById(con, expositionId);
            forward = Path.PAGE__ADMIN_EXPOSITION_PHOTO;
            con.commit();
        } catch(SQLException e){
            log.error("Cannot get exposition photo", e);
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            if(con != null){
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            throw new AppException("Cannot obtain expositions photo",e);
        } finally {
            if(con != null){
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        request.setAttribute("exposition", exposition);
        log.trace("Set the request attribute: exposition --> " + exposition);

        log.debug("Command finished");
        return forward;
    }
}
