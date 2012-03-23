/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cpuz.tools;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class Tools {

    public static String EscapeSingleQuote(String s) {

        int inicio = 0;
        while (s.indexOf("'", inicio) >= 0) {
            s = s.substring(0, s.indexOf("'", inicio)) + "\\" + s.substring(s.indexOf("'", inicio));
            inicio = s.indexOf("'", inicio);
            inicio++;
        }
        return s;
    }
}

