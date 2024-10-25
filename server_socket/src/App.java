import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class App implements Observador {

    private final List<ServerSocketThread> conexoes = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        new App().startServer();
    }

    public void startServer() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(2001)) {
            System.out.println("Servidor iniciado. Esperando conexões...");
            while (true) {
                Socket conexao = serverSocket.accept();
                System.out.println("Conexão estabelecida!");
                ServerSocketThread thread = new ServerSocketThread(this, conexao);
                conexoes.add(thread);
                new Thread(thread).start();
            }
        }
    }

    @Override
    public void enviaMensagem(String mensagem) throws IOException {
        synchronized (conexoes) {
            for (ServerSocketThread thread : conexoes) {
                thread.getSaida().writeUTF(mensagem);
            }
        }
    }

    public void removeConnection(ServerSocketThread thread) {
        synchronized (conexoes) {
            conexoes.remove(thread);
        }
    }
}
