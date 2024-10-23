import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class App {
    public static void main(String[] args) throws Exception {
        try(ServerSocket serverSocket = new ServerSocket(2001,10000)){
            while (true) {
                System.out.println("Servidor iniciado. Esperando conexões...");
                while(true){
                    Socket conexao = serverSocket.accept();
                    System.out.println("Conexão estabelecida!");
                    new Thread(new ServerSocketThread(conexao),"ThreadConexao").start();
                }    
            }
        }
    }
}
