/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.sockets;

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import ual.lp.server.mgr.Manager;
import ual.lp.server.objects.Department;

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

        switch (in) {
            case "Hello from Asura CPRN!":
                System.out.println("Ele disse olá, oh meu deus o que vou fazer ?");
                departamentos = new Manager(false).getDepartmentsString();
                String list = departamentos.toString();
                String csv = list.substring(1, list.length() - 1).replace(", ", ",");
                System.out.println("#######################");
                System.out.println("#######################");
                System.out.println(csv);
                System.out.println("#######################");
                System.out.println(departamentos.toString());
                System.out.println("#######################");

                sktOut.println("Recebido: " + "Ele disse olá, oh meu deus o que vou fazer ?");
 
                break;
            case "Request":

                //corrigir

                // Pckg ual.lp.server.mgr/Manager.java
                // Metodo:autocreateticket (departamento) devolve numero
                
                sktOut.println("---------------- WTF ----------------");
                new Manager(false).autoCreateTicket("Secretaria");
                new Manager(false).autoCreateTicket("Tesouraria");

                break;

        }

    }
}
