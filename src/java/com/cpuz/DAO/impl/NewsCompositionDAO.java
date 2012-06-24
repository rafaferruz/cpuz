package com.cpuz.DAO.impl;

import com.cpuz.domain.NewsComposition;
import com.cpuz.exceptions.UserException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;



/** NewsCompositionDAO es una plantilla de interface que puede servir de base para la
 * la creación de interfaces más específicos destánados a las operaciones CRUD
 * sobre tablas de una base de datos.
 * 
 * @author RAFAEL FERRUZ
 */

public interface NewsCompositionDAO {
    int createNewsComposition(NewsComposition rec) throws UserException  ,SQLException;
    int deleteNewsComposition(NewsComposition rec) throws UserException ,SQLException;
    int deleteNewsComposition(String sqlWhereClause) throws UserException ,SQLException;
    int updateNewsComposition(NewsComposition rec) throws UserException ,SQLException;
    NewsComposition readNewsComposition(String keyId) throws UserException ,SQLException, NamingException;
    List<NewsComposition> readNewsCompositions() throws UserException ,SQLException, NamingException;
    List<NewsComposition> readNewsCompositions(String sqlWhereClause) throws UserException ,SQLException, NamingException;
    public boolean keyIdExists(int keyId) throws SQLException, NamingException;
}
