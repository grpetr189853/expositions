package com.grpetr.task.db.dao;

import com.grpetr.task.db.entity.Exposition;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ExpositionDAOTest {
    private Connection connection;
    private ExpositionDAO expositionDAO;
    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        connection = new DBConnection().getDBConnection();
        DAOFactory.setDaoFQN("com.grpetr.task.db.dao.mysql.MySQLDaoFactory");
        expositionDAO = DAOFactory.getInstance().getExpositionDAO();
    }

    @Test
    public void setNewExpositionTest() throws SQLException{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate sqlDateIn = LocalDate.parse("2021-01-01", formatter);
        LocalDate sqlDateOut = LocalDate.parse("2021-02-01", formatter);
        Exposition actualExposition = new Exposition();
        actualExposition.setTheme("Theme1");
        actualExposition.setTicketPrice(500);
        actualExposition.setTicketsCount(200);
        actualExposition.setDateIn(sqlDateIn);
        actualExposition.setDateOut(sqlDateOut);
        actualExposition.setHallsNames("1,2");
        actualExposition.setImgName("");
        int expositionId  = expositionDAO.setNewExposition(connection, "Theme1", 500,200,sqlDateIn, sqlDateOut, new int[]{1,2}, "");
        Exposition expectedExposition = expositionDAO.getExpositionById(connection, expositionId);
        Assert.assertEquals(expectedExposition.getTheme(), actualExposition.getTheme());
        Assert.assertEquals(expectedExposition.getTicketPrice(), expectedExposition.getTicketPrice());
        Assert.assertEquals(expectedExposition.getTicketsCount(), actualExposition.getTicketsCount());
        Assert.assertEquals(expectedExposition.getDateIn(), actualExposition.getDateIn());
        Assert.assertEquals(expectedExposition.getDateOut(), actualExposition.getDateOut());
        Assert.assertEquals(expectedExposition.getImgName(), actualExposition.getImgName());
    }

    @Test
    public void editExpositionTest() throws SQLException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate sqlDateIn = LocalDate.parse("2021-01-01", formatter);
        LocalDate sqlDateOut = LocalDate.parse("2021-02-01", formatter);
        Exposition actualExposition = new Exposition();
        actualExposition.setTheme("Theme2");
        actualExposition.setTicketPrice(100);
        actualExposition.setTicketsCount(100);
        actualExposition.setDateIn(sqlDateIn);
        actualExposition.setDateOut(sqlDateOut);
        actualExposition.setHallsNames("1,2");
        actualExposition.setImgName("");
        int expositionId  = expositionDAO.setNewExposition(connection, "Theme1", 500,200,sqlDateIn, sqlDateOut, new int[]{1,2}, "");
        expositionDAO.editExposition(connection, expositionId, "Theme2", 100, 100, sqlDateIn, sqlDateOut,"");
        Exposition expectedExposition = expositionDAO.getExpositionById(connection, expositionId);
        Assert.assertEquals(expectedExposition.getTheme(), actualExposition.getTheme());
        Assert.assertEquals(expectedExposition.getTicketPrice(), expectedExposition.getTicketPrice());
        Assert.assertEquals(expectedExposition.getTicketsCount(), actualExposition.getTicketsCount());
        Assert.assertEquals(expectedExposition.getDateIn(), actualExposition.getDateIn());
        Assert.assertEquals(expectedExposition.getDateOut(), actualExposition.getDateOut());
        Assert.assertEquals(expectedExposition.getImgName(), actualExposition.getImgName());
    }
}
