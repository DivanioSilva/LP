/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.caller.utils;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import ual.lp.server.objects.Employee;

/**
 *
 * @author Divanio Silva
 */
public class Main {

    public static void main(String[] args) throws UnknownHostException {
        Config conf = new Config();
        Employee emp = new Employee();
        emp = conf.getEmployee();
        conf.getEmployee();
        System.out.println(emp.getName()+", "+emp.getDepartment().getAbbreviation()+", "+emp.getDeskNumber()+", "+emp.getDepartment().getName());
        System.out.println("MyIP: "+conf.getMyIP());
        System.out.println(Inet4Address.getLocalHost().getHostAddress());
        System.out.println("ServerIP: "+conf.getServerIP());
    }
}
