/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.caller.utils;

import ual.lp.server.objects.Employee;

/**
 *
 * @author Divanio Silva
 */
public class Main {
    
    public static void main(String[] args) {
        ConfigReader conf = new ConfigReader();
        Employee emp = new Employee();
        emp=conf.configEmployee();
        System.out.println(emp);
    }
}
