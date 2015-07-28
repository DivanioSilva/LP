/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.mgr;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ual.lp.display.DisplayInf;
import ual.lp.exceptions.BadConfigurationException;
import ual.lp.exceptions.NoTicketsException;
import ual.lp.server.dao.DepartmentDAO;
import ual.lp.server.dao.EmployeeDAO;
import ual.lp.server.dao.TicketDAO;
import ual.lp.server.objects.Department;
import ual.lp.server.objects.Employee;
import ual.lp.server.objects.Ticket;
import ual.lp.server.rmi.ServerRMI;
import ual.lp.server.sockets.SocketSrv;
import ual.lp.server.utils.Serverconfig;

/**
 *
 * @author Pedro
 */
public class Manager {

    static final Logger serverLog = Logger.getLogger("serverLogger");
    static final Logger callerTicketCallLog = Logger.getLogger("callerTicketCall");
    static final Logger callerTransfLog = Logger.getLogger("callerTransfLog");
    static final Logger loginLog = Logger.getLogger("login");
    private EmployeeDAO employeeDAO;
    private TicketDAO ticketDAO;
    private DepartmentDAO departmentDAO;
    private ServerRMI serverRMI;
    private ApplicationContext context;
    private Serverconfig serverconfig;
    private String serverIP;
    private String closeDay;
    private String remoteURL;
    private String remoteForm;
    private List<Department> departments;
    private List<Employee> employees;
    private DisplayInf displayInf;
    private List<Ticket> displayTickets;
    private Thread serviceThread;
    private Object remoteLock;
    private Thread socketThread;
    

    public Manager(boolean rmi) {

        this.context = new ClassPathXmlApplicationContext("ual/lp/spring/bean.xml");
        employeeDAO = (EmployeeDAO) context.getBean("employeeDAO");
        ticketDAO = (TicketDAO) context.getBean("ticketDAO");
        departmentDAO = (DepartmentDAO) context.getBean("departmentDAO");
        serverconfig = (Serverconfig) context.getBean("serverConfig");
        serverIP = serverconfig.getServerIP();
        closeDay = serverconfig.getCloseDay();
        remoteURL = serverconfig.getRemoteURL();
        remoteForm = serverconfig.getRemoteForm();
        departments = serverconfig.getDepartments();
        departmentDAO.loadDepartmens(departments);
        employees = new LinkedList<>();
        displayTickets = new LinkedList<>();
        remoteLock = new Object();
        initDisplayTickets();
        serviceThread = new Thread(new ServiceThread(this));
        socketThread = new Thread(new SocketSrv(5007, this));
        serviceThread.start();
        socketThread.start();
        if (rmi) {
            this.serverRMI = new ServerRMI(this);
        }
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
        synchronized (remoteLock) {

            try {
                this.verifyEmployeeConfig(employee);
                this.verifyEmployee(employee);
                //saber o id do gajo.
                employee.setEmpNumber(this.getEmpID(employee));

                this.addEmployee(employee);
                loginLog.info("Begin - Dept: " + employee.getDepartment().getName() + ". ID: " + employee.getEmpNumber() + " " + employee.getName());
                this.employeesCallback(employee.getDepartment());
            } catch (BadConfigurationException e) {
                serverLog.error("O caller apresenta configurações inválidas.", e);
                throw new BadConfigurationException("O caller apresenta configurações inválidas.");
            }
        }

    }

    /**
     * Método que o Dispenser irá invocar
     *
     * @param o nome do departamento como esta na DB
     * @return o número o ticket que ele irá imprimir.
     */
    public String autoCreateTicket(String dept) {
        synchronized (remoteLock) {
            
            for (Department d: departments) {
                if (d.getName().equals(dept)) {
                    if (d.isStopped()) {
                        System.out.println("INACTIVE");
                        return "INACTIVE";
                    }
                }
            }
            
            //        String response[] = this.getTicketDAO().autoCreateTicket(dept).split(",");
//        return response[0]+getRemoteURL()+response[1];
//        System.out.println(response);
            
            String response[] = this.getTicketDAO().autoCreateTicket(dept).split(",");
            System.out.println(response[0] + "," + getRemoteURL() + "?ck=" + response[1] + "," +getRemoteForm());
            return response[0] + "," + getRemoteURL() + "?ck=" + response[1] + "," +getRemoteForm();
        }

    }

