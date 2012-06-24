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
import com.cpuz.domain.UserType;
import com.cpuz.exceptions.UserException;
import com.cpuz.st2.beans.ControlParams;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Clase puente para aislar la clase Action de la clase DAO, de forma que Action
 * no utilice llamadas a la DAO sino llamadas a métodos de Model que a su vez realizarán
 * llamadas DAO.
 * 
 */
public class RolesService {

	private final transient Logger log = Logger.getLogger(this.getClass());
	private ControlParams control = new ControlParams();
	DAOFactory DAOFactory;

	public RolesService() {
	}

	public DAOFactory getDAOFactory() {
		return DAOFactory;
	}

	public void setDAOFactory(DAOFactory DAOFactory) {
		this.DAOFactory = DAOFactory;
	}

	/**
	 * Devuelve un valor booleano indicando si existe una fila con el id pasado como parámetro
	 * 
	 * @param rolId		id del rol a consultar
	 * @return			true si encuentra una fila con el id recibido, false si no lo encuentra.
	 * @throws UserException	Si recibe un parámetro rolId igual null 
	 */
	public boolean keyIdExists(Integer rolId) throws SQLException, UserException {
		if (rolId == null) {
			throw new UserException("roleException.rolIdNull");
		}
		return (DAOFactory.getRoleDAO().read(rolId) != null);
	}

	/**
	 * Proporciona un objeto List de registros de titulares de Roles
	 * con un número de registros indicado por el parámetro recChunk y
	 * a partir del indicado por el parámetro recStart. Se toma como lista
	 * base la totalidad de titulares de Roles ordenados por fecha empezando
	 * por el titular más reciente y continuando al más antiguo.
	 * @param recStart Nº de registro inicial
	 * @param recChunk Nº de registros a incluir en la búsqueda
	 * @return Un objeto List<Role> con los registros seleccionados
	 */
	public List<Role> getNewsRecords() throws SQLException {
		control.setUserType(UserType.ANONYMOUS);
		control.setRecStart(0);
		control.setRecChunk(0);
		return this.getRoleList(control);
	}

	/**
	 * Devuelve una List<Role> con los roles encontrados en la tabla roles con
	 * un número máximo de filas indicada por control.getRecChunk y a partir de la
	 * fila indicada por control.getRecStart. Si control.RecChunk es igual a 0,
	 * se devuelven todos los roles que haya en la tabla.
	 * 
	 * @param control		Pasa el objeto ControlParams que define variables de 
	 *						comportamiento de la consulta: Nº máximo de filas devueltas,
	 *						desde que fila hay que tomar, qué tipo de usuario accede 
	 *						(ver API de ControlParams). Si control es null, no se 
	 *						realiza ningún filtro y se devuelven todas las filas de la tabla
	 * @return				Una List<Role> de filas encontradas. Si no se encuentra ninguna
	 *						fila en la consulta, se devuelve una List<Role> vacía.
	 * @throws SQLException 
	 */
	public List<Role> getRoleList(ControlParams control) throws SQLException {
		List<Role> roles = DAOFactory.getRoleDAO().getRoleList(control);
		return roles;
	}

	public int getCountRows() throws SQLException {
		return DAOFactory.getRoleDAO().getCountRows();
	}

	public Role getById(int rolId) throws SQLException {
		return DAOFactory.getRoleDAO().read(rolId);
	}

	public int insertRole(Role role) throws SQLException, UserException {
		return DAOFactory.getRoleDAO().create(role);
	}

	public int updateRole(Role role) throws SQLException, UserException {
		return DAOFactory.getRoleDAO().update(role);
	}

	public int deleteRole(Role role) throws SQLException, UserException {
		if (role == null) {
			throw new UserException("roleException.roleNull");
		}
		if (role.getId() == null) {
			throw new UserException("roleException.rolIdNull");
		}
		return DAOFactory.getRoleDAO().delete(role.getId());
	}

	public int deleteRoleIds(List<String> ids) throws SQLException {
		return DAOFactory.getRoleDAO().deleteIds(ids);
	}

	public ControlParams getControl() {
		return control;
	}

	public void setControl(ControlParams control) {
		this.control = control;
	}
	
}
