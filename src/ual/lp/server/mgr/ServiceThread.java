/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.mgr;

/**
 *
 * @author Divanio Silva
 */
public class ServiceThread implements Runnable{

    private Manager manager;

    public ServiceThread(Manager manager) {
        this.manager = manager;
    }

    public ServiceThread() {
    }
    
    @Override
    public void run() {
        
        try {
            for (int i = 0; i < manager.getDepartments().size(); i++) {    
                manager.employeesCallback(manager.getDepartments().get(i));
            }
            
            Thread.sleep(1000);
        } catch (Exception e) {
        }        
    }
    
}
