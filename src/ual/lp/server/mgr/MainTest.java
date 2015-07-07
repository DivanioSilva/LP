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
        Manager mgr = new Manager(true);
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

//                        mgr.autoCreateTicket("Boss");
//                employee = config.getEmployee();
//        try {
//            for (int i = 0; i < 40; i++) {
//                mgr.autoCreateTicket("Secretaria");
//                Thread.sleep(1000);
//            }
//        } catch (Exception e) {
//
//        }
    }
}
