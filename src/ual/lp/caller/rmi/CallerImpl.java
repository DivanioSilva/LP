/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.caller.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import ual.lp.server.objects.Ticket;

/**
 *
 * @author Divanio Silva
 */
public class CallerImpl extends UnicastRemoteObject implements CallerInf {

    public CallerImpl() throws RemoteException {
    }

    @Override
    public void testCallback(String msgString) throws RemoteException {
        
        System.out.println("O server enviou a seguinte mensagem: "+msgString+".\nÉ UM CALLBACK :-)");
    }

    @Override
    public Ticket counter() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
