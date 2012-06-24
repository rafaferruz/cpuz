package com.cpuz.DAO.impl;

import com.cpuz.domain.Noticia;
import com.cpuz.exceptions.UserException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;



/** NoticiaDAO es una plantilla de interface que puede servir de base para la
 * la creación de interfaces más específicos destánados a las operaciones CRUD
 * sobre tablas de una base de datos.
 * 
 * @author RAFAEL FERRUZ
 */

public interface NoticiaDAO {
    int createNoticia(Noticia rec) throws UserException  ,SQLException;
    int deleteNoticia(Noticia rec) throws UserException ,SQLException;
    int deleteNoticia(String sqlWhereClause) throws UserException ,SQLException;
    int updateNoticia(Noticia rec) throws UserException ,SQLException;
    Noticia readNoticia(String keyId) throws UserException ,SQLException, NamingException;
    Noticia[] readAllNoticias() throws UserException ,SQLException, NamingException;
    Noticia[] readAllNoticias(String sqlWhereClause) throws UserException ,SQLException, NamingException;
    void setPropertiesNoticia(Noticia rec, ResultSet rs) throws UserException;
    public boolean keyIdExists(int keyId) throws SQLException, NamingException;
}
