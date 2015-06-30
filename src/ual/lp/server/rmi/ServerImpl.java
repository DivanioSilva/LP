/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import ual.lp.caller.rmi.CallerInf;
import ual.lp.exceptions.NoTicketsException;
import ual.lp.server.mgr.Manager;
import ual.lp.server.objects.Employee;
import ual.lp.server.objects.Ticket;

/**
 *
 * @author Divanio Silva
 */
public class ServerImpl extends UnicastRemoteObject implements ServerInf{
    private Manager manager;
    private Ticket ticket;
    
    public ServerImpl(Manager mgr) throws RemoteException {
        this.manager=mgr;
    }
    
    @Override
    public String printMessage() throws RemoteException {
        System.out.println("Estou a imprimir no servidor");
        return "Olá Pedrinho, aqui é o servidor que esta falando :-)";
    }

    @Override
    public void connect(CallerInf id) throws RemoteException {
        System.out.println(id.testCallback("O callback funcionou"));
    }

    @Override
    public void closeTicket(Ticket ticket) throws RemoteException {
        this.manager.closeTicket(ticket);
    }

    @Override
    public Ticket getNextTicket(Employee employee) throws RemoteException, NoTicketsException {
        ticket = new Ticket();
        return ticket=this.manager.getNextTicket(employee);
    }
}
