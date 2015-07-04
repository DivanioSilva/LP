/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.caller.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import javax.swing.JOptionPane;
import ual.lp.server.objects.Employee;
import ual.lp.server.objects.Ticket;

/**
 *
 * @author Divanio Silva
 */
public class CallerImpl extends UnicastRemoteObject implements CallerInf {

    public CallerImpl() throws RemoteException {
    }

    @Override
    public String testCallback(String msgString) throws RemoteException {
        String out=null;
        System.err.println("Finalmente o server fez o Callback");
        out = "O server enviou a seguinte mensagem: " + msgString + ".\nÉ UM CALLBACK :-)";
        JOptionPane.showMessageDialog(null, out);
        return out;
    }

    @Override
    public Ticket counter() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Employee> sendEmployees() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
