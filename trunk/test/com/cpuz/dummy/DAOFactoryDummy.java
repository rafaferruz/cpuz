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
package com.cpuz.dummy;

import com.cpuz.DAO.DAOFactory;

/**
 * Clase dummy de la clase DAOFactory.
 * 
 */
public class DAOFactoryDummy extends DAOFactory {
	public void startTransaction(){
		System.out.println("DAOFactoryDummy: Executing startTransaction()");
	}
	public void commit(){
		System.out.println("DAOFactoryDummy: Executing commit()");
	}
	public void rollback(Throwable ex){
		System.out.println("DAOFactoryDummy: Executing rollBack()");
	}

	public RoleDAODummy getRoleDAO() {
		return new RoleDAODummy();
	}

	public UserDAODummy getUserDAO() {
		return new UserDAODummy();
	}

	public UserRoleDAODummy getUserRoleDAO() {
		return new UserRoleDAODummy();
	}
	public SectionDAODummy getSectionDAO() {
		return new SectionDAODummy();
	}
	public NewsPieceDAODummy getNewsPieceDAO() {
		return new NewsPieceDAODummy();
	}
}
