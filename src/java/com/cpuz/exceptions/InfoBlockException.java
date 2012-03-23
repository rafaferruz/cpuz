package com.cpuz.exceptions;

public class InfoBlockException extends Exception{

    private static final long serialVersionUID = 901L;
    /**
     * Creates new <code>NoticiaException</code> without detail message.
     */
    public InfoBlockException() {
        this("InfoBlockException");
    }
    
    /**
     * Constructs an <code>NoticiaException</code> with the specified
     * detail message.
     * @param msg the detail message.
     */
    public InfoBlockException(String msg) {
        super(msg);
    }
}
