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
package com.cpuz.actions.admin;

import com.cpuz.domain.Role;
import com.cpuz.domain.User;
import com.cpuz.domain.UserCategoryType;
import com.cpuz.domain.UserRole;
import com.cpuz.domain.UserStatusType;
import com.cpuz.domain.UserType;
import com.cpuz.exceptions.UserException;
import com.cpuz.service.RolesService;
import com.cpuz.service.UserService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Esta clase gestiona las operaciones CRUD de los objetos User de la aplicación
 */
public class UserAction extends GenericAction<User> {

	private Map<Integer, String> mapCategories = new HashMap<>();
	private List<Role> rolesList;
	private List<UserRole> userRolesList;
	private RolesService rolesService;
	private List<String> authRolesSel = new ArrayList<>();
	private String[] availableRolesSel;
	private String passwordAgain;
	private UserService dataService;

	public UserAction() {
		super();
		dataEdit = new User();
	}

	@Override
	public String execute() {
		return "error";
	}

	public String objectNew() throws SQLException {
		control.setRecCount(1);
		control.setRunAction("New");
		dataEdit.setDate(new Date());
		rolesList = rolesService.getNewsRecords();
		requestAttributes.put("page", "/WEB-INF/views/userEdit.jsp");
		return "new";
	}

	public String objectEdit() throws SQLException, UserException {
		dataEdit = dataService.getById(control.getId());
		// Si no se encuentra el User buscado se lee lista de Users
		if (dataEdit == null) {
			return objectList();
		}
		passwordAgain = dataEdit.getPassword();
		rolesList = rolesService.getNewsRecords();
		userRolesList = dataEdit.getRoles();
		List<Role> removeRoles = new ArrayList<>();
		for (UserRole ur : userRolesList) {
			if (!ur.getRole().equals("")) {
				for (Role r : rolesList) {
					if (ur.getRole().equals(r.getRole())) {
						removeRoles.add(new Role(r.getRole()));
						break;
					}
				}
			}
		}
		rolesList.removeAll(removeRoles);
		mapStatus = getMapStatus();
		mapCategories = getMapCategory();
		control.setRunAction("Edit");
		requestAttributes.put("page", "/WEB-INF/views/userEdit.jsp");
		return "edit";
	}

	public String objectSaveNew() throws SQLException, UserException {
		// Creamos la lista de roles del objeto User
		dataEdit.setRoles(getAuthUserRoles(dataEdit.getUser(), authRolesSel));
		// Persistimos el objeto User
		if (dataService.insertUser(dataEdit) != 1) {
			this.addActionError(getText("UserEditErrorMsg"));
			return "new";
		}
		this.addActionMessage(getText("UserEditSaveOkMsg"));
		return objectList();
	}

	public String objectSaveEdit() throws SQLException, UserException {
		// Creamos la lista de roles del objeto User
		dataEdit.setRoles(getAuthUserRoles(dataEdit.getUser(), authRolesSel));
		// Persistimos el objeto User
		if (dataService.updateUser(dataEdit) != 1) {
			this.addActionError(getText("UserEditErrorMsg"));
			return "edit";
		}
		this.addActionMessage(getText("UserEditSaveOkMsg"));
		return objectList();
	}

	public String objectDelete() throws SQLException {
		if (selec1 != null) {
			String[] deletes = selec1.split(",");
			if (dataService.deleteUserIds(Arrays.asList(deletes)) > 0) {
				addActionMessage(getText("SuccessDeletedUsers"));
			} else {
				addActionError(getText("NoneDeletedUser"));
			}
			return objectList();
		}
		addActionError(getText("NoneSelectedUser"));
		return objectList();
	}

	public String objectList() throws SQLException {
		if (control.getRecCount() == 0) {
			control.setRecCount(dataService.getCountRows());
		}
		control.setUserType(UserType.ADMIN);
		dataList = dataService.getUserList(control);
		control.setRunAction("List");
		requestAttributes.put("page", "/WEB-INF/views/userList.jsp");
		return "list";
	}

	public List<String> getAuthRolesSel() {
		return authRolesSel;
	}

	public void setAuthRolesSel(List<String> authRolesSel) {
		this.authRolesSel = authRolesSel;
	}

	public String[] getAvailableRolesSel() {
		return availableRolesSel;
	}

	public void setAvailableRolesSel(String[] availableRolesSel) {
		this.availableRolesSel = availableRolesSel;
	}

	public RolesService getRolesService() {
		return rolesService;
	}

	public void setRolesService(RolesService rolesService) {
		this.rolesService = rolesService;
	}

	public void setRolesList(List<Role> rolesList) {
		this.rolesList = rolesList;
	}

	public void setUserRolesList(List<UserRole> userRolesList) {
		this.userRolesList = userRolesList;
	}

	public List<Role> getRolesList() {
		return rolesList;
	}

	public List<UserRole> getUserRolesList() {
		return userRolesList;
	}

	public String getPasswordAgain() {
		return passwordAgain;
	}

	public void setPasswordAgain(String password_again) {
		this.passwordAgain = password_again;
	}

	public Map<Integer, String> getMapStatus() {
		for (UserStatusType userStatusType : UserStatusType.list()) {
			mapStatus.put(userStatusType.getId(), userStatusType.getKey());
		}
		return mapStatus;
	}

	public Map<Integer, String> getMapCategory() {
		for (UserCategoryType userCategoryType : UserCategoryType.list()) {
			mapCategories.put(userCategoryType.getId(), userCategoryType.getKey());
		}
		return mapCategories;
	}

	// Creamos la lista de roles del objeto User
	private List<UserRole> getAuthUserRoles(String user, List<String> authUserRoles) {
		List<UserRole> userRoles = new ArrayList<>();
		if (authRolesSel != null) {
			for (String role : authRolesSel) {
				userRoles.add(new UserRole(user, role));
			}
		}
		return userRoles;
	}

	public UserService getDataService() {
		return dataService;
	}

	public void setDataService(Object dataService) {
		this.dataService = (UserService) dataService;
	}
}
