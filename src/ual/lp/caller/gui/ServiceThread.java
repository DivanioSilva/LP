/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.caller.gui;

/**
 *
 * @author Divanio Silva
 */
public class ServiceThread implements Runnable {

    private CallerGUI callerGUI;
    private boolean running;

    public ServiceThread(CallerGUI callerGUI) {
        this.callerGUI = callerGUI;
        this.running = true;
    }

 

    @Override
    public void run() {
        while (running) { 
            callerGUI.setLastTicket();
            try {
                
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
    }
}
