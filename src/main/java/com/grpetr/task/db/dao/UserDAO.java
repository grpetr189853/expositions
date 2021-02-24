package com.grpetr.task.db.dao;

import com.grpetr.task.db.constant.Language;
import com.grpetr.task.db.entity.User;

import javax.security.auth.login.LoginException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public interface UserDAO {

    User login(Connection con, String login, String password) throws SQLException, LoginException;

    boolean register(Connection con, String name, String login, String password, String email) throws SQLException;

    User getUserById(Connection con, int id) throws SQLException, LoginException;

    List<User> getAllUsers(Connection con, int limit, int offset) throws SQLException;

    int getUsersNumber(Connection con) throws SQLException;

    public void setUserLanguage(Connection con, int user_id, String language) throws SQLException;

    public Language getUserLanguage(Connection con, int user_id) throws SQLException;
}
