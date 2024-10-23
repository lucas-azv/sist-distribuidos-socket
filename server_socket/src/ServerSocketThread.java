import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerSocketThread implements Runnable{
    
    private final Socket socketClient;

    public ServerSocketThread(Socket socketClient) {
        this.socketClient = socketClient;
    }

    @Override
    public void run(){
        try(DataInputStream entrada = new DataInputStream(socketClient.getInputStream());
            DataOutputStream saida = new DataOutputStream(socketClient.getOutputStream())
        ){
            String linha;
            while((
                linha = entrada.readUTF()) != null && !linha.trim().isEmpty()){
                    saida.writeUTF("O servidor leu " + linha);
                }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
