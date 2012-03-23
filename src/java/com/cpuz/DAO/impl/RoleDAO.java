/*
 * RoleDAO.java
 *
 */
package com.cpuz.DAO.impl;

import com.cpuz.domain.Role;
import com.cpuz.domain.UserType;
import com.cpuz.exceptions.RoleException;
import com.cpuz.util.SqlUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Clase para la ejecución de operaciones CRUD sobre la tabla 'roles'.
 * 
 * @author RAFAEL FERRUZ
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
	 * @throws RoleException 
	 */
	public int create(Role role) throws RoleException {
		int rowCount = 0;
		String sql = "INSERT INTO roles ("
				+ "rol_role, "
				+ "rol_description) "
				+ " VALUES (?,?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, role.getRole());
			ps.setString(2, role.getDescription());
			log.debug("RoleDAO create(): " + ps.toString());
			rowCount = ps.executeUpdate();
		} catch (SQLException e) {
			e.toString();
			throw new RoleException("RoleDAO.create\n" + e);
		}
		return rowCount;
	}

	/**
	 * Recupera un Rol de la tabla 
	 *
	 * @param rolId		Id del objeto Role que se quiere recuperar de la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha recuperado con éxito.
	 * @throws RoleException 
	 */
	public Role read(int rolId) throws RoleException {
		Role role = null;
		String sql = "SELECT * FROM roles WHERE role_id = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				role = getCompleteRole(conn, rs);
			}
		} catch (SQLException e) {
			e.toString();
			throw new RoleException("RoleDAO.read\n" + e);
		}
		return role;
	}

	/**
	 * Actualiza un Rol en la tabla 
	 *
	 * @param role		Objeto Role que se quiere actualizar en la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha insertado con éxito.
	 * @throws RoleException 
	 */
	public int update(Role role) throws RoleException {
		int rowCount = 0;
		String sql = "UPDATE roles SET "
				+ "rol_role = ?, "
				+ "rol_description = ? "
				+ " WHERE rol_id = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, role.getRole());
			ps.setString(2, role.getDescription());
			ps.setInt(3, role.getId());
			log.debug("RoleDAO update(): " + ps.toString());
			rowCount = ps.executeUpdate();
		} catch (SQLException e) {
			e.toString();
			throw new RoleException("RoleDAO.update\n" + e);
		}
		return rowCount;
	}

	/**
	 * Elimina un Role de la tabla 
	 *
	 * @param rolId		Id del objeto Role que se quiere eliminar de la tabla
	 * @return			Un entero indicando el número de filas afectadas por la sentencia
	 *					SQL; en está caso será igual a 1 si se ha eliminado con éxito.
	 * @throws RoleException 
	 */
	public int delete(int rolId) throws RoleException {
		int rowCount = 0;
		String sql = "DELETE FROM roles WHERE rol_id = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, rolId);
			log.debug("RoleDAO delete(): " + ps.toString());
			rowCount = ps.executeUpdate();
		} catch (SQLException e) {
			e.toString();
			throw new RoleException("RoleDAO.delete\n" + e);
		}
		return rowCount;
	}

	private Role getCompleteRole(Connection conn, ResultSet rs) throws SQLException {
		Role role = new Role();
		role.setId(rs.getInt("rol_id"));
		role.setRole(rs.getString("rol_role"));
		role.setDescription(rs.getString("rol_description"));
		return role;
	}

	public List<Role> getRoleList(UserType userType, int offset, int count) throws RoleException {
		List<Role> roles = new ArrayList<>();
		String sql = "SELECT * FROM roles ORDER BY rol_role ";
		String limit = count > 0 ? " LIMIT ? OFFSET ?" : "";
		sql = sql + limit;
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			if (!limit.isEmpty()) {
				ps.setInt(1, count);
				ps.setInt(2, offset);
			}
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					roles.add(getCompleteRole(conn, rs));
				}
			}
		} catch (SQLException e) {
			e.toString();
			throw new RoleException("RoleDAO.roleList\n" + e);
		}
		return roles;
	}

	public int deleteIds(List<String> ids) throws RoleException {
		String sql = "DELETE FROM roles WHERE rol_id IN "
				+SqlUtil.getPreparedStatementInClause(ids);
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			SqlUtil.setList(ps, ids);
			log.debug("RoleDAO deleteIds(): " + ps.toString());
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.toString();
			throw new RoleException("RoleDAO.deleteIds\n" + e);
		}
	}
}
