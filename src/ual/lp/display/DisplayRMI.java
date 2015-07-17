/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.display;

import ual.lp.caller.rmi.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import ual.lp.caller.gui.CallerGUI;
import ual.lp.caller.utils.Config;
import ual.lp.server.rmi.ServerInf;
import ual.lp.server.rmi.ServerToDisplayInf;

/**
 *
 * @author Divanio Silva
 */
public class DisplayRMI {

    //172.16.214.237
    //172.16.120.77

    public static final String HOST = "localhost";
    public static final int PORT = 1099;
    public static final String MYIP = null;
    private CallerGUI callerGUI;
    private DisplayApp displayApp;
    private boolean serverOn = false;

    public DisplayRMI(CallerGUI callerGUI) throws RemoteException, NotBoundException {
        this.callerGUI = callerGUI;
        System.setProperty("java.rmi.server.hostname", "localhost");//informar isso no relatório

        Registry registry = LocateRegistry.getRegistry(HOST, PORT);
        ServerInf objRemoto = (ServerInf) registry.lookup("response");
        this.callerGUI.setRemoteObject(objRemoto);

    }

    public DisplayRMI(DisplayApp displayApp) {
        try {
            System.setProperty("java.rmi.server.hostname", "localhost");//informar isso no relatório
            Registry registry = LocateRegistry.getRegistry(HOST, PORT);
            ServerToDisplayInf objRemoto = (ServerToDisplayInf) registry.lookup("display");
            
            DisplayInf callback = new DisplayImpl(this);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                
            }
            
            objRemoto.connect(callback);
            this.serverOn = true;
            System.out.println("Ligação ao servidor com sucesso");
//            objRemoto.connect(callback);
            

            

        } catch (NotBoundException e) {
            System.out.println("Não encontrada a referência para o objRemoto");
//            System.out.println("Deu merda no client.");
            System.out.println(e.getMessage());
            this.serverOn = false;
           
            
        } catch (RemoteException e) {
            System.out.println("Problema com a ligação ao servidor");
            this.serverOn = false;
         
        }
    }

    /**
     * @return the serverOn
     */
    public boolean isServerOn() {
        return serverOn;
    }

    /**
     * @param serverOn the serverOn to set
     */
    public void setServerOn(boolean serverOn) {
        this.serverOn = serverOn;
    }

}
