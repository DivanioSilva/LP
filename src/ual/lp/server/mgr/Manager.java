/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.mgr;

import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ual.lp.exceptions.NoTicketsException;
import ual.lp.server.dao.DepartmentDAO;
import ual.lp.server.dao.EmployeeDAO;
import ual.lp.server.dao.TicketDAO;
import ual.lp.server.objects.Department;
import ual.lp.server.objects.Employee;
import ual.lp.server.objects.Ticket;
import ual.lp.server.rmi.ServerRMI;
import ual.lp.server.utils.Serverconfig;

/**
 *
 * @author Pedro
 */
public class Manager {
   
    private EmployeeDAO employeeDAO;
    private TicketDAO ticketDAO;
    private DepartmentDAO departmentDAO;
    private ServerRMI serverRMI;
    private ApplicationContext context;
    private Serverconfig serverconfig;
    private List<Department> departments;
    
    
    public Manager(boolean rmi){
        if(rmi) this.serverRMI = new ServerRMI(this); 
        this.context = new ClassPathXmlApplicationContext("ual/lp/spring/bean.xml");
        employeeDAO = (EmployeeDAO) context.getBean("employeeDAO");
        ticketDAO = (TicketDAO) context.getBean("ticketDAO");
        departmentDAO = (DepartmentDAO) context.getBean("departmentDAO");
        serverconfig = (Serverconfig) context.getBean("serverConfig");
        departments = serverconfig.getDepartments();
        departmentDAO.loadDepartmens(departments);
        
        
    }
//
//    public Manager(Manager manager) {
//        this.serverRMI = new ServerRMI(manager);
//        this.context = new ClassPathXmlApplicationContext("ual/lp/spring/bean.xml");
//        employeeDAO = (EmployeeDAO) context.getBean("employeeDAO");
//        ticketDAO = (TicketDAO) context.getBean("ticketDAO");
//    }
//    
    
   
    public void insertEmployee(Employee employee){
//        employeeDAO.insertEmployee(employee);
        
    }
    
    /**
     * Método que o Dispenser irá invocar
     * @param o nome do departamento como esta na DB
     * @return o número o ticket que ele irá imprimir.
     */
    public String autoCreateTicket(String dept){
        return this.getTicketDAO().autoCreateTicket(dept);
    }
    
    public void transferTicket(Ticket ticket){
        this.getTicketDAO().transferTicket(ticket);
    }
    
    public void insertTicket(Ticket ticket){
//        this.ticketDAO.insertTicket(ticket);
    }
    
    public void closeTicket(Ticket ticket){
        this.getTicketDAO().closeTicket(ticket);
    }
    
    /**
     * Método utilizado para criar um novo ticket solicitado pelo dispenser.
     * @param number do server.
     * @param idDept do dispenser.
     */
    public void createTicket(int number, int idDept){
        this.getTicketDAO().createTicket(number, idDept);
    }
    
    public Ticket getNextTicket(Employee employee) throws NoTicketsException{
        //employee.getDepartment().getId()
        
        return this.getTicketDAO().getNextTicket(employee);
    }
    
    public void verifyEmployee(Employee employee){
        this.getEmployeeDAO().verifyEmployee(employee);
    }
    public void loadDepartments (List<Department> departments){
        
    }

    /**
     * @return the employeeDAO
     */
    public EmployeeDAO getEmployeeDAO() {
        return employeeDAO;
    }

    /**
     * @param employeeDAO the employeeDAO to set
     */
    public void setEmployeeDAO(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    /**
     * @return the ticketDAO
     */
    public TicketDAO getTicketDAO() {
        return ticketDAO;
    }

    /**
     * @param ticketDAO the ticketDAO to set
     */
    public void setTicketDAO(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    /**
     * @return the serverRMI
     */
    public ServerRMI getServerRMI() {
        return serverRMI;
    }

    /**
     * @param serverRMI the serverRMI to set
     */
    public void setServerRMI(ServerRMI serverRMI) {
        this.serverRMI = serverRMI;
    }

    /**
     * @return the context
     */
    public ApplicationContext getContext() {
        return context;
    }

    /**
     * @param context the context to set
     */
    public void setContext(ApplicationContext context) {
        this.context = context;
    }
   
}
