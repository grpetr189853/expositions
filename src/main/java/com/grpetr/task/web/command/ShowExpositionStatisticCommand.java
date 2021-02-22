package com.grpetr.task.web.command;

import com.grpetr.task.db.DBManager;
import com.grpetr.task.db.dao.DAOFactory;
import com.grpetr.task.db.dao.OrderDAO;
import com.grpetr.task.db.bean.Statistic;
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

public class ShowExpositionStatisticCommand extends Command {
    private static final Logger log = Logger.getLogger(ShowExpositionStatisticCommand.class);
    private static final long serialVersionUID = 6482946015839864285L;
    public DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws AppException, IOException, ServletException {
        log.debug("Show Exposition Statistic Command starts");
        // put menu items list to the request

        int expositionId = Integer.parseInt(request.getParameter("exposition_id"));
        String forward = Path.PAGE__ERROR_PAGE;
        Connection con = null;
        List<Statistic> statistics = null;
        try {
            con = DBManager.getInstance().getConnection();
            OrderDAO orderDAO = daoFactory.getOrderDAO();
            statistics = orderDAO.showBoughtOrders(con, expositionId);
            log.trace("Found in DB: statistic --> " + statistics);
            forward = Path.PAGE__ADMIN_SHOW_STATISTIC;
            con.commit();
        } catch (SQLException e) {
            log.error("Cannot get statistic list", e);
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
            throw new AppException("Cannot obtain statistic list", e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        request.setAttribute("statistics", statistics);
        log.trace("Set the request attribute: statistic --> " + statistics);

        request.setAttribute("showExpositionStatistic", true);
        log.trace("Set the request attribute: showExpositionStatistic --> " + true);


        log.debug("Command finished");
        return forward;

    }
}
