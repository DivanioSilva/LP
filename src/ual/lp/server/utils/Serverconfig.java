/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.server.utils;

import java.io.FileReader;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import ual.lp.server.objects.Department;

/**
 *
 * @author Pedro
 */
public class Serverconfig {

    JSONObject jsonObject = new JSONObject();
    JSONParser parser = new JSONParser();

    Properties prop = new Properties();
    InputStream input = null;

    private List<Department> departments;
    private String url = null;
    private String serverIP = null;
    private String username = null;
    private String password = null;
    private String closeDay = null;
    private String remoteURL = null;
    private String remoteForm = null;

    public List<Department> getDepartments() {

        departments = new LinkedList<>();

        try {
            jsonObject = (JSONObject) parser.parse(new FileReader("serverconfig.json"));
            JSONArray jSONArray = (JSONArray) jsonObject.get("Departments");

            for (int i = 0; i < jSONArray.size(); i++) {
                JSONObject object = (JSONObject) jSONArray.get(i);
                String department = (String) object.get("department");
                String abbreviation = (String) object.get("abbreviation");
                departments.add(new Department(department, abbreviation));

                System.out.println(department);
                System.out.println(abbreviation);

            }
            return departments;

//            employee.setName((String) jsonObject.get("name"));
//            employee.setDeskNumber(Integer.parseInt((String) jsonObject.get("deskNumber")));
//            employee.setDepartment(new Department((String) jsonObject.get("department"), (String) jsonObject.get("abbreviation")));
        } catch (Exception e) {
            System.err.println("Erro ao ler o ficheiro de configuração 'serverconfig.json' para construir os departments do server.");
        }

        return departments;
    }

    public String getServerIP() {
        try {
            jsonObject = (JSONObject) parser.parse(new FileReader("serverconfig.json"));
            serverIP = (String) jsonObject.get("serverIP");
            System.out.println(serverIP);

        } catch (Exception e) {
            System.err.println("Erro ao ler o ficheiro de configuração 'serverconfig.json' para obter o IP do server.");
        }

        return serverIP;
    }
//

    public String getUrl() {
        try {
            jsonObject = (JSONObject) parser.parse(new FileReader("serverconfig.json"));
            url = (String) jsonObject.get("url");
            System.out.println(url);

        } catch (Exception e) {
            System.err.println("Erro ao ler o ficheiro de configuração 'serverconfig.json' para obter o url da DB.");
        }

        return url;
    }

    public String getUsername() {
        try {
            jsonObject = (JSONObject) parser.parse(new FileReader("serverconfig.json"));
            username = (String) jsonObject.get("username");
            System.out.println(username);

        } catch (Exception e) {
            System.err.println("Erro ao ler o ficheiro de configuração 'serverconfig.json' para obter o username da DB.");
        }

        return username;
    }

    public String getPassword() {
        try {
            jsonObject = (JSONObject) parser.parse(new FileReader("serverconfig.json"));
            password = (String) jsonObject.get("password");
            System.out.println(password);

        } catch (Exception e) {
            System.err.println("Erro ao ler o ficheiro de configuração 'serverconfig.json' para obter a pass da DB.");
        }

        return password;
    }

    /**
     * @return the closeDay
     */
    public String getCloseDay() {
        try {
            jsonObject = (JSONObject) parser.parse(new FileReader("serverconfig.json"));
            closeDay = (String) jsonObject.get("closeDay");
            System.out.println(closeDay);

        } catch (Exception e) {
            System.err.println("Erro ao ler o ficheiro de configuração 'serverconfig.json' para obter a hora do fecho do servidor.");
        }

        return closeDay;
    }

    /**
     * @return the remoteURL
     */
    public String getRemoteURL() {
        try {
            jsonObject = (JSONObject) parser.parse(new FileReader("serverconfig.json"));
            remoteURL = (String) jsonObject.get("remoteURL");
            System.out.println(remoteURL);

        } catch (Exception e) {
            System.err.println("Erro ao ler o ficheiro de configuração 'serverconfig.json' para obter o endereço da pagina web remota.");
        }

        return remoteURL;
    }

    public String getRemoteForm() {
        try {
            jsonObject = (JSONObject) parser.parse(new FileReader("serverconfig.json"));
            remoteForm= (String) jsonObject.get("remoteForm");
            System.out.println(remoteURL);

        } catch (Exception e) {
            System.err.println("Erro ao ler o ficheiro de configuração 'serverconfig.json' para obter o form da pagina web remota");
        }

        return remoteForm;
    }

}
