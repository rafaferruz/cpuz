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
import com.cpuz.domain.Role;
import com.cpuz.domain.User;
import com.cpuz.domain.UserRole;
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
	ControlParams control;
	String userCode;
	String roleName;

	public UserService() {
	}

	public ControlParams getControl() {
		return control;
	}

	public void setControl(ControlParams control) {
		this.control = control;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public boolean keyIdExists(int userId) throws SQLException {
		User user = new DAOFactory().getUserDAO().read(userId);
		return (user != null);
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
		User user = new DAOFactory().getUserDAO().read(userId);
		if (user != null) {
			List<String> userCodes = new ArrayList<>();
			userCodes.add(user.getUser());
			Map<String, List<UserRole>> userRoles = new DAOFactory().getUserRoleDAO().getUserRoleMap(userCodes);
			user.setRoles(userRoles.get(user.getUser()));
		}
		return user;
	}

	public User getByCode(String userCode) throws SQLException {
		User user = new DAOFactory().getUserDAO().read(userCode);
		if (user != null) {
			List<String> userCodes = new ArrayList<>();
			userCodes.add(user.getUser());
			Map<String, List<UserRole>> userRoles = new DAOFactory().getUserRoleDAO().getUserRoleMap(userCodes);
			user.setRoles(userRoles.get(user.getUser()));
		}
		return user;
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
			return 0;
		}
		return userCount;
	}

	public int updateUser(User user) throws SQLException {
		DAOFactory df = new DAOFactory();
		df.startTransaction();
		int userCount = df.getUserDAO().update(user);
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
			return 0;
		}
		return userCount;
	}

	public int deleteUser(User User) throws SQLException {
		return new DAOFactory().getUserDAO().delete(User.getId());
	}

	public int deleteUserIds(List<String> ids) throws SQLException {
		return new DAOFactory().getUserDAO().deleteIds(ids);
	}

	public boolean isUserInRole() throws SQLException {
		if (this.userCode != null && !this.userCode.equals("")) {
			UserRolesService userRolesService = new UserRolesService();
			return (userRolesService.getByUserAndRole(roleName, userCode) != null);
		}
		return false;
	}
}
