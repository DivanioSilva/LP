/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.caller.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import ual.lp.server.objects.Employee;
import ual.lp.server.objects.Ticket;

/**
 *
 * @author Divanio Silva
 */
public interface CallerInf extends Remote {

    public Ticket counter() throws RemoteException;

    public String testCallback(String serverMsg) throws RemoteException;

    public List<Employee> sendEmployees(List<Employee> employees) throws RemoteException;
}
