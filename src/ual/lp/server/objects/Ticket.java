/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.objects;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 *
 * @author Divanio Silva, Pedro Almeida e Pedro Tomás
 */
public class Ticket implements Serializable{
    private int idTicket;
    private int numberticket;
    private Date createHour;
    private int status;
    private Time timeCall;
    private int transferId;
    private Employee employee;
    private Department department;
    
    public Ticket() {
    }

    public Ticket(int idTicket, int numberticket, Date createHour, int status, Time timeCall, int transferId, Employee employee, Department department) {
        this.idTicket = idTicket;
        this.numberticket = numberticket;
        this.createHour = createHour;
        this.status = status;
        this.timeCall = timeCall;
        this.transferId = transferId;
        this.employee = employee;
        this.department = department;
    }

    public Ticket(int idTicket, int numberticket, Date createHour) {
        this.idTicket = idTicket;
        this.numberticket = numberticket;
        this.createHour = createHour;
    }

    @Override
    public String toString() {
        return "O gajo que atendeu o ticket: "+getEmployee().getName();
    }
    

    /**
     * @return the idTicket
     */
    public int getIdTicket() {
        return idTicket;
    }


    /**
     * @return the hour
     */
    public Date getHour() {
        return getCreateHour();
    }

    /**
     * @param hour the hour to set
     */
    public void setHour(Date hour) {
        this.setCreateHour(hour);
    }

    /**
     * @return the timeCall
     */
    public Time getTimeCall() {
        return timeCall;
    }

    /**
     * @param timeCall the timeCall to set
     */
    public void setTimeCall(Time timeCall) {
        this.timeCall = timeCall;
    }

    /**
     * @return the employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * @param employee the employee to set
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * @param idTicket the idTicket to set
     */
    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    /**
     * @return the createHour
     */
    public Date getCreateHour() {
        return createHour;
    }

    /**
     * @param createHour the createHour to set
     */
    public void setCreateHour(Date createHour) {
        this.createHour = createHour;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return the transferId
     */
    public int getTransferId() {
        return transferId;
    }

    /**
     * @param transferId the transferId to set
     */
    public void setTransferId(int transferId) {
        this.transferId = transferId;
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
     * @return the numberticket
     */
    public int getNumberticket() {
        return numberticket;
    }

    /**
     * @param numberticket the numberticket to set
     */
    public void setNumberticket(int numberticket) {
        this.numberticket = numberticket;
    }

}