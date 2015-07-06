/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.objects;

import java.io.Serializable;
import ual.lp.caller.inf.CallerInf;

/**
 *
 * @author Divanio Silva, Pedro Almeida e Pedro Tomás
 */
public class Employee implements Serializable{
    private int empNumber;
    private String name;
    private int deskNumber;
    private Department department;
    private CallerInf callerInf;

    public Employee() {
    }

    public Employee(int empNumber, String name, int deskNumber, Department department) {
        this.empNumber = empNumber;
        this.name = name;
        this.deskNumber = deskNumber;
        this.department = department;
    }

    public Employee(int empNumber, String name, int deskNumber) {
        this.empNumber = empNumber;
        this.name = name;
        this.deskNumber = deskNumber;
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
     * @return the deskNumber
     */
    public int getDeskNumber() {
        return deskNumber;
    }

    /**
     * @param deskNumber the deskNumber to set
     */
    public void setDeskNumber(int deskNumber) {
        this.deskNumber = deskNumber;
    }

    /**
     * @return the department
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * @return the callerInf
     */
    public CallerInf getCallerInf() {
        return callerInf;
    }

    /**
     * @param callerInf the callerInf to set
     */
    public void setCallerInf(CallerInf callerInf) {
        this.callerInf = callerInf;
    }


}