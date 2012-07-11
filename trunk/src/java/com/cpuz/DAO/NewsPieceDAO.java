/*
 * Copyright 2012 Rafael Ferruz
 * 
 * This file is part of CPUZ.
 * 
 * CPUZ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * CPUZ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have newsPieceeived a copy of the GNU General Public License
 * along with CPUZ.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.cpuz.DAO;

import com.cpuz.DAO.impl.NewsCompositionDAOImpl;
import com.cpuz.domain.NewsPiece;
import com.cpuz.exceptions.UserException;
import com.cpuz.st2.beans.ControlParams;
import com.cpuz.util.SqlUtil;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class NewsPieceDAO implements InjectableDAO {

	transient private final Logger log = Logger.getLogger(this.getClass());
	private Connection conn;

	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	/**
	 * Añade el NewsPiece a la tabla 
	 *
	 * @param newsPiece		Objeto NewsPiece que se quiere insertar en la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha insertado con éxito.
	 * @throws SQLException 
	 */
	public synchronized int create(NewsPiece newsPiece) throws SQLException, UserException {
		if (newsPiece == null || newsPiece.getId() == 0 || newsPiece.getDescription() == null
				|| newsPiece.getDescription().equals("")) {
			throw new UserException("sectionException.nullOrEmptyField");
		}

		String sql = "INSERT INTO newspieces SET "
				+ "npi_date = ?, "
				+ "npi_status = ?, "
				+ "npi_user = ?, "
				+ "npi_section_id = ?, "
				+ "npi_description = ?, "
				+ "npi_show_parameters = ?, "
				+ "npi_scope = ?, "
				+ "npi_access = ? ";

		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setTimestamp(1, new Timestamp(newsPiece.getDatetime().getTime()));
		ps.setInt(2, newsPiece.getStatus());
		ps.setString(3, newsPiece.getUser());
		ps.setInt(4, newsPiece.getSectionId());
		ps.setString(5, newsPiece.getDescription());
		ps.setString(6, newsPiece.getShowParameters());
		ps.setInt(7, newsPiece.getScope());
		ps.setInt(8, newsPiece.getAccess());
		log.debug("NewsPieceDAO create(): " + ps.toString());
		return ps.executeUpdate();
	}

	/**
	 * Recupera un NewsPiece de la tabla 
	 *
	 * @param newsPieceId		Id del objeto NewsPiece que se quiere newsPieceuperar de la tabla
	 * @return			Un objeto NewsPiece  con la información newsPieceuperada de la 
	 *					base de datos. Si no encuentra el id buscado, devuelve null.
	 * @throws SQLException 
	 */
	public NewsPiece read(int newsPieceId) throws SQLException {
		NewsPiece newsPiece = null;
		String sql = "SELECT * FROM newsPieces WHERE npi_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, newsPieceId);
		log.debug("NewsPieceDAO read(): " + ps.toString());
		ResultSet rs = ps.executeQuery(sql);
		if (rs.next()) {
			newsPiece = getCompleteNewsPiece(rs);
		}
		return newsPiece;
	}

	/**
	 * Actualiza un NewsPiece en la tabla 
	 *
	 * @param newsPiece		Objeto NewsPiece que se quiere actualizar en la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha insertado con éxito.
	 * @throws SQLException 
	 */
	public int update(NewsPiece newsPiece) throws SQLException, UserException {
		if (newsPiece == null || newsPiece.getId() == 0 || newsPiece.getDescription() == null
				|| newsPiece.getDescription().equals("")) {
			throw new UserException("sectionException.nullOrEmptyField");
		}
		int rowCount = 0;
		String sql = "UPDATE newsPieces SET "
				+ "npi_status = ?,"
				+ "npi_section_id = ?,"
				+ "npi_description = ?,"
				+ "npi_show_parameters = ?,"
				+ "npi_scope = ?,"
				+ "npi_access = ?"
				+ " WHERE npi_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, newsPiece.getStatus());
		ps.setInt(2, newsPiece.getSectionId());
		ps.setString(3, newsPiece.getDescription());
		ps.setString(4, newsPiece.getShowParameters());
		ps.setInt(5, newsPiece.getScope());
		ps.setInt(6, newsPiece.getAccess());
		ps.setInt(7, newsPiece.getId());
		log.debug("NewsPieceDAO update(): " + ps.toString());
		rowCount = ps.executeUpdate();
		return rowCount;
	}

	/**
	 * Elimina un NewsPiece de la tabla 
	 *
	 * @param secId		Id del objeto NewsPiece que se quiere eliminar de la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha eliminado con éxito.
	 * @throws SQLException 
	 */
	public int delete(int newsPieceId) throws SQLException {
		int rowCount = 0;
		String sql = "DELETE FROM newsPieces WHERE npi_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, newsPieceId);
		log.debug("NewsPieceDAO delete(): " + ps.toString());
		rowCount = ps.executeUpdate();
		return rowCount;
	}

	public List<NewsPiece> getNewsPieceList(ControlParams control) throws SQLException, UserException {
		List<NewsPiece> newsPieces = new ArrayList<>();
		String sql = "SELECT * FROM newsPieces ORDER BY npi_id ";
		String limit = control.getRecChunk() > 0 ? " LIMIT ? OFFSET ?" : "";
		sql = sql + limit;
		PreparedStatement ps = conn.prepareStatement(sql);
		if (!limit.isEmpty()) {
			ps.setInt(1, control.getRecChunk());
			ps.setInt(2, control.getRecStart());
		}
		log.debug("NewsPieceDAO getNewsPieceList(): " + ps.toString());
		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				newsPieces.add(getCompleteNewsPiece(rs));
			}
		}
		return getNewsPiecesWhitCompositions(newsPieces);
	}

	public int deleteIds(List<Integer> ids) throws SQLException {
		String sql = "DELETE FROM newsPieces WHERE npi_id IN "
				+ SqlUtil.getPreparedStatementInClause(ids);
		PreparedStatement ps = conn.prepareStatement(sql);
		SqlUtil.setList(ps, ids);
		log.debug("NewsPieceDAO deleteIds(): " + ps.toString());
		return ps.executeUpdate();
	}

	/**
	 * Devuelve el número de registros totales de la tabla
	 * 
	 * @return variable int con el número de registros en la tabla
	 */
	public int getCountRows() throws SQLException {
		String sql = "SELECT COUNT(*) FROM newsPieces";
		PreparedStatement ps = conn.prepareStatement(sql);
		log.debug("NewsPieceDAO getCountRows(): " + ps.toString());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			return rs.getInt(1);
		}
		return 0;
	}

	protected static NewsPiece getCompleteNewsPiece(ResultSet rs) throws SQLException {
		NewsPiece newsPiece = new NewsPiece();
		newsPiece.setId(rs.getInt("npi_id"));
		newsPiece.setDatetime(rs.getTimestamp("npi_date"));
		newsPiece.setStatus(rs.getInt("npi_status"));
		newsPiece.setUser(rs.getString("npi_user"));
		newsPiece.setSectionId(rs.getInt("npi_section_id"));
		newsPiece.setDescription(rs.getString("npi_description"));
		newsPiece.setShowParameters(rs.getString("npi_show_parameters"));
		newsPiece.setScope(rs.getInt("npi_scope"));
		newsPiece.setAccess(rs.getInt("npi_access"));
		return newsPiece;
	}

	public List<NewsPiece> getCompleteNewsPieces() throws SQLException, UserException {
		List<NewsPiece> newsPieces = new ArrayList<>();
		String sql = "SELECT * FROM newspieces "
				+ "LEFT JOIN newscomposition ON nco_npi_id = npi_id, "
				+ "sections AS sec "
				+ "WHERE npi_status = 2 AND npi_scope >= 0 "
				+ "AND TO_DAYS(npi_date) > (TO_DAYS(CURDATE()) - 1000) "
				+ "AND npi_section_id = sec_id "
				+ "ORDER BY npi_date DESC, nco_order";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		log.debug("getCompleteNewsPieces: " + ps.toString());
		NewsPiece newsPiece = null;
		while (rs.next()) {
			if (newsPiece == null) {
				newsPiece = getCompleteNewsPiece(rs);
			} else if (rs.getInt("npi_id") != newsPiece.getId()) {
				newsPieces.add(newsPiece);
				newsPiece = getCompleteNewsPiece(rs);
			}
			newsPiece.getNewsCompositionList().add(NewsCompositionDAOImpl.getNewsCompositionFromResultSet(rs));
		}
		rs.close();
		ps.close();
		return newsPieces;


	}

	private List<NewsPiece> getNewsPiecesWhitCompositions(List<NewsPiece> newsPieces) throws SQLException, UserException {
		List<Integer> newsPieceIds = new ArrayList<>();
		for (NewsPiece newsPiece : newsPieces) {
			newsPiece.getNewsCompositionList().clear(); // Se limpian la listas antes de volver a completarlas
			newsPieceIds.add(newsPiece.getId());
		}
		if (!newsPieceIds.isEmpty()) {
			String sql = "SELECT * FROM newscomposition AS nco "
					+ "WHERE nco.npi_id " + SqlUtil.getPreparedStatementInClause(newsPieceIds)
					+ "ORDER BY nco.npi_id, nco_order";
			PreparedStatement ps = conn.prepareStatement(sql);
			SqlUtil.setList(ps, newsPieceIds);
			ResultSet rs = ps.executeQuery();
			log.debug("getCompleteNewsPieces: " + ps.toString());
			while (rs.next()) {
				NewsPiece newsPiece = new NewsPiece();
				newsPiece.setId(rs.getInt("nco.npi_id"));
				if (newsPieces.indexOf(newsPiece) >= 0) {
					newsPieces.get(newsPieces.indexOf(newsPiece)).getNewsCompositionList()
							.add(NewsCompositionDAOImpl.getNewsCompositionFromResultSet(rs));
				}
			}
			rs.close();
			ps.close();
		}
		return newsPieces;

	}
}
