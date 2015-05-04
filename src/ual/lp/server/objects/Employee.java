/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.objects;

/**
 *
 * @author Divanio Silva, Pedro Almeida e Pedro Tomás
 */
public class Employee {
    private int empNumber;
    private String name;
    private String department;
//    private Department department;

    public Employee() {
    }

    public Employee(int empNumber, String name, String department) {
        this.empNumber = empNumber;
        this.name = name;
        this.department = department;
    }

    @Override
    public String toString() {
        String out = "That´s magic!!! Sou um objecto do tipo Employee :-)";
        return out;
    }

    /**
     * @return the empNumber
     */
    public int getEmpNumber() {
        return empNumber;
    }

    /**
     * @param empNumber the empNumber to set
     */
    public void setEmpNumber(int empNumber) {
        this.empNumber = empNumber;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

}