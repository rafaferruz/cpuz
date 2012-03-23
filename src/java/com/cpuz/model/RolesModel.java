package com.cpuz.model;

import com.cpuz.DAO.impl.DAOFactory;
import com.cpuz.domain.Role;
import com.cpuz.domain.UserType;
import com.cpuz.exceptions.RoleException;
import com.cpuz.exceptions.UserException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Clase puente para aislar la clase Action de la clase DAO, de forma que Action
 * no utilice llamadas a la DAO sino llamadas a métodos de Model que a su vez realizarán
 * llamadas DAO.
 * 
 */
public class RolesModel {

	private final transient Logger log = Logger.getLogger(this.getClass());

	public RolesModel() {
	}

	/**
	 * Devuelve un valor booleano indicando si existe una fila con el id pasado como parámetro
	 * 
	 * @param rolId		id del rol a consultar
	 * @return			true si encuentra una fila con el id recibido, false si no lo encuentra.
	 * @throws RoleException 
	 */
	public boolean keyIdExists(Integer rolId) throws RoleException {
		Role role = new DAOFactory().getRoleDAO().read(rolId);
		return (role != null);
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
	public List<Role> getNewsRecords() throws RoleException {
		/* Requirement codes: E5-1 */
		return this.getRoleList(UserType.ANONYMOUS, 0, 0);
	}

	public List<Role> getNewsRecords(UserType userType) throws RoleException {
		/* Requirement codes: E5-1 */
		return this.getRoleList(userType, 0, 0);

	}

	public List<Role> getRoleList(UserType userType, int offset, int count) throws RoleException {

		List<Role> roles = new ArrayList<>();
		roles = new DAOFactory().getRoleDAO().getRoleList(userType, offset, count);
		return roles;
	}

	public Role getById(int rolId) throws UserException, RoleException {
		return new DAOFactory().getRoleDAO().read(rolId);
	}

	public int insertRole(Role role) throws RoleException {
			return new DAOFactory().getRoleDAO().create(role);
	}

	public int updateRole(Role role) throws RoleException {
			return new DAOFactory().getRoleDAO().update(role);
	}

	public int deleteRole(Role role) throws RoleException {
			return new DAOFactory().getRoleDAO().delete(role.getId());
	}

	public int deleteRoleIds(List<String> ids) throws RoleException {
		return new DAOFactory().getRoleDAO().deleteIds(ids);
	}
}
