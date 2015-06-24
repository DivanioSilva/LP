/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Divanio Silva
 */
public class ServerImpl implements ServerInf{

    public ServerImpl() throws RemoteException {
    }
    
    @Override
    public String printMessage() throws RemoteException {
        System.out.println("Estou a imprimir no servidor");
        return "Olá Pedrinho, aqui é o servidor que esta falando :-)";
    }
}
