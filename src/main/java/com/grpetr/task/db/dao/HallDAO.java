package com.grpetr.task.db.dao;

import com.grpetr.task.db.entity.Hall;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface HallDAO {

    int setNewHall(Connection con, String hallName) throws SQLException;

    Hall getHallById(Connection con, int hallId) throws SQLException;

    List<Hall> getAllHalls(Connection con, int limit, int offset) throws SQLException;

    boolean deleteHall(Connection con, int hallId) throws Exception;

    int getHallsNumber(Connection con) throws SQLException;

    boolean checkWhetherHallIsOccupied(Connection con, int hall_id, LocalDate checkDate) throws SQLException;
}
