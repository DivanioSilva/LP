/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.exceptions;

/**
 *
 * @author Divanio Silva
 */
public class NoTicketsException extends Exception {

    /**
     * Creates a new instance of <code>NoTicketsException</code> without detail
     * message.
     */
    public NoTicketsException() {
    }

    /**
     * Constructs an instance of <code>NoTicketsException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NoTicketsException(String msg) {
        super(msg);
    }
}
