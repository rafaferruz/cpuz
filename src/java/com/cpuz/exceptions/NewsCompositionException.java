package com.cpuz.exceptions;

public class NewsCompositionException extends Exception{

    private static final long serialVersionUID = 901L;
    /**
     * Creates new <code>NoticiaException</code> without detail message.
     */
    public NewsCompositionException() {
        this("NewsCompositionException");
    }
    
    /**
     * Constructs an <code>NoticiaException</code> with the specified
     * detail message.
     * @param msg the detail message.
     */
    public NewsCompositionException(String msg) {
        super(msg);
    }
}
