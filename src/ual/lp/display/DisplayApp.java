/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.display;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pedro
 */
public class DisplayApp {

    private DisplayRMI displayRMI;
    private int sleepTime;

    public DisplayApp(int sleepTime) {
        this.displayRMI = new DisplayRMI(this);
        this.sleepTime = sleepTime;
        checkConnection();

    }

    private void checkConnection() {
        while (true) {
            if (!this.displayRMI.isServerOn()) {
                this.displayRMI = new DisplayRMI(this);
            }
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ex) {

            }

        }
    }

    public static void main(String[] args) {
        DisplayApp displayApp = new DisplayApp(5000);
    }
}
