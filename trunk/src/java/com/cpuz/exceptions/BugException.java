package com.cpuz.exceptions;

public class BugException extends Exception{

    private static final long serialVersionUID = 901L;
    /**
     * Creates new <code>NoticiaException</code> without detail message.
     */
    public BugException() {
        this("BugException");
    }
    
    /**
     * Constructs an <code>NoticiaException</code> with the specified
     * detail message.
     * @param msg the detail message.
     */
    public BugException(String msg) {
        super(msg);
    }
}
