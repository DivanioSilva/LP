/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.display;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import ual.lp.server.objects.Ticket;

/**
 *
 * @author Divanio Silva
 */
public interface DisplayInf extends Remote {
    
    public void sourceToDisplay(List<Ticket> tickets) throws RemoteException;

}
