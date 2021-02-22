package com.grpetr.task.db.dao;

import com.grpetr.task.db.entity.Exposition;
import com.grpetr.task.db.entity.Hall;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface ExpositionDAO {

    int setNewExposition(Connection con, String theme, int ticketPrice, int ticketsCount,
                         LocalDate dateIn, LocalDate dateOut, int[] halls_ids, String imgName) throws SQLException;

    void setNewExpositionHall(Connection con, int newRowId, Integer hall_id);

    void editExposition(Connection con, int expositionId, String theme, int ticketPrice, int ticketsCount,
                        LocalDate dateIn, LocalDate dateOut, String imgName) throws SQLException;

    List<Exposition> getAllExpositions(Connection con, int limit, int offset) throws SQLException;

    Exposition getExpositionById(Connection con, int expositionId) throws SQLException;

    boolean deleteExposition(Connection con, int expositionId) throws SQLException;

    int getExpositionsNumber(Connection con) throws SQLException;

    List<Hall> getExpositionHallsByExpositionId(Connection con, int expositionId) throws SQLException;

    void deleteAllExpositionHalls(Connection con, int expositionId) throws SQLException;

    List<Exposition> getFilteredExpositions(Connection con, LocalDate dateIn, LocalDate dateOut) throws SQLException;
}
