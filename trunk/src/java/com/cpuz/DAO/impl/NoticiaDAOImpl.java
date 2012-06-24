/*
 * NoticiaDAOImpl.java
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.cpuz.DAO.impl;

import com.cpuz.domain.Noticia;
import com.cpuz.exceptions.UserException;
import com.cpuz.tools.JDBCHelper;
import com.cpuz.tools.Tools;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.naming.NamingException;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class NoticiaDAOImpl implements NoticiaDAO {

    // JNDI name of the data source this class requires
    private static final String DATA_SOURCE_NAME = "ULT";
    private JDBCHelper jdbcHelper;
    private PreparedStatement selectStatement = null;
    private PreparedStatement insertStatement = null;

    /** Creates a new instance of NoticiaDAOImpl
     * 
     */
    public NoticiaDAOImpl() throws Exception {
        jdbcHelper = new JDBCHelper(DATA_SOURCE_NAME);
    }

    public boolean keyIdExists(int ssn) throws SQLException, NamingException {
        Connection connection = null;
        boolean exists = false;
        String selectStr = "SELECT NOT_id FROM NOTICIAS WHERE NOT_id = " + ssn;
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
     * Adds the Noticia to the Noticia model
     */
    public synchronized int createNoticia(Noticia rec)
            throws UserException, SQLException {
        Connection connection = null;
        // Declarar variables de uso en la frase SQL
        //** crear la frase INSERT SQL
        String request= "INSERT INTO NOTICIAS " +
                "(NOT_id, " +
                "NOT_FECHA, " +
                "NOT_ESTADO, " +
                "NOT_NOMBRE, " +
                "NOT_DNINIE, " +
                "NOT_EMAIL, " +
                "NOT_id_PROPIETARIO, " +
                "NOT_ORDEN, " +
                "NOT_TIPO_TITULAR, " +
                "NOT_TITULAR, " +
                "NOT_CONTENIDO, " +
                "NOT_FICHERO_IMAGEN, " +
                "NOT_ENLACE, " +
                "NOT_ANCHO_IMAGEN, " +
                "NOT_ALTO_IMAGEN, " +
                "NOT_POSICION_IMAGEN) " +
                " VALUES (" +
                rec.getId() + "," +
                "'" + (new SimpleDateFormat("yyyy-MM-dd").format(rec.getFecha())) + "'," +
                rec.getEstado() + "," +
                "'" + rec.getNombre() + "'," +
                "'" + rec.getDninie() + "'," +
                "'" + rec.getEmail() + "'," +
                rec.getIdPropietario() + "," +
                rec.getOrden() + "," +
                "'" + rec.getTipoTitular() + "'," +
                "'" + rec.getTitular() + "'," +
                "'" + Tools.EscapeSingleQuote(rec.getContenido()) + "'," +
                "'" + rec.getFicheroImagen() + "'," +
                "'" + rec.getEnlace() + "'," +
                rec.getAnchoImagen() + "," +
                rec.getAltoImagen() + "," +
                "'" + rec.getPosicionImagen() + "')";
        int rowCount = 0;

        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            //** obtener una variable de gestáón de SQL
            insertStatement = connection.prepareStatement(request);
            //** obtener el número de registros insertados
            rowCount = insertStatement.executeUpdate();
            request= "SELECT LAST_INSERT_id() FROM NOTICIAS ";
            insertStatement = connection.prepareStatement(request);
            ResultSet rs = insertStatement.executeQuery();
            if (rs.next()) {
                rec.setId(rs.getInt(1));
            } else {
                rec.setId(0);
            }
        } catch (Exception e) {
            e.toString();
            throw new UserException("NoticiaDAOImpl.createNoticia\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
            return rowCount;
    }

    /**-------------------------------------------------------------
     * deletes the Noticia from the Noticia model
     */
    public int deleteNoticia(Noticia rec)
            throws UserException, SQLException {
        Connection connection = null;
        int rowCount = 0;
        int keyId;
        //** 1 Assign id with id from rec object
        keyId = rec.getId();
        //** crear la frase DELETE SQL de tabla1
        String request= "DELETE FROM NOTICIAS WHERE NOT_id = " + keyId;
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
            throw new UserException("NoticiaModelImpl.deleteNoticia\n" + e);
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
    public int deleteNoticia(String sqlWhereClause) throws UserException, SQLException {
        Connection connection = null;
        int rowCount = 0;
        //** crear la frase DELETE SQL de tabla1
        String request= "DELETE FROM NOTICIAS " + sqlWhereClause;
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
            throw new UserException("NoticiaModelImpl.deleteNoticia\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
            return rowCount;
    }

    /**-------------------------------------------------------------
     * Updates the Noticia in the Noticia model
     */
    public int updateNoticia(Noticia rec)
            throws UserException, SQLException {
        Connection connection = null;
        String s = rec.getContenido();
        int inicio=0;
        while (s.indexOf("'",inicio)>=0){
            s=s.substring(0,s.indexOf("'",inicio))+"\\"+s.substring(s.indexOf("'",inicio));
            inicio=s.indexOf("'",inicio);
            inicio++;
        }
        int rowCount = 0;
        //** crear la frase UPDATE SQL
        String request= "UPDATE NOTICIAS SET " + "" +
                "NOT_FECHA = " + "'" + (new SimpleDateFormat("yyyy-MM-dd").format(rec.getFecha())) + "'," +
                "NOT_ESTADO = " + rec.getEstado() + "," +
                "NOT_NOMBRE = " + "'" + rec.getNombre() + "'," +
                "NOT_DNINIE = " + "'" + rec.getDninie() + "'," +
                "NOT_EMAIL = " + "'" + rec.getEmail() + "'," +
                "NOT_id_PROPIETARIO = " + rec.getIdPropietario() + "," +
                "NOT_ORDEN = " + rec.getOrden() + "," +
                "NOT_TIPO_TITULAR = " + "'" + rec.getTipoTitular() + "'," +
                "NOT_TITULAR = " + "'" + rec.getTitular() + "'," +
                "NOT_CONTENIDO = " + "'" + Tools.EscapeSingleQuote(rec.getContenido()) + "'," +
                "NOT_FICHERO_IMAGEN = " + "'" + rec.getFicheroImagen() + "'," +
                "NOT_ENLACE = " + "'" + rec.getEnlace() + "'," +
                "NOT_ANCHO_IMAGEN = " + rec.getAnchoImagen() + "," +
                "NOT_ALTO_IMAGEN = " + rec.getAltoImagen() + "," +
                "NOT_POSICION_IMAGEN = " + "'" + rec.getPosicionImagen() + "' " +
                " WHERE NOT_id = " + rec.getId();

        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            //** obtener una variable de gestáón de SQL
            selectStatement = connection.prepareStatement(request);
            //** obtener el número de registros actualizados
            rowCount = selectStatement.executeUpdate(request);
        } catch (Exception e) {
            e.toString();
            throw new UserException("NoticiaModelImpl.updateNoticiaInfo\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
            return rowCount;
    }
    // Noticia segment state query methods

    /**-------------------------------------------------------------
     * Given an ssn, returns the Noticia from the model
     */
    public Noticia readNoticia(String keyId)
            throws UserException, SQLException, NamingException {
        // Declarar variables de uso en la frase SQL
        Connection connection = null;
        //** crear la frase SELECT SQL
        String request= "SELECT * FROM NOTICIAS WHERE NOT_id = " + keyId;
        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            ResultSet result = null;
            Noticia cr = null;
            //** obtener una variable de gestáón de SQL
            selectStatement = connection.prepareStatement(request);
            //** obtener el resultset de registros obtenidos
            result = selectStatement.executeQuery(request);
            // The following selectStatement checks if query successful
            if (result.next()) {
                //** Asignar valores de columnas a un objeto Noticia
                cr = new Noticia();
                this.setPropertiesNoticia(cr, result); //m�todo ayudante
            } else {
                // if query failed
                throw new UserException("Noticia for " + keyId + " not found.");
            }
            result.close();
            // return cr
            return cr;
        } catch (SQLException e) {
            e.toString();
            throw new UserException("NoticiaModelImpl.readNoticia\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
    }

    /**-------------------------------------------------------------
     * Returns all Noticias in the Noticia model
     */
    public Noticia[] readAllNoticias()
            throws UserException, SQLException, NamingException {

        return this.readAllNoticias("");
    }

    public Noticia[] readAllNoticias(String sqlWhereClause) throws UserException, SQLException, NamingException {
        Connection connection = null;
        //** crear la frase SELECT SQL
        String request= "SELECT * FROM NOTICIAS " + sqlWhereClause;
        Noticia[] all = null;

        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            ResultSet result = null;
            Noticia cr = null;
            Noticia[] temp = new Noticia[1];
            ArrayList<Noticia> aList = new ArrayList<Noticia>(1);
            // verifica que la condición es una expresi�n correcta
            if (sqlWhereClause == null) {
                sqlWhereClause = "";
            }
//            connection = obtainConnection();
            //** obtener una variable de gestáón de SQL
            selectStatement = connection.prepareStatement(request);

            //** obtener el resultset de registros obtenidos
            result = selectStatement.executeQuery(request);
            while (result.next()) {
                //A* Asignar valores de campos a un objeto Noticia
                cr = new Noticia();
                this.setPropertiesNoticia(cr, result); //m�todo ayudante
                // Agregar el objeto Noticia a la lista de objetos Noticia
                aList.add(cr);
            }
            result.close();
            if (aList.size() > 0) {
                //Q? for an explanation of the following line of code
                //** lookup javadoc for
                all = aList.toArray(temp);
            } else {
                all = null;
            }
        } catch (SQLException e) {
            all = null;
            e.toString();
            throw new UserException("NoticiaDAOImpl.readAllNoticia\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
            return all;
    }

    public void setPropertiesNoticia(Noticia rec, ResultSet rs) throws UserException {
        try {
            rec.setId(rs.getInt("NOT_id"));
            rec.setFecha(rs.getDate("NOT_FECHA"));
            rec.setEstado(rs.getInt("NOT_ESTADO"));
            rec.setNombre(rs.getString("NOT_NOMBRE"));
            rec.setDninie(rs.getString("NOT_DNINIE"));
            rec.setEmail(rs.getString("NOT_EMAIL"));
            rec.setIdPropietario(rs.getInt("NOT_id_PROPIETARIO"));
            rec.setOrden(rs.getInt("NOT_ORDEN"));
            rec.setTipoTitular(rs.getString("NOT_TIPO_TITULAR"));
            rec.setTitular(rs.getString("NOT_TITULAR"));
            rec.setContenido(rs.getString("NOT_CONTENIDO"));
            rec.setFicheroImagen(rs.getString("NOT_FICHERO_IMAGEN"));
            rec.setEnlace(rs.getString("NOT_ENLACE"));
            rec.setAnchoImagen(rs.getInt("NOT_ANCHO_IMAGEN"));
            rec.setAltoImagen(rs.getInt("NOT_ALTO_IMAGEN"));
            rec.setPosicionImagen(rs.getString("NOT_POSICION_IMAGEN"));
        } catch (SQLException e) {
            throw new UserException("NoticiaDAOImpl.setPropertiesNoticia\n" + e);
        }
    }
}
