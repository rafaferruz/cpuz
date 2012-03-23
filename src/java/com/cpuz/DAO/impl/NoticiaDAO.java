package com.cpuz.DAO.impl;

import com.cpuz.domain.Noticia;
import com.cpuz.exceptions.NoticiaException;
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
    int createNoticia(Noticia rec) throws NoticiaException  ,SQLException;
    int deleteNoticia(Noticia rec) throws NoticiaException ,SQLException;
    int deleteNoticia(String sqlWhereClause) throws NoticiaException ,SQLException;
    int updateNoticia(Noticia rec) throws NoticiaException ,SQLException;
    Noticia readNoticia(String keyId) throws NoticiaException ,SQLException, NamingException;
    Noticia[] readAllNoticias() throws NoticiaException ,SQLException, NamingException;
    Noticia[] readAllNoticias(String sqlWhereClause) throws NoticiaException ,SQLException, NamingException;
    void setPropertiesNoticia(Noticia rec, ResultSet rs) throws NoticiaException;
    public boolean keyIdExists(int keyId) throws SQLException, NamingException;
}
