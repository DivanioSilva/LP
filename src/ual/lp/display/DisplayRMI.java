/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.display;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import ual.lp.caller.rmi.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ual.lp.caller.gui.CallerGUI;
import ual.lp.caller.utils.Config;
import ual.lp.server.objects.Ticket;
import ual.lp.server.rmi.ServerInf;
import ual.lp.server.rmi.ServerToDisplayInf;

/**
 *
 * @author Divanio Silva
 */
public class DisplayRMI {

    //172.16.214.237
    //172.16.120.77
    private String host;
    public static final int PORT = 1099;
    private String myIP;
    private String filePath;
    private CallerGUI callerGUI;
    private DisplayApp displayApp;
    private boolean serverOn = false;
    private ServerToDisplayInf remoteObj;
    static final org.apache.log4j.Logger displayLog = org.apache.log4j.Logger.getLogger("displayLogger");

//    public DisplayRMI(CallerGUI callerGUI) throws RemoteException, NotBoundException {
//        this.callerGUI = callerGUI;
//        
//        System.setProperty("java.rmi.server.hostname", "localhost");//informar isso no relatório
//
//        Registry registry = LocateRegistry.getRegistry(HOST, PORT);
//        ServerInf objRemoto = (ServerInf) registry.lookup("response");
//        this.callerGUI.setRemoteObject(objRemoto);
//
//    }

    public DisplayRMI(DisplayApp displayApp) {
        this.myIP = displayApp.getMyIP();
        this.host = displayApp.getServerIP();
        this.filePath = displayApp.getFilePath();
        try {
            System.setProperty("java.rmi.server.hostname", myIP);//informar isso no relatório
            Registry registry = LocateRegistry.getRegistry(host, PORT);
            remoteObj = (ServerToDisplayInf) registry.lookup("display");

            DisplayInf callback = new DisplayImpl(this);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                
            }

            remoteObj.connect(callback);
            this.displayFirstTicketList(remoteObj.getFirstTicketList());
            this.serverOn = true;
            System.out.println("Ligação ao servidor com sucesso");
//            objRemoto.connect(callback);

        } catch (NotBoundException e) {
            System.out.println("Não encontrada a referência para o objRemoto");
//            System.out.println("Deu merda no client.");
            System.out.println(e.getMessage());
            displayLog.error("Não encontrada a referência para o objRemoto"+e);
            this.serverOn = false;

        } catch (RemoteException e) {
            System.out.println("Problema com a ligação ao servidor");
            displayLog.error("Problema com a ligação ao servidor", e);
            this.serverOn = false;

        }
    }

    private void displayFirstTicketList(List<Ticket> tickets) {

        OutputStream outputStream = null;

        BufferedWriter writer = null;
        try {
            //Local do ficheiro será configurado pelo ficheiro de conf do display
            writer = new BufferedWriter(new FileWriter(filePath));
//
            for (Ticket ticket : tickets) {
                if (ticket.isLastCalled()) {

                    writer.write("<p style=\"color: red\">" + ticket.getDepartment().getName() + ": " + ticket.getDepartment().getAbbreviation() + ""
                            + ticket.getNumberticket() + " Mesa: " + ticket.getEmployee().getDeskNumber() + "</p>");
                } else {

                    if (ticket.getNumberticket() == -1) {
                        writer.write("<p>" + ticket.getDepartment().getName() + ": " + "</p>");
                    } else {
                        writer.write("<p>" + ticket.getDepartment().getName() + ": " + ticket.getDepartment().getAbbreviation() + ""
                                + ticket.getNumberticket() + " Mesa: " + ticket.getEmployee().getDeskNumber() + "</p>");
                    }

                }

            }
            //writer.write(lista.get(i));
        } catch (IOException e) {
            displayLog.error("Erro ao ler o ficheiro."+e);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.err.println("Erro ao actualizar o ficheiro.");
                displayLog.error("Erro ao ler o ficheiro."+e);
            }
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

    /**
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @return the remoteObj
     */
    public ServerToDisplayInf getRemoteObj() {
        return remoteObj;
    }

}
