/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.service;

import com.cpuz.domain.Section;
import com.cpuz.DAO.impl.SectionDAOImpl;
import com.cpuz.exceptions.SectionException;
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
public class SectionsModel {

    public SectionsModel() {
    }

    public boolean keyIdExists(String ssn) {
        try {
            SectionDAOImpl nDao = new SectionDAOImpl();
            return nDao.keyIdExists(ssn);

        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Proporciona un objeto List de registros de titulares de Sections
     * con un número de registros indicado por el parámetro recChunk y
     * a partir del indicado por el parámetro recStart. Se toma como lista
     * base la totalidad de titulares de Sections ordenados por fecha empezando
     * por el titular más reciente y continuando al más antiguo.
     * @param recStart N� de registro inicial
     * @param recChunk N� de registros a incluir en la b�squeda
     * @return Un objeto List<Section> con los registros seleccionados
     */
    public List<Section> getRecords() {
        /* Requirement codes: E5-1 */
        return this.getRecords(UserType.ANONYMOUS, "");
    }

    public List<Section> getRecords(UserType userType) {
        /* Requirement codes: E5-1 */
        return this.getRecords(userType, "");

    }

    public List<Section> getRecords(UserType userType, String selectionClause) {

        String sqlClause = "";
        List<Section> news = new ArrayList<Section>();
        try {
            SectionDAOImpl nDao = new SectionDAOImpl();
            if (!selectionClause.equals("")) {
                if (!sqlClause.equals("")) {
                    sqlClause += " AND (" + selectionClause + ") ";
                } else {
                    sqlClause = " (" + selectionClause + ") ";
                }
            }
            if (!sqlClause.equals("")) {
                sqlClause = " WHERE " + sqlClause;
            }

            sqlClause +=
                    " ORDER BY sec_id";
            news = nDao.readSections(sqlClause);
        } catch (SectionException ex) {
            Logger.getLogger(SectionsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SectionsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SectionsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return news;
    }

    public List<Section> getRecords(String selectClause, String whereClause, String orderClause) {

        String sqlClause = "";
        List<Section> records = new ArrayList<Section>();
        try {
            SectionDAOImpl nDao = new SectionDAOImpl();
            if (selectClause == null || selectClause.equals("")) {
                sqlClause = "SELECT * FROM sections ORDER BY sec_id";
            } else {
                sqlClause = selectClause + " " + whereClause + " " + orderClause;
            }

            records = nDao.readSections(sqlClause);

        } catch (SectionException ex) {
            Logger.getLogger(SectionsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SectionsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SectionsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return records;
    }

    public List<Section> getDetails(String idNews) {

        List<Section> news = new ArrayList<Section>();
        try {
            SectionDAOImpl nDao = new SectionDAOImpl();
            String sqlClause = "SELECT * FROM sections "
                    +" WHERE sec_id = '" + idNews + "'"
                    + " OR sec_id= '" + idNews + "'"
                    + " ORDER BY sec_id";
            news = nDao.readSections(sqlClause);
        } catch (SectionException ex) {
            Logger.getLogger(SectionsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SectionsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SectionsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return news;
    }

    public synchronized int setNewRecord(Section news) {

        try {
            SectionDAOImpl nDao = new SectionDAOImpl();
            return nDao.createSection(news);
        } catch (SectionException ex) {
            Logger.getLogger(SectionsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SectionsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SectionsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public synchronized int setUpdateRecord(Section news) {

        try {
            SectionDAOImpl nDao = new SectionDAOImpl();
            return nDao.updateSection(news);
        } catch (SectionException ex) {
            Logger.getLogger(SectionsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SectionsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SectionsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public synchronized int deleteNews(Section news) {

        try {
            SectionDAOImpl nDao = new SectionDAOImpl();
            return nDao.deleteSection(news);
        } catch (SectionException ex) {
            Logger.getLogger(SectionsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(SectionsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SectionsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
}





