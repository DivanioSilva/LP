import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientManager implements Runnable {

	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	private boolean running;
	
	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	private int id;

	/**
	 * Constrói o client manager
	 * 
	 * @param c
	 *            descriptor do socket criado para este cliente
	 * @param id  id da thread
	 */
	public ClientManager(Socket c, int id) {

		this.client = c;
		this.id=id;
		this.running = true;
	}

	public void run() {
		String data = new String();
		// Quando um cliente liga-se, então criamos as streams de leitura e
		// escrita
		System.out.println("Starting client "+id);
		try {
			in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
		} catch (IOException e) {
			System.out
					.println("[Thread "+id+"] Erro ao criar as streams de leitura e escrita em ");
			System.exit(-1);
		}

		// E finalmente comunicamos
		while (running) {
			try {
				data = in.readLine();
				if(data == null) running = false;
				
				System.out.println("[Thread "+id+"] Recebido: " + data);
				out.println("[Thread "+id+"] Recebido: " + data);
			} catch (IOException e) {
				System.out.println("[Thread "+id+"] Erro na leitura/escrita no socket em ");
				System.exit(-1);
			}
		}
	}
}
