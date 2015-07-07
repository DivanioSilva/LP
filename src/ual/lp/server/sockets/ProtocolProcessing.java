/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.sockets;

import java.io.PrintWriter;

/**
 *
 * @author Pedro
 */
public class ProtocolProcessing {
    private String in,out;
    private PrintWriter sktOut;
    public ProtocolProcessing(PrintWriter sktOut){
        this.sktOut=sktOut;
    }
    
    void inMessage (String in){
        this.in=in;
        
        switch (in) {
            case "Hello from Asura CPRN!":
                System.out.println("Ele disse olá, oh meu deus o que vou fazer ?");
                sktOut.println("Recebido: "+"Ele disse olá, oh meu deus o que vou fazer ?");
            case "Request":
                
            break;
                
                
                
    }
    
}
}