/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.model;

import com.cpuz.domain.UserRole;
import com.cpuz.DAO.impl.UserRoleDAOImpl;
import com.cpuz.exceptions.UserRoleException;
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
public class UserRolesModel {

    public UserRolesModel() {
    }

    public boolean keyIdExists(int ssn) {
        try {
            UserRoleDAOImpl nDao = new UserRoleDAOImpl();
            return nDao.keyIdExists(ssn);
        } catch (UserRoleException ex) {
            Logger.getLogger(UserRolesModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserRolesModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserRolesModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Proporciona un objeto List de registros de titulares de UserRoles
     * con un número de registros indicado por el parámetro recChunk y
     * a partir del indicado por el parámetro recStart. Se toma como lista
     * base la totalidad de titulares de UserRoles ordenados por fecha empezando
     * por el titular más reciente y continuando al más antiguo.
     * @param recStart N� de registro inicial
     * @param recChunk N� de registros a incluir en la b�squeda
     * @return Un objeto List<UserRole> con los registros seleccionados
     */
    public List<UserRole> getNewsRecords() {
        /* Requirement codes: E5-1 */
        return this.getNewsRecords(UserType.ANONYMOUS, "");
    }

    public List<UserRole> getNewsRecords(UserType userType) {
        /* Requirement codes: E5-1 */
        return this.getNewsRecords(userType, "");

    }

    public List<UserRole> getNewsRecords(UserType userType, String selectionClause) {

        String sqlWhereClause = "";
        List<UserRole> news = new ArrayList<UserRole>();
        try {
            UserRoleDAOImpl nDao = new UserRoleDAOImpl();
            /* Requirement codes: E5-1 */
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

            sqlWhereClause = "SELECT * FROM userroles " + sqlWhereClause
                    + " ORDER BY usr_role";
            news = nDao.readUserRoles(sqlWhereClause);
        } catch (UserRoleException ex) {
            Logger.getLogger(UserRolesModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserRolesModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserRolesModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return news;
    }

    public List<UserRole> getNewsRecords(String sqlClause) {

        List<UserRole> news = new ArrayList<UserRole>();
        try {
            UserRoleDAOImpl nDao = new UserRoleDAOImpl();
            news = nDao.readUserRoles(sqlClause);
        } catch (UserRoleException ex) {
            Logger.getLogger(UserRolesModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserRolesModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserRolesModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return news;
    }

    public List<UserRole> getNewsDetails(String idNews) {

        List<UserRole> news = new ArrayList<UserRole>();
        try {
            UserRoleDAOImpl nDao = new UserRoleDAOImpl();
            String sqlWhereClause = "SELECT * FROM userroles WHERE  usr_id= '" + idNews + "'"
                    + " ORDER BY usr_role";
            news = nDao.readUserRoles(sqlWhereClause);
        } catch (UserRoleException ex) {
            Logger.getLogger(UserRolesModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserRolesModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserRolesModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return news;
    }

    public synchronized int setNewRecord(UserRole news) {

        try {
            UserRoleDAOImpl nDao = new UserRoleDAOImpl();
            return nDao.createUserRole(news);
        } catch (UserRoleException ex) {
            Logger.getLogger(UserRolesModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserRolesModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserRolesModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public synchronized int setUpdateRecord(UserRole news) {

        try {
            UserRoleDAOImpl nDao = new UserRoleDAOImpl();
            return nDao.updateUserRole(news);
        } catch (UserRoleException ex) {
            Logger.getLogger(UserRolesModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserRolesModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserRolesModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public synchronized int deleteNews(UserRole news) {

        try {
            UserRoleDAOImpl nDao = new UserRoleDAOImpl();
            return nDao.deleteUserRole(news);
        } catch (UserRoleException ex) {
            Logger.getLogger(UserRolesModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserRolesModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserRolesModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    public synchronized int deleteRecords(String deleteClause) {

        try {
            UserRoleDAOImpl nDao = new UserRoleDAOImpl();
            return nDao.deleteUserRole(deleteClause);
        } catch (UserRoleException ex) {
            Logger.getLogger(UserRolesModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserRolesModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserRolesModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
}
