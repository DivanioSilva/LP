/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import ual.lp.display.DisplayInf;
import ual.lp.server.mgr.Manager;
import ual.lp.server.objects.Ticket;

/**
 *
 * @author Divanio Silva
 */
public class ServerToDisplayImpl extends UnicastRemoteObject implements ServerToDisplayInf{
    private Manager manager;

    public ServerToDisplayImpl(Manager manager) throws RemoteException {
        
        this.manager = manager;
    }
    

    @Override
    public void connect(DisplayInf displayInf) throws RemoteException {
        this.manager.setDisplayInf(displayInf);
    }

    @Override
    public List<Ticket> getFirstTicketList() throws RemoteException {
        return this.manager.getDisplayTickets();
    }

    @Override
    public String keepAlive(String ping) throws RemoteException {
        return "Pong";
    }
    
}
