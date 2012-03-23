/*
 * SectionDAOImpl.java
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.cpuz.DAO.impl;

import com.cpuz.domain.Section;
import com.cpuz.exceptions.SectionException;
import com.cpuz.tools.JDBCHelper;
import java.sql.*;
import java.util.*;
import javax.naming.NamingException;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class SectionDAOImpl implements SectionDAO {

    // JNDI name of the data source this class requires
    private static final String DATA_SOURCE_NAME = "CPUZ";
    private JDBCHelper jdbcHelper;
    private PreparedStatement selectStatement = null;
    private PreparedStatement insertStatement = null;

    /** Creates a new instance of SectionDAOImpl
     * 
     */
    public SectionDAOImpl() throws Exception {
        jdbcHelper = new JDBCHelper(DATA_SOURCE_NAME);
    }

    public boolean keyIdExists(String ssn) throws SQLException, NamingException {
        Connection connection = null;
        boolean exists = false;
        String selectStr = "SELECT sec_id FROM sections WHERE sec_id = '" + ssn + "'";
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
     * Adds the Section to the Section model
     */
    public synchronized int createSection(Section rec)
            throws SectionException, SQLException {
        Connection connection = null;
        // Declarar variables de uso en la frase SQL
        //** crear la frase INSERT SQL
        String request= "INSERT INTO sections "
                + "(sec_id, "
                + "sec_name, "
                + "sec_authorized_roles, "
                + "sec_group) "
                + " VALUES ("
                + "'" + rec.getId() + "',"
                + "'" + rec.getName() + "',"
                + "'" + rec.getAuthorizedRoles() + "',"
                + "'" + rec.getGroup()+"')";
        int rowCount = 0;
        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            //** obtener una variable de gestáón de SQL
            insertStatement = connection.prepareStatement(request);
            //** obtener el número de registros insertados
            rowCount = insertStatement.executeUpdate();
            request= "SELECT LAST_INSERT_id() FROM sections ";
            insertStatement = connection.prepareStatement(request);
            ResultSet rs = insertStatement.executeQuery();
            if (rs.next()) {
                rec.setId(rs.getString(1));
            } else {
                rec.setId("");
            }
        } catch (Exception e) {
            e.toString();
            throw new SectionException("SectionDAOImpl.createSection\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return rowCount;
    }

    /**-------------------------------------------------------------
     * deletes the Section from the Section model
     */
    public int deleteSection(Section rec)
            throws SectionException, SQLException {
        Connection connection = null;
        int rowCount = 0;
        String keyId;
        //** 1 Assign id with id from rec object
        keyId = rec.getId();
        //** crear la frase DELETE SQL de tabla1
        String request= "DELETE FROM sections WHERE sec_id = '" + keyId + "'";
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
            throw new SectionException("SectionModelImpl.deleteSection\n" + e);
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
     * @throws com.cpuz.exceptions.SectionException
     * @throws java.sql.SQLException
     */
    public int deleteSection(String sqlWhereClause) throws SectionException, SQLException {
        Connection connection = null;
        int rowCount = 0;
        //** crear la frase DELETE SQL de tabla1
        String request= "DELETE FROM sections " + sqlWhereClause;
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
            throw new SectionException("SectionModelImpl.deleteSection\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return rowCount;
    }

    /**-------------------------------------------------------------
     * Updates the Section in the Section model
     */
    public int updateSection(Section rec)
            throws SectionException, SQLException {
        Connection connection = null;
        int rowCount = 0;
        //** crear la frase UPDATE SQL
        String request= "UPDATE sections SET " + ""
                + "sec_name = " + "'" + rec.getName() + "',"
                + "sec_authorized_roles = " + "'" + rec.getAuthorizedRoles() + "', "
                + "sec_group = " + "'" + rec.getGroup() + "' "
                + " WHERE sec_id = '" + rec.getId() + "'";

        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            //** obtener una variable de gestáón de SQL
            selectStatement = connection.prepareStatement(request);
            //** obtener el número de registros actualizados
            rowCount = selectStatement.executeUpdate(request);
        } catch (Exception e) {
            e.toString();
            throw new SectionException("SectionModelImpl.updateSectionInfo\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return rowCount;
    }
    // Section segment state query methods

    /**-------------------------------------------------------------
     * Given an ssn, returns the Section from the model
     */
    public Section readSection(String keyId)
            throws SectionException, SQLException, NamingException {
        // Declarar variables de uso en la frase SQL
        Connection connection = null;
        //** crear la frase SELECT SQL
        String request= "SELECT * FROM sections WHERE sec_id = '" + keyId + "'";
        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            ResultSet result = null;
            Section cr = null;
            //** obtener una variable de gestáón de SQL
            selectStatement = connection.prepareStatement(request);
            //** obtener el resultset de registros obtenidos
            result = selectStatement.executeQuery(request);
            // The following selectStatement checks if query successful
            if (result.next()) {
                //** Asignar valores de columnas a un objeto Section
                cr = new Section();
                this.setPropertiesSection(cr, result); //m�todo ayudante
            } else {
                // if query failed
                throw new SectionException("Section for " + keyId + " not found.");
            }
            result.close();
            // return cr
            return cr;
        } catch (SQLException e) {
            e.toString();
            throw new SectionException("SectionModelImpl.readSection\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
    }

    /**-------------------------------------------------------------
     * Returns all Sections in the Section model
     */
    public List<Section> readSections()
            throws SectionException, SQLException, NamingException {

        return this.readSections("");
    }

    public List<Section> readSections(String sqlClause) throws SectionException, SQLException, NamingException {
        Connection connection = null;
        //** crear la frase SELECT SQL
        List<Section> aList = new ArrayList<Section>();
        String request= "";
        if (sqlClause == null) {
            request= "SELECT * FROM sections ";
        } else {
            request= sqlClause;
        }

        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            ResultSet result = null;
            Section cr = null;
            // verifica que la condición es una expresi�n correcta
//            connection = obtainConnection();
            //** obtener una variable de gestáón de SQL
            selectStatement = connection.prepareStatement(request);


            //** obtener el resultset de registros obtenidos
            result = selectStatement.executeQuery(request);
            while (result.next()) {
                //A* Asignar valores de campos a un objeto Section
                cr = new Section();
                this.setPropertiesSection(cr, result); //m�todo ayudante
                // Agregar el objeto Section a la lista de objetos Section
                aList.add(cr);
            }
            result.close();
        } catch (SQLException e) {
            e.toString();
            throw new SectionException("SectionDAOImpl.readSection\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return aList;
    }

    public void setPropertiesSection(Section rec, ResultSet rs) throws SectionException {
        try {
            rec.setId(rs.getString("sec_id"));
            rec.setName(rs.getString("sec_name"));
            rec.setAuthorizedRoles(rs.getString("sec_authorized_roles"));
            rec.setGroup(rs.getString("sec_group"));
        } catch (SQLException e) {
            throw new SectionException("SectionDAOImpl.setPropertiesSection\n" + e);
        }
    }
}
