package ual.lp.server.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class SocketSrv {
	
	private  ServerSocket server;
	private  Socket client;
	private  BufferedReader in;
	private  PrintWriter out;
	private  boolean running = true;
	
	/**
	 * Constrói o obj
	 */
	public SocketSrv(){}
	
	/**
	 * Função que cria um servidor socket num determinado porto e estabelece ligação
	 * @param port
	 */
	public void listenSocket(int port){
		
		String data = new String();
		//Primeiro criamos o socket, vinculando-o no porto inserido
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("Não foi possível estabelecer o socket no porto "+port);
			System.exit(-1);
		}
		
		//Depois esperamos pela ligação do cliente
		try {
			client = server.accept();
		} catch (IOException e) {
			System.out.println("Erro ao aceitar ligação de cliente em "+port);
			System.exit(-1);
		}
		
		//Quando um cliente liga-se, então criamos as streams de leitura e escrita
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(),true);
		} catch (IOException e) {
			System.out.println("Erro ao criar as streams de leitura e escrita em "+port);
			System.exit(-1);
		}
		
		//E finalmente comunicamos
		while(running){
			try {
				data = in.readLine();
				System.out.println("Recebido: "+data);
				out.println("Recebido: "+data);
                
                //Implementar solução de saida
                
			} catch (IOException e) {
				System.out.println("Erro na leitura/escrita no socket em "+port);
				System.exit(-1);
			}
		}
		
		System.out.println("A terminar..");
		//Ao terminar, fechamos as streams
		try {
			in.close();
			out.close();
		} catch (IOException e1) {
			System.out.println("Erro ao fechar as streams");
			System.exit(-1);
		}
		
		//Fechamos o socket do cliente
		if(!client.isClosed()){
			try {
				client.close();
			} catch (IOException e) {
				System.out.println("Erro ao fechar o socket cliente");
				System.exit(-1);
			}
		}
		
		//Fechamos o socket do servidor
		if(!server.isClosed()){
			try {
				server.close();
			} catch (IOException e) {
				System.out.println("Erro ao fechar o socket servior");
				System.exit(-1);
			}
		}

	}
	
}
