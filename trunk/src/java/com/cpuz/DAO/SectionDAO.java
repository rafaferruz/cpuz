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

import com.cpuz.DAO.impl.InjectableDAO;
import com.cpuz.domain.Section;
import com.cpuz.st2.beans.ControlParams;
import com.cpuz.util.SqlUtil;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

/**
 * Clase para la ejecución de operaciones CRUD sobre la tabla 'sections'.
 */
public class SectionDAO implements InjectableDAO {

	transient private final Logger log = Logger.getLogger(this.getClass());
	private Connection conn;

	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	/**
	 * Añade el Section a la tabla 
	 *
	 * @param rec		Objeto Section que se quiere insertar en la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha insertado con éxito.
	 * @throws SQLException 
	 */
	public synchronized int create(Section rec) throws SQLException {

		String sql = "INSERT INTO sections "
				+ "(sec_id, "
				+ "sec_name, "
				+ "sec_authorized_roles, "
				+ "sec_group) "
				+ " VALUES ("
				+ "'" + rec.getId() + "',"
				+ "'" + rec.getName() + "',"
				+ "'" + rec.getAuthorizedRoles() + "',"
				+ "'" + rec.getGroup() + "')";
		int rowCount = 0;

		PreparedStatement ps = conn.prepareStatement(sql);
		log.debug("SectionDAO create(): " + ps.toString());
		rowCount = ps.executeUpdate();
		return rowCount;
	}

	/**
	 * Recupera un Section de la tabla 
	 *
	 * @param secId		Id del objeto Section que se quiere recuperar de la tabla
	 * @return			Un objeto Section  con la información recuperada de la 
	 *					base de datos. Si no encuentra el id buscado, devuelve null.
	 * @throws SQLException 
	 */
	public Section read(String secId) throws SQLException {
		Section section = null;
		String sql = "SELECT * FROM sections WHERE sec_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, secId);
		log.debug("SectionDAO read(): " + ps.toString());
		ResultSet rs = ps.executeQuery(sql);
		if (rs.next()) {
			section = getCompleteSection(rs);
		}
		return section;
	}

	/**
	 * Actualiza un Section en la tabla 
	 *
	 * @param section		Objeto Section que se quiere actualizar en la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha insertado con éxito.
	 * @throws SQLException 
	 */
	public int update(Section section) throws SQLException {
		int rowCount = 0;
		String sql = "UPDATE sections SET " + ""
				+ "sec_name = ?, "
				+ "sec_authorized_roles = ?, "
				+ "sec_group = ? "
				+ " WHERE sec_id = ?";

		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, section.getName());
		ps.setString(2, section.getAuthorizedRoles());
		ps.setString(3, section.getGroup());
		ps.setString(4, section.getId());
		log.debug("SectionDAO update(): " + ps.toString());
		rowCount = ps.executeUpdate();
		return rowCount;
	}

	/**
	 * Elimina un Section de la tabla 
	 *
	 * @param secId		Id del objeto Section que se quiere eliminar de la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha eliminado con éxito.
	 * @throws SQLException 
	 */
	public int delete(String secId) throws SQLException {
		int rowCount = 0;
		String sql = "DELETE FROM sections WHERE sec_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, secId);
		log.debug("SectionDAO delete(): " + ps.toString());
		rowCount = ps.executeUpdate();
		return rowCount;
	}

	public List<Section> getSectionList(ControlParams control) throws SQLException {
		List<Section> sections = new ArrayList<>();
		String sql = "SELECT * FROM sections ORDER BY sec_id ";
		String limit = control.getRecChunk() > 0 ? " LIMIT ? OFFSET ?" : "";
		sql = sql + limit;
		PreparedStatement ps = conn.prepareStatement(sql);
		if (!limit.isEmpty()) {
			ps.setInt(1, control.getRecChunk());
			ps.setInt(2, control.getRecStart());
		}
		log.debug("SectionDAO getSectionList(): " + ps.toString());
		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				sections.add(getCompleteSection(rs));
			}
		}
		return sections;
	}

	public int deleteIds(List<String> ids) throws SQLException {
		String sql = "DELETE FROM sections WHERE sec_id IN "
				+ SqlUtil.getPreparedStatementInClause(ids);
		PreparedStatement ps = conn.prepareStatement(sql);
		SqlUtil.setList(ps, ids);
		log.debug("SectionDAO deleteIds(): " + ps.toString());
		return ps.executeUpdate();
	}

	/**
	 * Devuelve el número de registros totales de la tabla
	 * 
	 * @return variable int con el número de registros en la tabla
	 */
	public int getCountRows() throws SQLException {
		String sql = "SELECT COUNT(*) FROM sections";
		PreparedStatement ps = conn.prepareStatement(sql);
		log.debug("SectionDAO getCountRows(): " + ps.toString());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			return rs.getInt(1);
		}
		return 0;
	}

	public Section getCompleteSection(ResultSet rs) throws SQLException {
		Section section = new Section();
		section.setId(rs.getString("sec_id"));
		section.setName(rs.getString("sec_name"));
		section.setAuthorizedRoles(rs.getString("sec_authorized_roles"));
		section.setGroup(rs.getString("sec_group"));
		return section;
	}
}
