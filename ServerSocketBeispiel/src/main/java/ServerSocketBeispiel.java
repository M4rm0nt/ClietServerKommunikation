import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerSocketBeispiel {
    private static final int PORT = 1234;
    private static final ExecutorService pool = Executors.newFixedThreadPool(10);
    private static final Logger logger = LogManager.getLogger(ServerSocketBeispiel.class);

    public static void main(String[] args) {
        logger.info("Server wird gestartet...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server gestartet und hört auf Port " + PORT);

            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                pool.execute(new ClientHandler(socket));
            }
        } catch (IOException e) {
            logger.error("Exception beim Erstellen des ServerSockets: ", e);
        } finally {
            pool.shutdown();
            logger.info("Server heruntergefahren.");
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket socket;
        private static final Logger clientLogger = LogManager.getLogger(ClientHandler.class);

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                String text;
                while ((text = in.readLine()) != null) {
                    clientLogger.info("Nachricht vom Client: {}", text);
                    out.println("Echo: " + text);
                }
            } catch (IOException e) {
                clientLogger.error("Exception beim Verarbeiten einer Client-Verbindung: ", e);
            }
        }
    }
}
