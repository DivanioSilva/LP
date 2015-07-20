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
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import ual.lp.server.objects.Ticket;

/**
 *
 * @author Divanio Silva
 */
public class DisplayImpl extends UnicastRemoteObject implements DisplayInf {
   private DisplayRMI displayRMI;

    public DisplayImpl(DisplayRMI displayRMI) throws RemoteException {
        this.displayRMI = displayRMI;
    }

    @Override
    public void sourceToDisplay(List<Ticket> tickets) throws RemoteException {
        //informar onde o ficheiro irá ser colocado por ficheiro de conf do client

        OutputStream outputStream = null;

        BufferedWriter writer = null;
        try {
            //Local do ficheiro será configurado pelo ficheiro de conf do display
            writer = new BufferedWriter(new FileWriter("C:\\xampp\\htdocs\\test\\madeira.txt"));

            for (Ticket ticket : tickets) {
                if (ticket.isLastCalled()) {
                    writer.write("<p style=\"color: red\">" + ticket.getDepartment().getName() + ": " + ticket.getDepartment().getAbbreviation() + "" 
                    + ticket.getNumberticket() + ". Balcão: "+ticket.getEmployee().getDeskNumber()+"</p>");
                }
                else {
                   writer.write("<p>" + ticket.getDepartment().getName() + ": " + ticket.getDepartment().getAbbreviation() + "" 
                   + ticket.getNumberticket() + ". Balcão: "+ticket.getEmployee().getDeskNumber()+"</p>"); 
                }
                
            }
            //writer.write(lista.get(i));
        } catch (IOException e) {
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.err.println("Erro ao actualizar o ficheiro.");
            }
        }
    }
}
