/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.mgr;

import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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
        employeeDAO.insertEmployee(employee);
        
    }
    
    public Employee getEmployee(Employee employee){
        return employeeDAO.getEmployee(employee);
    }
    
    public List <Ticket> getTickets(){
        return ticketDAO.getTicket();
    }
    
    
    public void insertTicket(Ticket ticket){
        ticketDAO.insertTicket(ticket);
    }
    /**
     * @return the context
     */
//    public ApplicationContext getContext() {
//        return context;
//    }

    /**
     * @param context the context to set
     */
//    public void setContext(ApplicationContext context) {
//        this.context = context;
//    }

}
