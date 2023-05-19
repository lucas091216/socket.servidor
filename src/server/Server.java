
package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    private ServerSocket serverSocket;
    
    private void criarServerSocket(int porta) throws IOException{
        
        serverSocket = new ServerSocket(5555);
    }
    
    private Socket esperaConexao() throws IOException{
        Socket socket = serverSocket.accept();
        return socket;
    }
    
    private void fechaSocket(Socket s) throws IOException{
        s.close();
    }
    
    private void trataConexao(Socket socket) throws IOException, ClassNotFoundException{
        //Entrada e saida de Streams 
        //Tratamento de erros na conversasão com o cliente servidor 
        try{
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        
            //Protocolo 
            //Cliente --> Hello
            //server <-- Hello word! 
            System.out.println("Tratando...");
            Mensagem m = (Mensagem) input.readObject();
            String operacao = (String) m.getOperacao();
            Mensagem reply = null;
            
            if(operacao.equals("HELLO")){
                String nome = (String) m.getParam("nome");
                String sobrenome = (String) m.getParam("Sobrenome");
                
                reply = new Mensagem("HELLOREPLY");
                
                if(nome == null || sobrenome == null){
                    reply.setStatus(Status.PARAMERROR);
                }else{
                    
                reply.setStatus(Status.OK);
                reply.setParam("Mensagem","Hello word" + nome+ "" + sobrenome);
                
                }
                
                
            }
            
            
            output.writeObject(reply);
            output.flush();
            
            
            input.close();
            output.close();
        }catch(IOException e){
            System.out.println("Problema de tratamento de conexao com o cliente : " + socket.getInetAddress());
            System.out.println("Erro: " + e.getMessage());
        }finally{
            //independente de erro, no final de tudo ele fecha o socket 
            fechaSocket(socket);
        }
        
        
    }
    
    
    public static void main(String[] args) throws ClassNotFoundException {
        try{
        Server server = new Server();
            System.out.println("Aguardando Conexao...");
        server.criarServerSocket(5555);
        Socket socket = server.esperaConexao();//Protocolo
            System.out.println("Cliente Conectado.");
        server.trataConexao(socket);
            System.out.println("Cliente Finalizado");
            
            
        } catch(IOException e){
            System.out.println("Error no servidor " + e.getMessage());
        }catch(ClassNotFoundException e){
                System.out.println("Error no cast " + e.getMessage());
        }
             
        
        
       
        
    }
    
    
    
    
}
