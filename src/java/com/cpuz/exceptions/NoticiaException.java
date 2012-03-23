package com.cpuz.exceptions;

public class NoticiaException extends Exception{

    private static final long serialVersionUID = 901L;
    /**
     * Creates new <code>NoticiaException</code> without detail message.
     */
    public NoticiaException() {
        this("NoticiaException");
    }
    
    /**
     * Constructs an <code>NoticiaException</code> with the specified
     * detail message.
     * @param msg the detail message.
     */
    public NoticiaException(String msg) {
        super(msg);
    }
}
