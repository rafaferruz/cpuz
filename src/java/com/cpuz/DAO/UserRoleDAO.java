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
import com.cpuz.domain.User;
import com.cpuz.domain.UserRole;
import com.cpuz.exceptions.UserException;
import com.cpuz.util.SqlUtil;
import java.sql.*;
import java.util.*;
import org.apache.log4j.Logger;

/**
 * Clase para la ejecución de operaciones CRUD sobre la tabla 'userroles'.
 *
 */
public class UserRoleDAO implements InjectableDAO {

	transient private final Logger log = Logger.getLogger(this.getClass());
	private Connection conn;

	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	/**
	 * Añade el User a la tabla 
	 *
	 * @param User		Objeto User que se quiere insertar en la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha insertado con éxito.
	 * @throws SQLException 
	 */
	public int create(UserRole rec) throws SQLException, UserException {
		if (rec == null || rec.getRole() == null || rec.getRole().equals("")
				|| rec.getUser() == null || rec.getUser().equals("")) {
			throw new UserException("userRoleException.nullOrEmptyField");
		}

		String sql = "INSERT INTO userroles "
				+ "(usr_id, "
				+ "usr_status, "
				+ "usu_user, "
				+ "usr_role, "
				+ "usr_description) "
				+ " VALUES ("
				+ rec.getId() + ","
				+ " " + rec.getStatus() + ", "
				+ "'" + rec.getUser() + "', "
				+ "'" + rec.getRole() + "', "
				+ "'" + rec.getDescription() + "')";
		int rowCount = 0;

		PreparedStatement ps = conn.prepareStatement(sql);
		rowCount = ps.executeUpdate();
		sql = "SELECT LAST_INSERT_id() FROM userroles ";
		ps = conn.prepareStatement(sql);
		log.debug("UserRoleDAO create(): " + ps.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			rec.setId(rs.getInt(1));
		} else {
			rec.setId(0);
		}
		return rowCount;
	}

	/**
	 * Recupera un UserRole de la tabla 
	 *
	 * @param userRoleId		Id del objeto UserRole que se quiere recuperar de la tabla
	 * @return			Un objeto UserRole con la información recuperada de la 
	 *					base de datos. Si no encuentra el id buscado, devuelve null.
	 * @throws SQLException 
	 */
	public UserRole read(int userRoleId) throws SQLException {
		UserRole userRole = null;
		String sql = "SELECT * FROM userroles WHERE usr_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, userRoleId);
		log.debug("UserRoleDAO read(): " + ps.toString());
		ResultSet rs = ps.executeQuery(sql);
		if (rs.next()) {
			userRole = getCompleteUserRole(rs);
		}
		return userRole;
	}

	/**
	 * Recupera un UserRole de la tabla 
	 *
	 * @param userCode		Nombre o Code del User que se quiere recuperar de la tabla
	 * @param role		Role del User que se quiere recuperar de la tabla
	 * @return			Un objeto UserRole con la información recuperada de la 
	 *					base de datos. Si no encuentra el id buscado, devuelve null.
	 * @throws SQLException 
	 */
	public UserRole read(String role, String userCode) throws SQLException, UserException {
		if (role == null || role.equals("")
				|| userCode == null || userCode.equals("")) {
			throw new UserException("userRoleException.nullOrEmptyField");
		}

		UserRole userRole = null;
		String sql = "SELECT * FROM userroles WHERE usr_role = ? AND usu_user = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, role);
		ps.setString(2, userCode);
		log.debug("UserRoleDAO read(): " + ps.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			userRole = getCompleteUserRole(rs);
		}
		return userRole;
	}

