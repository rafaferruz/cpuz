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

import com.cpuz.domain.Bug;
import com.cpuz.DAO.DAOFactory;
import com.cpuz.domain.UserType;
import com.cpuz.st2.beans.ControlParams;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Clase puente para aislar la clase Action de la clase DAO, de forma que Action
 * no utilice llamadas a la DAO sino llamadas a métodos de Model que a su vez realizarán
 * llamadas DAO.
 * 
 */
public class BugsModel {
	private final transient Logger log = Logger.getLogger(this.getClass());
	ControlParams control=new ControlParams();

    public BugsModel() {
    }

	/**
	 * Devuelve un valor booleano indicando si existe una fila con el id pasado como parámetro
	 * 
	 * @param bugId		id del bug a consultar
	 * @return			true si encuentra una fila con el id recibido, false si no lo encuentra.
	 * @throws RoleException 
	 */
    public boolean keyIdExists(Integer bugId) throws SQLException {
            Bug bug = new DAOFactory().getBugDAO().read(bugId);
		return (bug != null);
    }

	/**
	 * Proporciona un objeto List de registros de titulares de Bugs
	 * con un número de registros indicado por el parámetro recChunk y
	 * a partir del indicado por el parámetro recStart. Se toma como lista
	 * base la totalidad de titulares de Bugs ordenados por fecha empezando
	 * por el titular más reciente y continuando al más antiguo.
	 * @param recStart Nº de registro inicial
	 * @param recChunk Nº de registros a incluir en la búsqueda
	 * @return Un objeto List<Bug> con los registros seleccionados
	 */
	public List<Bug> getNewsRecords() throws SQLException  {
		control.setUserType(UserType.ANONYMOUS);
		control.setRecStart(0);
		control.setRecChunk(0);
		return this.getBugList(control);
	}

	public List<Bug> getNewsRecords(UserType userType) throws SQLException {
		control.setUserType(userType);
		control.setRecStart(0);
		control.setRecChunk(0);
		return this.getBugList(control);

	}
	public List<Bug> getBugList(ControlParams control) throws SQLException  {

		List<Bug> bugs = new ArrayList<>();
		bugs = new DAOFactory().getBugDAO().getBugList(control);
		return bugs;
	}

	public int getCountRows() throws SQLException {
		return new DAOFactory().getBugDAO().getCountRows();
	}

	public Bug getById(int bugId) throws SQLException{
		return new DAOFactory().getBugDAO().read(bugId);
	}

	public int insertBug(Bug bug) throws SQLException {
		return new DAOFactory().getBugDAO().create(bug);
	}

	public int updateBug(Bug bug) throws SQLException{
		return new DAOFactory().getBugDAO().update(bug);
	}

	public int deleteBug(Bug bug) throws SQLException {
		return new DAOFactory().getBugDAO().delete(bug.getId());
	}

	public int deleteBugIds(List<String> ids) throws SQLException {
		return new DAOFactory().getBugDAO().deleteIds(ids);
	}

}
