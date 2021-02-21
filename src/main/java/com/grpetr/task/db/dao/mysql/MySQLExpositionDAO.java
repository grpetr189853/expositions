package com.grpetr.task.db.dao.mysql;

import com.grpetr.task.db.DBManager;
import com.grpetr.task.db.dao.ExpositionDAO;
import com.grpetr.task.db.entity.Exposition;
import com.grpetr.task.db.entity.Hall;
import sun.security.pkcs11.Secmod;

import java.net.ConnectException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Connection.TRANSACTION_READ_UNCOMMITTED;

/**
 * Data access object for Exposition entity.
 */
public class MySQLExpositionDAO implements ExpositionDAO {
    private final static String SET_NEW_EXPOSITION =
            "INSERT INTO exposition (theme, date_in, date_out, ticket_price, tickets_count,img_name) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
    private final static String GET_ALL_EXPOSITIONS =
            "SELECT exposition.id, theme, ticket_price, date_in, date_out, tickets_count, img_name, " +
                    "(select GROUP_CONCAT(h.hall_name) from halls h " +
                    "LEFT JOIN exposition_halls ON exposition_halls.halls_id = h.id " +
                    "where exposition_halls.exposition_id = exposition.id " +
                    "group by exposition_id) as halls_names " +
                    "FROM exposition " +
//                    "LEFT JOIN exposition_halls ON exposition.id = exposition_halls.exposition_id " +
//                    "LEFT JOIN halls ON exposition_halls.halls_id = halls.id " +
                    "ORDER BY date_in LIMIT ? OFFSET ?";
    private static final String GET_EXPOSITION_BY_ID = "SELECT exposition.id, theme, ticket_price, date_in, date_out, tickets_count, " +
            "(select GROUP_CONCAT(h.hall_name) from halls h " +
            "LEFT JOIN exposition_halls ON exposition_halls.halls_id = h.id " +
            "where exposition_halls.exposition_id = exposition.id " +
            "group by exposition_id) as halls_names, img_name " +
            " FROM exposition WHERE id = ?";

    private static final String DELETE_EXPOSITION_HALLS_BY_EXPOSITION_ID = "DELETE FROM exposition_halls WHERE exposition_id = ?";
    private static final String DELETE_EXPOSITION_BY_EXPOSITION_ID = "DELETE FROM exposition WHERE id = ?";
    private final static String SET_NEW_EXPOSITION_HALL =
            "INSERT INTO exposition_halls (exposition_id, halls_id) VALUES (?,?)";
    private static final String GET_NUMBER_OF_EXPOSITIONS = "SELECT COUNT(*) as expositions_number FROM exposition";
    private static final String GET_EXPOSITION_HALLS = "select halls.id as hall_Ids, halls.hall_name as hall_name from halls " +
                                                        "left join exposition_halls on halls.id = exposition_halls.halls_id " +
                                                        "where exposition_halls.exposition_id = ?";
    private static final String EDIT_EXPOSITION = "UPDATE exposition SET theme = ?, ticket_price = ?, tickets_count = ?, date_in =?, date_out = ?, img_name = ? WHERE id =?";
    private static final String GET_FILTERED_EXPOSITIONS = "SELECT exposition.id, theme, ticket_price, date_in, date_out, tickets_count, img_name, " +
            "(select GROUP_CONCAT(h.hall_name) from halls h " +
            "LEFT JOIN exposition_halls ON exposition_halls.halls_id = h.id " +
            "where exposition_halls.exposition_id = exposition.id " +
            "group by exposition_id) as halls_names " +
            "FROM exposition " +
            "LEFT JOIN exposition_halls ON exposition.id = exposition_halls.exposition_id " +
            "LEFT JOIN halls ON exposition_halls.halls_id = halls.id " +
            "where date_in between ? and ? and date_out between ? and ? " +
            "ORDER BY date_in ";;


