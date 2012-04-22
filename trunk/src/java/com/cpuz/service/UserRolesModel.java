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
import com.cpuz.domain.UserRole;
import com.cpuz.DAO.UserRoleDAO;
import com.cpuz.domain.User;
import com.cpuz.exceptions.UserRoleException;
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
public class UserRolesModel {

	private final transient Logger log = Logger.getLogger(this.getClass());
	ControlParams control = new ControlParams();

	public UserRolesModel() {
	}

	public boolean keyIdExists(int userRoleId) throws SQLException {
		UserRole userRole = new DAOFactory().getUserRoleDAO().read(userRoleId);
		return (userRole != null);
	}

	public List<UserRole> getUserRoleList(String user) throws SQLException {

		List<UserRole> userRoles = new ArrayList<>();
		userRoles = new DAOFactory().getUserRoleDAO().getUserRoleList(user);
		return userRoles;
	}

	public UserRole getById(int userRoleId) throws SQLException {
		return new DAOFactory().getUserRoleDAO().read(userRoleId);
	}

	public UserRole getByUserAndRole(String role, String userCode) throws SQLException {
		return new DAOFactory().getUserRoleDAO().read(role, userCode);
	}

	public int insertUserRole(UserRole userRole) throws SQLException {
		return new DAOFactory().getUserRoleDAO().create(userRole);
	}

	public int updateUserRole(UserRole UserRole) throws SQLException {
		return new DAOFactory().getUserRoleDAO().update(UserRole);
	}

	public int deleteUserRole(UserRole UserRole) throws SQLException {
		return new DAOFactory().getUserRoleDAO().delete(UserRole.getId());
	}

	public int deleteUserRoleIds(List<Integer> ids) throws SQLException {
		return new DAOFactory().getUserRoleDAO().deleteIds(ids);
	}
}
