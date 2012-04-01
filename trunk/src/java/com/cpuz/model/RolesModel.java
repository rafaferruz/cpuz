package com.cpuz.model;

import com.cpuz.DAO.DAOFactory;
import com.cpuz.domain.Role;
import com.cpuz.domain.UserType;
import com.cpuz.exceptions.RoleException;
import com.cpuz.exceptions.UserException;
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
public class RolesModel {

	private final transient Logger log = Logger.getLogger(this.getClass());
	ControlParams control=new ControlParams();

	public RolesModel() {
	}

	/**
	 * Devuelve un valor booleano indicando si existe una fila con el id pasado como parámetro
	 * 
	 * @param rolId		id del rol a consultar
	 * @return			true si encuentra una fila con el id recibido, false si no lo encuentra.
	 * @throws RoleException 
	 */
	public boolean keyIdExists(Integer rolId) throws SQLException {
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
	public List<Role> getNewsRecords() throws SQLException  {
		control.setUserType(UserType.ANONYMOUS);
		control.setRecStart(0);
		control.setRecChunk(0);
		return this.getRoleList(control);
	}

	public List<Role> getNewsRecords(UserType userType) throws SQLException {
		control.setUserType(userType);
		control.setRecStart(0);
		control.setRecChunk(0);
		return this.getRoleList(control);

	}

	public List<Role> getRoleList(ControlParams control) throws SQLException  {

		List<Role> roles = new ArrayList<>();
		roles = new DAOFactory().getRoleDAO().getRoleList(control);
		return roles;
	}

	public int getCountRows() {
		return new DAOFactory().getRoleDAO().getCountRows();
	}

	public Role getById(int rolId) throws SQLException{
		return new DAOFactory().getRoleDAO().read(rolId);
	}

	public int insertRole(Role role) throws SQLException {
		return new DAOFactory().getRoleDAO().create(role);
	}

	public int updateRole(Role role) throws SQLException{
		return new DAOFactory().getRoleDAO().update(role);
	}

	public int deleteRole(Role role) throws SQLException {
		return new DAOFactory().getRoleDAO().delete(role.getId());
	}

	public int deleteRoleIds(List<String> ids) throws SQLException {
		return new DAOFactory().getRoleDAO().deleteIds(ids);
	}
}
