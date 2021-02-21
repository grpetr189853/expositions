package com.grpetr.task.db.dao.mysql;

import com.grpetr.task.db.DBManager;
import com.grpetr.task.db.dao.HallDAO;
import com.grpetr.task.db.entity.Hall;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MySQLHallDAO implements HallDAO {
    private static final String SET_NEW_HALL = "INSERT INTO halls (hall_name) VALUES (?) ";
    private final static String GET_ALL_HALLS =
            "SELECT id, hall_name FROM halls LIMIT ? OFFSET ? ";
    private static final String DELETE_HALL_BY_YALL_ID = "DELETE FROM halls WHERE id = ?";
    private static final String GET_NUMBER_OF_HALLS = "SELECT COUNT(*) as halls_number FROM halls";
    private static final String CHECK_WHETHER_HALL_IS_OCCUPIED = "SELECT COUNT (*) FROM ( " +
            "SELECT exposition.date_in, exposition.date_out FROM halls h " +
            "LEFT JOIN exposition_halls ON exposition_halls.halls_id = h.id " +
            "LEFT JOIN exposition ON exposition.id = exposition_halls.exposition_id " +
            "WHERE h.id = ? and ? between exposition.date_in and exposition.date_out) as u";
    private static final String GET_HALL_BY_ID = "SELECT id, hall_name from halls WHERE id = ?";

    /**
     *
     * @param con
     * @param hallName
     * @return
     */
    @Override
    public int setNewHall(Connection con, String hallName) throws SQLException {
        int newRowId = 0;
        PreparedStatement pstmt = null;
        pstmt = con.prepareStatement(SET_NEW_HALL, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, hallName);
        int affectedRows = pstmt.executeUpdate();

        if (affectedRows == 1) {
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                newRowId = generatedKeys.getInt(1);
            }
        }
        return newRowId;
    }

    /**
     *
     * @param con
     * @param hallId
     * @return
     * @throws SQLException
     */
    public Hall getHallById(Connection con, int hallId) throws SQLException {
        Hall hall = null;
        PreparedStatement pstmt = con.prepareStatement(GET_HALL_BY_ID);
        pstmt.setInt(1, hallId);
        ResultSet resultSet = pstmt.executeQuery();
        hall = resultSetToHall(resultSet);
        return hall;
    }

    private Hall resultSetToHall(ResultSet resultSet) {
        Hall hall = new Hall();
        try {
            resultSet.next();
            hall.setId(resultSet.getInt("id"));
            hall.setHallName(resultSet.getString("hall_name"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return hall;
    }

    /**
     *
     * @param con
     * @param limit
     * @param offset
     * @return
     */
    @Override
    public List<Hall> getAllHalls(Connection con, int limit, int offset) throws SQLException {
        List<Hall> res = null;
        PreparedStatement pstmt = null;
        pstmt = con.prepareStatement(GET_ALL_HALLS);
        pstmt.setInt(1, limit);
        pstmt.setInt(2, offset);
        ResultSet resultSet = pstmt.executeQuery();
        res = resultSetToHallsList(resultSet);
        return res;
    }

    private List<Hall> resultSetToHallsList(ResultSet resultSet) throws SQLException{
        List<Hall> res = new ArrayList<>();
        while (resultSet.next()) {
            Hall hall = new Hall();
            hall.setId(resultSet.getInt("id"));
            hall.setHallName(resultSet.getString("hall_name"));
            res.add(hall);
        }
        return res;
    }

    /**
     *
     * @param con
     * @param hallId
     * @return
     * @throws Exception
     */
    @Override
    public boolean deleteHall(Connection con, int hallId) throws Exception{
        PreparedStatement pstmt = null;
        pstmt = con.prepareStatement(DELETE_HALL_BY_YALL_ID);
        pstmt.setInt(1, hallId);
        pstmt.executeUpdate();
        return true;
    }

    /**
     *
     * @param con
     * @return
     * @throws SQLException
     */
    @Override
    public int getHallsNumber(Connection con) throws SQLException {
        int res = 0;
        PreparedStatement pstmt = null;
        pstmt = con.prepareStatement(GET_NUMBER_OF_HALLS,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = pstmt.executeQuery();
        if (resultSet.first()) {
            res = resultSet.getInt("halls_number");
        }
        return res;
    }

    /**
     *
     * @param con
     * @param hall_id
     * @param checkDate
     * @return
     * @throws SQLException
     */
    public boolean checkWhetherHallIsOccupied(Connection con, int hall_id, LocalDate checkDate) throws SQLException {
        boolean res = false;
        PreparedStatement pstmt = null;
        Date sqlCheckDate = Date.valueOf(checkDate);
        pstmt = con.prepareStatement(CHECK_WHETHER_HALL_IS_OCCUPIED,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        pstmt.setInt(1, hall_id);
        pstmt.setDate(2, sqlCheckDate);
        ResultSet resultSet = pstmt.executeQuery();
        if(resultSet.next()){
            if(resultSet.getInt(1) == 1)
                res = true;
        }
        return res;
    }
}
