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
import com.cpuz.domain.Role;
import com.cpuz.domain.Section;
import com.cpuz.st2.beans.ControlParams;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase dummy para la ejecución tests de operaciones CRUD sobre la tabla 'sections'.
 * 
 */
public class SectionDAODummy extends SectionDAO {

	/*
	 * Método dummy para testear clases que accedan a SectionDAODummy.create()
	 * 
	 * Si el objeto pasado como parámetro es igual a null, devuelve una SQLException para su chequeo.
	 * Si la variable de instancia 'id' del objeto pasado es igual a null, se devuelve el valor 0 y
	 * chequeamos que no ha sido posible persistir la operación create (insert).
	 * En otro caso, la operación create (insert) ha sido correcta y devuelve el valor 1 como número de 
	 * filas añadidas a la base de datos.
	 */
	public int create(Section obj) throws SQLException {
		if (obj == null) {
			throw new SQLException("Testing SQLException");
		}
		if (obj.getId() == null) {
			return 0;
		} else {
			return 1;
		}
	}

	/*
	 * Método dummy para testear clases que accedan a RoleDAOdummy.read()
	 */
	public Section read(String objId) throws SQLException {
		if (objId == null) {
			throw new SQLException("Error en consulta SQL");
		}
		if (objId.equals("")) {	// Testeamos que no encuentra el registro
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
	public int update(Section obj) throws SQLException {
		if (obj == null) {
			throw new SQLException("Testing SQLException");
		}
		if (obj.getId() == null) {
			return 0;
		} else {
			return 1;
		}
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
	public int delete(String objId) throws SQLException {
		if (objId == null) {
			throw new SQLException("Testing SQLException");
		}
		if (objId.equals("")) {
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
		if (ids == null) {
			throw new SQLException("Testing SQLException");
		}
		if (ids.isEmpty()) {
			return 0;
		} else {
			return ids.size();
		}
	}

	public List<Section> getSectionList(ControlParams control) throws SQLException {
		if (control == null) {
			throw new SQLException("Testing SQLException");
		}
		List<Section> sections = new ArrayList<>();
		for (int i = 0; i < control.getRecCount(); i++) {
			sections.add(new Section());
		}
		return sections;
	}
}