    /**
     * Sets new Exposition
     * @param theme
     * @param ticketPrice
     * @param dateIn
     * @param dateOut
     * @param halls_ids
     * @return id of newly created Exposition
     * @throws SQLException
     */
    @Override
    public int setNewExposition(Connection con, String theme, int ticketPrice, int ticketsCount,
                                LocalDate dateIn, LocalDate dateOut, int[] halls_ids, String imgName) throws SQLException {
        Date sqlDateIn = Date.valueOf(dateIn);
        Date sqlDateOut = Date.valueOf(dateOut);
        int newRowId = 0;
        PreparedStatement pstmt = null;

        pstmt = con.prepareStatement(SET_NEW_EXPOSITION, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, theme);
        pstmt.setDate(2, sqlDateIn);
        pstmt.setDate(3, sqlDateOut);
        pstmt.setInt(4, ticketPrice);
        pstmt.setInt(5,ticketsCount);
        pstmt.setString(6,imgName);
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
     * Sets new Exposition hall
     * @param newRowId
     * @param hall_id
     */
    @Override
    public void setNewExpositionHall(Connection con, int newRowId, Integer hall_id) {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(SET_NEW_EXPOSITION_HALL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, newRowId);
            pstmt.setInt(2, hall_id);
            pstmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            DBManager.getInstance().rollbackAndClose(con);
        }
    }

    @Override
    public void editExposition(Connection con, int expositionId, String theme, int ticketPrice, int ticketsCount,
                               LocalDate dateIn, LocalDate dateOut, String imgName) throws SQLException{
        int res = 0;
        Date sqlDateIn = Date.valueOf(dateIn);
        Date sqlDateOut = Date.valueOf(dateOut);
        PreparedStatement pstmt = null;
        pstmt = con.prepareStatement(EDIT_EXPOSITION);
        pstmt.setString(1, theme);
        pstmt.setInt(2, ticketPrice);
        pstmt.setInt(3, ticketsCount);
        pstmt.setDate(4,sqlDateIn);
        pstmt.setDate(5, sqlDateOut);
        pstmt.setInt(7, expositionId);
        pstmt.setString(6, imgName);
        pstmt.executeUpdate();
    }

    /**
     * get all Expositions
     * @param limit
     * @param offset
     * @return all Expositions
     */
    @Override
    public List<Exposition> getAllExpositions(Connection con, int limit, int offset) throws SQLException{
        List<Exposition> expositionsList = null;
        PreparedStatement pstmt = null;
        pstmt = con.prepareStatement(GET_ALL_EXPOSITIONS);
        pstmt.setInt(1, limit);
        pstmt.setInt(2, offset);
        ResultSet resultSet = pstmt.executeQuery();
        expositionsList = resultSetToExpositionsList(resultSet);

        return expositionsList;
    }

    private List<Exposition> resultSetToExpositionsList(ResultSet resultSet) throws SQLException {
        List<Exposition> res = new ArrayList<>();
        while (resultSet.next()) {
            Exposition exposition = new Exposition();
            exposition.setId(resultSet.getInt("id"));
            exposition.setTheme(resultSet.getString("theme"));
            exposition.setDateIn(resultSet.getDate("date_in").toLocalDate());
            exposition.setDateOut(resultSet.getDate("date_out").toLocalDate());
            exposition.setTicketPrice(resultSet.getInt("ticket_price"));
            exposition.setHallsNames(resultSet.getString("halls_names"));
            exposition.setTicketsCount(resultSet.getInt("tickets_count"));
            exposition.setImgName(resultSet.getString("img_name"));
            res.add(exposition);
        }
        return res;
    }

    /**
     * gets Exposition by id
     * @param expositionId
     * @return Exposition entity
     */
    @Override
    public Exposition getExpositionById(Connection con, int expositionId) throws SQLException {
        Exposition exposition = null;
        PreparedStatement pstmt = con.prepareStatement(GET_EXPOSITION_BY_ID);
        pstmt.setInt(1, expositionId);
        ResultSet resultSet = pstmt.executeQuery();
        exposition = resultSetToExposition(resultSet);
        return exposition;
    }

