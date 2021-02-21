package com.grpetr.task.db.dao;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class OrderDAOTest {
    private Connection connection;
    private OrderDAO orderDAO;
    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        connection = new DBConnection().getDBConnection();
        DAOFactory.setDaoFQN("com.grpetr.task.db.dao.mysql.MySQLDaoFactory");
        orderDAO = DAOFactory.getInstance().getOrderDAO();
    }

    @Test
    public void setNewOrderTest() throws SQLException {

    }
}
