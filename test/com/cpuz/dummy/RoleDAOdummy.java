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

import com.cpuz.DAO.RoleDAO;
import com.cpuz.domain.Role;
import com.cpuz.exceptions.RoleException;
import com.cpuz.st2.beans.ControlParams;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase dummy para la ejecución tests de operaciones CRUD sobre la tabla 'roles'.
 * 
 */
public class RoleDAOdummy extends RoleDAO {

	/*
	 * Método dummy para testear clases que accedan a RoleDAOdummy.create()
	 */
	public int create(Role role) throws SQLException, RoleException {
		if (role == null || role.getRole() == null || role.getRole().equals("")
				|| role.getDescription() == null || role.getDescription().equals("")) {
			throw new RoleException("roleException.nullOrEmptyField");
		}
		if (role.getId() < 0) {
			throw new SQLException("Testing SQLException");
		}
		return role.getId();
	}

	/*
	 * Método dummy para testear clases que accedan a RoleDAOdummy.read()
	 */
	public Role read(int rolId) throws SQLException {
		if (rolId == -1) {
			throw new SQLException("Error en consulta SQL");
		}
		if (rolId == 0) {	// Testeamos que no encuentra el registro
			return null;
		} else {
			return new Role();
		}
	}

	/*
	 * Método dummy para testear clases que accedan a RoleDAOdummy.update()
	 */
	public int update(Role role) throws SQLException, RoleException {
		if (role == null || role.getId() == null || role.getRole() == null
				|| role.getRole().equals("") || role.getDescription() == null || role.getDescription().equals("")) {
			throw new RoleException("roleException.nullOrEmptyField");
		}
		if (role.getId() < 0) {
			throw new SQLException("Testing SQLException");
		}
		return role.getId();
	}

	public List<Role> getRoleList(ControlParams control) throws SQLException {
		if (control == null) {
			throw new SQLException("Testing SQLException");
		}
		List<Role> roles = new ArrayList<>();
		for (int i = 0; i < control.getRecCount(); i++) {
			roles.add(new Role());
		}
		return roles;
	}

	public int getCountRows() throws SQLException {
		return 1;
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

	public int deleteIds(List<String> ids) throws SQLException {
		if (ids == null || ids.isEmpty()) {
			return 0;
		}
		if (ids.get(0) == null) {
			throw new SQLException("Testing SQLException");
		}
		return ids.size();
	}
}
