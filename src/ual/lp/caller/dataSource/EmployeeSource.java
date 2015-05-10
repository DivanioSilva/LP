/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.caller.dataSource;

import java.util.ArrayList;
import ual.lp.server.objects.Employee;

/**
 *
 * @author Pedro
 */
public class EmployeeSource {
    private ArrayList<Employee> empList = new ArrayList<>();
    private Employee emp;
    
    public ArrayList<Employee> getData(){
        emp = new Employee();
        emp.setName("Divanio Silva");
        emp.setDepartment("Secretaria");
        empList.add(emp);
        emp = new Employee();
        emp.setName("Pedro Almeida");
        emp.setDepartment("Financeiro");
        empList.add(emp);
        emp = new Employee();
        emp.setName("Pedro Tomás");
        emp.setDepartment("Administrativo");
        empList.add(emp);
        return empList;
    }
    
//    public static void main(String[] args) {
//        Data d = new Data();
//        
//        for (int i = 0; i < 3; i++) {
//            System.out.println(d.getData().get(i).getName());
//        }
//    }
}
