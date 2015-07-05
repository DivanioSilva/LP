package ual.lp.server.sockets;



public class TestSrvSocket {

	public static void main(String[] args){
		
//		if(args.length != 2 || args[0].compareTo("-p")!=0){
//			System.out.println("-p <porto>");
//			System.exit(-1);
//		}
        int port =5000;
        
		//Constrói o servior
		SDPServerSocket servidor = new SDPServerSocket();
		//SDPMultiServerSocket servidor = new SDPMultiServerSocket();
		try{
			//int port = Integer.parseInt(args[1]); //Faz o parse do porto
			servidor.listenSocket(port);		  //Inicia o processo
		}catch(NumberFormatException ex){
			System.out.println("Porto inválido.");
			System.exit(-1);
		}
		 
		
		
	}

}
