package com.cpuz.exceptions;

public class DocumentException extends Exception{

    private static final long serialVersionUID = 901L;
    /**
     * Creates new <code>NoticiaException</code> without detail message.
     */
    public DocumentException() {
        this("DocumentException");
    }
    
    /**
     * Constructs an <code>NoticiaException</code> with the specified
     * detail message.
     * @param msg the detail message.
     */
    public DocumentException(String msg) {
        super(msg);
    }
}
