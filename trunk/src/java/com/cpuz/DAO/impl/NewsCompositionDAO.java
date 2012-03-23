package com.cpuz.DAO.impl;

import com.cpuz.domain.NewsComposition;
import com.cpuz.exceptions.NewsCompositionException;
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
    int createNewsComposition(NewsComposition rec) throws NewsCompositionException  ,SQLException;
    int deleteNewsComposition(NewsComposition rec) throws NewsCompositionException ,SQLException;
    int deleteNewsComposition(String sqlWhereClause) throws NewsCompositionException ,SQLException;
    int updateNewsComposition(NewsComposition rec) throws NewsCompositionException ,SQLException;
    NewsComposition readNewsComposition(String keyId) throws NewsCompositionException ,SQLException, NamingException;
    List<NewsComposition> readNewsCompositions() throws NewsCompositionException ,SQLException, NamingException;
    List<NewsComposition> readNewsCompositions(String sqlWhereClause) throws NewsCompositionException ,SQLException, NamingException;
    public boolean keyIdExists(int keyId) throws SQLException, NamingException;
}
