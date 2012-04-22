/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.service;

import com.cpuz.domain.NewsPiece;
import com.cpuz.DAO.impl.NewsPieceDAOImpl;
import com.cpuz.domain.NewsPieceScopeType;
import com.cpuz.domain.NewsPieceStatusType;
import com.cpuz.exceptions.NewsPieceException;
import com.cpuz.domain.UserType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class NewsPiecesModel {

	List<Integer> statusList = new ArrayList();
	List<Integer> scopeList = new ArrayList();
	Date fromDate;
	Date toDate;

	public NewsPiecesModel() {
	}

	public boolean keyIdExists(Integer ssn) {
		try {
			NewsPieceDAOImpl nDao = new NewsPieceDAOImpl();
			return nDao.keyIdExists(ssn);

		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Proporciona un objeto List de registros de titulares de NewsPieces
	 * con un número de registros indicado por el parámetro recChunk y
	 * a partir del indicado por el parámetro recStart. Se toma como lista
	 * base la totalidad de titulares de NewsPieces ordenados por fecha empezando
	 * por el titular más reciente y continuando al más antiguo.
	 * 
	 * @param recStart N� de registro inicial
	 * @param recChunk N� de registros a incluir en la b�squeda
	 * @return Un objeto List<NewsPiece> con los registros seleccionados
	 */
	public List<NewsPiece> getNewsRecords() {
		/* Requirement codes: E5-1 */
		return this.getNewsRecords(UserType.ANONYMOUS, "");
	}

	public List<NewsPiece> getNewsRecords(UserType userType) {
		/* Requirement codes: E5-1 */
		return this.getNewsRecords(userType, "");

	}

	public List<NewsPiece> getNewsRecords(UserType userType, String selectionClause) {
		if (userType != UserType.ADMIN) {
			setInStatusList(2);
		}
		return getNewsPieces(getStatusList(), getScopeList(), getFromDate(), getToDate());
	}

	public List<NewsPiece> getRecentNews() {
		List<NewsPiece> recentNews=getNewsPieces(statusList,scopeList,null,null);
		return recentNews;
	}
	public List<NewsPiece> getNewsPieces(List<Integer> statusList, List<Integer> scopeList,
			Date fromDate, Date toDate) {
		// Se estáblecen condiciones por defecto
		if (statusList.isEmpty()) {
			statusList.add(NewsPieceStatusType.AUTHORIZED.getId());
		}
		if (scopeList.isEmpty()) {
			scopeList.add(NewsPieceScopeType.GLOBAL.getId());
		}
		try {
			NewsPieceDAOImpl nDao = new NewsPieceDAOImpl();
			return nDao.readNewsPieces(statusList, scopeList, fromDate, toDate);
		} catch (NewsPieceException ex) {
			Logger.getLogger(NewsPiecesModel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(NewsPiecesModel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception ex) {
			Logger.getLogger(NewsPiecesModel.class.getName()).log(Level.SEVERE, null, ex);
		}
		return new ArrayList<>();
	}

	public List<NewsPiece> getRecords(String selectClause, String whereClause, String orderClause) {

		String sqlClause = "";
		List<NewsPiece> records = new ArrayList<>();
		try {
			NewsPieceDAOImpl nDao = new NewsPieceDAOImpl();
			if (selectClause == null || selectClause.equals("")) {
				sqlClause = "SELECT * FROM newspieces ORDER BY npi_date DESC, npi_id DESC";
			} else {
				sqlClause = selectClause + " " + whereClause + " " + orderClause;
			}

			records = nDao.readNewsPieces(sqlClause);

		} catch (NewsPieceException ex) {
			Logger.getLogger(NewsPiecesModel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(NewsPiecesModel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception ex) {
			Logger.getLogger(NewsPiecesModel.class.getName()).log(Level.SEVERE, null, ex);
		}
		return records;
	}

	public List<NewsPiece> getNewsDetails(String idNews) {

		List<NewsPiece> news = new ArrayList<>();
		try {
			NewsPieceDAOImpl nDao = new NewsPieceDAOImpl();
			String sqlWhereClause = "WHERE npi_user = '" + idNews + "'"
					+ " OR npi_id= '" + idNews + "'"
					+ " ORDER BY npi_date DESC, npi_id";
			for (NewsPiece n : nDao.readNewsPieces(sqlWhereClause)) {
				news.add(n);
			}
		} catch (NewsPieceException ex) {
			Logger.getLogger(NewsPiecesModel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(NewsPiecesModel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception ex) {
			Logger.getLogger(NewsPiecesModel.class.getName()).log(Level.SEVERE, null, ex);
		}
		return news;
	}

	public synchronized int setNewRecord(NewsPiece news) {

		try {
			NewsPieceDAOImpl nDao = new NewsPieceDAOImpl();
			return nDao.createNewsPiece(news);
		} catch (NewsPieceException ex) {
			Logger.getLogger(NewsPiecesModel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(NewsPiecesModel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception ex) {
			Logger.getLogger(NewsPiecesModel.class.getName()).log(Level.SEVERE, null, ex);
		}
		return -1;
	}

	public synchronized int setUpdateRecord(NewsPiece news) {

		try {
			NewsPieceDAOImpl nDao = new NewsPieceDAOImpl();
			return nDao.updateNewsPiece(news);
		} catch (NewsPieceException ex) {
			Logger.getLogger(NewsPiecesModel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(NewsPiecesModel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception ex) {
			Logger.getLogger(NewsPiecesModel.class.getName()).log(Level.SEVERE, null, ex);
		}
		return -1;
	}

	public synchronized int deleteNews(NewsPiece news) {

		try {
			NewsPieceDAOImpl nDao = new NewsPieceDAOImpl();
			return nDao.deleteNewsPiece(news);
		} catch (NewsPieceException ex) {
			Logger.getLogger(NewsPiecesModel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(NewsPiecesModel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception ex) {
			Logger.getLogger(NewsPiecesModel.class.getName()).log(Level.SEVERE, null, ex);
		}
		return -1;
	}

	public List<Integer> getScopeList() {
		return scopeList;
	}

	public void setInScopeList(Integer scope) {
		this.scopeList.add(scope);
	}

	public List<Integer> getStatusList() {
		return statusList;
	}

	public void setInStatusList(Integer status) {
		this.statusList.add(status);
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
}
