/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.mgr;

import java.rmi.RemoteException;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import ual.lp.server.objects.Department;

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
            synchronized (manager.getRemoteLock()) {
                for (int i = 0; i < manager.getEmployees().size(); i++) {
                    try {
                        //lembrar do null pointerException
                        manager.getEmployees().get(i).getCallerInf().keepAlive("Ping");
                    } catch (RemoteException ex) {
                        System.out.println("Problema a comunicar com o employee");
                        Department department = manager.getEmployees().get(i).getDepartment();
                        manager.getEmployees().remove(i);
                        manager.employeesCallback(department);

                    }

                }

            }

//            log.debug("Tenho: " + manager.getDepartments().size());
//            for (int i = 0; i < manager.getDepartments().size(); i++) {
//                manager.employeesCallback(manager.getDepartments().get(i));
//                log.debug(manager.getDepartments().get(i).getName());
//
//            }
            try {

                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
    }

}
