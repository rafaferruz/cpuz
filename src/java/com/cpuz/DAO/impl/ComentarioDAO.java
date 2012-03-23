/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cpuz.DAO.impl;

import com.cpuz.domain.Comentario;
import com.cpuz.exceptions.ComentarioException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author RAFAEL FERRUZ
 */
public interface ComentarioDAO {
    int createComentario(Comentario rec) throws ComentarioException  ,SQLException;
    int deleteComentario(Comentario rec) throws ComentarioException ,SQLException;
    int deleteComentario(String sqlWhereClause) throws ComentarioException ,SQLException;
    int updateComentario(Comentario rec) throws ComentarioException ,SQLException;
    Comentario readComentario(String keyId) throws ComentarioException ,SQLException, NamingException;
    Comentario[] readAllComentarios() throws ComentarioException ,SQLException, NamingException;
    Comentario[] readAllComentarios(String sqlWhereClause) throws ComentarioException ,SQLException, NamingException;
    void setPropertiesComentario(Comentario rec, ResultSet rs) throws ComentarioException;
    public boolean keyIdExists(int keyId) throws SQLException, NamingException;

}
