/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.service;

import com.cpuz.domain.Contact;
import com.cpuz.DAO.impl.ContactDAOImpl;
import com.cpuz.domain.UserType;
import com.cpuz.exceptions.UserException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class ContactsService {

    public ContactsService() {
    }

    public boolean keyIdExists(Integer ssn) {
        try {
            ContactDAOImpl nDao = new ContactDAOImpl();
            return nDao.keyIdExists(ssn);

        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Proporciona un objeto List de registros de titulares de Contacts
     * con un número de registros indicado por el parámetro recChunk y
     * a partir del indicado por el parámetro recStart. Se toma como lista
     * base la totalidad de titulares de Contacts ordenados por fecha empezando
     * por el titular más reciente y continuando al más antiguo.
     * @param recStart N� de registro inicial
     * @param recChunk N� de registros a incluir en la b�squeda
     * @return Un objeto List<Contact> con los registros seleccionados
     */
    public List<Contact> getNewsRecords() {
        /* Requirement codes: E5-1 */
        return this.getNewsRecords(UserType.ANONYMOUS, "");
    }

    public List<Contact> getNewsRecords(UserType userType) {
        /* Requirement codes: E5-1 */
        return this.getNewsRecords(userType, "");

    }

    public List<Contact> getNewsRecords(UserType userType, String selectionClause) {

        String sqlWhereClause = "";
        List<Contact> news = new ArrayList<Contact>();
        try {
            ContactDAOImpl nDao = new ContactDAOImpl();
            /* Requirement codes: E5-1 */
            if (userType != UserType.ADMIN) {
                sqlWhereClause = " con_status = 2 ";
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

            /*            String sqlWhereClause = "WHERE con_user=null" +
            " OR con_user=0 " +
            " OR con_user=con_id " +
            " ORDER BY con_FECHA DESC, con_id DESC";
             * */
            sqlWhereClause = "SELECT * FROM contacts " + sqlWhereClause
                    + " ORDER BY con_date DESC, con_id DESC";
            for (Contact n : nDao.readContacts(sqlWhereClause)) {
                news.add(n);
            }
        } catch (UserException ex) {
            Logger.getLogger(ContactsService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ContactsService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ContactsService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return news;
    }

    public List<Contact> getRecords(String selectClause, String whereClause, String orderClause) {

        String sqlClause = "";
        List<Contact> records = new ArrayList<Contact>();
        try {
            ContactDAOImpl nDao = new ContactDAOImpl();
            if (selectClause == null || selectClause.equals("")) {
                sqlClause = "SELECT * FROM contacts ORDER BY con_date DESC, con_id DESC";
            } else {
                sqlClause = selectClause + " " + whereClause + " " + orderClause;
            }

            records = nDao.readContacts(sqlClause);

        } catch (UserException ex) {
            Logger.getLogger(ContactsService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ContactsService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ContactsService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return records;
    }

    public List<Contact> getNewsDetails(String idNews) {

        List<Contact> news = new ArrayList<Contact>();
        try {
            ContactDAOImpl nDao = new ContactDAOImpl();
            String sqlWhereClause = "WHERE con_user = '" + idNews + "'"
                    + " OR con_id= '" + idNews + "'"
                    + " ORDER BY con_date DESC, con_id";
            for (Contact n : nDao.readContacts(sqlWhereClause)) {
                news.add(n);
            }
        } catch (UserException ex) {
            Logger.getLogger(ContactsService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ContactsService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ContactsService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return news;
    }

    public synchronized int setNewRecord(Contact news) {

        try {
            ContactDAOImpl nDao = new ContactDAOImpl();
            return nDao.createContact(news);
        } catch (UserException ex) {
            Logger.getLogger(ContactsService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ContactsService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ContactsService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public synchronized int setUpdateRecord(Contact news) {

        try {
            ContactDAOImpl nDao = new ContactDAOImpl();
            return nDao.updateContact(news);
        } catch (UserException ex) {
            Logger.getLogger(ContactsService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ContactsService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ContactsService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public synchronized int deleteNews(Contact news) {

        try {
            ContactDAOImpl nDao = new ContactDAOImpl();
            return nDao.deleteContact(news);
        } catch (UserException ex) {
            Logger.getLogger(ContactsService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ContactsService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ContactsService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
}
