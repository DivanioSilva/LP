/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.sockets;

import java.io.PrintWriter;
import ual.lp.server.mgr.Manager;

/**
 *
 * @author Pedro
 */
public class ProtocolProcessing {
    private String in,out;
    private PrintWriter sktOut;
    private Manager manager;
    
    
    
    
    public ProtocolProcessing(PrintWriter sktOut){
        this.sktOut=sktOut;
    }
    
    
     
    public ProtocolProcessing(PrintWriter sktOut,Manager manager){
        this.sktOut=sktOut;
        this.manager = manager;
    }
    
    
    void inMessage (String in){
        this.in=in;
        
        switch (in) {
            case "Hello from Asura CPRN!":
                System.out.println("Ele disse olá, oh meu deus o que vou fazer ?");
                
                //corrigir
                new Manager(false).autoCreateTicket("Secretaria");

                sktOut.println("Recebido: "+"Ele disse olá, oh meu deus o que vou fazer ?");
                
                // Pckg ual.lp.server.mgr/Manager.java
                // Metodo:autocreateticket (departamento) devolve numero
                // 
            case "Request":
                
            break;
                
                
                
    }
    
}
}