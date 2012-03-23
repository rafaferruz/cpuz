/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.model;

import com.cpuz.domain.User;
import com.cpuz.DAO.impl.UserDAOImpl;
import com.cpuz.exceptions.UserException;
import com.cpuz.domain.UserType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class UserModel {

    public UserModel() {
    }
    public boolean keyIdExists(int ssn) {
        try {
            UserDAOImpl nDao = new UserDAOImpl();
            return nDao.keyIdExists(ssn);
        } catch (UserException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Proporciona un objeto List de registros de titulares de Users
     * con un número de registros indicado por el parámetro recChunk y
     * a partir del indicado por el parámetro recStart. Se toma como lista
     * base la totalidad de titulares de Users ordenados por fecha empezando
     * por el titular más reciente y continuando al más antiguo.
     * @param recStart N� de registro inicial
     * @param recChunk N� de registros a incluir en la b�squeda
     * @return Un objeto List<User> con los registros seleccionados
     */
    public List<User> getNewsRecords() {
/* Requirement codes: E5-1 */
        return this.getNewsRecords(UserType.ANONYMOUS,"");
    }

    public List<User> getNewsRecords(UserType userType) {
/* Requirement codes: E5-1 */
        return this.getNewsRecords(userType,"");

    }
    public List<User> getNewsRecords(UserType userType, String selectionClause) {

        String sqlWhereClause="";
        List<User> news = new ArrayList<User>();
        try {
            UserDAOImpl nDao = new UserDAOImpl();
            if (!selectionClause.equals("")) {
                if (!sqlWhereClause.equals("")){
                sqlWhereClause+=" AND ("+selectionClause+") ";
                } else {
                    sqlWhereClause=" ("+selectionClause+") ";
                }
            }
            if (!sqlWhereClause.equals("")){
                sqlWhereClause=" WHERE " +sqlWhereClause;
            }

            sqlWhereClause ="SELECT * FROM users "+sqlWhereClause+
                    " ORDER BY usu_user";
            news = nDao.readUsers(sqlWhereClause);
        } catch (UserException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return news;
    }

        public List<User> getRecords(String selectClause, String whereClause, String orderClause) {

        String sqlClause = "";
        List<User> records = new ArrayList<User>();
        try {
            UserDAOImpl nDao = new UserDAOImpl();
            if (selectClause == null || selectClause.equals("")) {
                sqlClause = "SELECT * FROM users ORDER BY usu_user";
            } else {
                sqlClause = selectClause + " " + whereClause + " " + orderClause;
            }

            records = nDao.readUsers(sqlClause);

        } catch (UserException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return records;
    }



    public List<User> getNewsDetails(String idNews) {

        List<User> news = new ArrayList<User>();
        try {
            UserDAOImpl nDao = new UserDAOImpl();
            String sqlWhereClause = "WHERE usu_id = '" + idNews + "'" +
                    " OR usu_id= '" + idNews + "'" +
                    " ORDER BY usu_user";
            sqlWhereClause ="SELECT * FROM users "+sqlWhereClause;
            news = nDao.readUsers(sqlWhereClause);
        } catch (UserException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return news;
    }

    public synchronized int setNewRecord(User news) {

        try {
            UserDAOImpl nDao = new UserDAOImpl();
            return nDao.createUser(news);
        } catch (UserException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public synchronized int setUpdateRecord(User news) {

        try {
            UserDAOImpl nDao = new UserDAOImpl();
            return nDao.updateUser(news);
        } catch (UserException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public synchronized int deleteNews(User news) {

        try {
            UserDAOImpl nDao = new UserDAOImpl();
            return nDao.deleteUser(news);
        } catch (UserException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
}





