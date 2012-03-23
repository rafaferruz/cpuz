/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cpuz.exceptions;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class ComentarioException  extends Exception{

    private static final long serialVersionUID = 902L;
    /**
     * Creates new <code>NoticiaException</code> without detail message.
     */
    public ComentarioException() {
        this("ComentarioException");
    }

    /**
     * Constructs an <code>NoticiaException</code> with the specified
     * detail message.
     * @param msg the detail message.
     */
    public ComentarioException(String msg) {
        super(msg);
    }
}

