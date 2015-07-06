import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;


public class SDPMultiServerSocket {
	private  ServerSocket server;
	private  Socket client;
	private  boolean running;
	private  LinkedList<ClientManager> clientList;
	
	public SDPMultiServerSocket(){
		this.running = true;
		this.clientList = new LinkedList<ClientManager>();
	}
	
public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

/**
 * Cria o socket e escuta por ligações
 * 
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
		
		//Depois esperamos pelas ligações dos clientes
		while(running){
			ClientManager m;
			try {
				client = server.accept();
				
				m = new ClientManager(client, clientList.size());
				new Thread(m).start();
				clientList.add(m);
				
			} catch (IOException e) {
				System.out.println("Erro ao aceitar ligação de cliente em "+port);
				System.exit(-1);
			}
		}
}
}
