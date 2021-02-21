package com.grpetr.task.db.dao;

import com.grpetr.task.db.constant.AccessLevel;
import com.grpetr.task.db.entity.User;
import org.junit.Assert;
import org.junit.Before;

import javax.security.auth.login.LoginException;
import java.sql.Connection;
import java.sql.SQLException;

public class UserDAOTest {
    private Connection connection;
    private UserDAO userDAO;
    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        connection = new DBConnection().getDBConnection();
        DAOFactory.setDaoFQN("com.grpetr.task.db.dao.mysql.MySQLDaoFactory");
        userDAO = DAOFactory.getInstance().getUserDAO();
    }
    @org.junit.Test
    public void registerLoginTest() throws SQLException, LoginException {
        userDAO.register(connection,"admin1", "admin1", "admin1", "admin1@admin.com");
        User actualUser = userDAO.login(connection, "admin1", "admin1");
        User expectedUser = new User("admin1","admin1@admin.com", AccessLevel.USER,"admin1");

        Assert.assertEquals(expectedUser.getLogin(), actualUser.getLogin());
        Assert.assertEquals(expectedUser.getName(), actualUser.getName());
        Assert.assertEquals(expectedUser.getAccessLevel(), actualUser.getAccessLevel());
        Assert.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
    }

    @org.junit.Test
    public void getUserByIdTest() throws SQLException, LoginException{
        userDAO.register(connection,"admin2", "admin2", "admin2", "admin2@admin.com");
        User actualUser = userDAO.login(connection, "admin2", "admin2");
        int id = actualUser.getUserId();
        User expectedUser = userDAO.getUserById(connection, id);

        Assert.assertEquals(expectedUser.getLogin(), actualUser.getLogin());
        Assert.assertEquals(expectedUser.getName(), actualUser.getName());
        Assert.assertEquals(expectedUser.getAccessLevel(), actualUser.getAccessLevel());
        Assert.assertEquals(expectedUser.getEmail(), actualUser.getEmail());

    }
}
