/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cpuz.DAO.impl;

import com.cpuz.domain.Comentario;
import com.cpuz.exceptions.UserException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author RAFAEL FERRUZ
 */
public interface ComentarioDAO {
    int createComentario(Comentario rec) throws UserException  ,SQLException;
    int deleteComentario(Comentario rec) throws UserException ,SQLException;
    int deleteComentario(String sqlWhereClause) throws UserException ,SQLException;
    int updateComentario(Comentario rec) throws UserException ,SQLException;
    Comentario readComentario(String keyId) throws UserException ,SQLException, NamingException;
    Comentario[] readAllComentarios() throws UserException ,SQLException, NamingException;
    Comentario[] readAllComentarios(String sqlWhereClause) throws UserException ,SQLException, NamingException;
    void setPropertiesComentario(Comentario rec, ResultSet rs) throws UserException;
    public boolean keyIdExists(int keyId) throws SQLException, NamingException;

}
