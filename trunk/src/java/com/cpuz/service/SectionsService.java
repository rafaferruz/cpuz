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
import com.cpuz.st2.beans.ControlParams;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class SectionsService {
	private final transient Logger log = Logger.getLogger(this.getClass());
	ControlParams control=new ControlParams();

    public SectionsService() {
    }
    public boolean keyIdExists(String sectionId) throws SQLException {
            Section section = new DAOFactory().getSectionDAO().read(sectionId);
		return (section != null);
    }

	public List<Section> getSectionList(ControlParams control) throws SQLException  {

		List<Section> sections = new ArrayList<>();
		sections = new DAOFactory().getSectionDAO().getSectionList(control);
		return sections;
	}

	public int getCountRows() throws SQLException {
		return new DAOFactory().getSectionDAO().getCountRows();
	}

	public Section getById(String sectionId) throws SQLException{
		return new DAOFactory().getSectionDAO().read(sectionId);
	}

	public int insertSection(Section section) throws SQLException {
		return new DAOFactory().getSectionDAO().create(section);
	}

	public int updateSection(Section section) throws SQLException{
		return new DAOFactory().getSectionDAO().update(section);
	}

	public int deleteSection(Section section) throws SQLException {
		return new DAOFactory().getSectionDAO().delete(section.getId());
	}

	public int deleteSectionIds(List<String> ids) throws SQLException {
		return new DAOFactory().getSectionDAO().deleteIds(ids);
	}
}





