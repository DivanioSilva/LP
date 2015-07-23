/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import ual.lp.exceptions.BadConfigurationException;
import ual.lp.exceptions.NoTicketsException;
import ual.lp.server.objects.Employee;
import ual.lp.server.objects.Ticket;

/**
 *
 * @author Divanio Silva
 */
public interface ServerInf extends Remote {
    
    public String printMessage() throws RemoteException;
    
    public void connect(Employee employee) throws RemoteException, BadConfigurationException;
    
    public void TockTock(Employee employee) throws RemoteException;
    
    public void closeTicket(Ticket ticket) throws RemoteException;
    
    public Ticket getNextTicket(Employee employee) throws RemoteException, NoTicketsException;
    
    public void transferTicket(Ticket ticket) throws RemoteException;
    
    public void resetQueue(Employee employee) throws RemoteException;
    
    public Ticket getLastTicket(Employee employee)throws RemoteException, NoTicketsException;
    
    public void recallTicket(Ticket ticket) throws RemoteException;
    
}

/*
    public String autoCreateTicket(String dept){
        return this.ticketDAO.autoCreateTicket(dept);
    }
    
    public void transferTicket(Ticket ticket){
        this.ticketDAO.transferTicket(ticket);
    }
    
    public void insertTicket(Ticket ticket){
//        this.ticketDAO.insertTicket(ticket);
    }
    
    public void closeTicket(Ticket ticket){
        this.ticketDAO.closeTicket(ticket);
    }
    
    public void createTicket(int number, int idDept){
        this.ticketDAO.createTicket(number, idDept);
    }
    
    public Ticket getNextTicket(Employee employee) throws NoTicketsException{
        //employee.getDepartment().getId()
        
        return this.ticketDAO.getNextTicket(employee);
    }
    
    public void verifyEmployee(Employee employee){
        this.employeeDAO.verifyEmployee(employee);
    }
*/