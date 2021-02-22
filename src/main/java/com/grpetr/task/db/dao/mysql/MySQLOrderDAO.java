package com.grpetr.task.db.dao.mysql;

import com.grpetr.task.db.DBManager;
import com.grpetr.task.db.bean.BoughtTicket;
import com.grpetr.task.db.dao.OrderDAO;
import com.grpetr.task.db.entity.Order;
import com.grpetr.task.db.bean.Statistic;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MySQLOrderDAO implements OrderDAO {
    private final static String SET_NEW_ORDER =
            "INSERT INTO orders (user_id,  date_in, date_out, exposition_id, additional_info, cost) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
    private final static String EDIT_ORDER =
            "UPDATE orders SET user_id=?, places=?, class=?, date_in=?, date_out=?, " +
                    "order_apt_id=NULL, status='REQUESTED', cost=NULL " +
                    "WHERE id=?";
    private final static String GET_USERS_ORDERS =
            "SELECT o.id, o.user_id, o.exposition_id, o.date_in, o.date_out," +
                    "o.additional_info, o.cost AS cost, ex.theme  FROM " +
                    "orders AS o " +
                    "LEFT JOIN exposition as ex on o.exposition_id = ex.id " +
                    "WHERE (o.user_id=?) ORDER BY o.date_in DESC LIMIT ? OFFSET ?";
    private static final String GET_BOUGHT_ORDERS = "SELECT  name, theme,  additional_info, cost FROM orders \n" +
            "left join users on orders.user_id = users.id \n" +
            "left join exposition on exposition.id  = orders.exposition_id \n" +
            "WHERE exposition_id = ?";
    private static final String GET_TICKETS_COUNT = "SELECT tickets_count FROM exposition WHERE id = ? ";
    private static final String UPDATE_TICKETS_COUNT = "UPDATE exposition SET tickets_count = ? WHERE id = ? ";
    private static final String GET_NUMBER_OF_TICKETS = "SELECT COUNT(*) as tickets_number FROM orders";

    /**
     * @param con
     * @param userId
     * @param expositionId
     * @param additionalInfo
     * @param cost
     * @param dateIn
     * @param dateOut
     * @return
     */
    @Override
    public int setNewOrder(Connection con, int userId, int expositionId, String additionalInfo,
                           int cost, LocalDate dateIn, LocalDate dateOut) throws SQLException {
        int res = 0;
        PreparedStatement pstmt = null;
        Date sqlDateIn = Date.valueOf(dateIn);
        Date sqlDateOut = Date.valueOf(dateOut);
        pstmt = con.prepareStatement(SET_NEW_ORDER);
        pstmt.setInt(1, userId);
        pstmt.setDate(2, sqlDateIn);
        pstmt.setDate(3, sqlDateOut);
        pstmt.setInt(4, expositionId);
        pstmt.setString(5, additionalInfo);
        pstmt.setInt(6, cost);
        res = pstmt.executeUpdate();

        return res;
    }

    /**
     * @param con
     * @param userId
     * @param expositionId
     * @param additionalInfo
     * @param cost
     * @param dateIn
     * @param dateOut
     * @return
     * @throws SQLException
     */
    @Override
    public int editOrder(Connection con, int userId, int expositionId, String additionalInfo,
                         int cost, LocalDate dateIn, LocalDate dateOut)
            throws SQLException {
        int res = 0;
        Date sqlDateIn = Date.valueOf(dateIn);
        Date sqlDateOut = Date.valueOf(dateOut);
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(EDIT_ORDER);
            pstmt.setInt(1, userId);
            pstmt.setDate(2, sqlDateIn);
            pstmt.setDate(3, sqlDateOut);
            pstmt.setInt(4, expositionId);
            pstmt.setString(5, additionalInfo);
            pstmt.setInt(6, cost);
            res = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            DBManager.getInstance().rollbackAndClose(con);
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            DBManager.getInstance().commitAndClose(con);
        }
        return res;
    }

    /**
     * @param con
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    @Override
    public List<BoughtTicket> getUsersTickets(Connection con, int userId, int limit, int offset) throws SQLException {
        List<BoughtTicket> res = null;
        PreparedStatement pstmt = null;
        pstmt = con.prepareStatement(GET_USERS_ORDERS);
        pstmt.setInt(1, userId);
        pstmt.setInt(2, limit);
        pstmt.setInt(3, offset);
        ResultSet resultSet = pstmt.executeQuery();
        res = resultSetToTicketsList(resultSet);
        return res;
    }

    private List<BoughtTicket> resultSetToTicketsList(ResultSet resultSet)
            throws SQLException {
        List<BoughtTicket> res = new ArrayList<>();
        while (resultSet.next()) {
            BoughtTicket ticket = new BoughtTicket();
            ticket.setExpositionTheme(resultSet.getString("theme"));
            ticket.setDateIn(resultSet.getDate("date_in").toLocalDate());
            ticket.setDateOut(resultSet.getDate("date_out").toLocalDate());
            ticket.setExpositionId(resultSet.getInt("exposition_id"));
            ticket.setAdditionalInfo(resultSet.getString("additional_info"));
            ticket.setCost(resultSet.getInt("cost"));
            try {
                ticket.setAdditionalInfo(resultSet.getString("additional_info"));
                ticket.setCost(resultSet.getInt("cost"));
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            res.add(ticket);
        }
        return res;
    }

    /**
     * @param con
     * @param expositionId
     * @return
     */
    @Override
    public List<Statistic> showBoughtOrders(Connection con, int expositionId) throws SQLException {
        List<Statistic> res = null;
        PreparedStatement pstmt = null;
        pstmt = con.prepareStatement(GET_BOUGHT_ORDERS);
        pstmt.setInt(1, expositionId);
        ResultSet resultSet = pstmt.executeQuery();
        res = resultSetToStatisticList(resultSet);
        return res;
    }

    private List<Statistic> resultSetToStatisticList(ResultSet resultSet) throws SQLException {
        List<Statistic> res = new ArrayList<>();
        while (resultSet.next()) {
            Statistic statistic = new Statistic();
            statistic.setUserName(resultSet.getString("name"));
            statistic.setExpositionName(resultSet.getString("theme"));
            try {
                statistic.setAdditionalInfo(resultSet.getString("additional_info"));
                statistic.setCost(resultSet.getInt("cost"));
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            res.add(statistic);
        }
        return res;

    }

    /**
     * @param con
     * @param expositionId
     * @return
     */
    @Override
    public int checkTicketsCout(Connection con, int expositionId) throws SQLException {
        int res = 0;
        PreparedStatement pstmt = null;
        pstmt = con.prepareStatement(GET_TICKETS_COUNT);
        pstmt.setInt(1, expositionId);
        ResultSet resultSet = pstmt.executeQuery();
        while (resultSet.next()) {
            res = resultSet.getInt("tickets_count");
        }
        return res;
    }

    /**
     * @param con
     * @param newTicketsCount
     * @param expositionId
     */
    @Override
    public void updateTicketsCount(Connection con, int newTicketsCount, int expositionId) throws SQLException {
        PreparedStatement pstmt = null;
        pstmt = con.prepareStatement(UPDATE_TICKETS_COUNT);
        pstmt.setInt(1, newTicketsCount);
        pstmt.setInt(2, expositionId);
        pstmt.executeUpdate();

    }

    @Override
    public int getTicketsNumber(Connection con) throws SQLException {
        int res = 0;
        PreparedStatement pstmt = null;
        pstmt = con.prepareStatement(GET_NUMBER_OF_TICKETS,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = pstmt.executeQuery();
        if (resultSet.first()) {
            res = resultSet.getInt("tickets_number");
        }
        return res;
    }
}
