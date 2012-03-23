package com.cpuz.exceptions;

public class NewsPieceException extends Exception{

    private static final long serialVersionUID = 901L;
    /**
     * Creates new <code>NoticiaException</code> without detail message.
     */
    public NewsPieceException() {
        this("NewsPieceException");
    }
    
    /**
     * Constructs an <code>NoticiaException</code> with the specified
     * detail message.
     * @param msg the detail message.
     */
    public NewsPieceException(String msg) {
        super(msg);
    }
}
