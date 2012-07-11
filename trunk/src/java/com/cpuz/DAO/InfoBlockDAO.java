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
 * You should have received a copy of the GNU General Public License
 * along with CPUZ.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.cpuz.DAO;

import com.cpuz.domain.InfoBlock;
import com.cpuz.st2.beans.ControlParams;
import com.cpuz.util.SqlUtil;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

/**
 * Clase para la ejecución de operaciones CRUD sobre la tabla 'infoblocks'.
 */
public class InfoBlockDAO implements InjectableDAO {

	transient private final Logger log = Logger.getLogger(this.getClass());
	private Connection conn;

	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	/**
	 * Añade el InfoBlock a la tabla 
	 *
	 * @param rec		Objeto InfoBlock que se quiere insertar en la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha insertado con éxito.
	 * @throws SQLException 
	 */
	public synchronized int create(InfoBlock rec) throws SQLException {

		String sql = "INSERT INTO infoblocks "
				+ "(inb_date, "
				+ "inb_status, "
				+ "inb_user, "
				+ "inb_type, "
				+ "inb_header, "
				+ "inb_body, "
				+ "inb_scope) "
				+ " VALUES ( ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setDate(1, new java.sql.Date(rec.getDatetime().getTime()));
		ps.setInt(2, rec.getStatus());
		ps.setString(3, rec.getUser());
		ps.setString(4, rec.getType());
		ps.setString(5, rec.getHeader());
		ps.setString(6, rec.getBody());
		ps.setInt(7, rec.getScope());
		log.debug("InfoBlockDAO create(): " + ps.toString());
		return ps.executeUpdate();
	}

	/**
	 * Recupera un InfoBlock de la tabla 
	 *
	 * @param infoId		Id del objeto InfoBlock que se quiere recuperar de la tabla
	 * @return			Un objeto InfoBlock  con la información recuperada de la 
	 *					base de datos. Si no encuentra el id buscado, devuelve null.
	 * @throws SQLException 
	 */
	public InfoBlock read(int infoId) throws SQLException {
		InfoBlock infoBlock = null;
		String sql = "SELECT * FROM infoblocks WHERE inb_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, infoId);
		log.debug("InfoBlockDAO read(): " + ps.toString());
		ResultSet rs = ps.executeQuery(sql);
		if (rs.next()) {
			infoBlock = getCompleteInfoBlock(rs);
		}
		return infoBlock;
	}

	/**
	 * Actualiza un InfoBlock en la tabla 
	 *
	 * @param infoBlock		Objeto InfoBlock que se quiere actualizar en la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha insertado con éxito.
	 * @throws SQLException 
	 */
	public int update(InfoBlock infoBlock) throws SQLException {
		int rowCount = 0;
		String sql = "UPDATE infoblocks SET "
				+ "inb_status = ?,"
				+ "inb_type = ?,"
				+ "inb_header = ?,"
				+ "inb_body = ?,"
				+ "inb_scope = ? "
				+ " WHERE inb_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, infoBlock.getStatus());
		ps.setString(2, infoBlock.getType());
		ps.setString(3, infoBlock.getHeader());
		ps.setString(4, infoBlock.getBody());
		ps.setInt(5, infoBlock.getScope());
		ps.setInt(6, infoBlock.getId());
		log.debug("InfoBlockDAO update(): " + ps.toString());
		rowCount = ps.executeUpdate();
		return rowCount;
	}

	/**
	 * Elimina un InfoBlock de la tabla 
	 *
	 * @param secId		Id del objeto InfoBlock que se quiere eliminar de la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha eliminado con éxito.
	 * @throws SQLException 
	 */
	public int delete(Integer infoId) throws SQLException {
		int rowCount = 0;
		String sql = "DELETE FROM infoblocks WHERE inb_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, infoId);
		log.debug("InfoBlockDAO delete(): " + ps.toString());
		rowCount = ps.executeUpdate();
		return rowCount;
	}

	public List<InfoBlock> getInfoBlockList(ControlParams control) throws SQLException {
		List<InfoBlock> infoBlocks = new ArrayList<>();
		String sql = "SELECT * FROM infoblocks ORDER BY inb_id ";
		String limit = control.getRecChunk() > 0 ? " LIMIT ? OFFSET ?" : "";
		sql = sql + limit;
		PreparedStatement ps = conn.prepareStatement(sql);
		if (!limit.isEmpty()) {
			ps.setInt(1, control.getRecChunk());
			ps.setInt(2, control.getRecStart());
		}
		log.debug("InfoBlockDAO getInfoBlockList(): " + ps.toString());
		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				infoBlocks.add(getCompleteInfoBlock(rs));
			}
		}
		return infoBlocks;
	}

	public int deleteIds(List<Integer> ids) throws SQLException {
		String sql = "DELETE FROM infoblocks WHERE inb_id IN "
				+ SqlUtil.getPreparedStatementInClause(ids);
		PreparedStatement ps = conn.prepareStatement(sql);
		SqlUtil.setList(ps, ids);
		log.debug("InfoBlockDAO deleteIds(): " + ps.toString());
		return ps.executeUpdate();
	}

	/**
	 * Devuelve el número de registros totales de la tabla
	 * 
	 * @return variable int con el número de registros en la tabla
	 */
	public int getCountRows() throws SQLException {
		String sql = "SELECT COUNT(*) FROM infoblocks";
		PreparedStatement ps = conn.prepareStatement(sql);
		log.debug("InfoBlockDAO getCountRows(): " + ps.toString());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			return rs.getInt(1);
		}
		return 0;
	}


	public InfoBlock getCompleteInfoBlock(ResultSet rs) throws SQLException {
		InfoBlock infoBlock = new InfoBlock();
		infoBlock.setId(rs.getInt("inb_id"));
		infoBlock.setDatetime(rs.getTimestamp("inb_date"));
		infoBlock.setStatus(rs.getInt("inb_status"));
		infoBlock.setUser(rs.getString("inb_user"));
		infoBlock.setType(rs.getString("inb_type"));
		infoBlock.setHeader(rs.getString("inb_header"));
		infoBlock.setBody(rs.getString("inb_body"));
		infoBlock.setScope(rs.getInt("inb_scope"));
		return infoBlock;
	}
}
