/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.caller.app;

import ual.lp.caller.gui.CallerGUI;
import ual.lp.caller.mgr.CallerMGR;

/**
 *
 * @author Divanio Silva
 */
public class CallerApp {
    public static void main(String[] args) {
        CallerMGR mgr = new CallerMGR();
        CallerGUI caller = new CallerGUI(mgr);
        
        caller.setVisible(true);
    }
}
