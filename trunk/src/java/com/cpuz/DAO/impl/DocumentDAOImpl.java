/*
 * DocumentDAOImpl.java
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.cpuz.DAO.impl;

import com.cpuz.domain.Document;
import com.cpuz.exceptions.DocumentException;
import com.cpuz.tools.JDBCHelper;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.naming.NamingException;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class DocumentDAOImpl implements DocumentDAO {

    // JNDI name of the data source this class requires
    private static final String DATA_SOURCE_NAME = "CPUZ";
    private JDBCHelper jdbcHelper;
    private PreparedStatement selectStatement = null;
    private PreparedStatement insertStatement = null;

    /** Creates a new instance of DocumentDAOImpl
     * 
     */
    public DocumentDAOImpl() throws Exception {
        jdbcHelper = new JDBCHelper(DATA_SOURCE_NAME);
    }

    public boolean keyIdExists(int ssn) throws SQLException, NamingException {
        Connection connection = null;
        boolean exists = false;
        String selectStr = "SELECT doc_id FROM documents WHERE doc_id = " + ssn;
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
     * Adds the Document to the Document model
     */
    public synchronized int createDocument(Document rec)
            throws DocumentException, SQLException {

        String pattern = "yyyy-MM-dd hh:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat();
        // apply the pattern to the SimpleDateFormat class
        sdf.applyPattern(pattern);


        Connection connection = null;
        // Declarar variables de uso en la frase SQL
        //** crear la frase INSERT SQL
        String request= "INSERT INTO documents "
                + "(doc_id, "
                + "doc_date, "
                + "doc_user, "
                + "doc_user_reference, "
                + "doc_filename, "
                + "doc_repository_reference, "
                + "doc_scope) "
                + " VALUES ("
                + rec.getId() + ","
                + "'" + (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(rec.getDatetime())) + "',"
                + "'" + rec.getUser() + "',"
                + "'" + rec.getUserReference() + "',"
                + "'" + rec.getFilename() + "',"
                + "'" + rec.getRepositoryReference() + "',"
                + rec.getScope() + ")";
        int rowCount = 0;

        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            //** obtener una variable de gestáón de SQL
            insertStatement = connection.prepareStatement(request);
            //** obtener el número de registros insertados
            rowCount = insertStatement.executeUpdate();
            request= "SELECT LAST_INSERT_id() FROM documents ";
            insertStatement = connection.prepareStatement(request);
            ResultSet rs = insertStatement.executeQuery();
            if (rs.next()) {
                rec.setId(rs.getInt(1));
            } else {
                rec.setId(0);
            }
        } catch (Exception e) {
            e.toString();
            throw new DocumentException("DocumentDAOImpl.createDocument\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return rowCount;
    }

    /**-------------------------------------------------------------
     * deletes the Document from the Document model
     */
    public int deleteDocument(Document rec)
            throws DocumentException, SQLException {
        Connection connection = null;
        int rowCount = 0;
        int keyId;
        //** 1 Assign id with id from rec object
        keyId = rec.getId();
        //** crear la frase DELETE SQL de tabla1
        String request= "DELETE FROM documents WHERE doc_id = " + keyId;
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
            throw new DocumentException("DocumentModelImpl.deleteDocument\n" + e);
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
     * @throws com.cpuz.exceptions.DocumentException
     * @throws java.sql.SQLException
     */
    @Override
    public int deleteDocument(String sqlWhereClause) throws DocumentException, SQLException {
        Connection connection = null;
        int rowCount = 0;
        //** crear la frase DELETE SQL de tabla1
        String request= "DELETE FROM documents " + sqlWhereClause;
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
            throw new DocumentException("DocumentModelImpl.deleteDocument\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return rowCount;
    }

    /**-------------------------------------------------------------
     * Updates the Document in the Document model
     */
    public int updateDocument(Document rec)
            throws DocumentException, SQLException {
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
        String request= "UPDATE documents SET "
                + "doc_user_reference = " + "'" + rec.getUserReference() + "',"
                + "doc_filename = " + "'" + rec.getFilename() + "',"
                + "doc_repository_reference = " + "'" + rec.getRepositoryReference() + "',"
                + "doc_scope = " + rec.getScope()
                + " WHERE doc_id = " + rec.getId();

        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            //** obtener una variable de gestáón de SQL
            selectStatement = connection.prepareStatement(request);
            //** obtener el número de registros actualizados
            rowCount = selectStatement.executeUpdate(request);
        } catch (Exception e) {
            e.toString();
            throw new DocumentException("DocumentModelImpl.updateDocumentInfo\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return rowCount;
    }
    // Document segment state query methods

    /**-------------------------------------------------------------
     * Given an ssn, returns the Document from the model
     */
    public Document readDocument(String keyId)
            throws DocumentException, SQLException, NamingException {
        // Declarar variables de uso en la frase SQL
        Connection connection = null;
        //** crear la frase SELECT SQL
        String request= "SELECT * FROM documents WHERE doc_id = " + keyId;
        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            ResultSet result = null;
            Document cr = null;
            //** obtener una variable de gestáón de SQL
            selectStatement = connection.prepareStatement(request);
            //** obtener el resultset de registros obtenidos
            result = selectStatement.executeQuery(request);
            // The following selectStatement checks if query successful
            if (result.next()) {
                //** Asignar valores de columnas a un objeto Document
                cr = new Document();
                this.setPropertiesDocument(cr, result); //m�todo ayudante
            } else {
                // if query failed
                throw new DocumentException("Document for " + keyId + " not found.");
            }
            result.close();
            // return cr
            return cr;
        } catch (SQLException e) {
            e.toString();
            throw new DocumentException("DocumentModelImpl.readDocument\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
    }

    /**-------------------------------------------------------------
     * Returns all documents in the Document model
     */
    @Override
    public List<Document> readDocuments()
            throws DocumentException, SQLException, NamingException {

        return this.readDocuments("");
    }

    @Override
    public List<Document> readDocuments(String sqlClause) throws DocumentException, SQLException, NamingException {
        Connection connection = null;
        //** crear la frase SELECT SQL
        List<Document> aList = new ArrayList<Document>();
        String request= "";
        if (sqlClause == null) {
            request= "SELECT * FROM documents ";
        } else {
            request= sqlClause;
        }


        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            ResultSet result = null;
            Document cr = null;
//            connection = obtainConnection();
            //** obtener una variable de gestáón de SQL
            selectStatement = connection.prepareStatement(request);

            //** obtener el resultset de registros obtenidos
            result = selectStatement.executeQuery(request);
            while (result.next()) {
                //A* Asignar valores de campos a un objeto Document
                cr = new Document();
                this.setPropertiesDocument(cr, result); //m�todo ayudante
                // Agregar el objeto Document a la lista de objetos Document
                aList.add(cr);
            }
            result.close();
        } catch (SQLException e) {
            e.toString();
            throw new DocumentException("DocumentDAOImpl.readAllDocument\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return aList;
    }

    public void setPropertiesDocument(Document rec, ResultSet rs) throws DocumentException {
        try {
            rec.setId(rs.getInt("doc_id"));
            rec.setDatetime(rs.getTimestamp("doc_date"));
            rec.setUser(rs.getString("doc_user"));
            rec.setUserReference(rs.getString("doc_user_reference"));
            rec.setFilename(rs.getString("doc_filename"));
            rec.setRepositoryReference(rs.getString("doc_repository_reference"));
            rec.setScope(rs.getInt("doc_scope"));
        } catch (SQLException e) {
            throw new DocumentException("DocumentDAOImpl.setPropertiesDocument\n" + e);
        }
    }
}
