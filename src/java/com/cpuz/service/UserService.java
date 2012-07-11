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
package com.cpuz.service;

import com.cpuz.DAO.DAOFactory;
import com.cpuz.domain.User;
import com.cpuz.domain.UserRole;
import com.cpuz.exceptions.UserException;
import com.cpuz.st2.beans.ControlParams;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class UserService {

	private final transient Logger log = Logger.getLogger(this.getClass());
	ControlParams control = new ControlParams();
	DAOFactory DAOFactory;
	DAOFactory DAOFactoryTransactional;

	public UserService() {
	}

	public DAOFactory getDAOFactory() {
		return DAOFactory;
	}

	public DAOFactory getDAOFactoryTransactional() {
		return DAOFactoryTransactional;
	}

	public void setDAOFactory(DAOFactory DAOFactory) {
		this.DAOFactory = DAOFactory;
	}

	public void setDAOFactoryTransactional(DAOFactory DAOFactoryTransactional) {
		this.DAOFactoryTransactional = DAOFactoryTransactional;
	}

	public boolean keyIdExists(Integer userId) throws SQLException, UserException {
		if (userId == null) {
			throw new UserException("roleException.rolIdNull");
		}
		return (DAOFactory.getUserDAO().read(userId) != null);
	}

	public List<User> getUserList(ControlParams control) throws SQLException {
		List<User> users = DAOFactory.getUserDAO().getUserList(control);
		return users;
	}

	public int getCountRows() throws SQLException {
		return DAOFactory.getUserDAO().getCountRows();
	}

	public User getById(int userId) throws SQLException, UserException {
		User user = DAOFactory.getUserDAO().read(userId);
		if (user != null && user.getUser() != null && !user.getUser().isEmpty()) {
			user.setRoles(DAOFactory.getUserRoleDAO().getUserRoleList(user.getUser()));
		}
		return user;
	}

	public User getByCode(String userCode) throws SQLException, UserException {
		User user = DAOFactory.getUserDAO().read(userCode);
		if (user != null) {
			List<String> userCodes = new ArrayList<>();
			userCodes.add(user.getUser());
			Map<String, List<UserRole>> userUsers = DAOFactory.getUserRoleDAO().getUserRoleMap(userCodes);
			user.setRoles(userUsers.get(user.getUser()));
		}
		return user;
	}

	public int insertUser(User user) throws SQLException, UserException {
		DAOFactoryTransactional.startTransaction();
		int userCount = DAOFactoryTransactional.getUserDAO().create(user);
		if (userCount == 1) {
			// Eliminamos todos los UserRole que pudieran existir del User
			DAOFactoryTransactional.getUserRoleDAO().deleteAllOfUser(user);
			// Insertamos los nuevos userUser del User
			for (UserRole userUser : user.getRoles()) {
				DAOFactoryTransactional.getUserRoleDAO().create(userUser);
			}
			DAOFactoryTransactional.commit();
		} else {
			DAOFactoryTransactional.rollback(null);
			return 0;
		}
		return userCount;
	}

	public int updateUser(User user) throws SQLException, UserException {
		DAOFactoryTransactional.startTransaction();
		int userCount = DAOFactoryTransactional.getUserDAO().update(user);
		if (userCount == 1) {
			// Eliminamos todos los UserRole que pudieran existir del User
			int deletes = DAOFactoryTransactional.getUserRoleDAO().deleteAllOfUser(user);
			// Insertamos los nuevos userUser del User
			for (UserRole userUser : user.getRoles()) {
				DAOFactoryTransactional.getUserRoleDAO().create(userUser);
			}
			DAOFactoryTransactional.commit();
		} else {
			DAOFactoryTransactional.rollback(null);
			return 0;
		}
		return userCount;
	}

	public int deleteUser(User user) throws SQLException, UserException {
		if (user == null) {
			throw new UserException("roleException.roleNull");
		}
		if (user.getId() == null) {
			throw new UserException("roleException.rolIdNull");
		}
		DAOFactoryTransactional.startTransaction();
		int deletedRows = DAOFactoryTransactional.getUserDAO().delete(user.getId());
		if (deletedRows == 1) {
			// Eliminamos todos los UserRole que pudieran existir del User
			DAOFactoryTransactional.getUserRoleDAO().deleteAllOfUser(user);
			DAOFactoryTransactional.commit();
		} else {
			DAOFactoryTransactional.rollback(null);
			return 0;
		}
		return deletedRows;
	}

	public int deleteUserIds(List<String> ids) throws SQLException, UserException {
		List<User> users = DAOFactory.getUserDAO().getUserListOnIds(ids);
		List<String> usuRoles = new ArrayList();
		for (User user : users) {
			usuRoles.add(user.getUser());
		}
		DAOFactoryTransactional.startTransaction();
		int deletedRows = DAOFactoryTransactional.getUserDAO().deleteIds(ids);
		if (deletedRows > 0) {
			// Eliminamos todos los UserRole que pudieran existir de los User eliminados
			if (!usuRoles.isEmpty()) {
				DAOFactoryTransactional.getUserRoleDAO().deleteRolesOfUsers(usuRoles);
			}
			DAOFactoryTransactional.commit();
		} else {
			DAOFactoryTransactional.rollback(null);
			return 0;
		}

		return deletedRows;
	}

	public ControlParams getControl() {
		return control;
	}

	public void setControl(ControlParams control) {
		this.control = control;
	}
}
