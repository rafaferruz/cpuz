package com.cpuz.exceptions;

public class UserException extends Exception{

    /**
     * Creates new <code>UserException</code> without detail message.
     */
    public UserException() {
        this("UserException");
    }
    
    /**
     * Constructs an <code>UserException</code> with the specified
     * detail message.
     * @param msg the detail message.
     */
    public UserException(String msg) {
        super(msg);
    }
}
