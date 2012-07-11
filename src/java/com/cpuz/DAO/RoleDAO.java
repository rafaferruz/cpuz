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

import com.cpuz.domain.Role;
import com.cpuz.exceptions.UserException;
import com.cpuz.st2.beans.ControlParams;
import com.cpuz.util.SqlUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Clase para la ejecución de operaciones CRUD sobre la tabla 'roles'.
 * 
 */
public class RoleDAO implements InjectableDAO {

	transient private final Logger log = Logger.getLogger(this.getClass());
	private Connection conn;

	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	/**
	 * Añade el Rol a la tabla 
	 *
	 * @param role		Objeto Role que se quiere insertar en la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha insertado con éxito.
	 * @throws SQLException 
	 */
	public int create(Role role) throws SQLException, UserException {
		if (role == null || role.getRole() == null || role.getRole().equals("")
				|| role.getDescription() == null || role.getDescription().equals("")) {
			throw new UserException("roleException.nullOrEmptyField");
		}
		String sql = "INSERT INTO roles ("
				+ "rol_role, "
				+ "rol_description) "
				+ " VALUES (?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, role.getRole());
		ps.setString(2, role.getDescription());
		log.debug("RoleDAO create(): " + ps.toString());
		return ps.executeUpdate();

	}

	/**
	 * Recupera un Rol de la tabla 
	 *
	 * @param rolId		Id del objeto Role que se quiere recuperar de la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha recuperado con éxito.
	 * @throws SQLException 
	 */
	public Role read(int rolId) throws SQLException {
		Role role = null;
		String sql = "SELECT * FROM roles WHERE rol_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, rolId);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			role = getCompleteRole(rs);
		}
		return role;
	}

	/**
	 * Actualiza un Rol en la tabla 
	 *
	 * @param role		Objeto Role que se quiere actualizar en la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha insertado con éxito.
	 * @throws SQLException 
	 */
	public int update(Role role) throws SQLException, UserException {
		if (role == null || role.getId() == null || role.getRole() == null
				|| role.getRole().equals("") || role.getDescription() == null || role.getDescription().equals("")) {
			throw new UserException("roleException.nullOrEmptyField");
		}
		int rowCount = 0;
		String sql = "UPDATE roles SET "
				+ "rol_role = ?, "
				+ "rol_description = ? "
				+ " WHERE rol_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, role.getRole());
		ps.setString(2, role.getDescription());
		ps.setInt(3, role.getId());
		log.debug("RoleDAO update(): " + ps.toString());
		rowCount = ps.executeUpdate();
		return rowCount;
	}

	/**
	 * Elimina un Role de la tabla 
	 *
	 * @param rolId		Id del objeto Role que se quiere eliminar de la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha eliminado con éxito.
	 * @throws SQLException 
	 */
	public int delete(int rolId) throws SQLException {
		int rowCount = 0;
		String sql = "DELETE FROM roles WHERE rol_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, rolId);
		log.debug("RoleDAO delete(): " + ps.toString());
		rowCount = ps.executeUpdate();
		return rowCount;
	}

	private Role getCompleteRole(ResultSet rs) throws SQLException {
		Role role = new Role();
		role.setId(rs.getInt("rol_id"));
		role.setRole(rs.getString("rol_role"));
		role.setDescription(rs.getString("rol_description"));
		return role;
	}

	/*
	 * Devuelve una List<Role> con los roles encontrados en la tabla roles con
	 * un número máximo de filas indicada por control.getRecChunk y a partir de la
	 * fila indicada por control.getRecStart. Si control.RecChunk es igual a 0,
	 * se devuelven todos los roles que haya en la tabla.
	 * 
	 * @param control		Pasa el objeto ControlParams que define variables de 
	 *						comportamiento de la consulta: Nº máximo de filas devueltas,
	 *						desde que fila hay que tomar, qué tipo de usuario accede 
	 *						(ver API de ControlParams). Si control es null, no se 
	 *						realiza ningún filtro y se devuelven todas las filas de la tabla
	 * @return				Una List<Role> de filas encontradas. Si no se encuentra ninguna
	 *						fila en la consulta, se devuelve una List<Role> vacía.
	 * @throws SQLException 
	 */
	public List<Role> getRoleList(ControlParams control) throws SQLException {
		List<Role> roles = new ArrayList<>();
		String sql = "SELECT * FROM roles ORDER BY rol_role ";
		String limit = control != null && control.getRecChunk() > 0 ? " LIMIT ? OFFSET ?" : "";
		sql = sql + limit;
		PreparedStatement ps = conn.prepareStatement(sql);
		if (!limit.isEmpty()) {
			ps.setInt(1, control.getRecChunk());
			ps.setInt(2, control.getRecStart());
		}
		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				roles.add(getCompleteRole(rs));
			}
		}
		return roles;
	}

	public int deleteIds(List<String> ids) throws SQLException {
		if (ids==null || ids.isEmpty()){
			return 0;
		}
		String sql = "DELETE FROM roles WHERE rol_id IN "
				+ SqlUtil.getPreparedStatementInClause(ids);
		PreparedStatement ps = conn.prepareStatement(sql);
		SqlUtil.setList(ps, ids);
		log.debug("RoleDAO deleteIds(): " + ps.toString());
		return ps.executeUpdate();
	}

	/**
	 * Devuelve el número de registros totales de la tabla
	 * 
	 * @return variable int con el número de registros en la tabla
	 */
	public int getCountRows() throws SQLException {
		String sql = "SELECT COUNT(*) FROM roles";
		PreparedStatement ps = conn.prepareStatement(sql);
		log.debug("RoleDAO getCountRows(): " + ps.toString());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			return rs.getInt(1);
		}
		return 0;
	}
}
