package com.cpuz.exceptions;

public class SectionException extends Exception{

    private static final long serialVersionUID = 906L;
    /**
     * Creates new <code>NoticiaException</code> without detail message.
     */
    public SectionException() {
        this("SectionException");
    }
    
    /**
     * Constructs an <code>NoticiaException</code> with the specified
     * detail message.
     * @param msg the detail message.
     */
    public SectionException(String msg) {
        super(msg);
    }
}
