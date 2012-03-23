package com.cpuz.exceptions;

public class UserRoleException extends Exception{

    private static final long serialVersionUID = 901L;
    /**
     * Creates new <code>NoticiaException</code> without detail message.
     */
    public UserRoleException() {
        this("UserRoleException");
    }
    
    /**
     * Constructs an <code>NoticiaException</code> with the specified
     * detail message.
     * @param msg the detail message.
     */
    public UserRoleException(String msg) {
        super(msg);
    }
}
