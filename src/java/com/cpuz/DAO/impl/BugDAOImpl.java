/*
 * BugDAOImpl.java
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.cpuz.DAO.impl;

import com.cpuz.domain.Bug;
import com.cpuz.exceptions.BugException;
import com.cpuz.tools.JDBCHelper;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.naming.NamingException;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class BugDAOImpl implements BugDAO {

    // JNDI name of the data source this class requires
    private static final String DATA_SOURCE_NAME = "CPUZ";
    private JDBCHelper jdbcHelper;
    private PreparedStatement selectStatement = null;
    private PreparedStatement insertStatement = null;

    /** Creates a new instance of BugDAOImpl
     * 
     */
    public BugDAOImpl() throws Exception {
        jdbcHelper = new JDBCHelper(DATA_SOURCE_NAME);
    }

    public boolean keyIdExists(int ssn) throws SQLException, NamingException {
        Connection connection = null;
        boolean exists = false;
        String selectStr = "SELECT bug_id FROM bugs WHERE bug_id = " + ssn;
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
     * Adds the Bug to the Bug model
     */
    public synchronized int createBug(Bug rec)
            throws BugException, SQLException {

        Connection connection = null;
        // Declarar variables de uso en la frase SQL
        //** crear la frase INSERT SQL
        String request= "INSERT INTO bugs "
                + "(bug_date, "
                + "bug_status, "
                + "bug_user, "
                + "bug_priority, "
                + "bug_type, "
                + "bug_application, "
                + "bug_header, "
                + "bug_body) "
                + " VALUES ("
                + "'" + (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(rec.getDatetime())) + "',"
                + rec.getStatus() + ","
                + "'" + rec.getUser() + "',"
                + rec.getPriority() + ","
                + "'" + rec.getType() + "',"
                + "'" + rec.getApplication() + "',"
                + "'" + rec.getHeader() + "',"
                + "'" + rec.getBody()  + "')";
        int rowCount = 0;

        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            //** obtener una variable de gestáón de SQL
            insertStatement = connection.prepareStatement(request);
            //** obtener el número de registros insertados
            rowCount = insertStatement.executeUpdate();
            request= "SELECT LAST_INSERT_id() FROM bugs ";
            insertStatement = connection.prepareStatement(request);
            ResultSet rs = insertStatement.executeQuery();
            if (rs.next()) {
                rec.setId(rs.getInt(1));
            } else {
                rec.setId(0);
            }
        } catch (Exception e) {
            e.toString();
            throw new BugException("BugDAOImpl.createBug\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return rowCount;
    }

    /**-------------------------------------------------------------
     * deletes the Bug from the Bug model
     */
    public int deleteBug(Bug rec)
            throws BugException, SQLException {
        Connection connection = null;
        int rowCount = 0;
        int keyId;
        //** 1 Assign id with id from rec object
        keyId = rec.getId();
        //** crear la frase DELETE SQL de tabla1
        String request= "DELETE FROM bugs WHERE bug_id = " + keyId;
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
            throw new BugException("BugModelImpl.deleteBug\n" + e);
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
     * @throws com.cpuz.exceptions.BugException
     * @throws java.sql.SQLException
     */
    public int deleteBug(String sqlWhereClause) throws BugException, SQLException {
        Connection connection = null;
        int rowCount = 0;
        //** crear la frase DELETE SQL de tabla1
        String request= "DELETE FROM bugs " + sqlWhereClause;
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
            throw new BugException("BugModelImpl.deleteBug\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return rowCount;
    }

    /**-------------------------------------------------------------
     * Updates the Bug in the Bug model
     */
    public int updateBug(Bug rec)
            throws BugException, SQLException {
        Connection connection = null;
        /*        String s = rec.getContenido();
        int inicio=0;
        while (s.indexOf("'",inicio)>=0){
        s=s.substring(0,s.indexOf("'",inicio))+"\\"+s.substring(s.indexOf("'",inicio));
        inicio=s.indexOf("'",inicio);
        inicio++;
        }   */
        int rowCount = 0;
        //** crear la frase UPDATE SQL
        String request= "UPDATE bugs SET "
                + "bug_status = " + rec.getStatus() + ","
                + "bug_priority = " + rec.getPriority() + ","
                + "bug_type = " + "'" + rec.getType() + "',"
                + "bug_application = " + "'" + rec.getApplication() + "',"
                + "bug_header = " + "'" + rec.getHeader() + "',"
                + "bug_body = " + "'" + rec.getBody() + "'"
                + " WHERE bug_id = " + rec.getId();

        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            //** obtener una variable de gestáón de SQL
            selectStatement = connection.prepareStatement(request);
            //** obtener el número de registros actualizados
            rowCount = selectStatement.executeUpdate(request);
        } catch (Exception e) {
            e.toString();
            throw new BugException("BugModelImpl.updateBugInfo\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return rowCount;
    }
    // Bug segment state query methods

    /**-------------------------------------------------------------
     * Given an ssn, returns the Bug from the model
     */
    public Bug readBug(String keyId)
            throws BugException, SQLException, NamingException {
        // Declarar variables de uso en la frase SQL
        Connection connection = null;
        //** crear la frase SELECT SQL
        String request= "SELECT * FROM bugs WHERE bug_id = " + keyId;
        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            ResultSet result = null;
            Bug cr = null;
            //** obtener una variable de gestáón de SQL
            selectStatement = connection.prepareStatement(request);
            //** obtener el resultset de registros obtenidos
            result = selectStatement.executeQuery(request);
            // The following selectStatement checks if query successful
            if (result.next()) {
                //** Asignar valores de columnas a un objeto Bug
                cr = new Bug();
                this.setPropertiesBug(cr, result); //m�todo ayudante
            } else {
                // if query failed
                throw new BugException("Bug for " + keyId + " not found.");
            }
            result.close();
            // return cr
            return cr;
        } catch (SQLException e) {
            e.toString();
            throw new BugException("BugModelImpl.readBug\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
    }

    /**-------------------------------------------------------------
     * Returns all Bugs in the Bug model
     */
    /**-------------------------------------------------------------
     * Returns all Bugs in the Bug model
     */
    public List<Bug> readBugs()
            throws BugException, SQLException, NamingException {

        return this.readBugs("");
    }

    public List<Bug> readBugs(String sqlClause) throws BugException, SQLException, NamingException {
        Connection connection = null;
        //** crear la frase SELECT SQL
        List<Bug> aList = new ArrayList<Bug>();
        String request= "";
        if (sqlClause == null) {
            request= "SELECT * FROM bugs ";
        } else {
            request= sqlClause;
        }


        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            ResultSet result = null;
            Bug cr = null;
//            connection = obtainConnection();
            //** obtener una variable de gestáón de SQL
            selectStatement = connection.prepareStatement(request);

            //** obtener el resultset de registros obtenidos
            result = selectStatement.executeQuery(request);
            while (result.next()) {
                //A* Asignar valores de campos a un objeto Bug
                cr = new Bug();
                this.setPropertiesBug(cr, result); //m�todo ayudante
                // Agregar el objeto Bug a la lista de objetos Bug
                aList.add(cr);
            }
            result.close();
        } catch (SQLException e) {
            e.toString();
            throw new BugException("BugDAOImpl.readAllBug\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return aList;
    }

    public void setPropertiesBug(Bug rec, ResultSet rs) throws BugException {
        try {
            rec.setId(rs.getInt("bug_id"));
            rec.setDatetime(rs.getTimestamp("bug_date"));
            rec.setStatus(rs.getInt("bug_status"));
            rec.setUser(rs.getString("bug_user"));
            rec.setPriority(rs.getInt("bug_priority"));
            rec.setType(rs.getString("bug_type"));
            rec.setApplication(rs.getString("bug_application"));
            rec.setHeader(rs.getString("bug_header"));
            rec.setBody(rs.getString("bug_body"));
        } catch (SQLException e) {
            throw new BugException("BugDAOImpl.setPropertiesBug\n" + e);
        }
    }
}
