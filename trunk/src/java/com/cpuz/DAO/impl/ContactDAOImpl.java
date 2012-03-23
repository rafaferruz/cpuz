/*
 * ContactDAOImpl.java
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.cpuz.DAO.impl;

import com.cpuz.domain.Contact;
import com.cpuz.exceptions.ContactException;
import com.cpuz.tools.JDBCHelper;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.naming.NamingException;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class ContactDAOImpl implements ContactDAO {

    // JNDI name of the data source this class requires
    private static final String DATA_SOURCE_NAME = "CPUZ";
    private JDBCHelper jdbcHelper;
    private PreparedStatement selectStatement = null;
    private PreparedStatement insertStatement = null;

    /** Creates a new instance of ContactDAOImpl
     * 
     */
    public ContactDAOImpl() throws Exception {
        jdbcHelper = new JDBCHelper(DATA_SOURCE_NAME);
    }

    public boolean keyIdExists(int ssn) throws SQLException, NamingException {
        Connection connection = null;
        boolean exists = false;
        String selectStr = "SELECT con_id FROM contacts WHERE con_id = " + ssn;
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
     * Adds the Contact to the Contact model
     */
    public synchronized int createContact(Contact rec)
            throws ContactException, SQLException {

        String pattern = "yyyy-MM-dd hh:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat();
        // apply the pattern to the SimpleDateFormat class
        sdf.applyPattern(pattern);


        Connection connection = null;
        // Declarar variables de uso en la frase SQL
        //** crear la frase INSERT SQL
        String request= "INSERT INTO contacts "
                + "(con_date, "
                + "con_status, "
                + "con_user, "
                + "con_target, "
                + "con_header, "
                + "con_body, "
                + "con_email) "
                + " VALUES ("
                + "'" + (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(rec.getDatetime())) + "',"
                + rec.getStatus() + ","
                + "'" + rec.getUser() + "',"
                + "'" + rec.getTarget() + "',"
                + "'" + rec.getHeader() + "',"
                + "'" + rec.getBody() + "',"
                + "'" + rec.getEmail() + "')";
        int rowCount = 0;

        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            //** obtener una variable de gestáón de SQL
            insertStatement = connection.prepareStatement(request);
            //** obtener el número de registros insertados
            rowCount = insertStatement.executeUpdate();
            request= "SELECT LAST_INSERT_id() FROM contacts ";
            insertStatement = connection.prepareStatement(request);
            ResultSet rs = insertStatement.executeQuery();
            if (rs.next()) {
                rec.setId(rs.getInt(1));
            } else {
                rec.setId(0);
            }
        } catch (Exception e) {
            e.toString();
            throw new ContactException("ContactDAOImpl.createContact\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return rowCount;
    }

    /**-------------------------------------------------------------
     * deletes the Contact from the Contact model
     */
    public int deleteContact(Contact rec)
            throws ContactException, SQLException {
        Connection connection = null;
        int rowCount = 0;
        int keyId;
        //** 1 Assign id with id from rec object
        keyId = rec.getId();
        //** crear la frase DELETE SQL de tabla1
        String request= "DELETE FROM contacts WHERE con_id = " + keyId;
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
            throw new ContactException("ContactModelImpl.deleteContact\n" + e);
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
     * @throws com.cpuz.exceptions.ContactException
     * @throws java.sql.SQLException
     */
    public int deleteContact(String sqlWhereClause) throws ContactException, SQLException {
        Connection connection = null;
        int rowCount = 0;
        //** crear la frase DELETE SQL de tabla1
        String request= "DELETE FROM contacts " + sqlWhereClause;
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
            throw new ContactException("ContactModelImpl.deleteContact\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return rowCount;
    }

    /**-------------------------------------------------------------
     * Updates the Contact in the Contact model
     */
    public int updateContact(Contact rec)
            throws ContactException, SQLException {
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
        String request= "UPDATE contacts SET "
                + "con_status = " + rec.getStatus() + ","
                + "con_target = " + "'" + rec.getTarget() + "',"
                + "con_header = " + "'" + rec.getHeader() + "',"
                + "con_body = " + "'" + rec.getBody() + "',"
                + "con_email = " + "'"+rec.getEmail()+"' "
                + " WHERE con_id = " + rec.getId();

        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            //** obtener una variable de gestáón de SQL
            selectStatement = connection.prepareStatement(request);
            //** obtener el número de registros actualizados
            rowCount = selectStatement.executeUpdate(request);
        } catch (Exception e) {
            e.toString();
            throw new ContactException("ContactModelImpl.updateContactInfo\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return rowCount;
    }
    // Contact segment state query methods

    /**-------------------------------------------------------------
     * Given an ssn, returns the Contact from the model
     */
    public Contact readContact(String keyId)
            throws ContactException, SQLException, NamingException {
        // Declarar variables de uso en la frase SQL
        Connection connection = null;
        //** crear la frase SELECT SQL
        String request= "SELECT * FROM contacts WHERE con_id = " + keyId;
        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            ResultSet result = null;
            Contact cr = null;
            //** obtener una variable de gestáón de SQL
            selectStatement = connection.prepareStatement(request);
            //** obtener el resultset de registros obtenidos
            result = selectStatement.executeQuery(request);
            // The following selectStatement checks if query successful
            if (result.next()) {
                //** Asignar valores de columnas a un objeto Contact
                cr = new Contact();
                this.setPropertiesContact(cr, result); //m�todo ayudante
            } else {
                // if query failed
                throw new ContactException("Contact for " + keyId + " not found.");
            }
            result.close();
            // return cr
            return cr;
        } catch (SQLException e) {
            e.toString();
            throw new ContactException("ContactModelImpl.readContact\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
    }

    /**-------------------------------------------------------------
     * Returns all Contacts in the Contact model
     */
    /**-------------------------------------------------------------
     * Returns all Contacts in the Contact model
     */
    public List<Contact> readContacts()
            throws ContactException, SQLException, NamingException {

        return this.readContacts("");
    }

    public List<Contact> readContacts(String sqlClause) throws ContactException, SQLException, NamingException {
        Connection connection = null;
        //** crear la frase SELECT SQL
        List<Contact> aList = new ArrayList<Contact>();
        String request= "";
        if (sqlClause == null) {
            request= "SELECT * FROM contacts ";
        } else {
            request= sqlClause;
        }


        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            ResultSet result = null;
            Contact cr = null;
//            connection = obtainConnection();
            //** obtener una variable de gestáón de SQL
            selectStatement = connection.prepareStatement(request);

            //** obtener el resultset de registros obtenidos
            result = selectStatement.executeQuery(request);
            while (result.next()) {
                //A* Asignar valores de campos a un objeto Contact
                cr = new Contact();
                this.setPropertiesContact(cr, result); //m�todo ayudante
                // Agregar el objeto Contact a la lista de objetos Contact
                aList.add(cr);
            }
            result.close();
        } catch (SQLException e) {
            e.toString();
            throw new ContactException("ContactDAOImpl.readAllContact\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return aList;
    }

    public void setPropertiesContact(Contact rec, ResultSet rs) throws ContactException {
        try {
            rec.setId(rs.getInt("con_id"));
            rec.setDatetime(rs.getTimestamp("con_date"));
            rec.setStatus(rs.getInt("con_status"));
            rec.setUser(rs.getString("con_user"));
            rec.setTarget(rs.getString("con_target"));
            rec.setHeader(rs.getString("con_header"));
            rec.setBody(rs.getString("con_body"));
            rec.setEmail(rs.getString("con_email"));
        } catch (SQLException e) {
            throw new ContactException("ContactDAOImpl.setPropertiesContact\n" + e);
        }
    }
}
