package ual.lp.server.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
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
    public SocketSrv(Manager manager) {
        this.manager = manager;
    }

    /**
     * Função que cria um servidor socket num determinado porto e estabelece
     * ligação
     *
     * @param port
     */
    public void listenSocket(int port) {

        System.out.println("A iniciar ..");
      
        String data =null;
        //Primeiro criamos o socket, vinculando-o no porto inserido
        try {
            System.out.println("A criar socket ..");
            server = new ServerSocket(port);
            System.out.println("Criado ..");
        } catch (IOException e) {
            System.out.println("Não foi possível estabelecer o socket no porto " + port);
            System.exit(-1);
        }
          do {
            //Depois esperamos pela ligação do cliente
            try {
                //client=null;
                client = server.accept();
            } catch (IOException e) {
                System.out.println("Erro ao aceitar ligação de cliente em " + port);
                System.exit(-1);
            }

            //Quando um cliente liga-se, então criamos as streams de leitura e escrita
            try {
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out = new PrintWriter(client.getOutputStream(), true);
            } catch (IOException e) {
                System.out.println("Erro ao criar as streams de leitura e escrita em " + port);
                System.exit(-1);
            }

            //E finalmente comunicamos
            while (running) {
                try {
                    data = in.readLine();
                    if (data == null) {
                        running = false;
                    } else {
                        System.out.println("Recebido: " + data);

                        new ProtocolProcessing(out, manager).inMessage(data);
                    }

                } catch (IOException e) {
                    System.out.println("Erro na leitura/escrita no socket em " + port);
                    System.exit(-1);
                }
            }

            System.out.println("A fechar streams..");
            //Ao terminar, fechamos as streams
            try {
                in.close();
                out.close();
            } catch (IOException e1) {
                System.out.println("Erro ao fechar as streams");
                System.exit(-1);
            }

            System.out.println("A fechar socket cliente..");
            //Fechamos o socket do cliente
          if (!client.isClosed()) {
            System.out.println("Como não está fechado, a fechar socket cliente..");
                try {
                    client.close();
                } catch (IOException e) {
                    System.out.println("Erro ao fechar o socket cliente");
                    System.exit(-1);
                }
          }


        running = true;
        } while (true);
          
          //            System.out.println("A fechar socket servidor..");
//            //Fechamos o socket do servidor
//            if (!server.isClosed()) {
//                 System.out.println("Como não está fechado, a fechar socket servidor..");
//                try {
//                    server.close();
//                } catch (IOException e) {
//                    System.out.println("Erro ao fechar o socket servior");
//                    System.exit(-1);
//                }
//            }
    }

    @Override
    public void run(){
     //    int port =5006;
        
		//Constrói o servior
		SocketSrv servidor = new SocketSrv();
        
         try{
			//int port = Integer.parseInt(args[1]); //Faz o parse do porto
			servidor.listenSocket(port);		  //Inicia o processo
		}catch(NumberFormatException ex){
			System.out.println("Porto inválido.");
			System.exit(-1);
		}
        
    }
    
}
