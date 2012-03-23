/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.misc;

import com.cpuz.tools.JDBCHelper;
import java.sql.*;
import java.util.Date;
import javax.naming.NamingException;

/**
 * @author RAFAEL FERRUZ
 */
public class PageBean {

    // JNDI name of the data source this class requires
    private static final String DATA_SOURCE_NAME = "CPUZ";
    private Integer id;
    private java.sql.Date fecha;
    private String página;
    private String usersDeny;
    private String usersAllow;
    private String ordenejecutar;
    private String fechatexto;
    private boolean registered;
    private JDBCHelper jdbcHelper;
    private String fechaDesde;
    private String fechaHasta;
    private String buscartexto;
    private String user;
    private String fraseSql;

    public PageBean() {
        jdbcHelper = new JDBCHelper(DATA_SOURCE_NAME);
    }

    public void setId(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    public void setFecha(java.sql.Date value) {
        this.fecha = value;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFechatexto(String value) {
        this.fechatexto = value;


    }

    public String getFechatexto() {
        if (fecha == null) {
            return "null";
        }
        String fechatrabajo = fecha.toString();
        fechatexto = fechatrabajo.substring(8, 10) + "/" +
                fechatrabajo.substring(5, 7) + "/" +
                fechatrabajo.substring(0, 4);

        return fechatexto;
    }

    public void setPágina(String value) {
        this.página = value;
    }

    public String getPágina() {
        return página;
    }

    public void setUsersDeny(String value) {
        this.usersDeny = value;
    }

    public String getUsersDeny() {
        return usersDeny;
    }

    public void setUsersAllow(String value) {
        this.usersAllow = value;
    }

    public String getUsersAllow() {
        return usersAllow;
    }

    public void setOrdenejecutar(String value) {
        this.ordenejecutar = value;
    }

    public String getOrdenejecutar() {
        return ordenejecutar;
    }

    public void setUser(String value) {
        if (value.equals("")) {
            value = "undefined";
        }
        this.user = value;
    }

    public String getUser() {
        if (this.user.equals("undefined")) {
            this.user = "";
        }
        return user;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setFechaDesde(String value) {
        this.fechaDesde = value;
    }

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaHasta(String value) {
        this.fechaHasta = value;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setBuscartexto(String value) {
        this.buscartexto = value;
    }

    public String getBuscartexto() {
        return buscartexto;
    }

    public String getFraseSql() {

        String[] palabras = buscartexto.split(" ");

        String frasePalabras = "";

        // monto condición de selección entre fechas
        fraseSql = " WHERE ( pag_fecha >= '" + fechaDesde +
                "' AND pag_fecha <= '" + fechaHasta + "' ) ";

// monto la condición de selección por palabras
        for (int i = 1; i <= palabras.length; i++) {
            String palabra = palabras[i-1];
            if (i != 1) {
                frasePalabras = frasePalabras + " AND ";
            }
            frasePalabras = frasePalabras + " (pag_pagina LIKE '%" + palabra +
                    "%' OR pag_usersdeny LIKE '%" + palabra +
                    "%' OR pag_usersallow LIKE '%" + palabra + "%' ) ";

        }

        if (!frasePalabras.equals("")) {
            frasePalabras = " AND (" + frasePalabras + ")";
        }


        fraseSql = "SELECT * FROM paginas " + fraseSql + frasePalabras +
                "ORDER BY pag_pagina ";

        return fraseSql;
    }

    public void registerPágina() throws SQLException,
            javax.naming.NamingException {

        Connection connection = null;
        String insertStatementStr =
                "INSERT INTO PAGINAS VALUES(0, NOW(), ?, ?, ?)";
        String selectCustomerStr =
                "SELECT MAX(pag_id) " +
                " FROM paginas ";

        PreparedStatement insertStatement = null;
        PreparedStatement selectStatement = null;


        try {
            connection = jdbcHelper.getConnection();

            insertStatement = connection.prepareStatement(insertStatementStr);

            insertStatement.setString(1, página);
            insertStatement.setString(2, usersDeny);
            insertStatement.setString(3, usersAllow);

            insertStatement.executeUpdate();

            // Now verify if the customer is registered or not.
            selectStatement = connection.prepareStatement(selectCustomerStr);
//            selectStatement.setInt(1, id);

            ResultSet rs = selectStatement.executeQuery();

            if (rs.next()) {
                setId(rs.getInt(1));
                // El libre3 se ha registrado correctamente
                registered = true;
            } else {
                registered = false;
            }
            rs.close();
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, insertStatement);
        }
    }

    public Boolean autorizada() throws SQLException, NamingException {
        retrievePágina();
        if (registered) {
            if (usersDeny.contains("all") && !usersAllow.contains("#" + user + "#")) {
                return false;
            } else if (usersDeny.contains("#" + user + "#")) {
                return false;
            } else if (usersAllow.contains("all") || usersAllow.contains("#" + user + "#")) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public void retrievePágina() throws SQLException,
            javax.naming.NamingException {
        Connection connection = null;

        String selectCustomerStr =
                "SELECT * " +
                " FROM paginas " +
                "WHERE pag_pagina = ?";

        PreparedStatement selectStatement = null;

        try {
            connection = jdbcHelper.getConnection();

            // Now verify if the customer is registered or not.
            selectStatement = connection.prepareStatement(selectCustomerStr);
            selectStatement.setString(1, página);

            ResultSet rs = selectStatement.executeQuery();

            if (rs.next()) {
                setId(rs.getInt("pag_id"));
                setFecha(rs.getDate("pag_fecha"));
                setPágina(rs.getString("pag_pagina"));
                setUsersDeny(rs.getString("pag_usersdeny"));
                setUsersAllow(rs.getString("pag_usersallow"));

                // The customer was registered - we can go straight to the
                // response page
                registered = true;
            } else {
                registered = false;
            }
            rs.close();
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
    }

    public void deletePágina() throws SQLException,
            javax.naming.NamingException {
        Connection connection = null;

        String deleteCustomerStr =
                "DELETE FROM paginas " +
                "WHERE pag_pagina = ?";

        PreparedStatement deleteStatement = null;

        try {
            connection = jdbcHelper.getConnection();

            // Now verify if the customer is registered or not.
            deleteStatement = connection.prepareStatement(deleteCustomerStr);
            deleteStatement.setString(1, página);

            deleteStatement.executeUpdate();
            retrievePágina();
        } finally {
            jdbcHelper.cleanup(connection, deleteStatement, null);
        }
    }

    public void retrievePreviousPágina() throws SQLException,
            javax.naming.NamingException {
        Connection connection = null;

        String selectCustomerStr =
                "SELECT * " +
                " FROM paginas " +
                "WHERE pag_pagina < ? ORDER BY pag_pagina DESC LIMIT 1";

        PreparedStatement selectStatement = null;

        try {
//            connection = jdbcHelper.getConnection();

            // Now verify if the customer is registered or not.
            selectStatement = connection.prepareStatement(selectCustomerStr);
            selectStatement.setString(1, página);

            ResultSet rs = selectStatement.executeQuery();

            if (rs.next()) {
                setId(rs.getInt("pag_id"));
                setFecha(rs.getDate("pag_fecha"));
                setPágina(rs.getString("pag_pagina"));
                setUsersDeny(rs.getString("pag_usersdeny"));
                setUsersAllow(rs.getString("pag_usersallow"));

                // The customer was registered - we can go straight to the
                // response page
                registered = true;
            } else {
                registered = false;
            }
            rs.close();
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
    }

    public void retrieveNextPágina() throws SQLException,
            javax.naming.NamingException {
        Connection connection = null;

        String selectCustomerStr =
                "SELECT * " +
                " FROM paginas " +
                "WHERE pag_pagina > ? ORDER BY pag_pagina LIMIT 1";

        PreparedStatement selectStatement = null;

        try {
            connection = jdbcHelper.getConnection();

            // Now verify if the customer is registered or not.
            selectStatement = connection.prepareStatement(selectCustomerStr);
            selectStatement.setString(1, página);

            ResultSet rs = selectStatement.executeQuery();

            if (rs.next()) {
                setId(rs.getInt("pag_id"));
                setFecha(rs.getDate("pag_fecha"));
                setPágina(rs.getString("pag_pagina"));
                setUsersDeny(rs.getString("pag_usersdeny"));
                setUsersAllow(rs.getString("pag_usersallow"));

                // The customer was registered - we can go straight to the
                // response page
                registered = true;
            } else {
                registered = false;
            }
            rs.close();
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, null);
        }
    }

    public void updatePágina() throws SQLException,
            javax.naming.NamingException {

        Connection connection = null;
        String insertStatementStr =
                "UPDATE PAGINAS SET pag_fecha = ?, " +
                "pag_pagina = ?, " +
                "pag_usersdeny = ?," +
                "pag_usersallow = ? " +
                "WHERE pag_id = ?";
        String selectCustomerStr =
                "SELECT * " +
                " FROM paginas " +
                "WHERE pag_id = ?";

        PreparedStatement insertStatement = null;
        PreparedStatement selectStatement = null;

        try {
            connection = jdbcHelper.getConnection();

            insertStatement = connection.prepareStatement(insertStatementStr);

//            insertStatement.setInt(1, id);

            insertStatement.setString(1, fechatexto.substring(6, 10) + "-" +
                    fechatexto.substring(3, 5) + "-" +
                    fechatexto.substring(0, 2));
            insertStatement.setString(2, página);
            insertStatement.setString(3, usersDeny);
            insertStatement.setString(4, usersAllow);
            insertStatement.setInt(5, id);
            insertStatement.executeUpdate();

            // Now verify if the customer is registered or not.
            selectStatement = connection.prepareStatement(selectCustomerStr);
//            selectStatement.setInt(1, id);
            selectStatement.setInt(1, id);
            ResultSet rs = selectStatement.executeQuery();

            if (rs.next()) {
                setId(rs.getInt(1));
                // El libre3 se ha registrado correctamente
                registered = true;
            } else {
                registered = false;
            }
            rs.close();
        } finally {
            jdbcHelper.cleanup(connection, selectStatement, insertStatement);
        }
    }
}
