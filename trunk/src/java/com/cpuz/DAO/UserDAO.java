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
import com.cpuz.st2.beans.ControlParams;
import com.cpuz.util.SqlUtil;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.log4j.Logger;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class UserDAO implements InjectableDAO {

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
	public synchronized int create(User rec) throws SQLException {

		String sql = "INSERT INTO users "
				+ "(usu_date, usu_status, usu_category, usu_user, "
				+ "usu_name, usu_password, usu_email) "
				+ " VALUES ("
				+ "'" + (new SimpleDateFormat("yyyy-MM-dd").format(rec.getDate())) + "',"
				+ " " + rec.getStatus() + ","
				+ " " + rec.getCategory() + " ,"
				+ "'" + rec.getUser() + "',"
				+ "'" + rec.getName() + "',"
				+ "'" + rec.getPassword() + "',"
				+ "'" + rec.getEmail() + "')";
		int rowCount = 0;

		PreparedStatement ps = conn.prepareStatement(sql);
		rowCount = ps.executeUpdate();
		sql = "SELECT LAST_INSERT_id() FROM users ";
		ps = conn.prepareStatement(sql);
		log.debug("UserDAO create(): " + ps.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			rec.setId(rs.getInt(1));
		} else {
			rec.setId(0);
		}
		return rowCount;
	}

	/**
	 * Recupera un User de la tabla 
	 *
	 * @param userId		Id del objeto User que se quiere recuperar de la tabla
	 * @return			Un un objeto bug con la información recuperada de la 
	 *					base de datos. Si no encuentra el id buscado, devuelve null.
	 * @throws SQLException 
	 */
	public User read(int userId) throws SQLException {
		User user = null;
		String sql = "SELECT * FROM users WHERE usu_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, userId);
		log.debug("UserDAO create(): " + ps.toString());
		ResultSet rs = ps.executeQuery(sql);
		if (rs.next()) {
			user = getCompleteUser(rs);
		}
		return user;
	}

	/**
	 * Recupera un User de la tabla 
	 *
	 * @param userCode		user del objeto User que se quiere recuperar de la tabla
	 * @return			Un un objeto bug con la información recuperada de la 
	 *					base de datos. Si no encuentra el id buscado, devuelve null.
	 * @throws SQLException 
	 */
	public User read(String userCode) throws SQLException {
		User user = null;
		String sql = "SELECT * FROM users AS usu LEFT JOIN userroles AS usr ON usr.usu_user = usu.usu_user WHERE usu.usu_user = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, userCode);
		log.debug("UserDAO create(): " + ps.toString());
		ResultSet rs = ps.executeQuery();
		List<UserRole> userRoles = new ArrayList<>();
		while (rs.next()) {
			if (user == null) {
				user = getCompleteUser(rs);
				user.setRoles(userRoles);
			}
			UserRole userRole=new UserRole();
			userRole.setId(rs.getInt("usr.usr_id"));
			userRole.setStatus(rs.getInt("usr.usr_status"));
			userRole.setUser(rs.getString("usr.usu_user"));
			userRole.setRole(rs.getString("usr.usr_role"));
			userRole.setDescription(rs.getString("usr.usr_description"));
			userRoles.add(userRole);
		}
		return user;
	}

	/**
	 * Actualiza un User en la tabla 
	 *
	 * @param user		Objeto User que se quiere actualizar en la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha insertado con éxito.
	 * @throws SQLException 
	 */
	public int update(User user) throws SQLException {
		int rowCount = 0;
		String sql = "UPDATE users SET "
				+ "usu_date = ?, usu_status = ?, usu_category = ?, "
				+ "usu_user = ?, usu_name = ?, usu_password = ?, user_email = ? "
				+ " WHERE usu_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setDate(1, new java.sql.Date(user.getDate().getTime()));
		ps.setInt(2, user.getStatus());
		ps.setInt(3, user.getCategory());
		ps.setString(4, user.getUser());
		ps.setString(5, user.getName());
		ps.setString(6, user.getPassword());
		ps.setString(7, user.getEmail());
		ps.setInt(8, user.getId());
		log.debug("UserDAO update(): " + ps.toString());
		rowCount = ps.executeUpdate();
		return rowCount;
	}

	/**
	 * Elimina un User de la tabla 
	 *
	 * @param userId		Id del objeto User que se quiere eliminar de la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha eliminado con éxito.
	 * @throws SQLException 
	 */
	public int delete(int userId) throws SQLException {
		int rowCount = 0;
		String sql = "DELETE FROM users WHERE usu_id = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, userId);
		log.debug("UserDAO delete(): " + ps.toString());
		rowCount = ps.executeUpdate();
		return rowCount;
	}

	public List<User> getUserList(ControlParams control) throws SQLException {
		List<User> roles = new ArrayList<>();
		String sql = "SELECT * FROM users ORDER BY usu_id ";
		String limit = control.getRecChunk() > 0 ? " LIMIT ? OFFSET ?" : "";
		sql = sql + limit;
		PreparedStatement ps = conn.prepareStatement(sql);
		if (!limit.isEmpty()) {
			ps.setInt(1, control.getRecChunk());
			ps.setInt(2, control.getRecStart());
		}
		log.debug("UserDAO getUserList(): " + ps.toString());
		try (ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				roles.add(getCompleteUser(rs));
			}
		}
		return roles;
	}

	public int deleteIds(List<String> ids) throws SQLException {
		String sql = "DELETE FROM users WHERE usu_id IN "
				+ SqlUtil.getPreparedStatementInClause(ids);
		PreparedStatement ps = conn.prepareStatement(sql);
		SqlUtil.setList(ps, ids);
		log.debug("UserDAO deleteIds(): " + ps.toString());
		return ps.executeUpdate();
	}

	/**
	 * Devuelve el número de registros totales de la tabla
	 * 
	 * @return variable int con el número de registros en la tabla
	 */
	public int getCountRows() throws SQLException {
		String sql = "SELECT COUNT(*) FROM users";
		PreparedStatement ps = conn.prepareStatement(sql);
		log.debug("UserDAO getCountRows(): " + ps.toString());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			return rs.getInt(1);
		}
		return 0;
	}

	public User getCompleteUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("usu_id"));
		user.setDate(rs.getDate("usu_date"));
		user.setStatus(rs.getInt("usu_status"));
		user.setUser(rs.getString("usu_user"));
		user.setName(rs.getString("usu_name"));
		user.setPassword(rs.getString("usu_password"));
		user.setEmail(rs.getString("usu_email"));
		return user;
	}
}
