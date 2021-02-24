package com.grpetr.task.db.entity;

import com.grpetr.task.db.constant.AccessLevel;
import com.grpetr.task.db.constant.Language;
import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private final static Logger LOG = Logger.getLogger(User.class);
    private int userId;
    private String login;
    private String email;
    private AccessLevel accessLevel;
    private String name;
    private Language language;

    public User(String login) {
        this.login = login;
    }

    public User(String login, String email, AccessLevel accessLevel, String name) {
        this.login = login;
        this.email = email;
        this.accessLevel = accessLevel;
        this.name = name;
    }

    /*
    public User(String name, String login, String email, String password) throws LoginException{
        this.login = login;
        if (checkEmail(email)) {
            boolean completed = DBManager.getInstance().register(DBManager.getInstance().getConnection(), name, this.login, hash(password), email);
            if (!completed) {
                throw new LoginException(String.valueOf(1));
            }
        }
        else {
            throw new LoginException(String.valueOf(0));
        }
    }

    public User logIn(String login, String password) throws LoginException {
        return DBManager.getInstance().login(login, hash(password));
    }
    */
    private boolean checkEmail(String email) {
        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

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


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", accessLevel=" + accessLevel +
                ", name='" + name + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
