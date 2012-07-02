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

import com.cpuz.DAO.UserDAO;
import com.cpuz.domain.User;
import com.cpuz.exceptions.UserException;
import com.cpuz.st2.beans.ControlParams;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase dummy para la ejecución tests de operaciones CRUD sobre la tabla 'users'.
 * 
 */
public class UserDAODummy extends UserDAO {

	/*
	 * Método dummy para testear clases que accedan a UserDAODummy.create()
	 */
	public int create(User user) throws SQLException, UserException {
		if (user == null || user.getUser() == null || user.getUser().equals("")
				|| user.getPassword() == null || user.getPassword().equals("")) {
			throw new UserException("userException.nullOrEmptyField");
		}
		if (user.getId() < 0) {
			throw new SQLException("Testing SQLException");
		}
		return user.getId();
	}

	/*
	 * Método dummy para testear clases que accedan a UserDAODummy.read()
	 */
	public User read(int userId) throws SQLException {
		if (userId == -1) {
			throw new SQLException("Error en consulta SQL");
		}
		if (userId == 0) {	// Testeamos que no encuentra el registro
			return null;
		} else {
			User user=new User();
			user.setId(1);
			user.setUser("user1");
			return new User();
		}
	}

	/*
	 * Método dummy para testear clases que accedan a UserDAODummy.update()
	 */
	public int update(User user) throws SQLException, UserException {
		if (user == null || user.getId() == null || user.getUser() == null
				|| user.getUser().equals("")) {
			throw new UserException("userException.nullOrEmptyField");
		}
		if (user.getId() < 0) {
			throw new SQLException("Testing SQLException");
		}
		return user.getId();
	}

	public List<User> getUserList(ControlParams control) throws SQLException {
		if (control.getRecChunk() < 0) {
			throw new SQLException("Testing SQLException");
		}
		List<User> users = new ArrayList<>();
		for (int i = 0; i < control.getRecCount(); i++) {
			users.add(new User());
		}
		return users;
	}

	public int getCountRows() throws SQLException {
		return 1;
	}

	public int delete(int userId) throws SQLException, UserException {
		if (userId < 0) {
			throw new SQLException("Error en consulta SQL");
		}
		if (userId == 0) {	// Testeamos que no se elimina el registro
			return 0;
		} else {
			return 1;
		}
	}

	public int deleteIds(List<String> ids) throws SQLException {
		if (ids == null || ids.isEmpty()) {
			return 0;
		}
		if (ids.get(0).equals("")) {
			throw new SQLException("Testing SQLException");
		}
		if (ids.get(0).equals("none")) {
			return 0;
		}
		return ids.size();
	}
}
