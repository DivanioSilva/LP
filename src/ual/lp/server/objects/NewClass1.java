/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.objects;

import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Divanio Silva
 */
public class NewClass1 {
    
    JSONParser parser = new JSONParser();
    
    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        JSONParser parser = new JSONParser();
        Employee emp = new Employee();
        
        try {
            jsonObject = (JSONObject) parser.parse(new FileReader("config.json"));
            emp.setName((String) jsonObject.get("name"));
            emp.setDepartment((String) jsonObject.get("department"));
        } catch (Exception e) {
            System.err.println("Deu merda no JSON");
        }
        System.out.println(emp.getName()+" "+emp.getDepartment());
    }
}
