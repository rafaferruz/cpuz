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
import com.cpuz.domain.NewsPiece;
import com.cpuz.exceptions.UserException;
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
public class InitService {

	private final transient Logger log = Logger.getLogger(this.getClass());

	public InitService() {
	}

	/**
	 * Devuelve una lista de NewsPiece objects con toda la información completa,
	 * seleccionados por los parámetros pasados al método.
	 * 
	 * @return	Objeto List con las NewsPiece seleccionadas. Si no ha encontrado
	 * ninguna, devuelve una lista vacía.
	 */
	public List<NewsPiece> getWhatsNew() throws SQLException, UserException, UserException {
		List<NewsPiece> newsPieces = new ArrayList<>();
		newsPieces = new DAOFactory().getNewsPieceDAO().getCompleteNewsPieces();
		return newsPieces;
	}
}
