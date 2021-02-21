package com.grpetr.task.web.command;

import com.grpetr.task.db.DBManager;
import com.grpetr.task.db.constant.AccessLevel;
import com.grpetr.task.db.dao.DAOFactory;
import com.grpetr.task.db.dao.ExpositionDAO;
import com.grpetr.task.db.entity.Exposition;
import com.grpetr.task.exception.AppException;
import com.grpetr.task.web.constants.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ListExpositionsCommand extends Command {

    private static final Logger log = Logger.getLogger(ListExpositionsCommand.class);
    private static final int PER_PAGE = 10;
    private static final long serialVersionUID = -6437114848985082129L;
    public DAOFactory daoFactory = DAOFactory.getInstance();


    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws AppException, IOException, ServletException {

        log.debug("List Expositions Command starts");
        int page = 1;
        if (null != request.getParameter("page")) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int offset = (page - 1) * PER_PAGE;
        // get expositions list
        List<Exposition> expositions = null;
        Connection con = null;
        int numberOfExpositions = 0;
        try {
            con = DBManager.getInstance().getConnection();
            ExpositionDAO expositionDAO = daoFactory.getExpositionDAO();
            expositions = expositionDAO.getAllExpositions(con, PER_PAGE, offset);
            numberOfExpositions = expositionDAO.getExpositionsNumber(con);
            log.trace("Found in DB: expositions --> " + expositions);
            con.commit();
        } catch (SQLException e){
            log.error("Cannot get expositions list", e);
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
            throw new AppException("Cannot obtain expositions list",e);
        } finally {
            if(con != null){
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


        int noOfPages = (int) Math.ceil(numberOfExpositions * 1.0 / PER_PAGE);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);

        // put menu items list to the request
        request.setAttribute("expositions", expositions);
        log.trace("Set the request attribute: expositions --> " + expositions);
        request.setAttribute("showExpositions", true);
        log.trace("Set the request attribute: showExpositions --> " + true);


        HttpSession session = request.getSession();
        log.debug("Command finished");
        if(session.getAttribute("userRole") == AccessLevel.ADMIN){
            return Path.PAGE__ADMIN_EXPOSITIONS;
        } else if (session.getAttribute("userRole") == AccessLevel.USER) {
            return Path.PAGE__HOME_USER_JSP;
        }
        return Path.PAGE__NON_AUTORIZED_USER_EXPOSITIONS;
    }

}
