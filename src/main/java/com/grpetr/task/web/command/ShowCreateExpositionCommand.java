package com.grpetr.task.web.command;

import com.grpetr.task.db.DBManager;
import com.grpetr.task.db.dao.DAOFactory;
import com.grpetr.task.db.dao.HallDAO;
import com.grpetr.task.db.dao.HallDao;
import com.grpetr.task.db.entity.Hall;
import com.grpetr.task.exception.AppException;
import com.grpetr.task.exception.DBException;
import com.grpetr.task.web.constants.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ShowCreateExpositionCommand extends Command {
    private static final Logger log = Logger.getLogger(ShowCreateExpositionCommand.class);
    private static final long serialVersionUID = -2546605927643439481L;
    public DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws AppException, IOException, ServletException {

        log.debug("Show Create Exposition Command starts");
        Connection con = null;
        List<Hall> halls = null;
        int numberOfHalls;
        String forward = Path.PAGE__ERROR_PAGE;
        // get halls list
        try {
            con  = DBManager.getInstance().getConnection();
            HallDAO hallDAO = daoFactory.getHallDAO();
            numberOfHalls = hallDAO.getHallsNumber(con);
            halls = hallDAO.getAllHalls(con, numberOfHalls, 0);
            log.trace("Found in DB: halls --> " + halls);
            forward = Path.PAGE__ADMIN_CREATE_EXPOSITION;
            con.commit();
        } catch (Exception e){
            log.error("Cannot get halls list", e);
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
            throw new AppException("Cannot obtain halls list");
        } finally {
            if(con != null){
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


        // put halls items to the request
        request.setAttribute("halls", halls);
        log.trace("Set the request attribute: halls --> " + halls);
        request.setAttribute("showCreateExposition", true);
        log.trace("Set the request attribute: showHalls --> " + true);


        log.debug("Command finished");
        return forward;
    }

}
