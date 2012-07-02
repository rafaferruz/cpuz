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
 * You should have newsPieceeived a copy of the GNU General Public License
 * along with CPUZ.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.cpuz.dummy;

import com.cpuz.DAO.*;
import com.cpuz.domain.NewsPiece;
import com.cpuz.exceptions.UserException;
import com.cpuz.st2.beans.ControlParams;
import java.sql.*;
import java.util.*;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class NewsPieceDAODummy extends NewsPieceDAO {

	/**
	 * Método dummy para testear clases que accedan a NewsPieceDAODummy.create()
	 * 
	 * Si el objeto pasado como parámetro es igual a null, devuelve una SQLException para su chequeo.
	 * Si la variable de instancia 'id' del objeto pasado es igual a null, se devuelve el valor 0 y
	 * chequeamos que no ha sido posible persistir la operación create (insert).
	 * En otro caso, la operación create (insert) ha sido correcta y devuelve el valor 1 como número de 
	 * filas añadidas a la base de datos.
	 */
	public synchronized int create(NewsPiece obj) throws UserException, SQLException {

		if (obj == null || obj.getId() == 0 || obj.getDescription() == null
				|| obj.getDescription().equals("")) {
			throw new UserException("newsPieceException.nullOrEmptyField");
		}
		if (obj.getId() == -1) {
			throw new SQLException("Testing SQLException");
		}
		if (obj.getId() == -2) {
			return 0;
		}
		return 1;
	}

	/*
	 * Método dummy para testear clases que accedan a NewsPieceDAOdummy.read()
	 */
	public NewsPiece read(int objId) throws SQLException {
		if (objId == -1) {
			throw new SQLException("Error en consulta SQL");
		}
		if (objId == -2 || objId == 0) {	// Testeamos que no encuentra el registro
			return null;
		} else {
			return new NewsPiece();
		}
	}

	/*
	 * Método dummy para testear clases que accedan a NewsPieceDAODummy.update()
	 * 
	 * Si el objeto pasado como parámetro es igual a null, devuelve una SQLException para su chequeo.
	 * Si la variable de instancia 'id' del objeto pasado es igual a null, se devuelve el valor 0 y
	 * chequeamos que no ha sido posible persistir la operación update.
	 * En otro caso, la operación update ha sido correcta y dvuelve el valor 1 como número de 
	 * filas actualizadas en la base de datos.
	 */
	public int update(NewsPiece obj) throws SQLException, UserException {
		if (obj == null || obj.getId() == 0 || obj.getDescription() == null
				|| obj.getDescription().equals("")) {
			throw new UserException("sectionException.nullOrEmptyField");
		}
		if (obj.getId() == -1) {
			throw new SQLException("Testing SQLException");
		}
		if (obj.getId() == -2) {
			return 0;
		}
		return 1;
	}

	/*
	 * Método dummy para testear clases que accedan a NewsPieceDAODummy.delete()
	 * 
	 * Si el objeto pasado como parámetro es igual a null, devuelve una SQLException para su chequeo.
	 * Si la variable de instancia 'id' del objeto pasado es igual a un string vacio, se devuelve el valor 0 y
	 * chequeamos que no ha sido posible realizar la operación delete.
	 * En otro caso, la operación delete ha sido correcta y devuelve el valor 1 como número de 
	 * filas eliminadas de la base de datos.
	 */
	public int delete(int objId) throws SQLException {
		if (objId == -1) {
			throw new SQLException("Error en consulta SQL");
		}
		if (objId == 0) {
			return 0;
		} else {
			return 1;
		}
	}
	/*
	 * Método dummy para testear clases que accedan a NewsPieceDAODummy.deleteIds()
	 * 
	 * Si el objeto pasado como parámetro es igual a null, devuelve una SQLException para su chequeo.
	 * Si la lista de Ids a deletear es una lista vacía, se devuelve el valor 0 y
	 * chequeamos que no ha sido posible realizar la operación delete.
	 * En otro caso, la operación delete ha sido correcta y devuelve el valor del número de
	 * elementos de la la lista como número de filas eliminadas de la base de datos.
	 */

	public int deleteIds(List<Integer> ids) throws SQLException {
		if (ids == null || ids.isEmpty()) {
			return 0;
		}
		if (ids.get(0) == -1) {
			throw new SQLException("Testing SQLException");
		}
		if (ids.get(0) == 0) {
			return 0;
		}
		return ids.size();
	}

	public List<NewsPiece> getNewsPieceList(ControlParams control) throws SQLException {
		if (control.getRecChunk() < 0) {
			throw new SQLException("Testing SQLException");
		}
		List<NewsPiece> newsPieces = new ArrayList<>();
		for (int i = 0; i < control.getRecCount(); i++) {
			newsPieces.add(new NewsPiece());
		}
		return newsPieces;
	}

	public int getCountRows() throws SQLException {
		return 1;
	}
}
