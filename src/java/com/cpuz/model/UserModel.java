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
package com.cpuz.model;

import com.cpuz.DAO.DAOFactory;
import com.cpuz.domain.User;
import com.cpuz.domain.UserRole;
import com.cpuz.domain.UserType;
import com.cpuz.st2.beans.ControlParams;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class UserModel {

	private final transient Logger log = Logger.getLogger(this.getClass());
	ControlParams control = new ControlParams();

	public UserModel() {
	}

	public boolean keyIdExists(int userId) throws SQLException {
		User user = new DAOFactory().getUserDAO().read(userId);
		return (user != null);
	}

	/**
	 * Proporciona un objeto List de registros de Users
	 * con un número de registros indicado por el parámetro recChunk y
	 * a partir del indicado por el parámetro recStart.
	 * @param recStart Nº de registro inicial
	 * @param recChunk Nº de registros a incluir en la búsqueda
	 * @return Un objeto List<User> con los registros seleccionados
	 */
	public List<User> getNewsRecords() throws SQLException {
		control.setUserType(UserType.ANONYMOUS);
		control.setRecStart(0);
		control.setRecChunk(0);
		return this.getUserList(control);
	}

	public List<User> getNewsRecords(UserType userType) throws SQLException {
		control.setUserType(userType);
		control.setRecStart(0);
		control.setRecChunk(0);
		return this.getUserList(control);

	}

	public List<User> getUserList(ControlParams control) throws SQLException {

		List<User> users = new ArrayList<>();
		users = new DAOFactory().getUserDAO().getUserList(control);
		return users;
	}

	public int getCountRows() throws SQLException {
		return new DAOFactory().getUserDAO().getCountRows();
	}

	public User getById(int userId) throws SQLException {
		return new DAOFactory().getUserDAO().read(userId);
	}

	public int insertUser(User user) throws SQLException {
		DAOFactory df = new DAOFactory();
		df.startTransaction();
		int userCount = df.getUserDAO().create(user);
		if (userCount == 1) {
// Eliminamos todos los UserRole que pudieran existir del User
			df.getUserRoleDAO().deleteAllOfUser(user);
			// Insertamos los nuevos userRole del User
			for (UserRole userRole : user.getRoles()) {
				df.getUserRoleDAO().create(userRole);
			}
			df.commit();
		} else {
			df.rollback(null);
		}
		return userCount;
	}

	public int updateUser(User User) throws SQLException {
		return new DAOFactory().getUserDAO().update(User);
	}

	public int deleteUser(User User) throws SQLException {
		return new DAOFactory().getUserDAO().delete(User.getId());
	}

	public int deleteUserIds(List<String> ids) throws SQLException {
		return new DAOFactory().getUserDAO().deleteIds(ids);
	}
}
