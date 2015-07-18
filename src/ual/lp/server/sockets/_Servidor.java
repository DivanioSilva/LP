
public class _Servidor {
   
    public static boolean working = true;
   
	public static void main(String[] args){
     
		
		if(args.length != 2 || args[0].compareTo("-p")!=0){
			System.out.println("-p <porto>");
			System.exit(-1);
		}
		//Constrói o servior
		//SDPServerSocket servidor = new SDPServerSocket();
		_SDPMultiServerSocket servidor = new _SDPMultiServerSocket();
        
       
		try{
			int port = Integer.parseInt(args[1]); //Faz o parse do porto
			servidor.listenSocket(port);		  //Inicia o processo
                        
		}catch(NumberFormatException ex){
			System.out.println("Porto inválido.");
			System.exit(-1);
		}
		 
        
		
	}

}
