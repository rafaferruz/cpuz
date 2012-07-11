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

import com.cpuz.domain.Bug;
import com.cpuz.st2.beans.ControlParams;
import com.cpuz.util.SqlUtil;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.log4j.Logger;

/**
 * Clase para la ejecución de operaciones CRUD sobre la tabla 'bugs'.
 *
 */
public class BugDAO implements InjectableDAO {

	transient private final Logger log = Logger.getLogger(this.getClass());
	private Connection conn;

	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	/**
	 * Añade el Bug a la tabla 
	 *
	 * @param Bug		Objeto Bug que se quiere insertar en la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha insertado con éxito.
	 * @throws SQLException 
	 */
	public synchronized int create(Bug rec) throws SQLException {

		String sql = "INSERT INTO bugs "
				+ "(bug_date, bug_status, bug_user, bug_priority, bug_type, "
				+ "bug_application, bug_header, bug_body) "
				+ " VALUES ("
				+ "'" + (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(rec.getDatetime())) + "',"
				+ rec.getStatus() + ","
				+ "'" + rec.getUser() + "',"
				+ rec.getPriority() + ","
				+ "'" + rec.getType() + "',"
				+ "'" + rec.getApplication() + "',"
				+ "'" + rec.getHeader() + "',"
				+ "'" + rec.getBody() + "')";
		int rowCount = 0;

		PreparedStatement ps = conn.prepareStatement(sql);
		rowCount = ps.executeUpdate();
		sql = "SELECT LAST_INSERT_id() FROM bugs ";
		ps = conn.prepareStatement(sql);
		log.debug("BugDAO create(): " + ps.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			rec.setId(rs.getInt(1));
		} else {
			rec.setId(0);
		}
		return rowCount;
	}

	/**
	 * Recupera un BugRol de la tabla 
	 *
	 * @param bugId		Id del objeto Bug que se quiere recuperar de la tabla
	 * @return			Un un objeto bug con la información recuperada de la 
	 *					base de datos. Si no encuentra el id buscado, devuelve null.
	 * @throws SQLException 
	 */
	public Bug read(int bugId) throws SQLException {
		Bug bug = null;
		String sql = "SELECT * FROM bugs WHERE bug_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, bugId);
		log.debug("BugDAO read(): " + ps.toString());
		ResultSet rs = ps.executeQuery(sql);
		if (rs.next()) {
			bug = getCompleteBug(rs);
		} 
		return bug;
	}

	/**
	 * Actualiza un Bug en la tabla 
	 *
	 * @param bug		Objeto Bug que se quiere actualizar en la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha insertado con éxito.
	 * @throws SQLException 
	 */
	public int update(Bug bug) throws SQLException {
		int rowCount = 0;
		String sql = "UPDATE bugs SET "
				+ "bug_date = ?, bug_status = ?, bug_user = ?, "
				+ "bug_priority = ?, bug_type = ?, "
				+ "bug_application = ?, bug_header = ?, bug_body = ? "
				+ " WHERE bug_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setDate(1, new java.sql.Date(bug.getDatetime().getTime()));
		ps.setInt(2, bug.getStatus());
		ps.setString(3, bug.getUser());
		ps.setInt(4, bug.getPriority());
		ps.setString(5, bug.getType());
		ps.setString(6, bug.getApplication());
		ps.setString(7, bug.getHeader());
		ps.setString(8, bug.getBody());
		ps.setInt(9, bug.getId());
		log.debug("RoleDAO update(): " + ps.toString());
		rowCount = ps.executeUpdate();
		return rowCount;
	}

	/**
	 * Elimina un Bug de la tabla 
	 *
	 * @param bugId		Id del objeto Bug que se quiere eliminar de la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha eliminado con éxito.
	 * @throws SQLException 
	 */
	public int delete(int bugId) throws SQLException {
		int rowCount = 0;
		String sql = "DELETE FROM bugs WHERE bug_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, bugId);
		log.debug("BugDAO delete(): " + ps.toString());
		rowCount = ps.executeUpdate();
		return rowCount;
	}

	public List<Bug> getBugList(ControlParams control) throws SQLException {
		List<Bug> bugs = new ArrayList<>();
		String sql = "SELECT * FROM bugs ORDER BY bug_id ";
		String limit = control.getRecChunk() > 0 ? " LIMIT ? OFFSET ?" : "";
		sql = sql + limit;
		PreparedStatement ps = conn.prepareStatement(sql);
		if (!limit.isEmpty()) {
			ps.setInt(1, control.getRecChunk());
			ps.setInt(2, control.getRecStart());
		}
		log.debug("BugDAO getBugList(): " + ps.toString());
		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				bugs.add(getCompleteBug(rs));
			}
		}
		return bugs;
	}

	public int deleteIds(List<String> ids) throws SQLException {
		String sql = "DELETE FROM bugs WHERE bug_id IN "
				+ SqlUtil.getPreparedStatementInClause(ids);
		PreparedStatement ps = conn.prepareStatement(sql);
		SqlUtil.setList(ps, ids);
		log.debug("BugDAO deleteIds(): " + ps.toString());
		return ps.executeUpdate();
	}

	/**
	 * Devuelve el número de registros totales de la tabla
	 * 
	 * @return variable int con el número de registros en la tabla
	 */
	public int getCountRows() throws SQLException {
		String sql = "SELECT COUNT(*) FROM bugs";
		PreparedStatement ps = conn.prepareStatement(sql);
		log.debug("BugDAO getCountRows(): " + ps.toString());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			return rs.getInt(1);
		}
		return 0;
	}

	public Bug getCompleteBug(ResultSet rs) throws SQLException {
		Bug bug = new Bug();
		bug.setId(rs.getInt("bug_id"));
		bug.setDatetime(rs.getTimestamp("bug_date"));
		bug.setStatus(rs.getInt("bug_status"));
		bug.setUser(rs.getString("bug_user"));
		bug.setPriority(rs.getInt("bug_priority"));
		bug.setType(rs.getString("bug_type"));
		bug.setApplication(rs.getString("bug_application"));
		bug.setHeader(rs.getString("bug_header"));
		bug.setBody(rs.getString("bug_body"));
		return bug;
	}
}