    public void transferTicket(Ticket ticket) {
        synchronized (remoteLock) {
            callerTransfLog.info("Transfer: from " + ticket.getEmployee().getName() + ", ticket " + ticket.getDepartment().getAbbreviation() + "" + ticket.getNumberticket() + " to " + ticket.getTransferId());
            this.getTicketDAO().transferTicket(ticket);
        }

    }

    public void insertTicket(Ticket ticket) {
//        this.ticketDAO.insertTicket(ticket);
    }

    public void closeTicket(Ticket ticket) {
        synchronized (remoteLock) {
            callerTicketCallLog.info("Closed: " + ticket.getEmployee().getName() + " ticket " + ticket.getDepartment().getAbbreviation() + "" + ticket.getNumberticket());
            this.getTicketDAO().closeTicket(ticket);
        }

    }

    public int getEmpID(Employee employee) {
        synchronized (remoteLock) {
            return this.getEmployeeDAO().getEmployeeID(employee);
        }

    }

    /**
     * Método utilizado para criar um novo ticket solicitado pelo dispenser.
     *
     * @param number do server.
     * @param idDept do dispenser.
     */
    public void createTicket(int number, int idDept) {
        synchronized (remoteLock) {
            this.getTicketDAO().createTicket(number, idDept);
        }

    }

    public void recallTicket(Ticket ticket) {
        synchronized (remoteLock) {
            this.addDisplayTicket(ticket);
            try {

                this.displayInf.sourceToDisplay(displayTickets);

                System.out.println("Lista de tickets enviada ao Display");
            } catch (RemoteException e) {
                this.displayInf = null;
                System.err.println("Erro ao contactar o display.");
            } catch (NullPointerException e) {
                System.err.println("O display ainda não esta disponível.");

            }
        }

    }

    public Ticket getNextTicket(Employee employee) throws NoTicketsException {
        synchronized (remoteLock) {
            Ticket ticket = new Ticket();
            //employee.getDepartment().getId()
            ticket = this.getTicketDAO().getNextTicket(employee);

            this.addDisplayTicket(ticket);
            try {

                this.displayInf.sourceToDisplay(displayTickets);

                System.out.println("Lista de tickets enviada ao Display");
            } catch (RemoteException e) {
                this.displayInf = null;
                System.err.println("Erro ao contactar o display.");
            } catch (NullPointerException e) {
                System.err.println("O display ainda não esta disponível.");

            }

            callerTicketCallLog.info("Call: " + employee.getName() + " ticket " + ticket.getDepartment().getAbbreviation() + "" + ticket.getNumberticket());
            return ticket;
        }

    }

    public void verifyEmployee(Employee employee) {
        synchronized (remoteLock) {
            this.getEmployeeDAO().verifyEmployee(employee);
        }

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
        synchronized (remoteLock) {
            for (int i = 0; i < this.getDepartments().size(); i++) {
                if (employee.getDepartment().getName().equals(this.getDepartments().get(i).getName())
                        && employee.getDepartment().getAbbreviation().equals(this.getDepartments().get(i).getAbbreviation())) {
                    return;
                }
            }
            throw new BadConfigurationException("Configurações incorrectas");
        }

    }

    public void addEmployee(Employee employee) {
        synchronized (remoteLock) {
            for (int i = 0; i < this.employees.size(); i++) {
                if (employee.getName().equals(employees.get(i).getName())) {
                    employees.remove(i);
                }
            }
            employees.add(employee);
        }

    }

    public void addDisplayTicket(Ticket ticket) {
        synchronized (remoteLock) {
            ticket.setLastCalled(true);
            boolean foundOldTicket = false;
            for (int i = 0; i < this.displayTickets.size(); i++) {
                if (ticket.getDepartment().getName().equals(this.displayTickets.get(i).getDepartment().getName())) {
                    displayTickets.set(i, ticket);
                    foundOldTicket = true;
                } else {
                    this.displayTickets.get(i).setLastCalled(false);
                }
            }
            if (!foundOldTicket) {
                displayTickets.add(ticket);
            }
        }

    }

