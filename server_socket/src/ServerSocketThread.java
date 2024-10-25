import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerSocketThread implements Runnable {
    
    private final Socket socketClient;
    private final Observador observador;
    private DataOutputStream saida;

    public ServerSocketThread(Observador observador, Socket socketClient) {
        this.observador = observador;
        this.socketClient = socketClient;
    }
    
    public DataOutputStream getSaida() {
        return saida;
    }

    @Override
    public void run() {
        try {
            saida = new DataOutputStream(socketClient.getOutputStream());
            DataInputStream entrada = new DataInputStream(socketClient.getInputStream());
            String linha;

            while ((linha = entrada.readUTF()) != null && !linha.trim().isEmpty()) {
                saida.writeUTF("O servidor leu: " + linha);
                
                if (linha.contains("<todos>")) {
                    observador.enviaMensagem(linha.replace("<todos>", "").trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (saida != null) {
                    saida.close();
                }
                socketClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
