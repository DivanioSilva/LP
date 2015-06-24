/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.app;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import ual.lp.server.rmi.ServerImpl;
import ual.lp.server.rmi.ServerInf;

/**
 *
 * @author Divanio Silva
 */
public class Server {
    private static final int port = 1099;
    
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(port);
            Thread.sleep(2000);
            System.out.println("O server arrancou!");
            ServerInf serverImpl = new ServerImpl();
            registry.rebind("response", serverImpl);

        } catch (Exception e) {
            System.err.println("Deu merda no server!");
        }
    }
}
