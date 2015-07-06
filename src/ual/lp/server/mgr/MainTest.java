/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.mgr;

import java.util.logging.Level;
import java.util.logging.Logger;
import ual.lp.caller.utils.Config;
import ual.lp.exceptions.BadConfigurationException;
import ual.lp.server.objects.Department;
import ual.lp.server.objects.Employee;
import ual.lp.server.objects.Ticket;

/**
 *
 * @author Pedro
 */
public class MainTest {

    public static void main(String[] args) {
        Config config = new Config();
        Employee employee;
        Manager mgr = new Manager(false);
//        System.out.println(mgr.getEmployee(new Employee(1, "teste", "teste")).getName());
//        mgr.insertEmployee(new Employee(1, "Pedro Tomas", "Ceu"));
//        mgr.getEmployee(new Employee(5,"Pedro Tomas", "Ceu"));
//        System.out.println(mgr.getTickets());
//        Ticket t = new Ticket();
//        t.setIdTicket(2);
////////        t.setNumberticket(1);
//////        t.setStatus(0);
//////        Department dept = new Department(1, "Tesouraria", "T");
//////        Employee emp = new Employee(1, "Divanio Silva", 1, dept);
//////        t.setTransferId(2);
//////        t.setEmployee(emp);
//////        t.setDepartment(dept);
////        mgr.transferTicket(t);

                        mgr.autoCreateTicket("Boss");
        
//                employee = config.getEmployee();
//        try {
//            mgr.verifyEmployeeConfig(employee);
//            mgr.addEmployee(employee);
//            mgr.verifyEmployee(employee);
//            
////                mgr.verifyEmployee(employee);
//                
////                for (int i = 0; i < 40; i++) {
////            try {
////                mgr.autoCreateTicket("Tesouraria");
////                Thread.sleep(1000);
////                
////            } catch (InterruptedException ex) {
////                Logger.getLogger(MainTest.class.getName()).log(Level.SEVERE, null, ex);
////            }
////        }
//        
////        for (int i = 0; i < 20; i++) {
////            mgr.autoCreateTicket("Tesouraria");
////
////            
////            try {
////                mgr.autoCreateTicket("Tesouraria");
////                Thread.sleep(1000);
////
////            } catch (InterruptedException ex) {
////                Logger.getLogger(MainTest.class.getName()).log(Level.SEVERE, null, ex);
////            }
////
////        }
//
////        mgr.createTicket(2, 2);
////        employee = config.getEmployee();
////        mgr.getNextTicket(employee);
////
////        mgr.verifyEmployee(employee);
////        mgr.closeTicket(t);
////        System.out.println(mgr.getEmployee(new Employee(3, "teste", "teste")).getName());
////        ApplicationContext context = new ClassPathXmlApplicationContext("ual/lp/spring/bean.xml");
////        EmployeeDAO empDAO = (EmployeeDAO) context.getBean("employeeDAO");
////        
//////        Manager mgr = new Manager();
////        Employee emp = new Employee();
////        emp.setName("Divanio Silva");
////        emp.setDepartment("Financeiro");
////        empDAO.insert(emp);
//        } catch (BadConfigurationException ex) {
//            System.err.println("O camelo do gajo de configurou isso fez merda!\n"+ex.getMessage());
//        }
    }
}
