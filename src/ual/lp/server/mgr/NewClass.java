/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.mgr;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ual.lp.server.dao.EmployeeDAO;
import ual.lp.server.objects.Employee;

/**
 *
 * @author Pedro
 */
public class NewClass {
    public static void main(String[] args) {
        Manager mgr = new Manager();
        System.out.println(mgr.getEmployee(new Employee(1, "teste", "teste")).getName());
        mgr.insertEmployee(new Employee(1, "Pedro Tomas", "Ceu"));
        System.out.println(mgr.getEmployee(new Employee(3, "teste", "teste")).getName());
//        ApplicationContext context = new ClassPathXmlApplicationContext("ual/lp/spring/bean.xml");
//        EmployeeDAO empDAO = (EmployeeDAO) context.getBean("employeeDAO");
//        
////        Manager mgr = new Manager();
//        Employee emp = new Employee();
//        emp.setName("Divanio Silva");
//        emp.setDepartment("Financeiro");
//        empDAO.insert(emp);
    }
}
