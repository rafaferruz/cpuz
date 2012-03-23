/*
 * NewsPieceDAOImpl.java
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.cpuz.DAO.impl;

import com.cpuz.domain.NewsComposition;
import com.cpuz.domain.NewsPiece;
import com.cpuz.exceptions.NewsCompositionException;
import com.cpuz.exceptions.NewsPieceException;
import com.cpuz.tools.JDBCHelper;
import com.cpuz.util.SqlUtil;
import java.sql.*;
import java.util.*;
import javax.naming.NamingException;
import org.apache.log4j.Logger;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class NewsPieceDAOImpl implements NewsPieceDAO, InjectableDAO {

	transient private final Logger log = Logger.getLogger(this.getClass());
	// JNDI name of the data source this class requires
	private static final String DATA_SOURCE_NAME = "CPUZ";
	private JDBCHelper jdbcHelper;
	private PreparedStatement selectStatement = null;
	private PreparedStatement insertStatement = null;
	private Connection conn;

	public NewsPieceDAOImpl() throws Exception {
		jdbcHelper = new JDBCHelper(DATA_SOURCE_NAME);
	}

	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	public boolean keyIdExists(int ssn) throws SQLException, NamingException {
		Connection connection = null;
		boolean exists = false;
		String selectStr = "SELECT npi_id FROM newspieces WHERE npi_id = " + ssn;
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
	 * Adds the NewsPiece to the NewsPiece model
	 */
	public synchronized int createNewsPiece(NewsPiece rec)
			throws NewsPieceException, SQLException {

		Connection connection = null;
		// Declarar variables de uso en la frase SQL
		//** crear la frase INSERT SQL
		String request = "INSERT INTO newspieces SET "
				+ "npi_id = ?, "
				+ "npi_date = ?, "
				+ "npi_status = ?, "
				+ "npi_user = ?, "
				+ "npi_section = ?, "
				+ "npi_description = ?, "
				+ "npi_show_parameters = ?, "
				+ "npi_scope = ?, "
				+ "npi_access = ? ";
		int rowCount = 0;
		try {
			//** obtener una conexión a la BD
			connection = jdbcHelper.getConnection();
			//** obtener una variable de gestáón de SQL
			insertStatement = connection.prepareStatement(request);
			insertStatement.setInt(1, rec.getId());
			insertStatement.setTimestamp(2, new Timestamp(rec.getDatetime().getTime()));
			insertStatement.setInt(3, rec.getStatus());
			insertStatement.setString(4, rec.getUser());
			insertStatement.setString(5, rec.getDescription());
			insertStatement.setString(6, rec.getShowParameters());
			insertStatement.setInt(7, rec.getScope());
			insertStatement.setInt(8, rec.getAccess());

			//** obtener el número de registros insertados
			rowCount = insertStatement.executeUpdate();
			request = "SELECT LAST_INSERT_id() FROM newspieces ";
			insertStatement = connection.prepareStatement(request);
			ResultSet rs = insertStatement.executeQuery();
			if (rs.next()) {
				rec.setId(rs.getInt(1));
			} else {
				rec.setId(0);
			}
		} catch (Exception e) {
			e.toString();
			throw new NewsPieceException("NewsPieceDAOImpl.createNewsPiece\n" + e);
		} finally {
			jdbcHelper.cleanup(connection, selectStatement, null);
		}
		return rowCount;
	}

	/**-------------------------------------------------------------
	 * deletes the NewsPiece from the NewsPiece model
	 */
	public int deleteNewsPiece(NewsPiece rec)
			throws NewsPieceException, SQLException {
		Connection connection = null;
		int rowCount = 0;
		int keyId;
		//** 1 Assign id with id from rec object
		keyId = rec.getId();
		//** crear la frase DELETE SQL de tabla1
		String request = "DELETE FROM newspieces WHERE npi_id = " + keyId;
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
			throw new NewsPieceException("NewsPieceModelImpl.deleteNewsPiece\n" + e);
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
	 * @throws com.cpuz.exceptions.NewsPieceException
	 * @throws java.sql.SQLException
	 */
	public int deleteNewsPiece(String sqlWhereClause) throws NewsPieceException, SQLException {
		Connection connection = null;
		int rowCount = 0;
		//** crear la frase DELETE SQL de tabla1
		String request = "DELETE FROM newspieces " + sqlWhereClause;
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
			throw new NewsPieceException("NewsPieceModelImpl.deleteNewsPiece\n" + e);
		} finally {
			jdbcHelper.cleanup(connection, selectStatement, null);
		}
		return rowCount;
	}

	/**-------------------------------------------------------------
	 * Updates the NewsPiece in the NewsPiece model
	 */
	public int updateNewsPiece(NewsPiece rec)
			throws NewsPieceException, SQLException {
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
		String request = "UPDATE newspieces SET "
				+ "npi_status = " + rec.getStatus() + ","
				+ "npi_section = " + "'" + rec.getSection() + "',"
				+ "npi_description = " + "'" + rec.getDescription() + "',"
				+ "npi_show_parameters = " + "'" + rec.getShowParameters() + "',"
				+ "npi_scope = " + rec.getScope() + ","
				+ "npi_access = " + rec.getAccess()
				+ " WHERE npi_id = " + rec.getId();

		try {
			//** obtener una conexión a la BD
			connection = jdbcHelper.getConnection();
			//** obtener una variable de gestáón de SQL
			selectStatement = connection.prepareStatement(request);
			//** obtener el número de registros actualizados
			rowCount = selectStatement.executeUpdate(request);
		} catch (Exception e) {
			e.toString();
			throw new NewsPieceException("NewsPieceModelImpl.updateNewsPieceInfo\n" + e);
		} finally {
			jdbcHelper.cleanup(connection, selectStatement, null);
		}
		return rowCount;
	}
	// NewsPiece segment state query methods

	/**-------------------------------------------------------------
	 * Given an ssn, returns the NewsPiece from the model
	 */
	public NewsPiece readNewsPiece(String keyId)
			throws NewsPieceException, SQLException, NamingException {
		// Declarar variables de uso en la frase SQL
		Connection connection = null;
		//** crear la frase SELECT SQL
		String request = "SELECT * FROM newspieces WHERE npi_id = " + keyId;
		try {
			//** obtener una conexión a la BD
			connection = jdbcHelper.getConnection();
			ResultSet result = null;
			NewsPiece cr = null;
			//** obtener una variable de gestáón de SQL
			selectStatement = connection.prepareStatement(request);
			//** obtener el resultset de registros obtenidos
			result = selectStatement.executeQuery(request);
			// The following selectStatement checks if query successful
			if (result.next()) {
				cr = this.getNewsPieceFromResultSet(result); //m�todo ayudante
			} else {
				throw new NewsPieceException("NewsPiece for " + keyId + " not found.");
			}
			result.close();
			return cr;
		} catch (SQLException e) {
			e.toString();
			throw new NewsPieceException("NewsPieceModelImpl.readNewsPiece\n" + e);
		} finally {
			jdbcHelper.cleanup(connection, selectStatement, null);
		}
	}

	/**-------------------------------------------------------------
	 * Returns all NewsPieces in the NewsPiece model
	 */
	public List<NewsPiece> readNewsPieces()
			throws NewsPieceException, SQLException, NamingException {

		return this.readNewsPieces("");
	}

	public List<NewsPiece> readNewsPieces(String sqlClause) throws NewsPieceException, SQLException, NamingException {
		Connection connection = null;
		//** crear la frase SELECT SQL
		List<NewsPiece> aList = new ArrayList<>();
		String request = "";
		if (sqlClause == null) {
			request = "SELECT * FROM newspieces ";
		} else {
			request = sqlClause;
		}


		try {
			//** obtener una conexión a la BD
			connection = jdbcHelper.getConnection();
			ResultSet result = null;
			NewsPiece cr = null;
//            connection = obtainConnection();
			//** obtener una variable de gestáón de SQL
			selectStatement = connection.prepareStatement(request);

			//** obtener el resultset de registros obtenidos
			result = selectStatement.executeQuery(request);
			while (result.next()) {
				//A* Asignar valores de campos a un objeto NewsPiece
				aList.add(this.getNewsPieceFromResultSet(result)); //m�todo ayudante
			}
			result.close();
		} catch (SQLException e) {
			e.toString();
			throw new NewsPieceException("NewsPieceDAOImpl.readAllNewsPiece\n" + e);
		} finally {
			jdbcHelper.cleanup(connection, selectStatement, null);
		}
		return aList;
	}

	public List<NewsPiece> readNewsPieces(List<Integer> statusList, List<Integer> scopeList,
			java.util.Date fromDate, java.util.Date toDate) throws NewsPieceException, SQLException, NamingException {
		Connection connection = null;
		//** crear la frase SELECT SQL
		List<NewsPiece> aList = new ArrayList<>();
		String sqlClause = "SELECT * FROM newspieces "
				+ " WHERE "
				+ " npi_status IN " + SqlUtil.getIntegersStatementInClause(statusList)
				+ " AND "
				+ " npi_scope IN " + SqlUtil.getIntegersStatementInClause(scopeList)
				+ " ORDER BY npi_date DESC, npi_id DESC";
		try {
			//** obtener una conexión a la BD
			connection = jdbcHelper.getConnection();
			ResultSet result = null;
			//** obtener una variable de gestáón de SQL
			selectStatement = connection.prepareStatement(sqlClause);
			//** obtener el resultset de registros obtenidos
			result = selectStatement.executeQuery();
			while (result.next()) {
				aList.add(this.getNewsPieceFromResultSet(result)); //m�todo ayudante
			}
			result.close();
		} catch (SQLException e) {
			e.toString();
			throw new NewsPieceException("NewsPieceDAOImpl.readAllNewsPiece\n" + e);
		} finally {
			jdbcHelper.cleanup(connection, selectStatement, null);
		}
		return aList;
	}

	public static NewsPiece getNewsPieceFromResultSet(ResultSet rs) throws NewsPieceException {
		NewsPiece rec = new NewsPiece();
		try {
			rec.setId(rs.getInt("npi_id"));
			rec.setDatetime(rs.getTimestamp("npi_date"));
			rec.setStatus(rs.getInt("npi_status"));
			rec.setUser(rs.getString("npi_user"));
			rec.setSection(rs.getString("npi_section"));
			rec.setDescription(rs.getString("npi_description"));
			rec.setShowParameters(rs.getString("npi_show_parameters"));
			rec.setScope(rs.getInt("npi_scope"));
			rec.setAccess(rs.getInt("npi_access"));
		} catch (SQLException e) {
			throw new NewsPieceException("NewsPieceDAOImpl.setPropertiesNewsPiece\n" + e);
		}
		return rec;
	}

	public List<NewsPiece> getCompleteNewsPieces() throws SQLException, NewsPieceException, NewsCompositionException {
		List<NewsPiece> newsPieces = new ArrayList<>();
		String sql = "SELECT * FROM newspieces "
				+ "LEFT JOIN newscomposition ON nco_npi_id = npi_id, "
				+ "sections AS sec "
				+ "WHERE npi_status = 2 AND npi_scope >= 0 "
				+ "AND TO_DAYS(npi_date) > (TO_DAYS(CURDATE()) - 1000) "
				+ "AND npi_section = sec_id "
				+ "ORDER BY npi_date DESC, nco_order";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		log.debug("loadById: " + ps.toString());
		NewsPiece newsPiece = null;
		while (rs.next()) {
			if (newsPiece == null) {
				newsPiece = getNewsPieceFromResultSet(rs);
			} else if (rs.getInt("npi_id") != newsPiece.getId()) {
				newsPieces.add(newsPiece);
				newsPiece = getNewsPieceFromResultSet(rs);
			}
			newsPiece.getNewsCompositionList().add(NewsCompositionDAOImpl.getNewsCompositionFromResultSet(rs));
		}
		rs.close();
		ps.close();
		return newsPieces;


	}
}
