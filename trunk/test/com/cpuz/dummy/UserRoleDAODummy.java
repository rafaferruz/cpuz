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
package com.cpuz.dummy;

import com.cpuz.DAO.UserRoleDAO;
import com.cpuz.domain.User;
import com.cpuz.domain.UserRole;
import com.cpuz.exceptions.UserException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase dummy para la ejecución tests de operaciones CRUD sobre la tabla 'useruserRoles'.
 * 
 */
public class UserRoleDAODummy extends UserRoleDAO {

	/*
	 * Método dummy para testear clases que accedan a RoleDAODummy.create()
	 */
	public int create(UserRole userRole) throws SQLException, UserException {
		if (userRole == null || userRole.getRole() == null || userRole.getRole().equals("")
				|| userRole.getUser() == null || userRole.getUser().equals("")) {
			throw new UserException("userRoleException.nullOrEmptyField");
		}
		if (userRole.getId() < 0) {
			throw new SQLException("Testing SQLException");
		}
		return userRole.getId();
	}

	/*
	 * Método dummy para testear clases que accedan a RoleDAODummy.read()
	 */
	public UserRole read(int userRoleId) throws SQLException {
		if (userRoleId == -1) {
			throw new SQLException("Error en consulta SQL");
		}
		if (userRoleId == 0) {	// Testeamos que no encuentra el registro
			return null;
		} else {
			return new UserRole();
		}
	}

	/*
	 * Método dummy para testear clases que accedan a RoleDAODummy.read()
	 */
	public UserRole read(String role, String userCode) throws SQLException, UserException {
		if (role == null || role.equals("")
				|| userCode == null || userCode.equals("")) {
			throw new UserException("userRoleException.nullOrEmptyField");
		}
		if (role.equals("SQLException")) {
			throw new SQLException("Testing SQLException");
		}
		if (role.equals("notFound")) {	// Testeamos que no encuentra el registro
			return null;
		} else {
			UserRole userRole=new UserRole();
			userRole.setUser("user1");
			userRole.setRole("role1");
			return userRole;
		}
	}

	/*
	 * Método dummy para testear clases que accedan a RoleDAODummy.update()
	 */
	public int update(UserRole userRole) throws SQLException, UserException {
		if (userRole == null || userRole.getId() == null || userRole.getRole() == null
				|| userRole.getRole().equals("") || userRole.getUser() == null || userRole.getUser().equals("")) {
			throw new UserException("userRoleException.nullOrEmptyField");
		}
		if (userRole.getId() < 0) {
			throw new SQLException("Testing SQLException");
		}
		return userRole.getId();
	}

	public int delete(int rolId) throws SQLException {
		if (rolId < 0) {
			throw new SQLException("Error en consulta SQL");
		}
		if (rolId == 0) {	// Testeamos que no se elimina el registro
			return 0;
		} else {
			return 1;
		}
	}

	public int deleteIds(List<Integer> ids) throws SQLException {
		if (ids == null || ids.isEmpty()) {
			return 0;
		}
		if (ids.get(0) == -1) {
			throw new SQLException("Testing SQLException");
		}
		if (ids.get(0) == 0) {
			return 0;
		}
		return ids.size();
	}

	public int deleteAllOfUser(User user) throws SQLException, UserException {
		if (user == null || user.getUser() == null || user.getUser().equals("")) {
			throw new UserException("userRoleException.nullOrEmptyField");
		}
		if (user.getId() == -1) {
			throw new SQLException("Testing SQLException");
		}
		return 1;
	}

	public List<UserRole> getUserRoleList(String user) throws SQLException, UserException {
		if (user == null || user.equals("")) {
			throw new UserException("userRoleException.nullOrEmptyField");
		}
		if (user.equals("SQLException")) {
			throw new SQLException("Testing SQLException");
		}
		return new ArrayList<>();
	}

	public Map<String, List<UserRole>> getUserRoleMap(List<String> userCodes) throws SQLException, UserException {
		if (userCodes == null) {
			throw new UserException("userRoleException.nullOrEmptyField");
		}
		if (userCodes.get(0).equals("SQLException")) {
			throw new SQLException("Testing SQLException");
		}
		return new HashMap<>();
	}
}
