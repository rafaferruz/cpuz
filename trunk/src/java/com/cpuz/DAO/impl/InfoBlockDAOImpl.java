/*
 * InfoBlockDAOImpl.java
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.cpuz.DAO.impl;

import com.cpuz.domain.InfoBlock;
import com.cpuz.exceptions.InfoBlockException;
import com.cpuz.tools.JDBCHelper;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.naming.NamingException;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class InfoBlockDAOImpl implements InfoBlockDAO {

    // JNDI name of the data source this class requires
    private static final String DATA_SOURCE_NAME = "CPUZ";
    private JDBCHelper jdbcHelper;
    private PreparedStatement selectStatement = null;
    private PreparedStatement insertStatement = null;

    /** Creates a new instance of InfoBlockDAOImpl
     * 
     */
    public InfoBlockDAOImpl() throws Exception {
        jdbcHelper = new JDBCHelper(DATA_SOURCE_NAME);
    }

    public boolean keyIdExists(int ssn) throws SQLException, NamingException {
        Connection connection = null;
        boolean exists = false;
        String selectStr = "SELECT inb_id FROM infoblocks WHERE inb_id = " + ssn;
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
     * Adds the InfoBlock to the InfoBlock model
     */
    public synchronized int createInfoBlock(InfoBlock rec)
            throws InfoBlockException, SQLException {

        String pattern = "yyyy-MM-dd hh:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat();
        // apply the pattern to the SimpleDateFormat class
        sdf.applyPattern(pattern);


        Connection connection = null;
        // Declarar variables de uso en la frase SQL
        //** crear la frase INSERT SQL
        String request= "INSERT INTO infoblocks "
                + "(inb_date, "
                + "inb_status, "
                + "inb_user, "
                + "inb_type, "
                + "inb_header, "
                + "inb_body, "
                + "inb_scope) "
                + " VALUES ("
                + "'" + (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(rec.getDatetime())) + "',"
                + rec.getStatus() + ","
                + "'" + rec.getUser() + "',"
                + "'" + rec.getType() + "',"
                + "'" + rec.getHeader() + "',"
                + "'" + rec.getBody() + "',"
                + rec.getScope() + ")";
        int rowCount = 0;

        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            //** obtener una variable de gestáón de SQL
            insertStatement = connection.prepareStatement(request);
            //** obtener el número de registros insertados
            rowCount = insertStatement.executeUpdate();
            request= "SELECT LAST_INSERT_id() FROM infoblocks ";
            insertStatement = connection.prepareStatement(request);
            ResultSet rs = insertStatement.executeQuery();
            if (rs.next()) {
                rec.setId(rs.getInt(1));
            } else {
                rec.setId(0);
            }
        } catch (Exception e) {
            e.toString();
            throw new InfoBlockException("InfoBlockDAOImpl.createInfoBlock\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return rowCount;
    }

    /**-------------------------------------------------------------
     * deletes the InfoBlock from the InfoBlock model
     */
    public int deleteInfoBlock(InfoBlock rec)
            throws InfoBlockException, SQLException {
        Connection connection = null;
        int rowCount = 0;
        int keyId;
        //** 1 Assign id with id from rec object
        keyId = rec.getId();
        //** crear la frase DELETE SQL de tabla1
        String request= "DELETE FROM infoblocks WHERE inb_id = " + keyId;
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
            throw new InfoBlockException("InfoBlockModelImpl.deleteInfoBlock\n" + e);
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
     * @throws com.cpuz.exceptions.InfoBlockException
     * @throws java.sql.SQLException
     */
    public int deleteInfoBlock(String sqlWhereClause) throws InfoBlockException, SQLException {
        Connection connection = null;
        int rowCount = 0;
        //** crear la frase DELETE SQL de tabla1
        String request= "DELETE FROM infoblocks " + sqlWhereClause;
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
            throw new InfoBlockException("InfoBlockModelImpl.deleteInfoBlock\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return rowCount;
    }

    /**-------------------------------------------------------------
     * Updates the InfoBlock in the InfoBlock model
     */
    public int updateInfoBlock(InfoBlock rec)
            throws InfoBlockException, SQLException {
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
        String request= "UPDATE infoblocks SET "
                + "inb_status = " + rec.getStatus() + ","
                + "inb_type = " + "'" + rec.getType() + "',"
                + "inb_header = " + "'" + rec.getHeader() + "',"
                + "inb_body = " + "'" + rec.getBody() + "',"
                + "inb_scope = " + rec.getScope()
                + " WHERE inb_id = " + rec.getId();

        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            //** obtener una variable de gestáón de SQL
            selectStatement = connection.prepareStatement(request);
            //** obtener el número de registros actualizados
            rowCount = selectStatement.executeUpdate(request);
        } catch (Exception e) {
            e.toString();
            throw new InfoBlockException("InfoBlockModelImpl.updateInfoBlockInfo\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return rowCount;
    }
    // InfoBlock segment state query methods

    /**-------------------------------------------------------------
     * Given an ssn, returns the InfoBlock from the model
     */
    public InfoBlock readInfoBlock(String keyId)
            throws InfoBlockException, SQLException, NamingException {
        // Declarar variables de uso en la frase SQL
        Connection connection = null;
        //** crear la frase SELECT SQL
        String request= "SELECT * FROM infoblocks WHERE inb_id = " + keyId;
        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            ResultSet result = null;
            InfoBlock cr = null;
            //** obtener una variable de gestáón de SQL
            selectStatement = connection.prepareStatement(request);
            //** obtener el resultset de registros obtenidos
            result = selectStatement.executeQuery(request);
            // The following selectStatement checks if query successful
            if (result.next()) {
                //** Asignar valores de columnas a un objeto InfoBlock
                cr = new InfoBlock();
                this.setPropertiesInfoBlock(cr, result); //m�todo ayudante
            } else {
                // if query failed
                throw new InfoBlockException("InfoBlock for " + keyId + " not found.");
            }
            result.close();
            // return cr
            return cr;
        } catch (SQLException e) {
            e.toString();
            throw new InfoBlockException("InfoBlockModelImpl.readInfoBlock\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
    }

    /**-------------------------------------------------------------
     * Returns all InfoBlocks in the InfoBlock model
     */
    /**-------------------------------------------------------------
     * Returns all InfoBlocks in the InfoBlock model
     */
    public List<InfoBlock> readInfoBlocks()
            throws InfoBlockException, SQLException, NamingException {

        return this.readInfoBlocks("");
    }

    public List<InfoBlock> readInfoBlocks(String sqlClause) throws InfoBlockException, SQLException, NamingException {
        Connection connection = null;
        //** crear la frase SELECT SQL
        List<InfoBlock> aList = new ArrayList<InfoBlock>();
        String request= "";
        if (sqlClause == null) {
            request= "SELECT * FROM infoblocks ";
        } else {
            request= sqlClause;
        }


        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            ResultSet result = null;
            InfoBlock cr = null;
//            connection = obtainConnection();
            //** obtener una variable de gestáón de SQL
            selectStatement = connection.prepareStatement(request);

            //** obtener el resultset de registros obtenidos
            result = selectStatement.executeQuery(request);
            while (result.next()) {
                //A* Asignar valores de campos a un objeto InfoBlock
                cr = new InfoBlock();
                this.setPropertiesInfoBlock(cr, result); //m�todo ayudante
                // Agregar el objeto InfoBlock a la lista de objetos InfoBlock
                aList.add(cr);
            }
            result.close();
        } catch (SQLException e) {
            e.toString();
            throw new InfoBlockException("InfoBlockDAOImpl.readAllInfoBlock\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return aList;
    }

    public void setPropertiesInfoBlock(InfoBlock rec, ResultSet rs) throws InfoBlockException {
        try {
            rec.setId(rs.getInt("inb_id"));
            rec.setDatetime(rs.getTimestamp("inb_date"));
            rec.setStatus(rs.getInt("inb_status"));
            rec.setUser(rs.getString("inb_user"));
            rec.setType(rs.getString("inb_type"));
            rec.setHeader(rs.getString("inb_header"));
            rec.setBody(rs.getString("inb_body"));
            rec.setScope(rs.getInt("inb_scope"));
        } catch (SQLException e) {
            throw new InfoBlockException("InfoBlockDAOImpl.setPropertiesInfoBlock\n" + e);
        }
    }
}