    private Exposition resultSetToExposition(ResultSet resultSet) {
        Exposition exposition = new Exposition();
        try {
            resultSet.next();
            exposition.setId(resultSet.getInt("id"));
            exposition.setTheme(resultSet.getString("theme"));
            exposition.setDateIn(resultSet.getDate("date_in").toLocalDate());
            exposition.setDateOut(resultSet.getDate("date_out").toLocalDate());
            exposition.setTicketPrice(resultSet.getInt("ticket_price"));
            exposition.setHallsNames(resultSet.getString("halls_names"));
            exposition.setTicketsCount(resultSet.getInt("tickets_count"));
            exposition.setImgName(resultSet.getString("img_name"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return exposition;
    }

    /**
     * Deletes Exposition
     * @param expositionId
     */
   @Override
   public boolean deleteExposition(Connection con, int expositionId) throws SQLException {
        PreparedStatement pstmt = null;

        pstmt = con.prepareStatement(DELETE_EXPOSITION_HALLS_BY_EXPOSITION_ID);
        pstmt.setInt(1, expositionId);
        int affectedRows = pstmt.executeUpdate();

        PreparedStatement pstmtEx = con.prepareStatement(DELETE_EXPOSITION_BY_EXPOSITION_ID);
        pstmtEx.setInt(1, expositionId);
        pstmtEx.executeUpdate();
        return true;
    }

    /**
     *
     * @param con
     * @return
     * @throws SQLException
     */
    @Override
    public int getExpositionsNumber(Connection con) throws SQLException {
        int res = 0;
        PreparedStatement pstmt = null;
        pstmt = con.prepareStatement(GET_NUMBER_OF_EXPOSITIONS,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = pstmt.executeQuery();
        if (resultSet.first()) {
            res = resultSet.getInt("expositions_number");
        }
        return res;
    }

    public List<Hall> getExpositionHallsByExpositionId(Connection con, int expositionId) throws SQLException {
        List<Hall> hallList = null;
        PreparedStatement pstmt = null;
        con = DBManager.getInstance().getConnection();
        pstmt = con.prepareStatement(GET_EXPOSITION_HALLS);
        pstmt.setInt(1, expositionId);
        ResultSet resultSet = pstmt.executeQuery();
        hallList = resultSetToHallsList(resultSet);

        return hallList;
    }

    private List<Hall> resultSetToHallsList(ResultSet resultSet) throws SQLException{
        List<Hall> res = new ArrayList<>();
        while (resultSet.next()) {
            Hall hall = new Hall();
            hall.setId(resultSet.getInt("hall_ids"));
            hall.setHallName(resultSet.getString("hall_name"));
            res.add(hall);
        }
        return res;
    }

    public void deleteAllExpositionHalls(Connection con,int expositionId) throws SQLException{
        PreparedStatement pstmt = null;

        pstmt = con.prepareStatement(DELETE_EXPOSITION_HALLS_BY_EXPOSITION_ID);
        pstmt.setInt(1, expositionId);
        pstmt.executeUpdate();
    }

    public List<Exposition> getFilteredExpositions(Connection con, LocalDate dateIn, LocalDate dateOut) throws SQLException {
        List<Exposition> expositionsList = null;
        Date sqlDateIn = Date.valueOf(dateIn);
        Date sqlDateOut = Date.valueOf(dateOut);
        PreparedStatement pstmt = null;
        pstmt = con.prepareStatement(GET_FILTERED_EXPOSITIONS);
        pstmt.setDate(1, sqlDateIn);
        pstmt.setDate(2, sqlDateOut);
        pstmt.setDate(3, sqlDateIn);
        pstmt.setDate(4, sqlDateOut);
        ResultSet resultSet = pstmt.executeQuery();
        expositionsList = resultSetToExpositionsList(resultSet);

        return expositionsList;
    }

}

