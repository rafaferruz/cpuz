/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.DAO.query.entities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author RAFAEL FERRUZ
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
public class NewsPieceComposition implements Serializable {

    private static final long serialVersionUID = 101L;
    private Integer npi_id;
    private java.util.Date npi_date;
    private Integer npi_status;       // Puede ser: 0=Deshabilitado, 1=Revisado pendiente, 2=Autorizado
    private String npi_user;
    private String npi_section;
    private String npi_description;
    private Integer npi_scope;        // Puede ser : Global, Vecinal, Confidencial
    private Integer npi_access;       // Puede ser: Restáicted Solamente accede el propietario, o
    //            ALL
    private Integer nco_id;
    private Integer nco_idNpi;
    private String nco_componentType; // Puede tener los siguientes valores:
    //   InfoBlock, Image, Document, News
    private Integer nco_idComponent;
    private Integer nco_order;
    private String nco_headerAlt;
    private String nco_bodyAbstract;
    private String nco_headerStyle;   // Puede tener los siguientes valores:
    // Titular, Subtítulo, Destácado
    private Integer nco_imageHigh;
    private Integer nco_imageWidth;
    private String nco_linkedElement;   // Puede ser una URL absoluta (http;//....) o
    // Un ID de NewsPiece
    private boolean registered;

    public NewsPieceComposition() {
    }

    public String getNco_bodyAbstract() {
        return nco_bodyAbstract;
    }

    public void setNco_bodyAbstract(String nco_bodyAbstract) {
        this.nco_bodyAbstract = nco_bodyAbstract;
    }

    public String getNco_componentType() {
        return nco_componentType;
    }

    public void setNco_componentType(String nco_componentType) {
        this.nco_componentType = nco_componentType;
    }

    public String getNco_headerAlt() {
        return nco_headerAlt;
    }

    public void setNco_headerAlt(String nco_headerAlt) {
        this.nco_headerAlt = nco_headerAlt;
    }

    public String getNco_headerStyle() {
        return nco_headerStyle;
    }

    public void setNco_headerStyle(String nco_headerStyle) {
        this.nco_headerStyle = nco_headerStyle;
    }

    public Integer getNco_id() {
        return nco_id;
    }

    public void setNco_id(Integer nco_id) {
        this.nco_id = nco_id;
    }

    public Integer getNco_idComponent() {
        return nco_idComponent;
    }

    public void setNco_idComponent(Integer nco_idComponent) {
        this.nco_idComponent = nco_idComponent;
    }

    public Integer getNco_idNpi() {
        return nco_idNpi;
    }

    public void setNco_idNpi(Integer nco_idNpi) {
        this.nco_idNpi = nco_idNpi;
    }

    public Integer getNco_imageHigh() {
        return nco_imageHigh;
    }

    public void setNco_imageHigh(Integer nco_imageHigh) {
        this.nco_imageHigh = nco_imageHigh;
    }

    public Integer getNco_imageWidth() {
        return nco_imageWidth;
    }

    public void setNco_imageWidth(Integer nco_imageWidth) {
        this.nco_imageWidth = nco_imageWidth;
    }

    public String getNco_linkedElement() {
        return nco_linkedElement;
    }

    public void setNco_linkedElement(String nco_linkedElement) {
        this.nco_linkedElement = nco_linkedElement;
    }

    public Integer getNco_order() {
        return nco_order;
    }

    public void setNco_order(Integer nco_order) {
        this.nco_order = nco_order;
    }

    public Integer getNpi_access() {
        return npi_access;
    }

    public void setNpi_access(Integer npi_access) {
        this.npi_access = npi_access;
    }

    public Date getNpi_date() {
        return npi_date;
    }

    public void setNpi_date(Date npi_date) {
        this.npi_date = npi_date;
    }

    public String getNpi_description() {
        return npi_description;
    }

    public void setNpi_description(String npi_description) {
        this.npi_description = npi_description;
    }

    public Integer getNpi_id() {
        return npi_id;
    }

    public void setNpi_id(Integer npi_id) {
        this.npi_id = npi_id;
    }

    public Integer getNpi_scope() {
        return npi_scope;
    }

    public void setNpi_scope(Integer npi_scope) {
        this.npi_scope = npi_scope;
    }

    public String getNpi_section() {
        return npi_section;
    }

    public void setNpi_section(String npi_section) {
        this.npi_section = npi_section;
    }

    public Integer getNpi_status() {
        return npi_status;
    }

    public void setNpi_status(Integer npi_status) {
        this.npi_status = npi_status;
    }

    public String getNpi_user() {
        return npi_user;
    }

    public void setNpi_user(String npi_user) {
        this.npi_user = npi_user;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }
}
