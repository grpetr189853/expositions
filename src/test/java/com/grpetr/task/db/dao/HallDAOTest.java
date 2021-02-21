package com.grpetr.task.db.dao;

import com.grpetr.task.db.entity.Hall;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class HallDAOTest {

    private Connection connection;
    private HallDAO hallDAO;
    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        connection = new DBConnection().getDBConnection();
        DAOFactory.setDaoFQN("com.grpetr.task.db.dao.mysql.MySQLDaoFactory");
        hallDAO = DAOFactory.getInstance().getHallDAO();
    }

    @Test
    public void setNewHallTest() throws SQLException {
        Hall actualHall = new Hall();
        actualHall.setHallName("Hall1");
        int hallId = hallDAO.setNewHall(connection, "Hall1");
        Hall expectedHall = hallDAO.getHallById(connection, hallId);

        Assert.assertEquals(expectedHall.getHallName(), actualHall.getHallName());
    }
}
