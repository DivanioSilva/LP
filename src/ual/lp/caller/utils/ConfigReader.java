/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.caller.utils;

import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import ual.lp.server.objects.Employee;

/**
 *
 * @author Divanio Silva
 */
public class ConfigReader {
    private Employee employee;
    
    JSONParser parser = new JSONParser();
    
    public Employee configEmployee() {
        JSONObject jsonObject = new JSONObject();
        JSONParser parser = new JSONParser();
        employee = new Employee();
        
        try {
            jsonObject = (JSONObject) parser.parse(new FileReader("config.json"));
            employee.setName((String) jsonObject.get("name"));
//            employee.setDepartment((String) jsonObject.get("department"));
        } catch (Exception e) {
            System.err.println("Deu merda ao ler o ficheiro JSON");
        }
//        System.out.println(employee.getName()+" "+employee.getDepartment());
        return employee;
    }
}
