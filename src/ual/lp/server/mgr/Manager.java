/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.mgr;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ual.lp.exceptions.NoTicketsException;
import ual.lp.server.dao.EmployeeDAO;
import ual.lp.server.dao.TicketDAO;
import ual.lp.server.objects.Employee;
import ual.lp.server.objects.Ticket;

/**
 *
 * @author Pedro
 */
public class Manager {
   
    private EmployeeDAO employeeDAO;
    private TicketDAO ticketDAO;
    ApplicationContext context;
    
    public Manager(){
        this.context = new ClassPathXmlApplicationContext("ual/lp/spring/bean.xml");
        employeeDAO = (EmployeeDAO) context.getBean("employeeDAO");
        ticketDAO = (TicketDAO) context.getBean("ticketDAO");
    }
   
    public void insertEmployee(Employee employee){
//        employeeDAO.insertEmployee(employee);
        
    }
    
    /**
     * Método que o Dispenser irá invocar
     * @param o nome do departamento como esta na DB
     * @return o número o ticket que ele irá imprimir.
     */
    public String autoCreateTicket(String dept){
        return this.ticketDAO.autoCreateTicket(dept);
    }
    
    public void transferTicket(Ticket ticket){
        this.ticketDAO.transferTicket(ticket);
    }
    
    public void insertTicket(Ticket ticket){
//        this.ticketDAO.insertTicket(ticket);
    }
    
    public void closeTicket(Ticket ticket){
        this.ticketDAO.closeTicket(ticket);
    }
    
    /**
     * Método utilizado para criar um novo ticket solicitado pelo dispenser.
     * @param number do server.
     * @param idDept do dispenser.
     */
    public void createTicket(int number, int idDept){
        this.ticketDAO.createTicket(number, idDept);
    }
    
    public Ticket getNextTicket(Employee employee) throws NoTicketsException{
        //employee.getDepartment().getId()
        
        return this.ticketDAO.getNextTicket(employee);
    }
    
    public void verifyEmployee(Employee employee){
        this.employeeDAO.verifyEmployee(employee);
    }
}
