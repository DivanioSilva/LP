/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.caller.objects;

import java.sql.Time;
import java.util.Date;

/**
 *
 * @author Divanio Silva
 */
public class Ticket {
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

    @Override
    public String toString() {
        String out = "Cool bro! I'm an ticket!!! :-)";
        return out;
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
    
}