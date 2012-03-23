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
public class Noticia implements Serializable{

    private static final long serialVersionUID = 101L;

    private Integer id;
    private java.util.Date fecha;
    private Integer estádo = 0;
    private String nombre;
    private String dninie;
    private String email;
    private Integer idPropietario;
    private Integer orden;
    private String tipoTitular;
    private String titular;
    private String contenido;
    private String ficheroImagen;
    private String enlace;
    private Integer anchoImagen;
    private Integer altoImagen;
    private String posicionImagen;
    private boolean registered;

    public Noticia() {
    }

    public Noticia(Integer id) {
        this.id = id;
    }

   public Integer getAltoImagen() {
        return altoImagen;
    }

    public void setAltoImagen(Integer altoImagen) {
        this.altoImagen = altoImagen;
    }

    public Integer getAnchoImagen() {
        return anchoImagen;
    }

    public void setAnchoImagen(Integer anchoImagen) {
        this.anchoImagen = anchoImagen;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getDninie() {
        return dninie;
    }

    public void setDninie(String dninie) {
        this.dninie = dninie;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public Integer getEstado() {
        return estádo;
    }

    public void setEstado(Integer estádo) {
        this.estádo = estádo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFicheroImagen() {
        return ficheroImagen;
    }

    public void setFicheroImagen(String ficheroImagen) {
        this.ficheroImagen = ficheroImagen;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(Integer idPropietario) {
        this.idPropietario = idPropietario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getPosicionImagen() {
        return posicionImagen;
    }

    public void setPosicionImagen(String posicionImagen) {
        this.posicionImagen = posicionImagen;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public String getTipoTitular() {
        return tipoTitular;
    }

    public void setTipoTitular(String tipoTitular) {
        this.tipoTitular = tipoTitular;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Noticia other = (Noticia) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }


}