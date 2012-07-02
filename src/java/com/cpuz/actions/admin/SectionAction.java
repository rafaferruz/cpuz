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

import com.cpuz.domain.Section;
import com.cpuz.domain.UserType;
import com.cpuz.exceptions.UserException;
import com.cpuz.service.SectionsService;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Clase Action de Struts usada como Controller en el patrón MVC
 * 
 */
public class SectionAction extends GenericAction<Section> {

	private SectionsService dataService;

	public SectionAction() {
		super();
		dataEdit = new Section();

	}

	public String objectNew() {
		control.setRecCount(1);
		control.setRunAction("New");
		requestAttributes.put("page", "/WEB-INF/views/sectionEdit.jsp");
		return "new";
	}

	public String objectEdit() throws SQLException {
		dataEdit = dataService.getById(control.getId());
		if (dataEdit == null) {
			return objectList();
		}
		control.setRunAction("Edit");
		requestAttributes.put("page", "/WEB-INF/views/sectionEdit.jsp");
		return "edit";
	}

	public String objectSaveNew() throws SQLException, UserException {
		if (dataService.insertSection(dataEdit) == 1) {
			this.addActionMessage(getText("SectionEditSaveOkMsg"));
			return objectList();
		}
		return "edit";
	}

	public String objectSaveEdit() throws SQLException, UserException {
		if (dataService.keyIdExists(dataEdit.getId())) {
			try {
				dataService.updateSection(dataEdit);
				this.addActionMessage(getText("SectionEditSaveOkMsg"));
			} catch (SQLException ex) {
				this.addActionError(getText("SectionEditErrorMsg"));
				return "edit";
			}
			return objectList();
		}
		return "new";
	}

	public String objectDelete() throws SQLException {
		if (selec1 != null) {
			String[] deletes = selec1.split(",");
			if (dataService.deleteSectionIds(Arrays.asList(deletes)) > 0) {
				addActionMessage(getText("SuccessDeletedSections"));
			} else {
				addActionError(getText("NoneDeletedSection"));
			}
			return objectList();
		}
		addActionError(getText("NoneSelectedSection"));
		return objectList();
	}

	public String objectList() throws SQLException {
		if (control.getRecCount() == 0) {
			control.setRecCount(dataService.getCountRows());
		}
		control.setUserType(UserType.ADMIN);
		dataList = dataService.getSectionList(control);
		control.setRunAction("List");
		requestAttributes.put("page", "/WEB-INF/views/sectionList.jsp");
		return "list";
	}

	public SectionsService getDataService() {
		return dataService;
	}

	public void setDataService(Object dataService) {
		this.dataService = (SectionsService) dataService;
	}
}
