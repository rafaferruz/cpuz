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
import com.cpuz.domain.Section;
import com.cpuz.exceptions.UserException;
import com.cpuz.st2.beans.ControlParams;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class SectionsService {

	private final transient Logger log = Logger.getLogger(this.getClass());
	ControlParams control = new ControlParams();
	DAOFactory DAOFactory;

	public SectionsService() {
	}

	public DAOFactory getDAOFactory() {
		return DAOFactory;
	}

	public void setDAOFactory(DAOFactory DAOFactory) {
		this.DAOFactory = DAOFactory;
	}

	public boolean keyIdExists(int sectionId) throws SQLException, UserException {
		if (sectionId == 0) {
			throw new UserException("sectionException.sectionIdNull");
		}
		return (DAOFactory.getSectionDAO().read(sectionId) != null);
	}

	public List<Section> getSectionList(ControlParams control) throws SQLException {
		List<Section> sections = DAOFactory.getSectionDAO().getSectionList(control);
		return sections;
	}

	public int getCountRows() throws SQLException {
		return DAOFactory.getSectionDAO().getCountRows();
	}

	public Section getById(int sectionId) throws SQLException {
		return DAOFactory.getSectionDAO().read(sectionId);
	}

	public int insertSection(Section section) throws SQLException, UserException {
		return DAOFactory.getSectionDAO().create(section);
	}

	public int updateSection(Section section) throws SQLException, UserException {
		return DAOFactory.getSectionDAO().update(section);
	}

	public int deleteSection(Section section) throws SQLException, UserException {
		if (section == null) {
			throw new UserException("sectionException.sectionNull");
		}
		return DAOFactory.getSectionDAO().delete(section.getId());
	}

	public int deleteSectionIds(List<String> ids) throws SQLException {
		return DAOFactory.getSectionDAO().deleteIds(ids);
	}

	public ControlParams getControl() {
		return control;
	}

	public void setControl(ControlParams control) {
		this.control = control;
	}

	public List<Section> getSectionsNoGroup() throws SQLException {
		List<Section> sections = DAOFactory.getSectionDAO().getSectionsNoGroup();
		return sections;
	}

}
