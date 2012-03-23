/*
 * ImageDAOImpl.java
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.cpuz.DAO.impl;

import com.cpuz.domain.Image;
import com.cpuz.exceptions.ImageException;
import com.cpuz.tools.JDBCHelper;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.naming.NamingException;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class ImageDAOImpl implements ImageDAO {

    // JNDI name of the data source this class requires
    private static final String DATA_SOURCE_NAME = "CPUZ";
    private JDBCHelper jdbcHelper;
    private PreparedStatement selectStatement = null;
    private PreparedStatement insertStatement = null;

    /** Creates a new instance of ImageDAOImpl
     * 
     */
    public ImageDAOImpl() throws Exception {
        jdbcHelper = new JDBCHelper(DATA_SOURCE_NAME);
    }

    public boolean keyIdExists(int ssn) throws SQLException, NamingException {
        Connection connection = null;
        boolean exists = false;
        String selectStr = "SELECT img_id FROM images WHERE img_id = " + ssn;
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
     * Adds the Image to the Image model
     */
    public synchronized int createImage(Image rec)
            throws ImageException, SQLException {

                String pattern = "yyyy-MM-dd hh:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat();
        // apply the pattern to the SimpleDateFormat class
        sdf.applyPattern(pattern);


        Connection connection = null;
        // Declarar variables de uso en la frase SQL
        //** crear la frase INSERT SQL
        String request= "INSERT INTO images " +
                "(img_id, " +
                "img_date, " +
                "img_user, " +
                "img_user_reference, " +
                "img_filename, " +
                "img_repository_reference, " +
                "img_scope) " +
                " VALUES (" +
                rec.getId() + "," +
                "'" + (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(rec.getDatetime()))+"'," +
                "'" + rec.getUser() + "'," +
                "'" + rec.getUserReference() + "'," +
                "'" + rec.getFilename() + "'," +
                "'" + rec.getRepositoryReference() + "'," +
                rec.getScope() + ")";
        int rowCount = 0;

        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            //** obtener una variable de gestáón de SQL
            insertStatement = connection.prepareStatement(request);
            //** obtener el número de registros insertados
            rowCount = insertStatement.executeUpdate();
            request= "SELECT LAST_INSERT_id() FROM images ";
            insertStatement = connection.prepareStatement(request);
            ResultSet rs = insertStatement.executeQuery();
            if (rs.next()) {
                rec.setId(rs.getInt(1));
            } else {
                rec.setId(0);
            }
        } catch (Exception e) {
            e.toString();
            throw new ImageException("ImageDAOImpl.createImage\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
            return rowCount;
    }

    /**-------------------------------------------------------------
     * deletes the Image from the Image model
     */
    public int deleteImage(Image rec)
            throws ImageException, SQLException {
        Connection connection = null;
        int rowCount = 0;
        int keyId;
        //** 1 Assign id with id from rec object
        keyId = rec.getId();
        //** crear la frase DELETE SQL de tabla1
        String request= "DELETE FROM images WHERE img_id = " + keyId;
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
            throw new ImageException("ImageModelImpl.deleteImage\n" + e);
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
     * @throws com.cpuz.exceptions.ImageException
     * @throws java.sql.SQLException
     */
    public int deleteImage(String sqlWhereClause) throws ImageException, SQLException {
        Connection connection = null;
        int rowCount = 0;
        //** crear la frase DELETE SQL de tabla1
        String request= "DELETE FROM images " + sqlWhereClause;
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
            throw new ImageException("ImageModelImpl.deleteImage\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
            return rowCount;
    }

    /**-------------------------------------------------------------
     * Updates the Image in the Image model
     */
    public int updateImage(Image rec)
            throws ImageException, SQLException {
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
        String request= "UPDATE images SET " +
                "img_user_reference = " + "'" + rec.getUserReference() + "'," +
                "img_filename = " + "'" + rec.getFilename() + "'," +
                "img_repository_reference = " + "'" + rec.getRepositoryReference() + "'," +
                "img_scope = " + rec.getScope() +
                " WHERE img_id = " + rec.getId();

        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            //** obtener una variable de gestáón de SQL
            selectStatement = connection.prepareStatement(request);
            //** obtener el número de registros actualizados
            rowCount = selectStatement.executeUpdate(request);
        } catch (Exception e) {
            e.toString();
            throw new ImageException("ImageModelImpl.updateImageInfo\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
            return rowCount;
    }
    // Image segment state query methods

    /**-------------------------------------------------------------
     * Given an ssn, returns the Image from the model
     */
    public Image readImage(String keyId)
            throws ImageException, SQLException, NamingException {
        // Declarar variables de uso en la frase SQL
        Connection connection = null;
        //** crear la frase SELECT SQL
        String request= "SELECT * FROM images WHERE img_id = " + keyId;
        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            ResultSet result = null;
            Image cr = null;
            //** obtener una variable de gestáón de SQL
            selectStatement = connection.prepareStatement(request);
            //** obtener el resultset de registros obtenidos
            result = selectStatement.executeQuery(request);
            // The following selectStatement checks if query successful
            if (result.next()) {
                //** Asignar valores de columnas a un objeto Image
                cr = new Image();
                this.setPropertiesImage(cr, result); //m�todo ayudante
            } else {
                // if query failed
                throw new ImageException("Image for " + keyId + " not found.");
            }
            result.close();
            // return cr
            return cr;
        } catch (SQLException e) {
            e.toString();
            throw new ImageException("ImageModelImpl.readImage\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
    }

    /**-------------------------------------------------------------
     * Returns all Images in the Image model
     */
    public List<Image> readImages()
            throws ImageException, SQLException, NamingException {

        return this.readImages("");
    }

    public List<Image> readImages(String sqlClause) throws ImageException, SQLException, NamingException {
        Connection connection = null;
        //** crear la frase SELECT SQL
        List<Image> aList = new ArrayList<Image>();
        String request= "";
        if (sqlClause == null) {
            request= "SELECT * FROM images ";
        } else {
            request= sqlClause;
        }


        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            ResultSet result = null;
            Image cr = null;
//            connection = obtainConnection();
            //** obtener una variable de gestáón de SQL
            selectStatement = connection.prepareStatement(request);

            //** obtener el resultset de registros obtenidos
            result = selectStatement.executeQuery(request);
            while (result.next()) {
                //A* Asignar valores de campos a un objeto Image
                cr = new Image();
                this.setPropertiesImage(cr, result); //m�todo ayudante
                // Agregar el objeto Image a la lista de objetos Image
                aList.add(cr);
            }
            result.close();
        } catch (SQLException e) {
            e.toString();
            throw new ImageException("ImageDAOImpl.readAllImage\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return aList;
    }

    public void setPropertiesImage(Image rec, ResultSet rs) throws ImageException {
        try {
            rec.setId(rs.getInt("img_id"));
            rec.setDatetime(rs.getTimestamp("img_date"));
            rec.setUser(rs.getString("img_user"));
            rec.setUserReference(rs.getString("img_user_reference"));
            rec.setFilename(rs.getString("img_filename"));
            rec.setRepositoryReference(rs.getString("img_repository_reference"));
            rec.setScope(rs.getInt("img_scope"));
        } catch (SQLException e) {
            throw new ImageException("ImageDAOImpl.setPropertiesImage\n" + e);
        }
    }
}
