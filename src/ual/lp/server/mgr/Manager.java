/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.mgr;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ual.lp.exceptions.BadConfigurationException;
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
    
    static final Logger serverLog = Logger.getLogger("serverLogger");
    static final Logger callerTicketCallLog = Logger.getLogger("callerTicketCall");
    static final Logger callerTransfLog = Logger.getLogger("callerTransfLog");
    private EmployeeDAO employeeDAO;
    private TicketDAO ticketDAO;
    private DepartmentDAO departmentDAO;
    private ServerRMI serverRMI;
    private ApplicationContext context;
    private Serverconfig serverconfig;
    private List<Department> departments;
    private List<Employee> employees;
    
    public Manager(boolean rmi) {
        
        if (rmi) {
            this.serverRMI = new ServerRMI(this);
        }
        this.context = new ClassPathXmlApplicationContext("ual/lp/spring/bean.xml");
        employeeDAO = (EmployeeDAO) context.getBean("employeeDAO");
        ticketDAO = (TicketDAO) context.getBean("ticketDAO");
        departmentDAO = (DepartmentDAO) context.getBean("departmentDAO");
        serverconfig = (Serverconfig) context.getBean("serverConfig");
        departments = serverconfig.getDepartments();
        departmentDAO.loadDepartmens(departments);
        employees = new LinkedList<>();
    }
//
//    public Manager(Manager manager) {
//        this.serverRMI = new ServerRMI(manager);
//        this.context = new ClassPathXmlApplicationContext("ual/lp/spring/bean.xml");
//        employeeDAO = (EmployeeDAO) context.getBean("employeeDAO");
//        ticketDAO = (TicketDAO) context.getBean("ticketDAO");
//    }
//    

    public void insertEmployee(Employee employee) {
//        employeeDAO.insertEmployee(employee);

    }
    
    public void connect(Employee employee) throws BadConfigurationException {
        
        try {
            this.verifyEmployeeConfig(employee);
            this.verifyEmployee(employee);
            //saber o id do gajo.
            employee.setEmpNumber(this.getEmpID(employee));
            
            this.addEmployee(employee);
            
            this.employeesCallback(employee.getDepartment());
        } catch (BadConfigurationException e) {
            serverLog.error("O caller apresenta configurações inválidas.", e);
            throw new BadConfigurationException("O caller apresenta configurações inválidas.");
        }
    }

    /**
     * Método que o Dispenser irá invocar
     *
     * @param o nome do departamento como esta na DB
     * @return o número o ticket que ele irá imprimir.
     */
    public String autoCreateTicket(String dept) {
        return this.getTicketDAO().autoCreateTicket(dept);
    }
    
    public void transferTicket(Ticket ticket) {
        callerTransfLog.info("Transfer: from " + ticket.getEmployee().getName() + ", ticket " + ticket.getDepartment().getAbbreviation() + "" + ticket.getNumberticket() + " to " + ticket.getTransferId());
        this.getTicketDAO().transferTicket(ticket);
    }
    
    public void insertTicket(Ticket ticket) {
//        this.ticketDAO.insertTicket(ticket);
    }
    
    public void closeTicket(Ticket ticket) {
        callerTicketCallLog.info("Closed: " + ticket.getEmployee().getName() + " ticket " + ticket.getDepartment().getAbbreviation() + "" + ticket.getNumberticket());
        this.getTicketDAO().closeTicket(ticket);
    }
    
    public int getEmpID(Employee employee) {
        return this.getEmployeeDAO().getEmployeeID(employee);
    }

    /**
     * Método utilizado para criar um novo ticket solicitado pelo dispenser.
     *
     * @param number do server.
     * @param idDept do dispenser.
     */
    public void createTicket(int number, int idDept) {
        this.getTicketDAO().createTicket(number, idDept);
    }
    
    public Ticket getNextTicket(Employee employee) throws NoTicketsException {
        Ticket ticket = new Ticket();
        //employee.getDepartment().getId()
        ticket = this.getTicketDAO().getNextTicket(employee);
        callerTicketCallLog.info("Call: " + employee.getName() + " ticket " + ticket.getDepartment().getAbbreviation() + "" + ticket.getNumberticket());
        return ticket;
    }
    
    public void verifyEmployee(Employee employee) {
        this.getEmployeeDAO().verifyEmployee(employee);
    }
//    public void loadDepartments (List<Department> departments){
//        
//    }

    /**
     * verifica configurações do colaborador
     *
     * @param employee
     * @return true se as configurações estiverem correctas(Departamento e
     * abreviação no ficheiro de configuração do colaborador), false se as
     * configurações estiverem erradas
     */
    public void verifyEmployeeConfig(Employee employee) throws BadConfigurationException {
        
        for (int i = 0; i < this.departments.size(); i++) {
            if (employee.getDepartment().getName().equals(this.departments.get(i).getName())
                    && employee.getDepartment().getAbbreviation().equals(this.departments.get(i).getAbbreviation())) {
                return;
            }
        }
        throw new BadConfigurationException("Configurações incorrectas");
    }
    
    public void addEmployee(Employee employee) {
        for (int i = 0; i < this.employees.size(); i++) {
            if (employee.getName().equals(employees.get(i).getName())) {
                employees.remove(i);
            }
        }
        employees.add(employee);
    }

    /**
     * Método usado para o callback dos clientes com a lista de todos os
     * colaboradores pertencentes ao mesmo departamento
     *
     * @param department
     */
    public void employeesCallback(Department department) {
        
        List<Employee> departmentEmployees = this.getDepartmentEmployees(department);
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getDepartment().getName().equals(department.getName())) {
                try {
                    employees.get(i).getCallerInf().updateEmployees(departmentEmployees);
                    
                } catch (RemoteException ex) {
                    serverLog.debug("A remover o " + employees.get(i).getName() + "da lista dos employees");
                    System.out.println("A remover o " + employees.get(i).getName() + "da lista dos employees");
                    employees.remove(i);
                    
                    employeesCallback(department);
                    return;
                    
                }
            }
        }
        
        System.out.println("Lista a mandar para os employees: ");
        for (Employee emp : departmentEmployees) {
            System.out.println(emp.getName());
        }
    }

    /**
     * Método para devolver a lista de colaboradores de um determinado
     * departamento
     *
     * @param department
     * @return Lista de colaboradores
     */
    public List<Employee> getDepartmentEmployees(Department department) {
        
        List<Employee> departmentEmployees = new LinkedList<>();
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getDepartment().getName().equals(department.getName())) {
                departmentEmployees.add(employees.get(i));
            }
        }
        return departmentEmployees;
    }

    /**
     * Método usado para receber os nomes de todos os departamentos em formato
     * String
     *
     * @return Uma lista de departamentos
     */
    public List<String> getDepartmentsString() {
        List<String> depts = new LinkedList<>();
        for (Department d : this.departments) {
            depts.add(d.getName());
        }
        return depts;
    }
    
    /**
     * Método para encerrar todos os tickets que ainda possam estar em aberto no final do dia.
     * Uma thread para escutar o horário e acionar o método????
     */
    public void closeDay() {
        serverLog.info("Feito reset das filas no final do dia.");
        this.ticketDAO.closeDay();
    }
    
    /**
     * Faz o reset da fila de um determinado departamento.
     * Método utilizado pelo user com privilégios de admin.
     * @param department 
     */
    public void resetQueue(Department department) {
        serverLog.info("Reset da fila: " + department.getName());
        this.ticketDAO.resetQueue(department);
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
