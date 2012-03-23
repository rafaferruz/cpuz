package com.cpuz.DAO.impl;

import com.cpuz.domain.NewsPiece;
import com.cpuz.exceptions.NewsPieceException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;



/** NewsPieceDAO es una plantilla de interface que puede servir de base para la
 * la creación de interfaces más específicos destánados a las operaciones CRUD
 * sobre tablas de una base de datos.
 * 
 * @author RAFAEL FERRUZ
 */

public interface NewsPieceDAO {
    int createNewsPiece(NewsPiece rec) throws NewsPieceException  ,SQLException;
    int deleteNewsPiece(NewsPiece rec) throws NewsPieceException ,SQLException;
    int deleteNewsPiece(String sqlWhereClause) throws NewsPieceException ,SQLException;
    int updateNewsPiece(NewsPiece rec) throws NewsPieceException ,SQLException;
    NewsPiece readNewsPiece(String keyId) throws NewsPieceException ,SQLException, NamingException;
    List<NewsPiece> readNewsPieces() throws NewsPieceException ,SQLException, NamingException;
    List<NewsPiece> readNewsPieces(String sqlClause) throws NewsPieceException ,SQLException, NamingException;
    public boolean keyIdExists(int keyId) throws SQLException, NamingException;

}
