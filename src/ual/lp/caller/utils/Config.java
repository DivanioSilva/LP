/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.caller.utils;

import java.io.FileReader;
import java.io.InputStream;
import java.util.Properties;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import ual.lp.server.objects.Department;
import ual.lp.server.objects.Employee;

/**
 *
 * @author Divanio Silva
 */
public class Config {

    JSONObject jsonObject = new JSONObject();
    JSONParser parser = new JSONParser();

    Properties prop = new Properties();
    InputStream input = null;

    private Employee employee;
    private String myIP = null;
    private String serverIP = null;

//    JSONParser parser = new JSONParser();

    /*
     private String name;
     private int deskNumber;
     private Department department;
    
     {
     "name":"Divanio Silva", "deskNumber":"1", "abbreviation":"S", "ip":"192.168.1.32"
     }
     */
    public Employee getEmployee() {
        employee = new Employee();

        try {
            jsonObject = (JSONObject) parser.parse(new FileReader("config.json"));
            employee.setName((String) jsonObject.get("name"));
            employee.setDeskNumber(Integer.parseInt((String) jsonObject.get("deskNumber")));
            employee.setDepartment(new Department((String) jsonObject.get("abbreviation"), (String) jsonObject.get("iddepartment")));

        } catch (Exception e) {
            System.err.println("Erro ao ler o ficheiro de configuração 'config.json' para construir o employee desta máquina.");
        }
        
        return employee;
    }

    public String getMyIP() {
        try {
            jsonObject = (JSONObject) parser.parse(new FileReader("config.json"));
            myIP = (String) jsonObject.get("myIP");

        } catch (Exception e) {
            System.err.println("Erro ao ler o ficheiro de configuração 'config.json' para obter o IP da máquina.");
        }

        return myIP;
    }

    public String getServerIP() {
        try {
            jsonObject = (JSONObject) parser.parse(new FileReader("config.json"));
            serverIP = (String) jsonObject.get("serverIP");

        } catch (Exception e) {
            System.err.println("Erro ao ler o ficheiro de configuração 'config.json' para obter o IP do servidor.");
        }
        
        return serverIP;
    }

}
