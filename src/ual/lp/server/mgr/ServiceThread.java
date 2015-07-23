/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.mgr;

import org.apache.log4j.Logger;

/**
 *
 * @author Divanio Silva
 */
public class ServiceThread implements Runnable {

    static final Logger log = Logger.getLogger("debugLogger");
    private Manager manager;
    private boolean running;

    public ServiceThread(Manager manager) {
        this.manager = manager;
        log.debug("a thread de serviço do server arrancou.");
        this.running = true;
    }

    public ServiceThread() {
    }

    @Override
    public void run() {
        while (running) {
            try {
                log.debug("Tenho: " + manager.getDepartments().size());
                for (int i = 0; i < manager.getDepartments().size(); i++) {
                    manager.employeesCallback(manager.getDepartments().get(i));
                    log.debug(manager.getDepartments().get(i).getName());
                }

                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
    }

}
