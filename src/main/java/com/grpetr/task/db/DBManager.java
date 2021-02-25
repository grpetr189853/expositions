package com.grpetr.task.db;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

/**
 * Class with connection to database
 */
public class DBManager {
    private final static Logger LOG = Logger.getLogger(DBManager.class);
    private static DBManager instance;

    // //////////////////////////////////////////////////////////
    // singleton
    // //////////////////////////////////////////////////////////

    public static synchronized DBManager getInstance() {
        if (instance == null)
            instance = new DBManager();
        return instance;
    }

    /**
     * Returns a DB connection from the Pool Connections. Before using this
     * method you must configure the Date Source and the Connections Pool in your
     * WEB_APP_ROOT/META-INF/context.xml file.
     *
     * @return A DB connection.
     */
    public Connection getConnection() throws SQLException {
        Connection con = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");

            DataSource ds = (DataSource) envContext.lookup("jdbc/expositions");
            con = ds.getConnection();
        } catch (NamingException ex) {
            LOG.error("Cannot obtain a connection from the pool", ex);
        }
        return con;
    }

    // //////////////////////////////////////////////////////////
    // DB util methods
    // //////////////////////////////////////////////////////////

    /**
     * Commits and close the given connection.
     *
     * @param con Connection to be committed and closed.
     */
    public void commitAndClose(Connection con) {
        if (con != null) {
            try {
                con.commit();
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Rollbacks and close the given connection.
     *
     * @param con Connection to be rollbacked and closed.
     */
    public void rollbackAndClose(Connection con) {
        if (con != null) {
            try {
                con.rollback();
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}
