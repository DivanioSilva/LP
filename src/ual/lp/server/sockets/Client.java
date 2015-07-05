import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {
	
	public static void main(String[] args) {
		
		Socket socket;
		
//		if (args.length != 4 || args[2].compareTo("-p") != 0 || args[0].compareTo("-h") != 0 ) {
//			System.out.println("-h <hostname> -p <porto>");
//			System.exit(-1);
//		}
		
		try {
			InetAddress host = InetAddress.getByName("192.168.1.9");
			try{
			
			int port = 5005;
		
			//Cria o socket
			socket = new Socket(host,port);
			System.out.println("Ligação efectuada com sucesso.");
			
			//Cria as streams para a comunicação no socket
			PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			//Cria a stream de leitura da consola
			BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
			
			String buffer, msg = new String();
			while(true){
				System.out.println("Insira uma mensagem:");
				msg = console.readLine();
				out.println(msg);
				
				buffer = in.readLine();
				System.out.println("O Servidor respondeu: "+buffer);
				
			}
			
			}catch (IOException e) {
				System.out.println("Erro na ligação ao servidor.");
				System.exit(-1);
			}catch(NumberFormatException ex){
				System.out.println("Porto inválido.");
				System.exit(-1);
			} 
		} catch (UnknownHostException e) {
			System.out.println("Erro ao localizar o servidor.");
			System.exit(-1);
		}
	}

}
