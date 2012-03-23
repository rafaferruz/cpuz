/*
 * NewsCompositionDAOImpl.java
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.cpuz.DAO.impl;

import com.cpuz.domain.NewsComposition;
import com.cpuz.exceptions.NewsCompositionException;
import com.cpuz.tools.JDBCHelper;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.naming.NamingException;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class NewsCompositionDAOImpl implements NewsCompositionDAO {

	// JNDI name of the data source this class requires
	private static final String DATA_SOURCE_NAME = "CPUZ";
	private JDBCHelper jdbcHelper;
	private PreparedStatement selectStatement = null;
	private PreparedStatement insertStatement = null;

	/** Creates a new instance of NewsCompositionDAOImpl
	 * 
	 */
	public NewsCompositionDAOImpl() throws Exception {
		jdbcHelper = new JDBCHelper(DATA_SOURCE_NAME);
	}

	public boolean keyIdExists(int ssn) throws SQLException, NamingException {
		Connection connection = null;
		boolean exists = false;
		String selectStr = "SELECT nco_composition_id FROM newscomposition WHERE nco_composition_id = " + ssn;
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
	 * Adds the NewsComposition to the NewsComposition model
	 */
	public synchronized int createNewsComposition(NewsComposition rec)
			throws NewsCompositionException, SQLException {

		String pattern = "yyyy-MM-dd hh:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat();
		// apply the pattern to the SimpleDateFormat class
		sdf.applyPattern(pattern);


		Connection connection = null;
		// Declarar variables de uso en la frase SQL
		//** crear la frase INSERT SQL
		String request= "INSERT INTO newscomposition "
				+ "(nco_composition_id, "
				+ "nco_npi_id, "
				+ "nco_component_type, "
				+ "nco_component_id, "
				+ "nco_order, "
				+ "nco_header_alternate, "
				+ "nco_header_style, "
				+ "nco_body_abstract, "
				+ "nco_image_high, "
				+ "nco_image_width, "
				+ "nco_linked_element) "
				+ " VALUES ("
				+ rec.getId() + ","
				+ rec.getIdNpi() + ","
				+ "'" + rec.getComponentType() + "',"
				+ rec.getIdComponent() + ","
				+ rec.getOrder() + ","
				+ "'" + rec.getHeaderAlt() + "',"
				+ "'" + rec.getHeaderStyle() + "',"
				+ "'" + rec.getBodyAbstract() + "',"
				+ rec.getImageHigh() + ","
				+ rec.getImageWidth() + ", "
				+ "'" + rec.getLinkedElement() + "')";
		int rowCount = 0;

		try {
			//** obtener una conexión a la BD
			connection = jdbcHelper.getConnection();
			//** obtener una variable de gestión de SQL
			insertStatement = connection.prepareStatement(request);
			//** obtener el número de registros insertados
			rowCount = insertStatement.executeUpdate();
			request= "SELECT LAST_INSERT_id() FROM newscomposition ";
			insertStatement = connection.prepareStatement(request);
			ResultSet rs = insertStatement.executeQuery();
			if (rs.next()) {
				rec.setId(rs.getInt(1));
			} else {
				rec.setId(0);
			}
		} catch (Exception e) {
			e.toString();
			throw new NewsCompositionException("NewsCompositionDAOImpl.createNewsComposition\n" + e);
		} finally {
			jdbcHelper.cleanup(connection, selectStatement, null);
		}
		return rowCount;
	}

	/**-------------------------------------------------------------
	 * deletes the NewsComposition from the NewsComposition model
	 */
	public int deleteNewsComposition(NewsComposition rec)
			throws NewsCompositionException, SQLException {
		Connection connection = null;
		int rowCount = 0;
		int keyId;
		//** 1 Assign id with id from rec object
		keyId = rec.getId();
		//** crear la frase DELETE SQL de tabla1
		String request= "DELETE FROM newscomposition WHERE nco_composition_id = " + keyId;
		try {
			//** obtener una conexión a la BD
			connection = jdbcHelper.getConnection();
			//** obtener una variable de gestión de SQL
			selectStatement = connection.prepareStatement(request);
			//** obtener el número de registros eliminados
			rowCount = selectStatement.executeUpdate(request);
			selectStatement.close();
		} catch (Exception e) {
			e.toString();
			throw new NewsCompositionException("NewsCompositionModelImpl.deleteNewsComposition\n" + e);
		} finally {
			jdbcHelper.cleanup(connection, selectStatement, null);
		}
		return rowCount;
	}

	/**-------------------------------------------------------------------
	 *
	 * @param sqlWhereClause Parte de la frase SQL para indicar la condición WHERE
	 *                       para un borrado m�ltiple de filas. debe comenzar por
	 *                       WHERE y continuar con el resto de la frase SQL
	 * @return El número de filas eliminadas
	 * @throws com.cpuz.exceptions.NewsCompositionException
	 * @throws java.sql.SQLException
	 */
	public int deleteNewsComposition(String sqlWhereClause) throws NewsCompositionException, SQLException {
		Connection connection = null;
		int rowCount = 0;
		//** crear la frase DELETE SQL de tabla1
		String request= "DELETE FROM newscomposition " + sqlWhereClause;
		try {
			//** obtener una conexión a la BD
			connection = jdbcHelper.getConnection();
			//** obtener una variable de gestión de SQL
			selectStatement = connection.prepareStatement(request);
			//** obtener el número de registros eliminados
			rowCount = selectStatement.executeUpdate(request);
			selectStatement.close();
		} catch (Exception e) {
			e.toString();
			throw new NewsCompositionException("NewsCompositionModelImpl.deleteNewsComposition\n" + e);
		} finally {
			jdbcHelper.cleanup(connection, selectStatement, null);
		}
		return rowCount;
	}

	/**-------------------------------------------------------------
	 * Updates the NewsComposition in the NewsComposition model
	 */
	public int updateNewsComposition(NewsComposition rec)
			throws NewsCompositionException, SQLException {
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
		String request= "UPDATE newscomposition SET "
				+ "nco_npi_id = " + rec.getIdNpi() + ","
				+ "nco_component_type = " + "'" + rec.getComponentType() + "',"
				+ "nco_component_id = " + rec.getIdComponent() + ","
				+ "nco_order = " + rec.getOrder() + ","
				+ "nco_header_alternate = " + "'" + rec.getHeaderAlt() + "',"
				+ "nco_header_style = " + "'" + rec.getHeaderStyle() + "',"
				+ "nco_body_abstract = " + "'" + rec.getBodyAbstract() + "',"
				+ "nco_image_high = " + rec.getImageHigh() + ","
				+ "nco_image_width = " + rec.getImageWidth() + ", "
				+ "nco_linked_element = " + "'" + rec.getLinkedElement() + "' "
				+ " WHERE nco_composition_id = " + rec.getId();

		try {
			//** obtener una conexión a la BD
			connection = jdbcHelper.getConnection();
			//** obtener una variable de gestión de SQL
			selectStatement = connection.prepareStatement(request);
			//** obtener el número de registros actualizados
			rowCount = selectStatement.executeUpdate(request);
		} catch (Exception e) {
			e.toString();
			throw new NewsCompositionException("NewsCompositionModelImpl.updateNewsCompositionInfo\n" + e);
		} finally {
			jdbcHelper.cleanup(connection, selectStatement, null);
		}
		return rowCount;
	}
	// NewsComposition segment state query methods

	/**-------------------------------------------------------------
	 * Given an ssn, returns the NewsComposition from the model
	 */
	public NewsComposition readNewsComposition(String keyId)
			throws NewsCompositionException, SQLException, NamingException {
		// Declarar variables de uso en la frase SQL
		Connection connection = null;
		//** crear la frase SELECT SQL
		String request= "SELECT * FROM newscomposition WHERE nco_composition_id = " + keyId;
		try {
			//** obtener una conexión a la BD
			connection = jdbcHelper.getConnection();
			selectStatement = connection.prepareStatement(request);
			NewsComposition cr = null;
			try (ResultSet result = selectStatement.executeQuery(request)) {
				if (result.next()) {
					cr = this.getNewsCompositionFromResultSet(result);
				} else {
					// if query failed
					throw new NewsCompositionException("NewsComposition for " + keyId + " not found.");
				}
			}
			return cr;
		} catch (SQLException e) {
			e.toString();
			throw new NewsCompositionException("NewsCompositionModelImpl.readNewsComposition\n" + e);
		} finally {
			jdbcHelper.cleanup(connection, selectStatement, null);
		}
	}

	/**-------------------------------------------------------------
	 * Returns all NewsCompositions in the NewsComposition model
	 */
	public List<NewsComposition> readNewsCompositions()
			throws NewsCompositionException, SQLException, NamingException {

		return this.readNewsCompositions("");
	}

	public List<NewsComposition> readNewsCompositions(String sqlClause) throws NamingException, NewsCompositionException, SQLException {
		Connection connection = null;
		//** crear la frase SELECT SQL
		List<NewsComposition> aList = new ArrayList<>();
		String request= "";
		if (sqlClause == null) {
			request= "SELECT * FROM newscomposition ";
		} else {
			request= sqlClause;
		}


		try {
			//** obtener una conexión a la BD
			connection = jdbcHelper.getConnection();
			selectStatement = connection.prepareStatement(request);
			try (ResultSet result = selectStatement.executeQuery(request)) {
				while (result.next()) {
					aList.add(this.getNewsCompositionFromResultSet(result));
				}
			}
		} catch (SQLException e) {
			e.toString();
			throw new NewsCompositionException("NewsCompositionDAOImpl.readNewsComposition\n" + e);
		} finally {
			jdbcHelper.cleanup(connection, selectStatement, null);
		}
		return aList;
	}

	public List<NewsComposition> readCompositionItems(int npiId) throws NamingException, NewsCompositionException, SQLException {
		Connection connection = null;
		//** crear la frase SELECT SQL
		List<NewsComposition> aList = new ArrayList<>();
		String sqlFilter = "";
		if (npiId > 0) {
			sqlFilter = " WHERE nco_npi_id = npiId ";
		}
		String sqlClause = "SELECT * FROM newscomposition " + sqlFilter
				+ " ORDER BY nco_composition_id DESC, nco_order ";


		try {
			//** obtener una conexión a la BD
			connection = jdbcHelper.getConnection();
			selectStatement = connection.prepareStatement(sqlClause);
			try (ResultSet result = selectStatement.executeQuery()) {
				while (result.next()) {
					// Agregar el objeto NewsComposition a la lista de objetos NewsComposition
					aList.add(this.getNewsCompositionFromResultSet(result));
				}
			}
		} catch (SQLException e) {
			e.toString();
			throw new NewsCompositionException("NewsCompositionDAOImpl.readNewsComposition\n" + e);
		} finally {
			jdbcHelper.cleanup(connection, selectStatement, null);
		}
		return aList;
	}

	public static NewsComposition getNewsCompositionFromResultSet(ResultSet rs) throws NewsCompositionException {
		NewsComposition rec = new NewsComposition();
		try {
			rec.setId(rs.getInt("nco_composition_id"));
			rec.setIdNpi(rs.getInt("nco_npi_id"));
			rec.setComponentType(rs.getString("nco_component_type"));
			rec.setIdComponent(rs.getInt("nco_component_id"));
			rec.setOrder(rs.getInt("nco_order"));
			rec.setHeaderAlt(rs.getString("nco_header_alternate"));
			rec.setHeaderStyle(rs.getString("nco_header_style"));
			rec.setBodyAbstract(rs.getString("nco_body_abstract"));
			rec.setImageHigh(rs.getInt("nco_image_high"));
			rec.setImageWidth(rs.getInt("nco_image_width"));
			rec.setLinkedElement(rs.getString("nco_linked_element"));
		} catch (SQLException e) {
			throw new NewsCompositionException("NewsCompositionDAOImpl.setPropertiesNewsComposition\n" + e);
		}
		return rec;
	}
}
