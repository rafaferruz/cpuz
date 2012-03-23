/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.misc;

import com.cpuz.tools.JDBCHelper;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 * @author RAFA
 */
public class ConfigurationBean {

	// JNDI name of the data source this class requires
	private static final String DATA_SOURCE_NAME = "CPUZ";
	private Integer id;
	private Integer accesses = 0;
	private boolean registered = false;
	private JDBCHelper jdbcHelper;
	private Integer paso;

	public ConfigurationBean() {
		jdbcHelper = new JDBCHelper(DATA_SOURCE_NAME);
	}

	public void setId(Integer value) {
		this.id = value;
	}

	public Integer getId() {
		return id;
	}

	public void setAccesses(Integer value) {
		this.accesses = value;
	}

	public Integer getAccesses() {
		return accesses;
	}

	public Integer getPaso() {
		return paso;
	}

	public Integer incrementAccesses(int pasos) {
		try {
			loadConfiguration();
			accesses = accesses + pasos;
			saveOrUpdateConfiguration();
		} catch (SQLException ex) {
			Logger.getLogger(ConfigurationBean.class.getName()).log(Level.SEVERE, null, ex);
		} catch (NamingException ex) {
			Logger.getLogger(ConfigurationBean.class.getName()).log(Level.SEVERE, null, ex);
		}
		return accesses;
	}

	public boolean isRegistered() {
		return registered;
	}

	private void saveOrUpdateConfiguration() throws SQLException, javax.naming.NamingException {
		Connection connection = null;
		String insertStatementStr = "INSERT INTO CONFIG VALUES(?, ?) "
				+ "ON DUPLICATE KEY UPDATE cfg_accesses = ?";
		PreparedStatement insertStatement = null;
		try {
			connection = jdbcHelper.getConnection();
			insertStatement = connection.prepareStatement(insertStatementStr);
			insertStatement.setInt(1, ConfigType.CONFIG.getId());
			insertStatement.setInt(2, accesses);
			insertStatement.setInt(3, accesses);
			if (insertStatement.executeUpdate() > 0) {
				// El Configuracion se ha registrado correctamente
				registered = true;
			} else {
				registered = false;
			}
		} finally {
			jdbcHelper.cleanup(connection, insertStatement, null);
		}
	}

	private void loadConfiguration() throws SQLException, javax.naming.NamingException {
		Connection connection = null;
		String selectConfiguracionStr = "SELECT * FROM config WHERE cfg_id = ?";
		PreparedStatement selectStatement = null;
		try {
			connection = jdbcHelper.getConnection();
			selectStatement = connection.prepareStatement(selectConfiguracionStr);
			selectStatement.setInt(1, ConfigType.CONFIG.getId());
			ResultSet rs = selectStatement.executeQuery();
			if (rs.next()) {
				setId(rs.getInt("cfg_id"));
				setAccesses(rs.getInt("cfg_accesses"));
				// The Configuracion was registered - we can go straight to the
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
}

