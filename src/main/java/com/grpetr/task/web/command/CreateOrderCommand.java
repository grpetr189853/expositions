package com.grpetr.task.web.command;

import com.grpetr.task.db.DBManager;
import com.grpetr.task.db.dao.DAOFactory;
import com.grpetr.task.db.dao.ExpositionDAO;
import com.grpetr.task.db.dao.OrderDAO;
import com.grpetr.task.db.entity.Exposition;
import com.grpetr.task.db.entity.User;
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

public class CreateOrderCommand extends Command {

    private static final Logger log = Logger.getLogger(CreateOrderCommand.class);
    private static final long serialVersionUID = -7763509211267917958L;
    public DAOFactory daoFactory = DAOFactory.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request,
                                 HttpServletResponse response) throws AppException, IOException, ServletException {
        log.debug("Create Order Command starts");
        String forward = Path.PAGE__ERROR_PAGE;
        OrderDAO orderDAO = daoFactory.getOrderDAO();
        Connection con = null;
        int affectedRows = 0;
        try {
            con = DBManager.getInstance().getConnection();
            int expositionId = Integer.parseInt(request.getParameter("exposition_id"));
            User user = (User) request.getSession().getAttribute("user");
            String additional_info = request.getParameter("additional_info");
            ExpositionDAO expositionDAO = daoFactory.getExpositionDAO();
            Exposition exposition = expositionDAO.getExpositionById(con, expositionId);
            request.setAttribute("exposition", exposition);
            int ticketsCount = orderDAO.checkTicketsCout(con, expositionId);
            if (ticketsCount > 0) {
                affectedRows = orderDAO.setNewOrder(con, user.getUserId(), expositionId, additional_info, exposition.getTicketPrice(), exposition.getDateIn(), exposition.getDateOut());
                ticketsCount--;

                if (affectedRows == 1) {
                    forward = Path.COMMAND__LIST_EXPOSITIONS;
                }
                orderDAO.updateTicketsCount(con, ticketsCount, expositionId);
                log.trace("Succesfully updated tickets count");
            } else {
                log.error("There are no tickets for this exposition");
                throw new AppException("There are no tickets for this exposition");
            }
            con.commit();
        } catch (Exception e) {
            log.error("Cannot create order", e);
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
            throw new AppException("Cannot create order", e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        log.debug("Command finished");
        return new RedirectResult(forward);
    }
}
