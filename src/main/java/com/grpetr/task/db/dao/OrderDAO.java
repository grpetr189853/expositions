package com.grpetr.task.db.dao;

import com.grpetr.task.db.bean.BoughtTicket;
import com.grpetr.task.db.bean.Statistic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface OrderDAO {

    int setNewOrder(Connection con, int userId, int expositionId, String additionalInfo,
                    int cost, LocalDate dateIn, LocalDate dateOut) throws SQLException;

    int editOrder(Connection con, int userId, int expositionId, String additionalInfo,
                  int cost, LocalDate dateIn, LocalDate dateOut)
            throws SQLException;

    List<BoughtTicket> getUsersTickets(Connection con, int userId, int offset, int limit) throws SQLException;

    List<Statistic> showBoughtOrders(Connection con, int expositionId) throws SQLException;

    int checkTicketsCout(Connection con, int expositionId) throws SQLException;

    void updateTicketsCount(Connection con, int newTicketsCount, int expositionId) throws SQLException;

    int getTicketsNumber(Connection con) throws SQLException;
}
