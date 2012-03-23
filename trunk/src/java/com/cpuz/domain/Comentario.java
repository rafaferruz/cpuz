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
public class Comentario implements Serializable {

    private static final long serialVersionUID = 102L;
    private Integer id;
    private java.util.Date fecha;
    private Integer estádo = 0;
    private String ambito = "";
    private String nombre = "";
    private String parcela = "";
    private String email = "";
    private String publicar = "";
    private String titulo = "";
    private String comentario;
    private String ficheroImagen = "";

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getParcela() {
        return parcela;
    }

    public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    public String getPublicar() {
        return publicar;
    }

    public void setPublicar(String publicar) {
        this.publicar = publicar;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Comentario other = (Comentario) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
