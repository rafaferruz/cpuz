/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cpuz.domain;

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
public class NewsComposition implements Serializable{

    private static final long serialVersionUID = 101L;

    private Integer id;
    private Integer idNpi;
    private String componentType; // Puede tener los siguientes valores:
                                  //   InfoBlock, Image, Document, News
    private Integer idComponent;
    private Integer order;
    private String headerAlt;
    private String bodyAbstract;
    private String headerStyle;   // Puede tener los siguientes valores:
                                  // Titular, Subtítulo, Destácado
    private Integer imageHigh;
    private Integer imageWidth;
    private String linkedElement;   // Puede ser una URL absoluta (http;//....) o
                                  // Un ID de NewsPiece
    private boolean registered;

    public NewsComposition() {
    }

    public NewsComposition(Integer id) {
        this.id = id;
    }

    public String getBodyAbstract() {
        return bodyAbstract;
    }

    public void setBodyAbstract(String bodyAbstract) {
        this.bodyAbstract = bodyAbstract;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public String getLinkedElement() {
        return linkedElement;
    }

    public void setLinkedElement(String linkedElement) {
        this.linkedElement = linkedElement;
    }

    public String getHeaderAlt() {
        return headerAlt;
    }

    public void setHeaderAlt(String headerAlt) {
        this.headerAlt = headerAlt;
    }

    public String getHeaderStyle() {
        return headerStyle;
    }

    public void setHeaderStyle(String headerStyle) {
        this.headerStyle = headerStyle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdComponent() {
        return idComponent;
    }

    public void setIdComponent(Integer idComponent) {
        this.idComponent = idComponent;
    }

    public Integer getIdNpi() {
        return idNpi;
    }

    public void setIdNpi(Integer idNpi) {
        this.idNpi = idNpi;
    }

    public Integer getImageHigh() {
        return imageHigh;
    }

    public void setImageHigh(Integer imageHigh) {
        this.imageHigh = imageHigh;
    }

    public Integer getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NewsComposition other = (NewsComposition) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

}