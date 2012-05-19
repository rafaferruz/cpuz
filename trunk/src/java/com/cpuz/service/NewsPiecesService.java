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
import com.cpuz.st2.beans.ControlParams;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class NewsPiecesService {

	private final transient Logger log = Logger.getLogger(this.getClass());
	ControlParams control = new ControlParams();

	public NewsPiecesService() {
	}

	public boolean keyIdExists(int newsPieceId) throws SQLException {
		NewsPiece newsPiece = new DAOFactory().getNewsPieceDAO().read(newsPieceId);
		return (newsPiece != null);
	}

	public List<NewsPiece> getNewsPieceList(ControlParams control) throws SQLException {

		List<NewsPiece> newsPieces = new ArrayList<>();
		newsPieces = new DAOFactory().getNewsPieceDAO().getNewsPieceList(control);
		return newsPieces;
	}

	public int getCountRows() throws SQLException {
		return new DAOFactory().getNewsPieceDAO().getCountRows();
	}

	public NewsPiece getById(int newsPieceId) throws SQLException {
		return new DAOFactory().getNewsPieceDAO().read(newsPieceId);
	}

	public int insertNewsPiece(NewsPiece newsPiece) throws SQLException {
		return new DAOFactory().getNewsPieceDAO().create(newsPiece);
	}

	public int updateNewsPiece(NewsPiece newsPiece) throws SQLException {
		return new DAOFactory().getNewsPieceDAO().update(newsPiece);
	}

	public int deleteNewsPiece(NewsPiece newsPiece) throws SQLException {
		return new DAOFactory().getNewsPieceDAO().delete(newsPiece.getId());
	}

	public int deleteNewsPieceIds(List<Integer> ids) throws SQLException {
		return new DAOFactory().getNewsPieceDAO().deleteIds(ids);
	}
}
