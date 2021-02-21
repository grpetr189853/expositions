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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FilterExpositionsCommand extends Command {
    private static final Logger log = Logger.getLogger(FilterExpositionsCommand.class);
    private static final int PER_PAGE = 10;
    private static final long serialVersionUID = -65694454884076729L;
    public DAOFactory daoFactory = DAOFactory.getInstance();
    public final static String FORMATTER_PATTERN = "yyyy-MM-dd";
    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws AppException, IOException, ServletException {
        log.debug("Filter Expositions Command starts");

        List<Exposition> expositions = null;
        Connection con = null;
        String forward = Path.PAGE__ERROR_PAGE;
        int numberOfExpositions = 0;
        String date_in;
        String date_out;
        try {
            con = DBManager.getInstance().getConnection();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMATTER_PATTERN);
            String errorMessage = null;
            date_in = request.getParameter("date_in");
            date_out = request.getParameter("date_out");
            if(date_in.isEmpty() || date_out.isEmpty()){
                errorMessage = "Exposition Dates cannot be empty";
                request.setAttribute("errorMessage", errorMessage);
                log.error("errorMessage --> " + errorMessage);
                forward = Path.PAGE__ERROR_PAGE;
                return forward;
            }


            LocalDate dateIn = LocalDate.parse(date_in, formatter);
            LocalDate dateOut = LocalDate.parse(date_out, formatter);

            con = DBManager.getInstance().getConnection();
            ExpositionDAO expositionDAO = daoFactory.getExpositionDAO();
            expositions = expositionDAO.getFilteredExpositions(con, dateIn, dateOut);
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
            throw new AppException("Cannot obtain expositions list", e);
        } finally {
            if(con != null){
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // put menu items list to the request
        request.setAttribute("expositions", expositions);
        log.trace("Set the request attribute: expositions --> " + expositions);
        request.setAttribute("showExpositions", true);
        log.trace("Set the request attribute: showExpositions --> " + true);
        request.setAttribute("date_in", date_in);
        request.setAttribute("date_out", date_out);
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
