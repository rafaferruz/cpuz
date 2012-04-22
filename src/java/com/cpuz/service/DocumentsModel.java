/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.service;

import com.cpuz.domain.Document;
import com.cpuz.DAO.impl.DocumentDAOImpl;
import com.cpuz.domain.UserType;
import com.cpuz.exceptions.DocumentException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class DocumentsModel {

    public DocumentsModel() {
    }
    public boolean keyIdExists(Integer ssn) {
        try {
            DocumentDAOImpl nDao = new DocumentDAOImpl();
            return nDao.keyIdExists(ssn);

        } catch (Exception ex) {
            return false;
        }
    }


    /**
     * Proporciona un objeto List de registros de titulares de Documents
     * con un número de registros indicado por el parámetro recChunk y
     * a partir del indicado por el parámetro recStart. Se toma como lista
     * base la totalidad de titulares de Documents ordenados por fecha empezando
     * por el titular más reciente y continuando al más antiguo.
     * @param recStart N� de registro inicial
     * @param recChunk N� de registros a incluir en la b�squeda
     * @return Un objeto List<Document> con los registros seleccionados
     */
    public List<Document> getNewsRecords() {
/* Requirement codes: E5-1 */
        return this.getNewsRecords(UserType.ANONYMOUS,"");
    }

    public List<Document> getNewsRecords(UserType userType) {
/* Requirement codes: E5-1 */
        return this.getNewsRecords(userType,"");

    }
    public List<Document> getNewsRecords(UserType userType, String selectionClause) {

        String sqlWhereClause="";
        List<Document> news = new ArrayList<Document>();
        try {
            DocumentDAOImpl nDao = new DocumentDAOImpl();
/* Requirement codes: E5-1 */
/*            if (userType != UserType.ADMIN) {
                sqlWhereClause = " doc_status = 2 ";
            }
 * */
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

            /*            String sqlWhereClause = "WHERE doc_user=null" +
            " OR doc_user=0 " +
            " OR doc_user=doc_id " +
            " ORDER BY doc_FECHA DESC, doc_id DESC";
             * */
            sqlWhereClause ="SELECT * FROM documents "+sqlWhereClause+
                    " ORDER BY doc_date DESC, doc_id DESC";
            for (Document n : nDao.readDocuments(sqlWhereClause)) {
                news.add(n);
            }
        } catch (DocumentException ex) {
            Logger.getLogger(DocumentsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DocumentsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DocumentsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return news;
    }
    public List<Document> getRecords(String selectClause, String whereClause, String orderClause) {

        String sqlClause = "";
        List<Document> records = new ArrayList<Document>();
        try {
            DocumentDAOImpl nDao = new DocumentDAOImpl();
            if (selectClause == null || selectClause.equals("")) {
                sqlClause = "SELECT * FROM roles ORDER BY rol_role";
            } else {
                sqlClause = selectClause + " " + whereClause + " " + orderClause;
            }

            records = nDao.readDocuments(sqlClause);

        } catch (DocumentException ex) {
            Logger.getLogger(DocumentsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DocumentsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DocumentsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return records;
    }


    public List<Document> getNewsDetails(String idNews) {

        List<Document> news = new ArrayList<Document>();
        try {
            DocumentDAOImpl nDao = new DocumentDAOImpl();
            String sqlWhereClause = "WHERE doc_user = '" + idNews + "'" +
                    " OR doc_id= '" + idNews + "'" +
                    " ORDER BY doc_date DESC, doc_id";
            for (Document n : nDao.readDocuments(sqlWhereClause)) {
                news.add(n);
            }
        } catch (DocumentException ex) {
            Logger.getLogger(DocumentsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DocumentsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DocumentsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return news;
    }

    public synchronized int setNewRecord(Document news) {

        try {
            DocumentDAOImpl nDao = new DocumentDAOImpl();
            return nDao.createDocument(news);
        } catch (DocumentException ex) {
            Logger.getLogger(DocumentsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DocumentsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DocumentsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public synchronized int setUpdateRecord(Document news) {

        try {
            DocumentDAOImpl nDao = new DocumentDAOImpl();
            return nDao.updateDocument(news);
        } catch (DocumentException ex) {
            Logger.getLogger(DocumentsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DocumentsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DocumentsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public synchronized int deleteNews(Document news) {

        try {
            DocumentDAOImpl nDao = new DocumentDAOImpl();
            return nDao.deleteDocument(news);
        } catch (DocumentException ex) {
            Logger.getLogger(DocumentsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DocumentsModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DocumentsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
}





