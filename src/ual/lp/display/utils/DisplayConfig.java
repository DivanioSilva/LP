/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ual.lp.display.utils;

import java.io.FileReader;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import ual.lp.server.objects.Department;

/**
 *
 * @author Pedro
 */
public class DisplayConfig {

    JSONObject jsonObject = new JSONObject();
    JSONParser parser = new JSONParser();

    Properties prop = new Properties();
    InputStream input = null;

 
    private String filePath = null; 
    private String serverIP = null;
    private String myIP = null;
  
    
    
    
      public String getMyIP() {
        try {
            jsonObject = (JSONObject) parser.parse(new FileReader("displayconfig.json"));
            myIP = (String) jsonObject.get("myIP");

        } catch (Exception e) {
//            callerLog.fatal("Erro ao ler o ficheiro de configuração 'displayconfig.json' para obter o IP da máquina.\n", e);
            System.err.println("Erro ao ler o ficheiro de configuração 'displayconfig.json' para obter o IP da máquina.");
        }

        return myIP;
    }

    public String getServerIP() {
        try {
            jsonObject = (JSONObject) parser.parse(new FileReader("displayconfig.json"));
            serverIP = (String) jsonObject.get("serverIP");

        } catch (Exception e) {
//            callerLog.fatal("Erro ao ler o ficheiro de configuração 'displayconfig.json' para obter o IP do servidor.", e);
            System.err.println("Erro ao ler o ficheiro de configuração 'displayconfig.json' para obter o IP do servidor.");
        }

        return serverIP;
    }

    /**
     * @return the filePath
     */
    public String getFilePath() {
        
        try {
            jsonObject = (JSONObject) parser.parse(new FileReader("displayconfig.json"));
            filePath = (String) jsonObject.get("filePath");

        } catch (Exception e) {
//            callerLog.fatal("Erro ao ler o ficheiro de configuração 'displayconfig.json' para obter o IP do servidor.", e);
            System.err.println("Erro ao ler o ficheiro de configuração 'displayconfig.json' para obter o file path da localização do ficheiro para a pagina web.");
        }

       
        return filePath;
    }
    
}
