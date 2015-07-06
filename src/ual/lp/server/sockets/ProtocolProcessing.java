/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.sockets;

/**
 *
 * @author Pedro
 */
public class ProtocolProcessing {
    String in,out;
    
    public ProtocolProcessing(){
       
    }
    
    void inMessage (String in){
        this.in=in;
        
        switch (in) {
            case "Hello from Asura CPRN!":
                System.out.println("Ele disse olá, oh meu deus o que vou fazer ?");
                
            case "Request":
                
            break;
                
                
                
    }
    
}
}