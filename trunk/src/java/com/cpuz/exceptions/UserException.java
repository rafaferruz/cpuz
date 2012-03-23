package com.cpuz.exceptions;

public class UserException extends Exception{

    private static final long serialVersionUID = 901L;
    /**
     * Creates new <code>NoticiaException</code> without detail message.
     */
    public UserException() {
        this("NoticiaException");
    }
    
    /**
     * Constructs an <code>NoticiaException</code> with the specified
     * detail message.
     * @param msg the detail message.
     */
    public UserException(String msg) {
        super(msg);
    }
}
