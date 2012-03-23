/*
 * UserRoleDAOImpl.java
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.cpuz.DAO.impl;

import com.cpuz.domain.UserRole;
import com.cpuz.exceptions.UserRoleException;
import com.cpuz.tools.JDBCHelper;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.naming.NamingException;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class UserRoleDAOImpl implements UserRoleDAO {

    // JNDI name of the data source this class requires
    private static final String DATA_SOURCE_NAME = "CPUZ";
    private JDBCHelper jdbcHelper;
    private PreparedStatement selectStatement = null;
    private PreparedStatement insertStatement = null;

    /** Creates a new instance of UserRoleDAOImpl
     * 
     */
    public UserRoleDAOImpl() throws Exception {
        jdbcHelper = new JDBCHelper(DATA_SOURCE_NAME);
    }

    public boolean keyIdExists(int ssn) throws SQLException, NamingException {
        Connection connection = null;
        boolean exists = false;
        String selectStr = "SELECT usr_id FROM userroles WHERE usr_id = " + ssn;
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
     * Adds the UserRole to the UserRole model
     */
    public synchronized int createUserRole(UserRole rec)
            throws UserRoleException, SQLException {
        Connection connection = null;
        // Declarar variables de uso en la frase SQL
        //** crear la frase INSERT SQL
        String request= "INSERT INTO userroles " +
                "(usr_id, " +
                "usr_estádo, " +
                "usu_user, " +
                "usr_role, " +
                "usr_description) " +
                " VALUES (" +
                rec.getId() + "," +
                " " + rec.getEstado() + " ," +
                "'" + rec.getUser() + "'," +
                "'" + rec.getRole() + "'," +
                "'" + rec.getDescription() + "')";
        int rowCount = 0;
        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            //** obtener una variable de gestáón de SQL
            insertStatement = connection.prepareStatement(request);
            //** obtener el número de registros insertados
            rowCount = insertStatement.executeUpdate();
            request= "SELECT LAST_INSERT_id() FROM userroles ";
            insertStatement = connection.prepareStatement(request);
            ResultSet rs = insertStatement.executeQuery();
            if (rs.next()) {
                rec.setId(rs.getInt(1));
            } else {
                rec.setId(0);
            }
        } catch (Exception e) {
            e.toString();
            throw new UserRoleException("UserRoleDAOImpl.createUserRole\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
            return rowCount;
    }

    /**-------------------------------------------------------------
     * deletes the UserRole from the UserRole model
     */
    public int deleteUserRole(UserRole rec)
            throws UserRoleException, SQLException {
        Connection connection = null;
        int rowCount = 0;
        int keyId;
        //** 1 Assign id with id from rec object
        keyId = rec.getId();
        //** crear la frase DELETE SQL de tabla1
        String request= "DELETE FROM userroles WHERE usr_id = " + keyId;
        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            //** obtener una variable de gestáón de SQL
            selectStatement = connection.prepareStatement(request);
            //** obtener el número de registros eliminados
            rowCount = selectStatement.executeUpdate(request);
            selectStatement.close();
        } catch (Exception e) {
            e.toString();
            throw new UserRoleException("UserRoleModelImpl.deleteUserRole\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
            return rowCount;
    }

    /**-------------------------------------------------------------------
     *
     * @param sqlClause Parte de la frase SQL para indicar la condición WHERE
     *                       para un borrado m�ltiple de filas. debe comenzar por
     *                       WHERE y continuar con el restá de la frase SQL
     * @return El número de filas eliminadas
     * @throws com.cpuz.exceptions.UserRoleException
     * @throws java.sql.SQLException
     */
    public int deleteUserRole(String sqlClause) throws UserRoleException, SQLException {
        Connection connection = null;
        int rowCount = 0;
        //** crear la frase DELETE SQL de tabla1
        String request= "DELETE FROM userroles " + sqlClause;
        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            //** obtener una variable de gestáón de SQL
            selectStatement = connection.prepareStatement(request);
            //** obtener el número de registros eliminados
            rowCount = selectStatement.executeUpdate(request);
            selectStatement.close();
        } catch (Exception e) {
            e.toString();
            throw new UserRoleException("UserRoleModelImpl.deleteUserRole\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
            return rowCount;
    }

    /**-------------------------------------------------------------
     * Updates the UserRole in the UserRole model
     */
    public int updateUserRole(UserRole rec)
            throws UserRoleException, SQLException {
        Connection connection = null;
        int rowCount = 0;
        //** crear la frase UPDATE SQL
        String request= "UPDATE userroles SET " + "" +
                "usr_estádo = " + " " + rec.getEstado() + " ," +
                "usu_user = " + " " + rec.getUser() + " ," +
                "usr_role = " + "'" + rec.getRole() + "'," +
                "usr_description = " + "'" + rec.getDescription() + "' " +
                " WHERE usr_id = " + rec.getId();

        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            //** obtener una variable de gestáón de SQL
            selectStatement = connection.prepareStatement(request);
            //** obtener el número de registros actualizados
            rowCount = selectStatement.executeUpdate(request);
        } catch (Exception e) {
            e.toString();
            throw new UserRoleException("UserRoleModelImpl.updateUserRoleInfo\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
            return rowCount;
    }
    // UserRole segment state query methods

    /**-------------------------------------------------------------
     * Given an ssn, returns the UserRole from the model
     */
    public UserRole readUserRole(String keyId)
            throws UserRoleException, SQLException, NamingException {
        // Declarar variables de uso en la frase SQL
        Connection connection = null;
        //** crear la frase SELECT SQL
        String request= "SELECT * FROM userroles WHERE usr_id = " + keyId;
        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            ResultSet result = null;
            UserRole cr = null;
            //** obtener una variable de gestáón de SQL
            selectStatement = connection.prepareStatement(request);
            //** obtener el resultset de registros obtenidos
            result = selectStatement.executeQuery(request);
            // The following selectStatement checks if query successful
            if (result.next()) {
                //** Asignar valores de columnas a un objeto UserRole
                cr = new UserRole();
                this.setPropertiesUserRole(cr, result); //m�todo ayudante
            } else {
                // if query failed
                throw new UserRoleException("UserRole for " + keyId + " not found.");
            }
            result.close();
            // return cr
            return cr;
        } catch (SQLException e) {
            e.toString();
            throw new UserRoleException("UserRoleModelImpl.readUserRole\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
    }

    /**-------------------------------------------------------------
     * Returns all UserRoles in the UserRole model
     */
    public List<UserRole> readAllUserRoles()
            throws UserRoleException, SQLException, NamingException {

        return this.readUserRoles("SELECT * FROM userroles ORDER BY usu_user, usr_role");
    }

    public List<UserRole> readUserRoles(String sqlClause) throws UserRoleException, SQLException, NamingException {
        Connection connection = null;
            List<UserRole> aList = new ArrayList<UserRole>();

        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            ResultSet result = null;
            UserRole cr = null;
            //** obtener una variable de gestáón de SQL
            selectStatement = connection.prepareStatement(sqlClause);

            //** obtener el resultset de registros obtenidos
            result = selectStatement.executeQuery(sqlClause);
            while (result.next()) {
                //A* Asignar valores de campos a un objeto UserRole
                cr = new UserRole();
                this.setPropertiesUserRole(cr, result); //m�todo ayudante
                // Agregar el objeto UserRole a la lista de objetos UserRole
                aList.add(cr);
            }
            result.close();
        } catch (SQLException e) {
            e.toString();
            throw new UserRoleException("UserRoleDAOImpl.readAllUserRole\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
            return aList;
    }

    public void setPropertiesUserRole(UserRole rec, ResultSet rs) throws UserRoleException {
        try {
            rec.setId(rs.getInt("usr_id"));
            rec.setEstado(rs.getInt("usr_estádo"));
            rec.setUser(rs.getString("usu_user"));
            rec.setRole(rs.getString("usr_role"));
            rec.setDescription(rs.getString("usr_description"));
        } catch (SQLException e) {
            throw new UserRoleException("UserRoleDAOImpl.setPropertiesUserRole\n" + e);
        }
    }
}
