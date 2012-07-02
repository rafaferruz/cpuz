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

import com.cpuz.DAO.SectionDAO;
import com.cpuz.domain.Section;
import com.cpuz.exceptions.UserException;
import com.cpuz.st2.beans.ControlParams;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase dummy para la ejecución tests de operaciones CRUD sobre la tabla 'sections'.
 * 
 */
public class SectionDAODummy1 extends SectionDAO {

	/*
	 * Método dummy para testear clases que accedan a SectionDAODummy.create()
	 * 
	 * Si el objeto pasado como parámetro es igual a null, devuelve una SQLException para su chequeo.
	 * Si la variable de instancia 'id' del objeto pasado es igual a null, se devuelve el valor 0 y
	 * chequeamos que no ha sido posible persistir la operación create (insert).
	 * En otro caso, la operación create (insert) ha sido correcta y devuelve el valor 1 como número de 
	 * filas añadidas a la base de datos.
	 */
	public int create(Section obj) throws SQLException, UserException {
		if (obj == null || obj.getId() == 0 || obj.getName() == null
				|| obj.getName().equals("") ) {
			throw new UserException("sectionException.nullOrEmptyField");
		}
		if (obj.getId()==-1) {
			throw new SQLException("Testing SQLException");
		}
		if (obj.getId()==-2) {
			return 0;
		}
		return 1;
	}

	/*
	 * Método dummy para testear clases que accedan a SectionDAOdummy.read()
	 */
	public Section read(int objId) throws SQLException {
		if (objId==-1) {
			throw new SQLException("Error en consulta SQL");
		}
		if (objId==-2 || objId==0) {	// Testeamos que no encuentra el registro
			return null;
		} else {
			return new Section();
		}
	}

	/*
	 * Método dummy para testear clases que accedan a SectionDAODummy.update()
	 * 
	 * Si el objeto pasado como parámetro es igual a null, devuelve una SQLException para su chequeo.
	 * Si la variable de instancia 'id' del objeto pasado es igual a null, se devuelve el valor 0 y
	 * chequeamos que no ha sido posible persistir la operación update.
	 * En otro caso, la operación update ha sido correcta y dvuelve el valor 1 como número de 
	 * filas actualizadas en la base de datos.
	 */
	public int update(Section obj) throws SQLException, UserException {
		if (obj == null || obj.getId() == 0 || obj.getName() == null
				|| obj.getName().equals("") ) {
			throw new UserException("sectionException.nullOrEmptyField");
		}
		if (obj.getId()==-1) {
			throw new SQLException("Testing SQLException");
		}
		if (obj.getId()==-2) {
			return 0;
		}
		return 1;
	}

	/*
	 * Método dummy para testear clases que accedan a SectionDAODummy.delete()
	 * 
	 * Si el objeto pasado como parámetro es igual a null, devuelve una SQLException para su chequeo.
	 * Si la variable de instancia 'id' del objeto pasado es igual a un string vacio, se devuelve el valor 0 y
	 * chequeamos que no ha sido posible realizar la operación delete.
	 * En otro caso, la operación delete ha sido correcta y devuelve el valor 1 como número de 
	 * filas eliminadas de la base de datos.
	 */
	public int delete(int objId) throws SQLException {
		if (objId==-1) {
			throw new SQLException("Error en consulta SQL");
		}
		if (objId==0) {
			return 0;
		} else {
			return 1;
		}
	}

	/*
	 * Método dummy para testear clases que accedan a SectionDAODummy.deleteIds()
	 * 
	 * Si el objeto pasado como parámetro es igual a null, devuelve una SQLException para su chequeo.
	 * Si la lista de Ids a deletear es una lista vacía, se devuelve el valor 0 y
	 * chequeamos que no ha sido posible realizar la operación delete.
	 * En otro caso, la operación delete ha sido correcta y devuelve el valor del número de
	 * elementos de la la lista como número de filas eliminadas de la base de datos.
	 */
	public int deleteIds(List<String> ids) throws SQLException {
		if (ids == null || ids.isEmpty()) {
			return 0;
		}
		if (ids.get(0).equals("")) {
			throw new SQLException("Testing SQLException");
		}
		if (ids.get(0).equals("none")) {
			return 0;
		}
		return ids.size();
	}

	public List<Section> getSectionList(ControlParams control) throws SQLException {
		if (control.getRecChunk() < 0) {
			throw new SQLException("Testing SQLException");
		}
		List<Section> sections = new ArrayList<>();
		for (int i = 0; i < control.getRecCount(); i++) {
			sections.add(new Section());
		}
		return sections;
	}
		public int getCountRows() throws SQLException {
		return 1;
	}


}
