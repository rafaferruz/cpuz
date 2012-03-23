package com.cpuz.exceptions;

public class ContactException extends Exception{

    private static final long serialVersionUID = 901L;
    /**
     * Creates new <code>NoticiaException</code> without detail message.
     */
    public ContactException() {
        this("ContactException");
    }
    
    /**
     * Constructs an <code>NoticiaException</code> with the specified
     * detail message.
     * @param msg the detail message.
     */
    public ContactException(String msg) {
        super(msg);
    }
}
