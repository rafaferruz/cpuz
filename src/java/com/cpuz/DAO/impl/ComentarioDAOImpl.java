/*
 * ComentarioDAOImpl.java
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.cpuz.DAO.impl;

import com.cpuz.domain.Comentario;
import com.cpuz.exceptions.ComentarioException;
import com.cpuz.tools.JDBCHelper;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.naming.NamingException;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class ComentarioDAOImpl implements ComentarioDAO {

    // JNDI name of the data source this class requires
    private static final String DATA_SOURCE_NAME = "ULT";
    private JDBCHelper jdbcHelper;
    private PreparedStatement selectStatement = null;
    private PreparedStatement insertStatement = null;

    /** Creates a new instance of ComentarioDAOImpl
     *
     */
    public ComentarioDAOImpl() throws Exception {
        jdbcHelper = new JDBCHelper(DATA_SOURCE_NAME);
    }

    public boolean keyIdExists(int ssn) throws SQLException, NamingException {
        Connection connection = null;
        boolean exists = false;
        String selectStr = "SELECT COM_id FROM COMENTARIOS WHERE COM_id = " + ssn;
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
     * Adds the Comentario to the Comentario model
     */
    public synchronized int createComentario(Comentario rec)
            throws ComentarioException, SQLException {
        Connection connection = null;
        // Declarar variables de uso en la frase SQL
        //** crear la frase INSERT SQL
        String request= "INSERT INTO COMENTARIOS " +
                "(COM_id, " +
                "COM_FECHA, " +
                "COM_ESTADO, " +
                "COM_AMBITO, " +
                "COM_NOMBRE, " +
                "COM_PARCELA, " +
                "COM_EMAIL, " +
                "COM_PUBLICAR, " +
                "COM_TITULO, " +
                "COM_COMENTARIO, " +
                "COM_FICHERO_IMAGEN) " +
                " VALUES (" +
                rec.getId() + "," +
                "'" + (new SimpleDateFormat("yyyy-MM-dd").format(rec.getFecha())) + "'," +
                rec.getEstado() + "," +
                "'" + rec.getAmbito() + "'," +
                "'" + rec.getNombre() + "'," +
                "'" + rec.getParcela() + "'," +
                "'" + rec.getEmail() + "'," +
                "'" + rec.getPublicar() + "'," +
                "'" + rec.getTitulo() + "'," +
                "'" + rec.getComentario() + "'," +
                "'" + rec.getFicheroImagen() + "')";
        int rowCount = 0;
        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            //** obtener una variable de gestáón de SQL
            insertStatement = connection.prepareStatement(request);
            //** obtener el número de registros insertados
            rowCount = insertStatement.executeUpdate();
            request= "SELECT LAST_INSERT_id() FROM COMENTARIOS ";
            insertStatement = connection.prepareStatement(request);
            ResultSet rs = insertStatement.executeQuery();
            if (rs.next()) {
                rec.setId(rs.getInt(1));
            } else {
                rec.setId(0);
            }
        } catch (Exception e) {
            e.toString();
            throw new ComentarioException("ComentarioDAOImpl.createComentario\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return rowCount;
    }

    /**-------------------------------------------------------------
     * deletes the Comentario from the Comentario model
     */
    public int deleteComentario(Comentario rec)
            throws ComentarioException, SQLException {
        Connection connection = null;
        int rowCount = 0;
        int keyId;
        //** 1 Assign id with id from rec object
        keyId = rec.getId();
        //** crear la frase DELETE SQL de tabla1
        String request= "DELETE FROM COMENTARIOS WHERE COM_id = " + keyId;
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
            throw new ComentarioException("ComentarioModelImpl.deleteComentario\n" + e);
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
     * @throws com.cpuz.exceptions.ComentarioException
     * @throws java.sql.SQLException
     */
    public int deleteComentario(String sqlWhereClause) throws ComentarioException, SQLException {
        Connection connection = null;
        int rowCount = 0;
        //** crear la frase DELETE SQL de tabla1
        String request= "DELETE FROM COMENTARIOS " + sqlWhereClause;
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
            throw new ComentarioException("ComentarioModelImpl.deleteComentario\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return rowCount;
    }

    /**-------------------------------------------------------------
     * Updates the Comentario in the Comentario model
     */
    public int updateComentario(Comentario rec)
            throws ComentarioException, SQLException {
        Connection connection = null;
        int rowCount = 0;
        //** crear la frase UPDATE SQL
        String request= "UPDATE COMENTARIOS SET " + "" +
                "COM_FECHA = " + "'" + (new SimpleDateFormat("yyyy-MM-dd").format(rec.getFecha())) + "'," +
                "COM_ESTADO = " + rec.getEstado() + "," +
                "COM_AMBITO = " + "'" + rec.getAmbito() + "'," +
                "COM_NOMBRE = " + "'" + rec.getNombre() + "'," +
                "COM_PARCELA = " + "'" + rec.getParcela() + "'," +
                "COM_EMAIL = " + "'" + rec.getEmail() + "'," +
                "COM_PUBLICAR = " + "'" + rec.getPublicar() + "'," +
                "COM_TITULO = " + "'" + rec.getTitulo() + "'," +
                "COM_COMENTARIO = " + "'" + rec.getComentario() + "'," +
                "COM_FICHERO_IMAGEN = " + "'" + rec.getFicheroImagen() + "' " +
                " WHERE COM_id = " + rec.getId();

        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            //** obtener una variable de gestáón de SQL
            selectStatement = connection.prepareStatement(request);
            //** obtener el número de registros actualizados
            rowCount = selectStatement.executeUpdate(request);
        } catch (Exception e) {
            e.toString();
            throw new ComentarioException("ComentarioModelImpl.updateComentarioInfo\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return rowCount;
    }
    // Comentario segment state query methods

    /**-------------------------------------------------------------
     * Given an ssn, returns the Comentario from the model
     */
    public Comentario readComentario(String keyId)
            throws ComentarioException, SQLException, NamingException {
        // Declarar variables de uso en la frase SQL
        Connection connection = null;
        //** crear la frase SELECT SQL
        String request= "SELECT * FROM COMENTARIOS WHERE COM_id = " + keyId;
        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            ResultSet result = null;
            Comentario cr = null;
            //** obtener una variable de gestáón de SQL
            selectStatement = connection.prepareStatement(request);
            //** obtener el resultset de registros obtenidos
            result = selectStatement.executeQuery(request);
            // The following selectStatement checks if query successful
            if (result.next()) {
                //** Asignar valores de columnas a un objeto Comentario
                cr = new Comentario();
                this.setPropertiesComentario(cr, result); //m�todo ayudante
            } else {
                // if query failed
                throw new ComentarioException("Comentario for " + keyId + " not found.");
            }
            result.close();
            // return cr
            return cr;
        } catch (SQLException e) {
            e.toString();
            throw new ComentarioException("ComentarioModelImpl.readComentario\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
    }

    /**-------------------------------------------------------------
     * Returns all Comentarios in the Comentario model
     */
    public Comentario[] readAllComentarios()
            throws ComentarioException, SQLException, NamingException {

        return this.readAllComentarios("");
    }

    public Comentario[] readAllComentarios(String sqlWhereClause) throws ComentarioException, SQLException, NamingException {
        Connection connection = null;
        //** crear la frase SELECT SQL
        String request= "SELECT * FROM COMENTARIOS " + sqlWhereClause;
        Comentario[] all = null;

        try {
            //** obtener una conexión a la BD
            connection = jdbcHelper.getConnection();
            ResultSet result = null;
            Comentario cr = null;
            Comentario[] temp = new Comentario[1];
            ArrayList<Comentario> aList = new ArrayList<Comentario>(1);
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
                //A* Asignar valores de campos a un objeto Comentario
                cr = new Comentario();
                this.setPropertiesComentario(cr, result); //m�todo ayudante
                // Agregar el objeto Comentario a la lista de objetos Comentario
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
            throw new ComentarioException("ComentarioDAOImpl.readAllComentario\n" + e);
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
        return all;
    }

    public void setPropertiesComentario(Comentario rec, ResultSet rs) throws ComentarioException {
        try {
            rec.setId(rs.getInt("COM_id"));
            rec.setFecha(rs.getDate("COM_FECHA"));
            rec.setEstado(rs.getInt("COM_ESTADO"));
            rec.setAmbito(rs.getString("COM_AMBITO"));
            rec.setNombre(rs.getString("COM_NOMBRE"));
            rec.setParcela(rs.getString("COM_PARCELA"));
            rec.setEmail(rs.getString("COM_EMAIL"));
            rec.setPublicar(rs.getString("COM_PUBLICAR"));
            rec.setTitulo(rs.getString("COM_TITULO"));
            rec.setComentario(rs.getString("COM_COMENTARIO"));
            rec.setFicheroImagen(rs.getString("COM_FICHERO_IMAGEN"));
        } catch (SQLException e) {
            throw new ComentarioException("ComentarioDAOImpl.setPropertiesComentario\n" + e);
        }
    }
}
