package com.cpuz.service;

import com.cpuz.DAO.DAOFactory;
import com.cpuz.domain.NewsPiece;
import com.cpuz.exceptions.NewsCompositionException;
import com.cpuz.exceptions.NewsPieceException;
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
public class InitModel {

	private final transient Logger log = Logger.getLogger(this.getClass());

	public InitModel() {
	}

	/**
	 * Devuelve una lista de NewsPiece objects con toda la información completa,
	 * seleccionados por los parámetros pasados al método.
	 * 
	 * @return	Objeto List con las NewsPiece seleccionadas. Si no ha encontrado
	 * ninguna, devuelve una lista vacía.
	 */
	public List<NewsPiece> getWhatsNew() throws SQLException, NewsPieceException, NewsCompositionException {
		List<NewsPiece> newsPieces = new ArrayList<>();
		newsPieces = new DAOFactory().getNewsPieceDAOImpl().getCompleteNewsPieces();
		return newsPieces;
	}
}
