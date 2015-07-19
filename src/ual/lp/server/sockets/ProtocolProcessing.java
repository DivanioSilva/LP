/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.sockets;

import java.io.PrintWriter;

import java.util.List;
import ual.lp.server.mgr.Manager;

/**
 * 
 * Processamento do protocolo de comunicação entre o android e o servidor
 * @author Pedro Tomás
 * 
 */
public class ProtocolProcessing {

    private String in, out;
    private PrintWriter sktOut;
    private Manager manager;

    //Contrutores
    public ProtocolProcessing(PrintWriter sktOut) {
        this.sktOut = sktOut;
    }

    public ProtocolProcessing(PrintWriter sktOut, Manager manager) {
        this.sktOut = sktOut;
        this.manager = manager;
    }

    // Recepção da mensagem
    void inMessage(String in) {
        this.in = in;
        List<String> departamentos;
        String[] splitedProtocol;

        
        //Separar a messagem do protocolo
        //Ex: Request:Tesouraria- >  splitedProtocol[0] Request  e splitedProtocol[1] Tesouraria
        try {

            splitedProtocol = in.split(":");

            // Processamento do protocolo
            switch (splitedProtocol[0]) {

                // Inicio da comunicação - Asura espera os departamentos
                case "Hello from Asura CPRN!":

                    System.out.println("#ProtocolProcessing# - Asura iniciou o protocolo");
                    
                    departamentos = manager.getDepartmentsString(); 
                    
                    String list = departamentos.toString();
                    
                    String departmentsCsv = list.substring(1, list.length() - 1).replace(", ", ",");

                    System.out.println("#ProtocolProcessing# - Mensagem tratada, os departamentos serão" + departmentsCsv );

                    sktOut.println("BUTTONS;" + departmentsCsv);

                    break;

                case "Request":
                    String msg;

                    // Pckg ual.lp.server.mgr/Manager.java
                    // Metodo:autocreateticket (departamento) devolve Letra+ Numero (Ex. T23(
                    System.out.println("#ProtocolProcessing# - Pedido tickets - " + splitedProtocol[1]);

                    msg = manager.autoCreateTicket(splitedProtocol[1]);

                    System.out.println("#ProtocolProcessing# - Ticket a enviar - " + "TICKET;" + msg + " ***");
                    sktOut.println("TICKET;" + msg);

                    break;

            }
        } catch (Exception e) {
            System.err.println("#ProtocolProcessing# - Cliente desligou-se " + e.getMessage());
            System.exit(-1);
        }
    }
}
