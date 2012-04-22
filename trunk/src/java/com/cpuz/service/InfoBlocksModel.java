/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.service;

import com.cpuz.domain.InfoBlock;
import com.cpuz.DAO.impl.InfoBlockDAOImpl;
import com.cpuz.domain.UserType;
import com.cpuz.exceptions.InfoBlockException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class InfoBlocksModel {

    public InfoBlocksModel() {
    }

    public boolean keyIdExists(Integer ssn) {
        try {
            InfoBlockDAOImpl nDao = new InfoBlockDAOImpl();
            return nDao.keyIdExists(ssn);

        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Proporciona un objeto List de registros de titulares de InfoBlocks
     * con un número de registros indicado por el parámetro recChunk y
     * a partir del indicado por el parámetro recStart. Se toma como lista
     * base la totalidad de titulares de InfoBlocks ordenados por fecha empezando
     * por el titular más reciente y continuando al más antiguo.
     * @param recStart N� de registro inicial
     * @param recChunk N� de registros a incluir en la b�squeda
     * @return Un objeto List<InfoBlock> con los registros seleccionados
     */
    public List<InfoBlock> getNewsRecords() {
        /* Requirement codes: E5-1 */
        return this.getNewsRecords(UserType.ANONYMOUS, "");
    }

    public List<InfoBlock> getNewsRecords(UserType userType) {
        /* Requirement codes: E5-1 */
        return this.getNewsRecords(userType, "");

    }

    public List<InfoBlock> getNewsRecords(UserType userType, String selectionClause) {

        String sqlWhereClause = "";
        List<InfoBlock> news = new ArrayList<>();
        try {
            InfoBlockDAOImpl nDao = new InfoBlockDAOImpl();
            /* Requirement codes: E5-1 */
            if (userType != UserType.ADMIN) {
                sqlWhereClause = " inb_status = 2 ";
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

            /*            String sqlWhereClause = "WHERE inb_user=null" +
            " OR inb_user=0 " +
            " OR inb_user=inb_id " +
            " ORDER BY inb_FECHA DESC, inb_id DESC";
             * */
            sqlWhereClause = "SELECT * FROM infoblocks " + sqlWhereClause
                    + " ORDER BY inb_date DESC, inb_id DESC";
            for (InfoBlock n : nDao.readInfoBlocks(sqlWhereClause)) {
                news.add(n);
            }
        } catch (InfoBlockException ex) {
            Logger.getLogger(InfoBlocksModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InfoBlocksModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(InfoBlocksModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return news;
    }

    public List<InfoBlock> getRecords(String selectClause, String whereClause, String orderClause) {

        String sqlClause = "";
        List<InfoBlock> records = new ArrayList<InfoBlock>();
        try {
            InfoBlockDAOImpl nDao = new InfoBlockDAOImpl();
            if (selectClause == null || selectClause.equals("")) {
                sqlClause = "SELECT * FROM infoblocks ORDER BY inb_date DESC, inb_id DESC";
            } else {
                sqlClause = selectClause + " " + whereClause + " " + orderClause;
            }

            records = nDao.readInfoBlocks(sqlClause);

        } catch (InfoBlockException ex) {
            Logger.getLogger(InfoBlocksModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InfoBlocksModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(InfoBlocksModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return records;
    }

    public List<InfoBlock> getNewsDetails(String idNews) {

        List<InfoBlock> news = new ArrayList<InfoBlock>();
        try {
            InfoBlockDAOImpl nDao = new InfoBlockDAOImpl();
            String sqlWhereClause = "WHERE inb_user = '" + idNews + "'"
                    + " OR inb_id= '" + idNews + "'"
                    + " ORDER BY inb_date DESC, inb_id";
            for (InfoBlock n : nDao.readInfoBlocks(sqlWhereClause)) {
                news.add(n);
            }
        } catch (InfoBlockException ex) {
            Logger.getLogger(InfoBlocksModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InfoBlocksModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(InfoBlocksModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return news;
    }

    public synchronized int setNewRecord(InfoBlock news) {

        try {
            InfoBlockDAOImpl nDao = new InfoBlockDAOImpl();
            return nDao.createInfoBlock(news);
        } catch (InfoBlockException ex) {
            Logger.getLogger(InfoBlocksModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InfoBlocksModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(InfoBlocksModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public synchronized int setUpdateRecord(InfoBlock news) {

        try {
            InfoBlockDAOImpl nDao = new InfoBlockDAOImpl();
            return nDao.updateInfoBlock(news);
        } catch (InfoBlockException ex) {
            Logger.getLogger(InfoBlocksModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InfoBlocksModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(InfoBlocksModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public synchronized int deleteNews(InfoBlock news) {

        try {
            InfoBlockDAOImpl nDao = new InfoBlockDAOImpl();
            return nDao.deleteInfoBlock(news);
        } catch (InfoBlockException ex) {
            Logger.getLogger(InfoBlocksModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InfoBlocksModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(InfoBlocksModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
}
