package ual.lp.server.sockets;



public class TestSrvSocket {

	public static void main(String[] args){
		
//		if(args.length != 2 || args[0].compareTo("-p")!=0){
//			System.out.println("-p <porto>");
//			System.exit(-1);
//		}
      
  new Thread(new SocketSrv()).start();
        
  /*      
        int port =5006;
        
		//Constrói o servior
		SocketSrv servidor = new SocketSrv();
		//SDPMultiServerSocket servidor = new SDPMultiServerSocket();
		
        try{
			//int port = Integer.parseInt(args[1]); //Faz o parse do porto
			servidor.listenSocket(port);		  //Inicia o processo
		}catch(NumberFormatException ex){
			System.out.println("Porto inválido.");
			System.exit(-1);
		}
    */    
        
		 
		
		
	}

}
