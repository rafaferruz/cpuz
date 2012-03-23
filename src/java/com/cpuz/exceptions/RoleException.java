package com.cpuz.exceptions;

public class RoleException extends Exception{

    private static final long serialVersionUID = 901L;
    /**
     * Creates new <code>NoticiaException</code> without detail message.
     */
    public RoleException() {
        this("NoticiaException");
    }
    
    /**
     * Constructs an <code>NoticiaException</code> with the specified
     * detail message.
     * @param msg the detail message.
     */
    public RoleException(String msg) {
        super(msg);
    }
}