    private void initDisplayTickets() {
        synchronized (remoteLock) {
            for (Department d : getDepartments()) {
                Ticket ticket = new Ticket();
                ticket.setDepartment(d);
                ticket.setLastCalled(false);
                ticket.setNumberticket(-1);
                displayTickets.add(ticket);
            }
        }

    }

    /**
     * Método usado para o callback dos clientes com a lista de todos os
     * colaboradores pertencentes ao mesmo departamento
     *
     * @param department
     */
    public void employeesCallback(Department department) {

        synchronized (remoteLock) {
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

//        System.out.println("Lista a mandar para os employees: ");
//        for (Employee emp : departmentEmployees) {
//            System.out.println(emp.getName());
//        }
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
        synchronized (remoteLock) {
            List<Employee> departmentEmployees = new LinkedList<>();
            for (int i = 0; i < employees.size(); i++) {
                if (employees.get(i).getDepartment().getName().equals(department.getName())) {
                    departmentEmployees.add(employees.get(i));
                }
            }
            return departmentEmployees;
        }

    }
    /**
     * Método para parar/pausar um departamento
     * @param department 
     */
    public void stopDepartment(Department department) {
        synchronized (remoteLock) {
            for (Department d : departments) {
                if (d.getName().equals(department.getName())) {
                    d.setStopped(true);
                    break;
                }
            }
        }

    }
    /**
     * Método para resumir o funcionamento de um departamento
     * (após uma pausa)
     * @param department 
     */
    public void startDepartment(Department department) {
        synchronized (remoteLock) {
            for (Department d : departments) {
                if (d.getName().equals(department.getName())) {
                    d.setStopped(false);
                    break;
                }
            }
        }

    }
    
    

    /**
     * Método usado para receber os nomes de todos os departamentos em formato
     * String
     *
     * @return Uma lista de departamentos
     */
    public List<String> getDepartmentsString() {
        synchronized (remoteLock) {
            List<String> depts = new LinkedList<>();
            for (Department d : this.getDepartments()) {
                depts.add(d.getName());
            }
            return depts;
        }

    }

    /**
     * Método para encerrar todos os tickets que ainda possam estar em aberto no
     * final do dia. Uma thread para escutar o horário e acionar o método????
     */
    public void closeDay() {
        synchronized (remoteLock) {
            serverLog.info("Feito reset das filas no final do dia.");
            this.ticketDAO.closeDay();
        }

    }

    /**
     * Faz o reset da fila de um determinado departamento. Método utilizado pelo
     * user com privilégios de admin.
     *
     * @param department
     */
    public void resetQueue(Employee employee) {
        synchronized (remoteLock) {
            serverLog.info("Reset da fila: " + employee.getDepartment().getName());
            this.ticketDAO.resetQueue(employee);
        }

    }
    /**
     * devolve o ultimo ticket tirado por um cliente para um determinado departamento
     * @param department
     * @return
     * @throws NoTicketsException 
     */
    public Ticket getLastTicket(Department department) throws NoTicketsException {
        synchronized (remoteLock) {
            return this.ticketDAO.getLastTicket(department);
        }

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

    /**
     * @return the displayInf
     */
    public DisplayInf getDisplayInf() {
        return displayInf;
    }

    /**
     * @param displayInf the displayInf to set
     */
    public void setDisplayInf(DisplayInf displayInf) {
        this.displayInf = displayInf;
    }

    /**
     * @return the remoteURL
     */
    public String getRemoteURL() {
        return remoteURL;
    }

    /**
     * @return the remoteForm
     */
    public String getRemoteForm() {
        return remoteForm;
    }

    /**
     * @return the serverIP
     */
    public String getServerIP() {
        return serverIP;
    }

    /**
     * @return the displayTickets
     */
    public List<Ticket> getDisplayTickets() {
        return displayTickets;
    }

    /**
     * @param displayTickets the displayTickets to set
     */
    public void setDisplayTickets(List<Ticket> displayTickets) {
        this.displayTickets = displayTickets;
    }

    /**
     * @return the departments
     */
    public List<Department> getDepartments() {
        return departments;
    }

    /**
     * @return the employees
     */
    public List<Employee> getEmployees() {
        return employees;
    }

    /**
     * @return the remoteLock
     */
    public Object getRemoteLock() {
        return remoteLock;
    }

}
