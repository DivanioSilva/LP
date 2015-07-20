package ual.lp.server.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import ual.lp.server.mgr.Manager;

public class SocketSrv extends Thread {

    private ServerSocket server;
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private boolean running = true;
    private Manager manager;
    private int port;

    /**
     * Constrói o obj
     */
    public SocketSrv() {
    }

     public SocketSrv(int port) {
      this.port=port;
    }
    public SocketSrv(int port, Manager manager) {
        this.port=port;
        this.manager = manager;
    }

    /**
     * Função que cria um servidor socket num determinado porto e estabelece
     * ligação
     *
     * @param port
     */ 
    public void listenSocket(int port) {

        System.out.println("#SocketSrv# - A iniciar ..");
     
        String data =null;
        //Primeiro criamos o socket, vinculando-o no porto inserido
        try {
            System.out.println("#SocketSrv# - A criar socket ..");
            server = new ServerSocket(port);
            System.out.println("#SocketSrv# - Socket Criado com sucesso... A aguardar ligação da Asura");
        } catch (IOException e) {
            System.err.println("#SocketSrv# - Não foi possível estabelecer o socket no porto " + port);
            System.exit(-1);
        }
          do {
            //Depois esperamos pela ligação do cliente
            try {
                //client=null;
                client = server.accept();
            } catch (IOException e) {
                System.err.println("#SocketSrv# - Erro ao aceitar ligação de cliente em " + port);
                System.exit(-1);
            }

            //Quando um cliente liga-se, então criamos as streams de leitura e escrita
            try {
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out = new PrintWriter(client.getOutputStream(), true);
            } catch (IOException e) {
                System.err.println("#SocketSrv# - Erro ao criar as streams de leitura e escrita em " + port);
                System.exit(-1);
            }
            
            //E finalmente comunicamos
            while (running) {
                try {
                    data = in.readLine();
                    if (data == null) {
                        running = false;
                    } else {
                        System.out.println("#SocketSrv# - Recebido: " + data);

                        new ProtocolProcessing(out, manager).inMessage(data);
                        
                    }

                } catch (IOException e) {
                    System.err.println("#SocketSrv# - Erro na leitura/escrita no socket em " + port);
                    running = false;
                    //System.exit(-1);
                }
            }

            System.out.println("#SocketSrv# - A fechar streams..");
            //Ao terminar, fechamos as streams
            try {
                in.close();
                out.close();
            } catch (IOException e1) {
                System.err.println("#SocketSrv# - Erro ao fechar as streams");
                System.exit(-1);
            }

            System.out.println("#SocketSrv# - A verificar se o socket cliente está aberto");
            //Fechamos o socket do cliente
          if (!client.isClosed()) {
            System.out.println("#SocketSrv# - Como não está fechado, a fechar socket cliente..");
                try {
                    client.close();
                } catch (IOException e) {
                    System.err.println("#SocketSrv# - Erro ao fechar o socket cliente");
                    System.exit(-1);
                }
          }

        System.out.println("#SocketSrv# - Cliente desligado com sucesso !");
        running = true;
        } while (true);
          

    }

    @Override
    public void run(){
     //    int port =5006;
        
		//Constrói o servior
        // 	SocketSrv servidor = new SocketSrv();
        
        try{
			this.listenSocket(port);		  //Inicia o processo
		}catch(NumberFormatException ex){
			System.err.println("#SocketSrv# - Porto inválido.");
			System.exit(-1);
		}
        
    }
    
}