	/**
	 * Actualiza un UserRole en la tabla 
	 *
	 * @param userRole		Objeto UserRole que se quiere actualizar en la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha insertado con éxito.
	 * @throws SQLException 
	 */
	public int update(UserRole rec) throws SQLException, UserException {
		if (rec == null || rec.getRole() == null || rec.getRole().equals("")
				|| rec.getUser() == null || rec.getUser().equals("")) {
			throw new UserException("userRoleException.nullOrEmptyField");
		}

		int rowCount = 0;
		String sql = "UPDATE userroles SET "
				+ "usr_status = ?, usu_user = ?, usr_role = ?, usr_description = ? "
				+ " WHERE usr_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, rec.getStatus());
		ps.setString(2, rec.getUser());
		ps.setString(3, rec.getRole());
		ps.setString(4, rec.getDescription());
		ps.setInt(5, rec.getId());
		log.debug("UserRoleDAO update(): " + ps.toString());
		rowCount = ps.executeUpdate();
		return rowCount;
	}

	/**
	 * Elimina un UserRole de la tabla 
	 *
	 * @param userRoleId		Id del objeto UserRole que se quiere eliminar de la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha eliminado con éxito.
	 * @throws SQLException 
	 */
	public int delete(int userRoleId) throws SQLException {
		int rowCount = 0;
		String sql = "DELETE FROM userroles WHERE usr_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, userRoleId);
		log.debug("UserRoleDAO delete(): " + ps.toString());
		rowCount = ps.executeUpdate();
		return rowCount;
	}

	/**
	 * Elimina un UserRole de la tabla 
	 *
	 * @param userRoleId		Id del objeto UserRole que se quiere eliminar de la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha eliminado con éxito.
	 * @throws SQLException 
	 */
	public int deleteAllOfUser(User user) throws SQLException, UserException {
		if (user == null || user.getUser() == null || user.getUser().equals("")) {
			throw new UserException("userRoleException.nullOrEmptyField");
		}
		int rowCount = 0;
		String sql = "DELETE FROM userroles WHERE usu_user = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, user.getUser());
		log.debug("UserRoleDAO deleteAllOfUser(): " + ps.toString());
		rowCount = ps.executeUpdate();
		return rowCount;
	}

	public List<UserRole> getUserRoleList(String user) throws SQLException, UserException {
		if (user == null || user.equals("")) {
			throw new UserException("userRoleException.nullOrEmptyField");
		}
		List<UserRole> roles = new ArrayList<>();
		String sql = "SELECT * FROM userroles ORDER BY usr_user = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, user);
		log.debug("UserRoleDAO getUserList(): " + ps.toString());
		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				roles.add(getCompleteUserRole(rs));
			}
		}
		return roles;
	}

	public Map<String, List<UserRole>> getUserRoleMap(List<String> userCodes) throws SQLException, UserException {
		if (userCodes == null) {
			throw new UserException("userRoleException.nullOrEmptyField");
		}
		Map<String, List<UserRole>> userRoles = new HashMap<>();
		if (!userCodes.isEmpty()) {
			String sql = "SELECT * FROM userroles "
					+ "WHERE usu_user IN " + SqlUtil.getPreparedStatementInClause(userCodes) + " "
					+ "ORDER BY usu_user";
			PreparedStatement ps = conn.prepareStatement(sql);
			SqlUtil.setList(ps, userCodes);
			log.debug("UserRoleDAO getUserList(): " + ps.toString());
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					if (userRoles.get(rs.getString("usu_user")) == null) {
						userRoles.put(rs.getString("usu_user"), new ArrayList<UserRole>());
					}
					userRoles.get(rs.getString("usu_user")).add(this.getCompleteUserRole(rs));
				}
			}
		}
		return userRoles;
	}

	public int deleteIds(List<Integer> ids) throws SQLException {
		String sql = "DELETE FROM userroles WHERE usr_id IN "
				+ SqlUtil.getPreparedStatementInClause(ids);
		PreparedStatement ps = conn.prepareStatement(sql);
		SqlUtil.setList(ps, ids);
		log.debug("UserRoleDAO deleteIds(): " + ps.toString());
		return ps.executeUpdate();
	}

	public UserRole getCompleteUserRole(ResultSet rs) throws SQLException {
		UserRole userRole = new UserRole();
		userRole.setId(rs.getInt("usr_id"));
		userRole.setStatus(rs.getInt("usr_status"));
		userRole.setUser(rs.getString("usu_user"));
		userRole.setRole(rs.getString("usr_role"));
		userRole.setDescription(rs.getString("usr_description"));
		return userRole;
	}
}
