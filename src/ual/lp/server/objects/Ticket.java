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
    private String numberTicket;
    private Date hour;
    private Time timeCall;
    private Employee employee;

    public Ticket() {
    }

    public Ticket(int idTicket, String numberTicket, Date hour, Time timeCall, Employee employee) {
        this.idTicket = idTicket;
        this.numberTicket = numberTicket;
        this.hour = hour;
        this.timeCall = timeCall;
        this.employee = employee;
    }

    public Ticket(int idTicket, String numberTicket, Date hour) {
        this.idTicket = idTicket;
        this.numberTicket = numberTicket;
        this.hour = hour;
    }

    @Override
    public String toString() {
        return idTicket+" "+numberTicket+" "+hour; //To change body of generated methods, choose Tools | Templates.
    }

    

    /**
     * @return the idTicket
     */
    public int getIdTicket() {
        return idTicket;
    }

    /**
     * @return the numberTicket
     */
    public String getNumberTicket() {
        return numberTicket;
    }

    /**
     * @return the hour
     */
    public Date getHour() {
        return hour;
    }

    /**
     * @param hour the hour to set
     */
    public void setHour(Date hour) {
        this.hour = hour;
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
     * @param numberTicket the numberTicket to set
     */
    public void setNumberTicket(String numberTicket) {
        this.numberTicket = numberTicket;
    }

}