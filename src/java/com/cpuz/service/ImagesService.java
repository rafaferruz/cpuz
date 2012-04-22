/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.service;

import com.cpuz.domain.Image;
import com.cpuz.DAO.impl.ImageDAOImpl;
import com.cpuz.domain.UserType;
import com.cpuz.exceptions.ImageException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class ImagesService {

    public ImagesService() {
    }
    public boolean keyIdExists(Integer ssn) {
        try {
            ImageDAOImpl nDao = new ImageDAOImpl();
            return nDao.keyIdExists(ssn);

        } catch (Exception ex) {
            return false;
        }
    }


    /**
     * Proporciona un objeto List de registros de titulares de Images
     * con un número de registros indicado por el parámetro recChunk y
     * a partir del indicado por el parámetro recStart. Se toma como lista
     * base la totalidad de titulares de Images ordenados por fecha empezando
     * por el titular más reciente y continuando al más antiguo.
     * @param recStart N� de registro inicial
     * @param recChunk N� de registros a incluir en la b�squeda
     * @return Un objeto List<Image> con los registros seleccionados
     */
    public List<Image> getNewsRecords() {
/* Requirement codes: E5-1 */
        return this.getNewsRecords(UserType.ANONYMOUS,"");
    }

    public List<Image> getNewsRecords(UserType userType) {
/* Requirement codes: E5-1 */
        return this.getNewsRecords(userType,"");

    }
    public List<Image> getNewsRecords(UserType userType, String selectionClause) {

        String sqlWhereClause="";
        List<Image> news = new ArrayList<>();
        try {
            ImageDAOImpl nDao = new ImageDAOImpl();
/* Requirement codes: E5-1 */
/*            if (userType != UserType.ADMIN) {
                sqlWhereClause = " img_status = 2 ";
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

            /*            String sqlWhereClause = "WHERE img_user=null" +
            " OR img_user=0 " +
            " OR img_user=img_id " +
            " ORDER BY img_FECHA DESC, img_id DESC";
             * */
            sqlWhereClause ="SELECT * FROM images "+sqlWhereClause+
                    " ORDER BY img_date DESC, img_id DESC";
            for (Image n : nDao.readImages(sqlWhereClause)) {
                news.add(n);
            }
        } catch (ImageException ex) {
            Logger.getLogger(ImagesService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ImagesService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ImagesService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return news;
    }
    public List<Image> getRecords(String selectClause, String whereClause, String orderClause) {

        String sqlClause = "";
        List<Image> records = new ArrayList<>();
        try {
            ImageDAOImpl nDao = new ImageDAOImpl();
            if (selectClause == null || selectClause.equals("")) {
                sqlClause = "SELECT * FROM roles ORDER BY rol_role";
            } else {
                sqlClause = selectClause + " " + whereClause + " " + orderClause;
            }

            records = nDao.readImages(sqlClause);

        } catch (ImageException ex) {
            Logger.getLogger(ImagesService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ImagesService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ImagesService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return records;
    }


    public List<Image> getNewsDetails(String idNews) {

        List<Image> news = new ArrayList<>();
        try {
            ImageDAOImpl nDao = new ImageDAOImpl();
            String sqlWhereClause = "WHERE img_user = '" + idNews + "'" +
                    " OR img_id= '" + idNews + "'" +
                    " ORDER BY img_date DESC, img_id";
            for (Image n : nDao.readImages(sqlWhereClause)) {
                news.add(n);
            }
        } catch (ImageException ex) {
            Logger.getLogger(ImagesService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ImagesService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ImagesService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return news;
    }

    public synchronized int setNewRecord(Image news) {

        try {
            ImageDAOImpl nDao = new ImageDAOImpl();
            return nDao.createImage(news);
        } catch (ImageException ex) {
            Logger.getLogger(ImagesService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ImagesService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ImagesService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public synchronized int setUpdateRecord(Image news) {

        try {
            ImageDAOImpl nDao = new ImageDAOImpl();
            return nDao.updateImage(news);
        } catch (ImageException ex) {
            Logger.getLogger(ImagesService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ImagesService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ImagesService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public synchronized int deleteNews(Image news) {

        try {
            ImageDAOImpl nDao = new ImageDAOImpl();
            return nDao.deleteImage(news);
        } catch (ImageException ex) {
            Logger.getLogger(ImagesService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ImagesService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ImagesService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
}





