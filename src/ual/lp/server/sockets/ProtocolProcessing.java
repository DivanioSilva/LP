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
 * @author Pedro
 */
public class ProtocolProcessing {

    private String in, out;
    private PrintWriter sktOut;
    private Manager manager;

    public ProtocolProcessing(PrintWriter sktOut) {
        this.sktOut = sktOut;
    }

    public ProtocolProcessing(PrintWriter sktOut, Manager manager) {
        this.sktOut = sktOut;
        this.manager = manager;
    }

    void inMessage(String in) {
        this.in = in;
        List<String> departamentos;
        String[] splitedProtocol;

        //Separar a messagem do protocolo
        //Ex: Request:Tesouraria- >  [0] Request  e [1] Tesouraria
        
       
try {
            
            splitedProtocol = in.split(":");
            
            switch (splitedProtocol[0]) {
                case "Hello from Asura CPRN!":
                    System.out.println("Ele disse olá, oh meu deus o que vou fazer ?");
                    departamentos = new Manager(false).getDepartmentsString();
                    String list = departamentos.toString();
                    String csv = list.substring(1, list.length() - 1).replace(", ", ",");
                    
                    System.out.println("*** Mensagem tratada *** " + "BUTTONS:" + csv + " ***");
                    
                    System.out.println("*** toString departamentos*** " + departamentos.toString() + " ***");
                    
                    sktOut.println("BUTTONS:" + csv);
                    
                    break;
                
                case "Request":
                    String msg;
                //corrigir

                // Pckg ual.lp.server.mgr/Manager.java
                    // Metodo:autocreateticket (departamento) devolve numero
                    System.out.println("*** Antes de pedir ***" + splitedProtocol[1] + " ***");
                    msg = new Manager(false).autoCreateTicket(splitedProtocol[1]);
                    
                    System.out.println("*** Depois de pedir ***" + "TICKET:" + msg + " ***");
                    sktOut.println("TICKET:" + msg);

               // new Manager(false).autoCreateTicket(splitedProtocol[1]);
                    // new Manager(false).autoCreateTicket("Tesouraria");
                    break;
               
            }
        } catch (Exception e) {
            System.out.println("Cliente desligou-se "+ e.getMessage());
            System.exit(-1);
        }
    }
}
