package com.grpetr.task.web.command;

import com.grpetr.task.db.DBManager;
import com.grpetr.task.db.bean.BoughtTicket;
import com.grpetr.task.db.dao.DAOFactory;
import com.grpetr.task.db.dao.OrderDAO;
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

public class ShowBoughtTicketsCommand extends Command {

    private static final Logger log = Logger.getLogger(ShowBoughtTicketsCommand.class);
    private static final long serialVersionUID = 5100157693867410541L;
    public DAOFactory daoFactory = DAOFactory.getInstance();
    private static final int PER_PAGE = 10;

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws AppException, IOException, ServletException {
        log.debug("Show bought tickets Command starts");
        int page = 1;
        if (null != request.getParameter("page")) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int offset = (page - 1) * PER_PAGE;
        int userId = Integer.parseInt(request.getParameter("userId"));
        String forward = Path.PAGE__ERROR_PAGE;
        Connection con = null;
        List<BoughtTicket> tickets;
        int numberOfTickets;
        try {
            con = DBManager.getInstance().getConnection();
            OrderDAO orderDAO = daoFactory.getOrderDAO();
            tickets = orderDAO.getUsersTickets(con, userId, PER_PAGE, offset);
            log.trace("Found in DB: tickets --> " + tickets);
            numberOfTickets = orderDAO.getTicketsNumber(con);
            forward = Path.PAGE__USER_SHOW_BOUGHT_ORDERS;
            con.commit();
        } catch (Exception e){
            log.error("Cannot get tickets list", e);
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
            throw new AppException("Cannot obtain tickets list");
        } finally {
            if(con != null){
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        request.setAttribute("tickets", tickets);
        log.trace("Set the request attribute: tickets --> " + tickets);
        int noOfPages = (int) Math.ceil(numberOfTickets * 1.0 / PER_PAGE);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
        log.debug("Command finished");
        return forward;
    }
}
