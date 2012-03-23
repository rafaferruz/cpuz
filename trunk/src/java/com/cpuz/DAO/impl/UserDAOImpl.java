/*
 * UserDAOImpl.java
 *
 */
package com.cpuz.DAO.impl;

import com.cpuz.domain.User;
import com.cpuz.exceptions.UserException;
import com.cpuz.tools.JDBCHelper;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.naming.NamingException;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class UserDAOImpl implements UserDAO {

    // JNDI name of the data source this class requires
    private static final String DATA_SOURCE_NAME = "CPUZ";
    private JDBCHelper jdbcHelper;
    private PreparedStatement selectStatement = null;
    private PreparedStatement insertStatement = null;

    /** Creates a new instance of UserDAOImpl
     * 
     */
    public UserDAOImpl() throws Exception {
        jdbcHelper = new JDBCHelper(DATA_SOURCE_NAME);
    }

    public boolean keyIdExists(int ssn) throws SQLException, NamingException {
        Connection connection = null;
        boolean exists = false;
        String selectStr = "SELECT usu_id FROM users WHERE usu_id = " + ssn;
        try {
            connection = jdbcHelper.getConnection();
            selectStatement = connection.prepareStatement(selectStr);
            ResultSet result = selectStatement.executeQuery();
            exists = result.next();
        } catch (SQLException e) {
            System.err.println("in keyIdExists, query for " + ssn + " failed");
            e.toString();
            return false;
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return exists;
    }

    /**----------------------------------------------------------
     * Adds the User to the User model
     */
    public synchronized int createUser(User rec)
            throws UserException, SQLException {
        Connection connection = null;
        String request= "INSERT INTO users "
                + "(usu_id, "
                + "usu_date, "
                + "usu_status, "
                + "usu_category, "
                + "usu_user, "
                + "usu_name, "
                + "usu_password, "
                + "usu_email) "
                + " VALUES ("
                + rec.getId() + ","
                + "'" + (new SimpleDateFormat("yyyy-MM-dd").format(rec.getDate())) + "',"
                + " " + rec.getStatus() + ","
                + " " + rec.getCategory() + " ,"
                + "'" + rec.getUser() + "',"
                + "'" + rec.getName() + "',"
                + "'" + rec.getPassword() + "',"
                + "'" + rec.getEmail() + "')";
        int rowCount = 0;
        try {
            connection = jdbcHelper.getConnection();
            insertStatement = connection.prepareStatement(request);
            rowCount = insertStatement.executeUpdate();
            request= "SELECT LAST_INSERT_id() FROM users ";
            insertStatement = connection.prepareStatement(request);
            ResultSet rs = insertStatement.executeQuery();
            if (rs.next()) {
                rec.setId(rs.getInt(1));
            } else {
                rec.setId(0);
            }
        } catch (Exception e) {
            e.toString();
            throw new UserException("UserDAOImpl.createUser\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return rowCount;
    }

    /**-------------------------------------------------------------
     * deletes the User from the User model
     */
    public int deleteUser(User rec)
            throws UserException, SQLException {
        Connection connection = null;
        int rowCount = 0;
        int keyId = rec.getId();
        String request= "DELETE FROM users WHERE usu_id = " + keyId;
        try {
            connection = jdbcHelper.getConnection();
            selectStatement = connection.prepareStatement(request);
            rowCount = selectStatement.executeUpdate(request);
            selectStatement.close();
        } catch (Exception e) {
            e.toString();
            throw new UserException("UserModelImpl.deleteUser\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return rowCount;
    }

    /**-------------------------------------------------------------------
     *
     * @param sqlWhereClause Parte de la frase SQL para indicar la condición WHERE
     *                       para un borrado m�ltiple de filas. debe comenzar por
     *                       WHERE y continuar con el restá de la frase SQL
     * @return El número de filas eliminadas
     * @throws com.cpuz.exceptions.UserException
     * @throws java.sql.SQLException
     */
    public int deleteUser(String sqlWhereClause) throws UserException, SQLException {
        Connection connection = null;
        int rowCount = 0;
        String request= "DELETE FROM users " + sqlWhereClause;
        try {
            connection = jdbcHelper.getConnection();
            selectStatement = connection.prepareStatement(request);
            rowCount = selectStatement.executeUpdate(request);
            selectStatement.close();
        } catch (Exception e) {
            e.toString();
            throw new UserException("UserModelImpl.deleteUser\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return rowCount;
    }

    /**-------------------------------------------------------------
     * Updates the User in the User model
     */
    public int updateUser(User rec)
            throws UserException, SQLException {
        Connection connection = null;
        int rowCount = 0;
        String request= "UPDATE users SET " + ""
                + "usu_date = " + "'" + (new SimpleDateFormat("yyyy-MM-dd").format(rec.getDate())) + "',"
                + "usu_status = " + rec.getStatus() + ","
                + "usu_category = " + rec.getCategory() + ","
                + "usu_user = " + "'" + rec.getUser() + "',"
                + "usu_name = " + "'" + rec.getName() + "',"
                + "usu_password = " + "'" + rec.getPassword() + "',"
                + "usu_email = " + "'" + rec.getEmail() + "' "
                + " WHERE usu_id = " + rec.getId();

        try {
            connection = jdbcHelper.getConnection();
            selectStatement = connection.prepareStatement(request);
            rowCount = selectStatement.executeUpdate(request);
        } catch (Exception e) {
            e.toString();
            throw new UserException("UserModelImpl.updateUserInfo\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return rowCount;
    }

    /**-------------------------------------------------------------
     * Given an ssn, returns the User from the model
     */
    public User readUser(String keyId)
            throws UserException, SQLException, NamingException {
        Connection connection = null;
        String request= "SELECT * FROM users WHERE usu_id = " + keyId;
        try {
            connection = jdbcHelper.getConnection();
            ResultSet result = null;
            User cr = null;
            selectStatement = connection.prepareStatement(request);
            result = selectStatement.executeQuery(request);
            if (result.next()) {
                cr = new User();
                this.setPropertiesUser(cr, result); //m�todo ayudante
            } else {
                throw new UserException("User for " + keyId + " not found.");
            }
            result.close();
            return cr;
        } catch (SQLException e) {
            e.toString();
            throw new UserException("UserModelImpl.readUser\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
    }

    /**-------------------------------------------------------------
     * Returns all Users in the User model
     */
    public List<User> readAllUsers()
            throws UserException, SQLException, NamingException {
        return this.readUsers("SELECT * FROM users ORDER BY usu_user");
    }

    public List<User> readUsers(String sqlClause) throws UserException, SQLException, NamingException {
        List<User> aList = new ArrayList<User>();
        Connection connection = null;
        try {
            connection = jdbcHelper.getConnection();
            ResultSet result = null;
            User cr = null;
            selectStatement = connection.prepareStatement(sqlClause);
            result = selectStatement.executeQuery(sqlClause);
            while (result.next()) {
                cr = new User();
                this.setPropertiesUser(cr, result); //m�todo ayudante
                aList.add(cr);
            }
            result.close();
        } catch (SQLException e) {
            e.toString();
            throw new UserException("UserDAOImpl.readAllUser\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return aList;
    }

    public void setPropertiesUser(User rec, ResultSet rs) throws UserException {
        try {
            rec.setId(rs.getInt("usu_id"));
            rec.setDate(rs.getDate("usu_date"));
            rec.setStatus(rs.getInt("usu_status"));
            rec.setCategory(rs.getInt("usu_category"));
            rec.setUser(rs.getString("usu_user"));
            rec.setName(rs.getString("usu_name"));
            rec.setPassword(rs.getString("usu_password"));
            rec.setEmail(rs.getString("usu_email"));
        } catch (SQLException e) {
            throw new UserException("UserDAOImpl.setPropertiesUser\n" + e);
        }
    }
}
