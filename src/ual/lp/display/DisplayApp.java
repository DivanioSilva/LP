/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.display;

import java.util.logging.Level;
import java.util.logging.Logger;
import ual.lp.display.utils.DisplayConfig;

/**
 *
 * @author Pedro
 */
public class DisplayApp {

    private DisplayRMI displayRMI;
    private int sleepTime;
    private String myIP;
    private String serverIP;
    private String filePath;

    public DisplayApp(int sleepTime) {
        DisplayConfig displayConfig = new DisplayConfig();
        myIP = displayConfig.getMyIP();
        serverIP = displayConfig.getServerIP();
        filePath = displayConfig.getFilePath();
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

    /**
     * @return the myIP
     */
    public String getMyIP() {
        return myIP;
    }

    /**
     * @return the serverIP
     */
    public String getServerIP() {
        return serverIP;
    }

    /**
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    public static void main(String[] args) {
        DisplayApp displayApp = new DisplayApp(5000);
    }
}
