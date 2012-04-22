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
import com.cpuz.domain.UserRole;
import com.cpuz.service.RolesService;
import com.cpuz.service.UserRolesService;
import com.cpuz.service.UserService;
import com.cpuz.st2.beans.ControlParams;
import com.opensymphony.xwork2.ActionSupport;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.interceptor.RequestAware;

/**
 * Esta clase gestiona las operaciones CRUD de los objetos User de la aplicación
 */
public class UserAction extends ActionSupport implements RequestAware, Serializable {

	private ControlParams control = new ControlParams();
	private List<User> dataList = new ArrayList<>();
	private User dataEdit = new User();
	private UserService dataService;
	private Map<String, Object> requestAttributes = new HashMap<>();
	private List<Role> rolesList;
	private List<UserRole> userRolesList;
	private RolesService rolesService;
	private UserRolesService userRolesService;
	private String[] authRolesSel;
	private String[] availableRolesSel;
	private String selec1;
	private String passwordAgain;

	public UserAction() {
		super();
	}

	@Override
	public String execute() {
		return "error";
	}

	public String userNew() {
		control.setRecCount(1);
		control.setRunAction("New");
		dataEdit.setDate(new Date());
		requestAttributes.put("page", "/WEB-INF/views/UserEdit.jsp");
		return "NEW";
	}

	public String userEdit() throws Exception {
		dataEdit = dataService.getById(control.getId());
		// Se lee lista de Users
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
		control.setRunAction("Edit");
		requestAttributes.put("page", "/WEB-INF/views/UserEdit.jsp");
		return "EDIT";
	}

	public String userSaveNew() throws SQLException, Exception {

		if (dataService.insertUser(dataEdit) != 1) {
			this.addActionError(getText("UserEditErrorMsg"));
			return "NEW";
		}
		this.addActionMessage(getText("UserEditSaveOkMsg"));
		return userList();
	}

	public String userSaveEdit() throws Exception {

		if (dataService.updateUser(dataEdit) != 1) {
			this.addActionError(getText("UserEditErrorMsg"));
			return "NEW";
		}
		this.addActionMessage(getText("UserEditSaveOkMsg"));
		return "NEW";
	}

	public String userDelete() throws Exception {
		if (selec1 != null) {
			List<String> deletes = Arrays.asList(selec1.split(","));
			List<Integer> deleteIds = new ArrayList<>();
			for (String id : deletes) {
				deleteIds.add(Integer.parseInt(id));
			}
			if (dataService.deleteUserIds(deletes) == 0) {
				addActionError(getText("NoneDeletedUser"));
				return userList();
			} else {
				this.addActionMessage(getText("DeletedSelectedUserOkMsg"));
				return userList();
			}
		}
		addActionError(getText("NoneSelectedUser"));
		return userList();
	}

	public String userList() throws Exception {
		if (control.getRecCount() == 0) {
			control.setRecCount(dataService.getCountRows());
		}
		dataList = dataService.getUserList(control);
		control.setRunAction("List");
		requestAttributes.put("page", "/WEB-INF/views/UserList.jsp");
		return "LIST";
	}

	public String userNavigation() throws Exception {
		control.doNavigation();
		return userList();
	}

	@Override
	public void validate() {
		super.validate();
	}

	public ControlParams getControl() {
		return control;
	}

	public void setControl(ControlParams control) {
		this.control = control;
	}

	public User getDataEdit() {
		return dataEdit;
	}

	public void setDataEdit(User dataEdit) {
		this.dataEdit = dataEdit;
	}

	public List<User> getDataList() {
		return dataList;
	}

	public void setDataList(List<User> dataList) {
		this.dataList = dataList;
	}

	public void setDataService(UserService dataService) {
		this.dataService = dataService;
	}

	public String[] getAuthRolesSel() {
		return authRolesSel;
	}

	public void setAuthRolesSel(String[] authRolesSel) {
		this.authRolesSel = authRolesSel;
	}

	public String[] getAvailableRolesSel() {
		return availableRolesSel;
	}

	public void setAvailableRolesSel(String[] availableRolesSel) {
		this.availableRolesSel = availableRolesSel;
	}

	public String getSelec1() {
		return selec1;
	}

	public void setSelec1(String selec1) {
		this.selec1 = selec1;
	}

	public void setRolesService(RolesService rolesService) {
		this.rolesService = rolesService;
	}

	public void setUserRolesService(UserRolesService userRolesService) {
		this.userRolesService = userRolesService;
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

	@Override
	public void setRequest(Map map) {
		this.requestAttributes = map;
	}

}
