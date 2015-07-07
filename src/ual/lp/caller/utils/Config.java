/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.caller.utils;

import java.io.FileReader;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;
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

    static final Logger callerLog = Logger.getLogger("callerLogger");

    Properties prop = new Properties();
    InputStream input = null;

    private Employee employee;
    private String myIP = null;
    private String serverIP = null;

    public Employee getEmployee() {
        employee = new Employee();

        try {
            jsonObject = (JSONObject) parser.parse(new FileReader("callerconfig.json"));
            employee.setName((String) jsonObject.get("name"));
            employee.setDeskNumber(Integer.parseInt((String) jsonObject.get("deskNumber")));
            employee.setDepartment(new Department((String) jsonObject.get("department"), (String) jsonObject.get("abbreviation")));

        } catch (Exception e) {
            callerLog.fatal("Erro ao ler o ficheiro de configuração 'config.json' para construir o employee desta máquina.", e);
            System.err.println("Erro ao ler o ficheiro de configuração 'config.json' para construir o employee desta máquina.");
        }

        return employee;
    }

    public String getMyIP() {
        try {
            jsonObject = (JSONObject) parser.parse(new FileReader("callerconfig.json"));
            myIP = (String) jsonObject.get("myIP");

        } catch (Exception e) {
            callerLog.fatal("Erro ao ler o ficheiro de configuração 'config.json' para obter o IP da máquina.\n", e);
            System.err.println("Erro ao ler o ficheiro de configuração 'config.json' para obter o IP da máquina.");
        }

        return myIP;
    }

    public String getServerIP() {
        try {
            jsonObject = (JSONObject) parser.parse(new FileReader("callerconfig.json"));
            serverIP = (String) jsonObject.get("serverIP");

        } catch (Exception e) {
            callerLog.fatal("Erro ao ler o ficheiro de configuração 'config.json' para obter o IP do servidor.", e);
            System.err.println("Erro ao ler o ficheiro de configuração 'config.json' para obter o IP do servidor.");
        }

        return serverIP;
    }

}
