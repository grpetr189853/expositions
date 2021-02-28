package com.grpetr.task.db.dao.mysql;

import com.grpetr.task.db.constant.AccessLevel;
import com.grpetr.task.db.DBManager;
import com.grpetr.task.db.constant.Language;
import com.grpetr.task.db.dao.UserDAO;
import com.grpetr.task.db.entity.User;

import javax.security.auth.login.LoginException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object for User entity.
 */
public class MySQLUserDAO implements UserDAO {

    private static final String LOGIN_ATTEMPT =
            "SELECT id, access_level, users.name, email FROM users WHERE login=? AND passhash=?";
    private static final String REGISTER_ATTEMPT =
            "INSERT INTO users (login, passhash, email, access_level, name, user_language) " +
                    "VALUES (?, ?, ?, 'user', ?, 'en')";
    private static final String GET_USER_BY_ID =
            "SELECT id, email, login, access_level, name FROM users " +
                    "WHERE id=?";
    private static final String GET_ALL_USERS =
            "SELECT id, email, login, access_level, name FROM users " +
                    "ORDER BY access_level LIMIT ? OFFSET ?";
    private static final String GET_NUMBER_OF_USERS = "SELECT COUNT(*) as users_number FROM users";
    private static final String SET_USER_LANGUAGE = "UPDATE users SET user_language = ? WHERE id = ?";
    private static final String GET_USER_LANGUAGE = "SELECT user_language FROM users WHERE id = ?";

    /**
     * Logs in User
     * @param login
     * @param password
     * @return whether User is logged or not
     * @throws LoginException
     */
    @Override
    public User login(Connection con, String login, String password) throws SQLException, LoginException {
        User user = new User(login);
        PreparedStatement pstmt = null;

        pstmt = con.prepareStatement(LOGIN_ATTEMPT,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        pstmt.setString(1, login);
        pstmt.setString(2, hash(password));
        ResultSet resultSet = pstmt.executeQuery();
        boolean completed = resultSet.first();
        if (completed) {
            user.setUserId(resultSet.getInt("id"));
            user.setEmail(resultSet.getString("email"));
            user.setAccessLevel(AccessLevel.valueOf(resultSet.getString("access_level").toUpperCase()));
            user.setName(resultSet.getString("name"));
        } else {
            throw new LoginException();
        }
        return user;
    }

    /**
     * @param input
     * @return
     */
    private String hash(String input) {
        String md5Hashed = null;
        if (null == input) {
            return null;
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(input.getBytes(), 0, input.length());
            md5Hashed = new BigInteger(1, digest.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5Hashed;
    }


    /**
     * Register User
     * @param name
     * @param login
     * @param password
     * @param email
     * @return whether User is registered successfully or not
     */
    @Override
    public boolean register(Connection con, String name, String login, String password, String email) throws SQLException {
        boolean res = false;
        PreparedStatement pstmt = null;
        pstmt = con.prepareStatement(REGISTER_ATTEMPT);
        pstmt.setString(1, login);
        pstmt.setString(2, hash(password));
        pstmt.setString(3, email);
        pstmt.setString(4, name);
        pstmt.executeUpdate();
        res = true;
        return res;
    }

    /**
     * Gets User by Id
     * @param id
     * @return User entity.
     * @throws SQLException
     * @throws LoginException
     */
    @Override
    public User getUserById(Connection con, int id) throws SQLException, LoginException {
        User user = null;
        PreparedStatement pstmt = null;
        pstmt = con.prepareStatement(GET_USER_BY_ID,
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        pstmt.setInt(1, id);
        ResultSet resultSet = pstmt.executeQuery();
        if (resultSet.first()) {
            String login = resultSet.getString("login");
            user = new User(login);
            user.setUserId(resultSet.getInt("id"));
            user.setEmail(resultSet.getString("email"));
            user.setAccessLevel(AccessLevel.valueOf(resultSet.getString("access_level").toUpperCase()));
            user.setName(resultSet.getString("name"));
        }

        return user;
    }

    /**
     * Gets all Users
     * @param limit
     * @param offset
     * @return
     * @throws SQLException
     * @throws LoginException
     */
    @Override
    public List<User> getAllUsers(Connection con, int limit, int offset) throws SQLException {
        List<User> usersList = null;
        PreparedStatement pstmt = null;
        pstmt = con.prepareStatement(GET_ALL_USERS);
        pstmt.setInt(1, limit);
        pstmt.setInt(2, offset);
        ResultSet resultSet = pstmt.executeQuery();
        usersList = resultSetToUsersList(resultSet);
        return usersList;
    }

    private List<User> resultSetToUsersList(ResultSet resultSet) throws SQLException {
        List<User> res = new ArrayList<>();
        while (resultSet.next()) {
            String login = resultSet.getString("login");
            User user = new User(login);
            user.setUserId(resultSet.getInt("id"));
            user.setLogin(login);
            user.setName(resultSet.getString("name"));
            user.setEmail(resultSet.getString("email"));
            user.setAccessLevel(AccessLevel.valueOf(resultSet.getString("access_level").toUpperCase()));
            res.add(user);
        }
        return res;
    }

    /**
     * Gets Users Number
     * @param con
     * @return
     * @throws SQLException
     */
    @Override
    public int getUsersNumber(Connection con) throws SQLException {
        int res = 0;
        PreparedStatement pstmt = null;
        pstmt = con.prepareStatement(GET_NUMBER_OF_USERS,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = pstmt.executeQuery();
        if (resultSet.first()) {
            res = resultSet.getInt("users_number");
        }
        return res;
    }

    /**
     * Sets User language
     * @param con
     * @param user_id
     * @param language
     * @throws SQLException
     */
    @Override
    public void setUserLanguage(Connection con, int user_id, String language) throws SQLException {
        PreparedStatement pstmt = null;
        pstmt = con.prepareStatement(SET_USER_LANGUAGE);
        pstmt.setInt(2,user_id);
        pstmt.setString(1, language);
        pstmt.executeUpdate();
    }

    /**
     * Gets User language
     * @param con
     * @param user_id
     * @return
     * @throws SQLException
     */
    @Override
    public Language getUserLanguage(Connection con, int user_id) throws SQLException {
        PreparedStatement pstmt = null;
        pstmt = con.prepareStatement(GET_USER_LANGUAGE);
        pstmt.setInt(1,user_id);
        Language language = null;
        ResultSet resultSet = pstmt.executeQuery();
        while (resultSet.next()){
            language = Language.valueOf(resultSet.getString("user_language"));
        }
        return language;
    }
}

