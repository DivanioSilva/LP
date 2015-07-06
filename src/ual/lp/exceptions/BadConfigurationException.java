/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.exceptions;

/**
 *
 * @author Pedro
 */
public class BadConfigurationException extends Exception {

    /**
     * Creates a new instance of <code>badConfigurationException</code> without
     * detail message.
     */
    public BadConfigurationException() {
    }

    /**
     * Constructs an instance of <code>badConfigurationException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public BadConfigurationException(String msg) {
        super(msg);
    }
}
