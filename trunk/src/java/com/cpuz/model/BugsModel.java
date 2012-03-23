/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.model;

import com.cpuz.domain.Bug;
import com.cpuz.DAO.impl.BugDAOImpl;
import com.cpuz.domain.UserType;
import com.cpuz.exceptions.BugException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class BugsModel {

    public BugsModel() {
    }

    public boolean keyIdExists(Integer ssn) {
        try {
            BugDAOImpl nDao = new BugDAOImpl();
            return nDao.keyIdExists(ssn);

        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Proporciona un objeto List de registros de titulares de Bugs
     * con un número de registros indicado por el parámetro recChunk y
     * a partir del indicado por el parámetro recStart. Se toma como lista
     * base la totalidad de titulares de Bugs ordenados por fecha empezando
     * por el titular más reciente y continuando al más antiguo.
     * @param recStart N� de registro inicial
     * @param recChunk N� de registros a incluir en la b�squeda
     * @return Un objeto List<Bug> con los registros seleccionados
     */
    public List<Bug> getNewsRecords() {
        /* Requirement codes: E5-1 */
        return this.getNewsRecords(UserType.ANONYMOUS, "");
    }

    public List<Bug> getNewsRecords(UserType userType) {
        /* Requirement codes: E5-1 */
        return this.getNewsRecords(userType, "");

    }

    public List<Bug> getNewsRecords(UserType userType, String selectionClause) {

        String sqlWhereClause = "";
        List<Bug> news = new ArrayList<Bug>();
        try {
            BugDAOImpl nDao = new BugDAOImpl();
            /* Requirement codes: E5-1 */
            if (userType != UserType.ADMIN) {
                sqlWhereClause = " bug_status = 2 ";
            }
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

            /*            String sqlWhereClause = "WHERE bug_user=null" +
            " OR bug_user=0 " +
            " OR bug_user=bug_id " +
            " ORDER BY bug_FECHA DESC, bug_id DESC";
             * */
            sqlWhereClause = "SELECT * FROM bugs " + sqlWhereClause
                    + " ORDER BY bug_priority, bug_date DESC, bug_id DESC";
            for (Bug n : nDao.readBugs(sqlWhereClause)) {
                news.add(n);
            }
        } catch (BugException ex) {
            Logger.getLogger(BugsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BugsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(BugsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return news;
    }

    public List<Bug> getRecords(String selectClause, String whereClause, String orderClause) {

        String sqlClause = "";
        List<Bug> records = new ArrayList<Bug>();
        try {
            BugDAOImpl nDao = new BugDAOImpl();
            if (selectClause == null || selectClause.equals("")) {
                sqlClause = "SELECT * FROM bugs ORDER BY bug_priority, bug_date DESC, bug_id DESC";
            } else {
                sqlClause = selectClause + " " + whereClause + " " + orderClause;
            }

            records = nDao.readBugs(sqlClause);

        } catch (BugException ex) {
            Logger.getLogger(BugsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BugsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(BugsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return records;
    }

    public synchronized int setNewRecord(Bug news) {

        try {
            BugDAOImpl nDao = new BugDAOImpl();
            return nDao.createBug(news);
        } catch (BugException ex) {
            Logger.getLogger(BugsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BugsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(BugsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public synchronized int setUpdateRecord(Bug news) {

        try {
            BugDAOImpl nDao = new BugDAOImpl();
            return nDao.updateBug(news);
        } catch (BugException ex) {
            Logger.getLogger(BugsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BugsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(BugsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public synchronized int deleteNews(Bug news) {

        try {
            BugDAOImpl nDao = new BugDAOImpl();
            return nDao.deleteBug(news);
        } catch (BugException ex) {
            Logger.getLogger(BugsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(BugsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(BugsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
}
