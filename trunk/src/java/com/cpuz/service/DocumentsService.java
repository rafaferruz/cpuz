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
import com.cpuz.domain.Document;
import com.cpuz.st2.beans.ControlParams;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class DocumentsService {

	private final transient Logger log = Logger.getLogger(this.getClass());
	ControlParams control=new ControlParams();

    public DocumentsService() {
    }
    public boolean keyIdExists(int documentId) throws SQLException {
            Document document = new DAOFactory().getDocumentDAO().read(documentId);
		return (document != null);
    }


	public List<Document> getDocumentList(ControlParams control) throws SQLException  {

		List<Document> documents = new ArrayList<>();
		documents = new DAOFactory().getDocumentDAO().getDocumentList(control);
		return documents;
	}

	public int getCountRows() throws SQLException {
		return new DAOFactory().getDocumentDAO().getCountRows();
	}

	public Document getById(int documentId) throws SQLException{
		return new DAOFactory().getDocumentDAO().read(documentId);
	}

	public int insertDocument(Document document) throws SQLException {
		return new DAOFactory().getDocumentDAO().create(document);
	}

	public int updateDocument(Document document) throws SQLException{
		return new DAOFactory().getDocumentDAO().update(document);
	}

	public int deleteDocument(Document document) throws SQLException {
		return new DAOFactory().getDocumentDAO().delete(document.getId());
	}

	public int deleteDocumentIds(List<Integer> ids) throws SQLException {
		return new DAOFactory().getDocumentDAO().deleteIds(ids);
	}
}
