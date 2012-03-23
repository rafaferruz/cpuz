/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.model;

import com.cpuz.domain.NewsComposition;
import com.cpuz.DAO.impl.NewsCompositionDAOImpl;
import com.cpuz.domain.UserType;
import com.cpuz.exceptions.NewsCompositionException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class NewsCompositionsModel {

	private int npiId;

	public NewsCompositionsModel() {
	}

	public boolean keyIdExists(Integer ssn) {
		try {
			NewsCompositionDAOImpl nDao = new NewsCompositionDAOImpl();
			return nDao.keyIdExists(ssn);

		} catch (Exception ex) {
			return false;
		}
	}

	/**
	 * Proporciona un objeto List de registros de titulares de NewsCompositions
	 * con un número de registros indicado por el parámetro recChunk y
	 * a partir del indicado por el parámetro recStart. Se toma como lista
	 * base la totalidad de titulares de NewsCompositions ordenados por fecha empezando
	 * por el titular más reciente y continuando al más antiguo.
	 * @param recStart N� de registro inicial
	 * @param recChunk N� de registros a incluir en la b�squeda
	 * @return Un objeto List<NewsComposition> con los registros seleccionados
	 */
	public List<NewsComposition> getNewsRecords() {
		/* Requirement codes: E5-1 */
		return this.getNewsRecords(UserType.ANONYMOUS, "");
	}

	public List<NewsComposition> getNewsRecords(UserType userType) {
		/* Requirement codes: E5-1 */
		return this.getNewsRecords(userType, "");

	}

	public List<NewsComposition> getNewsRecords(UserType userType, String selectionClause) {

		String sqlWhereClause = "";
		List<NewsComposition> news = new ArrayList<NewsComposition>();
		try {
			NewsCompositionDAOImpl nDao = new NewsCompositionDAOImpl();
			/* Requirement codes: E5-1 */
			/*            if (userType != UserType.ADMIN) {
			sqlWhereClause = " nco_status = 2 ";
			}
			 *  */
			if (!selectionClause.equals("")) {
				if (!sqlWhereClause.equals("")) {
					sqlWhereClause += " AND (" + selectionClause + ") ";
				} else {
					sqlWhereClause = " (" + selectionClause + ") ";
				}
			}
			if (!sqlWhereClause.equals("")) {
				sqlWhereClause = " WHERE " + sqlWhereClause;
			}

			sqlWhereClause = "SELECT * FROM newscomposition " + sqlWhereClause
					+ " ORDER BY nco_composition_id DESC,nco_order ";
			for (NewsComposition n : nDao.readNewsCompositions(sqlWhereClause)) {
				news.add(n);
			}
		} catch (NewsCompositionException ex) {
			Logger.getLogger(NewsCompositionsModel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(NewsCompositionsModel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception ex) {
			Logger.getLogger(NewsCompositionsModel.class.getName()).log(Level.SEVERE, null, ex);
		}
		return news;
	}

	public List<NewsComposition> getCompositionItems() {
		List<NewsComposition> news = new ArrayList<>();
		try {
			NewsCompositionDAOImpl nDao = new NewsCompositionDAOImpl();
			for (NewsComposition n : nDao.readCompositionItems(this.getNpiId())) {
				news.add(n);
			}
		} catch (NewsCompositionException ex) {
			Logger.getLogger(NewsCompositionsModel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(NewsCompositionsModel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception ex) {
			Logger.getLogger(NewsCompositionsModel.class.getName()).log(Level.SEVERE, null, ex);
		}
		return news;
	}

	public List<NewsComposition> getRecords(String selectClause, String whereClause, String orderClause) {

		String sqlClause = "";
		List<NewsComposition> records = new ArrayList<NewsComposition>();
		try {
			NewsCompositionDAOImpl nDao = new NewsCompositionDAOImpl();
			if (selectClause == null || selectClause.equals("")) {
				sqlClause = "SELECT * FROM newscomposition ORDER BY inb_date DESC, inb_id DESC";
			} else {
				sqlClause = selectClause + " " + whereClause + " " + orderClause;
			}

			records = nDao.readNewsCompositions(sqlClause);

		} catch (NewsCompositionException ex) {
			Logger.getLogger(NewsCompositionsModel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(NewsCompositionsModel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception ex) {
			Logger.getLogger(NewsCompositionsModel.class.getName()).log(Level.SEVERE, null, ex);
		}
		return records;
	}

	public List<NewsComposition> getNewsDetails(String idNews) {

		List<NewsComposition> news = new ArrayList<NewsComposition>();
		try {
			NewsCompositionDAOImpl nDao = new NewsCompositionDAOImpl();
			String sqlWhereClause = "WHERE nco_user = '" + idNews + "'"
					+ " OR nco_id= '" + idNews + "'"
					+ " ORDER BY nco_composition_id, nco_order";
			sqlWhereClause = "SELECT * FROM newscomposition " + sqlWhereClause;
			for (NewsComposition n : nDao.readNewsCompositions(sqlWhereClause)) {
				news.add(n);
			}
		} catch (NewsCompositionException ex) {
			Logger.getLogger(NewsCompositionsModel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(NewsCompositionsModel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception ex) {
			Logger.getLogger(NewsCompositionsModel.class.getName()).log(Level.SEVERE, null, ex);
		}
		return news;
	}

	public synchronized int setNewRecord(NewsComposition news) {

		try {
			NewsCompositionDAOImpl nDao = new NewsCompositionDAOImpl();
			return nDao.createNewsComposition(news);
		} catch (NewsCompositionException ex) {
			Logger.getLogger(NewsCompositionsModel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(NewsCompositionsModel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception ex) {
			Logger.getLogger(NewsCompositionsModel.class.getName()).log(Level.SEVERE, null, ex);
		}
		return -1;
	}

	public synchronized int setUpdateRecord(NewsComposition news) {

		try {
			NewsCompositionDAOImpl nDao = new NewsCompositionDAOImpl();
			return nDao.updateNewsComposition(news);
		} catch (NewsCompositionException ex) {
			Logger.getLogger(NewsCompositionsModel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(NewsCompositionsModel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception ex) {
			Logger.getLogger(NewsCompositionsModel.class.getName()).log(Level.SEVERE, null, ex);
		}
		return -1;
	}

	public synchronized int deleteNews(NewsComposition news) {

		try {
			NewsCompositionDAOImpl nDao = new NewsCompositionDAOImpl();
			return nDao.deleteNewsComposition(news);
		} catch (NewsCompositionException ex) {
			Logger.getLogger(NewsCompositionsModel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(NewsCompositionsModel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception ex) {
			Logger.getLogger(NewsCompositionsModel.class.getName()).log(Level.SEVERE, null, ex);
		}
		return -1;
	}

	public synchronized int deleteNews(String sqlNewsComposition) {

		try {
			NewsCompositionDAOImpl nDao = new NewsCompositionDAOImpl();
			return nDao.deleteNewsComposition(sqlNewsComposition);
		} catch (NewsCompositionException ex) {
			Logger.getLogger(NewsCompositionsModel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(NewsCompositionsModel.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception ex) {
			Logger.getLogger(NewsCompositionsModel.class.getName()).log(Level.SEVERE, null, ex);
		}
		return -1;
	}

	public int getNpiId() {
		return npiId;
	}

	public void setNpiId(int npiId) {
		this.npiId = npiId;
	}
}
