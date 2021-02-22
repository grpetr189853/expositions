package com.grpetr.task.web.command;

import com.grpetr.task.db.DBManager;
import com.grpetr.task.db.dao.DAOFactory;
import com.grpetr.task.db.dao.HallDAO;
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
import java.util.List;

public class ListHallsCommand extends Command {
    private static final Logger log = Logger.getLogger(ListHallsCommand.class);
    private static final int PER_PAGE = 10;
    private static final long serialVersionUID = 7217578686074251001L;
    public DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws AppException, IOException, ServletException {

        log.debug("List Halls Command starts");
        int page = 1;
        if (null != request.getParameter("page")) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int offset = (page - 1) * PER_PAGE;
        // get halls list
        List<Hall> halls = null;
        Connection con = null;
        HallDAO hallDAO = daoFactory.getHallDAO();
        int numberOfHalls = 0;
        try {
            con = DBManager.getInstance().getConnection();
            halls = hallDAO.getAllHalls(con, PER_PAGE, offset);
            numberOfHalls = hallDAO.getHallsNumber(con);
            log.trace("Found in DB: halls --> " + halls);
            con.commit();
        } catch (SQLException e) {
            log.error("Cannot get Halls", e);
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
            throw new AppException("Cannot get Halls", e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        int noOfPages = (int) Math.ceil(numberOfHalls * 1.0 / PER_PAGE);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);

        // put menu items list to the request
        request.setAttribute("halls", halls);
        log.trace("Set the request attribute: halls --> " + halls);
        request.setAttribute("showHalls", true);
        log.trace("Set the request attribute: showHalls --> " + true);


        log.debug("Command finished");
        return Path.PAGE__ADMIN_HALLS;
    }

}
