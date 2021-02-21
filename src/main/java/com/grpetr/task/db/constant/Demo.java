package com.grpetr.task.db.constant;

import com.grpetr.task.db.DBManager;
import com.grpetr.task.db.entity.Exposition;
import com.grpetr.task.db.entity.Order;
import com.grpetr.task.db.entity.User;

import javax.security.auth.login.LoginException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Demo {
    public static void main(String[] args) throws LoginException, SQLException {
        DBManager dbManager = new DBManager();
        Connection con = DBManager.getInstance().getConnection();
//        User user = new User("petrenko","grpetr","grpetr189853@gmail.com","123");

//        User user1 = dbManager.getUser(con, 6);
//        System.out.println(user1);
//        System.out.println(user1.getAccessLevel());
//        List<User> users = dbManager.getAllUsers(con, 1, 0);
//        System.out.println(users);
//        List<Order> orders = dbManager.getUsersTickets(con,4, 0, 2);
//        System.out.println(orders);
//        List<Exposition> expositions = dbManager.getAllExpositions(con, 2,0);
//        System.out.println(expositions);
//        dbManager.setNewExposition(con,"bouyobyo",500, LocalDate.of(2020,05,05),LocalDate.of(2020,05,05));
//        dbManager.setNewOrder(con, 4,2, "dkdfjkdjfj", 500, LocalDate.of(2020,05,05),LocalDate.of(2020,05,05));
    }
}
