/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import ual.lp.server.mgr.Manager;

/**
 *
 * @author Divanio Silva
 */
public class ServerRMI {
    private Manager manager;
    
    private static final int port = 1099;
    
    public ServerRMI(Manager manager) {
        
        try {
            System.setProperty("java.rmi.server.hostname", "localhost");
            Registry registry = LocateRegistry.createRegistry(port);
            Thread.sleep(2000);
            System.out.println("O server arrancou!");
            ServerInf serverImpl = new ServerImpl(manager);
            
            registry.rebind("response", serverImpl);
        } catch (RemoteException e) {
            System.out.println("");
        } catch (InterruptedException e) {
            System.out.println("");
        }
    }
}
