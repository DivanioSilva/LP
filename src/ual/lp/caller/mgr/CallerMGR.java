/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.caller.mgr;

import javax.swing.JOptionPane;
import ual.lp.caller.dataSource.EmployeeSource;
import ual.lp.caller.dataSource.TicketSource;
import ual.lp.caller.inf.CallerInf;

/**
 *
 * @author Divanio Silva, Pedro Almeida e Pedro Tomás
 */
public class CallerMGR implements CallerInf {
    private EmployeeSource empData;
    private TicketSource ticketSource;
    int qtd = 0;

    public CallerMGR() {
        empData = new EmployeeSource();
        ticketSource = new TicketSource();
        qtd = empData.getData().size();
    }
    
    
    
    @Override
    public String callTicket() {
        //Aceder a um método do servidor para chamar uma senha e imprimi-la na aplicação.
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
//    public void knowNextCallTicket() {
//        //Thread(?) para saber qual será a próxima senha a ser chamada?
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    
    //Devolvo o ArrayList???
    @Override
    public EmployeeSource colleagueList() {
        //Testando a estrutura que irei receber do server.
//        empData = new EmployeeSource();
        
//        int qtd = empData.getData().size();
        System.out.println("MGR imprime: ");
        
        for (int i = 0; i < qtd; i++) {
            System.out.println("Nome: " + empData.getData().get(i).getName() + ", dpt " + empData.getData().get(i).getDepartment());
        }
        return empData;
    }
    
    public String[] employeesList(){
//        int qtd = empData.getData().size();
        String[] list = new String[qtd];
        for (int i = 0; i < qtd; i++) {
            list[i] = empData.getData().get(i).getName();
        }
        return list;
    }
    
    public String[] deptmentList(){
//        int qtd = empData.getData().size();
        String[] list = new String[qtd];
        for (int i = 0; i < qtd; i++) {
            list[i] = empData.getData().get(i).getDepartment();
        }
        return list;
    }

//    @Override
//    public void departmentTransfer(Department[] departments) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    @Override
//    public void numberPeopleWaiting(int qtd) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    //devolve o próximo a ser chamado. Será impresso no caller e actualizado pelo server.
    @Override
    public String showNextCallTicket() {
//        ticketSource = new TicketSource();
        int qtd = ticketSource.getTickets().size();
        String next;

        try {
            for (int i = 0; i < qtd; i++) {
            next = ticketSource.getTickets().get(i+1).getNumberTicket();
//                System.out.println(ticketSource.getTickets().get(i).getNumberTicket());
            
            return next;
        }
            
        } catch (Exception e) {
            System.err.println("Erro no método showNextCallTicket" +e.getMessage());
        }
        return null;
    }
    
    public String showActualTicket(){
        int qtd = ticketSource.getTickets().size();
        String next;
        
        try {
            for (int i = 0; i < qtd; i++) {
            next = ticketSource.getTickets().get(i).getNumberTicket();
//                System.out.println(ticketSource.getTickets().get(i).getNumberTicket());
            //artifícil para simular a actualização da lista pelo server.
            ticketSource.getTickets().remove(i);
            return next;
        }
            
        } catch (Exception e) {
            System.err.println("Erro no método showActualTicket" +e.getMessage());
        }
        return null;
    }
}
