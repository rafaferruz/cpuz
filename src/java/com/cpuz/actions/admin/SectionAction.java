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
import com.cpuz.domain.Section;
import com.cpuz.service.RolesService;
import com.cpuz.service.SectionsService;
import com.cpuz.st2.beans.ControlParams;
import com.opensymphony.xwork2.ActionSupport;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.interceptor.RequestAware;

/**
 * Clase Action de Struts usada como Controller en el patrón MVC
 * 
 */
public class SectionAction extends ActionSupport implements RequestAware, Serializable {

	private ControlParams control = new ControlParams();
	private List<Section> dataList = new ArrayList<>();
	private Section dataEdit = new Section();
	private SectionsService dataService;
	private Map<Integer, String> mapStatus = new HashMap<>();
	private Map<String, Object> requestAttributes = new HashMap<>();
	private List<Role> availableRolesList;
	private List<String> authRolesList;
	private RolesService rolesService;
	private String authRolesSel;
	private String[] availableRolesSel;
	private String selec1;

	public SectionAction() {
		super();
	}

	@Override
	public String execute() throws Exception {
		return "error";
	}

	public String sectionNew() throws Exception {
		availableRolesList = rolesService.getNewsRecords();
		control.setRecCount(1);
		control.setRunAction("New");
		requestAttributes.put("page", "/WEB-INF/views/SectionEdit.jsp");
		return "new";
	}

	public String sectionEdit() throws SQLException{
//FIXME: Se debe cambiar la clave de la section a tipo int		dataEdit = dataService.getById(control.getId().toString());
		// Se lee lista de Roles
		availableRolesList = rolesService.getNewsRecords();

		authRolesList.clear();
		if (dataEdit.getAuthorizedRoles() != null
				&& !dataEdit.getAuthorizedRoles().isEmpty()) {
			for (String role : dataEdit.getAuthorizedRoles().split(",")) {
				if (!role.equals("")) {
					authRolesList.add(role);
					availableRolesList.remove(new Role(role));
				}
			}
		}
		control.setRunAction("Edit");
		requestAttributes.put("page", "/WEB-INF/views/SectionEdit.jsp");
		return "edit";
	}
	public String sectionSaveNew() throws Exception {
		dataEdit.setAuthorizedRoles(authRolesSel.replaceAll(" ", ""));
		if (dataService.insertSection(dataEdit) == 1) {
			this.addActionMessage(getText("SectionEditSaveOkMsg"));
			return sectionList();
		}
		return "edit";
	}

	public String sectionSaveEdit() throws Exception {

		dataEdit.setAuthorizedRoles(authRolesSel.replaceAll(" ", ""));
		if (dataService.keyIdExists(dataEdit.getId().toString())) {
			try {
				dataService.updateSection(dataEdit);
				this.addActionMessage(getText("SectionEditSaveOkMsg"));
			} catch (Exception ex) {
				this.addActionError(getText("SectionEditErrorMsg"));
				return "edit";
			}
			return sectionList();
		}
		return "new";
	}
	public String bugDelete() throws Exception {
		if (selec1 != null) {
			String[] deletes = selec1.split(",");
			if (dataService.deleteSectionIds(Arrays.asList(deletes)) > 0) {
				addActionMessage(getText("SuccessDeletedBugs"));
			} else {
				addActionError(getText("NoneDeletedBug"));
			}
			return sectionList();
		}
		addActionError(getText("NoneSelectedRole"));
		return sectionList();
	}

	public String sectionList() throws Exception {
		if (control.getRecCount() == 0) {
			control.setRecCount(dataService.getCountRows());
		}
		dataList = dataService.getSectionList(control);
		control.setRunAction("List");
		requestAttributes.put("page", "/WEB-INF/views/sectionList.jsp");
		return "list";
	}


	public String SectionNavigation() throws Exception {
		control.doNavigation();
		return sectionList();
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

	public Map<Integer, String> getMapStatus() {
		return mapStatus;
	}

	public void setMapStatus(HashMap<Integer, String> mapStatus) {
		this.mapStatus = mapStatus;
	}

	public Section getDataEdit() {
		return dataEdit;
	}

	public void setDataEdit(Section dataEdit) {
		this.dataEdit = dataEdit;
	}

	public List<Section> getDataList() {
		return dataList;
	}

	public void setDataList(List<Section> dataList) {
		this.dataList = dataList;
	}

	public void setDataService(SectionsService dataService) {
		this.dataService = dataService;
	}

	public void setAuthRolesList(List<String> authRolesList) {
		this.authRolesList = authRolesList;
	}

	public void setAvailableRolesList(List<Role> availableRolesList) {
		this.availableRolesList = availableRolesList;
	}

	public void setRolesService(RolesService rolesService) {
		this.rolesService = rolesService;
	}

	public String getAuthRolesSel() {
		return authRolesSel;
	}

	public void setAuthRolesSel(String authRolesSel) {
		this.authRolesSel = authRolesSel;
	}

	public String[] getAvailableRolesSel() {
		return availableRolesSel;
	}

	public void setAvailableRolesSel(String[] availableRolesSel) {
		this.availableRolesSel = availableRolesSel;
	}

	public List<String> getAuthRolesList() {
		return authRolesList;
	}

	public List<Role> getAvailableRolesList() {
		return availableRolesList;
	}

	public String getSelec1() {
		return selec1;
	}

	public void setSelec1(String selec1) {
		this.selec1 = selec1;
	}

	public void initMapStatus() {
		//Prepara tipos de status para radio element
		mapStatus.put(0, this.getText("received"));
		mapStatus.put(1, this.getText("waiting"));
		mapStatus.put(2, this.getText("authorized"));
	}

	public void setRequest(Map map) {
		this.requestAttributes = map;
	}
}
