/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import ual.lp.caller.rmi.CallerInf;
import ual.lp.exceptions.BadConfigurationException;
import ual.lp.exceptions.NoTicketsException;
import ual.lp.server.mgr.Manager;
import ual.lp.server.objects.Department;
import ual.lp.server.objects.Employee;
import ual.lp.server.objects.Ticket;

/**
 *
 * @author Divanio Silva
 */
public class ServerImpl extends UnicastRemoteObject implements ServerInf {

    private Manager manager;
    private Ticket ticket;

    public ServerImpl(Manager mgr) throws RemoteException {
        this.manager = mgr;
    }

    @Override
    public String printMessage() throws RemoteException {
        System.out.println("Estou a imprimir no servidor");
        return "Olá Pedrinho, aqui é o servidor que esta falando :-)";
    }

    @Override
    public void connect(Employee employee) throws RemoteException, BadConfigurationException {
        manager.connect(employee);
    }

    @Override
    public void TockTock(Employee employee) throws RemoteException {
        this.manager.verifyEmployee(employee);
    }

    @Override
    public void closeTicket(Ticket ticket) throws RemoteException {
        this.manager.closeTicket(ticket);
    }

    @Override
    public Ticket getNextTicket(Employee employee) throws RemoteException, NoTicketsException {
        ticket = new Ticket();
        return ticket = this.manager.getNextTicket(employee);
    }

    @Override
    public void transferTicket(Ticket ticket) throws RemoteException {
        this.manager.transferTicket(ticket);
    }

    @Override
    public void resetQueue(Employee employee) throws RemoteException {
        this.manager.resetQueue(employee);
    }

    @Override
    public Ticket getLastTicket(Employee employee) throws RemoteException, NoTicketsException{
        return this.manager.getLastTicket(employee.getDepartment());
    }

    @Override
    public void recallTicket(Ticket ticket) throws RemoteException {
        this.manager.recallTicket(ticket);
    }

    @Override
    public void stopDepartment(Department department) throws RemoteException {
        this.manager.stopDepartment(department);
    }
}
